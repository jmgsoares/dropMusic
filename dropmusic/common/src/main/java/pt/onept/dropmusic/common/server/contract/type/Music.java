package pt.onept.dropmusic.common.server.contract.type;

import java.io.Serializable;

public class Music extends DropmusicDataType<Music> {
	private int albumId;
	private String name;

	public Music() {
	}

	public Music(int id, int albumId, String name) {
		super(id);
		this.albumId = albumId;
		this.name = name;
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
