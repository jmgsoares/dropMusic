package pt.onept.dropmusic.common.communication.multicast;

import java.util.UUID;

public class MulticastHandler {

	public MulticastHandler(String address, int port) {
		UUID uuid = UUID.randomUUID();
		boolean run = true;
		while (true) {
			ReceiveDatagram receiveConnection = new ReceiveDatagram(uuid, address, port);
			SendDatagram sendConnection = new SendDatagram(uuid, address, port);
			receiveConnection.start();
			System.out.println("Joined MultiCast group @ " + address + "with the uuid " + uuid);
			sendConnection.start();
		}
	}
}