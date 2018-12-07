package sd;

import java.rmi.RemoteException;
import java.rmi.Remote;

public interface IAgeServer extends Remote {
	public Integer findAgeOf(String name) throws RemoteException;
}
