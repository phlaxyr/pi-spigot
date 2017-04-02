package pi;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

// Shouldn't be instantiated. 
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MathUtil {
	
	/**
	 * Compute a power with 10 as the base.
	 */
	public static int powb10(int exp) {
		switch(exp) {
			case 0:
				return 1;
			case 1:
				return 10;
			case 2: 
				return 100;
			case 3: 
				return 1000;
			case 4: 
				return 10000;
			case 5: 
				return 100000;
			case 6:
				return 1000000;
			case 7:
				return 10000000;
			case 8:
				return 100000000;
			case 9:
				return 1000000000;
			default:
				throw new IllegalArgumentException();
		}
	}
	
}
