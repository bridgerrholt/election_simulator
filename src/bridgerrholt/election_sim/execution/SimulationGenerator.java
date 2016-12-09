package bridgerrholt.election_sim.execution;

import bridgerrholt.sqlite_interface.Database;

import java.sql.Connection;
import java.sql.Statement;

public class SimulationGenerator implements Execution {
	public SimulationGenerator(String simulationFileName,
	                           String generatorFileName,
	                           SettingsReader settings) throws Exception {
		simulationConnection = Database.createConnection(simulationFileName);
		generatorConnection  = Database.createConnection(generatorFileName);
	}

	public void run() throws Exception {
		Statement statement = simulationConnection.createStatement();
		statement.execute("DELETE opinion_lists");

		System.out.println("Generated successfully.");
	}

	public void close() throws java.sql.SQLException {
		simulationConnection.close();
		generatorConnection.close();
	}

	private Connection simulationConnection;
	private Connection generatorConnection;
}
