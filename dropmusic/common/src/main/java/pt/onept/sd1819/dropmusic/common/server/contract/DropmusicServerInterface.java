package pt.onept.sd1819.dropmusic.common.server.contract;

import pt.onept.sd1819.dropmusic.common.client.contract.Notifiable;
import pt.onept.sd1819.dropmusic.common.exception.DataServerException;
import pt.onept.sd1819.dropmusic.common.server.contract.subcontract.*;

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

}
