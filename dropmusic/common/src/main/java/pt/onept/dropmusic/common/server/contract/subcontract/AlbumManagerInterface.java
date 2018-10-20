package pt.onept.dropmusic.common.server.contract.subcontract;

import pt.onept.dropmusic.common.server.contract.type.Album;

import java.rmi.Remote;
import java.util.List;

/**
 * Manage platform Albums
 * @author João Soares
 * @version 1e-1024
 */
public interface AlbumManagerInterface extends Remote {

    /**
     * insert album
     * @param album album to insert
     * @return the success of the operation
     */
    public boolean insert(Album album);

    /**
     * Remove album
     * @param album Album to remove
     * @return the success of the operation
     */
    public boolean remove(Album album);

    /**
     * Edit Album
     * @param album album with the edited information
     * @return the success of the operation
     */
    public boolean edit(Album album);

    /**
     *
     * @param query query string
     * @return the list of albums that matches the query
     */
    public List<Album> search(String query);

    /**
     *
     * @return the review manager
     */
    public ReviewManagerInterface review(Album album);
}