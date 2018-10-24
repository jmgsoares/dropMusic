package pt.onept.dropmusic.common.communication.multicast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.UUID;

public class SendDatagram extends Thread {
	private final String multicastAddress;
	private final int port;
	private final UUID uuid;

	public SendDatagram(UUID uuid, String address, int port) {
		this.uuid=uuid;
		this.multicastAddress = address;
		this.port= port;
	}

	public void run() {
		try {
			String message;
			message = "Logged in the group!";
			sendMessage(message);
			while(true){
				InputStreamReader input = new InputStreamReader(System.in);
				BufferedReader reader = new BufferedReader(input);
				message = reader.readLine();
				if(message.equals("\\exit")) {
					message = "Logged off the group!";
					sendMessage(message);
					Thread.currentThread().interrupt();
					return;
				}
				sendMessage(message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendMessage(String message){
		try {
			MulticastSocket socket = new MulticastSocket(port);
			message = uuid + message;
			byte[] buffer = message.getBytes();
			InetAddress group = InetAddress.getByName(multicastAddress);
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, port);
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
