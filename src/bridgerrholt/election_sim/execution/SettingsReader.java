package bridgerrholt.election_sim.execution;

import bridgerrholt.election_sim.Scale;
import bridgerrholt.sqlite_interface.Database;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.util.Map;

public class SettingsReader {
	public SettingsReader(String settingsPath) throws Exception {
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

	public Scale getScale() { return scale; }

	private Scale scale = new Scale(2);
}
