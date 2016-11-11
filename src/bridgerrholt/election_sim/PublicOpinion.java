package bridgerrholt.election_sim;

import java.util.ArrayList;
import java.util.List;

class PublicOpinion {
	/// How far the scale goes outwards on both sides.
	/// So, the scale is [0, 2*scaleDepth].
	public static final int scaleDepth  = 2;
	public static final int scaleMiddle = scaleDepth;
	public static final int scaleMax    = scaleMiddle + scaleDepth;
	public static final int scaleSize   = scaleMax + 1;


	PublicOpinion(ArrayList<Integer> counts) {
		assert(counts != null);

		assert(counts.size() == scaleSize);

		this.counts = counts;
	}

	List<Integer> getCounts() { return counts; }


	private List<Integer> counts;
}
