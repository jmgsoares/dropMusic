package pt.onept.dropmusic.dataserver;

import pt.onept.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.dropmusic.common.communication.protocol.Message;
import pt.onept.dropmusic.common.communication.protocol.MessageBuilder;
import pt.onept.dropmusic.common.communication.protocol.Operation;
import pt.onept.dropmusic.common.server.contract.type.*;
import pt.onept.dropmusic.dataserver.database.DatabaseConnector;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

final class MessageHandler implements Runnable {
	private DatabaseConnector dbConnector;
	private MulticastHandler multicastHandler;

	public MessageHandler(DatabaseConnector dbConnector, MulticastHandler multicastHandler) {
		this.dbConnector = dbConnector;
		this.multicastHandler = multicastHandler;
	}

	@Override
	public void run() {
		Message incoming;
		while (!Thread.interrupted()) {
			incoming = this.multicastHandler.receive();
			handle(incoming);
		}

	}

	private void handle(Message message) {
		switch (message.getOperation()) {

			case REGISTER:
				this.register(message);
				break;
			case LOGIN:
				this.login(message);
				break;
			case CREATE:
				if (message.getData() instanceof Artist) {
					this.createArtist(message);
				} else if (message.getData() instanceof Album) {
					this.createAlbum(message);
				} else if (message.getData() instanceof Music) {
					this.createMusic(message);
				}

				break;
			case READ:
				if (message.getData() instanceof Artist) {
					this.readArtist(message);
				} else if (message.getData() instanceof Album) {
					this.readAlbum(message);
				} else if (message.getData() instanceof Music) {
					this.readMusic(message);
				}

				break;
			case UPDATE:
				break;
			case DELETE:
				break;
			case SEARCH:
				this.search(message);
				break;
			case ADD_REVIEW:
				break;
			case GET_NOTIFICATIONS:
				break;
			case NOTIFY:
				break;
			case DELETE_NOTIFICATIONS:
				break;
			case UPLOAD_MUSIC:
				break;
			case DOWNLOAD_MUSIC:
				break;
			case SHARE_MUSIC:
				break;
			case SUCCESS:
				break;
			case DUPLICATE:
				break;
			case NOT_FOUND:
				break;
			case NO_PERMIT:
				break;
			case EXCEPTION:
				break;
		}
	}

	private void register(Message message) {
		User self = message.getSelf();
		self.setEditor(false);
		Message outgoing = MessageBuilder.buildReply(message, Operation.SUCCESS);
		try( Connection db = this.dbConnector.getConnection() ) {
			PreparedStatement ps = db.prepareStatement("INSERT INTO account(name, password, editor) VALUES(?, ?, ?);");
			ps.setString(1, self.getUsername());
			ps.setString(2, self.getPassword());
			ps.setBoolean(3, self.isEditor());
			ps.execute();
			outgoing.setOperation(Operation.SUCCESS);
		} catch (SQLException e) {
			if (e.getMessage().contains("duplicate")) outgoing.setOperation(Operation.DUPLICATE);
			else outgoing.setOperation(Operation.EXCEPTION);
			e.printStackTrace();
		}
		this.multicastHandler.send(outgoing);
	}

