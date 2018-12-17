package pt.onept.sd1819.dropmusic.server.service;

import org.json.JSONArray;
import org.json.JSONObject;
import pt.onept.sd1819.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.sd1819.dropmusic.common.communication.protocol.Message;
import pt.onept.sd1819.dropmusic.common.communication.protocol.MessageBuilder;
import pt.onept.sd1819.dropmusic.common.communication.protocol.Operation;
import pt.onept.sd1819.dropmusic.common.exception.DataServerException;
import pt.onept.sd1819.dropmusic.common.exception.DuplicatedException;
import pt.onept.sd1819.dropmusic.common.exception.NotFoundException;
import pt.onept.sd1819.dropmusic.common.exception.UnauthorizedException;
import pt.onept.sd1819.dropmusic.common.server.contract.subcontract.FileManagerInterface;
import pt.onept.sd1819.dropmusic.common.server.contract.type.File;
import pt.onept.sd1819.dropmusic.common.server.contract.type.User;
import pt.onept.sd1819.dropmusic.server.service.rest.DropBox20;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class FileManager extends UnicastRemoteObject implements FileManagerInterface {

	private MulticastHandler multicastHandler;

	public FileManager(MulticastHandler multicastHandler) throws RemoteException {
		super();
		this.multicastHandler = multicastHandler;
	}

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


	@Override
	public void linkRemoteFile(User self, File object) throws NotFoundException, DuplicatedException, UnauthorizedException, RemoteException, DataServerException {
		JSONObject remoteFileMetaData = DropBox20.getSharedFileMetaData(object.getDropBoxFileId(),self.getDropBoxToken());
		if (remoteFileMetaData == null) throw new NotFoundException();
		object.setDropBoxFileName(remoteFileMetaData.getString("name"))
				.setDropBoxPrevUrl(remoteFileMetaData.getString("preview_url"))
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
					throw new RemoteException();
			}
		} catch (TimeoutException e) {
			System.out.println("NO SERVER ANSWER!");
			throw new DataServerException();
		}
		return fileList;
	}
}
