package pt.onept.sd1819.dropmusic.web.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import pt.onept.sd1819.dropmusic.common.exception.*;
import pt.onept.sd1819.dropmusic.common.server.contract.subcontract.AlbumManagerInterface;
import pt.onept.sd1819.dropmusic.common.server.contract.subcontract.ArtistManagerInterface;
import pt.onept.sd1819.dropmusic.common.server.contract.subcontract.ReviewManagerInterface;
import pt.onept.sd1819.dropmusic.common.server.contract.type.Album;
import pt.onept.sd1819.dropmusic.common.server.contract.type.Artist;
import pt.onept.sd1819.dropmusic.common.server.contract.type.Review;
import pt.onept.sd1819.dropmusic.web.LoginAware;
import pt.onept.sd1819.dropmusic.web.communication.CommunicationManager;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Class to handle all the related album actions
 * This Class will handle all the calls to the RMI server in order to perform the necessary operations
 * It uses the Album Class as its base model
 * @see com.opensymphony.xwork2.ModelDriven
 * This class implements the LoginAware interface to use the session attributes
 * @see pt.onept.sd1819.dropmusic.web.LoginAware
 */
public class AlbumAction extends ActionSupport implements LoginAware, ModelDriven<Album> {
	private Album album = new Album();
	private List<Album> albums;
	private Map<String, Object> session;
	private List<Artist> artistList;
	private Review review = new Review();

	/**
	 * Create Album
	 * @return Action result
	 */
	public String create() {
		if (album.getName()==null) return Action.INPUT;
		try {
			//Gets RMI Album Manager Server Interface to send the information
			AlbumManagerInterface albumManager = CommunicationManager.getServerInterface().album();
			albumManager.create(this.getUser(), album);
			addActionMessage("Album created successfully");
			return Action.SUCCESS;
		} catch (DuplicatedException e) {
			addActionError("The album already exists");
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
	 * Read Album
	 * @return Action result
	 */
	public String read() {
		if (album.getId()==0) return Action.INPUT;
		try {
			AlbumManagerInterface albumManager = CommunicationManager.getServerInterface().album();
			album = albumManager.read(this.getUser(), album);
			return Action.SUCCESS;
		} catch (RemoteException | DataServerException e) {
			e.printStackTrace();
			addActionError("There was an error around here");
			return Action.ERROR;
		} catch (NotFoundException e) {
			addActionError("Album not found");
			return Action.ERROR;
		} catch (UnauthorizedException e) {
			addActionError("You lack the permissions to perform the operation");
			return Action.ERROR;
		}
	}

	/**
	 * Lists all the Albums
	 * @return Action result
	 */
	public String list() {
		try {
			AlbumManagerInterface albumManager  = CommunicationManager.getServerInterface().album();
			this.albums = albumManager.list(this.getUser());
			return Action.SUCCESS;
		} catch (DataServerException | RemoteException e) {
			e.printStackTrace();
			addActionError("There was an error around here");
			return Action.ERROR;
		}
	}

	/**
	 * Updates a specific album
	 * @return Action result
	 */
	public String update() {
		if (album.getId() == 0) return Action.INPUT;
		try {
			AlbumManagerInterface albumManager = CommunicationManager.getServerInterface().album();
			albumManager.update(this.getUser(), album);
			addActionMessage("Album updated successfully");
			return Action.SUCCESS;
		} catch (NotFoundException e) {
			addActionError("The album already exists");
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
	 * @return
	 * @throws Exception
	 */
	public String delete() {
		return Action.SUCCESS;
	}

	/**
	 * Inserts a review into an album
	 * @return Action Result
	 */
	public String review()  {
		if(review.getReview()==null | review.getReview().equals("") | review.getScore()<0 | review.getScore()>5 ) {
			addActionError("Some data fields where improperly set (Review score 0-5 + Text)");
			return Action.INPUT;
		}
		try {
			ReviewManagerInterface reviewManager = CommunicationManager.getServerInterface().review();
			reviewManager.add(this.getUser(), review);
			addActionMessage("Review added successfully");
			return Action.SUCCESS;
		} catch (DataServerException | RemoteException e) {
			e.printStackTrace();
			addActionError("There was an error around here");
			return Action.ERROR;
		} catch (IncompleteException e) {
			addActionError("Some data fields where improperly set (Review score 0-5 + Text)");
			return Action.INPUT;
		}
	}

	/**
	 * Support function to retrieve all of the app artists
	 * @return A List with all the artists
	 */
	public List<Artist> getArtistList() {
		try {
			ArtistManagerInterface artistManager = CommunicationManager.getServerInterface().artist();
			return artistManager.list(this.getUser());
		} catch (DataServerException | RemoteException e) {
			e.printStackTrace();
		}
		return new LinkedList<>();
	}

	/**
	 * Support function to retrieve all of the app albums
	 * @return A List with all the artists
	 */
	public List<Album> getAlbums() {
		return albums;
	}

	/**
	 * Getter for the model of this Action Class (in this case, Album)
	 * @return album parameter
	 * @see ModelDriven
	 */
	@Override
	public Album getModel() {
		return this.album;
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

	/**
	 * Getter for the review
	 * @return the review parameter
	 */
	public Review getReview() {
		return this.review;
	}
}
