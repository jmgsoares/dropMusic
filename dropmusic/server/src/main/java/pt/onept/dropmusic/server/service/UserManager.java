package pt.onept.dropmusic.server.service;

import pt.onept.dropmusic.common.exception.DuplicatedException;
import pt.onept.dropmusic.common.exception.NotFoundException;
import pt.onept.dropmusic.common.exception.UnauthorizedException;
import pt.onept.dropmusic.common.server.contract.subcontract.UserManagerInterface;
import pt.onept.dropmusic.common.server.contract.type.Notification;
import pt.onept.dropmusic.common.server.contract.type.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class UserManager extends UnicastRemoteObject implements UserManagerInterface {

	public UserManager() throws RemoteException {
		super();
	}

	@Override
	public void login(User user) throws RemoteException, UnauthorizedException {
	}

	@Override
	public List<Notification> getNotifications(User user) throws RemoteException {
		return null;
	}

	@Override
	public void create(User object) throws DuplicatedException, UnauthorizedException, RemoteException {

	}

	@Override
	public void update(User object) throws NotFoundException, UnauthorizedException, RemoteException {

	}

	@Override
	public void delete(User object) throws NotFoundException, UnauthorizedException, RemoteException {

	}
}
