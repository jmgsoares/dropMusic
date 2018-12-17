package pt.onept.sd1819.dropmusic.web.servlet;

import pt.onept.sd1819.dropmusic.common.client.contract.Notifiable;
import pt.onept.sd1819.dropmusic.common.server.contract.type.Notification;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Class to implement the Notifiable Interface in order to be able to subscribe to the RMI Notifications
 */
public class ClientNotifier extends UnicastRemoteObject implements Notifiable {
	volatile NotificationServlet notificationServlet;

	/**
	 *
	 * @param notificationServlet the servlet that will be used to notify the client
	 * @throws RemoteException on RMI error
	 */
	public ClientNotifier(NotificationServlet notificationServlet) throws RemoteException {
		this.notificationServlet = notificationServlet;
	}

	/**
	 * Function executed upon a new notification
	 * @param notification the notification to display to the user
	 * @return the operation success
	 * @throws RemoteException on RMI error
	 */
	@Override
	public boolean notify(Notification notification) throws RemoteException {
		return this.notificationServlet.notify(notification);
	}
}
