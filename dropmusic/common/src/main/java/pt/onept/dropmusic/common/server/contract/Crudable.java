package pt.onept.dropmusic.common.server.contract;

import pt.onept.dropmusic.common.exception.DuplicatedException;
import pt.onept.dropmusic.common.exception.IncompleteException;
import pt.onept.dropmusic.common.exception.NotFoundException;
import pt.onept.dropmusic.common.exception.UnauthorizedException;
import pt.onept.dropmusic.common.server.contract.type.User;

import java.rmi.RemoteException;

/**
 * CRUDable interface of type T
 *
 * @author João Soares
 * @version 1e-1024
 */
public interface Crudable<T> {


	void create(User self, T object) throws DuplicatedException, UnauthorizedException, RemoteException, IncompleteException;

	T read(User self, T object) throws NotFoundException, UnauthorizedException, RemoteException;


	void update(User self, T object) throws NotFoundException, UnauthorizedException, RemoteException, IncompleteException;


	void delete(User self, T object) throws NotFoundException, UnauthorizedException, RemoteException;
}