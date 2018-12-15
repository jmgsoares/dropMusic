package pt.onept.sd1819.dropmusic.web;

import org.apache.struts2.interceptor.SessionAware;
import pt.onept.sd1819.dropmusic.common.server.contract.type.User;

import java.util.Map;

public interface LoginAware extends SessionAware {

	Map<String, Object> getSession();

	default User getUser() {
		return (User) this.getSession().get("user");
	}

	default boolean isLogged() {
		return this.getSession() != null && this.getSession().containsKey("logged") && (boolean) this.getSession().get("logged");
	}

	default void login(User user) {
		this.getSession().put("user", user);
		this.getSession().put("logged", true);
	}

	default void logout() {
		this.getSession().clear();
	}

}
