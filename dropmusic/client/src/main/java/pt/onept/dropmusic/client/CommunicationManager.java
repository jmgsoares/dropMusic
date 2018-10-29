package pt.onept.dropmusic.client;

import pt.onept.dropmusic.common.server.contract.DropmusicServerInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CommunicationManager {
	public static DropmusicServerInterface dropmusicServer;
	private static String serverAddress;
	private static int port;
	private static long failOverTime;

	static synchronized void getServerInterface(String serverAddress, int port, long failOverTime) {
		CommunicationManager.serverAddress = serverAddress;
		CommunicationManager.port = port;
		CommunicationManager.failOverTime = failOverTime;
		boolean retry = true;

		long deadLine = System.currentTimeMillis() + Client.failOverTime;
		while (retry & deadLine >= System.currentTimeMillis()) {

			try {
				CommunicationManager.lookupRemoteObject();
				retry = false;
			} catch (RemoteException | NotBoundException e) {
				retry = handleFailOver();
			}
		}
		if (retry) {
			System.out.println("Valid RMI registry hasn't been found @ " + serverAddress + ":" + port);
			System.exit(0);
		}
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
