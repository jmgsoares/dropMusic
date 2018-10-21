package pt.onept.dropmusic.common.server.contract.subcontract;

import pt.onept.dropmusic.common.server.contract.type.Music;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Manage the platform musics
 * @author João Soares
 * @version 1e-1024
 */
public interface MusicManagerInterface extends Remote {

    /**
     * insert music
     * @param music the music to insert
     * @return the success of the operation
     */
    public boolean insert(Music music) throws RemoteException;

    /**
     * remove music
     * @param music the music to remove
     * @return the success of the operation
     */
    public boolean remove(Music music) throws RemoteException;

    /**
     * edit music
     * @param music the edited music
     * @return the success of the operation
     */
    public boolean edit(Music music) throws RemoteException;
}
