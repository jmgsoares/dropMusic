package pt.onept.sd1819.dropmusic.web.communication;

import pt.onept.sd1819.dropmusic.common.server.contract.DropmusicServerInterface;

import java.rmi.RemoteException;

public class CommunicationManager {
	private static String registryIpAddress = "localhost";
	private static int port=1099;
	private static long failOverTime=30000;

	private CommunicationManager() { }

	public static synchronized DropmusicServerInterface getServerInterface() throws RemoteException {
		return pt.onept.sd1819.dropmusic.common.communication.rmi.CommunicationManager.getServerInterface(
				CommunicationManager.registryIpAddress,
				CommunicationManager.port,
				CommunicationManager.failOverTime
		);
	}
}
