import java.util.Random;

public class UsePercolation {

	public static void main(String[] args) {
		int len = 20, i = 0;
		Random rand = new Random(System.currentTimeMillis());
		Percolation per = new Percolation(len);		
		
		while (!per.percolates()) {
			int row = rand.nextInt(len) + 1;
			int col = rand.nextInt(len) + 1;
			per.open(row, col);
			if (i++ % 50 == 0) {
				per.draw();
				StdOut.println();
			}
		}
		
		per.draw();
	}
	
}
