package pt.onept.sd1819.dropmusic.web.servlet;

import org.apache.struts2.interceptor.SessionAware;
import org.json.JSONObject;
import pt.onept.sd1819.rest.dropbox.DropBoxRestService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(urlPatterns = { "/CallBackServlet" })
public class CallBackServlet extends HttpServlet implements SessionAware {
	private Map<String, Object> session;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String code = req.getParameter("code");
		JSONObject test = DropBoxRestService.getAccessTokenResponse(code);
		PrintWriter writer = resp.getWriter();
		writer.println("<html><head></head><body></h1>" + test.toString(5) + "</h1></body></html>");
		writer.flush();

	}

	@Override
	public void setSession(Map<String, Object> session) { this.session = session; }

	}

