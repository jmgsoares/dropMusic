package pt.onept.dropmusic.common.communication.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.concurrent.BlockingQueue;

public class Sender implements Runnable {
	private String multicastAddress;
	private int port;
	private BlockingQueue<String> sendingQueue;

	Sender(String multicastAddress, int port, BlockingQueue<String> sendingQueue) {
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
					String serializedMessage = this.sendingQueue.take();
					DatagramPacket packet = new DatagramPacket(
							serializedMessage.getBytes(),
							serializedMessage.getBytes().length,
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
