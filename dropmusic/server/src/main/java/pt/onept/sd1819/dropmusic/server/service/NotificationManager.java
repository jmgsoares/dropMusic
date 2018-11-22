package pt.onept.sd1819.dropmusic.server.service;

import pt.onept.sd1819.dropmusic.common.client.contract.Notifiable;
import pt.onept.sd1819.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.sd1819.dropmusic.common.communication.protocol.Message;
import pt.onept.sd1819.dropmusic.common.communication.protocol.MessageBuilder;
import pt.onept.sd1819.dropmusic.common.communication.protocol.Operation;
import pt.onept.sd1819.dropmusic.common.exception.*;
import pt.onept.sd1819.dropmusic.common.server.contract.subcontract.NotificationManagerInterface;
import pt.onept.sd1819.dropmusic.common.server.contract.type.Notification;
import pt.onept.sd1819.dropmusic.common.server.contract.type.User;
import pt.onept.sd1819.dropmusic.server.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.concurrent.TimeoutException;


public class NotificationManager extends UnicastRemoteObject implements NotificationManagerInterface {
	private MulticastHandler multicastHandler;

	public NotificationManager(MulticastHandler multicastHandler) throws RemoteException {
		super();
		this.multicastHandler = multicastHandler;
	}

	@Override //TODO REPLACE THIS FUNCTION WITH THE CRUDABLE ONE (READ)
	public List<Notification> get(User self) throws RemoteException, DataServerException {
		try {
			Message outgoing = MessageBuilder.build(Operation.GET_NOTIFICATIONS, self);
			Message incoming = this.multicastHandler.sendAndWait(outgoing);
			switch (incoming.getOperation()) {
				case SUCCESS:
					return incoming.getDataList();
				default:
					throw new RemoteException();
			}
		} catch (TimeoutException e) {
			System.out.println("NO SERVER ANSWER!");
			throw new DataServerException();
		}
	}

	@Override
	public void notifyUser(User userToNotify, Notification notification) {
		System.out.println(userToNotify.getId());
		System.out.println(notification.getMessage());
		Notifiable client = null;
		try {
			client = Server.dropmusicServer.client().get((long) userToNotify.getId());
			System.out.println(client);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		boolean storeNotification = true;
		if (client != null) {
			try {
				storeNotification = !client.notify(notification);
			} catch (RemoteException e) {
				storeNotification = true;
				e.printStackTrace();
			}
		} else if (storeNotification) {
			//TODO STORE NOTIFICATION ON SERVER
		}
	}

	@Override //TODO IMPLEMENT TO CREATE NOTIFICATIONS
	public void create(User self, Notification object) throws DuplicatedException, UnauthorizedException, RemoteException, IncompleteException, DataServerException {

	}

	@Override //TODO IMPLEMENT TO REPLACE THE GET FUNCTION TO READ THE LIST OF NOTIFICATIONS
	public Notification read(User self, Notification object) throws NotFoundException, UnauthorizedException, RemoteException, DataServerException {
		return null;
	}

	/**
	 * This method is not applied to notifications in the scope of this project
	 *
	 * @deprecated
	 */
	@Deprecated
	public void update(User self, Notification object) throws NotFoundException, UnauthorizedException, RemoteException, IncompleteException, DataServerException {

	}

	@Override //TODO IMPLEMENT TO DELETE USER'S NOTIFICATIONS
	public void delete(User self, Notification object) throws NotFoundException, UnauthorizedException, RemoteException, DataServerException {

	}
}
