package pt.onept.dropmusic.dataserver;

import pt.onept.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.dropmusic.common.communication.protocol.Message;
import pt.onept.dropmusic.common.utililty.JsonUtility;
import pt.onept.dropmusic.dataserver.database.DatabaseConnector;

public class Dataserver {

	public static void main(String[] args) {
		MulticastHandler multicastHandler;
		DatabaseConnector dataBase;
		String db = "onept.pt:5432/dropmusic";
		String dbUser = "dropmusicDBuser";
		String dbUserPassword = "dropmusicDBpassword";
		String txMultiCastAddress = "224.3.2.2";
		String rxMultiCastAddress = "224.3.2.1";
		int multiCastPort = 4321;

		multicastHandler = new MulticastHandler(txMultiCastAddress, rxMultiCastAddress, multiCastPort);
		dataBase = new DatabaseConnector("jdbc:postgresql://" + db, dbUser, dbUserPassword);
		//dataBase.createDB("createDB.sql");
		new Thread(new MessageHandler(dataBase, multicastHandler)).start();

	}
}