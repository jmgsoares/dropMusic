package pt.onept.dropmusic.dataserver;

import pt.onept.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.dropmusic.dataserver.database.DatabaseHandler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Dataserver {

	public static void main(String[] args) {
		DatabaseHandler dataBase;
		String db = "onept.pt:65112/dropmusic";
		String dbUser = "dropmusicDBuser";
		String dbUserPassword = "dropmusicDBpassword";
		String multiCastAddress = "224.3.2.1";
		int multiCastPort = 4321;

		BlockingQueue<String> received = new ArrayBlockingQueue<>(100);



		MulticastHandler multicastHandler = new MulticastHandler(multiCastAddress, multiCastPort);
		multicastHandler.messageReceiver(received);

		dataBase = new DatabaseHandler("jdbc:postgresql://" + db, dbUser, dbUserPassword);
		//dataBase.executeSqlStatement(dataBase.getSqlScriptFromFile("recreate_dropmusic_sql.postgres.sql"));

	}
}
