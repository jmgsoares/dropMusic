package pt.onept.dropmusic.server.service;

import pt.onept.dropmusic.common.client.contract.Notifiable;
import pt.onept.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.dropmusic.common.server.contract.DropmusicServerInterface;
import pt.onept.dropmusic.common.server.contract.subcontract.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class DropmusicServer extends UnicastRemoteObject implements DropmusicServerInterface {
	private MulticastHandler multicastHandler;
	private Map<Long, Notifiable> client;


	private UserManagerInterface userManager;
	private AlbumManagerInterface albumManager;
	private MusicManagerInterface musicManager;
	private ArtistManagerInterface artistManager;
	private NotificationManagerInterface notificationManager;
	private ReviewManagerInterface reviewManager;

	public DropmusicServer(MulticastHandler multicastHandler) throws RemoteException {
		super();
		this.multicastHandler = multicastHandler;
		this.userManager = new UserManager(this.multicastHandler);
		this.albumManager = new AlbumManager(this.multicastHandler);
		this.musicManager = new MusicManager(this.multicastHandler);
		this.artistManager = new ArtistManager(this.multicastHandler);
		this.notificationManager = new NotificationManager(this.multicastHandler);
		this.reviewManager = new ReviewManager(this.multicastHandler);
		this.client = new HashMap<>();
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
	public Map<Long, Notifiable> client() { return this.client;}

	@Override
	public void subscribe(long id, Notifiable client) throws RemoteException {
		if(this.client.put(id,client) == null) System.out.println("Subscribing client with user id " + id);
	}

	@Override
	public void unSubscribe(long id) throws RemoteException {
		this.client.remove(id);
		System.out.println("UnSubscribing client with user id " + id);
	}

}
