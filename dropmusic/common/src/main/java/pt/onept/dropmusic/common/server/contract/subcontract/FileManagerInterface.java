package pt.onept.dropmusic.common.server.contract.subcontract;

import pt.onept.dropmusic.common.exception.NotFoundException;
import pt.onept.dropmusic.common.server.contract.type.FileAddress;
import pt.onept.dropmusic.common.server.contract.type.Music;
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

	FileAddress add(User self, FileAddress file, Music music) throws NotFoundException, RemoteException;

	FileAddress download(User self, long fileId) throws NotFoundException, RemoteException;
}
