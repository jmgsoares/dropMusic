package sd.jspsupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/login/AddMessage" })
public class AddMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static List<String> messages = new ArrayList<>();

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if (request.getParameter("who") != null) {
			String what = request.getParameter("what");
			String who = request.getParameter("who");
			messages.add(who + ": " + what);
		} else {
			System.out.println("failed to get Parameter");
		}
		response.getOutputStream().flush();
	}
}
