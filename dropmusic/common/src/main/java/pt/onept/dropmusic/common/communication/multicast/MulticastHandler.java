package pt.onept.dropmusic.common.communication.multicast;

import com.google.gson.JsonSyntaxException;
import pt.onept.dropmusic.common.communication.protocol.Message;
import pt.onept.dropmusic.common.utililty.JsonUtility;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

public class MulticastHandler {
	private String txAddress;
	private String rxAddress;
	private int port;
	private Map<UUID, BlockingQueue<Message>> routedReceivingQueues;
	private BlockingQueue<Message> receivingQueue;
	private BlockingQueue<String> sendingQueue;

	public MulticastHandler(String txAddress, String rxAddress, int port) {
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

	private Message receive(UUID messageId) throws JsonSyntaxException, TimeoutException{
		Message response = null;

		while(response == null) {
			try {
				response = this.routedReceivingQueues.get(messageId).poll(3, TimeUnit.SECONDS);
				if (response == null) throw new TimeoutException();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return response;
	}

	public Message receive() {
		Message receivedMessage = null;

		while(receivedMessage == null) {
			try {
				receivedMessage = receivingQueue.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return receivedMessage;
	}

	public void send(Message message) {
		String serializedMessage = JsonUtility.toJson(message);
		System.out.println("Sending message:");
		System.out.println(serializedMessage);
		this.sendingQueue.add(serializedMessage);
	}
}