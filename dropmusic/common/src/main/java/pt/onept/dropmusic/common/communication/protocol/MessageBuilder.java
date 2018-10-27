package pt.onept.dropmusic.common.communication.protocol;

import java.util.*;

public final class MessageBuilder {

	private MessageBuilder() {
	}

	public static <T> Message<T> buildMessage(T object, String operation) {
		Map<String, String> dataList = new HashMap<>();
		//TODO PUT object to list??
		return MessageBuilder.buildMessage(dataList, operation);
	}

	public static <T> Message<T> buildMessage(Map<String,String> objects, String operation) {
		UUID requestUuid = UUID.randomUUID();
		//TODO error on objects.getclass() - need to get the Class of the object inside the list
		return new Message<> (requestUuid, operation, Message.class , objects);
	}
}
