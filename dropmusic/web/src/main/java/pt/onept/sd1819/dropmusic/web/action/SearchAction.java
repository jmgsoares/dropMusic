package pt.onept.sd1819.dropmusic.web.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import pt.onept.sd1819.dropmusic.common.exception.DataServerException;
import pt.onept.sd1819.dropmusic.common.server.contract.type.Album;
import pt.onept.sd1819.dropmusic.web.LoginAware;
import pt.onept.sd1819.dropmusic.web.communication.CommunicationManager;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public class SearchAction extends ActionSupport implements LoginAware {
	private String query = "";
	private Map<String, Object> session;
	private List<Album> albums;

	@Override
	public String execute() throws Exception {
		if (query == null || query.isEmpty()) return Action.INPUT;
		try {
			this.albums = CommunicationManager.getServerInterface().album().search(this.getUser(), query);
			return Action.SUCCESS;
		} catch (RemoteException | DataServerException e) {
			e.printStackTrace();
			addActionError("There was an error around here");
		}
		return Action.ERROR;
	}

	public List<Album> getAlbums() {
		return albums;
	}

	public SearchAction setQuery(String query) {
		this.query = query;
		return this;
	}

	@Override
	public void setSession(Map<String, Object> map) {
		this.session = map;
	}

	public String getQuery() {
		return query;
	}

	@Override
	public Map<String, Object> getSession() {
		return this.session;
	}
}
