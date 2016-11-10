package bridgerrholt.election_sim;

import java.util.List;

public class Engine
{

}


class Topic
{
	Topic(String name, PublicViews publicViews) {
		assert(name != null);
		assert(publicViews != null);

		this.name        = name;
		this.publicViews = publicViews;
	}






	private String      name;
	private PublicViews publicViews;
}


class PublicViews
{
	public static final int rankingCount = 2;

	PublicViews(List<Integer> positiveRankingCounts,
	            List<Integer> negativeRankingCounts,
	            int           neutralCounts) {
		assert(positiveRankingCounts != null);
		assert(negativeRankingCounts != null);

		assert(positiveRankingCounts.size() == rankingCount);
		assert(negativeRankingCounts.size() == rankingCount);

		this.positiveRankingCounts = positiveRankingCounts;
		this.negativeRankingCounts = negativeRankingCounts;
		this.neutralCounts         = neutralCounts;
	}

	List<Integer> getPositiveRankingCounts() { return positiveRankingCounts; }
	List<Integer> getNegativeRankingCounts() { return negativeRankingCounts; }
	int           getNeutralCounts()         { return neutralCounts; }

	private List<Integer> positiveRankingCounts;
	private List<Integer> negativeRankingCounts;
	private int           neutralCounts;
}