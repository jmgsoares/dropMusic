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

/**
 * Class to handle all the search action
 * This Class will handle all the calls to the RMI server in order to perform the necessary operations
 * This class implements the LoginAware interface to use the session attributes
 * @see pt.onept.sd1819.dropmusic.web.LoginAware
 */
public class SearchAction extends ActionSupport implements LoginAware {
	private String query = "";
	private Map<String, Object> session;
	private List<Album> albums;

	/**
	 * Search action
	 * @return Action result
	 * @throws Exception
	 */
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

	/**
	 * Getter
	 * @return albums parameter
	 */
	public List<Album> getAlbums() {
		return albums;
	}

	/**
	 * Setter
	 * @param query search query to set
	 * @return the object (builder setter)
	 */
	public SearchAction setQuery(String query) {
		this.query = query;
		return this;
	}

	/**
	 * Setter for session
	 * @param map session map
	 * @see pt.onept.sd1819.dropmusic.web.LoginAware
	 */
	@Override
	public void setSession(Map<String, Object> map) {
		this.session = map;
	}

	/**
	 * Getter
	 * @return query parameter
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * Getter for session
	 * @return the session map
	 * @see pt.onept.sd1819.dropmusic.web.LoginAware
	 */
	@Override
	public Map<String, Object> getSession() {
		return this.session;
	}
}
