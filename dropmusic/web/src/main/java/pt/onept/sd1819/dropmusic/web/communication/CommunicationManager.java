package pt.onept.sd1819.dropmusic.web.communication;

import pt.onept.sd1819.dropmusic.common.server.contract.DropmusicServerInterface;

import java.rmi.RemoteException;

/**
 * Class to serve as a decorator for the RMI Communication manager
 * This File has all the parameters needed to connect to the RMI Server
 * @see pt.onept.sd1819.dropmusic.common.communication.rmi.CommunicationManager
 */
public class CommunicationManager {
	private static String registryIpAddress = "localhost";
	private static int port=1099;
	private static long failOverTime=30000;

	private CommunicationManager() { }

	/**
	 * Gets the parameterized ServerInterface
	 * @return Server Interface
	 * @throws RemoteException on RMI error
	 */
	public static synchronized DropmusicServerInterface getServerInterface() throws RemoteException {
		return pt.onept.sd1819.dropmusic.common.communication.rmi.CommunicationManager.getServerInterface(
				CommunicationManager.registryIpAddress,
				CommunicationManager.port,
				CommunicationManager.failOverTime
		);
	}
}
