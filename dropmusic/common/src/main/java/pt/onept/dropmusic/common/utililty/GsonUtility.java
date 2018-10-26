package pt.onept.dropmusic.common.utililty;

import com.google.gson.Gson;
import pt.onept.dropmusic.common.server.contract.type.Message;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class GsonUtility {

	private GsonUtility() {}

	public static <T> String toGson(UUID uuid, T object, String operation) {
		Gson gson = new Gson();
		Set<T> dataList = new HashSet<>();
		dataList.add(object);
		Message<T> message = new Message<T>(uuid, operation, object.getClass().toString(),dataList);
		return gson.toJson(message);
	}

	public static <T> String toGson(UUID uuid, Set<T> objectSet, String operation) {
		Gson gson = new Gson();
		Message<T> message = new Message<>(uuid, operation, objectSet.getClass().toString(),objectSet);
		return gson.toJson(message);
	}

	public static Message fromGson(String message) {
		Gson gson = new Gson();
		return gson.fromJson(message, Message.class);
	}
}
