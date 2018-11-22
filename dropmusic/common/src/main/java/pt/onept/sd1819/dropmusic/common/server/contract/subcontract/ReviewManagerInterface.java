package pt.onept.sd1819.dropmusic.common.server.contract.subcontract;

import pt.onept.sd1819.dropmusic.common.exception.DataServerException;
import pt.onept.sd1819.dropmusic.common.exception.IncompleteException;
import pt.onept.sd1819.dropmusic.common.server.contract.type.Review;
import pt.onept.sd1819.dropmusic.common.server.contract.type.User;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Manage the Album reviews
 *
 * @author João Soares
 * @version 1e-1024
 */
public interface ReviewManagerInterface extends Remote, Serializable {

	void add(User self, Review review) throws RemoteException, IncompleteException, DataServerException;
}
