package bridgerrholt.election_sim.execution;

import bridgerrholt.election_sim.Scale;
import bridgerrholt.sqlite_interface.Database;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Simulation implements Execution {
	public Simulation(String databasePath, SettingsReader settings) throws Exception {
		connection = Database.createConnection(databasePath);

		SetupReader.execute(connection, "database_setup.txt");
	}

	public void run() throws Exception {

		Statement regionsStatement = connection.createStatement();

		ResultSet regions = regionsStatement.executeQuery("SELECT * FROM regions");

		while (regions.next()) {
			PreparedStatement singleRegion = connection.prepareStatement(
				"SELECT * FROM public_opinion WHERE region_id = ?");
			singleRegion.setInt(1, regions.getInt("rowid"));

			calculateCandidateOrder(singleRegion);
		}

		/*

		// Each region's choices.
		for i in sql("SELECT * FROM regions)
			calculateCandidateOrder(sql("SELECT * FROM public_opinion WHERE region_id = {i.rowid}))

		// Total population choices.
		calculateCandidateOrder(sql("SELECT * FROM public_opinions))

		*/
	}

	public void close() throws java.sql.SQLException {
		connection.close();
	}

	ArrayList<Integer> calculateCandidateOrder(Statement publicOpinions) {
		/*

		for i in publicOpinions


		*/

		return new ArrayList<>();
	}



	private Connection connection;
}



class SetupReader {
	static void execute(Connection connection, String setupPath) throws Exception {
		List<String> lines = Files.readAllLines(
			Paths.get(setupPath), Charset.defaultCharset()
		);

		State state = State.NONE;

		for (String line : lines) {
			line = line.trim();

			if (line.equals("tables:"))
				state = State.TABLES;

			else {
				Statement statement = connection.createStatement();
				switch (state) {
					case NONE:
						while (statement.execute(line)) { }
						break;

					case TABLES:
						String queryText = "CREATE TABLE IF NOT EXISTS ".concat(line);
						while (statement.execute(queryText)) { }
						break;

					default:
						throw new Exception("Invalid state within SetupReader");
				}
			}
		}
	}


	private enum State {
		NONE,
		TABLES
	}
}