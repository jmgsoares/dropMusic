package sd.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sd.IAgeServer;

@WebServlet(urlPatterns = { "/AgeRemote" })
public class AgeRemote extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private IAgeServer ageServer;
	
	@Override
	public void init() throws ServletException {
		try {
			Registry registry = LocateRegistry.getRegistry(1099);
			ageServer = (IAgeServer) registry.lookup("ageServer");
		} catch (AccessException e) {
			throw new ServletException(e);
		} catch (RemoteException e) {
			throw new ServletException(e);
		} catch (NotBoundException e) {
			throw new ServletException(e);
		}
	}
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
PrintWriter writer = response.getWriter();
		
		String name = request.getParameter("name");
		if (name == null) {
			writer.println("<html><form method='GET' action='?'><input name='name' /><input type='submit' /></form></html>");	
		} else {
			Integer value = ageServer.findAgeOf(name);
			if (value < 1) {
				writer.println("Name " + name + " not found in database.");
			} else {
				writer.println("Name " + name + " is " + value + " years old.");
			}
		}
		writer.flush();
	}
	
}
