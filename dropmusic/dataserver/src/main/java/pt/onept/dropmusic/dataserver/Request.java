package pt.onept.dropmusic.dataserver;

import pt.onept.dropmusic.common.server.contract.type.Message;

final class Request {

	private Request() { }

	static void process(Message message){
		System.out.println("I got message " + message.getUuid() + " to process");
	}
}