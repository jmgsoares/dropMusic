package pt.onept.sd1819.dropmusic.common.communication.multicast;

import com.google.gson.JsonSyntaxException;
import pt.onept.sd1819.dropmusic.common.communication.protocol.Message;
import pt.onept.sd1819.dropmusic.common.utililty.JavaSerializationUtility;
import pt.onept.sd1819.dropmusic.common.utililty.JsonUtility;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * This class handles all the multicast communication
 */
public class MulticastHandler {
	//TODO failOverTime value never used?? - check if needed
	private static int failOverTime;
	private String txAddress;
	private String rxAddress;
	private int port;
	private Map<UUID, BlockingQueue<Message>> routedReceivingQueues;
	private BlockingQueue<Message> receivingQueue;
	private BlockingQueue<byte[]> sendingQueue;

	public MulticastHandler(String txAddress, String rxAddress, int port, int failOverTime) {
		MulticastHandler.failOverTime = failOverTime;
		this.txAddress = txAddress;
		this.rxAddress = rxAddress;
		this.port = port;
		this.routedReceivingQueues = new ConcurrentHashMap<>();
		this.receivingQueue = new LinkedBlockingQueue<>();
		this.sendingQueue = new LinkedBlockingQueue<>();
		Listener listener = new Listener(this.rxAddress, this.port, this.routedReceivingQueues, this.receivingQueue);
		Sender sender = new Sender(this.txAddress, this.port, this.sendingQueue);
		new Thread(listener).start();
		new Thread(sender).start();
	}

	public Message sendAndWait(Message message) throws JsonSyntaxException, TimeoutException {
		BlockingQueue<Message> responseQueue = new LinkedBlockingQueue<>();

		this.routedReceivingQueues.put(message.getId(), responseQueue);
		this.send(message);
		try {
			return receive(message.getId());
		} finally {
			routedReceivingQueues.remove(message.getId());
		}
	}

	private Message receive(UUID messageId) throws JsonSyntaxException, TimeoutException {
		Message response = null;
		while (response == null) {
			try {
				response = this.routedReceivingQueues.get(messageId).poll(5, TimeUnit.SECONDS);
				if (response == null) {
					this.routedReceivingQueues.remove(messageId);
					throw new TimeoutException();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return response;
	}

	public Message receive() {
		Message receivedMessage = null;

		while (receivedMessage == null) {
			try {
				receivedMessage = receivingQueue.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return receivedMessage;
	}

	public void send(Message message) {
		byte[] serializedMessage = new byte[0];
		try {
			serializedMessage = JavaSerializationUtility.serialize(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("S: " + JsonUtility.toJson(message));
		this.sendingQueue.add(serializedMessage);
	}
}