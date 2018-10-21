package pt.onept.dropmusic.common.server.contract;

import pt.onept.dropmusic.common.server.contract.subcontract.AlbumManagerInterface;
import pt.onept.dropmusic.common.server.contract.subcontract.ArtistManagerInterface;
import pt.onept.dropmusic.common.server.contract.subcontract.MusicManagerInterface;
import pt.onept.dropmusic.common.server.contract.subcontract.UserManagerInterface;

import java.rmi.Remote;

/**
 * Manage the DropMusic platform
 * @author João Soares
 * @version 1e-1024
 */

public interface DropmusicInterface extends Remote {
    /**
     * @return the user manager
     */
    public UserManagerInterface user();

    /**
     * @return the album manager
     */
    public AlbumManagerInterface album();

    /**
     * @return the music manager
     */
    public MusicManagerInterface music();

    /**
     * @return the artist manager
     */
    public ArtistManagerInterface artist();
}
