package pt.onept.dropmusic.dataserver.database;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.jdbc.pool.OracleDataSource;

import javax.xml.crypto.Data;

public class DatabaseHandler {
    private Connection db;

    public DatabaseHandler() {}

    public void getDBConnection () throws SQLException {
        String dbUrl = "jdbc:oracle:thin:@onept.pt:1521:XE";
        OracleDataSource ds = new OracleDataSource();
        ds.setURL(dbUrl);
        db = ds.getConnection("bd","bd");
    }

    private String getSqlScript () throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("dropmusic_db.sql").getFile());
        byte[] bin = Files.readAllBytes(Paths.get(file.getPath()));
        return new String(bin);
    }

    public void createDB() throws SQLException, IOException {
        Statement statement = this.db.createStatement();
        statement.executeUpdate(getSqlScript());
    }
}
