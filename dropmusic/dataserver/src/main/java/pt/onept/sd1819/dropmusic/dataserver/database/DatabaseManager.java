package pt.onept.sd1819.dropmusic.dataserver.database;

import pt.onept.sd1819.dropmusic.common.exception.IncompleteException;
import pt.onept.sd1819.dropmusic.common.exception.NotFoundException;
import pt.onept.sd1819.dropmusic.common.exception.UnauthorizedException;
import pt.onept.sd1819.dropmusic.common.server.contract.type.*;

import java.io.InvalidClassException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DatabaseManager {
	private static final Object lock = new Object();
	private static Map<Class, String> objectTable = null;
	private static Map<Class, String> popTable = null;
	private static Map<Class, String> listTable = null;

	private DatabaseConnector dbConnector;

	public DatabaseManager(DatabaseConnector dbConnector) {
		this.dbConnector = dbConnector;
	}

	private static Map<Class, String> initObjectTable() {
		Map<Class, String> c2s = new HashMap<>();
		c2s.put(Album.class, "album");
		c2s.put(Artist.class, "artist");
		c2s.put(File.class, "upload");
		c2s.put(Music.class, "music");
		c2s.put(Notification.class, "notification");
		c2s.put(Review.class, "review");
		c2s.put(User.class, "account");
		return c2s;
	}

	private static Map<Class, String> initPopTable() {
		Map<Class, String> queries = new HashMap<>();
		queries.put(Album.class, "SELECT al.* FROM album al LEFT JOIN artist_album aa on al.id = aa.alb_id WHERE aa.id = ?");
		queries.put(Review.class, "SELECT * FROM review r WHERE r.alb_id = ?");
		queries.put(Music.class, "SELECT * FROM music m WHERE m.alb_id = ?");
		queries.put(Notification.class, "SELECT * FROM notification n WHERE n.use_id = ?");
		queries.put(Artist.class, "SELECT ar.* FROM artist_album AS aa LEFT JOIN artist ar ON aa.id = ar.id WHERE aa.alb_id = ?");
		return queries;
	}

	private static Map<Class, String> initListTable() {
		Map<Class, String> queries = new HashMap<>();
		queries.put(Album.class, "SELECT * FROM album");
		queries.put(Review.class, "SELECT * FROM review");
		queries.put(Music.class, "SELECT * FROM music");
		queries.put(Notification.class, "SELECT * FROM notification");
		queries.put(Artist.class, "SELECT * FROM artist");
		queries.put(User.class, "SELECT * FROM account");
		return queries;
	}

	public static String getListQuery(Class cls) {
		if (listTable == null) {
			synchronized (lock) {
				if (listTable == null) DatabaseManager.listTable = DatabaseManager.initListTable();
			}
		}
		return listTable.get(cls);
	}

	public static String getTable(Class cls) {
		if (objectTable == null) {
			synchronized (lock) {
				if (objectTable == null) DatabaseManager.objectTable = DatabaseManager.initObjectTable();
			}
		}
		return objectTable.get(cls);
	}

	public static String getPopQuery(Class cls) {
		if (popTable == null) {
			synchronized (lock) {
				if (popTable == null) DatabaseManager.popTable = DatabaseManager.initPopTable();
			}
		}
		return popTable.get(cls);
	}

	public <T extends DropmusicDataType> PreparedStatement getInsertStatement(Connection connection, Class<T> tClass, T object) throws SQLException, InvalidClassException {
		PreparedStatement ps;
		if (object instanceof Album) {
			Album album = (Album) object;
			ps = connection.prepareStatement("SELECT * FROM add_album(?, ?, ?);");
			ps.setInt(1, album.getArtist().getId());
			ps.setString(2, album.getName());
			ps.setString(3, album.getDescription());
		} else if (object instanceof Artist) {
			Artist artist = (Artist) object;
			ps = connection.prepareStatement("INSERT INTO artist(name) VALUES(?) RETURNING *;");
			ps.setString(1, artist.getName());
		} else if (object instanceof File) {
			File file = (File) object;
			ps = connection.prepareStatement(""); //TODO
		} else if (object instanceof Music) {
			Music music = (Music) object;
			ps = connection.prepareStatement("INSERT INTO music(alb_id, name) VALUES (?, ?) RETURNING *;");
			ps.setInt(1, music.getAlbumId());
			ps.setString(2, music.getName());
		} else if (object instanceof Notification) {
			Notification notification = (Notification) object;
			ps = connection.prepareStatement("INSERT INTO notification(use_id, message) VALUES(?, ?) RETURNING *;");
			ps.setInt(1, notification.getUserId());
			ps.setString(2, notification.getMessage());
		} else if (object instanceof Review) {
			Review review = (Review) object;
			ps = connection.prepareStatement("INSERT INTO review(alb_id, text, score) VALUES(?, ?, ?) RETURNING *;");
			ps.setInt(1, review.getAlbumId());
			ps.setString(2, review.getReview());
			ps.setFloat(3, review.getScore());
		} else if (object instanceof User) {
			User user = (User) object;
			ps = connection.prepareStatement("INSERT INTO account(name, password) VALUES (?, ?) RETURNING *;");
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
		} else {
			System.out.println("###" + TypeFactory.getSubtype(object).toString());
			throw new InvalidClassException("");
		}
		return ps;
	}

	public <T extends DropmusicDataType> PreparedStatement getUpdateStatement(Connection connection, Class<T> tClass, T object) throws SQLException, InvalidClassException {
		PreparedStatement ps;
		if (object instanceof Album) {
			Album album = (Album) object;
			ps = connection.prepareStatement("UPDATE album SET name = ?, description = ? WHERE id = ?");
			ps.setString(1, album.getName());
			ps.setString(2, album.getDescription());
			ps.setInt(3, album.getId());
		} else if (object instanceof Artist) {
			Artist artist = (Artist) object;
			ps = connection.prepareStatement("UPDATE artist SET name = ? WHERE id = ?;");
			ps.setString(1, artist.getName());
			ps.setInt(2, artist.getId());
		} else if (object instanceof Music) {
			Music music = (Music) object;
			ps = connection.prepareStatement("UPDATE music SET name = ? WHERE id = ?;");
			ps.setString(1, music.getName());
			ps.setInt(2, music.getId());
		} else if (object instanceof User) {
			User user = (User) object;
			ps = connection.prepareStatement("UPDATE account SET editor = ? WHERE id = ?;");
			ps.setBoolean(1, user.getEditor());
			ps.setInt(2, user.getId());

		} else {
			System.out.println("###" + TypeFactory.getSubtype(object).toString());
			throw new InvalidClassException("");
		}
		return ps;
	}

	//TODO Implement delete of DB function... delete queriesTable??
	public <T extends DropmusicDataType> PreparedStatement getDeleteStatement(Connection connection, Class<T> tClass, T object) throws SQLException, InvalidClassException {
		PreparedStatement ps = null;
//		if( object instanceof Album ) {
//			Album album = (Album) object;
//			ps = connection.prepareStatement("SELECT * FROM add_album(?, ?, ?);");
//			ps.setInt(1, album.getArtist().getId());
//			ps.setString(2, album.getName());
//			ps.setString(3, album.getDescription());
//		} else if( object instanceof Artist ) {
//			Artist artist = (Artist) object;
//			ps = connection.prepareStatement("INSERT INTO artist(name) VALUES(?) RETURNING *;");
//			ps.setString(1, artist.getName());
//		} else if( object instanceof File ) {
//			File upload = (File) object;
//			ps = connection.prepareStatement("");
//		} else if( object instanceof Music ) {
//			Music music = (Music) object;
//			ps = connection.prepareStatement("INSERT INTO music(alb_id, name) VALUES (?, ?) RETURNING *;");
//			ps.setInt(1, music.getAlbumId());
//			ps.setString(2, music.getName());
//		} else
		System.out.println(object);
		if (object instanceof Notification) {
			Notification notification = (Notification) object;
			ps = connection.prepareStatement("DELETE FROM notification WHERE use_id = ?");
			ps.setInt(1, ((Notification) object).getUserId());
//		} else if( object instanceof Review ) {
//			Review review = (Review) object;
//			ps = connection.prepareStatement("INSERT INTO review(alb_id, text, score) VALUES(?, ?, ?) RETURNING *;");
//			ps.setInt(1,review.getAlbumId());
//			ps.setString(2, review.getReview());
//			ps.setFloat(3, review.getScore());
//		} else if( object instanceof User ) {
//			User user = (User) object;
//			ps = connection.prepareStatement("INSERT INTO account(name, password) VALUES (?, ?) RETURNING *;");
//			ps.setString(1, user.getUsername());
//			ps.setString(2, user.getPassword());
		}
		return ps;
	}

// THESE FUNCTIONS GET CALLED BY THE HANDLER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

	public User login(User user) throws UnauthorizedException, SQLException {
		try (
				Connection dbConnection = this.dbConnector.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement("SELECT * FROM account WHERE name = ? AND password = ?")
		) {
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return TypeFactory.constructType(User.class, rs);
			} else {
				throw new UnauthorizedException();
			}
		}
	}

	public <T extends DropmusicDataType> T insert(Class<T> tClass, T object) throws SQLException, InvalidClassException, IncompleteException {
		try (
				Connection dbConnection = this.dbConnector.getConnection();
				PreparedStatement ps = this.getInsertStatement(dbConnection, tClass, object)
		) {
			ResultSet rs = ps.executeQuery();
			if (rs.next()) return TypeFactory.constructType(tClass, rs);
			else throw new IncompleteException();
		}
	}

	public <T extends DropmusicDataType> T read(Class<T> tClass, int id) throws SQLException, NotFoundException {
		String tableName = DatabaseManager.getTable(tClass);
		try (
				Connection dbConnection = this.dbConnector.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?")
		) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				T object = TypeFactory.constructType(tClass, rs);
				if (tClass.equals(Artist.class) || tClass.equals(Album.class)) populate(tClass, object);
				return object;
			} else {
				throw new NotFoundException();
			}
		}
	}

	public <T extends DropmusicDataType> List<T> readList(Class<T> tClass, DropmusicDataType object) throws SQLException {
		List<T> list = new LinkedList<>();
		try (
				Connection connection = dbConnector.getConnection();
				PreparedStatement ps = connection.prepareStatement(DatabaseManager.getPopQuery(tClass))
		) {
			ps.setInt(1, object.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) list.add(TypeFactory.constructType(tClass, rs));
		}
		return list;
	}

	public <T extends DropmusicDataType> List<T> list(Class<T> tClass, DropmusicDataType object) throws SQLException {
		List<T> list = new LinkedList<>();
		try (
				Connection connection = dbConnector.getConnection();
				PreparedStatement ps = connection.prepareStatement(DatabaseManager.getListQuery(tClass))
		) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) list.add(TypeFactory.constructType(tClass, rs));
		}
		return list;
	}

	private <T extends DropmusicDataType> void populate(Class<T> tClass, T object) throws SQLException {
		if (object instanceof Artist) {
			Artist artist = (Artist) object;
			artist.setAlbums(this.readList(Album.class, artist));
		} else if (object instanceof Album) {
			Album album = (Album) object;
			album.setReviews(this.readList(Review.class, album))
					.setArtist(this.readList(Artist.class, album).get(0))
					.setMusics(this.readList(Music.class, album));
		}
	}

	public <T extends DropmusicDataType> void delete(Class<T> tClass, T object) throws SQLException {
		try (
				Connection dbConnection = this.dbConnector.getConnection();
				PreparedStatement ps = this.getDeleteStatement(dbConnection, tClass, object)
		) {

			ps.executeUpdate();
		} catch (InvalidClassException e) {
			e.printStackTrace();
		}
	}

	//TODO populate the albums returned by this
	public List<Album> searchAlbum(String queryString) throws SQLException {
		List<Album> list = new LinkedList<>();
		try (
				Connection connection = dbConnector.getConnection();
				PreparedStatement ps = connection.prepareStatement("SELECT al.* FROM album AS al\n" +
						"LEFT JOIN artist_album artal on al.id = artal.alb_id\n" +
						"LEFT JOIN artist a on artal.id = a.id\n" +
						"WHERE a.name LIKE ? OR al.name LIKE ?"
				)
		) {
			queryString = "%" + queryString + "%";
			ps.setString(1, queryString);
			ps.setString(2, queryString);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Album album = TypeFactory.constructType(Album.class, rs);
				populate(Album.class, album);
				list.add(album);
				List<Artist> artistList = this.readList(Artist.class, album);
				album.setArtist(artistList.get(0));
			}
		}
		return list;
	}

	public <T extends DropmusicDataType> void update(Class<T> tClass, T object) throws SQLException, InvalidClassException, IncompleteException {
		try (
				Connection dbConnection = this.dbConnector.getConnection();
				PreparedStatement ps = this.getUpdateStatement(dbConnection, tClass, object)
		) {
			ps.executeUpdate();
		}

	}

	public <T extends DropmusicDataType> void logInsert(Class<T> tClass, T object, int id) {
		String tableName;
		if (tClass.equals(Album.class)) tableName = "ACCOUNT_ALBUM_CHANGES";
		else if (tClass.equals(Artist.class)) tableName = "ACCOUNT_ARTIST_CHANGES";
		else return;
		try (
				Connection dbConnection = this.dbConnector.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement("INSERT INTO " + tableName + " VALUES(?,?)")
		) {
			ps.setInt(1, id);
			ps.setInt(2, object.getId());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}