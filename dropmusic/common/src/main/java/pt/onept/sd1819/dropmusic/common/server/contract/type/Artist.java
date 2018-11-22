package pt.onept.sd1819.dropmusic.common.server.contract.type;

import java.util.List;

public class Artist extends DropmusicDataType<Artist> {
	//TODO add some more data fields because...
	private String name;
	private List<Album> albums;

	public Artist() {
	}

	public Artist(int id, String name, List<Album> albums) {
		super(id);
		this.name = name;
		this.albums = albums;
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