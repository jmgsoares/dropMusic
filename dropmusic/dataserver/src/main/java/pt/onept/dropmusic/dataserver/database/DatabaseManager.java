package pt.onept.dropmusic.dataserver.database;

import pt.onept.dropmusic.common.exception.NotFoundException;
import pt.onept.dropmusic.common.exception.UnauthorizedException;
import pt.onept.dropmusic.common.server.contract.type.*;

import java.io.InvalidClassException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static pt.onept.dropmusic.dataserver.database.TypeFactory.constructType;

public class DatabaseManager {
	private DatabaseConnector dbConnector;
	private static Map<Class, String> objectTable = null;
	private static final Object lock = new Object();

	private static Map<Class, String> initObjectTable() {
		Map<Class, String> c2s = new HashMap<>();
		c2s.put(Album.class, "album");
		c2s.put(Artist.class, "artist");
		c2s.put(Upload.class, "upload");
		c2s.put(Music.class, "music");
		c2s.put(Notification.class, "notification");
		c2s.put(Review.class, "reveiew");
		c2s.put(User.class, "account");
		return c2s;
	}

	public static String getTable(Class cls) {
		if(objectTable == null) {
			synchronized (lock) {
				if(objectTable == null) DatabaseManager.objectTable = DatabaseManager.initObjectTable();
			}
		}
		return objectTable.get(cls);
	}

	public <T extends DropmusicDataType> PreparedStatement getInsertStatement(Connection connection, Class<T> tClass, T object) throws SQLException, InvalidClassException {
		PreparedStatement ps;
		if( object instanceof Album ) {
			Album album = (Album) object;
			ps = connection.prepareStatement("INSERT INTO album(name, description) VALUES(?, ?) RETURNING *;" +
													"INSERT INTO artist_album(id, alb_id) VALUES(?, ?) ");
			ps.setString(1, album.getName());
			ps.setString(2, album.getDescription());
		} else if( object instanceof Artist ) {
			Artist artist = (Artist) object;
			ps = connection.prepareStatement("INSERT INTO artist(name) VALUES(?) RETURNING *;");
			ps.setString(1, artist.getName());
		} else if( object instanceof Upload ) {
			Upload upload = (Upload) object;
			ps = connection.prepareStatement(""); //TODO
		} else if( object instanceof Music ) {
			Music music = (Music) object;
			ps = connection.prepareStatement("INSERT INTO music(alb_id, name) VALUES (?, ?) RETURNING *;");
			ps.setInt(1, music.getAlbumId());
			ps.setString(2, music.getName());
		} else if( object instanceof Notification ) {
			Notification notification = (Notification) object;
			ps = connection.prepareStatement("INSERT INTO notification(use_id, message) VALUES(?, ?) RETURNING *;");
			ps.setInt(1, notification.getUserId());
			ps.setString(2, notification.getMessage());
		} else if( object instanceof Review ) {
			Review review = (Review) object;
			ps = connection.prepareStatement("INSERT INTO review(alb_id, text, score) VALUES(?, ?, ?) RETURNING *;");
			ps.setInt(1,review.getAlbumId());
			ps.setString(2, review.getReview());
			ps.setFloat(3, review.getScore());
		} else if( object instanceof User ) {
			User user = (User) object;
			ps = connection.prepareStatement("INSERT INTO account(name, password) VALUES (?, ?) RETURNING *;");
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());

		}
		throw new InvalidClassException("");
	}

	public DatabaseManager(DatabaseConnector dbConnector) {
		this.dbConnector = dbConnector;
	}

// THESE FUNCTIONS GET CALLED BY THE HANDLER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! LISADHJLA SHDLAS HDKLAJSHD OIASD

	public User login(User user) throws UnauthorizedException, SQLException {
		try(
			Connection dbConnection = this.dbConnector.getConnection();
			PreparedStatement ps = dbConnection.prepareStatement("SELECT * FROM account WHERE name = ? AND password = ?")
		) {
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return TypeFactory.constructType(User.class, rs);
			} else {
				throw new UnauthorizedException();
			}
		}
	}

	public <T extends DropmusicDataType> T read(Class<T> tClass, int id) throws SQLException, NotFoundException {
		String tableName = DatabaseManager.getTable(tClass);

		try (
			Connection dbConnection = this.dbConnector.getConnection();
			PreparedStatement ps = dbConnection.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?")
		){
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return TypeFactory.constructType(tClass, rs);

			} else {
				throw new NotFoundException();
			}
		}
	}

	public <T extends DropmusicDataType> T insert(Class<T> tClass, T object) throws SQLException {
		String tableName = DatabaseManager.getTable(tClass);

		try (
			Connection dbConnection = this.dbConnector.getConnection();
			PreparedStatement ps = this.getInsertStatement(dbConnection, tClass, object)
		){

		}
	}

}
