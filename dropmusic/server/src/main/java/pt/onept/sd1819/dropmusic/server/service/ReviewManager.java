package pt.onept.sd1819.dropmusic.server.service;

import pt.onept.sd1819.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.sd1819.dropmusic.common.communication.protocol.Message;
import pt.onept.sd1819.dropmusic.common.communication.protocol.MessageBuilder;
import pt.onept.sd1819.dropmusic.common.communication.protocol.Operation;
import pt.onept.sd1819.dropmusic.common.exception.DataServerException;
import pt.onept.sd1819.dropmusic.common.exception.IncompleteException;
import pt.onept.sd1819.dropmusic.common.server.contract.subcontract.ReviewManagerInterface;
import pt.onept.sd1819.dropmusic.common.server.contract.type.Album;
import pt.onept.sd1819.dropmusic.common.server.contract.type.Review;
import pt.onept.sd1819.dropmusic.common.server.contract.type.User;
import pt.onept.sd1819.dropmusic.server.Server;

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


			Message outgoingAlbumRequest = MessageBuilder.build(Operation.READ, self)
					.setData(new Album().setId(review.getAlbumId()));
			Message incomingAlbum = multicastHandler.sendAndWait(outgoingAlbumRequest);
			Server.dropmusicServer.update().update(incomingAlbum.getData());



		} catch (TimeoutException e) {
			System.out.println("NO SERVER ANSWER!");
			throw new DataServerException();
		}
	}

}
