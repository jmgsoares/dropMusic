package pt.onept.sd1819.dropmusic.web.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import pt.onept.sd1819.dropmusic.common.exception.*;
import pt.onept.sd1819.dropmusic.common.server.contract.subcontract.UserManagerInterface;
import pt.onept.sd1819.dropmusic.common.server.contract.type.User;
import pt.onept.sd1819.dropmusic.web.LoginAware;
import pt.onept.sd1819.dropmusic.web.communication.CommunicationManager;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 * Class to handle all the related User actions
 * This Class will handle all the calls to the RMI server in order to perform the necessary operations
 * It uses the User Class as its base model
 * @see com.opensymphony.xwork2.ModelDriven
 * This class implements the LoginAware interface to use the session attributes
 * @see pt.onept.sd1819.dropmusic.web.LoginAware
 */
public class UserAction extends ActionSupport implements LoginAware, ModelDriven<User> {
	private User user = new User();
	private Map<String, Object> session;
	private List<User> userList;

	/**
	 * Action to handle login
	 * @return Action result
	 */
	public String userLogin() {
		try {
			UserManagerInterface userManager = CommunicationManager.getServerInterface().user();
			User loggedUser = userManager.login(this.user);
			this.login(loggedUser);
			return Action.SUCCESS;
		} catch (RemoteException | DataServerException e) {
			e.printStackTrace();
			addActionError("There was an error around here");
		} catch (UnauthorizedException e) {
			addActionError("Wrong credentials");
		}
		return Action.ERROR;
	}

	/**
	 * Action to handle logout
	 * @return Action result
	 */
	public String userLogout(){
		this.logout();
		return Action.SUCCESS;
	}

	/**
	 * Action to register/create a user
	 * @return Action result
	 */
	public String create() {
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

	/**
	 * Read user
	 * @return Action result
	 */
	public String read() {
		if (user.getId()==0) return Action.INPUT;
		if (!this.getUser().getEditor() && ( user.getId() != this.getUser().getId() ) ) return Action.INPUT;
		try {
			UserManagerInterface userManager = CommunicationManager.getServerInterface().user();
			user = userManager.read(this.getUser(), user);
			return Action.SUCCESS;
		} catch (RemoteException | DataServerException e) {
			e.printStackTrace();
			addActionError("There was an error around here");
			return Action.ERROR;
		} catch (NotFoundException e) {
			addActionError("User not found");
			return Action.ERROR;
		} catch (UnauthorizedException e) {
			addActionError("You lack the permissions to perform the operation");
			return Action.ERROR;
		}
	}

	/**
	 * Updates a specific user
	 * @return Action result
	 */
	public String update() {
		if (user.getId()==0) return Action.INPUT;
		try {
			UserManagerInterface userManager = CommunicationManager.getServerInterface().user();
			userManager.update(this.getUser(), user);
			addActionMessage("User " + user.getUsername() + " updated successfully");
			return Action.SUCCESS;
		} catch (DataServerException | RemoteException e) {
			e.printStackTrace();
			addActionError("There was an error around here");
			return Action.ERROR;
		} catch (IncompleteException e) {
			addActionError("Some of the information provided is incorrect");
			return Action.ERROR;
		} catch (NotFoundException e) {
			addActionError("User not found");
			return Action.ERROR;
		} catch (UnauthorizedException e) {
			addActionError("You lack the permissions to perform the operation");
			return Action.ERROR;
		}
	}

	/**
	 * Lists all the Users
	 * @return Action result
	 */
	public String list()  {
		try {
			UserManagerInterface userManager = CommunicationManager.getServerInterface().user();
			userList = userManager.list(this.getUser());
			return Action.SUCCESS;
		} catch (DataServerException | RemoteException e) {
			e.printStackTrace();
			addActionError("There was an error around here");
			return Action.ERROR;
		}
	}

	/**
	 * Getter
	 * @return userList parameter
	 */
	public List<User> getUserList() {
		return userList;
	}

	/**
	 * Getter for the model of this Action Class (in this case, Artist)
	 * @return artist parameter
	 * @see ModelDriven
	 */
	public User getModel() {
		return this.user;
	}

	/**
	 * Setter for session
	 * @param map session map
	 * @see pt.onept.sd1819.dropmusic.web.LoginAware
	 */
	@Override
	public void setSession(Map<String, Object> map) {
		this.session = map;
	}

	/**
	 * Getter for session
	 * @return the session map
	 * @see pt.onept.sd1819.dropmusic.web.LoginAware
	 */
	@Override
	public Map<String, Object> getSession() {
		return this.session;
	}

	/**
	 * Function to get the OAuth Authorization URL
	 * @return the AuthUrl
	 */
	public String getoAuthUrl() {
		try {
			return CommunicationManager.getServerInterface().oAuthProvider().getAuthorizationUrl();
		} catch (RemoteException e) {
			e.printStackTrace();
			e.printStackTrace();
			addActionError("There was an error around here");
			return Action.ERROR;
		}
	}
}
