package pi;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestPolynomialExecutor {

	/**
	 * Make sure PolynomialExecutor works with BBP
	 */
	@Test
	public void testBBP() {
		double d = PolynomialExecutor.getValueForPoly(120, 151, 47, 512, 1024, 712, 194, 15, 16);
		
		assertEquals(Math.PI, d, 0.00001);
	}

}
