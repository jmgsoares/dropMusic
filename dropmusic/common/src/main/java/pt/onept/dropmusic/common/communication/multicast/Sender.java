package pt.onept.dropmusic.common.communication.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.concurrent.BlockingQueue;

public class Sender implements Runnable {
	private String multicastAddress;
	private int port;
	private BlockingQueue<byte[]> sendingQueue;

	Sender(String multicastAddress, int port, BlockingQueue<byte[]> sendingQueue) {
		this.multicastAddress = multicastAddress;
		this.port = port;
		this.sendingQueue = sendingQueue;
	}

	@Override
	public void run() {
		try (MulticastSocket socket = new MulticastSocket(this.port)) {
			InetAddress group = InetAddress.getByName(this.multicastAddress);
			while (!Thread.interrupted()) {
				try {
					byte[] serializedMessage = this.sendingQueue.take();
					DatagramPacket packet = new DatagramPacket(
							serializedMessage,
							serializedMessage.length,
							group,
							port
					);
					socket.send(packet);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
