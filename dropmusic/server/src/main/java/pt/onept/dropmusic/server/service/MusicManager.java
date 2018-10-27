package pt.onept.dropmusic.server.service;

import pt.onept.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.dropmusic.common.exception.DuplicatedException;
import pt.onept.dropmusic.common.exception.NotFoundException;
import pt.onept.dropmusic.common.exception.UnauthorizedException;
import pt.onept.dropmusic.common.server.contract.subcontract.MusicManagerInterface;
import pt.onept.dropmusic.common.server.contract.type.Music;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MusicManager extends UnicastRemoteObject implements MusicManagerInterface {
	MulticastHandler multicastHandler;

	public MusicManager(MulticastHandler multicastHandler) throws RemoteException {
		super();
		this.multicastHandler = multicastHandler;
	}

	public MulticastHandler comunicationHandler() { return this.multicastHandler; }

	@Override
	public void create(Music object) throws DuplicatedException, UnauthorizedException, RemoteException {

	}

	@Override
	public Music read(Long id) throws NotFoundException, UnauthorizedException, RemoteException {
		return null;
	}

	@Override
	public void update(Music object) throws NotFoundException, UnauthorizedException, RemoteException {

	}

	@Override
	public void delete(Music object) throws NotFoundException, UnauthorizedException, RemoteException {

	}
}
