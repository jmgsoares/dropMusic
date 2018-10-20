package pt.onept.dropmusic.common.server.contract.subcontract;

import pt.onept.dropmusic.common.server.contract.type.Music;

import java.rmi.Remote;

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
    public boolean insert(Music music);

    /**
     * remove music
     * @param music the music to remove
     * @return the success of the operation
     */
    public boolean remove(Music music);

    /**
     * edit music
     * @param music the edited music
     * @return
     */
    public boolean edit(Music music);
}
