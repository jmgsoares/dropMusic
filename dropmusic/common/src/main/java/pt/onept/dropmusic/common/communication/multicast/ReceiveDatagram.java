package pt.onept.dropmusic.common.communication.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.UUID;

public class ReceiveDatagram extends Thread{
	private final String multicastAddress;
	private final int port;
	private final UUID uuid;

	public ReceiveDatagram(UUID uuid, String address, int port) {
		this.uuid=uuid;
		this.multicastAddress = address;
		this.port= port;
	}

	public void run() {
		MulticastSocket socket = null;
		try {
			InetAddress group = InetAddress.getByName(multicastAddress);
			socket = new MulticastSocket(port);
			socket.joinGroup(group);
			while (!Thread.interrupted()) {
				byte[] buffer = new byte[65536];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				String text = new String(packet.getData(), 0, packet.getLength());
				int messageLength=text.length();
				if(!text.contains(uuid.toString())) {
					System.out.print(text.substring(0,36) + " said: ");
					System.out.println(text.substring(36,messageLength));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			assert socket != null;
			socket.close();
		}
	}
}