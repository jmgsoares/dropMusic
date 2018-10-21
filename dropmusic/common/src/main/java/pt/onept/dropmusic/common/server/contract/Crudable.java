package pt.onept.dropmusic.common.server.contract;

import pt.onept.dropmusic.common.exception.DuplicatedException;
import pt.onept.dropmusic.common.exception.NotFoundException;
import pt.onept.dropmusic.common.exception.UnauthorizedException;
import java.rmi.RemoteException;

/**
 * Manage platform Albums
 * @author João Soares
 * @version 1e-1024
 */
public interface Crudable<T> {
    void create(T object) throws DuplicatedException, UnauthorizedException, RemoteException;
    void update(T object) throws NotFoundException, UnauthorizedException, RemoteException;
    void delete(T object) throws NotFoundException, UnauthorizedException, RemoteException;
}