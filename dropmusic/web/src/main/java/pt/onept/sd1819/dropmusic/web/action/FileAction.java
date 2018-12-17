package pt.onept.sd1819.dropmusic.web.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import pt.onept.sd1819.dropmusic.common.exception.DataServerException;
import pt.onept.sd1819.dropmusic.common.exception.DuplicatedException;
import pt.onept.sd1819.dropmusic.common.exception.NotFoundException;
import pt.onept.sd1819.dropmusic.common.exception.UnauthorizedException;
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

public class FileAction extends ActionSupport implements LoginAware, ModelDriven<File> {
	private File file = new File();
	private Map<String, Object> session;
	private List<File> files;


	public String linkFile()  {
		if(file.getDropBoxFileId()==null) return Action.INPUT;
		file.setOwnerId(this.getUser().getId());
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

	public String listRemote() throws Exception {
		files = this.getRemoteFiles();
		return Action.SUCCESS;
	}

	public List<Music> getMusics() {
		try {
			MusicManagerInterface musicManager = CommunicationManager.getServerInterface().music();
			return musicManager.list(this.getUser());
		} catch (DataServerException | RemoteException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public List<User> getUsers()  {
		try {
			UserManagerInterface userManager = CommunicationManager.getServerInterface().user();
			return userManager.list(this.getUser());
		} catch (DataServerException | RemoteException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public List<File> getFiles() {
		return files;
	}

	public List<File> getRemoteFiles() {
		try {
			FileManagerInterface fileManager = CommunicationManager.getServerInterface().file();
			return fileManager.listRemoteFiles(this.getUser());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return new LinkedList<>();
	}

	@Override
	public File getModel() {
		return this.file;
	}

	@Override
	public void setSession(Map<String, Object> map) {
		this.session = map;
	}

	@Override
	public Map<String, Object> getSession() {
		return this.session;
	}
}
