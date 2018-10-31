package pt.onept.dropmusic.common.server.contract;

import pt.onept.dropmusic.common.client.contract.Notifiable;
import pt.onept.dropmusic.common.exception.DataServerException;
import pt.onept.dropmusic.common.server.contract.subcontract.*;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

/**
 * Manage the DropMusic platform
 *
 * @author João Soares
 * @version 1e-1024
 */

public interface DropmusicServerInterface extends Remote, Serializable {

	UserManagerInterface user() throws RemoteException, DataServerException;

	AlbumManagerInterface album() throws RemoteException, DataServerException;

	MusicManagerInterface music() throws RemoteException, DataServerException;

	ArtistManagerInterface artist() throws RemoteException, DataServerException;

	NotificationManagerInterface notification() throws RemoteException, DataServerException;

	ReviewManagerInterface review() throws RemoteException, DataServerException;

	Map<Long, Notifiable> client() throws RemoteException;

	void subscribe(long id, Notifiable client) throws RemoteException;

	void unSubscribe(long id) throws RemoteException;

}
