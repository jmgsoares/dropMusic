package pt.onept.dropmusic.common.communication.multicast;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;

public class MulticastHandler {
	private String address;
	private int port;
	private UUID uuid;
	private BlockingQueue<String> messageQueue;

	public MulticastHandler(String address, int port, UUID uuid) {
		this.address = address;
		this.port = port;
		this.uuid = uuid;
	}

	public void sendAndWait() {



	}
}