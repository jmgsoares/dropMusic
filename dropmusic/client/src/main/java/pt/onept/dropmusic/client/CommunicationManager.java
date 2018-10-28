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
			while (retry) {
				retry = false;
				try {
					CommunicationManager.lookupRemoteObject();
				} catch (RemoteException | NotBoundException e) {
					retry = handleFailOver();
					if(!retry) {
						System.out.println("Valid RMI registry hasn't been found @ " + serverAddress + ":" + port);
						System.exit(0);
					}
				}
			}
	}

	private static synchronized void lookupRemoteObject() throws RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry(serverAddress, port);
		CommunicationManager.dropmusicServer = (DropmusicServerInterface) registry.lookup("Dropmusic");
	}

	public static boolean handleFailOver()  {
		long deadLine = System.currentTimeMillis() + CommunicationManager.failOverTime;
		while (deadLine > System.currentTimeMillis()) {
			try {
				CommunicationManager.lookupRemoteObject();
				return true;
			} catch (RemoteException | NotBoundException e) { }
		}
		return false;
	}

}
