package pt.onept.dropmusic.client.shell;

import asg.cliche.*;
import pt.onept.dropmusic.common.exception.DuplicatedException;
import pt.onept.dropmusic.common.exception.IncompleteException;
import pt.onept.dropmusic.common.exception.NotFoundException;
import pt.onept.dropmusic.common.exception.UnauthorizedException;
import pt.onept.dropmusic.common.server.contract.Crudable;
import pt.onept.dropmusic.common.server.contract.DropmusicServerInterface;
import pt.onept.dropmusic.common.server.contract.type.*;
import pt.onept.dropmusic.common.utililty.JsonUtility;

import java.rmi.RemoteException;
import java.util.List;
import java.util.stream.Collectors;

public class AppShell implements ShellManageable, ShellDependent {
	private DropmusicServerInterface dropmusicServer;
	private Shell shell;
	private User user;

	public AppShell(DropmusicServerInterface dropmusicServer, User user) {
		this.dropmusicServer = dropmusicServer;
		this.user = user;
	}

	@Override
	public void cliEnterLoop() {
		try {
			this.shell.processLine("message \"Welcome " + this.user.getUsername() + "\"");
			//this.shell.processLine("getnotifications");
		} catch (CLIException e) {
			e.printStackTrace();
		}
	}

	@Command(name = "getnotifications", abbrev = "catnot")
	public String getNotifications() {
		String output;
		List<Notification> notifications;

		try {
			notifications = this.dropmusicServer.notification().get(this.user);
			if (!notifications.isEmpty()) {
				output = notifications.stream()
						.map(Notification::getMessage)
						.collect(Collectors.joining("\n"));
			} else output = "No new notifications";
		} catch (RemoteException e) {
			e.printStackTrace();
			//TODO Handle failover
			output = e.getMessage();
		}
		return output;
	}

