package pt.onept.dropmusic.common.communication.multicast;

import java.util.UUID;

public class MulticastHandler extends Thread{
	private String address;
	private int port;
	private UUID uuid;

	public MulticastHandler(String address, int port, UUID uuid) {
		this.address = address;
		this.port = port;
		this.uuid = uuid;
		ReceiveDatagram receive = new ReceiveDatagram(this.address, this.port, uuid);
		receive.start();
	}

	public void send(byte[] buffer) {
		SendDatagram send;
		send = new SendDatagram(this.address, this.port, buffer, this.uuid);
		send.start();
	}
}