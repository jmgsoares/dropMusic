package pt.onept.dropmusic.common.server.contract.subcontract;

import pt.onept.dropmusic.common.exception.DataServerException;
import pt.onept.dropmusic.common.server.contract.Crudable;
import pt.onept.dropmusic.common.server.contract.type.Notification;
import pt.onept.dropmusic.common.server.contract.type.User;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface NotificationManagerInterface extends Remote, Serializable, Crudable<Notification> {

	List<Notification> get(User self) throws RemoteException, DataServerException;

	void notifyUser(User userToNotify, Notification notification) throws RemoteException, DataServerException;

}
