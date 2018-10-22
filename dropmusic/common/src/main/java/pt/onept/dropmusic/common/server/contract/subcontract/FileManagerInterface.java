package pt.onept.dropmusic.common.server.contract.subcontract;

import pt.onept.dropmusic.common.exception.NotFoundException;
import pt.onept.dropmusic.common.server.contract.Crudable;
import pt.onept.dropmusic.common.server.contract.type.File;
import pt.onept.dropmusic.common.server.contract.type.Music;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Manage platform Files
 * @author João Soares
 * @version 1e-1024
 */
public interface FileManagerInterface extends Remote, Serializable {

	/**
	 * Get the server address where to upload the file
	 * @return string with the ip address of the server
	 * @throws RemoteException if the operation failed to execute
	 */
	String uploadServer() throws RemoteException;

	/**
	 * Tells the server where the file is, and the music it is associated with
	 * @param file the file that was uploaded to the server
	 * @param music to associate the uploaded file with
	 * @throws RemoteException if the operation failed to execute
	 * @throws NotFoundException if the music or file aren't found on the server
	 */
	void addFile(File file, Music music) throws NotFoundException, RemoteException;

	/**
	 * gets the server where the file is stored
	 * @param file the file to search for
	 * @return string with the ip address of the server that has the file
	 * @throws NotFoundException if the file isn't found
	 * @throws RemoteException if the operation failed to execute
	 */
	String downloadServer(File file) throws NotFoundException, RemoteException;
}
