package pt.onept.dropmusic.server.service;

import com.google.gson.JsonSyntaxException;
import pt.onept.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.dropmusic.common.communication.protocol.MessageBuilder;
import pt.onept.dropmusic.common.exception.DuplicatedException;
import pt.onept.dropmusic.common.exception.NotFoundException;
import pt.onept.dropmusic.common.exception.UnauthorizedException;
import pt.onept.dropmusic.common.server.contract.subcontract.ArtistManagerInterface;
import pt.onept.dropmusic.common.server.contract.type.Artist;
import pt.onept.dropmusic.common.communication.protocol.Message;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class ArtistManager extends UnicastRemoteObject implements ArtistManagerInterface {

	private MulticastHandler multicastHandler;

	public ArtistManager(MulticastHandler multicastHandler) throws RemoteException {
		super();
		this.multicastHandler = multicastHandler;
	}

	@Override
	public void create(Artist object) throws DuplicatedException, UnauthorizedException, RemoteException {

	}

	@Override
	public Artist read(Long id) throws NotFoundException, UnauthorizedException, RemoteException {
		Message message;
		Artist artist = null;
		Message output = MessageBuilder.buildMessage(artist, "get");
		try {
			message = this.multicastHandler.sendAndWait(output);
			List<Artist> artistList = message.getDataList();
			if (artistList.isEmpty()) throw new NotFoundException();
			assert artistList.size() == 1;
			artist = (Artist) message.getDataList().get(0);
			return artist;
		} catch (TimeoutException e) {
			//TODO FAILOVER
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		throw new NotFoundException();
	}

	@Override
	public void update(Artist object) throws NotFoundException, UnauthorizedException, RemoteException {

	}

	@Override
	public void delete(Artist object) throws NotFoundException, UnauthorizedException, RemoteException {

	}
}
