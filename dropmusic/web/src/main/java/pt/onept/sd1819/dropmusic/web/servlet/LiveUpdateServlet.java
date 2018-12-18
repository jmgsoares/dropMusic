package pt.onept.sd1819.dropmusic.web.servlet;

import pt.onept.sd1819.dropmusic.common.server.contract.type.DropmusicDataType;
import pt.onept.sd1819.dropmusic.common.server.contract.type.User;
import pt.onept.sd1819.dropmusic.web.communication.CommunicationManager;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.UUID;

/**
 * Servlet to handle the WebSocket connection
 */
@ServerEndpoint(value = "/liveUpdate", configurator = GetHttpSessionConfigurator.class)
public class LiveUpdateServlet {
	private UUID subscriptionId;
	private RemoteEndpoint.Basic endpoint;
	private User user;
	private Session session;

	public LiveUpdateServlet()  {
	}

	/**
	 * On open subscribes the notifications for the current user in session
	 * @param session user session
	 */
	@OnOpen
	public void open(Session session) {
		this.session = session;
		this.user = getUser(session);
		if(user == null) {
			close();
			return;
		}
		this.endpoint = session.getBasicRemote();
		try {
			subscriptionId = CommunicationManager.getServerInterface().update().subscribe(
					getBean(session),
					new ClientUpdater(this));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * On close unsubscribes the notifications
	 */
	@OnClose
	public void close() {
		if (this.user == null) return;
		try {
			CommunicationManager.getServerInterface().update().unSubscribe(getBean(this.session), this.subscriptionId);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@OnMessage
	public void message(String message) {
	}

	/**
	 * On error unsubscribes the client
	 * @param t error
	 */
	@OnError
	public void handleError(Throwable t) {
		close();
		t.printStackTrace();
	}

	/**
	 * Function to return the login status of the session
	 * @param sessionInput user session
	 * @return user login status
	 */
	private static boolean isLogged(Session sessionInput) {
		HttpSession session = (HttpSession) sessionInput.getUserProperties().get("session");
		Boolean isLogged = (Boolean) session.getAttribute("logged");
		return isLogged != null && isLogged;
	}

	/**
	 * Function to retrieve the User from the session
	 * @param sessionInput user session
	 * @return the user stored in session
	 */
	private static User getUser(Session sessionInput) {
		if( LiveUpdateServlet.isLogged(sessionInput) ) {
			HttpSession session = (HttpSession) sessionInput.getUserProperties().get("session");
			return (User) session.getAttribute("user");
		}
		return null;
	}

	/**
	 * Function to retrieve bean from user session
	 * @param sessionInput user session
	 * @param <T> Type
	 * @return bean object
	 */
	private static <T extends DropmusicDataType> T getBean(Session sessionInput) {
		if( LiveUpdateServlet.isLogged(sessionInput) ) {
			HttpSession session = (HttpSession) sessionInput.getUserProperties().get("session");
			return (T) session.getAttribute("bean");
		}
		return null;
	}

	/**
	 * Function to send the notifications to the user
	 * @param object updated object
	 * @return success of the operation
	 */
	public boolean update(DropmusicDataType object) {
		try {
			this.endpoint.sendText(object.toString());
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
