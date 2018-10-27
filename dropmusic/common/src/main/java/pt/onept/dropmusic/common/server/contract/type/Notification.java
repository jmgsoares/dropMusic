package pt.onept.dropmusic.common.server.contract.type;

import java.io.Serializable;

public class Notification extends DropmusicDataType implements Serializable {
	private int id;
	private int userId;
	private String message;

	public Notification() {
	}

	public Notification(int id, int userId, String message) {
		this.id = id;
		this.userId = userId;
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public Notification setId(int id) {
		this.id = id;
		return this;
	}

	public int getUserId() {
		return userId;
	}

	public Notification setUserId(int userId) {
		this.userId = userId;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public Notification setMessage(String message) {
		this.message = message;
		return this;
	}
}
