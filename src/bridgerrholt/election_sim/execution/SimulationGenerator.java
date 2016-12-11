package bridgerrholt.election_sim.execution;

import bridgerrholt.election_sim.SettingsReader;
import bridgerrholt.sqlite_interface.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class SimulationGenerator implements Execution {
	public SimulationGenerator(String simulationFileName,
	                           String generatorFileName,
	                           SettingsReader settings) throws Exception {
		simulationConnection = Database.createConnection(simulationFileName);
		generatorConnection  = Database.createConnection(generatorFileName);
	}

	public void run() throws Exception {
		Statement statement = simulationConnection.createStatement();
		Database.clearTable(simulationConnection, "opinion_lists");
		Database.clearTable(simulationConnection, "regional_opinions");


		PreparedStatement overview = generatorConnection.prepareStatement(
			"SELECT * FROM regional_opinion_overview"
		);

		while (overview.execute()) {
			ResultSet resultSet = overview.getResultSet();

			int topicId  = resultSet.getInt("topic_id");
			int regionId = resultSet.getInt("region_id");

			ArrayList<Integer> peopleCounts = calculateCounts(
				topicId, regionId,
				resultSet.getInt("favored_scale_index"),
				resultSet.getInt("favor_intensity")
			);

			PreparedStatement regionalOpinionInsert = simulationConnection.prepareStatement(
				"INSERT INTO regional_opinions (list_id, scale_index, person_count) " +
				"VALUES (?, ?, ?)"
			);

			for (Integer i : peopleCounts) {

			}
		}

		System.out.println("Generated successfully.");
	}

	public void close() throws java.sql.SQLException {
		simulationConnection.close();
		generatorConnection.close();
	}

	private Connection simulationConnection;
	private Connection generatorConnection;
}
