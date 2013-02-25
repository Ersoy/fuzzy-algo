import java.util.Random;

public class UsePercolation {
	
	/**
	 * Displays current state of the percolation grid by
	 * drawing each site with a specific character
	 * (e.g. open +, close -, full *). 	
	 */
	public static void draw(Percolation per) {
		for (int i = 1; i <= per.GRID_BORDER_LENGTH; i++) {
			for (int j = 1; j <= per.GRID_BORDER_LENGTH; j++) {				
				StdOut.printf("%2s", per.isFull(i, j) ? "*" : per.isOpen(i, j) ? "+" : "-");
			}
			StdOut.println();
		}
	}
	
	public static void main(String[] args) {
		int len = 20, i = 0;
		Random rand = new Random(System.currentTimeMillis());
		Percolation per = new Percolation(len);		
		
		while (!per.percolates()) {
			int row = rand.nextInt(len) + 1;
			int col = rand.nextInt(len) + 1;
			per.open(row, col);
			if (i++ % 50 == 0) {
				draw(per);
				StdOut.println();
			}
		}

		draw(per);
	}
}
