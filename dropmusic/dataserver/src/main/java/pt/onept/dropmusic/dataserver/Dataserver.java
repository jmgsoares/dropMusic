package pt.onept.dropmusic.dataserver;

import pt.onept.dropmusic.dataserver.database.DatabaseHandler;

import java.io.IOException;

public class Dataserver {

	public static void main(String[] args) throws IOException {
		DatabaseHandler db;
		db = new DatabaseHandler();
		db.getDBConnection();
		db.createDB();

	}
}
