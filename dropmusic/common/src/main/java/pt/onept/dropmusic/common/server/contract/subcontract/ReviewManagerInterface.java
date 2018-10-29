package pt.onept.dropmusic.common.server.contract.subcontract;

import pt.onept.dropmusic.common.exception.DataServerException;
import pt.onept.dropmusic.common.exception.IncompleteException;
import pt.onept.dropmusic.common.server.contract.Crudable;
import pt.onept.dropmusic.common.server.contract.type.Album;
import pt.onept.dropmusic.common.server.contract.type.Review;
import pt.onept.dropmusic.common.server.contract.type.User;

import javax.sound.sampled.ReverbType;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Manage the Album reviews
 *
 * @author João Soares
 * @version 1e-1024
 */
public interface ReviewManagerInterface extends Remote, Serializable {

	void add(User self, Review review) throws RemoteException, IncompleteException, DataServerException;
}
