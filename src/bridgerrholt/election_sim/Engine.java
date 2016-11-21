package bridgerrholt.election_sim;

import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;

import java.sql.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.util.Map;

import com.google.gson.Gson;

import bridgerrholt.helpers.Helpers;
import bridgerrholt.sqlite_interface.Database;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;


public class Engine {
	public Engine() {

	}

	public void run(String simulationPath) throws Exception {
		if (!simulationPath.endsWith("/") && !simulationPath.endsWith("\\")) {
			simulationPath = simulationPath.concat("/");
		}

		Execution execution = new Execution(
			simulationPath.concat(databaseFileName),
			simulationPath.concat(settingsFileName)
		);
		execution.run();
	}

	private static String databaseFileName = "database.db";
	private static String settingsFileName = "settings.json";
}


class Execution {
	Execution(String databasePath, String settingsPath) throws Exception {
		connection = Database.createConnection(databasePath);

		SetupReader.execute(connection, "database_setup.txt");

		// Read the provided JSON file containing settings for the simulation.
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new FileReader(settingsPath));
		JsonElement json = gson.fromJson(reader, JsonElement.class);
		JsonObject root = json.getAsJsonObject();

		// Loop through each object.
		for (Map.Entry<String, JsonElement> entry : root.entrySet()) {
			if (entry.getKey().equals("scaleDepth")) {
				scale = new Scale(entry.getValue().getAsInt());
			}
		}
	}

	void run() throws Exception {
		/*

		// Each region's choices.
		for i in sql("SELECT * FROM regions)
			calculateCandidateOrder(sql("SELECT * FROM public_opinion WHERE region_id = {i.rowid}))

		// Total population choices.
		calculateCandidateOrder(sql("SELECT * FROM public_opinions))

		*/
	}


	private Connection connection;
	private Scale scale = new Scale(2);
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
