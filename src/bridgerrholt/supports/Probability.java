package bridgerrholt.supports;

public class Probability {
	public static double normalDistribution(double x, double expectation, double deviation) {
		double variance = deviation * deviation;

		return (
			(1 / Math.sqrt(2*variance*Math.PI))
			* Math.pow(Math.E,
				-(Math.pow(x - expectation, 2)) / (2 * variance)
			)
		);
	}
}
