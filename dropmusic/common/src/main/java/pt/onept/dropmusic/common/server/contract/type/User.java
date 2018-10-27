package pt.onept.dropmusic.common.server.contract.type;

import java.io.Serializable;

public class User extends DropmusicDataType implements Serializable {
	private int id;
	private String username;
	private String password;
	private Boolean editor;

	public User() {
		editor = false;
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

	public Boolean isEditor() {
		return editor;
	}

	public User setEditor(Boolean editor) {
		this.editor = editor;
		return this;
	}

	public int getId() {
		return id;
	}

	public User setId(int id) {
		this.id = id;
		return this;
	}

}
