package pt.onept.dropmusic.common.server.contract.type;

import java.io.Serializable;
import java.util.List;

public class Artist extends DropmusicDataType implements Serializable {
	private int id;
	private String name;
	private List<Album> albums;

	public Artist() {
	}

	public Artist(int id, String name, List<Album> albums) {
		this.id = id;
		this.name = name;
		this.albums = albums;
	}

	public int getId() {
		return id;
	}

	public Artist setId(int id) {
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

	public List<Album> getAlbums() {
		return albums;
	}

	public Artist setAlbums(List<Album> albums) {
		this.albums = albums;
		return this;
	}
}
