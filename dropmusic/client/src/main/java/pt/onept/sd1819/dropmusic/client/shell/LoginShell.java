package pt.onept.sd1819.dropmusic.client.shell;

import asg.cliche.*;
import pt.onept.sd1819.dropmusic.client.Client;
import pt.onept.sd1819.dropmusic.common.communication.rmi.CommunicationManager;
import pt.onept.sd1819.dropmusic.common.exception.DataServerException;
import pt.onept.sd1819.dropmusic.common.exception.DuplicatedException;
import pt.onept.sd1819.dropmusic.common.exception.IncompleteException;
import pt.onept.sd1819.dropmusic.common.exception.UnauthorizedException;
import pt.onept.sd1819.dropmusic.common.server.contract.type.User;

import java.io.IOException;
import java.rmi.RemoteException;

public class LoginShell implements ShellDependent, ShellManageable {
	private Shell shell;

	public LoginShell() {

	}

	@Command
	public String message(String message) {
		return message;
	}

	@Command(description = "Register a user. Usage: register <user> <password>")
	public String register(String name, String password) {
		User user = new User()
				.setUsername(name)
				.setPassword(password);
		String output = null;
		boolean retry = true;

		long deadLine = System.currentTimeMillis() + Client.failOverTime;

		while (retry & deadLine >= System.currentTimeMillis()) {
			try {
				CommunicationManager.getServerInterface().user().create(user, user);
				retry = false;
				output = "User " + user.getUsername() + " created successfully";
			} catch (DuplicatedException e) {
				retry = false;
				output = "User " + user.getUsername() + " already exists";
			} catch (UnauthorizedException e) {
				retry = false;
				output = "Unauthorized!";
			} catch (RemoteException e) {
				CommunicationManager.handleFailOver();
				output = "RMI SERVER FAIL";
			} catch (IncompleteException e) {
				retry = false;
				output = "Incomplete request";
			} catch (DataServerException e) {
				output = "DATA SERVER FAIL";
			}
		}
		return output;
	}

	@Command(description = "Login. Usage: login <user> <password>")
	public String login(String name, String password) {
		User user = new User()
				.setUsername(name)
				.setPassword(password);
		String output = null;
		boolean retry = true;

		long deadLine = System.currentTimeMillis() + Client.failOverTime;

		while (retry & deadLine >= System.currentTimeMillis()) {

			try {
				user = CommunicationManager.getServerInterface().user().login(user);
				retry = false;
				createAppShell(user);
				output = "";
			} catch (RemoteException e) {
				CommunicationManager.handleFailOver();
				output = "RMI SERVER FAIL";
			} catch (UnauthorizedException e) {
				output = "Wrong user name or password";
				retry = false;
			} catch (DataServerException e) {
				output = "DATA SERVER FAIL";
			}
		}
		return output;
	}

	private void createAppShell(User user) {
		AppShell appShell = new AppShell(user);

		try {
			ShellFactory.createSubshell(user.getUsername(), this.shell, "", appShell)
					.commandLoop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void cliSetShell(Shell theShell) {
		this.shell = theShell;
	}

	@Override
	public void cliEnterLoop() {
		try {
			this.shell.processLine("message \"Welcome to Dropmusic v1e-1024\"");
		} catch (CLIException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void cliLeaveLoop() {

	}
}
