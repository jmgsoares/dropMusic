package pt.onept.sd1819.dropmusic.common.server.contract;

import pt.onept.sd1819.dropmusic.common.exception.DataServerException;
import pt.onept.sd1819.dropmusic.common.server.contract.type.User;

import java.rmi.RemoteException;
import java.util.List;

public interface Listable<T> {

	List<T> list(User self) throws RemoteException, DataServerException;

}
