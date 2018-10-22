package pt.onept.dropmusic.common.server.contract.type;

import java.io.Serializable;

public class Notification implements Serializable {
	private String message;

	public Notification(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public Notification setMessage(String message) {
		this.message = message;
		return this;
	}
}
