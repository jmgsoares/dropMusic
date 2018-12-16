package pt.onept.sd1819.dropmusic.common.server.contract.subcontract;

import pt.onept.sd1819.dropmusic.common.client.contract.Notifiable;
import pt.onept.sd1819.dropmusic.common.exception.DataServerException;
import pt.onept.sd1819.dropmusic.common.server.contract.Crudable;
import pt.onept.sd1819.dropmusic.common.server.contract.type.Notification;
import pt.onept.sd1819.dropmusic.common.server.contract.type.User;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;


public interface NotificationManagerInterface extends Remote, Serializable, Crudable<Notification> {

	//TODO replace by the crudable
	//TODO extend the searchable interface needed?
	List<Notification> get(User self) throws RemoteException, DataServerException;

	void notifyUser(User userToNotify, Notification notification) throws RemoteException, DataServerException;

	UUID subscribe(int id, Notifiable client) throws RemoteException;

	void unSubscribe(int id, UUID subscriptionId) throws RemoteException;
}
