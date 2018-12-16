package pt.onept.sd1819.dropmusic.common.server.contract.subcontract;

import pt.onept.sd1819.dropmusic.common.exception.DataServerException;
import pt.onept.sd1819.dropmusic.common.server.contract.Crudable;
import pt.onept.sd1819.dropmusic.common.server.contract.Listable;
import pt.onept.sd1819.dropmusic.common.server.contract.type.Artist;
import pt.onept.sd1819.dropmusic.common.server.contract.type.User;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Manage platform Albums
 *
 * @author João Soares
 * @version 1e-1024
 */
public interface ArtistManagerInterface extends Remote, Crudable<Artist>, Listable<Artist>, Serializable {

	void clean(User self) throws RemoteException, DataServerException;
}