package pt.onept.sd1819.dropmusic.dataserver;

import pt.onept.sd1819.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.sd1819.dropmusic.common.communication.protocol.Message;
import pt.onept.sd1819.dropmusic.common.communication.protocol.MessageBuilder;
import pt.onept.sd1819.dropmusic.common.communication.protocol.Operation;
import pt.onept.sd1819.dropmusic.common.exception.IncompleteException;
import pt.onept.sd1819.dropmusic.common.exception.NotFoundException;
import pt.onept.sd1819.dropmusic.common.exception.UnauthorizedException;
import pt.onept.sd1819.dropmusic.common.server.contract.type.*;
import pt.onept.sd1819.dropmusic.dataserver.database.DatabaseManager;
import pt.onept.sd1819.dropmusic.dataserver.database.TypeFactory;

import java.io.InvalidClassException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Class responsible for handling all the messages that come via multicast and sort them to the proper dataBase function call
 * All methods in this class are directly related to the operations declared in the protocol
 * @see Operation
 */
final class MessageHandler implements Runnable {
	private DatabaseManager dbManager;
	private MulticastHandler multicastHandler;

	public MessageHandler(DatabaseManager dbManager, MulticastHandler multicastHandler) {
		this.dbManager = dbManager;
		this.multicastHandler = multicastHandler;
	}

	@Override
	public void run() {
		Message incoming;
		while (!Thread.interrupted()) {
			incoming = this.multicastHandler.receive();
			handle(incoming);
		}
	}

