package pt.onept.sd1819.dropmusic.server.service;

import pt.onept.sd1819.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.sd1819.dropmusic.common.communication.protocol.Message;
import pt.onept.sd1819.dropmusic.common.communication.protocol.MessageBuilder;
import pt.onept.sd1819.dropmusic.common.communication.protocol.Operation;
import pt.onept.sd1819.dropmusic.common.exception.*;
import pt.onept.sd1819.dropmusic.common.server.contract.subcontract.MusicManagerInterface;
import pt.onept.sd1819.dropmusic.common.server.contract.type.Music;
import pt.onept.sd1819.dropmusic.common.server.contract.type.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.TimeoutException;

public class MusicManager extends UnicastRemoteObject implements MusicManagerInterface {
	private MulticastHandler multicastHandler;

	public MusicManager(MulticastHandler multicastHandler) throws RemoteException {
		super();
		this.multicastHandler = multicastHandler;
	}

	@Override
	public void create(User self, Music object) throws DuplicatedException, DataServerException, UnauthorizedException, RemoteException, IncompleteException {
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
				default:
					throw new RemoteException();
			}
		} catch (TimeoutException e) {
			System.out.println("NO SERVER ANSWER!");
			throw new DataServerException();
		}
	}

	@Override
	public Music read(User self, Music object) throws NotFoundException, DataServerException {
		Message incoming;
		Message outgoing = MessageBuilder.build(Operation.READ, self)
				.setData(object);
		try {
			incoming = this.multicastHandler.sendAndWait(outgoing);
			Music music = (Music) incoming.getData();
			if (music == null) throw new NotFoundException();
			return music;
		} catch (TimeoutException e) {
			System.out.println("NO SERVER ANSWER!");
			throw new DataServerException();
		}
	}


	@Override
	public void update(User self, Music object) {

	}

	@Override
	public void delete(User self, Music object) {

	}
}
