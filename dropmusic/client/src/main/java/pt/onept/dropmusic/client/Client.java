package pt.onept.dropmusic.client;


import asg.cliche.ShellFactory;
import pt.onept.dropmusic.client.shell.LoginShell;

import java.io.IOException;


public class Client {
	public static void main(String[] args) {
		String registryIpAddress = "192.168.1.40";
		int port = 1099;
		int failOverTime = 30000;
		System.out.println("Dropmusic client starting\nTrying to locate the remote object");


		try {
			CommunicationManager.getServerInterface(registryIpAddress, port, failOverTime);
			LoginShell loginShell = new LoginShell();
			ShellFactory.createConsoleShell("Dropmusic", "", loginShell).commandLoop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
