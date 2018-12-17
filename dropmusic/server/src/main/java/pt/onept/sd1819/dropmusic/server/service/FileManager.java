package pt.onept.sd1819.dropmusic.server.service;

import org.json.JSONArray;
import org.json.JSONObject;
import pt.onept.sd1819.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.sd1819.dropmusic.common.communication.protocol.Message;
import pt.onept.sd1819.dropmusic.common.communication.protocol.MessageBuilder;
import pt.onept.sd1819.dropmusic.common.communication.protocol.Operation;
import pt.onept.sd1819.dropmusic.common.exception.*;
import pt.onept.sd1819.dropmusic.common.server.contract.subcontract.FileManagerInterface;
import pt.onept.sd1819.dropmusic.common.server.contract.type.File;
import pt.onept.sd1819.dropmusic.common.server.contract.type.User;
import pt.onept.sd1819.dropmusic.server.service.rest.DropBox20;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Class implements the FileManagerInterface for the DropBox API 20 and specific application file related operations with the dataServer
 * We refer to remote files to address the files a user has on the dropBox and local the files the user linked from his dropBox
 * Shared files are the ones that other users shared with "this" user
 * @see DropBox20
 */
public class FileManager extends UnicastRemoteObject implements FileManagerInterface {

	private MulticastHandler multicastHandler;

	/**
	 * Constructor
	 * @param multicastHandler multicast communication handler
	 * @throws RemoteException upon any RMI error
	 */
	public FileManager(MulticastHandler multicastHandler) throws RemoteException {
		super();
		this.multicastHandler = multicastHandler;
	}

	/**
	 * Function to list the remote files a specific user has
	 * @param self the user whom we want to retrieve the file list
	 * @return the user remote file list
	 * @throws RemoteException upon any RMI error
	 */
	@Override
	public List<File> listRemoteFiles(User self) throws RemoteException {
		List<File> fileList = new ArrayList<>();
		JSONArray remoteFileList = DropBox20.listUserFiles(self.getDropBoxToken());
		if (remoteFileList != null) {
			for (Object object : remoteFileList) {
				fileList.add(new File()
						.setDropBoxFileId(((JSONObject)object).getString("id"))
						.setDropBoxFilePath(((JSONObject)object).getString("path_lower"))
						.setDropBoxFileName(((JSONObject)object).getString("name"))
				);
			}
		}
		return fileList;
	}

	/**
	 * Links a file from the user dropBox with a music from the system
	 * @param self the current user
	 * @param object the remote file to link
	 * @throws NotFoundException if the file isn't found
	 * @throws DuplicatedException on duplicate link to the same remote file
	 * @throws UnauthorizedException if user not authorized to perform the operation
	 * @throws RemoteException upon any RMI error
	 * @throws DataServerException if any problem occurred processing the request in dataServer
	 */
	@Override
	public void linkRemoteFile(User self, File object) throws NotFoundException, DuplicatedException, UnauthorizedException, RemoteException, DataServerException {
		JSONObject remoteFileMetaData = DropBox20.getSharedFileMetaData(object.getDropBoxFileId(),self.getDropBoxToken());
		if (remoteFileMetaData == null) throw new NotFoundException();

		object.setDropBoxFileName(remoteFileMetaData.getString("name"))
				.setDropBoxPrevUrl(remoteFileMetaData.getString("preview_url").replace("?dl=0","?dl=1"))
				.setDropBoxFilePath(remoteFileMetaData.getString("path_display"));

		Message outgoing = MessageBuilder.build(Operation.LINK_FILE, self)
				.setData(object);

		try {
			Message incoming = multicastHandler.sendAndWait(outgoing);
			switch (incoming.getOperation()) {
				case SUCCESS:
					break;
				case NO_PERMIT:
					throw new UnauthorizedException();
				case DUPLICATE:
					throw new DuplicatedException();
				case EXCEPTION:
					throw new RemoteException();
			}
		} catch (TimeoutException e) {
			System.out.println("NO SERVER ANSWER!");
			throw new DataServerException();
		}
	}

	/**
	 * Lists the files other users shared with the current user
	 * @param self the current user
	 * @return Shared files list
	 * @throws RemoteException upon any RMI error
	 * @throws DataServerException if any problem occurred while processing the request in dataServer
	 */
	@Override
	public List<File> listSharedFiles(User self) throws RemoteException, DataServerException {
		List<File> fileList;
		Message incoming;
		Message outgoing = MessageBuilder.build(Operation.LIST_SHARES, self);
		try {
			incoming = multicastHandler.sendAndWait(outgoing);
			fileList = incoming.getDataList();
			switch (incoming.getOperation()) {
				case SUCCESS:
					break;
				case EXCEPTION:
					throw new DataServerException();
			}
		} catch (TimeoutException e) {
			System.out.println("NO SERVER ANSWER!");
			throw new DataServerException();
		}
		return fileList;
	}

	/**
	 * Shares a file that the current user previously associated with a music, with a target user
	 * @param self the current user
	 * @param file the file to be shared
	 * @param targetUser the target user
	 * @throws RemoteException upon any RMI error
	 * @throws DataServerException if any problem occurred processing the request in dataServer
	 * @throws OAuthException upon any Rest request error
	 */
	@Override
	public void shareFile(User self, File file, User targetUser) throws RemoteException, DataServerException, OAuthException {
		Message incomingUserDetails;
		Message outgoingUserDetailRequest = MessageBuilder.build(Operation.READ, self)
				.setData(targetUser);
		Message incomingFileDetails;
		Message outgoingFileDetailRequest = MessageBuilder.build(Operation.READ, self)
				.setData(file);
		try {
			incomingUserDetails = this.multicastHandler.sendAndWait(outgoingUserDetailRequest);
			targetUser = (User) incomingUserDetails.getData();
			if(targetUser.getDropBoxUid()==null) throw new OAuthException();
			incomingFileDetails = this.multicastHandler.sendAndWait(outgoingFileDetailRequest);
			file = (File) incomingFileDetails.getData();

			DropBox20.shareFile(file.getDropBoxFileId(),targetUser.getDropBoxUid(),self.getDropBoxToken());

			Message outgoing = MessageBuilder.build(Operation.SHARE, self)
					.setData(file)
					.setTarget(targetUser);

			Message incoming = multicastHandler.sendAndWait(outgoing);
			switch (incoming.getOperation()) {
				case SUCCESS:
					break;
				case EXCEPTION:
					throw new DataServerException();
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Lists all the files the user has linked from dropBox to DropMusic musics
	 * @param self the current user
	 * @return the local file list
	 * @throws RemoteException upon any RMI error
	 * @throws DataServerException if any problem occurred while processing the request in dataServer
	 */
	@Override
	public List<File> list(User self) throws RemoteException, DataServerException {
		List<File> fileList;
		Message incoming;
		Message outgoing = MessageBuilder.build(Operation.LIST, self)
				.setData(new File());
		try {
			incoming = multicastHandler.sendAndWait(outgoing);
			fileList = incoming.getDataList();
			switch (incoming.getOperation()) {
				case SUCCESS:
					break;
				case EXCEPTION:
					throw new DataServerException();
			}
		} catch (TimeoutException e) {
			System.out.println("NO SERVER ANSWER!");
			throw new DataServerException();
		}
		return fileList;
	}
}
