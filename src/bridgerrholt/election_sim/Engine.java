package bridgerrholt.election_sim;

import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import java.sql.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.util.Map;

import bridgerrholt.election_sim.execution.Execution;
import bridgerrholt.election_sim.execution.SettingsReader;
import bridgerrholt.election_sim.execution.Simulation;
import bridgerrholt.election_sim.execution.SimulationGenerator;
import com.google.gson.Gson;

import bridgerrholt.sqlite_interface.Database;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;


public class Engine {
	private Scanner inputScanner;

	public Engine() {
		inputScanner = new Scanner(System.in);
	}

	public void run(String simulationPath) throws Exception {
		ArrayList<String> beginningOptions = new ArrayList<>();
		beginningOptions.add("QUIT");
		beginningOptions.add("Simulation");
		beginningOptions.add("Simulation Generator");

		if (!simulationPath.endsWith("/") && !simulationPath.endsWith("\\")) {
			simulationPath = simulationPath.concat("/");
		}

		Execution execution = null;

		SettingsReader settings = new SettingsReader(
			simulationPath.concat(settingsFileName)
		);

		while (true) {
			int answer = askQuestion("Run which one?", beginningOptions, 0);
			boolean toQuit = false;

			switch (answer) {
				case 0:
					toQuit = true;
					break;

				case 1:
					execution = new Simulation(
						simulationPath.concat(simulationFileName),
						settings
					);
					break;

				case 2:
					execution = new SimulationGenerator(
						simulationPath.concat(simulationFileName),
						simulationPath.concat(simulationGeneratorFileName),
						settings
					);
					break;

				default:
					toQuit = true;
					break;
			}

			if (toQuit) break;

			System.out.println();
			execution.run();
			execution.close();
			System.out.println();
		}
	}

	private int askQuestion(String question, ArrayList<String> options, int startingNumber) {
		assert (options.size() > 0);


		int currentNumber = startingNumber;
		for (String i : options) {
			System.out.println(currentNumber + ": " + i);
			++currentNumber;
		}

		System.out.println(question);

		int maximumNumber = startingNumber + options.size() - 1;
		int inputNumber;

		while (true) {
			System.out.print("> ");

			try {
				inputNumber = inputScanner.nextInt();
			}
			catch (Exception e) {
				// Failure.
				inputScanner.next();
				continue;
			}

			if (inputNumber >= startingNumber && inputNumber <= maximumNumber) {
				break;
			}
		}

		return (inputNumber - startingNumber);
	}

	private static String simulationFileName          = "simulation.db";
	private static String simulationGeneratorFileName = "simulation_generator.db";
	private static String settingsFileName            = "settings.json";
}





