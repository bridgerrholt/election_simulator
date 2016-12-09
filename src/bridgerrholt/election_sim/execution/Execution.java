package bridgerrholt.election_sim.execution;

public interface Execution {
	public void run() throws Exception;

	public void close() throws Exception;
}
