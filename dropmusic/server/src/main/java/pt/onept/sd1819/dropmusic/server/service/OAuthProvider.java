package pt.onept.sd1819.dropmusic.server.service;

import pt.onept.sd1819.dropmusic.common.server.contract.subcontract.OAuthProviderInterface;
import pt.onept.sd1819.dropmusic.server.service.rest.DropBox20;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * This class implements the OAuthProviderInterface for the DropBox20 API
 */
public class OAuthProvider extends UnicastRemoteObject implements OAuthProviderInterface {

	public OAuthProvider() throws RemoteException {
		super();
	}

	/**
	 * gets the authorization URL
	 * @return the authorization Url
	 * @throws RemoteException if any RMI error is raised
	 */
	public String getAuthorizationUrl() throws RemoteException {
		return DropBox20.getAuthorizationUrl();
	}

	/**
	 * gets the access token by exchanging the authorization code provided
	 * @param authorizationCode the authorization code to exchange
	 * @return the access token
	 * @throws RemoteException if any RMI error is raised
	 */
	public String getAccessTokenResponse(String authorizationCode) throws RemoteException {
		return DropBox20.getAccessTokenResponse(authorizationCode).toString();
	}

	/**
	 * Helper function to get the if we're in test or production environment
	 * @return true is test - false if production
	 * @throws RemoteException if any RMI error is raised
	 */
	public Boolean getLocal() throws RemoteException {
		return DropBox20.getLocal();
	}

}
