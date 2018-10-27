package pt.onept.dropmusic.dataserver;

import pt.onept.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.dropmusic.common.communication.protocol.Message;
import pt.onept.dropmusic.common.utililty.JsonUtility;
import pt.onept.dropmusic.dataserver.database.DatabaseHandler;

public class Dataserver {

	public static void main(String[] args) {
		DatabaseHandler dataBase;
		String db = "onept.pt:5432/dropmusic";
		String dbUser = "dropmusicDBuser";
		String dbUserPassword = "dropmusicDBpassword";
		String txMultiCastAddress = "224.3.2.2";
		String rxMultiCastAddress = "224.3.2.1";
		int multiCastPort = 4321;

		dataBase = new DatabaseHandler("jdbc:postgresql://" + db, dbUser, dbUserPassword);
		//dataBase.executeSqlStatement(dataBase.getSqlScriptFromFile("recreate_dropmusic_sql.postgres.sql"));


		MulticastHandler multicastHandler = new MulticastHandler(txMultiCastAddress, rxMultiCastAddress, multiCastPort);
		Message receivedMessage;
		while (true) {

			receivedMessage = multicastHandler.receive();
			System.out.println(JsonUtility.toJson(receivedMessage));
		}
	}
}