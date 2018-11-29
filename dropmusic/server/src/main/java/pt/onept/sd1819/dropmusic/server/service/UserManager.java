package pt.onept.sd1819.dropmusic.server.service;

import pt.onept.sd1819.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.sd1819.dropmusic.common.communication.protocol.Message;
import pt.onept.sd1819.dropmusic.common.communication.protocol.MessageBuilder;
import pt.onept.sd1819.dropmusic.common.communication.protocol.Operation;
import pt.onept.sd1819.dropmusic.common.exception.*;
import pt.onept.sd1819.dropmusic.common.server.contract.subcontract.UserManagerInterface;
import pt.onept.sd1819.dropmusic.common.server.contract.type.Notification;
import pt.onept.sd1819.dropmusic.common.server.contract.type.User;
import pt.onept.sd1819.dropmusic.server.Server;

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
	public User login(User user) throws RemoteException, UnauthorizedException, DataServerException {
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
			System.out.println("NO SERVER ANSWER!");
			throw new DataServerException();
		}
	}

	@Override
	public void create(User self, User object) throws DuplicatedException, DataServerException, RemoteException {
		Message outgoing = MessageBuilder.build(Operation.REGISTER, self);
		try {
			Message incoming = multicastHandler.sendAndWait(outgoing);
			switch (incoming.getOperation()) {
				case SUCCESS:
					break;
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
	public User read(User self, User object) {
		return null;
	}

	@Override
	public void update(User self, User object) throws NotFoundException, DataServerException, UnauthorizedException, RemoteException, IncompleteException {
		Message incoming;
		Message outgoing = MessageBuilder.build(Operation.UPDATE_USER, self)
				.setTarget(object);
		try {
			incoming = multicastHandler.sendAndWait(outgoing);
			switch (incoming.getOperation()) {
				case SUCCESS:
					if (object.isEditor()) {
						Notification notification = new Notification();
						notification
								.setUserId(object.getId())
								.setMessage("You have been granted editor privileges");
						Server.dropmusicServer.notification().notifyUser(object, notification);
					}
					break;
				case NO_PERMIT:
					throw new UnauthorizedException();
				case NOT_FOUND:
					throw new NotFoundException();
				case INCOMPLETE:
					throw new IncompleteException();
				default:
					throw new RemoteException();
			}
		} catch (TimeoutException e) {
			System.out.println("NO SERVER ANSWER!");
			throw new DataServerException();
		}
	}

	@Override
	public void delete(User self, User object) {

	}
}