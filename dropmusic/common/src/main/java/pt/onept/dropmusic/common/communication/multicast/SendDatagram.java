package pt.onept.dropmusic.common.communication.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class SendDatagram implements Runnable {
	private String multicastAddress;
	private int port;
	private String message;

	public SendDatagram(String multicastAddress, int port, String message) {
		this.multicastAddress = multicastAddress;
		this.port = port;
		this.message = message;
	}

	@Override
	public void run() {
		try {
			MulticastSocket socket = new MulticastSocket(this.port);
			InetAddress group = InetAddress.getByName(this.multicastAddress);
			byte[] buffer = message.getBytes();
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, port);
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
