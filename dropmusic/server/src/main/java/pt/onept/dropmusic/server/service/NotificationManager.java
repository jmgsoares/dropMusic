package pt.onept.dropmusic.server.service;

import pt.onept.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.dropmusic.common.server.contract.subcontract.NotificationManagerInterface;
import pt.onept.dropmusic.common.server.contract.type.Notification;
import pt.onept.dropmusic.common.server.contract.type.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class NotificationManager extends UnicastRemoteObject implements NotificationManagerInterface {
	private MulticastHandler multicastHandler;

	public NotificationManager(MulticastHandler multicastHandler) throws RemoteException {
		super();
		this.multicastHandler = multicastHandler;
	}

	@Override
	public List<Notification> get(User self) {
		return null;
	}

	@Override
	public void notifyUser(User userToNotify, Notification notification) {

	}

	@Override
	public void delete(User self, Long lastSeenNotificationId) {

	}
}
