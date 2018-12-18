package pt.onept.sd1819.dropmusic.common.server.contract.subcontract;

import pt.onept.sd1819.dropmusic.common.client.contract.Notifiable;
import pt.onept.sd1819.dropmusic.common.client.contract.Updatable;
import pt.onept.sd1819.dropmusic.common.exception.DataServerException;
import pt.onept.sd1819.dropmusic.common.server.contract.Crudable;
import pt.onept.sd1819.dropmusic.common.server.contract.type.DropmusicDataType;
import pt.onept.sd1819.dropmusic.common.server.contract.type.Notification;
import pt.onept.sd1819.dropmusic.common.server.contract.type.User;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;


/**
 * Manage the platform notifications
 *
 * @author João Soares
 * @version 1e-1024
 */
public interface UpdateManagerInterface extends Remote, Serializable {

	<T extends DropmusicDataType> void update(T object) throws RemoteException;

	<T extends DropmusicDataType> UUID subscribe(T object, Updatable client) throws RemoteException;

	<T extends DropmusicDataType> void unSubscribe(T object, UUID subscriptionId) throws RemoteException;
}
