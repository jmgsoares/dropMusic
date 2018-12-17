package pt.onept.sd1819.dropmusic.common.server.contract.subcontract;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Manage the platform oath calss
 *
 * @author João Soares
 * @version 1e-1024
 */
public interface OAuthProviderInterface extends Remote, Serializable {

	Boolean getLocal() throws RemoteException;

	String getAccessTokenResponse(String authorizationCode) throws RemoteException;

	String getAuthorizationUrl() throws RemoteException;
}
