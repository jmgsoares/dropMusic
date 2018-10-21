package pt.onept.dropmusic.common.server.contract.subcontract;

import pt.onept.dropmusic.common.server.contract.type.Album;
import pt.onept.dropmusic.common.server.contract.type.Review;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Manage the Album reviews
 * @author João Soares
 * @version 1e-1024
 */
public interface ReviewManagerInterface extends Remote {

    /**
     * insert review
     * @param review the review to insert
     * @return the success of the operation
     */
    public boolean insert(Review review) throws RemoteException;

    /**
     *
     * @param album the album to get reviews from
     * @return the list of reviews
     */
    public List<Review> get(Album album) throws RemoteException;
}
