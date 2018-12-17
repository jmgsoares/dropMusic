package pt.onept.sd1819.dropmusic.web.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import pt.onept.sd1819.dropmusic.web.communication.CommunicationManager;

import java.rmi.RemoteException;

/**
 * Class to handle the index (default action), in order to always provide a refreshed auth url
 * This Class will handle all the calls to the RMI server in order to perform the necessary operations
 */
public class IndexAction extends ActionSupport {

	@Override
	public String execute() {
		return Action.SUCCESS;
	}

	/**
	 * Function to get the OAuth Authorization URL
	 * @return the AuthUrl
	 */
	public String getoAuthUrl() {
		try {
			return CommunicationManager.getServerInterface().oAuthProvider().getAuthorizationUrl();
		} catch (RemoteException e) {
			e.printStackTrace();
			e.printStackTrace();
			addActionError("There was an error around here");
			return Action.ERROR;
		}
	}
}
