package pt.onept.sd1819.dropmusic.web.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import pt.onept.sd1819.dropmusic.common.exception.DataServerException;
import pt.onept.sd1819.dropmusic.common.exception.NotFoundException;
import pt.onept.sd1819.dropmusic.common.exception.UnauthorizedException;
import pt.onept.sd1819.dropmusic.common.server.contract.subcontract.MusicManagerInterface;
import pt.onept.sd1819.dropmusic.common.server.contract.type.Music;
import pt.onept.sd1819.dropmusic.web.LoginAware;
import pt.onept.sd1819.dropmusic.web.communication.CommunicationManager;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public class MusicAction extends ActionSupport implements LoginAware, ModelDriven<Music> {
	private Music music = new Music();
	private List<Music> musics;
	private Map<String, Object> session;

	public String read() throws Exception {
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

	public List<Music> getMusics() {
		return musics;
	}

	@Override
	public Music getModel() {
		return this.music;
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
