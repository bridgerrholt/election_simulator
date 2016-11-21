import bridgerrholt.election_sim.Engine;

public class Main {
	public static void main(String[] args) throws Exception {
		try {
			Engine engine = new Engine();
			engine.run("databases/test_0");
		}

		catch (Exception e) {
			System.out.println("Main caught: " + e.getMessage());
			throw e;
		}
	}
}
