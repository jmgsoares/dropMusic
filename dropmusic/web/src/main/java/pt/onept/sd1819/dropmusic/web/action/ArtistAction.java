package pt.onept.sd1819.dropmusic.web.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.interceptor.SessionAware;
import pt.onept.sd1819.dropmusic.common.exception.DataServerException;
import pt.onept.sd1819.dropmusic.common.exception.DuplicatedException;
import pt.onept.sd1819.dropmusic.common.exception.IncompleteException;
import pt.onept.sd1819.dropmusic.common.exception.UnauthorizedException;
import pt.onept.sd1819.dropmusic.common.server.contract.subcontract.ArtistManagerInterface;
import pt.onept.sd1819.dropmusic.common.server.contract.type.Artist;
import pt.onept.sd1819.dropmusic.common.server.contract.type.User;
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
			return Action.ERROR;
		} catch (UnauthorizedException e) {
			addActionError("You lack the permissions to perform the operation");
			return Action.ERROR;
		}
	}

	public String read() throws Exception {
		return Action.SUCCESS;
	}

	public String update() throws Exception {
		return Action.SUCCESS;
	}

	public String delete() throws Exception {
		return Action.SUCCESS;
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
