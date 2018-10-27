package pt.onept.dropmusic.common.utililty;

import java.io.*;

public final class JavaSerializationUtility {

	public static byte[] serialize(Serializable object) throws IOException {
		try (
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)
		) {
			objectOutputStream.writeObject(object);
			objectOutputStream.flush();
			return byteArrayOutputStream.toByteArray();
		}
	}

	public static Object deserialize(byte[] serializedObject) throws IOException, ClassNotFoundException {
		Object object = null;
		try (
				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(serializedObject);
				ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)
		) {
			return objectInputStream.readObject();
		}
	}
}
