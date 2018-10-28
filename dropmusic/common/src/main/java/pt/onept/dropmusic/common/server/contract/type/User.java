package pt.onept.dropmusic.common.server.contract.type;

import java.io.Serializable;

public class User extends DropmusicDataType<User> {
	private String username;
	private String password;
	private Boolean editor;

	public User() {
	}

	public User(int id, String username, String password, Boolean editor) {
		super(id);
		this.username = username;
		this.password = password;
		this.editor = editor;
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

}
