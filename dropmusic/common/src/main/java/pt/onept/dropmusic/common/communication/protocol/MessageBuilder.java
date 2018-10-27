package pt.onept.dropmusic.common.communication.protocol;

import pt.onept.dropmusic.common.server.contract.type.DropmusicDataType;
import pt.onept.dropmusic.common.server.contract.type.User;

import java.util.*;

public final class MessageBuilder {

	private MessageBuilder() {
	}

	public static <T extends DropmusicDataType> Message<T> build(Operation operation, User self) {
		return new Message<T>()
				.setAppId(Message.getAPPID())
				.setId(UUID.randomUUID())
				.setOperation(operation)
				.setSelf(self);
	}

	public static <T extends DropmusicDataType> Message<T> buildReply(Message request, Operation operation) {
		return build(operation, null).setId(request.getId());
	}
}
