package pt.onept.dropmusic.server.service;

import pt.onept.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.dropmusic.common.communication.protocol.Message;
import pt.onept.dropmusic.common.communication.protocol.MessageBuilder;
import pt.onept.dropmusic.common.communication.protocol.Operation;
import pt.onept.dropmusic.common.exception.*;
import pt.onept.dropmusic.common.server.contract.subcontract.AlbumManagerInterface;
import pt.onept.dropmusic.common.server.contract.type.Album;
import pt.onept.dropmusic.common.server.contract.type.Album;
import pt.onept.dropmusic.common.server.contract.type.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
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
	public Album read(User self, Album object) throws NotFoundException, UnauthorizedException, RemoteException, DataServerException {
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
	public void update(User self, Album object) throws NotFoundException, UnauthorizedException, RemoteException, IncompleteException {

	}

	@Override
	public void delete(User self, Album object) throws NotFoundException, UnauthorizedException, RemoteException {

	}

	@Override
	public List<Album> search(User self, String query) throws RemoteException, DataServerException {
		List<Album> albums = new LinkedList<>();
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
}
