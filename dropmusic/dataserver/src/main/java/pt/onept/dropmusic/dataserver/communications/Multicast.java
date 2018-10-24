package pt.onept.dropmusic.dataserver.communications;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class Multicast implements Runnable {
    private InetAddress multicastAddress;
    private int port;
    private MulticastSocket socket;

    public Multicast(InetAddress multicastAddress, int port) {
        this.multicastAddress = multicastAddress;
        this.port = port;
        this.socket = null;
    }

    public Multicast(String multicastAddress, int port) throws UnknownHostException {
        this(InetAddress.getByName(multicastAddress), port);
    }

    @Override
    public void run() {
        try {
            this.socket = new MulticastSocket(this.port);
            this.socket.joinGroup(multicastAddress);
            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String message = new String(packet.getData(),0,packet.getLength());
                //What to do next?
                //handle multiple packets?? Max packet size?
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
