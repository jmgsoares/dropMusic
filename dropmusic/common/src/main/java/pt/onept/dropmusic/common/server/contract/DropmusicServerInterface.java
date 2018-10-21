package pt.onept.dropmusic.common.server.contract;

import pt.onept.dropmusic.common.server.contract.subcontract.AlbumManagerInterface;
import pt.onept.dropmusic.common.server.contract.subcontract.ArtistManagerInterface;
import pt.onept.dropmusic.common.server.contract.subcontract.MusicManagerInterface;
import pt.onept.dropmusic.common.server.contract.subcontract.UserManagerInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Manage the DropMusic platform
 * @author João Soares
 * @version 1e-1024
 */

public interface DropmusicServerInterface extends Remote {
    /**
     * @return the user manager
     * @throws RemoteException if failed to execute the operation
     */
    UserManagerInterface user() throws RemoteException;

    /**
     * @return the album manager
     * @throws RemoteException if failed to execute the operation
     */
    AlbumManagerInterface album() throws RemoteException;

    /**
     * @return the music manager
     * @throws RemoteException if failed to execute the operation
     */
    MusicManagerInterface music() throws RemoteException;

    /**
     * @return the artist manager
     * @throws RemoteException if failed to execute the operation
     */
    ArtistManagerInterface artist() throws RemoteException;
}
