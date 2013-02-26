/*-----------------------------------------------------------------
 * Author:       Ersoy Hasanoglu
 * Written:      2/18/2013
 * Last updated: 2/24/2013
 *-----------------------------------------------------------------*/

public class PercolationStats {
	private final int N, T;
	private final double[] trasholds;
	
	public PercolationStats(int n, int t) {
		if (n <= 0 || t <= 0)
			throw new IllegalArgumentException();
		N = n; T = t;
		
        Examplify ex = new Examplify();        
		trasholds = ex.getTrasholds();
	}

	public double mean() {	
		double total = 0;
		for (int i = 0; i < T; i++) {
			total += trasholds[i]; 
		}
		return total / T;
	}

	public double stddev() {
		double count = 0;
		for (int i = 0; i < T; i++)
			count += Math.pow(trasholds[i] - mean(), 2);
		return Math.sqrt(count / (T - 1));
	}

	public double confidenceLo() {		
		return mean() - 1.96 * stddev() / Math.sqrt(T);
	}

	public double confidenceHi() {
		return mean() + 1.96 * stddev() / Math.sqrt(T);
	}

	class Examplify {
		private double[] trasholds;		

		public Examplify() {
			StdRandom.setSeed(System.currentTimeMillis());
			trasholds = new double[T];
			for (int i = 0; i < T; i++) 
				trasholds[i] = trashold();
		}

		public double[] getTrasholds() {
			return trasholds;
		}
		
		private double trashold() {
			Percolation per = new Percolation(N);					
			double count = 0;
			
			while (!per.percolates()) {
				int row = StdRandom.uniform(N) + 1;
				int col = StdRandom.uniform(N) + 1;
				per.open(row, col);
				count++;
			}
			
			return count / (N * N);
		}
	}

	public static void main(String[] args) {
		int n = StdIn.readInt();
		int t = StdIn.readInt();

		PercolationStats stats = new PercolationStats(n, t);
		StdOut.printf("mean                    = %f\n", stats.mean());
		StdOut.printf("stddev                  = %f\n", stats.stddev());
		StdOut.printf("95%% confidence interval = %f, %f\n", 
				stats.confidenceLo(), stats.confidenceHi());
	}
}
