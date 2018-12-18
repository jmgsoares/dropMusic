package pt.onept.sd1819.dropmusic.web.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import pt.onept.sd1819.dropmusic.common.exception.*;
import pt.onept.sd1819.dropmusic.common.server.contract.subcontract.ArtistManagerInterface;
import pt.onept.sd1819.dropmusic.common.server.contract.type.Artist;
import pt.onept.sd1819.dropmusic.web.LoginAware;
import pt.onept.sd1819.dropmusic.web.communication.CommunicationManager;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Class to handle all the related Artist actions
 * This Class will handle all the calls to the RMI server in order to perform the necessary operations
 * It uses the Artist Class as its base model
 * @see com.opensymphony.xwork2.ModelDriven
 * This class implements the LoginAware interface to use the session attributes
 * @see pt.onept.sd1819.dropmusic.web.LoginAware
 */
public class ArtistAction extends ActionSupport implements LoginAware, ModelDriven<Artist> {
	private Artist artist = new Artist();
	private List<Artist> artists;
	private Map<String, Object> session;

	/**
	 * Create Artist
	 * @return Action result
	 */
	public String create() {
		if (artist.getName()==null) return Action.INPUT;
		try {
			ArtistManagerInterface artistManager = CommunicationManager.getServerInterface().artist();
			artistManager.create(this.getUser(), artist);
			addActionMessage("Artist created successfully");
			return Action.SUCCESS;
		} catch (DuplicatedException e) {
			addActionError("The artist already exists");
			return Action.INPUT;
		} catch (IncompleteException e) {
			addActionError("Some data fields where left empty");
			return Action.INPUT;
		} catch (RemoteException | DataServerException e) {
			e.printStackTrace();
			addActionError("There was an error around here");
			return Action.ERROR;
		} catch (UnauthorizedException e) {
			addActionError("You lack the permissions to perform the operation");
			return Action.ERROR;
		}
	}

	/**
	 * Read Artist
	 * @return Action result
	 */
	public String read() {
		session.remove("object");
		if (artist.getId()==0) return Action.INPUT;
		try {
			ArtistManagerInterface artistManager = CommunicationManager.getServerInterface().artist();
			artist = artistManager.read(this.getUser(), artist);
			session.put("object", artist);
			return Action.SUCCESS;
		} catch (RemoteException | DataServerException e) {
			e.printStackTrace();
			addActionError("There was an error around here");
			return Action.ERROR;
		} catch (NotFoundException e) {
			addActionError("Artist not found");
			return Action.ERROR;
		} catch (UnauthorizedException e) {
			addActionError("You lack the permissions to perform the operation");
			return Action.ERROR;
		}
	}

	/**
	 * Updates a specific artist
	 * @return Action result
	 */
	public String update() {
		if (artist.getId() == 0) return Action.INPUT;
		try {
			ArtistManagerInterface artistManager = CommunicationManager.getServerInterface().artist();
			artistManager.update(this.getUser(), artist);
			addActionMessage("Artist updated successfully");
			return Action.SUCCESS;
		} catch (NotFoundException e) {
			addActionError("The Artist wasn't found");
			return Action.INPUT;
		} catch (IncompleteException e) {
			addActionError("Some data fields where left empty");
			return Action.INPUT;
		} catch (RemoteException | DataServerException e) {
			e.printStackTrace();
			addActionError("An error happened around here");
			return Action.ERROR;
		} catch (UnauthorizedException e) {
			addActionError("You lack the permissions to perform the operation");
			return Action.ERROR;
		}
	}

	/**
	 * @deprecated
	 * Not implemented
	 * @return Action Result
	 */
	public String delete() {
		return Action.SUCCESS;
	}

	/**
	 * Cleans the DB by removing all the artists that aren't connected to any album or song
	 * @return Action result
	 */
	public String clean() {
		try {
			ArtistManagerInterface artistManager = CommunicationManager.getServerInterface().artist();
			artistManager.clean(this.getUser());
			addActionMessage("Artists cleaned successfully");
			return Action.SUCCESS;
		} catch (DataServerException | RemoteException e) {
			e.printStackTrace();
			addActionError("There was an error around here");
			return Action.ERROR;
		}
	}

	/**
	 * Lists all the Artists
	 * @return Action result
	 */
	public String list() {
		try {
			ArtistManagerInterface artistManager = CommunicationManager.getServerInterface().artist();
			this.artists = artistManager.list(this.getUser());
			return Action.SUCCESS;
		} catch (DataServerException | RemoteException e) {
			e.printStackTrace();
			addActionError("There was an error around here");
			return Action.ERROR;
		}
	}

	/**
	 * Getter for the artists parameter
	 * @return the artists parameter
	 */
	public List<Artist> getArtists() {
		return artists;
	}

	/**
	 * Getter for the model of this Action Class (in this case, Artist)
	 * @return artist parameter
	 * @see ModelDriven
	 */
	@Override
	public Artist getModel() {
		return this.artist;
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
	 * Getter for session
	 * @return the session map
	 * @see pt.onept.sd1819.dropmusic.web.LoginAware
	 */
	@Override
	public Map<String, Object> getSession() {
		return this.session;
	}
}
