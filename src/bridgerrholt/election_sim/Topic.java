package bridgerrholt.election_sim;

import java.util.List;
import java.util.ArrayList;

class Topic {
	Topic(String name, PublicOpinion publicOpinion) {
		assert(name          != null);
		assert(publicOpinion != null);

		this.name          = name;
		this.publicOpinion = publicOpinion;
	}

	public String        getName()          { return name; }
	public PublicOpinion getPublicOpinion() { return publicOpinion; }


	private String        name;
	private PublicOpinion publicOpinion;
}
