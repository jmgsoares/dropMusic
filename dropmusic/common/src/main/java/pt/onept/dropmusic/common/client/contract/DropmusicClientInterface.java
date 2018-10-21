package pt.onept.dropmusic.common.client.contract;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Manage the DropMusic platform
 * @author João Soares
 * @version 1e-1024
 */
public interface DropmusicClientInterface extends Remote {

    /**
     * Notify client
     * @param msg the notification message
     * @return the sucess of the operation
     * @throws RemoteException if failed to execute the operation
     */
    public boolean notifyClient(String msg) throws RemoteException;
}
