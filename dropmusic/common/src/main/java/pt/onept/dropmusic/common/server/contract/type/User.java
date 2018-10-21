package pt.onept.dropmusic.common.server.contract.type;

import java.io.Serializable;

public class User implements Serializable {
	String username;
	String password;
	boolean editor;

	public User() {
		editor=false;
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
}
