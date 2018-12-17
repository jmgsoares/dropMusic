package pt.onept.sd1819.dropmusic.web.servlet;

import org.json.JSONObject;
import pt.onept.sd1819.dropmusic.common.exception.DataServerException;
import pt.onept.sd1819.dropmusic.common.exception.IncompleteException;
import pt.onept.sd1819.dropmusic.common.exception.NotFoundException;
import pt.onept.sd1819.dropmusic.common.exception.UnauthorizedException;
import pt.onept.sd1819.dropmusic.common.server.contract.subcontract.OAuthProviderInterface;
import pt.onept.sd1819.dropmusic.common.server.contract.subcontract.UserManagerInterface;
import pt.onept.sd1819.dropmusic.common.server.contract.type.User;
import pt.onept.sd1819.dropmusic.web.communication.CommunicationManager;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;

/**
 * Servlet to handle user GET requests routed through the DropBox Rest API upon user login or account linking with the application
 */
@WebServlet(urlPatterns = { "/dropBoxOA20" })
public class CallBackServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String code = req.getParameter("code");
		HttpSession session = req.getSession(true);
		User user = (User) session.getAttribute("user");
		Boolean isLogged = (Boolean) session.getAttribute("logged");
		OAuthProviderInterface oAuthProvider;

		try {
			oAuthProvider = CommunicationManager.getServerInterface().oAuthProvider();
		} catch (RemoteException e) {
			e.printStackTrace();
			writeAndSendResponse(resp, ResponseType.ERROR);
			return;
		}

		if (code == null) {
			this.writeAndSendResponse(resp, ResponseType.WRONG_CALL);
			return;
		}
		String codeToTokenResponse = oAuthProvider.getAccessTokenResponse(code);

		JSONObject codeToTokenResponseJson = new JSONObject(codeToTokenResponse);

		if ( !codeToTokenResponseJson.has("access_token")) {
			this.writeAndSendResponse(resp, ResponseType.WRONG_CODE);
			return;
		}

		if(user != null && isLogged) {
			if (user.getDropBoxUid() == null) {
				user.setDropBoxToken(codeToTokenResponseJson.getString("access_token"));
				user.setDropBoxUid(codeToTokenResponseJson.getString("account_id"));
				try {
					UserManagerInterface userManager = CommunicationManager.getServerInterface().user();
					userManager.update(new User().setEditor(true), user);
				} catch (DataServerException | IncompleteException | NotFoundException | UnauthorizedException e) {
					e.printStackTrace();
				}
			}
			this.writeAndSendResponse(resp, ResponseType.REDIRECT_HOME, oAuthProvider);

		} else {
			String tokenUserId = codeToTokenResponseJson.getString("account_id");
			try {
				UserManagerInterface userManager = CommunicationManager.getServerInterface().user();
				User loggingUser = userManager.login(new User().setDropBoxUid(tokenUserId));
				session.setAttribute("user", loggingUser);
				session.setAttribute("logged", true);
			} catch (DataServerException | RemoteException | UnauthorizedException e) {
				e.printStackTrace();
			} finally {
				this.writeAndSendResponse(resp, ResponseType.REDIRECT_HOME, oAuthProvider);
			}
		}
	}

	private void writeAndSendResponse(HttpServletResponse resp, ResponseType type, OAuthProviderInterface oAuthProvider) throws IOException {
		PrintWriter writer = resp.getWriter();
		if(oAuthProvider.getLocal()) writer.println("<!DOCTYPE html><html><head><meta http-equiv=\"refresh\" content=\"0; url=http://localhost:8080/dropmusic\"></head><body><p>If your browser doesn't redirect in 5 seconds go to<a href=\"http://localhost:8080/dropmusic\">\">this page</a></p></body></html>");
		else writer.println("<!DOCTYPE html><html><head><meta http-equiv=\"refresh\" content=\"0; url=https://onept.pt:8443/dropmusic\"></head><body><p>If your browser doesn't redirect in 5 seconds go to<a href=\"https://onept.pt:8443/dropmusic\">\">this page</a></p></body></html>");
		writer.flush();
	}

	private void writeAndSendResponse(HttpServletResponse resp, ResponseType type) throws IOException {
		PrintWriter writer = resp.getWriter();
		switch (type) {
			case WRONG_CALL:
				writer.println("<html><head></head><body></h1>Wrong Call</h1><br><a href=\"login.jsp\">Go Back</a></body></html>");
				break;
			case WRONG_CODE:
				writer.println("<html><head></head><body></h1>Wrong Authorization Code</h1><br><a href=\"login.jsp\">Go Back</a></body></html>");
				break;
			case ERROR:
				writer.println("<html><head></head><body></h1>There was an error processing the request</h1></body></html>");
				break;
		}
		writer.flush();
	}

}
