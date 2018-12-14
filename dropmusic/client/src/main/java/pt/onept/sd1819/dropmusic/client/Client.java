package pt.onept.sd1819.dropmusic.client;

import asg.cliche.ShellFactory;
import pt.onept.sd1819.dropmusic.client.shell.LoginShell;
import pt.onept.sd1819.dropmusic.common.communication.rmi.CommunicationManager;
import pt.onept.sd1819.dropmusic.common.utililty.PropertiesReaderUtility;

import java.io.IOException;
import java.util.Properties;

public class Client {
	public static String registryIpAddress;
	public static int port;
	public static int failOverTime;

	public static void main(String[] args) {

		Properties appProps = PropertiesReaderUtility.read("client.properties");

		registryIpAddress = appProps.getProperty("registryIpAddress");
		port = Integer.parseInt(appProps.getProperty("port"));
		failOverTime = Integer.parseInt(appProps.getProperty("failOverTime"));

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
