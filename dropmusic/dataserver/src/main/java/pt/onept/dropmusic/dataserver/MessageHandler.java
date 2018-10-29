package pt.onept.dropmusic.dataserver;

import pt.onept.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.dropmusic.common.communication.protocol.Message;
import pt.onept.dropmusic.common.communication.protocol.MessageBuilder;
import pt.onept.dropmusic.common.communication.protocol.Operation;
import pt.onept.dropmusic.common.exception.IncompleteException;
import pt.onept.dropmusic.common.exception.NotFoundException;
import pt.onept.dropmusic.common.exception.UnauthorizedException;
import pt.onept.dropmusic.common.server.contract.type.DropmusicDataType;
import pt.onept.dropmusic.common.server.contract.type.Notification;
import pt.onept.dropmusic.common.server.contract.type.User;
import pt.onept.dropmusic.dataserver.database.DatabaseManager;
import pt.onept.dropmusic.dataserver.database.TypeFactory;

import java.io.InvalidClassException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;

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

	private void handle(Message message) {
		try {
			Method method = this.getClass().getDeclaredMethod(message.getOperation().toString().toLowerCase(), Message.class, Message.class);
			method.setAccessible(true);
			Message reply = MessageBuilder.buildReply(message, null);
			method.invoke(this, message, reply);
			if(reply != null & Dataserver.isMaster) this.multicastHandler.send(reply);
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	private void login(Message incoming, Message outgoing) {
		User user;
		try {
			user = this.dbManager.login(incoming.getSelf());
			outgoing.setOperation(Operation.SUCCESS)
					.setSelf(user);
		} catch (SQLException e) {
			outgoing.setOperation(Operation.EXCEPTION);
		} catch (UnauthorizedException e) {
			outgoing.setOperation(Operation.NO_PERMIT);
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
		if(!incoming.getSelf().isEditor()) outgoing.setOperation(Operation.NO_PERMIT);
		else this.create_raw(incoming, outgoing);
	}

	private void register(Message incoming, Message outgoing) {
		incoming.setData(incoming.getSelf());
		this.create_raw(incoming, outgoing);
		outgoing.setSelf((User) outgoing.getData());
	}

	private void create_raw(Message incoming, Message outgoing) {
		DropmusicDataType data = incoming.getData();

		try {
			outgoing.setData(this.dbManager.insert(TypeFactory.getSubtype(data), data))
					.setOperation(Operation.SUCCESS);
		} catch (SQLException e) {
			e.printStackTrace();
			if( e.getMessage().contains("duplicate")) outgoing.setOperation(Operation.DUPLICATE);
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
			outgoing.setDataList(this.dbManager.searchAlbum(incoming.getQuery()));
		} catch (SQLException e) {
			e.printStackTrace();
			outgoing.setOperation(Operation.EXCEPTION);
		}

	}

	private void update(Message incoming, Message outgoing) {
		if(!incoming.getSelf().isEditor()) outgoing.setOperation(Operation.NO_PERMIT);
		DropmusicDataType data = incoming.getData();
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

	private void add_review(Message incoming, Message outgoing) {
		DropmusicDataType data = incoming.getData();

		try {
			outgoing.setData(this.dbManager.insert(TypeFactory.getSubtype(data), data))
					.setOperation(Operation.SUCCESS);
		} catch (SQLException e) {
			e.printStackTrace();
			if( e.getMessage().contains("not present")) outgoing.setOperation(Operation.INCOMPLETE);
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
		} catch (SQLException | NotFoundException e) {
			e.printStackTrace();
			outgoing.setOperation(Operation.EXCEPTION);
		}
	}

	private void update_user(Message incoming, Message outgoing) {
		if(!incoming.getSelf().isEditor()) outgoing.setOperation(Operation.NO_PERMIT);
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
}