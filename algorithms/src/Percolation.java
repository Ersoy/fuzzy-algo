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
	
	/**
	 * Creates n-by-n grid, with all sites blocked. 
	 */
	public Percolation(int n) {
		// length of the id array
		// top and bottom sites included
		int siteCount = n * n + 2;  
		
		GRID_VIRTUAL_TOP_SITE = siteCount - 1;
		GRID_VIRTUAL_BOTTOM_SITE = siteCount - 2; 		
		GRID_BORDER_LENGTH = n;
		wuf = new WeightedQuickUnionUF(siteCount);
		
		states = new boolean[n*n];
		for (int i = 0; i < n*n; i++) 
			states[i] = false;
	}
	
	/**
	 * Opens site if it is not already
	 */
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
	
	/**
	 * Checks whether grid percolates
	 */
	public boolean percolates() {
		return wuf.connected(GRID_VIRTUAL_TOP_SITE, GRID_VIRTUAL_BOTTOM_SITE);
	}
	
	/**
	 * Checks whether site is open
	 */
	public boolean isOpen(int row, int col) {		
		return states[locate(row, col)];
	}
	
	/**
	 * Checks whether site is full
	 */
	public boolean isFull(int row, int col) {
		int loc = locate(row, col);
		return isOpen(row, col) && wuf.connected(loc, GRID_VIRTUAL_TOP_SITE);
	}
	
	/**
	 * Checks bounds of a given site, throws exception if 
	 * given row and column are outside the bounds of grid.
	 */
	private void verify(int row, int col) {
		if (row < 0 || row > GRID_BORDER_LENGTH)
			throw new IndexOutOfBoundsException("row index is out of bounds");
		
		if (col < 0 || col > GRID_BORDER_LENGTH)
			throw new IndexOutOfBoundsException("col index is out of bounds");
	}

	/**
	 * Retrieves the id array position of a given site.	
	 */
	private int locate(int row, int col) {
		return (row - 1) * GRID_BORDER_LENGTH + (col - 1);
	}
}
