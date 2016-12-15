package bridgerrholt.election_sim;

public class Scale {
	public Scale(int depth) {
		this.depth = depth;
	}

	public int getDepth()       { return depth; }
	public int getMiddleIndex() { return getDepth(); }
	public int getMaxIndex()    { return getMiddleIndex() + getDepth(); }
	public int getSize()        { return getMaxIndex() + 1; }
	public int getFirstIndex()  { return 0; }

	private int depth;
}
