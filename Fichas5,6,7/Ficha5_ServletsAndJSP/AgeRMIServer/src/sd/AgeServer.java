package sd;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;

public class AgeServer extends UnicastRemoteObject implements IAgeServer {
	private static final long serialVersionUID = 3486256653329270106L;
	
	private Properties properties = new Properties();

	protected AgeServer() throws RemoteException, IOException
	{
		super();
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("db.txt");
		properties.load(in);
	}

	@Override
	public Integer findAgeOf(String name) throws RemoteException {
		Object value = this.properties.get(name);
		if (value != null) {
			return Integer.valueOf(String.valueOf(value));
		}
		return -1;
	}

	public static void main(String[] args)
	{
	 // For simplification purposes we are not using a policy file in this example
		try
		{
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.rebind("ageServer", (IAgeServer) new AgeServer());
			System.out.println("Rmi Server Running...");
		}
		catch (AccessException e)
		{
			e.printStackTrace();
		}
		catch (RemoteException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
