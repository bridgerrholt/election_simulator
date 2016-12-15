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
		int favorIntensity) throws Exception {

		PreparedStatement populationStatement = simulationConnection.prepareStatement(
			"SELECT population FROM regions WHERE region_id = ?"
		);

		populationStatement.setInt(1, regionId);
		populationStatement.execute();
		ResultSet populationSet = populationStatement.getResultSet();
		int population = populationSet.getInt(1);

		int scaleSize = settings.getScale().getSize();

		ArrayList<Integer> peopleCounts = new ArrayList<>(scaleSize);

		// Non-mathematical version
		int    maxPoints     =  100;
		double pointRate     = -(1.0/(scaleSize-1)) * favorIntensity;
		double currentPoints = maxPoints;
		int[]  points = new int[scaleSize];

		for (int i = 0; i < scaleSize; ++i) {
			points[i] = Math.toIntExact(Math.round(currentPoints));
			currentPoints += pointRate;
		}

		int left  = favoredScaleIndex - 1;
		int right = favoredScaleIndex + 1;
		peopleCounts.set(0, points[favoredScaleIndex]);

		for (int i = 1; i < scaleSize; ++i) {
			if (left >= 0 && left < scaleSize) {
				peopleCounts.set(left, points[i]);
			}
			if (right >= 0 && right < scaleSize) {
				peopleCounts.set(right, points[i]);
			}

			left--;
			right++;
		}



		// x = scale index
		// t = total scale index size
		// s = favoredScaleIndex
		// f = favorIntensity
		// Function goes from y = 1/t to y = (x - t)

		/*
		scaleSize
		favoredScaleIndex
		favorIntensity
		scaleFrontIndex

		arr (scaleSize)
		i = favoredScaleIndex - scaleFrontIndex

		maximum = 100
		minimum = maximum/scaleSize
		difference = maximum - minimum

		additional = (scaleSize * difference) / maximum
		arr[i] = minimum + additional


		*/


		return peopleCounts;
	}

	private Connection     simulationConnection;
	private Connection     generatorConnection;
	private SettingsReader settings;
}
