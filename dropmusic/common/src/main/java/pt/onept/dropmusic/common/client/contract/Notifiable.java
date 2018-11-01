package pt.onept.dropmusic.common.client.contract;

import pt.onept.dropmusic.common.server.contract.type.Notification;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Notifiable remote interface
 *
 * @author João Soares
 * @version 1e-1024
 */
public interface Notifiable extends Remote, Serializable {

	//TODO Implement function to check if logged client is still the same

	boolean notify(Notification notification) throws RemoteException;

}
