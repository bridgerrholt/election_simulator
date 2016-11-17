package bridgerrholt.sqlite_interface;

import java.sql.*;

import java.util.HashMap;
import java.util.ArrayList;

public class Database {
	/*private String     fileName;
	private Connection connection = null;


	public Database(String fileName) throws Exception {
		// Fail if JDBC is not found.
		Class.forName("org.sqlite.JDBC");

		this.fileName = "jdbc:sqlite:" + fileName;

		connection = DriverManager.getConnection(this.fileName);
	}


	public void close() {
		try {
			connection.close();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	public Statement createStatement() {
		return connection.createStatement();
	}

	public Statement prepareStatement(String statement) {
		return connection.prepareStatement(statement);
	}


	public ResultSet executeQuery(String contents) throws Exception {
		Statement stmt = getConnection().createStatement();
		ResultSet toReturn = stmt.executeQuery(contents);
		stmt.close();
		return toReturn;
	}

	private String generateSelectFrom(String contents, String tableName) {
		return "SELECT ".concat(contents).concat(" FROM ").concat(tableName);
	}*/

	public static Connection createConnection(String fileName)
			throws Exception {

		// Fail if JDBC is not found.
		Class.forName("org.sqlite.JDBC");

		return DriverManager.getConnection("jdbc:sqlite:".concat(fileName));
	}

	/*public static createRowList(ResultSet)

	ArrayList<HashMap<String, Object> > introList = new ArrayList<>();

			while (introSet.next()) {
		int index = introList.size();
		introList.add(new HashMap<>());

		introList.get(index).put("id", introSet.getObject());
		introList.get(index).put("listId", introSet.getInt("id"));
		introList.get(index).put("nextListId", introSet.getInt("id"));
		introList.get(index).put("type", introSet.getInt("id"));
		introList.get(index).put("id", introSet.getInt("id"));
	}*/


}
