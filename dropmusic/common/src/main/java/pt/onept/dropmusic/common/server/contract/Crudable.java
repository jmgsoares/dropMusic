package pt.onept.dropmusic.common.server.contract;

import pt.onept.dropmusic.common.exception.DuplicatedException;
import pt.onept.dropmusic.common.exception.NotFoundException;
import pt.onept.dropmusic.common.exception.UnauthorizedException;
import java.rmi.RemoteException;

/**
 * CRUDable interface of type T
 * @author João Soares
 * @version 1e-1024
 */
public interface Crudable<T> {

	/**
	 * Create object T
	 * @param object object to be created
	 * @throws DuplicatedException if the object already exists
	 * @throws UnauthorizedException if there is no Authorization to perform the operation
	 * @throws RemoteException if operation failed to execute
	 */
	void create(T object) throws DuplicatedException, UnauthorizedException, RemoteException;

	/**
	 * Update object T
	 * @param object object to be updated
	 * @throws NotFoundException if the object doesn't exists
	 * @throws UnauthorizedException if there is no Authorization to perform the operation
	 * @throws RemoteException if operation failed to execute
	 */
	void update(T object) throws NotFoundException, UnauthorizedException, RemoteException;

	/**
	 * Delete object T
	 * @param object object to be deleted
	 * @throws NotFoundException if the object doesn't exists
	 * @throws UnauthorizedException if there is no Authorization to perform the operation
	 * @throws RemoteException if operation failed to execute
	 */
	void delete(T object) throws NotFoundException, UnauthorizedException, RemoteException;
}