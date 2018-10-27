package pt.onept.dropmusic.common.server.contract.type;

import java.io.Serializable;

public class Music implements Serializable {
	private String name;

	public Music(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Music setName(String name) {
		this.name = name;
		return this;
	}
}
