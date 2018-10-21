package pt.onept.dropmusic.common.server.contract;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Searchable interface of type  T
 * @author João Soares
 * @version 1e-1024
 */
public interface Searchable<T> {

	/**
	 * Search
	 * @param query string to search for
	 * @return List of objects of type T
	 * @throws RemoteException if failed to execute the operation
	 */
	List<T> search(String query) throws RemoteException;
}
