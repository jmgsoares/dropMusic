package pt.onept.dropmusic.client.shell;

import asg.cliche.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pt.onept.dropmusic.common.exception.DuplicatedException;
import pt.onept.dropmusic.common.exception.NotFoundException;
import pt.onept.dropmusic.common.exception.UnauthorizedException;
import pt.onept.dropmusic.common.server.contract.Crudable;
import pt.onept.dropmusic.common.server.contract.DropmusicServerInterface;
import pt.onept.dropmusic.common.server.contract.type.*;

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
			this.shell.processLine("getnotifications");
		} catch (CLIException e) {
			e.printStackTrace();
		}
	}

	@Command(name = "getnotifications", abbrev = "catnot")
	public String getNotifications() {
		String output;
		List<Notification> notifications;

		try {
			notifications = this.dropmusicServer.user().read(this.user.getId()).getNotifications();
			if (!notifications.isEmpty()) {
				output = notifications.stream()
						.map(Notification::getMessage)
						.collect(Collectors.joining("\n"));
			} else output = "No new notifications";
		} catch (NotFoundException | RemoteException | UnauthorizedException e) {
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
			this.dropmusicServer.artist().create(artist);
			output = "Artist " + artist.getName() + " created successfully";
		} catch (DuplicatedException e) {
			output = "Artist " + artist.getName() + " already exists";
		} catch (UnauthorizedException e) {
			output = "Unauthorized";
		} catch (RemoteException e) {
			//TODO Handle failover
			output = e.getMessage();
		}
		return output;
	}

	@Command(name = "rmartist", description = "Remove a artist. Usage: rmartist <artist-name>", abbrev = "rmart")
	public String deleteArtist(String artistName) {
		Artist artist = new Artist()
				.setName(artistName);
		String output;

		try {
			this.dropmusicServer.artist().delete(artist);
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

	@Command(name = "mvartist", description = "Update a artist. Usage: mvartist <artist-name>", abbrev = "mvart")
	public String updateArtist(String artistName) {
		Artist artist = new Artist()
				.setName(artistName);
		String output;
		try {
			this.dropmusicServer.artist().update(artist);
			output = "Artist " + artist.getName() + " updated successfully";
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

	@Command(name = "mkalbum", description = "Create a new album. Usage: mkalbum <album-name>", abbrev = "mkalb")
	public String createAlbum(String albumName) {
		Album album = new Album();
		String output;

		try {
			dropmusicServer.album().create(album);
			output = "Album " + album.getName() + " created successfully";
		} catch (DuplicatedException e) {
			output = "Album " + album.getName() + " already exists";
		} catch (UnauthorizedException e) {
			output = "Unauthorized";
		} catch (RemoteException e) {
			//TODO Handle failover
			output = e.getMessage();
		}
		return output;
	}

	@Command(name = "rmalbum", description = "Remove a album. Usage: rmalbum <album-name>", abbrev = "rmaalb")
	public String deleteAlbum(String albumName) {
		Album album = new Album();
		String output;

		try {
			this.dropmusicServer.album().delete(album);
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

	@Command(name = "mvalbum", description = "Update a album. Usage: mvalbum <album-name>", abbrev = "mvalb")
	public String updateAlbum(String albumName) {
		Album album = new Album();
		String output;
		try {
			this.dropmusicServer.album().update(album);
			output = "Album " + album.getName() + " updated successfully";
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

	@Command(name = "mkmusic", description = "Create a new music. Usage: mkmusic <music-name>", abbrev = "mkmus")
	public String createMusic(String musicName) {
		Music music = new Music(musicName);
		String output;

		try {
			dropmusicServer.music().create(music);
			output = "Music " + music.getName() + " created successfully";
		} catch (DuplicatedException e) {
			output = "Music " + music.getName() + " already exists";
		} catch (UnauthorizedException e) {
			output = "Unauthorized";
		} catch (RemoteException e) {
			//TODO Handle failover
			output = e.getMessage();
		}
		return output;
	}

	@Command(name = "rmmusic", description = "Remove a music. Usage: rmmusic <music-name>", abbrev = "rmmus")
	public String deleteMusic(String musicName) {
		Music music = new Music(musicName);
		String output;

		try {
			this.dropmusicServer.music().delete(music);
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

	@Command(name = "mvmusic", description = "Update a music. Usage: mvmusic <music-name>", abbrev = "mvmus")
	public String updateMusic(String musicName) {
		Music music = new Music(musicName);
		String output;
		try {
			this.dropmusicServer.music().update(music);
			output = "Music " + music.getName() + " updated successfully";
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

	@Command(name = "catartist", description = "Show details about an artist. Usage catartist <artist-id>", abbrev = "catart")
	public String readArtist(long id) {
		String output;
		try {
			output = cat(id, this.dropmusicServer.artist());
		} catch (RemoteException e) {
			e.printStackTrace();
			output = e.getMessage();
		}
		return output;
	}

	@Command(name = "catalbum", description = "Show details about an album. Usage catalbum <album-id>", abbrev = "catalb")
	public String readAlbum(long id) {
		String output;

		try {
			output = cat(id, this.dropmusicServer.album());
		} catch (RemoteException e) {
			e.printStackTrace();
			output = e.getMessage();
		}
		return output;
	}

	@Command(name = "catmusic", description = "Show details about a music. Usage catmusic <music-id>", abbrev = "catmus")
	public String readMusic(long id) {
		String output;
		try {
			output = cat(id, this.dropmusicServer.music());
		} catch (RemoteException e) {
			e.printStackTrace();
			output = e.getMessage();
		}
		return output;
	}

	//TODO generify the mk mv rm
	private <T> String cat(long id, Crudable<T> client) {
		String output;
		T object;

		try {
			object = client.read(id);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			output = gson.toJson(object);
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
