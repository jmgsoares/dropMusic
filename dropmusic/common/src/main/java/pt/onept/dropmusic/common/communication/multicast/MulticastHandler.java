package pt.onept.dropmusic.common.communication.multicast;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;

public class MulticastHandler {
	private String address;
	private int port;
	private BlockingQueue<String> receivedMessageQueue;

	public MulticastHandler(String address, int port) {
		this.address = address;
		this.port = port;
	}

	public void sendAndWait(String message) {
		System.out.println("imma send this forking message:");
		System.out.println(message);
		SendDatagram sendDatagram = new SendDatagram(this.address, this.port, message);
		sendDatagram.run();
		System.out.println("message sent and now i should w8 for answer");
	}

	public void messageReceiver(BlockingQueue<String> receivedMessageQueue){
		Listener listener = new Listener(this.address, this.port,receivedMessageQueue);
		listener.run();
	}
}