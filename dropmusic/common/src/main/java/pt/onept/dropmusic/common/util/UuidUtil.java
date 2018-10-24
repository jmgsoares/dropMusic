package pt.onept.dropmusic.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public class UuidUtil {
	public UuidUtil() {
	}

	public byte[] AddUuidBuffer(UUID uuid, byte[] buffer) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		output.write(uuid.toString().getBytes());
		output.write(buffer);
		return output.toByteArray();
	}
}
