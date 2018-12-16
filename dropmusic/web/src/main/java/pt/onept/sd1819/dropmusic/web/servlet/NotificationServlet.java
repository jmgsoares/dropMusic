package pt.onept.sd1819.dropmusic.web.servlet;

import pt.onept.sd1819.dropmusic.common.exception.DataServerException;
import pt.onept.sd1819.dropmusic.common.server.contract.type.Notification;
import pt.onept.sd1819.dropmusic.common.server.contract.type.User;
import pt.onept.sd1819.dropmusic.web.communication.CommunicationManager;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.UUID;

@ServerEndpoint(value = "/notification", configurator = GetHttpSessionConfigurator.class)
public class NotificationServlet {
	private UUID subscriptionId;
	private RemoteEndpoint.Basic endpoint;
	private User user;

	public NotificationServlet()  {
	}

	@OnOpen
	public void open(Session sessionInput) {
		this.user = getUser(sessionInput);

		if(user == null) {
			close();
			return;
		}
		this.endpoint = sessionInput.getBasicRemote();

		try {
			subscriptionId = CommunicationManager.getServerInterface().notification().subscribe(this.user.getId(), new ClientNotifier(this));
		} catch (IOException | DataServerException e) {
			e.printStackTrace();
		}


	}

	@OnClose
	public void close() {
		try {
			CommunicationManager.getServerInterface().notification().unSubscribe(this.user.getId(), this.subscriptionId);
		} catch (RemoteException | DataServerException e) {
			e.printStackTrace();
		}
	}

	@OnMessage
	public void message(String message) {

	}

	@OnError
	public void handleError(Throwable t) {
		t.printStackTrace();
	}

	private static boolean isLogged(Session sessionInput) {
		HttpSession session = (HttpSession) sessionInput.getUserProperties().get("session");
		Boolean isLogged = (Boolean) session.getAttribute("logged");
		return isLogged != null && isLogged;
	}

	private static User getUser(Session sessionInput) {
		if( NotificationServlet.isLogged(sessionInput) ) {
			HttpSession session = (HttpSession) sessionInput.getUserProperties().get("session");
			return (User) session.getAttribute("user");
		}
		return null;
	}

	public boolean notify(Notification notification) {
		try {
			this.endpoint.sendText(notification.getMessage());
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
