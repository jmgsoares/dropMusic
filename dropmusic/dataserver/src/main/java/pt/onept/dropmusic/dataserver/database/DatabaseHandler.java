package pt.onept.dropmusic.dataserver.database;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

import oracle.jdbc.pool.OracleConnectionPoolDataSource;
import oracle.jdbc.pool.OracleDataSource;

public class DatabaseHandler {
    private Connection db;

    public DatabaseHandler() {}

    public void getDBConnection () {
        String dbUrl = "jdbc:oracle:thin:bd/bd@onept.pt:65112:XE";
	    try {
		    OracleDataSource ds = new OracleConnectionPoolDataSource();
		    ds.setURL(dbUrl);
		    ds.setDatabaseName("BD");
		    System.out.println(ds.toString());
		    this.db = ds.getConnection();
	    } catch (SQLException e) {
		    e.printStackTrace();
	    }

    }

//    private String getSqlScript () throws NullPointerException{
//        ClassLoader classLoader = getClass().getClassLoader();
//	    URL resource = classLoader.getResource("new_dropmusic_db.sql");
//	    if(resource == null) throw new NullPointerException();
//        File file = new File(resource.getFile());
//	    byte[] bin = new byte[0];
//	    try {
//		    bin = Files.readAllBytes(Paths.get(file.getPath()));
//	    } catch (IOException e) {
//		    e.printStackTrace();
//		    System.out.println("Could not read sql file to create the database");
//	    }
//	    return new String(bin);
//    }

	private String getSqlScript () throws NullPointerException{
		ClassLoader classLoader = getClass().getClassLoader();
		URL resource = classLoader.getResource("new_dropmusic_db.sql");
		if(resource == null) throw new NullPointerException();
		File cs = new File(resource.getPath());
		String sql;
		try {
			sql =  Files.lines(Paths.get(resource.getPath())).collect(Collectors.joining("\n"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Could not read sql file to create the database");
			throw new NullPointerException();
		}
		return sql;
	}


	public void createDB() {
	    try {
		    Statement statement = this.db.createStatement();
		    String data = getSqlScript();

		    statement.execute(data);
		    System.out.println(statement.getConnection().isValid(1000));
	    } catch (SQLException e) {
		    e.printStackTrace();
		    System.out.println("Could not create the database");
	    }

    }

    public boolean executeSql(String sql) throws SQLException {
        Statement statement = this.db.createStatement();
        return statement.execute(sql);
    }

    public ResultSet querySql(String sql) throws SQLException {
        Statement statement = this.db.createStatement();
        return statement.executeQuery(sql);
    }
}
