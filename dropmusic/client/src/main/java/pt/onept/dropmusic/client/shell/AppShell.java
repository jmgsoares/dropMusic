package pt.onept.dropmusic.client.shell;

import asg.cliche.*;
import pt.onept.dropmusic.client.Client;
import pt.onept.dropmusic.client.CommunicationManager;
import pt.onept.dropmusic.client.service.Notify;
import pt.onept.dropmusic.common.client.contract.Notifiable;
import pt.onept.dropmusic.common.exception.*;
import pt.onept.dropmusic.common.server.contract.Crudable;
import pt.onept.dropmusic.common.server.contract.DropmusicServerInterface;
import pt.onept.dropmusic.common.server.contract.type.*;
import pt.onept.dropmusic.common.utililty.JsonUtility;

import java.rmi.RemoteException;
import java.util.List;
import java.util.stream.Collectors;

public class AppShell implements ShellManageable, ShellDependent, Notifiable {
	public static Shell shell;
	private User user;

	public AppShell(User user) {
		this.user = user;
	}
	@Override
	public void cliEnterLoop() {
		try {
			this.shell.processLine("message \"Welcome " + this.user.getUsername() + "\"");
			this.shell.processLine("getnotifications");
			try {
				Notifiable client = new Notify();
				//TODO Handle Failover
				CommunicationManager.dropmusicServer.subscribe(this.user.getId(), client );
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} catch (CLIException e) {
			e.printStackTrace();
		}
	}

	@Command(name = "getnotifications", abbrev = "catnot")
	public String getNotifications() {
		String output = null;
		List<Notification> notifications;
		boolean retry = true;

		long deadLine = System.currentTimeMillis() + Client.failOverTime;

		while (retry & deadLine >= System.currentTimeMillis()) {

			try {
				notifications = CommunicationManager.dropmusicServer.notification().get(this.user);
				retry = false;
				if (notifications != null && !notifications.isEmpty()) {
					output = "You have " + notifications.size() + " new notifications\n" +
							notifications.stream()
							.map(Notification::getMessage)
							.collect(Collectors.joining("\n"));
					removeNotifications(notifications.size());
				} else output = "No new notifications";
			} catch (RemoteException e) {
				CommunicationManager.handleFailOver();
				output = "RMI SERVER FAIL";
			} catch (DataServerException e) {
				output = "DATA SERVER FAIL";
			}
		}
		return output;
	}

	public void removeNotifications(long lastSeen) {
		boolean retry = true;

		long deadLine = System.currentTimeMillis() + Client.failOverTime;

//		while (retry & deadLine >= System.currentTimeMillis()) {
//
//			try {
//				//TODO Adapt function to use crudable delete, not custom one
//				//CommunicationManager.dropmusicServer.notification().delete(this.user,lastSeen);
//				retry = false;
//			} catch (RemoteException e) {
//				CommunicationManager.handleFailOver();
//			} catch (DataServerException e) {
//			}
//		}
	}

	@Override
	public void cliLeaveLoop() {
		try {
			this.shell.processLine("message \"User " + this.user.getUsername() + " logged out\"");
			//TODO Handle Failover

			CommunicationManager.dropmusicServer.unSubscribe(this.user.getId());
		} catch (CLIException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void cliSetShell(Shell theShell) {
		this.shell = theShell;
	}

	@Command
	public static String message(String message) {
		return message;
	}

	@Command(name = "mkartist", description = "Create a new artist. Usage: mkartist <artist-name>", abbrev = "mkart")
	public String createArtist(String artistName) {
		Artist artist = new Artist()
				.setName(artistName);
		String output = null;
		boolean retry = true;

		long deadLine = System.currentTimeMillis() + Client.failOverTime;

		while (retry & deadLine >= System.currentTimeMillis()) {

			try {
				CommunicationManager.dropmusicServer.artist().create(this.user, artist);
				retry = false;
				output = "Artist " + artist.getName() + " created successfully";
			} catch (DuplicatedException e) {
				output = "Artist " + artist.getName() + " already exists";
				retry = false;
			} catch (UnauthorizedException e) {
				retry = false;
				output = "Unauthorized";
			} catch (RemoteException e) {
				CommunicationManager.handleFailOver();
				output = "SERVER FAIL";
			} catch (IncompleteException e) {
				retry = false;
				output = "Incomplete request";
			} catch (DataServerException e) {
				output = "SERVER FAIL";
			}
		}
		return output;
	}

	@Command(name = "rmartist", description = "Remove a artist. Usage: rmartist <artist-id>", abbrev = "rmart")
	public String deleteArtist(int id) {
		Artist artist = new Artist()
				.setId(id);
		String output = null;
		boolean retry = true;

		long deadLine = System.currentTimeMillis() + Client.failOverTime;

		while (retry & deadLine >= System.currentTimeMillis()) {

			try {
				CommunicationManager.dropmusicServer.artist().delete(this.user, artist);
				retry = false;
				output = "Artist " + artist.getName() + " deleted";
			} catch (NotFoundException e) {
				output = "Artist " + artist.getName() + " not found";
				retry = false;
			} catch (UnauthorizedException e) {
				output = "Unauthorized";
				retry = false;
			} catch (RemoteException e) {
				CommunicationManager.handleFailOver();
				output = "SERVER FAIL";
			} catch (DataServerException e) {
				output = "SERVER FAIL";
			}
		}
		return output;
	}

	@Command(name = "mvartist", description = "Update a artist. Usage: mvartist <artist-id> <artist-name>", abbrev = "mvart")
	public String updateArtist(int id, String artistName) {
		Artist artist = new Artist()
				.setId(id)
				.setName(artistName);
		String output = null;
		boolean retry = true;

		long deadLine = System.currentTimeMillis() + Client.failOverTime;

		while (retry & deadLine >= System.currentTimeMillis()) {

			try {
				CommunicationManager.dropmusicServer.artist().update(this.user, artist);
				output = "Artist " + artist.getName() + " updated successfully";
				retry = false;
			} catch (NotFoundException e) {
				output = "Artist " + artist.getName() + " not found";
				retry = false;
			} catch (UnauthorizedException e) {
				output = "Unauthorized";
				retry = false;
			} catch (RemoteException e) {
				CommunicationManager.handleFailOver();
				output = "SERVER FAIL";
			} catch (IncompleteException e) {
				output = "Request incomplete";
				retry = false;
			} catch (DataServerException e) {
				output = "SERVER FAIL";
			}
		}
		return output;
	}

	@Command(name = "mkalbum", description = "Create a new album. Usage: mkalbum <album-name> <artist-id> <description>", abbrev = "mkalb")
	public String createAlbum(String albumName, int artistId, String description) {
		Album album = new Album()
				.setName(albumName)
				.setArtist(new Artist().setId(artistId))
				.setDescription(description);
		String output = null;
		boolean retry = true;

		long deadLine = System.currentTimeMillis() + Client.failOverTime;

		while (retry & deadLine >= System.currentTimeMillis()) {

			try {
				CommunicationManager.dropmusicServer.album().create(this.user, album);
				output = "Album " + album.getName() + " created successfully";
				retry = false;
			} catch (DuplicatedException e) {
				output = "Album " + album.getName() + " already exists";
				retry = false;
			} catch (UnauthorizedException e) {
				output = "Unauthorized";
				retry = false;
			} catch (RemoteException e) {
				CommunicationManager.handleFailOver();
				output = "SERVER FAIL";
			} catch (IncompleteException e) {
				output = "Incomplete request";
				retry = false;
			} catch (DataServerException e) {
				output = "SERVER FAIL";
			}
		}
		return output;
	}

	@Command(name = "rmalbum", description = "Remove a album. Usage: rmalbum <album-id>", abbrev = "rmaalb")
	public String deleteAlbum(int id) {
		Album album = new Album().setId(id);
		String output = null;
		boolean retry = true;

		long deadLine = System.currentTimeMillis() + Client.failOverTime;

		while (retry & deadLine >= System.currentTimeMillis()) {

			try {
				CommunicationManager.dropmusicServer.album().delete(this.user, album);
				output = "Album " + album.getName() + " deleted";
				retry = false;
			} catch (NotFoundException e) {
				output = "Album " + album.getName() + " not found";
				retry = false;
			} catch (UnauthorizedException e) {
				output = "Unauthorized";
				retry = false;
			} catch (RemoteException e) {
				CommunicationManager.handleFailOver();
				output = "SERVER FAIL";
			} catch (DataServerException e) {
				output = "SERVER FAIL";
			}
		}
		return output;
	}

	@Command(name = "mvalbum", description = "Update a album. Usage: mvalbum <album-id> <album-name> <album-desc>", abbrev = "mvalb")
	public String updateAlbum(int id, String albumName, String albumDescription) {
		Album album = new Album()
				.setId(id)
				.setName(albumName)
				.setDescription(albumDescription);
		String output = null;
		boolean retry = true;

		long deadLine = System.currentTimeMillis() + Client.failOverTime;

		while (retry & deadLine >= System.currentTimeMillis()) {
			try {
				CommunicationManager.dropmusicServer.album().update(this.user, album);
				output = "Album " + album.getName() + " updated successfully";
				retry = false;
			} catch (NotFoundException e) {
				output = "Album " + album.getName() + " not found";
				retry = false;
			} catch (UnauthorizedException e) {
				output = "Unauthorized";
				retry = false;
			} catch (RemoteException e) {
				CommunicationManager.handleFailOver();
				output = "SERVER FAIL";
			} catch (IncompleteException e) {
				output = "Incomplete request";
				retry = false;
			} catch (DataServerException e) {
				output = "SERVER FAIL";
			}
		}
		return output;
	}

	@Command(name = "mkmusic", description = "Create a new music. Usage: mkmusic <album-id> <music-name>", abbrev = "mkmus")
	public String createMusic(int id, String musicName) {
		Music music = new Music()
				.setAlbumId(id)
				.setName(musicName);
		String output = null;
		boolean retry = true;

		long deadLine = System.currentTimeMillis() + Client.failOverTime;

		while (retry & deadLine >= System.currentTimeMillis()) {
			try {
				CommunicationManager.dropmusicServer.music().create(this.user, music);
				output = "Music " + music.getName() + " created successfully";
				retry = false;
			} catch (DuplicatedException e) {
				output = "Music " + music.getName() + " already exists";
				retry = false;
			} catch (UnauthorizedException e) {
				output = "Unauthorized";
				retry = false;
			} catch (RemoteException e) {
				CommunicationManager.handleFailOver();
				output = "SERVER FAIL";
				output = e.getMessage();
			} catch (IncompleteException e) {
				output = "Request incomplete";
				retry = false;
			} catch (DataServerException e) {
				output = "SERVER FAIL";
			}
		}
		return output;
	}

	@Command(name = "rmmusic", description = "Remove a music. Usage: rmmusic <music-id>", abbrev = "rmmus")
	public String deleteMusic(int id) {
		Music music = new Music().setId(id);
		String output = null;
		boolean retry = true;

		long deadLine = System.currentTimeMillis() + Client.failOverTime;

		while (retry & deadLine >= System.currentTimeMillis()) {

			try {
				CommunicationManager.dropmusicServer.music().delete(this.user, music);
				output = "Music " + music.getName() + " deleted";
				retry = false;
			} catch (NotFoundException e) {
				output = "Music " + music.getName() + " not found";
				retry = false;
			} catch (UnauthorizedException e) {
				output = "Unauthorized";
				retry = false;
			} catch (RemoteException e) {
				CommunicationManager.handleFailOver();
				output = "SERVER FAIL";
			} catch (DataServerException e) {
				output = "SERVER FAIL";
			}
		}
		return output;
	}

	@Command(name = "mvmusic", description = "Update a music. Usage: mvmusic <album-id> <music-name>", abbrev = "mvmus")
	public String updateMusic(int id, String musicName) {
		Music music = new Music()
				.setAlbumId(id)
				.setName(musicName);
		String output = null;
		boolean retry = true;

		long deadLine = System.currentTimeMillis() + Client.failOverTime;

		while (retry & deadLine >= System.currentTimeMillis()) {

			try {
				CommunicationManager.dropmusicServer.music().update(this.user, music);
				retry = false;
				output = "Music " + music.getName() + " updated successfully";
			} catch (NotFoundException e) {
				output = "Music " + music.getName() + " not found";
				retry = false;
			} catch (UnauthorizedException e) {
				output = "Unauthorized";
				retry = false;
			} catch (RemoteException e) {
				CommunicationManager.handleFailOver();
				output = "SERVER FAIL";
			} catch (IncompleteException e) {
				output = "Incomplete request";
				retry = false;
			} catch (DataServerException e) {
				output = "SERVER FAIL";
			}
		}
		return output;
	}

	@Command(name = "catartist", description = "Show details about an artist. Usage catartist <artist-id>", abbrev = "catart")
	public String readArtist(int id) {
		String output = null;
		boolean retry = true;
		long deadLine = System.currentTimeMillis() + Client.failOverTime;

		while (retry & deadLine >= System.currentTimeMillis()) {
			try {
				output = cat(new Artist().setId(id), CommunicationManager.dropmusicServer.artist());
				retry = false;
			} catch (RemoteException e) {
				CommunicationManager.handleFailOver();
				output = "SERVER FAIL";
			} catch (DataServerException e) {
				output = "SERVER FAIL";
			}
		}
		return output;
	}

	@Command(name = "catalbum", description = "Show details about an album. Usage catalbum <album-id>", abbrev = "catalb")
	public String readAlbum(int id) {
		String output = null;
		boolean retry = true;
		long deadLine = System.currentTimeMillis() + Client.failOverTime;

		while (retry & deadLine >= System.currentTimeMillis()) {
			try {
				output = cat(new Album().setId(id), CommunicationManager.dropmusicServer.album());
				retry = false;
			} catch (RemoteException e) {
				CommunicationManager.handleFailOver();
				output = "SERVER FAIL";
			} catch (DataServerException e) {
				output = "SERVER FAIL";
			}
		}
		return output;
	}

	@Command(name = "catmusic", description = "Show details about a music. Usage catmusic <music-id>", abbrev = "catmus")
	public String readMusic(int id) {
		String output = null;
		boolean retry = true;
		long deadLine = System.currentTimeMillis() + Client.failOverTime;

		while (retry & deadLine >= System.currentTimeMillis()) {
			try {
				output = cat(new Music().setId(id), CommunicationManager.dropmusicServer.music());
				retry = false;
			} catch (RemoteException e) {
				CommunicationManager.handleFailOver();
				output = "SERVER FAIL";
			} catch (DataServerException e) {
				output = "SERVER FAIL";
			}
		}
		return output;
	}

	@Command(name = "search", description = "Search album by the artist name or album title. Usage search <search string>")
	public String search(String searchString) {
		String output = null;
		boolean retry = true;
		long deadLine = System.currentTimeMillis() + Client.failOverTime;

		while (retry & deadLine >= System.currentTimeMillis()) {

			try {
				List<Album> albums = CommunicationManager.dropmusicServer.album().search(this.user, searchString);
				output = JsonUtility.toPrettyJson(albums);
				retry = false;
			} catch (RemoteException e) {
				CommunicationManager.handleFailOver();
				output = "SERVER FAIL";
			} catch (DataServerException e) {
				output = "SERVER FAIL";
			}
		}
		return output;
	}

	@Command(name= "mkreview", description = "Write a review about an album. Usage: mkreview <alb-id> <score 1-5> <review>", abbrev = "mkrev")
	public String mkReview(int id, float score, String reviewText) {
		Review review = new Review()
				.setAlbumId(id)
				.setReview(reviewText)
				.setScore(score);
		String output = null;
		boolean retry = true;

		long deadLine = System.currentTimeMillis() + Client.failOverTime;

		while (retry & deadLine >= System.currentTimeMillis()) {
			try {
				CommunicationManager.dropmusicServer.review().add(this.user, review);
				output = "Review added successfully";
				retry = false;
			} catch (RemoteException e) {
				CommunicationManager.handleFailOver();
				output = "SERVER FAIL";
				output = e.getMessage();
			} catch (IncompleteException e) {
				output = "Request incomplete";
				retry = false;
			} catch (DataServerException e) {
				output = "SERVER FAIL";
			}
		}
		return output;

	}

	@Command(name = "mvuser", description = "Update the editor status of a user. Usage mvuser <user-id> <iseditor?>(true or false)")
	public String mvUser(int id, boolean isEditor) {
		User user = new User()
				.setId(id)
				.setEditor(isEditor);
		String output = null;
		boolean retry = true;

		long deadLine = System.currentTimeMillis() + Client.failOverTime;

		while (retry & deadLine >= System.currentTimeMillis()) {

			try {
				CommunicationManager.dropmusicServer.user().update(this.user, user);
				output = "User updated successfully";
				retry = false;
			} catch (NotFoundException e) {
				output = "Specified user not found";
				retry = false;
			} catch (UnauthorizedException e) {
				output = "Unauthorized";
				retry = false;
			} catch (RemoteException e) {
				CommunicationManager.handleFailOver();
				output = "SERVER FAIL";
			} catch (IncompleteException e) {
				output = "Request incomplete";
				retry = false;
			} catch (DataServerException e) {
				output = "SERVER FAIL";
			}
		}
		return output;
	}

	private <T> String cat(T objectId, Crudable<T> client) {
		String output = null;
		T object;
		boolean retry = true;

		long deadLine = System.currentTimeMillis() + Client.failOverTime;

		while (retry & deadLine >= System.currentTimeMillis()) {

			try {
				object = client.read(this.user, objectId);
				output = JsonUtility.toPrettyJson(object);
				retry = false;
			} catch (NotFoundException e) {
				output = "Not found";
				retry = false;
			} catch (UnauthorizedException e) {
				output = "Unauthorized";
				retry = false;
			} catch (RemoteException e) {
				CommunicationManager.handleFailOver();
				output = "SERVER FAIL";
			} catch (DataServerException e) {
				output = "SERVER FAIL";
			}
		}
		return output;
	}

	@Override
	public boolean notify(Notification notification) throws RemoteException {
		return false;
	}
}
