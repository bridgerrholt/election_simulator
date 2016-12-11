package bridgerrholt.sqlite_interface;

import java.sql.*;

import java.util.HashMap;
import java.util.ArrayList;

public class Database {
	public static Connection createConnection(String fileName)
			throws Exception {

		// Fail if JDBC is not found.
		Class.forName("org.sqlite.JDBC");

		return DriverManager.getConnection("jdbc:sqlite:".concat(fileName));
	}

	/// Deletes all the rows of a table as well as its primary key data.
	public static void clearTable(Connection connection, String tableName)
			throws SQLException {
		Statement statement = connection.createStatement();
		statement.execute("delete from " + tableName);
		statement.execute("delete from sqlite_sequence where name='" + tableName + "'");
	}
}
