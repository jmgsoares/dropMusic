package pt.onept.dropmusic.server.service;

import pt.onept.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.dropmusic.common.communication.protocol.Message;
import pt.onept.dropmusic.common.communication.protocol.MessageBuilder;
import pt.onept.dropmusic.common.communication.protocol.Operation;
import pt.onept.dropmusic.common.exception.DataServerException;
import pt.onept.dropmusic.common.server.contract.subcontract.NotificationManagerInterface;
import pt.onept.dropmusic.common.server.contract.type.Notification;
import pt.onept.dropmusic.common.server.contract.type.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class NotificationManager extends UnicastRemoteObject implements NotificationManagerInterface {
	private MulticastHandler multicastHandler;

	public NotificationManager(MulticastHandler multicastHandler) throws RemoteException {
		super();
		this.multicastHandler = multicastHandler;
	}

	@Override
	public List<Notification> get(User self) throws RemoteException, DataServerException {
		try {
			Message outgoing = MessageBuilder.build(Operation.GET_NOTIFICATIONS, self);
			Message incoming = this.multicastHandler.sendAndWait(outgoing);
			switch (incoming.getOperation()) {
				case SUCCESS:
					return incoming.getDataList();
				default:
					throw new RemoteException();
			}
		} catch (TimeoutException e) {
			System.out.println("NO SERVER ANSWER!");
			throw new DataServerException();
		}
	}

	@Override
	public void notifyUser(User userToNotify, Notification notification) {

	}

	@Override
	public void delete(User self, Long lastSeenNotificationId) throws RemoteException, DataServerException {
		try {
			Message outgoing = MessageBuilder.build(Operation.DELETE_NOTIFICATIONS, self)
					.setData(new Notification().setUserId(self.getId()));
			Message incoming = this.multicastHandler.sendAndWait(outgoing);
			switch (incoming.getOperation()) {
				case SUCCESS:
					return;
				default:
					throw new RemoteException();
			}
		} catch (TimeoutException e) {
			System.out.println("NO SERVER ANSWER!");
			throw new DataServerException();
		}
	}
}
