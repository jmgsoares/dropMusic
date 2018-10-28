package pt.onept.dropmusic.client.shell;

import asg.cliche.*;
import pt.onept.dropmusic.client.CommunicationManager;
import pt.onept.dropmusic.common.exception.DuplicatedException;
import pt.onept.dropmusic.common.exception.IncompleteException;
import pt.onept.dropmusic.common.exception.UnauthorizedException;
import pt.onept.dropmusic.common.server.contract.DropmusicServerInterface;
import pt.onept.dropmusic.common.server.contract.type.User;

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

		while (retry) {
			retry = false;
			try {
				CommunicationManager.dropmusicServer.user().create(user, user);
				output = "User " + user.getUsername() + " created successfully";
			} catch (DuplicatedException e) {
				output = "User " + user.getUsername() + " already exists";
			} catch (UnauthorizedException e) {
				output = "Unauthorized!";
			} catch (RemoteException e) {
				retry = CommunicationManager.handleFailOver();
				if (!retry) output = "RMI SERVER FAIL";
			} catch (IncompleteException e) {
				output = "Incomplete request";
			}
		}
		return output;
	}

	@Command(description = "Login. Usage: login <user> <password>")
	public String login(String name, String password) {
		User user = new User()
				.setUsername(name)
				.setPassword(password);
		String output =null;
		boolean retry = true;

		while (retry) {
			retry = false;
			try {
				user = CommunicationManager.dropmusicServer.user().login(user);
				createAppShell(user);
				output = "";
			} catch (RemoteException e) {
				retry = CommunicationManager.handleFailOver();
				if (!retry) output = "RMI SERVER FAIL";
			} catch (UnauthorizedException e) {
				output = "Wrong user name or password";
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
