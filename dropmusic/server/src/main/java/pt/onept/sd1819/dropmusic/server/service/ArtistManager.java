package pt.onept.sd1819.dropmusic.server.service;

import pt.onept.sd1819.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.sd1819.dropmusic.common.communication.protocol.Message;
import pt.onept.sd1819.dropmusic.common.communication.protocol.MessageBuilder;
import pt.onept.sd1819.dropmusic.common.communication.protocol.Operation;
import pt.onept.sd1819.dropmusic.common.exception.*;
import pt.onept.sd1819.dropmusic.common.server.contract.subcontract.ArtistManagerInterface;
import pt.onept.sd1819.dropmusic.common.server.contract.type.Artist;
import pt.onept.sd1819.dropmusic.common.server.contract.type.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.TimeoutException;

public class ArtistManager extends UnicastRemoteObject implements ArtistManagerInterface {

	private MulticastHandler multicastHandler;

	public ArtistManager(MulticastHandler multicastHandler) throws RemoteException {
		super();
		this.multicastHandler = multicastHandler;
	}

	@Override
	public void create(User self, Artist object) throws DuplicatedException, UnauthorizedException, RemoteException, DataServerException {
		Message outgoing = MessageBuilder.build(Operation.CREATE, self)
				.setData(object);

		try {
			Message incoming = multicastHandler.sendAndWait(outgoing);
			switch (incoming.getOperation()) {
				case SUCCESS:
					break;
				case NO_PERMIT:
					throw new UnauthorizedException();
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
	public Artist read(User self, Artist object) throws NotFoundException, DataServerException {
		Message incoming;
		Message outgoing = MessageBuilder.build(Operation.READ, self)
				.setData(object);
		try {
			incoming = this.multicastHandler.sendAndWait(outgoing);
			Artist artist = (Artist) incoming.getData();
			if (artist == null) throw new NotFoundException();
			return artist;
		} catch (TimeoutException e) {
			System.out.println("NO SERVER ANSWER!");
			throw new DataServerException();
		}
	}

	@Override
	public void update(User self, Artist object) throws NotFoundException, UnauthorizedException, RemoteException, IncompleteException {
		Message incoming;
		Message outgoing = MessageBuilder.build(Operation.UPDATE, self)
				.setData(object);
		try {
			incoming = multicastHandler.sendAndWait(outgoing);
			switch (incoming.getOperation()) {
				case SUCCESS:
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
	public void delete(User self, Artist object) {

	}
}