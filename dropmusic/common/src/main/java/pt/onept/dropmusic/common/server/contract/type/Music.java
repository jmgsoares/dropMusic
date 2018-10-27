package pt.onept.dropmusic.common.server.contract.type;

import java.io.Serializable;

public class Music extends DropmusicDataType implements Serializable {
	private int id;
	private int albumId;
	private String name;

	public Music() {
	}

	public Music(int id, int albumId, String name) {
		this.id = id;
		this.albumId = albumId;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public Music setId(int id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Music setName(String name) {
		this.name = name;
		return this;
	}

	public int getAlbumId() {
		return albumId;
	}

	public Music setAlbumId(int albumId) {
		this.albumId = albumId;
		return this;
	}
}
