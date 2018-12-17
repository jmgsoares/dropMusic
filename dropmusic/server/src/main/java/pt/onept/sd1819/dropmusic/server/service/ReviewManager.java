package pt.onept.sd1819.dropmusic.server.service;

import pt.onept.sd1819.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.sd1819.dropmusic.common.communication.protocol.Message;
import pt.onept.sd1819.dropmusic.common.communication.protocol.MessageBuilder;
import pt.onept.sd1819.dropmusic.common.communication.protocol.Operation;
import pt.onept.sd1819.dropmusic.common.exception.DataServerException;
import pt.onept.sd1819.dropmusic.common.exception.IncompleteException;
import pt.onept.sd1819.dropmusic.common.server.contract.subcontract.ReviewManagerInterface;
import pt.onept.sd1819.dropmusic.common.server.contract.type.Review;
import pt.onept.sd1819.dropmusic.common.server.contract.type.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.TimeoutException;

/**
 * Class to manage all the album review operations
 */
public class ReviewManager extends UnicastRemoteObject implements ReviewManagerInterface {
	private MulticastHandler multicastHandler;

	public ReviewManager(MulticastHandler multicastHandler) throws RemoteException {
		super();
		this.multicastHandler = multicastHandler;
	}

	@Override
	public void add(User self, Review review) throws RemoteException, DataServerException, IncompleteException {
		Message outgoing = MessageBuilder.build(Operation.ADD_REVIEW, self)
				.setData(review);

		try {
			Message incoming = multicastHandler.sendAndWait(outgoing);
			switch (incoming.getOperation()) {
				case SUCCESS:
					break;
				case INCOMPLETE:
					throw new IncompleteException();
				default:
					throw new RemoteException();
			}
		} catch (TimeoutException e) {
			System.out.println("NO SERVER ANSWER!");
			throw new DataServerException();
		}
	}

}
