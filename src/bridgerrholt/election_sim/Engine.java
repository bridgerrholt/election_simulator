package bridgerrholt.election_sim;

import java.util.List;
import java.util.ArrayList;

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

	private List<Topic>     topics;
	private List<Candidate> candidates;
}


