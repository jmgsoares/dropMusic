package pt.onept.dropmusic.server.service;

import pt.onept.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.dropmusic.common.exception.DuplicatedException;
import pt.onept.dropmusic.common.exception.NotFoundException;
import pt.onept.dropmusic.common.exception.UnauthorizedException;
import pt.onept.dropmusic.common.server.contract.subcontract.UserManagerInterface;
import pt.onept.dropmusic.common.server.contract.type.Notification;
import pt.onept.dropmusic.common.server.contract.type.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserManager extends UnicastRemoteObject implements UserManagerInterface {
	MulticastHandler multicastHandler;

	public UserManager(MulticastHandler multicastHandler) throws RemoteException {
		super();
		this.multicastHandler = multicastHandler;
	}

	public MulticastHandler comunicationHandler() { return this.multicastHandler; }

	@Override
	public User login(User user) throws RemoteException, UnauthorizedException {

		return user
				.setId(1L);
	}

	@Override
	public void create(User object) throws DuplicatedException, UnauthorizedException, RemoteException {

	}

	@Override
	public User read(Long id) throws NotFoundException, UnauthorizedException, RemoteException {
		User test = new User()
				.setId(id)
				.setPassword("123")
				.setUsername("teste")
				.setEditor(true);
		test.getNotifications().add(new Notification("asdf"));
		return test;
	}

	@Override
	public void update(User object) throws NotFoundException, UnauthorizedException, RemoteException {

	}

	@Override
	public void delete(User object) throws NotFoundException, UnauthorizedException, RemoteException {

	}
}
