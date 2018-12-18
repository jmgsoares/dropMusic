package pt.onept.sd1819.dropmusic.common.utililty;

import pt.onept.sd1819.dropmusic.common.server.contract.type.*;

public final class SubType {

	private SubType() {
	}

	/**
	 * Returns the object parent class
	 * @param object object
	 * @param <T> Type class
	 * @return the Class of the object
	 */
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
