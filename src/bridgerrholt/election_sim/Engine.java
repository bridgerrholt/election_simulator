package bridgerrholt.election_sim;

import java.util.List;
import java.util.ArrayList;

import java.sql.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;

import bridgerrholt.helpers.Helpers;
import bridgerrholt.sqlite_interface.Database;

public class Engine {
	public Engine() {
		topics     = new ArrayList<>();
		candidates = new ArrayList<>();
	}

	public Engine(ArrayList<Topic> topics, ArrayList<Candidate> candidates) {
		if (topics == null)
			this.topics = new ArrayList<>();
		else
			this.topics = topics;

		if (candidates == null)
			this.candidates = new ArrayList<>();
		else
			this.candidates = candidates;
	}

	public void setup(String databasePath) throws Exception {
		connection = Database.createConnection(databasePath);

		SetupReader setupReader = new SetupReader(connection, "database_setup.txt");
	}


	private List<Topic>     topics;
	private List<Candidate> candidates;
	private Connection      connection;
}



class SetupReader {
	SetupReader(Connection connection, String setupPath) throws Exception {
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
