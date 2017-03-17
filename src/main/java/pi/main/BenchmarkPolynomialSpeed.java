package pi.main;

import java.util.Random;

import pi.PolynomialExecutor;

public class BenchmarkPolynomialSpeed {
	public static void main(String[] args) {

		final int trials = 50_000_000;
		final int outertrials = 7;

		System.out.println("Benchmarking polynomial speed with " + trials + " trials for " + (outertrials - 2) + " iterations");

		int[] array = new int[trials * 8];

		double timesum = 0;

		System.out.println("Warming up...");
		for (int k = 0; k < outertrials; k++) {
			for (int i = 0; i < trials; i++) {
				for (int j = 0; j < 8; j++) {
					array[i * 8 + j] = (int) randomLong();
				}
			}

			if(k > 1)
				System.out.println("Running iteration #" + (k - 1));

			long start = System.nanoTime();

			for (int i = 0; i < trials; i++) {
				double d = PolynomialExecutor.getValueForPoly(array[i * 8], array[i * 8 + 1], array[i * 8 + 2], 
						array[i * 8 + 3], array[i * 8 + 4], array[i * 8 + 5], array[i * 8 + 6], array[i * 8 + 7], 10);
				
				// consume value
				if(d == Double.NaN)
					throw new AssertionError();
			}

			long elapsed = System.nanoTime() - start;
			// dont count warmup
			if(k > 1)
				timesum += (((double) elapsed) / trials);
		}
		
		// compute average
		double avgtime = timesum / (outertrials - 2);
		
		System.out.println("Duration of each polynomial test: " + avgtime + " ns");
	}

	private static transient int seed = new Random().nextInt();
	private static long randomLong() {
		seed ^= (seed << 21);
		seed ^= (seed >>> 35);
		seed ^= (seed << 4);
		return seed;
	}
}
