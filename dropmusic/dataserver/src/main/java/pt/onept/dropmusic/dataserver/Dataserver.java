package pt.onept.dropmusic.dataserver;

import pt.onept.dropmusic.dataserver.database.DatabaseHandler;

import java.io.IOException;

import java.sql.SQLException;


public class Dataserver {

    public static void main(String[] args) throws IOException {
        DatabaseHandler db;
        db = new DatabaseHandler();
        try {
            db.getDBConnection();
            db.createDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
