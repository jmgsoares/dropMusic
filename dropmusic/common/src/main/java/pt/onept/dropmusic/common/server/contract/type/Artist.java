package pt.onept.dropmusic.common.server.contract.type;

import java.io.Serializable;

public class Artist implements Serializable {
	private long id;
	private String name;

	public Artist(String name) {
		this.name = name;
	}

	public Artist(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public Artist setId(long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Artist setName(String name) {
		this.name = name;
		return this;
	}
}
