package pt.onept.sd1819.dropmusic.common.communication.rmi;

import pt.onept.sd1819.dropmusic.common.server.contract.DropmusicServerInterface;

import java.awt.color.CMMException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Class that handles all RMI communication from client to server
 */
public final class CommunicationManager {
	private static DropmusicServerInterface dropmusicServer = null;
	private static String serverAddress;
	private static int port;
	private static long failOverTime;

	public static synchronized DropmusicServerInterface getServerInterface(String serverAddress, int port, long failOverTime)
	throws RemoteException {
		CommunicationManager.serverAddress = serverAddress;
		CommunicationManager.port = port;
		CommunicationManager.failOverTime = failOverTime;
		boolean retry = true;

		long deadLine = System.currentTimeMillis() + failOverTime;
		while (retry & deadLine >= System.currentTimeMillis()) {

			try {
				CommunicationManager.lookupRemoteObject();
				retry = false;
			} catch (RemoteException | NotBoundException e) {
				retry = handleFailOver();
			}
		}
		if (retry) {
			throw new RemoteException();
		} else {
			return CommunicationManager.dropmusicServer;
		}
	}

	public static synchronized DropmusicServerInterface getServerInterface() throws RemoteException {
		return getServerInterface(
				CommunicationManager.serverAddress,
				CommunicationManager.port,
				CommunicationManager.failOverTime
		);
	}

	private static synchronized void lookupRemoteObject() throws RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry(serverAddress, port);
		CommunicationManager.dropmusicServer = (DropmusicServerInterface) registry.lookup("Dropmusic");
	}

	public static boolean handleFailOver() {
		try {
			CommunicationManager.lookupRemoteObject();
			return false;
		} catch (RemoteException | NotBoundException e) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		return true;
	}
}
