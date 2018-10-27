package pt.onept.dropmusic.common.communication.multicast;

import pt.onept.dropmusic.common.server.contract.type.Message;

import java.util.Map;
import java.util.UUID;

public class Listener extends Thread {
	private String multicastAddress;
	private int port;
	private Map<UUID, Message> receivedMessageQueue;

	Listener(String multicastAddress, int port, Map<UUID, Message> receivedMessageQueue) {
		this.multicastAddress = multicastAddress;
		this.port = port;
		this.receivedMessageQueue = receivedMessageQueue;
		System.out.println("Listener up");
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			ReceiveDatagram.get(this.multicastAddress,this.port,this.receivedMessageQueue);
		}
	}
}
