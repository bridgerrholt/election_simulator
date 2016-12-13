package bridgerrholt.election_sim.execution;

import bridgerrholt.election_sim.SettingsReader;
import bridgerrholt.database_tools.DatabaseInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class SimulationGenerator implements Execution {
	public SimulationGenerator(String simulationFileName,
	                           String generatorFileName,
	                           SettingsReader settings) throws Exception {
		simulationConnection = DatabaseInterface.createConnection(simulationFileName);
		generatorConnection  = DatabaseInterface.createConnection(generatorFileName);
	}

	public void run() throws Exception {
		Statement statement = simulationConnection.createStatement();
		DatabaseInterface.clearTable(simulationConnection, "opinion_lists");
		DatabaseInterface.clearTable(simulationConnection, "regional_opinions");


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

	private ArrayList<Integer> calculateCounts(
		int topicId,
		int regionId,
		int favoredScaleIndex,
		int favorIntensity) throws Exception{

		PreparedStatement populationStatement = simulationConnection.prepareStatement(
			"SELECT population FROM regions WHERE region_id = ?"
		);

		populationStatement.setInt(1, regionId);
		populationStatement.execute();
		ResultSet populationSet = populationStatement.getResultSet();
		int population = populationSet.getInt(1);

		ArrayList<Integer> peopleCounts = new ArrayList<>();

		// x = scale index
		// t = total scale index size
		// s = favoredScaleIndex
		// f = favorIntensity
		// Function goes from y = 1/t to y = (x - t)


		return peopleCounts;
	}

	private Connection simulationConnection;
	private Connection generatorConnection;
}
