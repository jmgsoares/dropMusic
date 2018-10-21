package pt.onept.dropmusic.common.server.contract.subcontract;


import pt.onept.dropmusic.common.exception.UnauthorizedException;
import pt.onept.dropmusic.common.server.contract.Crudable;
import pt.onept.dropmusic.common.server.contract.type.Notification;
import pt.onept.dropmusic.common.server.contract.type.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Manage platform users
 * @author João Soares
 * @version 1e-1024
 */
public interface UserManagerInterface extends Remote, Crudable<User> {

    /**
     * User login
     * @param user to login with
     * @throws RemoteException if the operation failed to execute
     * @throws UnauthorizedException if the login isn't successful
     */
    void login(User user) throws RemoteException, UnauthorizedException;

	/**
	 * Get the user stored notifications on the server
	 * @param user the user to get the stored notifications
	 * @return the list of unread notifications for the user
	 * @throws RemoteException if the operation failed to execute
	 */
	List<Notification> getNotifications(User user) throws RemoteException;
}