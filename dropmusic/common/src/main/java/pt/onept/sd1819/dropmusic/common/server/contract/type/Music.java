package pt.onept.sd1819.dropmusic.common.server.contract.type;

public class Music extends DropmusicDataType<Music> {
	//TODO add some more data fields because...
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