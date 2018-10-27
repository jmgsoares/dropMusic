package pt.onept.dropmusic.common.server.contract;

import pt.onept.dropmusic.common.server.contract.subcontract.*;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Manage the DropMusic platform
 *
 * @author João Soares
 * @version 1e-1024
 */

public interface DropmusicServerInterface extends Remote, Serializable {

	UserManagerInterface user() throws RemoteException;

	AlbumManagerInterface album() throws RemoteException;

	MusicManagerInterface music() throws RemoteException;

	ArtistManagerInterface artist() throws RemoteException;

	NotificationManagerInterface notification() throws RemoteException;

	ReviewManagerInterface review() throws RemoteException;
}
