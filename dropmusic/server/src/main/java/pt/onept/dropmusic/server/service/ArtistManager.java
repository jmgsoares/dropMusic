package pt.onept.dropmusic.server.service;

import pt.onept.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.dropmusic.common.exception.DuplicatedException;
import pt.onept.dropmusic.common.exception.NotFoundException;
import pt.onept.dropmusic.common.exception.UnauthorizedException;
import pt.onept.dropmusic.common.server.contract.subcontract.ArtistManagerInterface;
import pt.onept.dropmusic.common.server.contract.type.Artist;
import pt.onept.dropmusic.common.server.contract.type.Message;
import pt.onept.dropmusic.common.utililty.GsonUtility;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.UUID;

public class ArtistManager extends UnicastRemoteObject implements ArtistManagerInterface {
	private MulticastHandler multicastHandler;

	public ArtistManager(MulticastHandler multicastHandler) throws RemoteException {
		super();
		this.multicastHandler = multicastHandler;
	}

	public MulticastHandler comunicationHandler() { return this.multicastHandler; }

	@Override
	public void create(Artist object) throws DuplicatedException, UnauthorizedException, RemoteException {

	}

	@Override
	public Artist read(Long id) throws NotFoundException, UnauthorizedException, RemoteException {
		Message message;
		Artist artist = new Artist()
				.setId(id);
		UUID requestUuid = UUID.randomUUID();
		String output = GsonUtility.toGson(requestUuid,artist,"get");
		message = comunicationHandler().sendAndWait(requestUuid,output);
		System.out.println();
		return artist;
	}

	@Override
	public void update(Artist object) throws NotFoundException, UnauthorizedException, RemoteException {

	}

	@Override
	public void delete(Artist object) throws NotFoundException, UnauthorizedException, RemoteException {

	}
}
