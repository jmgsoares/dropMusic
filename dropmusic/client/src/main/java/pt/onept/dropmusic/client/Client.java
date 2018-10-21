package pt.onept.dropmusic.client;


import asg.cliche.ShellFactory;
import pt.onept.dropmusic.common.server.contract.DropmusicServerInterface;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {
	public static void main(String[] args) {
		try {
			DropmusicServerInterface dropmusicServer = (DropmusicServerInterface) Naming.lookup("Dropmusic");
			ShellHandler shellHandler = new ShellHandler(dropmusicServer);
			ShellFactory.createConsoleShell("Dropmusic", "", shellHandler).commandLoop();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
