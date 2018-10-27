package pt.onept.dropmusic.server.service;

import pt.onept.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.dropmusic.common.server.contract.DropmusicServerInterface;
import pt.onept.dropmusic.common.server.contract.subcontract.AlbumManagerInterface;
import pt.onept.dropmusic.common.server.contract.subcontract.ArtistManagerInterface;
import pt.onept.dropmusic.common.server.contract.subcontract.MusicManagerInterface;
import pt.onept.dropmusic.common.server.contract.subcontract.UserManagerInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DropmusicServer extends UnicastRemoteObject implements DropmusicServerInterface {
	MulticastHandler multicastHandler;

	private UserManagerInterface userManager;
	private AlbumManagerInterface albumManager;
	private MusicManagerInterface musicManager;
	private ArtistManagerInterface artistManager;

	public DropmusicServer(MulticastHandler multicastHandler) throws RemoteException {
		super();
		this.multicastHandler = multicastHandler;
		this.userManager = new UserManager(this.multicastHandler);
		this.albumManager = new AlbumManager(this.multicastHandler);
		this.musicManager = new MusicManager(this.multicastHandler);
		this.artistManager = new ArtistManager(this.multicastHandler);
	}

	public MulticastHandler comunicationHandler() {
		return this.multicastHandler;
	}

	@Override
	public UserManagerInterface user() {
		return this.userManager;
	}

	@Override
	public AlbumManagerInterface album() {
		return this.albumManager;
	}

	@Override
	public MusicManagerInterface music() {
		return this.musicManager;
	}

	@Override
	public ArtistManagerInterface artist() {
		return this.artistManager;
	}
}
