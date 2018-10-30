package pt.onept.dropmusic.common.server.contract.subcontract;

import pt.onept.dropmusic.common.exception.DataServerException;
import pt.onept.dropmusic.common.exception.UnauthorizedException;
import pt.onept.dropmusic.common.server.contract.Crudable;
import pt.onept.dropmusic.common.server.contract.type.User;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Manage platform users
 *
 * @author João Soares
 * @version 1e-1024
 */
public interface UserManagerInterface extends Remote, Crudable<User>, Serializable {

	User login(User user) throws RemoteException, UnauthorizedException, DataServerException;

}