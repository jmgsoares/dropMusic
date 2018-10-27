package pt.onept.dropmusic.common.server.contract.type;

import java.io.Serializable;

public class FileAddress extends DropmusicDataType implements Serializable {
	private long id;
	private String ipAddress;
	private int port;
	private String name;

		public FileAddress() {
	}

	public FileAddress(long id, String ipAddress, int port, String name) {
		this.id = id;
		this.ipAddress = ipAddress;
		this.port = port;
		this.name = name;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public FileAddress setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
		return this;
	}

	public int getPort() {
		return port;
	}

	public FileAddress setPort(int port) {
		this.port = port;
		return this;
	}

	public String getName() {
		return name;
	}

	public FileAddress setName(String name) {
		this.name = name;
		return this;
	}

	public long getId() {
		return id;
	}

	public FileAddress setId(long id) {
		this.id = id;
		return this;
	}
}
