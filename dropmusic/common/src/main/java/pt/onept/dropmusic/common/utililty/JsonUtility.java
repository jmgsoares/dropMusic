package pt.onept.dropmusic.common.utililty;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public final class JsonUtility {
	private static Gson gson = new Gson();
	private static Gson gsonp = new GsonBuilder().setPrettyPrinting().create();

	private JsonUtility() {
	}

	public static String toJson(Object object) {
		return gson.toJson(object);
	}

	public static String toPrettyJson(Object object) {
		return gsonp.toJson(object);
	}

	public static <T> T fromJson(String json, Class<T> t) throws JsonSyntaxException {
		return gson.fromJson(json, t);
	}
}
