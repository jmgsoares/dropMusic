package pt.onept.sd1819.dropmusic.client.shell;

import asg.cliche.*;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import pt.onept.sd1819.dropmusic.client.Client;
import pt.onept.sd1819.dropmusic.common.communication.rmi.CommunicationManager;
import pt.onept.sd1819.dropmusic.client.service.NotificationService;
import pt.onept.sd1819.dropmusic.common.exception.*;
import pt.onept.sd1819.dropmusic.common.server.contract.Crudable;
import pt.onept.sd1819.dropmusic.common.server.contract.type.*;
import pt.onept.sd1819.dropmusic.common.utililty.JsonUtility;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Main shell of the application where all the functions are implemented to be called on the CLI
 */
public class AppShell implements ShellManageable, ShellDependent {
	private Shell shell;
	private User user;
	private NotificationService notification;
	private UUID subscriptionId;

	/**
	 * Constructor
	 *
	 * @param user User to attach the shell
	 */
	public AppShell(User user) {
		this.user = user;
		try {
			this.notification = new NotificationService();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Prints messages to the user
	 *
	 * @param message message to print
	 * @return message to print @ the CLI
	 */
	@Command
	public static String message(String message) {
		return message;
	}

	/**
	 * Pre-Processing upon creating the shell
	 * Retrieves notifications and subscribes to the live notifications
	 */
	@Override
	public void cliEnterLoop() {
		try {
			this.shell.processLine("message \"Welcome " + this.user.getUsername() + "\"");
			this.shell.processLine("getnotifications");
			boolean retry = true;

			long deadLine = System.currentTimeMillis() + Client.failOverTime;

			while (retry & deadLine >= System.currentTimeMillis()) {

				try {
					this.subscriptionId = CommunicationManager.getServerInterface().notification().subscribe(this.user.getId(), this.notification);
					retry = false;
				} catch (RemoteException | DataServerException e) {
					CommunicationManager.handleFailOver();
				}
			}
			if (retry) this.shell.processLine("message \"RMI SERVER ERROR while subscribing\n" +
					"You will not be able to receive live notifications\"");
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
				notifications = CommunicationManager.getServerInterface().notification().get(this.user);
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
//				//TODO Adapt function to use crudable delete, not the custom spaghetti one
//				//CommunicationManager.dropmusicServer.notification().delete(this.user,lastSeen);
//				retry = false;
//			} catch (RemoteException e) {
//				CommunicationManager.handleFailOver();
//			} catch (DataServerException e) {
//			}
//		}
	}

	/**
	 * Function to clean on login
	 * Unsubscribe the live notifications
	 */
	@Override
	public void cliLeaveLoop() {
		boolean retry = true;

		long deadLine = System.currentTimeMillis() + Client.failOverTime;

		while (retry & deadLine >= System.currentTimeMillis()) {

			try {
				CommunicationManager.getServerInterface().notification().unSubscribe(this.user.getId(), subscriptionId);
				this.shell.processLine("message \"User " + this.user.getUsername() + " logged out\"");
				retry = false;
			} catch (CLIException e) {
				e.printStackTrace();
			} catch (RemoteException | DataServerException e) {
				CommunicationManager.handleFailOver();
			}
		}
	}

	@Override
	public void cliSetShell(Shell theShell) {
		this.shell = theShell;
		this.notification.setShell(this.shell);

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
				CommunicationManager.getServerInterface().artist().create(this.user, artist);
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
				CommunicationManager.getServerInterface().artist().delete(this.user, artist);
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
				CommunicationManager.getServerInterface().artist().update(this.user, artist);
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
				CommunicationManager.getServerInterface().album().create(this.user, album);
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
				CommunicationManager.getServerInterface().album().delete(this.user, album);
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
				CommunicationManager.getServerInterface().album().update(this.user, album);
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
				CommunicationManager.getServerInterface().music().create(this.user, music);
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
				CommunicationManager.getServerInterface().music().delete(this.user, music);
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

	@Command(name = "mvmusic", description = "Update a music. Usage: mvmusic <music-id> <music-name>", abbrev = "mvmus")
	public String updateMusic(int id, String musicName) {
		Music music = new Music()
				.setId(id)
				.setName(musicName);
		String output = null;
		boolean retry = true;

		long deadLine = System.currentTimeMillis() + Client.failOverTime;

		while (retry & deadLine >= System.currentTimeMillis()) {

			try {
				CommunicationManager.getServerInterface().music().update(this.user, music);
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
				output = cat(new Artist().setId(id), CommunicationManager.getServerInterface().artist());
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
				output = cat(new Album().setId(id), CommunicationManager.getServerInterface().album());
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
				output = cat(new Music().setId(id), CommunicationManager.getServerInterface().music());
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
				List<Album> albums = CommunicationManager.getServerInterface().album().search(this.user, searchString);
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

	@Command(name = "mkreview", description = "Write a review about an album. Usage: mkreview <alb-id> <score 1-5> <review>", abbrev = "mkrev")
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
				CommunicationManager.getServerInterface().review().add(this.user, review);
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
				CommunicationManager.getServerInterface().user().update(this.user, user);
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

	@Command(name = "listRemote", description = "List remote files")
	public String listRemoteFiles() {
		List<File> fileList;
		boolean retry = true;
		long deadLine = System.currentTimeMillis() + Client.failOverTime;

		while (retry & deadLine >= System.currentTimeMillis()) {
			try {
				this.user = CommunicationManager.getServerInterface().user().read(this.user, user);

				fileList = CommunicationManager.getServerInterface().file().listRemoteFiles(this.user);
				retry = false;
				return JsonUtility.toPrettyJson(fileList);

			} catch (RemoteException | NotFoundException | UnauthorizedException | DataServerException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	@Command(name = "listMusics", description = "List app musics")
	public String listMusics() {
		List<Music> musicList;
		boolean retry = true;
		long deadLine = System.currentTimeMillis() + Client.failOverTime;

		while (retry & deadLine >= System.currentTimeMillis()) {
			try {
				this.user = CommunicationManager.getServerInterface().user().read(this.user, user);

				musicList = CommunicationManager.getServerInterface().music().list(this.user);

				retry = false;

				return JsonUtility.toPrettyJson(musicList);

			} catch (RemoteException | NotFoundException | UnauthorizedException | DataServerException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	@Command(name = "linkRemote", description = "Link file")
	public String linkRemote(String remoteId, int localId) {
		boolean retry = true;
		long deadLine = System.currentTimeMillis() + Client.failOverTime;
		while (retry & deadLine >= System.currentTimeMillis()) {
			try {
				this.user = CommunicationManager.getServerInterface().user().read(this.user, user);
				Music music = CommunicationManager.getServerInterface().music().read(this.user, new Music().setId(localId));

				 CommunicationManager.getServerInterface().file().linkRemoteFile(
				 		this.user,
						 new File()
								 .setDropBoxFileId(remoteId)
								 .setMusicId(localId)
								 .setOwnerId(this.user.getId())
						         .setMusicName(music.getName())
						 );
				retry = false;

			} catch (RemoteException | NotFoundException | UnauthorizedException | DataServerException | DuplicatedException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	@Command(name = "listUsers", description = "Link file")
	public String listUsers() {
		List<User> userList;
		boolean retry = true;
		long deadLine = System.currentTimeMillis() + Client.failOverTime;

		while (retry & deadLine >= System.currentTimeMillis()) {
			try {
				this.user = CommunicationManager.getServerInterface().user().read(this.user, user);

				userList = CommunicationManager.getServerInterface().user().list(this.user);

				retry = false;

				return JsonUtility.toPrettyJson(userList);

			} catch (RemoteException | NotFoundException | UnauthorizedException | DataServerException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	@Command(name = "listFiles", description = "List files")
	public String listFiles() {
		List<File> fileList;
		boolean retry = true;
		long deadLine = System.currentTimeMillis() + Client.failOverTime;

		while (retry & deadLine >= System.currentTimeMillis()) {
			try {
				this.user = CommunicationManager.getServerInterface().user().read(this.user, user);

				fileList = CommunicationManager.getServerInterface().file().list(this.user);
				retry = false;
				return JsonUtility.toPrettyJson(fileList);

			} catch (RemoteException | NotFoundException | UnauthorizedException | DataServerException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	@Command(name = "listSharedFiles", description = "List shared")
	public String listSharedFiles() {
		List<File> fileList;
		boolean retry = true;
		long deadLine = System.currentTimeMillis() + Client.failOverTime;

		while (retry & deadLine >= System.currentTimeMillis()) {
			try {
				this.user = CommunicationManager.getServerInterface().user().read(this.user, user);

				fileList = CommunicationManager.getServerInterface().file().listSharedFiles(this.user);
				retry = false;
				return JsonUtility.toPrettyJson(fileList);

			} catch (RemoteException | NotFoundException | UnauthorizedException | DataServerException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	@Command(name = "shareFile", description = "Share a file")
	public String shareFile(int fileId, int userId) {
		boolean retry = true;
		long deadLine = System.currentTimeMillis() + Client.failOverTime;

		while (retry & deadLine >= System.currentTimeMillis()) {
			try {
				this.user = CommunicationManager.getServerInterface().user().read(this.user, user);

				CommunicationManager.getServerInterface().file().shareFile(
						this.user,
						new File().setId(fileId),
						new User().setId(userId));
				retry = false;
				return "";

			} catch (RemoteException | NotFoundException | UnauthorizedException | DataServerException e) {
				e.printStackTrace();
			} catch (OAuthException e) {
				return "That user has no DropBox Account Associated";
			}
		}
		return "";
	}

	@Command(name = "saveFile", description = "Save a file")
	public String saveFile(String fileName, String path) {
		try {
			JSONObject filePath = new JSONObject();
			filePath.put("path", "/SD_UC_1819/" + fileName);
			HttpResponse<InputStream> response = Unirest.post(
					"https://content.dropboxapi.com/2/files/download")
					.header("Authorization", "Bearer " + this.user.getDropBoxToken())
					.header("Content-Type", "application/octet-stream")
					.header("Dropbox-API-Arg", filePath.toString())
					.asBinary();
			JSONObject responseApiResult = new JSONObject(
					response.getHeaders().getFirst("dropbox-api-result"));

			saveFileLocally(
					response.getRawBody(),
					path,
					responseApiResult.get("name").toString());

			return "SUCCESS";
		} catch (UnirestException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static void saveFileLocally(InputStream inputStream, String pathToLocalAppFolder, String fileName) {
		try {
			java.io.File file = new java.io.File(pathToLocalAppFolder + "/" + fileName);

			Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Command(name = "saveSharedFile", description = "Save a shared file")
	public String saveSharedFile(String urlFile, String path) {
		try {
			JSONObject dropBoxApiArgs = new JSONObject();
			dropBoxApiArgs.put("url", urlFile);
			HttpResponse<InputStream> response = Unirest.post(
					"https://content.dropboxapi.com/2/sharing/get_shared_link_file")
					.header("Authorization", "Bearer " + this.user.getDropBoxToken())
					.header("Dropbox-API-Arg", dropBoxApiArgs.toString())
					.asBinary();

			JSONObject responseApiResult = new JSONObject(
					response.getHeaders().getFirst("dropbox-api-result"));

			saveFileLocally(
					response.getRawBody(),
					path,
					responseApiResult.get("name").toString());

			return "SUCCESS";
		} catch (UnirestException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Command(name = "uploadFile", description = "Upload a file")
	public String uploadFile(String path, String fileName) {
		try {
			JSONObject filePath = new JSONObject();
			filePath.put("path", "/SD_UC_1819/" + fileName);
			java.io.File file = new java.io.File(path + "/SD_UC_1819/" + fileName);
			HttpResponse<JsonNode> response = Unirest.post("https://content.dropboxapi.com/2/files/upload")
					.header("Authorization", "Bearer " + this.user.getDropBoxToken())
					.header("Content-Type", "application/octet-stream")
					.header("Dropbox-API-Arg", filePath.toString())
					.body(Files.readAllBytes(file.toPath()))
					.asJson();
			return "SUCCESS";
		} catch (UnirestException | IOException e) {
			e.printStackTrace();
			return null;
		}
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

}