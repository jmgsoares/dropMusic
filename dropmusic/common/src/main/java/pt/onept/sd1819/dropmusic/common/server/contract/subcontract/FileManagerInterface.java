package pt.onept.sd1819.dropmusic.common.server.contract.subcontract;

import pt.onept.sd1819.dropmusic.common.exception.DataServerException;
import pt.onept.sd1819.dropmusic.common.exception.DuplicatedException;
import pt.onept.sd1819.dropmusic.common.exception.NotFoundException;
import pt.onept.sd1819.dropmusic.common.exception.UnauthorizedException;
import pt.onept.sd1819.dropmusic.common.server.contract.type.File;
import pt.onept.sd1819.dropmusic.common.server.contract.type.User;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Manage platform Files
 *
 * @author João Soares
 * @version 1e-1024
 */
public interface FileManagerInterface extends Remote, Serializable {

	List<File> listRemoteFiles(User self) throws RemoteException;

	void linkRemoteFile(User self, File file) throws DuplicatedException, NotFoundException, UnauthorizedException, RemoteException, DataServerException;
}
