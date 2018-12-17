package pt.onept.sd1819.dropmusic.web;

import org.apache.struts2.interceptor.SessionAware;
import pt.onept.sd1819.dropmusic.common.server.contract.type.User;

import java.util.Map;

/**
 * Interface to create default functions to give direct access to relevant data
 * @see org.apache.struts2.interceptor.SessionAware
 */
public interface LoginAware extends SessionAware {

	/**
	 * Getter for session
	 * @see SessionAware
	 * @return the session map
	 */
	Map<String, Object> getSession();

	/**
	 * Function to return the user in session
	 * @return the user in session
	 */
	default User getUser() {
		return (User) this.getSession().get("user");
	}

	/**
	 * Function to retrieve the user login status
	 * @return the login status of the user
	 */
	default boolean isLogged() {
		return this.getSession() != null && this.getSession().containsKey("logged") && (boolean) this.getSession().get("logged");
	}

	/**
	 * Function to set the login of an user
	 * @param user user logged in
	 */
	default void login(User user) {
		this.getSession().put("user", user);
		this.getSession().put("logged", true);
	}

	/**
	 * Function to clear the session upon logout call
	 */
	default void logout() {
		this.getSession().clear();
	}

}
