package pt.onept.dropmusic.dataserver;

import pt.onept.dropmusic.dataserver.database.DatabaseHandler;

public class Dataserver {

	public static void main(String[] args) {
		DatabaseHandler dataBase;
		String db = "onept.pt:65112/dropmusic";
		String dbUser = "dropmusicDBuser";
		String dbUserPassword = "dropmusicDBpassword";

		dataBase = new DatabaseHandler("jdbc:postgresql://" + db, dbUser, dbUserPassword);
		//dataBase.executeSqlStatement(dataBase.getSqlScriptFromFile("recreate_dropmusic_sql.postgres.sql"));

	}
}
