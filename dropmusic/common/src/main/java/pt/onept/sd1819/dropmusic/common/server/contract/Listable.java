package pt.onept.sd1819.dropmusic.common.server.contract;

import pt.onept.sd1819.dropmusic.common.exception.DataServerException;
import pt.onept.sd1819.dropmusic.common.exception.NotFoundException;
import pt.onept.sd1819.dropmusic.common.exception.UnauthorizedException;
import pt.onept.sd1819.dropmusic.common.server.contract.type.User;

import java.rmi.RemoteException;

public interface Listable<T> {

	T list(User self, T object) throws NotFoundException, UnauthorizedException, RemoteException, DataServerException;

}
