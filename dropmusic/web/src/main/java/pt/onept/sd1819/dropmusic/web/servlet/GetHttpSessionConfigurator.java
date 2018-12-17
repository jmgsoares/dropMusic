package pt.onept.sd1819.dropmusic.web.servlet;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * Configurator to retrieve the user session on upgrading the connection to a WebSocket
 */
public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator {
	@Override
	public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
		config.getUserProperties().put("session", (HttpSession)request.getHttpSession());
	}
}