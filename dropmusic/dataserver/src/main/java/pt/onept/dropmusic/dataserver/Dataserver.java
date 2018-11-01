package pt.onept.dropmusic.dataserver;

import pt.onept.dropmusic.common.communication.multicast.MulticastHandler;
import pt.onept.dropmusic.common.utililty.PropertiesReaderUtility;
import pt.onept.dropmusic.dataserver.database.DatabaseConnector;
import pt.onept.dropmusic.dataserver.database.DatabaseManager;

import java.util.Properties;


public class Dataserver {
	static boolean isMaster;
	private static String db;
	private static String dbUser;
	private static String dbUserPassword;
	private static String txMultiCastAddress;
	private static String rxMultiCastAddress;
	private static int multiCastPort;
	private static int failOverTime;
	private static boolean cleanAndPopulate;

	public static void main(String[] args) {
		MulticastHandler multicastHandler;
		DatabaseConnector databaseConnector;
		DatabaseManager databaseManager;

		Properties appProps = PropertiesReaderUtility.read("dataserver.properties");
		Dataserver.db = appProps.getProperty("db");
		Dataserver.dbUser = appProps.getProperty("dbUser");
		Dataserver.dbUserPassword = appProps.getProperty("dbUserPassword");
		Dataserver.txMultiCastAddress = appProps.getProperty("txMultiCastAddress");
		Dataserver.rxMultiCastAddress = appProps.getProperty("rxMultiCastAddress");
		Dataserver.isMaster = Boolean.parseBoolean(appProps.getProperty("isMaster"));
		Dataserver.multiCastPort = Integer.parseInt(appProps.getProperty("multiCastPort"));
		Dataserver.failOverTime = Integer.parseInt(appProps.getProperty("failOverTime"));
		Dataserver.cleanAndPopulate = Boolean.parseBoolean(appProps.getProperty("cleanAndPopulate"));

		if (isMaster) System.out.println("DataServer in primary mode");
		else System.out.println("DataServer in backup mode");

		databaseConnector = new DatabaseConnector("jdbc:postgresql://" + db, dbUser, dbUserPassword);

		if (cleanAndPopulate) {
			System.out.println("Cleaning and populating db @ " + db);
			databaseConnector.executeSqlScript("createDB.sql");
			databaseConnector.executeSqlScript("dummy_data.sql");
		}

		multicastHandler = new MulticastHandler(txMultiCastAddress, rxMultiCastAddress, multiCastPort, failOverTime);
		databaseManager = new DatabaseManager(databaseConnector);
		new Thread(new MessageHandler(databaseManager, multicastHandler)).start();
	}
}