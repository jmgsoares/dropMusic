package pt.onept.sd1819.dropmusic.server.service;

import pt.onept.sd1819.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.sd1819.dropmusic.common.server.contract.DropmusicServerInterface;
import pt.onept.sd1819.dropmusic.common.server.contract.subcontract.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class DropmusicServer extends UnicastRemoteObject implements DropmusicServerInterface {
	private MulticastHandler multicastHandler;


	private UserManagerInterface userManager;
	private AlbumManagerInterface albumManager;
	private MusicManagerInterface musicManager;
	private ArtistManagerInterface artistManager;
	private NotificationManagerInterface notificationManager;
	private ReviewManagerInterface reviewManager;
	private DropBoxRestManagerInterface dropBoxRestManager;

	public DropmusicServer(MulticastHandler multicastHandler) throws RemoteException {
		super();
		this.multicastHandler = multicastHandler;
		this.userManager = new UserManager(this.multicastHandler);
		this.albumManager = new AlbumManager(this.multicastHandler);
		this.musicManager = new MusicManager(this.multicastHandler);
		this.artistManager = new ArtistManager(this.multicastHandler);
		this.notificationManager = new NotificationManager(this.multicastHandler);
		this.reviewManager = new ReviewManager(this.multicastHandler);
		this.dropBoxRestManager = new DropBoxManager();
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

	@Override
	public NotificationManagerInterface notification() {
		return this.notificationManager;
	}

	@Override
	public ReviewManagerInterface review() {
		return this.reviewManager;
	}

	@Override
	public DropBoxRestManagerInterface dropBoxAPI() {
		return this.dropBoxRestManager;
	}

}
