package pt.onept.dropmusic.dataserver.database;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DatabaseConnector {
	private String url;
	private String dbUser;
	private String dbUserPassword;

	public DatabaseConnector(String url, String dbUser, String dbUserPassword) {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Driver for DB not found");
		}
		this.url = url;
		this.dbUser = dbUser;
		this.dbUserPassword = dbUserPassword;
	}

	public Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(this.url, this.dbUser, this.dbUserPassword);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Unable to connect to the specified URL" + url + "with user/password" + dbUser + "/" + dbUserPassword);
		}
		return connection;
	}

	private String getSqlScriptFromFile(String file) throws NullPointerException {
		byte[] encoded = null;
		Stream<String> stream = new BufferedReader(
				new InputStreamReader(ClassLoader.getSystemResourceAsStream(file)))
				.lines();
		encoded = stream.collect(Collectors.joining("")).getBytes();
		return new String(encoded);
	}

	public void executeSqlScript(String filePath) {
		try {
			String script = getSqlScriptFromFile(filePath);
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			statement.executeUpdate(script);
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Could not create the database");
		}
	}
}
