package pt.onept.sd1819.dropmusic.common.server.contract.type;

public class File extends DropmusicDataType<File> {
	private String ipAddress;
	private String name;
	private String dropBoxFileId;

	public File() {
	}

	public File(int id, String ipAddress, String name, String dropBoxFileId) {
		super(id);
		this.ipAddress = ipAddress;
		this.name = name;
		this.dropBoxFileId = dropBoxFileId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public File setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
		return this;
	}

	public String getName() {
		return name;
	}

	public File setName(String name) {
		this.name = name;
		return this;
	}

	public String getDropBoxFileId() {
		return dropBoxFileId;
	}

	public File setDropBoxFileId(String dropBoxFileId) {
		this.dropBoxFileId = dropBoxFileId;
		return this;
	}
}