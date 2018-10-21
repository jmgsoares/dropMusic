package pt.onept.dropmusic.common.server.contract.subcontract;

import pt.onept.dropmusic.common.server.contract.type.Artist;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Manage platform Albums
 * @author João Soares
 * @version 1e-1024
 */
public interface ArtistManagerInterface extends Remote {

    /**
     * Add artist
     * @param artist artist to insert
     * @return the success of the operation
     * @throws RemoteException if failed to execute the operation
     */
    public boolean insertArtist(Artist artist) throws RemoteException;

    /**
     * Remove artist
     * @param artist artist to remove
     * @return the success of the operation
     * @throws RemoteException if failed to execute the operation
     */
    public boolean removeArtist(Artist artist) throws RemoteException;

    /**
     * Edit artist
     * @param artist the artist to remove
     * @return the success of the operation
     * @throws RemoteException if failed to execute the operation
     */
    public boolean editArtist(Artist artist) throws RemoteException;
}
