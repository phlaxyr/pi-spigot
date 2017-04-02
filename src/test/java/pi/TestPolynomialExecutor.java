package pi;

import static org.junit.Assert.*;

import java.util.Random;

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
	
	
	public static void main(String[] args) {
		Random r = new Random();
		for(int i=0;i<10000000;i++) {
			int a,b,c,d,e,f,g,h;
			a=r.nextInt(1500)+1;
			b=r.nextInt(1500)+1;
			c=r.nextInt(1500)+1;
			d=r.nextInt(1500)+1;
			e=r.nextInt(1500)+1;
			f=r.nextInt(1500)+1;
			g=r.nextInt(1500)+1;
			h=r.nextInt(1500)+1;
			double dub = PolynomialExecutor.getValueForPoly(a,b,c,d,e,f,g,h,10);
			int smallest;
			int shifts=0;
			if((dub > Math.PI - 0.0001) && (dub < Math.PI + 0.0001)) {
				smallest = a<b?a:b;
				if(c<smallest) smallest=c;
				if(d<smallest) smallest=d;
				if(e<smallest) smallest=e;
				if(f<smallest) smallest=f;
				if(g<smallest) smallest=g;
				if(h<smallest) smallest=h;
				while(smallest>10) {
					shifts++;
					smallest>>=1;
				}
				System.out.println((a>>shifts)+","+(b>>shifts)+","+(c>>shifts)+","+(d>>shifts)+","+(e>>shifts)+","+(f>>shifts)+","+(g>>shifts)+","+(h>>shifts));
			
			}
		}
	}
}
