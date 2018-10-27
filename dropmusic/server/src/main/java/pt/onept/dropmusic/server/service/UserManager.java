package pt.onept.dropmusic.server.service;

import pt.onept.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.dropmusic.common.communication.protocol.Message;
import pt.onept.dropmusic.common.communication.protocol.MessageBuilder;
import pt.onept.dropmusic.common.communication.protocol.Operation;
import pt.onept.dropmusic.common.exception.DuplicatedException;
import pt.onept.dropmusic.common.exception.IncompleteException;
import pt.onept.dropmusic.common.exception.NotFoundException;
import pt.onept.dropmusic.common.exception.UnauthorizedException;
import pt.onept.dropmusic.common.server.contract.subcontract.UserManagerInterface;
import pt.onept.dropmusic.common.server.contract.type.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.TimeoutException;

public class UserManager extends UnicastRemoteObject implements UserManagerInterface {
	private MulticastHandler multicastHandler;

	public UserManager(MulticastHandler multicastHandler) throws RemoteException {
		super();
		this.multicastHandler = multicastHandler;
	}


	@Override
	public User login(User user) throws RemoteException, UnauthorizedException {
		Message outgoing = MessageBuilder.build(Operation.LOGIN, user);
		try {
			Message incoming = multicastHandler.sendAndWait(outgoing);
			switch (incoming.getOperation()) {
				case SUCCESS:
					return incoming.getSelf();
				case NO_PERMIT:
					throw new UnauthorizedException();
				default:
					throw new RemoteException();
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
			throw new RemoteException();
		}
	}

	@Override
	public void create(User self, User object) throws DuplicatedException, UnauthorizedException, RemoteException, IncompleteException {
		Message outgoing = MessageBuilder.build(Operation.REGISTER, self);
		try {
			Message incoming = multicastHandler.sendAndWait(outgoing);
			switch (incoming.getOperation()) {
				case SUCCESS:
					break;
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
	public User read(User self, User object) throws NotFoundException, UnauthorizedException, RemoteException {
		return null;
	}

	@Override
	public void update(User self, User object) throws NotFoundException, UnauthorizedException, RemoteException, IncompleteException {

	}

	@Override
	public void delete(User self, User object) throws NotFoundException, UnauthorizedException, RemoteException {

	}
}
