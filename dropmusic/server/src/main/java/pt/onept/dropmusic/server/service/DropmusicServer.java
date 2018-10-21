package pt.onept.dropmusic.server.service;

import pt.onept.dropmusic.common.server.contract.DropmusicServerInterface;
import pt.onept.dropmusic.common.server.contract.subcontract.AlbumManagerInterface;
import pt.onept.dropmusic.common.server.contract.subcontract.ArtistManagerInterface;
import pt.onept.dropmusic.common.server.contract.subcontract.MusicManagerInterface;
import pt.onept.dropmusic.common.server.contract.subcontract.UserManagerInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DropmusicServer extends UnicastRemoteObject implements DropmusicServerInterface {
	private UserManagerInterface userManager = new UserManager();
	private AlbumManagerInterface albumManager = new AlbumManager();
	private MusicManagerInterface musicManager = new MusicManager();
	private ArtistManagerInterface artistManager = new ArtistManager();

	public DropmusicServer() throws RemoteException {
		super();
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
