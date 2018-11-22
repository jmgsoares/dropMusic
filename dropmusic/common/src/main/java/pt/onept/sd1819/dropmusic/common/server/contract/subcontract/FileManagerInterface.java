package pt.onept.sd1819.dropmusic.common.server.contract.subcontract;

import pt.onept.sd1819.dropmusic.common.exception.DataServerException;
import pt.onept.sd1819.dropmusic.common.exception.NotFoundException;
import pt.onept.sd1819.dropmusic.common.server.contract.type.File;
import pt.onept.sd1819.dropmusic.common.server.contract.type.Music;
import pt.onept.sd1819.dropmusic.common.server.contract.type.User;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Manage platform Files
 *
 * @author João Soares
 * @version 1e-1024
 */
public interface FileManagerInterface extends Remote, Serializable {

	//TODO replace the add with the crudable implementation?
	File add(User self, File file, Music music) throws NotFoundException, RemoteException, DataServerException;

	File download(User self, long fileId) throws NotFoundException, RemoteException, DataServerException;
}
