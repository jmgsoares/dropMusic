package pt.onept.sd1819.dropmusic.common.client.contract;

import pt.onept.sd1819.dropmusic.common.server.contract.type.DropmusicDataType;
import pt.onept.sd1819.dropmusic.common.server.contract.type.Notification;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Notifiable remote interface
 *
 * @author João Soares
 * @version 1e-1024
 */
public interface Updatable extends Remote, Serializable {

	boolean update(DropmusicDataType object) throws RemoteException;

}
