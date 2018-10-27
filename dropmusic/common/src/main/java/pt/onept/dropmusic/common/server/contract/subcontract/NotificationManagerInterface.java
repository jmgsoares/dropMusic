package pt.onept.dropmusic.common.server.contract.subcontract;

import pt.onept.dropmusic.common.server.contract.type.Notification;
import pt.onept.dropmusic.common.server.contract.type.User;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface NotificationManagerInterface extends Remote, Serializable {

	List<Notification> get(User self) throws RemoteException;

	void notifyUser(User userToNotify, Notification notification) throws RemoteException;

	void delete(User self, Long lastSeenNotificationId) throws RemoteException;

}
