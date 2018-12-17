package pt.onept.sd1819.dropmusic.server.service;

import pt.onept.sd1819.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.sd1819.dropmusic.common.communication.protocol.Message;
import pt.onept.sd1819.dropmusic.common.communication.protocol.MessageBuilder;
import pt.onept.sd1819.dropmusic.common.communication.protocol.Operation;
import pt.onept.sd1819.dropmusic.common.exception.*;
import pt.onept.sd1819.dropmusic.common.server.contract.Listable;
import pt.onept.sd1819.dropmusic.common.server.contract.subcontract.UserManagerInterface;
import pt.onept.sd1819.dropmusic.common.server.contract.type.Notification;
import pt.onept.sd1819.dropmusic.common.server.contract.type.User;
import pt.onept.sd1819.dropmusic.server.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Class to manage all the user related operations
 */
public class UserManager extends UnicastRemoteObject implements UserManagerInterface, Listable<User> {
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
	public void create(User self, User object) throws DuplicatedException, DataServerException, RemoteException, IncompleteException {
		if (self==null || (self.getUsername().equals("") || self.getPassword().equals(""))) throw new IncompleteException();
		Message outgoing = MessageBuilder.build(Operation.REGISTER, self);
		try {
			Message incoming = multicastHandler.sendAndWait(outgoing);
			switch (incoming.getOperation()) {
				case SUCCESS:
					break;
				case DUPLICATE:
					throw new DuplicatedException();
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
	public User read(User self, User object) throws NotFoundException, UnauthorizedException, RemoteException, DataServerException{
		Message incoming;
		Message outgoing = MessageBuilder.build(Operation.READ, self)
				.setData(object);
		try {
			incoming = this.multicastHandler.sendAndWait(outgoing);
			User user = (User) incoming.getData();
			if (user == null) throw new NotFoundException();
			return user;
		} catch (TimeoutException e) {
			System.out.println("NO SERVER ANSWER!");
			throw new DataServerException();
		}
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
					if (self.getId() != 0 && object.getEditor()) {
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

	@Override
	public List<User> list(User self) throws RemoteException, DataServerException {
		List<User> userList;
		Message incoming;
		Message outgoing = MessageBuilder.build(Operation.LIST, self)
				.setData(new User());

		try {
			incoming = multicastHandler.sendAndWait(outgoing);
			userList = incoming.getDataList();
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
		return userList;
	}
}
