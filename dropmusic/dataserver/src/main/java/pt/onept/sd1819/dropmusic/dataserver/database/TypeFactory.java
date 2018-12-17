package pt.onept.sd1819.dropmusic.dataserver.database;

import pt.onept.sd1819.dropmusic.common.server.contract.type.*;

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
						.setDescription(rs.getString("description"))
						.setScore(rs.getFloat("score"));
			} else if (tClass.equals(Artist.class)) {
				((Artist) object)
						.setId(rs.getInt("id"))
						.setName(rs.getString("name"));

			} else if (tClass.equals(File.class)) {
				((File) object)
						.setId(rs.getInt("id"))
						.setDropBoxFileId(rs.getString("dropbox_file_id"))
						.setDropBoxFileName(rs.getString("dropbox_file_name"))
						.setDropBoxFilePath(rs.getString("dropbox_file_path"))
						.setDropBoxPrevUrl(rs.getString("dropbox_prev_url"));

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
						.setDropBoxUid(rs.getString("dropbox_uid"))
						.setDropBoxToken(rs.getString("dropbox_token"))
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
		if (object instanceof File) return File.class;
		if (object instanceof Music) return Music.class;
		if (object instanceof Notification) return Notification.class;
		if (object instanceof Review) return Review.class;
		if (object instanceof User) return User.class;
		return DropmusicDataType.class;
	}


}
