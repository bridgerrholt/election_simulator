package bridgerrholt.election_sim;

import java.util.List;

class Candidate {
	public Candidate(List<List<Integer> > topicOpinions) {
		assert(topicOpinions != null);

		this.topicOpinions = topicOpinions;
	}


	private List<List<Integer> > topicOpinions;
}
