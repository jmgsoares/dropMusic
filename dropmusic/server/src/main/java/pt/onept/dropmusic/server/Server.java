package pt.onept.dropmusic.server;

import pt.onept.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.dropmusic.common.server.contract.DropmusicServerInterface;
import pt.onept.dropmusic.server.service.DropmusicServer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
	public static void main(String[] args) {
		try {
			String txMultiCastAddress = "224.3.2.1";
			String rxMulticastAddress = "224.3.2.2";
			int multiCastPort = 4321;

			MulticastHandler multicastHandler = new MulticastHandler(txMultiCastAddress, rxMulticastAddress, multiCastPort);

			DropmusicServerInterface dropmusicServer = new DropmusicServer(multicastHandler);
			//Naming.rebind("Dropmusic", dropmusicServer);
			Registry test = LocateRegistry.createRegistry(1099);
			test.rebind("Dropmusic", dropmusicServer);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
