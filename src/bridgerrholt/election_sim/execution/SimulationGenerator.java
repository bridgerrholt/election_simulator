package bridgerrholt.election_sim.execution;

import bridgerrholt.election_sim.SettingsReader;
import bridgerrholt.database_tools.DatabaseInterface;
import bridgerrholt.supports.Probability;

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
		this.settings = settings;
	}

	public void run() throws Exception {
		Statement statement = simulationConnection.createStatement();
		DatabaseInterface.clearTable(simulationConnection, "opinion_lists");
		DatabaseInterface.clearTable(simulationConnection, "regional_opinions");


		PreparedStatement overview = generatorConnection.prepareStatement(
			"SELECT * FROM regional_opinion_overview"
		);

		ResultSet resultSet = overview.executeQuery();


		while (resultSet.next()) {
			int topicId  = resultSet.getInt("topic_id");
			int regionId = resultSet.getInt("region_id");

			ArrayList<Integer> peopleCounts = calculateCounts(
				topicId, regionId,
				resultSet.getInt("favored_scale_index"),
				resultSet.getInt("favor_intensity")
			);

			for (Integer i : peopleCounts) {
				System.out.print(i + " ");
			}

			System.out.println();

			/*PreparedStatement regionalOpinionInsert = simulationConnection.prepareStatement(
				"INSERT INTO regional_opinions (list_id, scale_index, person_count) " +
				"VALUES (?, ?, ?)"
			);

			for (Integer i : peopleCounts) {

			}*/
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
			"SELECT (population) FROM regions WHERE rowid = ?"
		);

		populationStatement.setInt(1, regionId);
		ResultSet populationSet = populationStatement.executeQuery();
		boolean populationSuccess = populationSet.next();
		assert (populationSuccess);
		int population = populationSet.getInt(1);

		int scaleSize = settings.getScale().getSize();


		// Piece-wise figuring for the deviation based on favorIntensity.
		double deviation = 0;
		final double deviationMax = 1000;
		if (favorIntensity < 0 || favorIntensity > 100) {
			throw new Exception("favor_intensity invalid");
		}
		if (favorIntensity == 0) {
			deviation = deviationMax;
		}
		// Very undocumented and bad code.
		// Basically taking a value from a line (1, 3) to (50, 1).
		else if (favorIntensity <= 50) {
			deviation = 3 - ((2 * (favorIntensity - 1)) / 49);
		}
		// (50, 1) to (100, 0.1).
		else {
			deviation = 1 - ((0.9 * (favorIntensity - 50)) / 50);
		}


		ArrayList<Double> rawDistribution = new ArrayList<>(scaleSize);
		double rawTotal = 0;
		for (int i = 0; i < scaleSize; i++) {
			double exact = Probability.normalDistribution(i, favoredScaleIndex, deviation);
			rawDistribution.add(exact);
			rawTotal += exact;
		}

		ArrayList<Integer> peopleCounts = new ArrayList<>(scaleSize);
		int personTotal = 0;
		for (Double rawValue : rawDistribution) {
			double ratio = rawValue / rawTotal;
			int rounded = (int)Math.floor(population * ratio);
			peopleCounts.add(rounded);
			personTotal += rounded;
		}

		int personDifference = population - personTotal;
		if (personDifference > 0) {
			peopleCounts.set(favoredScaleIndex,
				peopleCounts.get(favoredScaleIndex) + personDifference
			);
		}



		return peopleCounts;
	}

	private Connection     simulationConnection;
	private Connection     generatorConnection;
	private SettingsReader settings;
}
