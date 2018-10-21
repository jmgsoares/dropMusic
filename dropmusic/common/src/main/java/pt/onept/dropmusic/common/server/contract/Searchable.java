package pt.onept.dropmusic.common.server.contract;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Manage platform Albums
 * @author João Soares
 * @version 1e-1024
 */
public interface Searchable<T> {
    List<T> search(String query) throws RemoteException;
}
