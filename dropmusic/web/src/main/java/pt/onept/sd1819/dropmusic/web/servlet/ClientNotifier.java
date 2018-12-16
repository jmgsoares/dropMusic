package pt.onept.sd1819.dropmusic.web.servlet;

import pt.onept.sd1819.dropmusic.common.client.contract.Notifiable;
import pt.onept.sd1819.dropmusic.common.server.contract.type.Notification;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientNotifier extends UnicastRemoteObject implements Notifiable {
	volatile NotificationServlet notificationServlet;

	public ClientNotifier(NotificationServlet notificationServlet) throws RemoteException {
		this.notificationServlet = notificationServlet;
	}

	@Override
	public boolean notify(Notification notification) throws RemoteException {
		return this.notificationServlet.notify(notification);
	}
}
