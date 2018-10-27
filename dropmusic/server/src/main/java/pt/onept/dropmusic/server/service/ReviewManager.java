package pt.onept.dropmusic.server.service;

import pt.onept.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.dropmusic.common.server.contract.subcontract.ReviewManagerInterface;
import pt.onept.dropmusic.common.server.contract.type.Review;
import pt.onept.dropmusic.common.server.contract.type.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ReviewManager extends UnicastRemoteObject implements ReviewManagerInterface {
	private MulticastHandler multicastHandler;

	public ReviewManager(MulticastHandler multicastHandler) throws RemoteException {
		super();
		this.multicastHandler = multicastHandler;
	}

	@Override
	public void add(User self, Review review) {

	}

}
