import bridgerrholt.election_sim.Engine;

public class Main {
	public static void main(String[] args) throws Exception {
		try {
			Engine engine = new Engine();
			engine.setup("databases/test_0/database.db");
		}

		catch (Exception e) {
			System.out.println("Main caught: " + e.getMessage());
			throw e;
		}
	}
}
