package pt.onept.dropmusic.common.server.contract.subcontract;

import pt.onept.dropmusic.common.exception.DataServerException;
import pt.onept.dropmusic.common.exception.NotFoundException;
import pt.onept.dropmusic.common.server.contract.type.Music;
import pt.onept.dropmusic.common.server.contract.type.Upload;
import pt.onept.dropmusic.common.server.contract.type.User;

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

	Upload add(User self, Upload file, Music music) throws NotFoundException, RemoteException, DataServerException;

	Upload download(User self, long fileId) throws NotFoundException, RemoteException, DataServerException;
}
