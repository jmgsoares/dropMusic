package pt.onept.sd1819.dropmusic.common.utililty;

import java.io.*;

/**
 * Class with support methods to serialize e deserialize the message to exchange via multicast
 * @see pt.onept.sd1819.dropmusic.common.communication.protocol.Message
 */
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
		try (
				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(serializedObject);
				ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)
		) {
			return objectInputStream.readObject();
		}
	}
}
