package pt.onept.dropmusic.server;

import pt.onept.dropmusic.common.server.contract.DropmusicServerInterface;
import pt.onept.dropmusic.server.service.DropmusicServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
	public static void main(String[] args) {
		try {
			DropmusicServerInterface dropmusicServer = new DropmusicServer();
			//Naming.rebind("Dropmusic", dropmusicServer);
			Registry test = LocateRegistry.createRegistry(1099);
			test.rebind("Dropmusic", dropmusicServer);
			System.out.println("Server running");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
