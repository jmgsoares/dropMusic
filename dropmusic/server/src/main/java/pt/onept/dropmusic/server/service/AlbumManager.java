package pt.onept.dropmusic.server.service;

import pt.onept.dropmusic.common.exception.DuplicatedException;
import pt.onept.dropmusic.common.exception.NotFoundException;
import pt.onept.dropmusic.common.exception.UnauthorizedException;
import pt.onept.dropmusic.common.server.contract.subcontract.AlbumManagerInterface;
import pt.onept.dropmusic.common.server.contract.subcontract.ReviewManagerInterface;
import pt.onept.dropmusic.common.server.contract.type.Album;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class AlbumManager extends UnicastRemoteObject implements AlbumManagerInterface {

	public AlbumManager() throws RemoteException {
		super();
	}

	@Override
	public ReviewManagerInterface review(Album album) throws RemoteException {
		return null;
	}

	@Override
	public void create(Album object) throws DuplicatedException, UnauthorizedException, RemoteException {

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
