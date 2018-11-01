package pt.onept.dropmusic.common.server.contract.type;

public class Upload extends DropmusicDataType<Upload> {
	private String ipAddress;
	private String name;

	public Upload() {
	}

	public Upload(int id, String ipAddress, String name) {
		super(id);
		this.ipAddress = ipAddress;
		this.name = name;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public Upload setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
		return this;
	}

	public String getName() {
		return name;
	}

	public Upload setName(String name) {
		this.name = name;
		return this;
	}

}