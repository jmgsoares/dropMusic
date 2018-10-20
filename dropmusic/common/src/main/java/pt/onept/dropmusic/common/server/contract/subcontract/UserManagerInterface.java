package pt.onept.dropmusic.common.server.contract.subcontract;

import pt.onept.dropmusic.common.server.contract.type.User;

import java.rmi.Remote;

/**
 * Manage platform users
 * @author João Soares
 * @version 1e-1024
 */
public interface UserManagerInterface extends Remote {

    /**
     * user login
     * @param user user to login with
     * @return the success of the operation
     */
    public boolean login(User user);

    /**
     * register User
     * @param user the user to register
     * @return the success of the operation
     */
    public boolean register(User user);
}