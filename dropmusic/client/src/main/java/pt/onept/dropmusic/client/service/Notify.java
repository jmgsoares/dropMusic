package pt.onept.dropmusic.client.service;

import asg.cliche.CLIException;
import pt.onept.dropmusic.client.shell.AppShell;
import pt.onept.dropmusic.common.client.contract.Notifiable;
import pt.onept.dropmusic.common.server.contract.type.Notification;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Notify extends UnicastRemoteObject implements Notifiable {

	public Notify() throws RemoteException {
		super();
	}

	@Override
	public boolean notify(Notification notification) throws RemoteException {
		try {
			AppShell.shell.processLine("message \" \nGot 1 notification\n" + notification.getMessage() + "\nPress <enter> to continue\"");
			return true;
		} catch (CLIException e) {
			e.printStackTrace();
			return false;
		}
	}
}