	private void login(Message message) {
		User self = message.getSelf();
		Message outgoing = MessageBuilder.buildReply(message, Operation.SUCCESS);
		try ( Connection db = this.dbConnector.getConnection() ) {
			PreparedStatement ps = db.prepareStatement("SELECT * FROM account WHERE name = ? AND password = ?;");
			ps.setString(1, self.getUsername());
			ps.setString(2, self.getPassword());
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				User newSelf = new User()
						.setId(rs.getInt("id"))
						.setUsername(rs.getString("name"))
						.setPassword(rs.getString("password"))
						.setEditor(rs.getBoolean("editor"));
				outgoing.setSelf(newSelf)
						.setOperation(Operation.SUCCESS);
			} else {
				outgoing.setOperation(Operation.NO_PERMIT);
			}
		} catch (SQLException e) {
			outgoing.setOperation(Operation.EXCEPTION);
			e.printStackTrace();
		}
		this.multicastHandler.send(outgoing);
	}

	private void createArtist(Message message) {
		Artist artist = (Artist) message.getData();
		Message outgoing = MessageBuilder.buildReply(message, Operation.NO_PERMIT);
		if (message.getSelf().isEditor()) {
			try (Connection db = this.dbConnector.getConnection()) {
				PreparedStatement ps = db.prepareStatement("INSERT INTO artist(name) VALUES(?);");
				ps.setString(1, artist.getName());
				ps.execute();
				outgoing.setOperation(Operation.SUCCESS);
			} catch (SQLException e) {
				if (e.getMessage().contains("duplicate")) outgoing.setOperation(Operation.DUPLICATE);
				else outgoing.setOperation(Operation.EXCEPTION);
				e.printStackTrace();
			}
		}
		this.multicastHandler.send(outgoing);
	}

	private void createAlbum(Message message) {
		Album album = (Album) message.getData();
		Message outgoing = MessageBuilder.buildReply(message, Operation.NO_PERMIT);
		if (message.getSelf().isEditor()) {
			try (Connection db = this.dbConnector.getConnection()) {
				db.setAutoCommit(false);
				PreparedStatement ps = db.prepareStatement("INSERT INTO album(name, description) VALUES(?, ?) RETURNING id;");
				ps.setString(1, album.getName());
				ps.setString(2, album.getDescription());
				ResultSet rs = ps.executeQuery();
				if(rs.next()) {
					int albumId = rs.getInt(1);
					PreparedStatement ps2 = db.prepareStatement("INSERT INTO artist_album(id, alb_id) VALUES(?, ?);");
					ps2.setInt(1, (int) album.getArtist().getId());
					ps2.setInt(2, albumId);
					ps2.execute();
					db.commit();
					outgoing.setOperation(Operation.SUCCESS);
				}
			} catch (SQLException e) {
				if (e.getMessage().contains("duplicate")) outgoing.setOperation(Operation.DUPLICATE);
				else if (e.getMessage().contains("not present")) outgoing.setOperation(Operation.INCOMPLETE);
				else outgoing.setOperation(Operation.EXCEPTION);
				e.printStackTrace();
			}
		}
		this.multicastHandler.send(outgoing);
	}

	private void createMusic(Message message) {
		Music music = (Music) message.getData();
		Message outgoing = MessageBuilder.buildReply(message, Operation.NO_PERMIT);
		if (message.getSelf().isEditor()) {
			try (Connection db = this.dbConnector.getConnection()) {
				PreparedStatement ps = db.prepareStatement("INSERT INTO music(alb_id, name) VALUES(?, ?);");
				ps.setInt(1, music.getAlbumId());
				ps.setString(2, music.getName());
				ps.execute();
				outgoing.setOperation(Operation.SUCCESS);
			} catch (SQLException e) {
				if (e.getMessage().contains("duplicate")) outgoing.setOperation(Operation.DUPLICATE);
				else if (e.getMessage().contains("not present")) outgoing.setOperation(Operation.INCOMPLETE);
				else outgoing.setOperation(Operation.EXCEPTION);
				e.printStackTrace();
			}
		}
		this.multicastHandler.send(outgoing);
	}

	private void readArtist(Message message) {
		Artist artist = (Artist) message.getData();
		Message<Artist> outgoing = MessageBuilder.buildReply(message, Operation.NOT_FOUND);
		try (Connection db = this.dbConnector.getConnection()) {
			PreparedStatement ps = db.prepareStatement("SELECT * FROM artist WHERE id = ?;");
			ps.setInt(1,(int) artist.getId());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				Artist selectedArtist = new Artist()
						.setId(rs.getInt("id"))
						.setName(rs.getString("name"));
				outgoing.setOperation(Operation.SUCCESS)
				.setData(selectedArtist);
				PreparedStatement psa = db.prepareStatement("SELECT al.* FROM artist_album ar\n" +
																"LEFT JOIN album al ON ar.alb_id = al.id\n" +
																"WHERE ar.id = ?;");
				psa.setInt(1, selectedArtist.getId());
				ResultSet rsa = psa.executeQuery();
				List<Album> albums = new LinkedList<>();
				while (rsa.next()) {
					albums.add(new Album()
							.setId(rsa.getInt("id"))
							.setName(rsa.getString("name"))
					);
				}
				selectedArtist.setAlbums(albums);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			outgoing.setOperation(Operation.EXCEPTION);
		}
		multicastHandler.send(outgoing);
	}

	private void readMusic(Message message) {
		Music music = (Music) message.getData();
		Message<Music> outgoing = MessageBuilder.buildReply(message, Operation.NOT_FOUND);
		try (Connection db = this.dbConnector.getConnection()) {
			PreparedStatement ps = db.prepareStatement("SELECT * FROM music WHERE id = ?;");
			ps.setInt(1, music.getId());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Music selectedMusic = new Music()
						.setId(rs.getInt("id"))
						.setName(rs.getString("name"))
						.setAlbumId(rs.getInt("alb_id"));
				outgoing.setOperation(Operation.SUCCESS)
						.setData(selectedMusic);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			outgoing.setOperation(Operation.EXCEPTION);
		}
		multicastHandler.send(outgoing);
	}

	private void readAlbum(Message message) {
		Album album = (Album) message.getData();
		Message<Album> outgoing = MessageBuilder.buildReply(message, Operation.NOT_FOUND);
		try (Connection db = this.dbConnector.getConnection()) {
			PreparedStatement ps = db.prepareStatement("SELECT * FROM album WHERE id = ?;");
			ps.setInt(1, album.getId());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Album selectedAlbum = new Album()
						.setId(rs.getInt("id"))
						.setName(rs.getString("name"))
						.setDescription(rs.getString("description"))
						.setScore(rs.getFloat("score"));
				PreparedStatement psa = db.prepareStatement("SELECT a.* FROM artist_album\n" +
						"LEFT JOIN artist a on artist_album.id = a.id\n" +
						"WHERE alb_id = ?;");
				psa.setInt(1, selectedAlbum.getId());
				ResultSet rsa = psa.executeQuery();
				if(rsa.next()) {
					selectedAlbum.setArtist(
						new Artist()
							.setId(rsa.getInt("id"))
							.setName(rsa.getString("name"))
					);
					PreparedStatement psm = db.prepareStatement("SELECT * FROM music WHERE alb_id = ?;");
					psm.setInt(1, selectedAlbum.getId());
					ResultSet rsm = psm.executeQuery();
					List<Music> musics = new LinkedList<>();
					while (rsm.next()) {
						musics.add(new Music()
								.setId(rsm.getInt("id"))
								.setAlbumId(rsm.getInt("alb_id"))
								.setName(rsm.getString("name"))
						);
					}
					selectedAlbum.setMusics(musics);
					PreparedStatement psr = db.prepareStatement("SELECT * FROM reveiew WHERE alb_id = ?;");
					psr.setInt(1, selectedAlbum.getId());
					ResultSet rsr = psr.executeQuery();
					List<Review> reviews = new LinkedList<>();
					while (rsr.next()) {
						reviews.add(new Review()
								.setReview(rsr.getString("text"))
								.setScore(rsr.getFloat("score"))
						);
					}
					selectedAlbum.setReviews(reviews);
				}











				outgoing.setOperation(Operation.SUCCESS)
						.setData(selectedAlbum);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			outgoing.setOperation(Operation.EXCEPTION);
		}
		multicastHandler.send(outgoing);
	}

	private void searh(Message message) {
		
	}
}