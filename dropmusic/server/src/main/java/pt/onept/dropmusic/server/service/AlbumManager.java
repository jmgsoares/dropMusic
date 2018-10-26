package pt.onept.dropmusic.server.service;

import pt.onept.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.dropmusic.common.exception.DuplicatedException;
import pt.onept.dropmusic.common.exception.NotFoundException;
import pt.onept.dropmusic.common.exception.UnauthorizedException;
import pt.onept.dropmusic.common.server.contract.subcontract.AlbumManagerInterface;
import pt.onept.dropmusic.common.server.contract.subcontract.ReviewManagerInterface;
import pt.onept.dropmusic.common.server.contract.type.Album;
import pt.onept.dropmusic.common.server.contract.type.Message;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class AlbumManager extends UnicastRemoteObject implements AlbumManagerInterface {
	MulticastHandler multicastHandler;

	public AlbumManager(MulticastHandler multicastHandler) throws RemoteException {
		super();
		this.multicastHandler = multicastHandler;
	}

	public MulticastHandler comunicationHandler() { return this.multicastHandler; }

	@Override
	public ReviewManagerInterface review(Album album) throws RemoteException {
		return null;
	}

	@Override
	public void create(Album object) throws DuplicatedException, UnauthorizedException, RemoteException {

	}

	@Override
	public Album read(Long id) throws NotFoundException, UnauthorizedException, RemoteException {
		return null;
	}

	@Override
	public void update(Album object) throws NotFoundException, UnauthorizedException, RemoteException {

	}

	@Override
	public void delete(Album object) throws NotFoundException, UnauthorizedException, RemoteException {

	}

	@Override
	public List<Album> search(String query) throws RemoteException {
		return null;
	}
}
