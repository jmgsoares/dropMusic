package pt.onept.dropmusic.server;

import pt.onept.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.dropmusic.common.server.contract.DropmusicServerInterface;
import pt.onept.dropmusic.common.utililty.PropertiesReaderUtility;
import pt.onept.dropmusic.server.service.DropmusicServer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

public class Server {
	public static String txMultiCastAddress;
	public static String rxMulticastAddress;
	public static int multiCastPort;
	public static int rmiServerPort;
	public static int failOverTime;
	public static DropmusicServerInterface dropmusicServer;


	public static void main(String[] args) {
		Registry registry = null;
		boolean boot = true;



		Properties appProps = PropertiesReaderUtility.read("server.properties");



		txMultiCastAddress = appProps.getProperty("txMultiCastAddress");
		rxMulticastAddress = appProps.getProperty("rxMulticastAddress");
		multiCastPort = Integer.parseInt(appProps.getProperty("multiCastPort"));
		rmiServerPort = Integer.parseInt(appProps.getProperty("rmiServerPort"));
		failOverTime = Integer.parseInt(appProps.getProperty("failOverTime"));


		while (registry == null){
			try {
				registry = LocateRegistry.createRegistry(rmiServerPort);
				if(!boot) System.out.println("Fault detected. Switching...");
			} catch (RemoteException e) {
				registry = null;
				if (boot) {
					System.out.println("RMI server in backup mode");
					boot = false;
				}
				try {
					Thread.sleep(250);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		System.out.println("RMI server in primary mode");

		try {
			MulticastHandler multicastHandler = new MulticastHandler(txMultiCastAddress, rxMulticastAddress, multiCastPort, failOverTime);
			dropmusicServer = new DropmusicServer(multicastHandler);
			registry.rebind("Dropmusic", dropmusicServer);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
