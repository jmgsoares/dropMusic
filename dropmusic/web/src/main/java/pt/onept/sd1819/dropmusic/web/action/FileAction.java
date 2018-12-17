package pt.onept.sd1819.dropmusic.web.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import pt.onept.sd1819.dropmusic.common.exception.*;
import pt.onept.sd1819.dropmusic.common.server.contract.subcontract.FileManagerInterface;
import pt.onept.sd1819.dropmusic.common.server.contract.subcontract.MusicManagerInterface;
import pt.onept.sd1819.dropmusic.common.server.contract.subcontract.UserManagerInterface;
import pt.onept.sd1819.dropmusic.common.server.contract.type.File;
import pt.onept.sd1819.dropmusic.common.server.contract.type.Music;
import pt.onept.sd1819.dropmusic.common.server.contract.type.User;
import pt.onept.sd1819.dropmusic.web.LoginAware;
import pt.onept.sd1819.dropmusic.web.communication.CommunicationManager;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Class to handle all the related File actions
 * This Class will handle all the calls to the RMI server in order to perform the necessary operations
 * We refer as remote files (the files in the DropBox) and local files the files that an user already linked
 * It uses the File Class as its base model
 * @see com.opensymphony.xwork2.ModelDriven
 * This class implements the LoginAware interface to use the session attributes
 * @see pt.onept.sd1819.dropmusic.web.LoginAware
 */
public class FileAction extends ActionSupport implements LoginAware, ModelDriven<File> {
	private File file = new File();
	private Map<String, Object> session;
	private List<File> files;
	private User targetUser = new User();

	/**
	 * Action to link a remote () file with a local file
	 * @return Action result
	 */
	public String linkFile()  {
		if(file.getDropBoxFileId()==null) return Action.INPUT;
		file.setOwnerId(this.getUser().getId());
		file.setMusicName(getMusicName(file.getMusicId()));
		try {
			FileManagerInterface fileManager = CommunicationManager.getServerInterface().file();
			fileManager.linkRemoteFile(this.getUser(), file);
			addActionMessage("The file was linked successfully");
			return Action.SUCCESS;
		} catch (NotFoundException | RemoteException | DataServerException | UnauthorizedException e) {
			e.printStackTrace();
			addActionError("There was an error around here");
			return Action.ERROR;
		} catch (DuplicatedException e) {
			e.printStackTrace();
			addActionError("That file is already associated with another music");
			return Action.ERROR;
		}
	}

	/**
	 * Action to list all the remote (DropBox) files
	 * @return Action Result
	 */
	public String listRemote() {
		files = this.getRemoteFiles();
		return Action.SUCCESS;
	}

	/**
	 * Action to list all the remote (DropBox) files
	 * @return Action Result
	 */
	public String listLocal() {
		try {
			FileManagerInterface fileManager = CommunicationManager.getServerInterface().file();
			this.files = fileManager.list(this.getUser());
			return Action.SUCCESS;
		} catch (DataServerException | RemoteException e) {
			e.printStackTrace();
			addActionError("There was an error around here");
			return Action.ERROR;
		}
	}

	/**
	 * Action to list all the files that where shared with the user
	 * @return Action Result
	 */
	public String listShares() {
		try {
			FileManagerInterface fileManager = CommunicationManager.getServerInterface().file();
			this.files = fileManager.listSharedFiles(this.getUser());
			return Action.SUCCESS;
		} catch (RemoteException | DataServerException e) {
			e.printStackTrace();
			addActionError("There was an error around here");
			return Action.ERROR;
		}
	}

	/**
	 * Action to share a file with a user
	 * @return Action Result
	 */
	public String share(){
		if (this.targetUser.getId() == 0) return Action.INPUT;
		try {
			FileManagerInterface fileManager = CommunicationManager.getServerInterface().file();
			fileManager.shareFile(this.getUser(), this.file, this.targetUser);
			addActionMessage("File shared successfully");
			return Action.SUCCESS;
		} catch (DataServerException | RemoteException e) {
			e.printStackTrace();
			addActionError("There was an error around here");
			return Action.ERROR;
		} catch (OAuthException e) {
			e.printStackTrace();
			addActionError("The user selected doesn't have a dropbox Account Linked");
			return Action.ERROR;
		}
	}

	/**
	 * Action to support the playing of a song inside the application
	 * @return Action Result (will always be success)
	 */
	public String play() { return Action.SUCCESS; }

	/**
	 * Getter for the parameter targetUser
	 * @return the the targetUser parameter
	 */
	public User getTargetUser() {
		return targetUser;
	}

	/**
	 * Support function to retrieve all of the app musics
	 * @return A List with all the musics of the application
	 */
	public List<Music> getMusics() {
		try {
			MusicManagerInterface musicManager = CommunicationManager.getServerInterface().music();
			return musicManager.list(this.getUser());
		} catch (DataServerException | RemoteException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * Support function to retrieve the name of a specific Music
	 * @param id of the music to get the name
	 * @return the name of the music
	 */
	public String getMusicName(int id) {
		Music music = new Music().setId(id);
		try {
			MusicManagerInterface musicManager = CommunicationManager.getServerInterface().music();
			music = musicManager.read(this.getUser(), music);
			return music.getName();
		} catch (RemoteException | DataServerException | NotFoundException | UnauthorizedException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Support function to retrieve all of the app users
	 * @return A List with all the users of the application
	 */
	public List<User> getUsers()  {
		try {
			UserManagerInterface userManager = CommunicationManager.getServerInterface().user();
			return userManager.list(this.getUser());
		} catch (DataServerException | RemoteException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * Getter
	 * @return
	 */
	public List<File> getFiles() {
		return files;
	}

	/**
	 * Support function to retrieve all remote files of a user
	 * @return A List with all the remote user files
	 */
	public List<File> getRemoteFiles() {
		try {
			FileManagerInterface fileManager = CommunicationManager.getServerInterface().file();
			return fileManager.listRemoteFiles(this.getUser());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return new LinkedList<>();
	}

	/**
	 * Support function to retrieve all local files of a user
	 * @return A List with all the local user files
	 */
	public List<File> getLocalFiles() {
		try {
			FileManagerInterface fileManager = CommunicationManager.getServerInterface().file();
			return fileManager.list(this.getUser());
		} catch (DataServerException | RemoteException e) {
			e.printStackTrace();
		}
		return new LinkedList<>();
	}

	/**
	 * Getter for the model of this Action Class (in this case, Artist)
	 * @return artist parameter
	 * @see ModelDriven
	 */
	@Override
	public File getModel() {
		return this.file;
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
