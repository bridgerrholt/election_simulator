package bridgerrholt.database_tools;

import java.sql.*;

public class DatabaseInterface {
	/// Creates a connection using the default driver information.
	/// @param fileName The location of the database file, without "jdbc:{subprotocol}".
	public static Connection createConnection(String fileName)
		throws ClassNotFoundException, SQLException {

		return createConnection(fileName,
			getDefaultDriverClass(),
			getdefaultDriverSubProtocol()
		);
	}

	/// Creates a connection using given driver information.
	/// @param fileName The location of the database file, with the driver prefix.
	/// @param driverClassName
	public static Connection createConnection(String fileName,
	                                          String driverClassName,
	                                          String subProtocol)
			throws ClassNotFoundException, SQLException {

		// Fail if the driver is not found.
		Class.forName(driverClassName);

		return DriverManager.getConnection(
			"jdbc:".concat(subProtocol).concat(":").concat(fileName)
		);
	}

	/// Deletes all the rows of a table as well as its primary key data.
	public static void clearTable(Connection connection, String tableName)
			throws SQLException {
		Statement statement = connection.createStatement();
		statement.execute("delete from " + tableName);
		//statement.execute("delete from sqlite_sequence where name='" + tableName + "'");
	}

	public static String getDefaultDriverClass() { return defaultDriverClass; }
	public static String getdefaultDriverSubProtocol() { return defaultDriverSubProtocol; }

	private static String defaultDriverClass       = "org.sqlite.JDBC";
	private static String defaultDriverSubProtocol = "sqlite";
}
