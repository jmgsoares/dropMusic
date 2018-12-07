package sd.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/Age" })
public class Age extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Properties properties = new Properties();
	
	@Override
	public void init() throws ServletException {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("db.txt");
		try {
			properties.load(in);
		} catch (IOException e) {
			throw new ServletException(e);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter writer = response.getWriter();
		
		String name = request.getParameter("name");
		if (name == null) {
			writer.println("<html><form method='GET' action='?'><input name='name' /><input type='submit' /></form></html>");	
		} else {
			String value = (String) properties.get(name);
			if (value == null) {
				writer.println("Name " + name + " not found in database.");
			} else {
				writer.println("Name " + name + " is " + value + " years old.");
			}
		}
		writer.flush();
	}
}
