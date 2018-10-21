package pt.onept.dropmusic.common.server.contract.subcontract;

import pt.onept.dropmusic.common.exception.DuplicatedException;
import pt.onept.dropmusic.common.exception.IncompleteException;
import pt.onept.dropmusic.common.exception.UnauthorizedException;
import pt.onept.dropmusic.common.server.contract.type.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Manage platform users
 * @author João Soares
 * @version 1e-1024
 */
public interface UserManagerInterface extends Remote {

    /**
     * user login
     * @param user to login with
     * @throws RemoteException
     * @throws UnauthorizedException
     */
    public void login(User user) throws RemoteException, UnauthorizedException;

    /**
     * register User
     * @param user to register
     * @throws RemoteException
     * @throws DuplicatedException
     * @throws IncompleteException
     */
    public void register(User user) throws RemoteException, DuplicatedException, IncompleteException;
}