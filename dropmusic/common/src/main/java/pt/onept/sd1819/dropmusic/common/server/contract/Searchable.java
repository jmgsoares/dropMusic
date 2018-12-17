package pt.onept.sd1819.dropmusic.common.server.contract;

import pt.onept.sd1819.dropmusic.common.exception.DataServerException;
import pt.onept.sd1819.dropmusic.common.server.contract.type.User;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

/**
 * SEARCHable interface of type T
 *
 * @author João Soares
 * @version 1e-1024
 */

public interface Searchable<T> extends Serializable {

	List<T> search(User self, String query) throws RemoteException, DataServerException;
}
