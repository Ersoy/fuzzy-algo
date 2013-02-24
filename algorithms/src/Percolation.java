/*-----------------------------------------------------------------
 * Author:       Ersoy Hasanoglu
 * Written:      2/18/2013
 * Last updated: 2/24/2013
 *-----------------------------------------------------------------*/

public class Percolation {	
	private final WeightedQuickUnionUF wuf;
	private final int GRID_VIRTUAL_TOP_SITE, GRID_VIRTUAL_BOTTOM_SITE;
	private final boolean[] states;
	
	public final int GRID_BORDER_LENGTH;
	
	public Percolation(int len) {
		// length of the union find data structure
		// top and bottom sites are included
		int siteCount = len * len + 2;  
		
		GRID_VIRTUAL_TOP_SITE = siteCount - 1;
		GRID_VIRTUAL_BOTTOM_SITE = siteCount - 2; 		
		GRID_BORDER_LENGTH = len;
		wuf = new WeightedQuickUnionUF(siteCount);
		
		states = new boolean[len*len];
		for (int i = 0; i < len*len; i++) 
			states[i] = false;
	}
	
	public void open(int row, int col) {		
		verify(row, col);		
		if (isOpen(row, col)) return;
		
		int currentLocation = locate(row, col);		
		states[currentLocation] = true;
		
		if (row > 1 && isOpen(row-1, col)) // up
			wuf.union(locate(row-1, col), currentLocation);		
		
		if (col > 1 && isOpen(row, col-1)) // left
			wuf.union(locate(row, col-1), currentLocation);		
		
		if (col < GRID_BORDER_LENGTH && isOpen(row, col+1)) // right
			wuf.union(locate(row, col+1), currentLocation);		
		
		if (row < GRID_BORDER_LENGTH && isOpen(row+1, col)) // down
			wuf.union(locate(row+1, col), currentLocation);		
		
		if (row == 1)
			wuf.union(currentLocation, GRID_VIRTUAL_TOP_SITE);
		
		if (row == GRID_BORDER_LENGTH 
				&& wuf.connected(currentLocation, GRID_VIRTUAL_TOP_SITE))
			wuf.union(currentLocation, GRID_VIRTUAL_BOTTOM_SITE);		
	}
	
	public boolean percolates() {
		return wuf.connected(GRID_VIRTUAL_TOP_SITE, GRID_VIRTUAL_BOTTOM_SITE);
	}
	
	public boolean isOpen(int row, int col) {
		return states[locate(row, col)];
	}
	
	public boolean isFull(int row, int col) {
		int loc = locate(row, col);
		return isOpen(row, col) && wuf.connected(loc, GRID_VIRTUAL_TOP_SITE);
	}
	
	/**
	 * Checks bounds of a given site, throws exception if given row and column
	 * are outside the bounds of grid.
	 */
	private void verify(int row, int col) {
		if (row < 0 || row > GRID_BORDER_LENGTH)
			throw new IndexOutOfBoundsException("row index is out of bounds");
		
		if (col < 0 || col > GRID_BORDER_LENGTH)
			throw new IndexOutOfBoundsException("col index is out of bounds");
	}

	/**
	 * Retrieves the position of a given site.	
	 */
	private int locate(int row, int col) {
		return (row - 1) * GRID_BORDER_LENGTH + (col - 1);
	}
	
	/**
	 * Displays current state of the percolation grid by
	 * drawing each site with a specific character 
	 * (e.g. open +, close -, full *). 	
	 */
	public void draw() {
		for (int i = 1; i <= GRID_BORDER_LENGTH; i++) {
			for (int j = 1; j <= GRID_BORDER_LENGTH; j++) {				
				StdOut.printf("%2s", isFull(i, j) ? "*" : isOpen(i, j) ? "+" : "-");
			}
			StdOut.println();
		}
	}
	
	public static void main(String[] args) {
		Percolation per = new Percolation(6);
		per.open(1, 2);
		per.open(2, 2);
		per.open(3, 2);
		per.open(4, 2);
		per.open(5, 2);
		per.open(6, 3);
		per.open(2, 5);
		per.open(2, 6);
		per.open(3, 6);
		per.open(3, 3);
		per.open(2, 6);
		per.open(5, 1);
		per.open(6, 1);
		per.draw();
		
		StdOut.printf("Percolates %s\n", per.percolates());
	}
}
