
public class Percolation {	
	private final WeightedQuickUnionUF wuf;
	private final int top, bottom, seq;
	private final boolean[] states;
	
	public final int length;
	
	public Percolation(int len) {
		seq = len * len + 2; bottom = seq - 2; top = seq - 1; length = len;		
		wuf = new WeightedQuickUnionUF(seq);
		
		states = new boolean[len*len];
		for (int i = 0; i < len*len; i++) 
			states[i] = false;
	}
	
	public void open(int row, int col) {		
		verify(row, col);		
		if (isOpen(row, col)) return;
		
		int curr = locate(row, col);		
		states[curr] = true;
		
		if (row > 1 && isOpen(row-1, col)) { // up
			wuf.union(locate(row-1, col), curr);
		}
		
		if (col > 1 && isOpen(row, col-1)) { // left
			wuf.union(locate(row, col-1), curr);
		}
		
		if (col < length && isOpen(row, col+1)) { // right
			wuf.union(locate(row, col+1), curr);
		}
		
		if (row < length && isOpen(row+1, col)) { // down
			wuf.union(locate(row+1, col), curr);
		}
		
		if (row == 1) {
			wuf.union(curr, top);
		}
		
		if (row == length && wuf.connected(curr, top)) {
			wuf.union(curr, bottom);
		}
	}
	
	public boolean percolates() {
		return wuf.connected(top, bottom);
	}
	
	public boolean isOpen(int row, int col) {
		return states[locate(row, col)];
	}
	
	public boolean isFull(int row, int col) {
		return isOpen(row, col) && wuf.connected(locate(row, col), top);
	}
	
	private void verify(int row, int col) {
		if (row < 0 || row > length || col < 0 || col > length)
			throw new IndexOutOfBoundsException();
	}
		
	private int locate(int row, int col) {
		return (row - 1) * length + (col - 1);
	}
	
	public void draw() {
		for (int i = 1; i <= length; i++) {
			for (int j = 1; j <= length; j++) {				
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
