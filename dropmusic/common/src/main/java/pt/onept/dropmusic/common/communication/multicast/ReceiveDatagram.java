package pt.onept.dropmusic.common.communication.multicast;

import pt.onept.dropmusic.common.server.contract.type.Message;
import pt.onept.dropmusic.common.utililty.GsonUtility;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Map;
import java.util.UUID;

final class ReceiveDatagram {

	private ReceiveDatagram() { }

	static void get(String multicastAddress, int port, Map<UUID, Message> receivedMessageQueue) {
		MulticastSocket socket = null;
		try {
			InetAddress group = InetAddress.getByName(multicastAddress);
			socket = new MulticastSocket(port);
			socket.joinGroup(group);
			while (!Thread.interrupted()) {
				byte[] buffer = new byte[65536];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				String gsonMessage = new String(packet.getData(), 0, packet.getLength());
				if(!gsonMessage.contains("de9db107-9834-4aeb-a731-86d959513831")) {
					System.out.println("Invalid message received");
					return;
				}
				Message message = GsonUtility.fromGson(gsonMessage);
				receivedMessageQueue.put(message.getUuid(), message);
				System.out.println("valid received message from " + message.getUuid());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			assert socket != null;
			socket.close();
		}
	}
}

