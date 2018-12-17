package pt.onept.sd1819.dropmusic.web.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import pt.onept.sd1819.dropmusic.common.exception.*;
import pt.onept.sd1819.dropmusic.common.server.contract.subcontract.AlbumManagerInterface;
import pt.onept.sd1819.dropmusic.common.server.contract.subcontract.MusicManagerInterface;
import pt.onept.sd1819.dropmusic.common.server.contract.type.Album;
import pt.onept.sd1819.dropmusic.common.server.contract.type.Music;
import pt.onept.sd1819.dropmusic.web.LoginAware;
import pt.onept.sd1819.dropmusic.web.communication.CommunicationManager;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Class to handle all the related music actions
 * This Class will handle all the calls to the RMI server in order to perform the necessary operations
 * It uses the Music Class as its base model
 * @see com.opensymphony.xwork2.ModelDriven
 * This class implements the LoginAware interface to use the session attributes
 * @see pt.onept.sd1819.dropmusic.web.LoginAware
 */
public class MusicAction extends ActionSupport implements LoginAware, ModelDriven<Music> {
	private Music music = new Music();
	private List<Music> musics;
	private Map<String, Object> session;

	/**
	 * Create Music
	 * @return Action result
	 */
	public String create() {
		if (music.getName()==null) return Action.INPUT;
		try {
			MusicManagerInterface musicManager = CommunicationManager.getServerInterface().music();
			musicManager.create(this.getUser(), music);
			addActionMessage("Music created successfully");
			return Action.SUCCESS;
		} catch (DuplicatedException e) {
			addActionError("The Music already exists");
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
	 * Read Music
	 * @return Action result
	 */
	public String read() {
		if (music.getId()==0) return Action.INPUT;
		try {
			MusicManagerInterface musicManager = CommunicationManager.getServerInterface().music();
			music = musicManager.read(this.getUser(), music);
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
	 * Updates a specific music
	 * @return Action result
	 */
	public String update() {
		if (music.getId() == 0) return Action.INPUT;
		try {
			MusicManagerInterface musicManager = CommunicationManager.getServerInterface().music();
			musicManager.update(this.getUser(), music);
			addActionMessage("Music updated successfully");
			return Action.SUCCESS;
		} catch (NotFoundException e) {
			addActionError("The Music already exists");
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
	 * Lists all the musics
	 * @return Action result
	 */
	public String list() {
		try {
			MusicManagerInterface musicManager = CommunicationManager.getServerInterface().music();
			this.musics = musicManager.list(this.getUser());
			return Action.SUCCESS;
		} catch (DataServerException | RemoteException e) {
			e.printStackTrace();
			addActionError("There was an error around here");
			return Action.ERROR;
		}
	}

	/**
	 * Getter
	 * @return musics parameter
	 */
	public List<Music> getMusics() {
		return musics;
	}

	/**
	 * Support function to retrieve all of the app albums
	 * @return A List with all the artists
	 */
	public List<Album> getAlbumList() {
		try {
			AlbumManagerInterface albumManager = CommunicationManager.getServerInterface().album();
			return albumManager.list(this.getUser());
		} catch (DataServerException | RemoteException e) {
			e.printStackTrace();
		}
		return new LinkedList<>();
	}

	/**
	 * Getter for the model of this Action Class (in this case, Album)
	 * @return album parameter
	 * @see ModelDriven
	 */
	@Override
	public Music getModel() {
		return this.music;
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
