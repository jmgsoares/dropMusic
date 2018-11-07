package pt.onept.dropmusic.dataserver.database;

import pt.onept.dropmusic.common.server.contract.type.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class TypeFactory {

	private TypeFactory() {
	}

	public static <T extends DropmusicDataType> T constructType(Class<T> tClass, ResultSet rs) throws SQLException {
		T object = null;
		try {
			object = tClass.newInstance();
			if (tClass.equals(Album.class)) {
				((Album) object)
						.setId(rs.getInt("id"))
						.setName(rs.getString("name"))
						.setDescription(rs.getString("description"));

			} else if (tClass.equals(Artist.class)) {
				((Artist) object)
						.setId(rs.getInt("id"))
						.setName(rs.getString("name"));

			} else if (tClass.equals(Upload.class)) {
				((Upload) object)
						.setId(rs.getInt("id"))
						.setIpAddress(rs.getString("ip_address"))
						.setName(rs.getString("name"));

			} else if (tClass.equals(Music.class)) {
				((Music) object)
						.setId(rs.getInt("id"))
						.setName(rs.getString("name"))
						.setAlbumId(rs.getInt("alb_id"));

			} else if (tClass.equals(Notification.class)) {
				((Notification) object)
						.setId(rs.getInt("id"))
						.setMessage(rs.getString("message"))
						.setUserId(rs.getInt("use_id"));

			} else if (tClass.equals(Review.class)) {
				((Review) object)
						.setReview(rs.getString("text"))
						.setScore(rs.getFloat("score"));

			} else if (tClass.equals(User.class)) {
				((User) object)
						.setId(rs.getInt("id"))
						.setUsername(rs.getString("name"))
						.setPassword(rs.getString("password"))
						.setEditor(rs.getBoolean("editor"));
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return object;
	}


	public static <T extends DropmusicDataType> Class getSubtype(T object) {
		if (object instanceof Album) return Album.class;
		if (object instanceof Artist) return Artist.class;
		if (object instanceof Upload) return Upload.class;
		if (object instanceof Music) return Music.class;
		if (object instanceof Notification) return Notification.class;
		if (object instanceof Review) return Review.class;
		if (object instanceof User) return User.class;
		return DropmusicDataType.class;
	}


}
