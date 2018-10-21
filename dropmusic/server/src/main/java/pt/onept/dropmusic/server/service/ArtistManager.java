package pt.onept.dropmusic.server.service;

import pt.onept.dropmusic.common.exception.DuplicatedException;
import pt.onept.dropmusic.common.exception.NotFoundException;
import pt.onept.dropmusic.common.exception.UnauthorizedException;
import pt.onept.dropmusic.common.server.contract.subcontract.ArtistManagerInterface;
import pt.onept.dropmusic.common.server.contract.type.Artist;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ArtistManager extends UnicastRemoteObject implements ArtistManagerInterface {

	public ArtistManager() throws RemoteException {
		super();
	}

	@Override
	public void create(Artist object) throws DuplicatedException, UnauthorizedException, RemoteException {

	}

	@Override
	public void update(Artist object) throws NotFoundException, UnauthorizedException, RemoteException {

	}

	@Override
	public void delete(Artist object) throws NotFoundException, UnauthorizedException, RemoteException {

	}
}