	@Override
	public void cliLeaveLoop() {
		try {
			this.shell.processLine("message \"User " + this.user.getUsername() + " logged out\"");
		} catch (CLIException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void cliSetShell(Shell theShell) {
		this.shell = theShell;
	}

	@Command
	public String message(String message) {
		return message;
	}

	@Command(name = "mkartist", description = "Create a new artist. Usage: mkartist <artist-name>", abbrev = "mkart")
	public String createArtist(String artistName) {
		Artist artist = new Artist()
				.setName(artistName);
		String output;

		try {
			this.dropmusicServer.artist().create(this.user, artist);
			output = "Artist " + artist.getName() + " created successfully";
		} catch (DuplicatedException e) {
			output = "Artist " + artist.getName() + " already exists";
		} catch (UnauthorizedException e) {
			output = "Unauthorized";
		} catch (RemoteException e) {
			//TODO Handle failover
			output = e.getMessage();
		} catch (IncompleteException e) {
			output = "Incomplete request";
		}
		return output;
	}

	@Command(name = "rmartist", description = "Remove a artist. Usage: rmartist <artist-id>", abbrev = "rmart")
	public String deleteArtist(int id) {
		Artist artist = new Artist()
				.setId(id);
		String output;

		try {
			this.dropmusicServer.artist().delete(this.user, artist);
			output = "Artist " + artist.getName() + " deleted";
		} catch (NotFoundException e) {
			output = "Artist " + artist.getName() + " not found";
		} catch (UnauthorizedException e) {
			output = "Unauthorized";
		} catch (RemoteException e) {
			//TODO Handle failover
			output = e.getMessage();
		}
		return output;
	}

	@Command(name = "mvartist", description = "Update a artist. Usage: mvartist <artist-id> <artist-name>", abbrev = "mvart")
	public String updateArtist(int id, String artistName) {
		Artist artist = new Artist()
				.setId(id)
				.setName(artistName);
		String output;
		try {
			this.dropmusicServer.artist().update(this.user, artist);
			output = "Artist " + artist.getName() + " updated successfully";
		} catch (NotFoundException e) {
			output = "Artist " + artist.getName() + " not found";
		} catch (UnauthorizedException e) {
			output = "Unauthorized";
		} catch (RemoteException e) {
			//TODO Handle failover
			output = e.getMessage();
		} catch (IncompleteException e) {
			output = "Request incomplete";
		}
		return output;
	}

	@Command(name = "mkalbum", description = "Create a new album. Usage: mkalbum <album-name> <artist-id> <description>", abbrev = "mkalb")
	public String createAlbum(String albumName, int artistId, String description) {
		Album album = new Album()
				.setName(albumName)
				.setArtist(new Artist().setId(artistId))
				.setDescription(description);
		String output;

		try {
			dropmusicServer.album().create(this.user, album);
			output = "Album " + album.getName() + " created successfully";
		} catch (DuplicatedException e) {
			output = "Album " + album.getName() + " already exists";
		} catch (UnauthorizedException e) {
			output = "Unauthorized";
		} catch (RemoteException e) {
			//TODO Handle failover
			output = e.getMessage();
		} catch (IncompleteException e) {
			output = "Incomplete request";
		}
		return output;
	}

	@Command(name = "rmalbum", description = "Remove a album. Usage: rmalbum <album-id>", abbrev = "rmaalb")
	public String deleteAlbum(int id) {
		Album album = new Album().setId(id);
		String output;

		try {
			this.dropmusicServer.album().delete(this.user, album);
			output = "Album " + album.getName() + " deleted";
		} catch (NotFoundException e) {
			output = "Album " + album.getName() + " not found";
		} catch (UnauthorizedException e) {
			output = "Unauthorized";
		} catch (RemoteException e) {
			//TODO Handle failover
			output = e.getMessage();
		}
		return output;
	}

	@Command(name = "mvalbum", description = "Update a album. Usage: mvalbum <album-id> <album-name> <album-desc>", abbrev = "mvalb")
	public String updateAlbum(int id, String albumName, String albumDescription) {
		Album album = new Album()
				.setId(id)
				.setName(albumName)
				.setDescription(albumDescription);
		String output;
		try {
			this.dropmusicServer.album().update(this.user, album);
			output = "Album " + album.getName() + " updated successfully";
		} catch (NotFoundException e) {
			output = "Album " + album.getName() + " not found";
		} catch (UnauthorizedException e) {
			output = "Unauthorized";
		} catch (RemoteException e) {
			//TODO Handle failover
			output = e.getMessage();
		} catch (IncompleteException e) {
			output = "Incomplete request";
		}
		return output;
	}

	@Command(name = "mkmusic", description = "Create a new music. Usage: mkmusic <album-id> <music-name>", abbrev = "mkmus")
	public String createMusic(int id, String musicName) {
		Music music = new Music()
				.setAlbumId(id)
				.setName(musicName);
		String output;

		try {
			dropmusicServer.music().create(this.user, music);
			output = "Music " + music.getName() + " created successfully";
		} catch (DuplicatedException e) {
			output = "Music " + music.getName() + " already exists";
		} catch (UnauthorizedException e) {
			output = "Unauthorized";
		} catch (RemoteException e) {
			//TODO Handle failover
			output = e.getMessage();
		} catch (IncompleteException e) {
			output = "Request incomplete";
		}
		return output;
	}

	@Command(name = "rmmusic", description = "Remove a music. Usage: rmmusic <music-id>", abbrev = "rmmus")
	public String deleteMusic(int id) {
		Music music = new Music().setId(id);
		String output;

		try {
			this.dropmusicServer.music().delete(this.user, music);
			output = "Music " + music.getName() + " deleted";
		} catch (NotFoundException e) {
			output = "Music " + music.getName() + " not found";
		} catch (UnauthorizedException e) {
			output = "Unauthorized";
		} catch (RemoteException e) {
			//TODO Handle failover
			output = e.getMessage();
		}
		return output;
	}

	@Command(name = "mvmusic", description = "Update a music. Usage: mvmusic <album-id> <music-name>", abbrev = "mvmus")
	public String updateMusic(int id, String musicName) {
		Music music = new Music()
				.setAlbumId(id)
				.setName(musicName);
		String output;
		try {
			this.dropmusicServer.music().update(this.user, music);
			output = "Music " + music.getName() + " updated successfully";
		} catch (NotFoundException e) {
			output = "Music " + music.getName() + " not found";
		} catch (UnauthorizedException e) {
			output = "Unauthorized";
		} catch (RemoteException e) {
			//TODO Handle failover
			output = e.getMessage();
		} catch (IncompleteException e) {
			output = "Incomplete request";
		}
		return output;
	}

	@Command(name = "catartist", description = "Show details about an artist. Usage catartist <artist-id>", abbrev = "catart")
	public String readArtist(int id) {
		String output;
		try {
			output = cat(new Artist().setId(id), this.dropmusicServer.artist());
		} catch (RemoteException e) {
			e.printStackTrace();
			output = e.getMessage();
		}
		return output;
	}

	@Command(name = "catalbum", description = "Show details about an album. Usage catalbum <album-id>", abbrev = "catalb")
	public String readAlbum(int id) {
		String output;

		try {
			output = cat(new Album().setId(id), this.dropmusicServer.album());
		} catch (RemoteException e) {
			e.printStackTrace();
			output = e.getMessage();
		}
		return output;
	}

	@Command(name = "catmusic", description = "Show details about a music. Usage catmusic <music-id>", abbrev = "catmus")
	public String readMusic(int id) {
		String output;
		try {
			output = cat(new Music().setId(id), this.dropmusicServer.music());
		} catch (RemoteException e) {
			e.printStackTrace();
			output = e.getMessage();
		}
		return output;
	}

	public String search(String searchString) {
		String output;
		try {
			List<Album> albums = this.dropmusicServer.album().search(this.user, searchString);
			output = JsonUtility.toPrettyJson(albums);
		} catch (RemoteException e) {
			e.printStackTrace();
			output = e.getMessage();
		}
		return output;
	}

	//TODO generify the mk mv rm
	private <T> String cat(T objectId, Crudable<T> client) {
		String output = null;
		T object;

		try {
			object = client.read(this.user, objectId);
			output = JsonUtility.toPrettyJson(object);
		} catch (NotFoundException e) {
			output = "Not found";
		} catch (UnauthorizedException e) {
			output = "Unauthorized";
		} catch (RemoteException e) {
			e.printStackTrace();
			output = e.getMessage();
		}
		return output;
	}
}
