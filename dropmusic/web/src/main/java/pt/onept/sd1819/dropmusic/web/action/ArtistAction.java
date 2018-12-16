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
import java.util.Map;

public class ArtistAction extends ActionSupport implements LoginAware, ModelDriven<Artist> {
	private Artist artist = new Artist();
	private Map<String, Object> session;

	public String create() throws Exception{
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

	public String read() throws Exception {
		if (artist.getId()==0) return Action.INPUT;
		try {
			ArtistManagerInterface artistManager = CommunicationManager.getServerInterface().artist();
			artist = artistManager.read(this.getUser(), artist);
			addActionMessage("Artists without albums & songs where cleaned");
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

	public String update() throws Exception {
		return Action.SUCCESS;
	}

	public String delete() throws Exception {
		return Action.SUCCESS;
	}

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

	@Override
	public Artist getModel() {
		return this.artist;
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
