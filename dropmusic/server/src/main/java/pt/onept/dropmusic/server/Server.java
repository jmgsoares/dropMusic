package pt.onept.dropmusic.server;

import pt.onept.dropmusic.common.server.contract.DropmusicServerInterface;
import pt.onept.dropmusic.server.service.DropmusicServer;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class Server {
	public static void main(String[] args) {
		try {
			DropmusicServerInterface dropmusicServer = new DropmusicServer();
			Naming.rebind("Dropmusic", dropmusicServer);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}


	}
}
