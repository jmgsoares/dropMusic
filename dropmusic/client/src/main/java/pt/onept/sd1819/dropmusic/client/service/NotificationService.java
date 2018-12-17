package pt.onept.sd1819.dropmusic.client.service;

import asg.cliche.CLIException;
import asg.cliche.Shell;
import pt.onept.sd1819.dropmusic.common.client.contract.Notifiable;
import pt.onept.sd1819.dropmusic.common.server.contract.type.Notification;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Client Notification service in order to process RPC from the RMI Server
 */
public class NotificationService extends UnicastRemoteObject implements Notifiable {

	/**
	 * The shell to write notifications
	 */
	private Shell shell;

	/**
	 *
	 * @throws RemoteException
	 */
	public NotificationService() throws RemoteException {
	}

	public NotificationService setShell(Shell shell) {
		this.shell = shell;
		return this;
	}

	/**
	 * Function to be called remotely to notify the client about an event
	 * @param notification Notification to display
	 * @return the success of the operation
	 */
	@Override
	public boolean notify(Notification notification) {
		try {
			this.shell.processLine("message \" \nGot 1 notification\n" + notification.getMessage() + "\nPress <enter> to continue\"");
			return true;
		} catch (CLIException e) {
			e.printStackTrace();
			return false;
		}
	}
}