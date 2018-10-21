package pt.onept.dropmusic.common.server.contract.subcontract;

import pt.onept.dropmusic.common.server.contract.Crudable;
import pt.onept.dropmusic.common.server.contract.type.Album;
import pt.onept.dropmusic.common.server.contract.type.Review;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Manage the Album reviews
 * @author João Soares
 * @version 1e-1024
 */
public interface ReviewManagerInterface extends Remote, Crudable<Review>, Serializable {

    /**
     * @param album the album to get reviews from
     * @return the list of reviews
     * @throws RemoteException if failed to execute the operation
     */
    List<Review> get(Album album) throws RemoteException;
}
