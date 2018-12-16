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

public class AlbumAction extends ActionSupport implements LoginAware, ModelDriven<Album> {
	private Album album = new Album();
	private List<Album> albums;
	private Map<String, Object> session;
	private List<Artist> artistList;
	private Review review = new Review();

	public String create() throws Exception {
		if (album.getName()==null) return Action.INPUT;
		try {
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
			return Action.ERROR;
		} catch (UnauthorizedException e) {
			addActionError("You lack the permissions to perform the operation");
			return Action.ERROR;
		}
	}

	public String read() throws Exception {
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

	public String update() throws Exception {
		if (album.getId()==0) return Action.INPUT;
		return Action.SUCCESS;
	}

	public String delete() throws Exception {
		return Action.SUCCESS;
	}

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

	public List<Artist> getArtistList() {
		try {
			ArtistManagerInterface artistManager = CommunicationManager.getServerInterface().artist();
			return artistManager.list(this.getUser());
		} catch (DataServerException | RemoteException e) {
			e.printStackTrace();
		}
		return new LinkedList<>();
	}

	public List<Album> getAlbums() {
		return albums;
	}

	@Override
	public Album getModel() {
		return this.album;
	}

	@Override
	public void setSession(Map<String, Object> map) {
		this.session = map;
	}

	@Override
	public Map<String, Object> getSession() {
		return this.session;
	}

	public Review getReview() {
		return this.review;
	}
}
