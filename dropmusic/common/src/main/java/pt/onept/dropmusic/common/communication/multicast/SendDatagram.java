package pt.onept.dropmusic.common.communication.multicast;

import pt.onept.dropmusic.common.util.UuidUtil;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.UUID;

public class SendDatagram extends Thread {
	private final String multicastAddress;
	private final int port;
	private byte[] buffer;
	private UUID uuid;

	SendDatagram(String address, int port, byte[] buffer, UUID uuid) {
		this.multicastAddress = address;
		this.port = port;
		this.buffer = buffer;
		this.uuid = uuid;
		this.start();
	}

	public void run() {
		try {
			UuidUtil uuidTool = new UuidUtil();
			this.buffer = uuidTool.AddUuidBuffer(this.uuid,this.buffer);
			MulticastSocket socket = new MulticastSocket(this.port);
			InetAddress group = InetAddress.getByName(this.multicastAddress);
			DatagramPacket packet = new DatagramPacket(this.buffer, this.buffer.length, group, this.port);
			socket.send(packet);
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
