package pt.onept.sd1819.dropmusic.server.service.rest;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * DropBox rest service to handle all the calls to the API
 * @author João Soares
 * @version 1.0
 */
public final class DropBox20 {

	/**
	 * DropBox API APP Key
	 */
	private static final String API_APP_KEY = "720jq03bm89t8fp";

	/**
	 * DropBox API APP Secret
	 */
	private static final String API_APP_SECRET = "btm3iho1dwtgw32";

	/**
	 * DropBox API APP Folder (This is used in both contexts (remote and local)
	 */
	private static final String APP_FOLDER = "/SD_UC_1819";

	/**
	 * DropBox Authorization URL
	 */
	private static final String AUTHORIZATION_URI = "https://www.dropbox.com/1/oauth2/authorize";

	/**
	 * APP Callback URL
	 */
	private static final String CALLBACK = "http://localhost:8080/dropmusic/dropBoxOA20";
	private static final boolean LOCAL = true;
	//private static final String CALLBACK = "https://onept.pt:8443/dropmusic/dropBoxOA20";

	private DropBox20( ) { }

	/**
	 * Lists the file the user has inside the "App" folder.
	 * @return An array with all the files in the folder
	 */
	public static JSONArray listUserFiles(String userToken) {
		try {
			JSONObject bodyParams = new JSONObject();
			bodyParams.put("path", DropBox20.APP_FOLDER);
			bodyParams.put("recursive", false);
			bodyParams.put("include_media_info", false);
			bodyParams.put("include_deleted", false);
			bodyParams.put("include_has_explicit_shared_members", false);
			bodyParams.put("include_mounted_folders", true);

			HttpResponse<JsonNode> response = Unirest.post("https://api.dropboxapi.com/2/files/list_folder")
					.header("Authorization", "Bearer " + userToken)
					.header("Content-Type", "application/json")
					.body(bodyParams)
					.asJson();
			return response.getBody().getObject().getJSONArray("entries");
		} catch (UnirestException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Lists the files other users shared with the user
	 * @return An array with all the files other users shared with the user
	 */
	public static JSONArray listSharedFiles(String userToken) {
		try {
			JSONObject bodyParams = new JSONObject();
			bodyParams.put("limit", 100);

			HttpResponse<JsonNode> response = Unirest.post(
					"https://api.dropboxapi.com/2/sharing/list_received_files")
					.header("Authorization", "Bearer " + userToken)
					.header("Content-Type", "application/json")
					.body(bodyParams)
					.asJson();
			return response.getBody().getObject().getJSONArray("entries");
		} catch (UnirestException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Uploads a file to the user "App" folder
	 * @param pathToAppFolder Path to the folder where the source file is located
	 * @param fileName Name of the file to upload
	 * @return A JsonObject with the uploaded file information
	 */
	public static JSONObject uploadFile(String pathToAppFolder, String fileName, String userToken) {
		try {
			JSONObject filePath = new JSONObject();
			filePath.put("path", APP_FOLDER + "/" + fileName);
			File file = new File(pathToAppFolder + APP_FOLDER + "/" + fileName);
			HttpResponse<JsonNode> response = Unirest.post("https://content.dropboxapi.com/2/files/upload")
					.header("Authorization", "Bearer " + userToken)
					.header("Content-Type", "application/octet-stream")
					.header("Dropbox-API-Arg", filePath.toString())
					.body(Files.readAllBytes(file.toPath()))
					.asJson();
			return response.getBody().getObject();
		} catch (UnirestException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Deletes a file from the user "App" folder
	 * @param fileName Name of the file to delete
	 * @return The success of the operation
	 */
	public static boolean deleteFile(String fileName, String userToken) {
		try {
			JSONObject filePath = new JSONObject();
			filePath.put("path", APP_FOLDER + "/" + fileName);

			HttpResponse<JsonNode> response = Unirest.post("https://api.dropboxapi.com/2/files/delete_v2")
					.header("Authorization", "Bearer " + userToken)
					.header("Content-Type", "application/json")
					.body(filePath)
					.asJson();
			return !response.getBody().toString().contains("error_summary");
		} catch (UnirestException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Gets a file form the user "App" folder
	 * @param pathToLocalAppFolder Path to the local folder where to store the file
	 * @param fileName Name of the file to download
	 * @return A JsonObject with the downloaded file information
	 */
	public static JSONObject getFile(String pathToLocalAppFolder, String fileName, String userToken) {
		try {
			JSONObject filePath = new JSONObject();
			filePath.put("path", DropBox20.APP_FOLDER + "/" + fileName);
			HttpResponse<InputStream> response = Unirest.post(
					"https://content.dropboxapi.com/2/files/download")
					.header("Authorization", "Bearer " + userToken)
					.header("Content-Type", "application/octet-stream")
					.header("Dropbox-API-Arg", filePath.toString())
					.asBinary();
			JSONObject responseApiResult = new JSONObject(
					response.getHeaders().getFirst("dropbox-api-result"));

			DropBox20.saveFileLocally(
					response.getRawBody(),
					pathToLocalAppFolder,
					responseApiResult.get("name").toString());

			return responseApiResult;
		} catch (UnirestException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Gets the meta data of a shared file
	 * @param fileId Dropbox file Id
	 * @return A JsonObject with the shared file information
	 */
	public static JSONObject getSharedFileMetaData(String fileId, String userToken) {
		try {
			JSONObject filePath = new JSONObject();
			filePath.put("file", fileId);

			HttpResponse<JsonNode> response = Unirest.post("https://api.dropboxapi.com/2/sharing/get_file_metadata")
					.header("Authorization", "Bearer " + userToken)
					.header("Content-Type", "application/json")
					.body(filePath)
					.asJson();
			return response.getBody().getObject();
		} catch (UnirestException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Gets a file shared by other users
	 * @param pathToLocalAppFolder Path to the local folder where to store the file
	 * @param fileUrl Dropbox preview url
	 * @return A JsonObject with the downloaded file information
	 */
	public static JSONObject getSharedFile(String pathToLocalAppFolder, String fileUrl, String userToken) {
		try {
			JSONObject dropBoxApiArgs = new JSONObject();
			dropBoxApiArgs.put("url", fileUrl);
			HttpResponse<InputStream> response = Unirest.post(
					"https://content.dropboxapi.com/2/sharing/get_shared_link_file")
					.header("Authorization", "Bearer " + userToken)
					.header("Dropbox-API-Arg", dropBoxApiArgs.toString())
					.asBinary();

			JSONObject responseApiResult = new JSONObject(
					response.getHeaders().getFirst("dropbox-api-result"));

			DropBox20.saveFileLocally(
					response.getRawBody(),
					pathToLocalAppFolder,
					responseApiResult.get("name").toString());

			return responseApiResult;
		} catch (UnirestException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Shares a file with a specific user
	 * @param fileId The dropbox file id to share
	 * @param userId ID of the user whom to share the file with
	 * @return A JsonObject with the result of the operation
	 */
	public static JSONObject shareFile(String fileId, String userId, String userToken) {
		try {
			JSONObject bodyParams = new JSONObject();
			JSONArray members = new JSONArray();
			JSONObject user = new JSONObject();

			bodyParams.put("file", fileId);
			user.put(".tag", "dropbox_id");
			user.put("dropbox_id", userId);
			members.put(user);
			bodyParams.put("members",members);
			bodyParams.put("custom_message", "This is a share from DropMusic");
			bodyParams.put("quiet", false);
			bodyParams.put("access_level", "viewer");

			HttpResponse<JsonNode> response = Unirest.post(
					"https://api.dropboxapi.com/2/sharing/add_file_member")
					.header("Authorization", "Bearer " + userToken)
					.header("Content-Type", "application/json")
					.body(bodyParams)
					.asJson();
			return response.getBody().getArray().getJSONObject(0);
		} catch (UnirestException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Unshares a file with a specific user
	 * @param fileId The dropbox file id to unshare
	 * @param eMail Email of the user whom to unshare the file with
	 * @return A JsonObject with the result of the operation
	 */
	public JSONObject unShareFile(String fileId, String eMail, String userToken) {
		try {
			JSONObject bodyParams = new JSONObject();
			JSONObject user = new JSONObject();

			bodyParams.put("file", fileId);
			user.put(".tag", "email");
			user.put("email", eMail);
			bodyParams.put("member", user);

			HttpResponse<JsonNode> response = Unirest.post(
					"https://api.dropboxapi.com/2/sharing/remove_file_member_2")
					.header("Authorization", "Bearer " + userToken)
					.header("Content-Type", "application/json")
					.body(bodyParams)
					.asJson();
			return response.getBody().getObject();
		} catch (UnirestException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Authorization URL builder function
	 * @return The authorization URL
	 */
	public static String getAuthorizationUrl() {
		return DropBox20.AUTHORIZATION_URI
				+ "?client_id="
				+ DropBox20.API_APP_KEY
				+ "&response_type=code&redirect_uri="
				+ DropBox20.CALLBACK;
	}

	/**
	 * Function to exchange the user authorization code for an access token
	 * @param authorizationCode the authorization code to exchange
	 * @return A JsonObject with the operation result (mainly the accessToken and the user ID)
	 */
	public static JSONObject getAccessTokenResponse(String authorizationCode) {
		try {
			HttpResponse<JsonNode> response = Unirest.post("https://api.dropbox.com/1/oauth2/token")
					.header("Content-Type", "application/x-www-form-urlencoded")
					.field("code", authorizationCode)
					.field("grant_type", "authorization_code")
					.field("redirect_uri", DropBox20.CALLBACK)
					.basicAuth(DropBox20.API_APP_KEY, DropBox20.API_APP_SECRET)
					.asJson();
			return response.getBody().getObject();
		} catch (UnirestException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Function to save inputstream to local filesystem
	 * @param inputStream The inputStream to save
	 * @param pathToLocalAppFolder Path where to save the file
	 * @param fileName The name to give the file
	 */
	private static void saveFileLocally(InputStream inputStream, String pathToLocalAppFolder, String fileName) {
		try {

			File file = new File(pathToLocalAppFolder + DropBox20.APP_FOLDER + "/" + fileName);

			Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean getLocal() { return DropBox20.LOCAL; }
}

