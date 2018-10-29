package pt.onept.dropmusic.common.server.contract;

import pt.onept.dropmusic.common.exception.DataServerException;
import pt.onept.dropmusic.common.server.contract.type.User;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

public interface Searchable<T> extends Serializable {

	List<T> search(User self, String query) throws RemoteException, DataServerException;
}
