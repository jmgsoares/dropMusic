package pt.onept.sd1819.dropmusic.web.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import pt.onept.sd1819.dropmusic.web.communication.CommunicationManager;

import java.rmi.RemoteException;

public class IndexAction extends ActionSupport {

	@Override
	public String execute() throws Exception {
		return Action.SUCCESS;
	}

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
