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

		List<String> lines = Files.readAllLines(
			Paths.get("database_setup.txt"), Charset.defaultCharset()
		);

		for (String i : lines) {
			Statement statement = connection.createStatement();
			while (statement.execute(i)) {

			}
		}



	}


	private List<Topic>     topics;
	private List<Candidate> candidates;
	private Connection      connection;
}


