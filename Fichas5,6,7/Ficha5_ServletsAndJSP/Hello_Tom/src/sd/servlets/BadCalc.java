package sd.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/BadCalc" })
public class BadCalc extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		if (request.getParameter("n1") != null) {
			int n1 = Integer.parseInt(request.getParameter("n1"));
			int n2 = Integer.parseInt(request.getParameter("n2"));
			int ans = n1 + n2;
			
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.write("Sum of <b>" + n1 + "</b> and <b>" + n2 + "</b> is: <b>" + ans + "</b>.");
		} else {
			PrintWriter writer = response.getWriter();
			writer.println("<html><form method='GET' action='?'><input name='n1' type='number' /> + <input name='n2' type='number' /><input type='submit' /></form></html>");
			writer.flush();	
		}
	}
}
