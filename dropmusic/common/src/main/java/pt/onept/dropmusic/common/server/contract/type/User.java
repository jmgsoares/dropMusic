package pt.onept.dropmusic.common.server.contract.type;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class User implements Serializable {
	private Long id;
	private String username;
	private String password;
	private boolean editor;
	private List<Notification> notifications;

	public User() {
		editor = false;
		notifications = new LinkedList<>();
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.editor = false;
	}

	public String getUsername() {
		return username;
	}

	public User setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	public boolean isEditor() {
		return editor;
	}

	public User setEditor(boolean editor) {
		this.editor = editor;
		return this;
	}

	public Long getId() {
		return id;
	}

	public User setId(Long id) {
		this.id = id;
		return this;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

}
