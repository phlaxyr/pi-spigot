package pi;

import org.apache.commons.math3.util.ArithmeticUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;


public class PolynomialExecutor {
	public static double getValueForPoly(int a, int b, int c, int d, int e, int f, int g, int h, int base) {

		final int iters = 10;
		
		double total = 0;
		for (int k = 0; k < iters; k++) {
			
			// (ax^2 + bx + c) / (dx^4 + ex^3 + fx^2 + gx + h)
			total += (double) (a * k * k + b * k + c)
					/ (double) ((d * k * k * k * k + e * k * k * k + f * k * k + g * k + h)
							* ((base == 10) ? MathUtil.powb10(k) : ArithmeticUtils.pow(base, k)));	// op. case for b10

		}
		
		return total;

	}
	
	
}
