package pt.onept.sd1819.dropmusic.client.service;

import asg.cliche.CLIException;
import asg.cliche.Shell;
import pt.onept.sd1819.dropmusic.common.client.contract.Notifiable;
import pt.onept.sd1819.dropmusic.common.server.contract.type.Notification;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class NotificationService extends UnicastRemoteObject implements Notifiable {

	private Shell shell;

	public NotificationService() throws RemoteException {
	}

	public NotificationService setShell(Shell shell) {
		this.shell = shell;
		return this;
	}

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