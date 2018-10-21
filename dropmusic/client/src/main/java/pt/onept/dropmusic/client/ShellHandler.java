package pt.onept.dropmusic.client;

import asg.cliche.Command;
import pt.onept.dropmusic.common.exception.DuplicatedException;
import pt.onept.dropmusic.common.exception.UnauthorizedException;
import pt.onept.dropmusic.common.server.contract.DropmusicServerInterface;
import pt.onept.dropmusic.common.server.contract.type.User;

import java.rmi.RemoteException;

public class ShellHandler {
	private DropmusicServerInterface dropmusicServer;

	public ShellHandler(DropmusicServerInterface dropmusicServer) {
		this.dropmusicServer = dropmusicServer;
	}

	@Command(description = "Register a user")
	public String register(String name, String password) {
		User user = new User(name, password);
		String output;

		try {
			this.dropmusicServer.user().create(user);
			output = "User " + user.getUsername() + " created successfully";
		} catch (DuplicatedException e) {
			output = "User " + user.getUsername() + " already exists";
		} catch (UnauthorizedException e) {
			output = "Unauthorized!";
		} catch (RemoteException e) {
			//TODO Handle the failover
			e.printStackTrace();
			output = e.getMessage();
		}
		return output;
	}

	@Command(description = "Login")
	public String login(String name, String password) {
		User user = new User(name, password);
		String output;

		try {
			this.dropmusicServer.user().login(user);
			output = "Welcome to Dropmusic " + user.getUsername();
			//TODO subshell here
		} catch (RemoteException e) {
			//TODO Handle failover
			output = e.getMessage();
		} catch (UnauthorizedException e) {
			output = "Wrong user name or password";
		}
		return output;
	}
}
