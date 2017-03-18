package pi.main;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import pi.PolynomialExecutor;

public class ClearBlocks {

	public static void main(String[] args) throws IOException, InterruptedException {
		final int ceiling = 16;
		
		Queue<int[]> queue = new ConcurrentLinkedQueue<>();
		Thread[] threads = new Thread[ceiling];
		for(int i = 0; i < ceiling; i++) {
			final int p = i;
			
			Thread thread = new Thread() {
	
				@Override
				public void run() {
					System.out.println("Starting thread " + p);
					int[] arr = new int[7];
					int n = 0;
					do {
						double d = PolynomialExecutor.getValueForPoly(
								p, arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], 10);
						
						if(d < Math.PI + 0.001)
							queue.add(Arrays.copyOf(arr, arr.length));
						else {
							arr[arr.length - 1] = 0;
							getNext(arr, ceiling, 2);
						}
						
						if(arr[1] == ceiling - 1) {
							n += 100 / (ceiling);
							System.out.println("Thread " + p + " " + n + "% complete");
						}
						
						if(n >= 100)
							break;
					} while(getNext(arr, ceiling, 1));
					
					System.out.println("Thread " + p + " ending");
				}
			};
			
			thread.start();
			
			threads[i] = thread;
		}
		
		// wait for threads to finish
		for(Thread t : threads)
			t.join();
		
		BufferedWriter fw = new BufferedWriter(new FileWriter("result"));
		for(int[] q : queue)
			fw.write(Arrays.toString(q) + "\n");
	}

	
	public static boolean getNext(int[] n, int max, int addplace) {
		if(n[0] == max - 1)
			return false;
		for(int i = n.length - addplace; i >= 0; i--) {
			n[i]++;
			
			if(n[i] < max)
				return true;
			
			n[i] -= max;
		}
		
		return false;
	}
}