	/**
	 * Function to handle message receiving and calls the appropriate method via the Operation on the incoming message
	 * @param message the incoming message
	 */
	private void handle(Message message) {
		try {
			Method method = this.getClass().getDeclaredMethod(message.getOperation().toString().toLowerCase(), Message.class, Message.class);
			method.setAccessible(true);
			Message reply = MessageBuilder.buildReply(message, null);
			method.invoke(this, message, reply);
			if (reply != null & Dataserver.isMaster) this.multicastHandler.send(reply);
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	private void get_editors(Message incoming, Message outgoing) {
		try {
			outgoing.setDataList(this.dbManager.getEditors(TypeFactory.getSubtype(incoming.getData()), incoming.getData()))
					.setOperation(Operation.SUCCESS);
		} catch (SQLException e) {
			e.printStackTrace();
			outgoing.setOperation(Operation.EXCEPTION);
		}
	}

	private void login(Message incoming, Message outgoing) {
		User user;
		try {
			user = this.dbManager.login(incoming.getSelf());
			outgoing.setOperation(Operation.SUCCESS)
					.setSelf(user);
		} catch (SQLException e) {
			e.printStackTrace();
			outgoing.setOperation(Operation.EXCEPTION);
		} catch (UnauthorizedException e) {
			outgoing.setOperation(Operation.NO_PERMIT);
		}
	}

	private void clean(Message incoming, Message outgoing) {
		try {
			this.dbManager.clean();
			outgoing.setOperation(Operation.SUCCESS);
		} catch (SQLException e) {
			e.printStackTrace();
			outgoing.setOperation(Operation.EXCEPTION);
		}
	}

	private void read(Message incoming, Message outgoing) {
		DropmusicDataType data = incoming.getData();
		Class tClass = TypeFactory.getSubtype(data);
		try {
			outgoing.setData(this.dbManager.read(tClass, data.getId()))
					.setOperation(Operation.SUCCESS);
		} catch (SQLException e) {
			e.printStackTrace();
			outgoing.setOperation(Operation.EXCEPTION);
		} catch (NotFoundException e) {
			outgoing.setOperation(Operation.NOT_FOUND);
		}
	}

	private void create(Message incoming, Message outgoing) {
		User user = null;
		try {
			user = dbManager.read(User.class, incoming.getSelf().getId());
		} catch (SQLException e) {
			e.printStackTrace();
			outgoing.setOperation(Operation.EXCEPTION);
		} catch (NotFoundException e) {
			outgoing.setOperation(Operation.NOT_FOUND);
		}
		if (!user.getEditor()) outgoing.setOperation(Operation.NO_PERMIT);
		else this.create_raw(incoming, outgoing);
	}

	private void link_file(Message incoming, Message outgoing) {
		create_raw(incoming, outgoing);
	}

	private void register(Message incoming, Message outgoing) {
		incoming.setData(incoming.getSelf());
		this.create_raw(incoming, outgoing);
		outgoing.setSelf((User) outgoing.getData());
	}

	private void create_raw(Message incoming, Message outgoing) {
		DropmusicDataType data = incoming.getData();
		try {
			DropmusicDataType createdObject = this.dbManager.insert(TypeFactory.getSubtype(data), data);
			if(TypeFactory.getSubtype(data).equals(Album.class)) {
				Album createdAlbum = (Album) createdObject;
				Album dataAlbum = (Album) data;
				if(dataAlbum.getMusics() != null) {
					List<Music> musicList = new LinkedList<>();
					for (Music m : dataAlbum.getMusics()) {
						musicList.add(dbManager.insert(Music.class, m.setAlbumId(createdAlbum.getId())));
					}
					if(!musicList.isEmpty()) createdAlbum.setMusics(musicList);
				}
			}
			outgoing.setData(createdObject)
					.setOperation(Operation.SUCCESS);
			accountabilityHandler(MessageBuilder.buildReply(
					incoming,
					outgoing.getOperation())
						.setData(outgoing.getData())
						.setSelf(incoming.getSelf())
			);
		} catch (SQLException e) {
			e.printStackTrace();
			if (e.getMessage().contains("duplicate")) outgoing.setOperation(Operation.DUPLICATE);
			else outgoing.setOperation(Operation.INCOMPLETE);
		} catch (InvalidClassException e) {
			e.printStackTrace();
			outgoing.setOperation(Operation.EXCEPTION);
		} catch (IncompleteException e) {
			e.printStackTrace();
			outgoing.setOperation(Operation.INCOMPLETE);
		}
	}

	private void get_notifications(Message incoming, Message outgoing) {
		try {
			outgoing.setDataList(this.dbManager.readList(Notification.class, incoming.getSelf()))
					.setOperation(Operation.SUCCESS);
		} catch (SQLException e) {
			e.printStackTrace();
			outgoing.setOperation(Operation.EXCEPTION);
		}
	}

	private void search(Message incoming, Message outgoing) {
		DropmusicDataType data = incoming.getData();
		try {
			outgoing.setDataList(this.dbManager.searchAlbum(incoming.getQuery()))
					.setOperation(Operation.SUCCESS);
		} catch (SQLException e) {
			e.printStackTrace();
			outgoing.setOperation(Operation.EXCEPTION);
		}

	}

	private void list(Message incoming, Message outgoing) {
		DropmusicDataType data = incoming.getData();
		Class tClass = TypeFactory.getSubtype(data);
		try {
			if(tClass.equals(File.class)) {
				outgoing.setDataList(this.dbManager.list(incoming.getSelf().getId(), tClass, data))
						.setOperation(Operation.SUCCESS);
			} else {
				outgoing.setDataList(this.dbManager.list(tClass, data))
						.setOperation(Operation.SUCCESS);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			outgoing.setOperation(Operation.EXCEPTION);
		}
	}

	private void share(Message incoming, Message outgoing) {
		try {
			this.dbManager.insertShare(
					incoming.getData().getId(),
					incoming.getTarget().getId()
			);
			outgoing.setOperation(Operation.SUCCESS);
		} catch (SQLException e) {
			e.printStackTrace();
			outgoing.setOperation(Operation.EXCEPTION);
		}
	}

	private void list_shares(Message incoming, Message outgoing) {
		try {
			outgoing.setDataList(this.dbManager.listFileShares(incoming.getSelf().getId(), File.class))
					.setOperation(Operation.SUCCESS);
		} catch (SQLException e) {
			e.printStackTrace();
			outgoing.setOperation(Operation.EXCEPTION);
		}
	}

	private void update(Message incoming, Message outgoing) {
		if (!incoming.getSelf().getEditor()) {
			outgoing.setOperation(Operation.NO_PERMIT);
			return;
		}
		DropmusicDataType data = incoming.getData();
		Class tClass = TypeFactory.getSubtype(data);
		try {
			this.dbManager.update(tClass, data);
			accountabilityHandler(incoming);
			outgoing.setOperation(Operation.SUCCESS);
		} catch (SQLException | InvalidClassException e) {
			outgoing.setOperation(Operation.EXCEPTION);
			e.printStackTrace();
		} catch (IncompleteException e) {
			outgoing.setOperation(Operation.INCOMPLETE);
			e.printStackTrace();
		}
	}

	private void add_review(Message incoming, Message outgoing) {
		DropmusicDataType data = incoming.getData();

		try {
			outgoing.setData(this.dbManager.insert(TypeFactory.getSubtype(data), data))
					.setOperation(Operation.SUCCESS);
		} catch (SQLException e) {
			e.printStackTrace();
			if (e.getMessage().contains("not present")) outgoing.setOperation(Operation.INCOMPLETE);
			else outgoing.setOperation(Operation.EXCEPTION);
		} catch (InvalidClassException e) {
			e.printStackTrace();
			outgoing.setOperation(Operation.EXCEPTION);
		} catch (IncompleteException e) {
			e.printStackTrace();
			outgoing.setOperation(Operation.INCOMPLETE);
		}
	}

	private void delete_notifications(Message incoming, Message outgoing) {
		DropmusicDataType data = incoming.getData();
		Class tClass = TypeFactory.getSubtype(data);
		try {
			dbManager.delete(tClass, incoming.getData());
			System.out.println(tClass.toString());
			outgoing.setOperation(Operation.SUCCESS);
		} catch (SQLException e) {
			e.printStackTrace();
			outgoing.setOperation(Operation.EXCEPTION);
		}
	}

	//TODO can the update be used for this?
	private void update_user(Message incoming, Message outgoing) {
		if (!incoming.getSelf().getEditor()) outgoing.setOperation(Operation.NO_PERMIT);
		DropmusicDataType data = incoming.getTarget();
		Class tClass = TypeFactory.getSubtype(data);
		try {
			this.dbManager.update(tClass, data);
			outgoing.setOperation(Operation.SUCCESS);
		} catch (SQLException | InvalidClassException e) {
			outgoing.setOperation(Operation.EXCEPTION);
			e.printStackTrace();
		} catch (IncompleteException e) {
			outgoing.setOperation(Operation.INCOMPLETE);
			e.printStackTrace();
		}
	}

	/**
	 * This function handles the operation logging
	 * @param incoming the incoming message
	 */
	public void accountabilityHandler (Message incoming) {
		User user = incoming.getSelf();
		DropmusicDataType data = incoming.getData();
		if (user == null || data == null) return;
		Class cls = TypeFactory.getSubtype(data);
		if(cls.equals(Album.class) || cls.equals(Artist.class)) this.dbManager.logInsert(cls, data, user.getId());
	}
}