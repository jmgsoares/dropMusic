package pt.onept.dropmusic.common.server.contract.type;

import java.util.Set;
import java.util.UUID;

public class Message<T> {
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

	public UUID getId() {
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

