package pt.onept.dropmusic.common.client.contract;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Notifiable remote interface
 * @author João Soares
 * @version 1e-1024
 */
public interface Notifiable extends Remote {

    /**
     * Notify client on real-time (Callback)
     * @param msg the notification message
     * @return the sucess of the operation
     * @throws RemoteException if failed to execute the operation
     */
    boolean notify(String msg) throws RemoteException;
}
