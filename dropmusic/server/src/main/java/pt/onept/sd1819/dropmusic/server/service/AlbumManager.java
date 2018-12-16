package pt.onept.sd1819.dropmusic.server.service;

import pt.onept.sd1819.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.sd1819.dropmusic.common.communication.protocol.Message;
import pt.onept.sd1819.dropmusic.common.communication.protocol.MessageBuilder;
import pt.onept.sd1819.dropmusic.common.communication.protocol.Operation;
import pt.onept.sd1819.dropmusic.common.exception.*;
import pt.onept.sd1819.dropmusic.common.server.contract.subcontract.AlbumManagerInterface;
import pt.onept.sd1819.dropmusic.common.server.contract.type.Album;
import pt.onept.sd1819.dropmusic.common.server.contract.type.Notification;
import pt.onept.sd1819.dropmusic.common.server.contract.type.User;
import pt.onept.sd1819.dropmusic.server.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class AlbumManager extends UnicastRemoteObject implements AlbumManagerInterface {
	private MulticastHandler multicastHandler;

	public AlbumManager(MulticastHandler multicastHandler) throws RemoteException {
		super();
		this.multicastHandler = multicastHandler;
	}

	@Override
	public void create(User self, Album object) throws DuplicatedException, UnauthorizedException, RemoteException, IncompleteException, DataServerException {
		Message outgoing = MessageBuilder.build(Operation.CREATE, self)
				.setData(object);
		try {
			Message incoming = multicastHandler.sendAndWait(outgoing);
			switch (incoming.getOperation()) {
				case SUCCESS:
					break;
				case NO_PERMIT:
					throw new UnauthorizedException();
				case INCOMPLETE:
					throw new IncompleteException();
				case DUPLICATE:
					throw new DuplicatedException();
				case EXCEPTION:
					throw new RemoteException();
			}
		} catch (TimeoutException e) {
			System.out.println("NO SERVER ANSWER!");
			throw new DataServerException();
		}
	}

	@Override
	public Album read(User self, Album object) throws NotFoundException, DataServerException {
		Message incoming;
		Message outgoing = MessageBuilder.build(Operation.READ, self)
				.setData(object);
		try {
			incoming = this.multicastHandler.sendAndWait(outgoing);
			Album album = (Album) incoming.getData();
			if (album == null) throw new NotFoundException();
			return album;
		} catch (TimeoutException e) {
			System.out.println("NO SERVER ANSWER!");
			throw new DataServerException();
		}
	}

	@Override
	public void update(User self, Album object) throws NotFoundException, UnauthorizedException, RemoteException, IncompleteException  {
		Message incoming;
		Message outgoing = MessageBuilder.build(Operation.UPDATE, self)
				.setData(object);
		try {
			incoming = multicastHandler.sendAndWait(outgoing);
			switch (incoming.getOperation()) {
				case SUCCESS:
					Message incomingEditorList = multicastHandler.sendAndWait(MessageBuilder.build(Operation.GET_EDITORS, self).setData(object));
					List<User> editorList = incomingEditorList.getDataList();
					if (editorList != null) editorList.forEach(e -> {
						try {
							Server.dropmusicServer.notification().notifyUser(
									e,
									new Notification().setMessage(
											"The album " + object.getId() + " - " + object.getName() + " was updated"
									)
							);
						} catch (RemoteException | DataServerException e1) {
							e1.printStackTrace();
						}
					});
					break;
				case NO_PERMIT:
					throw new UnauthorizedException();
				case EXCEPTION:
					throw new RemoteException();
				case NOT_FOUND:
					throw new NotFoundException();
				case INCOMPLETE:
					throw new IncompleteException();
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(User self, Album object) {

	}

	@Override
	public List<Album> search(User self, String query) throws DataServerException {
		List<Album> albums;
		Message incoming;
		Message outgoing = MessageBuilder.build(Operation.SEARCH, self)
				.setQuery(query);
		try {
			incoming = this.multicastHandler.sendAndWait(outgoing);
			albums = (List<Album>) incoming.getDataList();

		} catch (TimeoutException e) {
			System.out.println("NO SERVER ANSWER!");
			throw new DataServerException();
		}
		return albums;
	}

	@Override
	public List<Album> list(User self) throws RemoteException, DataServerException {
		List<Album> artistList;
		Message incoming;
		Message outgoing = MessageBuilder.build(Operation.LIST, self)
				.setData(new Album());
		try {
			incoming = multicastHandler.sendAndWait(outgoing);
			artistList = incoming.getDataList();
			switch (incoming.getOperation()) {
				case SUCCESS:
					break;
				case EXCEPTION:
					throw new RemoteException();

			}
		} catch (TimeoutException e) {
			System.out.println("NO SERVER ANSWER!");
			throw new DataServerException();
		}
		return artistList;
	}
}
