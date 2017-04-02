package pi.main;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentLinkedQueue;

import pi.PolynomialExecutor;

public class ClearBlocks {

	public static void main(String[] args) throws IOException, InterruptedException {
		final int ceiling = 11;

		SortedSet<int[]> queue = Collections.synchronizedSortedSet(new TreeSet<int[]>(new Comparator<int[]>() {

			@Override
			public int compare(int[] o1, int[] o2) {
				for(int i = 0; i < o1.length; i++) {
					int k = Integer.compare(o1[i], o2[i]);
					if(k != 0)
						return k;
				}
				
				return 0;
			}
			
		}));
		Thread[] threads = new Thread[ceiling];
		for(int i = 0; i < ceiling; i++) {
			final int p = i;
			
			Thread thread = new Thread() {
	
				@Override
				public void run() {
					System.out.println("Starting thread " + p);
					int[] arr = new int[7];
					int n = 0;
					
					boolean de = true;
					do {
						double d = PolynomialExecutor.getValueForPoly(
								p, arr[0], arr[1], 10 - arr[2], 10 - arr[3], 10 - arr[4], 10 - arr[5], 10 - arr[6], 10);
						
						if(d < Math.PI) {
							queue.add(new int[] {p, arr[0], arr[1], 10 - arr[2], 10 - arr[3], 10 - arr[4], 10 - arr[5], 10 - arr[6]});
							queue.remove(new int[] {p, arr[0], arr[1], 10 - arr[2], 10 - arr[3], 10 - arr[4], 10 - arr[5], 10 - arr[6] + 1});
							queue.remove(new int[] {p, arr[0], arr[1], 10 - arr[2], 10 - arr[3], 10 - arr[4], 10 - arr[5] + 1, 10 - arr[6]});
							queue.remove(new int[] {p, arr[0], arr[1], 10 - arr[2], 10 - arr[3], 10 - arr[4] + 1, 10 - arr[5], 10 - arr[6]});
							queue.remove(new int[] {p, arr[0], arr[1], 10 - arr[2], 10 - arr[3] + 1, 10 - arr[4], 10 - arr[5], 10 - arr[6]});
							queue.remove(new int[] {p, arr[0], arr[1] - 1, 10 - arr[2], 10 - arr[3], 10 - arr[4], 10 - arr[5], 10 - arr[6]});
							queue.remove(new int[] {p, arr[0] - 1, arr[1], 10 - arr[2], 10 - arr[3], 10 - arr[4], 10 - arr[5], 10 - arr[6]});
						} else {
							arr[arr.length - 1] = 0;
							getNext(arr, ceiling, 2);
						}
						if(arr[2] == ceiling - 1) {
							if(de) {
								System.out.println("Thread " + p + " : " + arr[0] + "" + arr[1]);
								de = false;
							}
						} else de = true;
						
					//	if(n >= 1000)
					//		break;
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
		
		Thread.sleep(1000);
		
		Set<int[]> s = new TreeSet<>(queue);
		
		int[] ne = new int[7];
		
		System.out.println("Dropping top digit");
		for(int[] qe : queue) {
			for(int j = 0; j < qe[0]; j++) {
				s.remove(new int[] {j, qe[1], qe[2], qe[3], qe[4], qe[5], qe[6], qe[7]});
			}
		}
		
		System.out.println("Saving state");
		BufferedWriter fw = new BufferedWriter(new FileWriter("result"));
		for(int[] q : s)
			fw.write(q[0] + "," + q[1] + "," + q[2] + "," + (10 - q[3]) + "," + (10 - q[4]) + "," + (10 - q[5]) + "," + (10 - q[6]) + "," + (10 - q[7]) + "\n");
		fw.flush();
		fw.close();
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
