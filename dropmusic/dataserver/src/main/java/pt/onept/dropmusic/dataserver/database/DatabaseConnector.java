package pt.onept.dropmusic.dataserver.database;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

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
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			URL resource = classLoader.getResource(file);
			if (resource == null) throw new NullPointerException();
			encoded = Files.readAllBytes(Paths.get(new File(resource.getFile()).getPath()));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Could not read sql file to create the database");
		}
		return new String(encoded != null ? encoded : new byte[0]);
	}

	public void createDB(String filePath) {
		try {
			String createScript = getSqlScriptFromFile(filePath);

			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			statement.executeUpdate(createScript);
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Could not create the database");
		}
	}
}
