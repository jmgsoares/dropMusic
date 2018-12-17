package pt.onept.sd1819.dropmusic.common.communication.multicast;

import pt.onept.sd1819.dropmusic.common.communication.protocol.Message;
import pt.onept.sd1819.dropmusic.common.utililty.JavaSerializationUtility;
import pt.onept.sd1819.dropmusic.common.utililty.JsonUtility;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;

/**
 * This class implements a listener for the multicast messages
 */
public class Listener implements Runnable {
	private String multicastAddress;
	private int port;
	private Map<UUID, BlockingQueue<Message>> routedReceivingQueues;
	private BlockingQueue<Message> receivingQueue;

	Listener(
			String multicastAddress,
			int port,
			Map<UUID, BlockingQueue<Message>> routedReceivingQueues,
			BlockingQueue<Message> receivingQueue
	) {
		this.multicastAddress = multicastAddress;
		this.port = port;
		this.routedReceivingQueues = routedReceivingQueues;
		this.receivingQueue = receivingQueue;
	}

	private void receiveAndQueueMessage() {
		InetAddress group;
		byte[] buffer = new byte[65536];
		DatagramPacket packet;

		try {
			group = InetAddress.getByName(this.multicastAddress);
			try (MulticastSocket socket = new MulticastSocket(this.port)) {
				socket.joinGroup(group);
				while (!Thread.interrupted()) {
					packet = new DatagramPacket(buffer, buffer.length);
					socket.receive(packet);
					String serializedMessage = new String(packet.getData(), 0, packet.getLength());
					if (!serializedMessage.contains(Message.getAPPID())) {
						System.out.println("R: Invalid Message");
						return;
					}
					Message message = (Message) JavaSerializationUtility.deserialize(packet.getData());
					BlockingQueue<Message> destinationQueue = routedReceivingQueues.get(message.getId());
					if (destinationQueue == null) destinationQueue = this.receivingQueue;
					//TODO add can lose messages if there is no space - IllegalStateException (no space)
					destinationQueue.add(message);
					System.out.println("R: " + JsonUtility.toJson(message));
				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		System.out.println("Listener up");
		while (!Thread.interrupted()) {
			this.receiveAndQueueMessage();
		}
	}
}
