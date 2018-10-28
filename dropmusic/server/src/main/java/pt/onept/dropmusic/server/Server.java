package pt.onept.dropmusic.server;

import pt.onept.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.dropmusic.common.server.contract.DropmusicServerInterface;
import pt.onept.dropmusic.server.service.DropmusicServer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
	public static void main(String[] args) {
		String txMultiCastAddress = "224.3.2.1";
		String rxMulticastAddress = "224.3.2.2";
		int multiCastPort = 4321;
		int rmiServerPort = 1099;
		Registry registry = null;
		boolean boot = true;

		while (registry == null){
			try {
				registry = LocateRegistry.createRegistry(rmiServerPort);
			} catch (RemoteException e) {
				registry = null;
				if (boot) {
					System.out.println("Server in backup mode");
					boot = false;
				}
				try {
					Thread.sleep(250);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		System.out.println("Server in primary mode");

		try {
			MulticastHandler multicastHandler = new MulticastHandler(txMultiCastAddress, rxMulticastAddress, multiCastPort);
			DropmusicServerInterface dropmusicServer = null;
			dropmusicServer = new DropmusicServer(multicastHandler);
			registry.rebind("Dropmusic", dropmusicServer);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
