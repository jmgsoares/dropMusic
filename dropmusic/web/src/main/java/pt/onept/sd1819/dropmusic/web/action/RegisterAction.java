package pt.onept.sd1819.dropmusic.web.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import pt.onept.sd1819.dropmusic.common.exception.DataServerException;
import pt.onept.sd1819.dropmusic.common.exception.DuplicatedException;
import pt.onept.sd1819.dropmusic.common.exception.IncompleteException;
import pt.onept.sd1819.dropmusic.common.exception.UnauthorizedException;
import pt.onept.sd1819.dropmusic.common.server.contract.subcontract.UserManagerInterface;
import pt.onept.sd1819.dropmusic.common.server.contract.type.User;
import pt.onept.sd1819.dropmusic.web.communication.CommunicationManager;

import java.rmi.RemoteException;


public class RegisterAction extends ActionSupport implements ModelDriven<User> {
	private User user = new User();

	@Override
	public String execute() throws Exception {
		try {
			UserManagerInterface userManager = CommunicationManager.getServerInterface().user();
			userManager.create(user, null);
			addActionMessage("User " + user.getUsername() + " created successfully");
			return Action.SUCCESS;
		} catch (DuplicatedException e) {
			addActionError("User " + user.getUsername() + " already exists");
		} catch (IncompleteException e) {
			addActionError("Some data fields were left empty");
		} catch (RemoteException | UnauthorizedException | DataServerException e) {
			e.printStackTrace();
			addActionError("There was an error around here");
		}
		return Action.ERROR;
	}

	@Override
	public User getModel() {
		return user;
	}
}
