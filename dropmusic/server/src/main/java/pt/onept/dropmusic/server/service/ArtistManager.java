package pt.onept.dropmusic.server.service;

import com.google.gson.JsonSyntaxException;
import pt.onept.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.dropmusic.common.communication.protocol.MessageBuilder;
import pt.onept.dropmusic.common.communication.protocol.Operation;
import pt.onept.dropmusic.common.exception.DuplicatedException;
import pt.onept.dropmusic.common.exception.IncompleteException;
import pt.onept.dropmusic.common.exception.NotFoundException;
import pt.onept.dropmusic.common.exception.UnauthorizedException;
import pt.onept.dropmusic.common.server.contract.subcontract.ArtistManagerInterface;
import pt.onept.dropmusic.common.server.contract.type.Artist;
import pt.onept.dropmusic.common.communication.protocol.Message;
import pt.onept.dropmusic.common.server.contract.type.User;

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
	public void create(User self, Artist object) throws DuplicatedException, UnauthorizedException, RemoteException, IncompleteException {
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
			e.printStackTrace();
			throw new RemoteException();
		}
	}

	@Override
	public Artist read(User self, Artist object) throws NotFoundException, UnauthorizedException, RemoteException {
		Message incoming;
		Message outgoing = MessageBuilder.build(Operation.READ, self)
				.setData(object);
		try {
			incoming = this.multicastHandler.sendAndWait(outgoing);
			Artist artist = (Artist) incoming.getData();
			if (artist == null) throw new NotFoundException();
			return artist;
		} catch (TimeoutException e) {
			//TODO FAILOVER
			e.printStackTrace();
		}
		throw new NotFoundException();
	}

	@Override
	public void update(User self, Artist object) throws NotFoundException, UnauthorizedException, RemoteException, IncompleteException {

	}

	@Override
	public void delete(User self, Artist object) throws NotFoundException, UnauthorizedException, RemoteException {

	}
}
