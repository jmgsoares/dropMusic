package pt.onept.dropmusic.common.communication.multicast;

import pt.onept.dropmusic.common.server.contract.type.Message;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MulticastHandler {
	private String txAddress;
	private String rxAddress;
	private int port;
	private Map<UUID, Message> receivedMessageQueue;

	public Map<UUID, Message> getReceivedMessageQueue() {
		return receivedMessageQueue;
	}

	public MulticastHandler(String txAddress, String rxAddress, int port) {
		this.txAddress = txAddress;
		this.rxAddress = rxAddress;
		this.port = port;
		this.receivedMessageQueue = new ConcurrentHashMap<>();
	}

	public Message sendAndWait(UUID messageUuid, String message) {
		System.out.println("Sending message:");
		System.out.println(message);
		SendDatagram sendDatagram = new SendDatagram(this.txAddress, this.port, message);
		sendDatagram.start();
		return this.receivedMessageQueue.get(messageUuid);
	}

	public void messageReceiver(){
		Listener listener = new Listener(this.rxAddress, this.port, this.receivedMessageQueue);
		listener.start();
	}

	public void send(UUID messageUuid, String message) {
		System.out.println("Sending message:");
		System.out.println(message);
		SendDatagram sendDatagram = new SendDatagram(this.txAddress, this.port, message);
		sendDatagram.start();
	}
}