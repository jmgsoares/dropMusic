package pt.onept.dropmusic.dataserver;

import pt.onept.dropmusic.common.communication.protocol.Message;

final class MessageHandler {

	private MessageHandler() {
	}

	static void handle(Message message) {
		System.out.println("I got message " + message.getId() + " to process");
	}
}