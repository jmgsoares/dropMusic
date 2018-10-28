package pt.onept.dropmusic.common.server.contract.type;

import java.io.Serializable;

public class Notification extends DropmusicDataType<Notification> {
	private int userId;
	private String message;

	public Notification() {
	}

	public Notification(int id, int userId, String message) {
		super(id);
		this.userId = userId;
		this.message = message;
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
