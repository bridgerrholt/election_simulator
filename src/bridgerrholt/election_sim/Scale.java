package bridgerrholt.election_sim;

public class Scale {
	Scale(int depth) {
		this.depth = depth;
	}

	public int getDepth() { return depth; }
	public int getMiddleIndex() { return getDepth(); }
	public int getMaxIndex() { return getMiddleIndex() + getDepth(); }
	public int getSize() { return getMaxIndex() + 1; }

	private int depth;
}
