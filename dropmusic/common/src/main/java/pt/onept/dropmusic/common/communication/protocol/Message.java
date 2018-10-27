package pt.onept.dropmusic.common.communication.protocol;

import java.util.Map;
import java.util.UUID;


public class Message<T> {
	private static String APPID = "de9db107-9834-4aeb-a731-86d959513831";
	private final String appId;
	private final UUID id;
	private final String operation;
	private final Class<T> objectClass;
	private final Map<String, String> parameters;

	public Message(UUID id, String operation, Class objectClass, Map<String,String> dataList) {
		this.id = id;
		this.operation = operation;
		this.objectClass = objectClass;
		this.parameters = dataList;
		this.appId = Message.APPID;
	}

	public static String getAppIdConstant() {
		return Message.APPID;
	}

	public UUID getId() {
		return id;
	}

	public String getOperation() {
		return operation;
	}

	public Class getObjectClass() {
		return objectClass;
	}

	public void putParameter(String name, String value) {
		this.parameters.put(name, value);
	}

	public String getParameter(String name) {
		return this.parameters.get(name);
	}
}