package pt.onept.sd1819.dropmusic.server.service;

import pt.onept.sd1819.dropmusic.common.server.contract.subcontract.DropBoxRestManagerInterface;
import pt.onept.sd1819.dropmusic.server.service.rest.DropBox20;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DropBoxManager extends UnicastRemoteObject implements DropBoxRestManagerInterface {

	public DropBoxManager() throws RemoteException {
		super();
	}

	public String getAuthorizationUrl() throws RemoteException {
		return DropBox20.getAuthorizationUrl();
	}

	public String getAccessTokenResponse(String authorizationCode) throws RemoteException {
		return DropBox20.getAccessTokenResponse(authorizationCode).toString();
	}

	public Boolean getLocal() throws RemoteException {
		return DropBox20.getLocal();
	}

}
