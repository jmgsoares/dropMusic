package pt.onept.dropmusic.common.server.contract.type;

import java.util.*;


public class Message<T> {
	private final String appid = "de9db107-9834-4aeb-a731-86d959513831";
	private final UUID id;
	private final String operation;
	private final String objectClass;
	private final Set<T> dataList;

	public Message(UUID id, String operation, String objectClass, Set<T> dataList) {
		this.id = id;
		this.operation = operation;
		this.objectClass = objectClass;
		this.dataList = dataList;
	}

	public UUID getUuid() {
		return id;
	}

	public String getOperation() {
		return operation;
	}

	public String getObjectClass() {
		return objectClass;
	}

	public Set<T> getDataList() {
		return dataList;
	}
}