package pt.onept.dropmusic.common.communication.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.UUID;

class ReceiveDatagram extends Thread {
	private String address;
	private int port;
	private UUID uuid;

	ReceiveDatagram(String address, int port, UUID uuid) {
		this.address = address;
		this.port = port;
		this.uuid = uuid;
	}

	public void run() {
		try {
			MulticastSocket socket = new MulticastSocket(this.port);
			InetAddress group = InetAddress.getByName(this.address);
			socket.joinGroup(group);
			while (true) {
				byte[] buffer = new byte[65500];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				String message = new String(packet.getData(),0,packet.getLength());
				int messagelenght = message.length();
				if(!message.contains(this.uuid.toString())){
					message=message.substring(this.uuid.toString().length(),messagelenght);
					//TODO CREATE INTERFACE
					messageHandler(message);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
