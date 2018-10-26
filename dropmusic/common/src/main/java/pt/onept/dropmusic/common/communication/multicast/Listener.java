package pt.onept.dropmusic.common.communication.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.concurrent.BlockingQueue;

public class Listener implements Runnable {
	private String multicastAddress;
	private int port;
	private BlockingQueue<String> receivedMessageQueue;

	public Listener(String multicastAddress, int port, BlockingQueue<String> receivedMessageQueue) {
		this.multicastAddress = multicastAddress;
		this.port = port;
		this.receivedMessageQueue = receivedMessageQueue;
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			MulticastSocket socket = null;
			try {
				InetAddress group = InetAddress.getByName(multicastAddress);
				socket = new MulticastSocket(port);
				socket.joinGroup(group);
				while (!Thread.interrupted()) {
					byte[] buffer = new byte[65536];
					DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
					socket.receive(packet);
					String message = new String(packet.getData(), 0, packet.getLength());
					receivedMessageQueue.add(message);
					System.out.println("suuppppp, i got this:");
					System.out.println(receivedMessageQueue.take());
				}
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			} finally {
				assert socket != null;
				socket.close();
			}
		}
	}
}
