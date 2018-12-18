package pt.onept.sd1819.dropmusic.web.servlet;

import pt.onept.sd1819.dropmusic.common.client.contract.Updatable;
import pt.onept.sd1819.dropmusic.common.server.contract.type.DropmusicDataType;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Class to implement the Updatable Interface in order to be able to subscribe to the RMI Live Updates
 */
public class ClientUpdater extends UnicastRemoteObject implements Updatable {
	volatile LiveUpdateServlet updateServlet;

	/**
	 *
	 * @param updateServlet the servlet that will be used to update the client
	 * @throws RemoteException on RMI error
	 */
	public ClientUpdater(LiveUpdateServlet updateServlet) throws RemoteException {
		this.updateServlet = updateServlet;
	}

	/**
	 * Function executed upon a update
	 * @param object the notification to be updated on the user side
	 * @return the operation success
	 * @throws RemoteException on RMI error
	 */
	@Override
	public boolean update(DropmusicDataType object) throws RemoteException {
		return this.updateServlet.update(object);
	}
}
