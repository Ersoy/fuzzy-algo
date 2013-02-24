import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class PercolationImageCreator {
	private final Percolation per;
	private final BufferedImage image;
	private final Graphics graph;
	
	private final int SITE_SIZE = 15;
	private final int GRID_SIZE;
	
	public PercolationImageCreator(Percolation per) {
		this.per = per;
		GRID_SIZE = per.GRID_BORDER_LENGTH * SITE_SIZE;
		
		image = new BufferedImage(GRID_SIZE, GRID_SIZE, BufferedImage.TYPE_INT_RGB);
		graph = image.createGraphics();				
		clean();
	}
	
	public void createImage(String filename) {
		File file = new File(filename);
		try {
			draw();
			ImageIO.write(image, "png", file);
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}
	
	private void draw() {
		for (int i = 1; i <= per.GRID_BORDER_LENGTH; i++)
			for (int j = 1; j <= per.GRID_BORDER_LENGTH; j++)
				drawSite(i, j);
	}
	
	private void drawSite(int row, int col) {
		if (per.isOpen(row, col)) {
			Color color = per.isFull(row, col) ? Color.magenta : Color.white;
			drawSite(row, col, color);
		}
	}
	
	private void drawSite(int row, int col, Color color) {
		Point locate = new Point((col-1) * SITE_SIZE, (row-1) * SITE_SIZE);
		graph.setColor(color);		
		graph.fillRect(locate.x, locate.y, SITE_SIZE, SITE_SIZE);
		graph.setColor(Color.black);
		graph.drawRect(locate.x, locate.y, SITE_SIZE, SITE_SIZE);
	}
	
	private void clean() {
		graph.setColor(Color.black);
		graph.fillRect(0, 0, GRID_SIZE, GRID_SIZE);
	}
	
	public static void main(String[] args) {
		int len = 15;
		Random rand = new Random(System.currentTimeMillis());
		Percolation per = new Percolation(len);
		PercolationImageCreator creator = new PercolationImageCreator(per);
		
		while (!per.percolates()) {
			int row = rand.nextInt(len) + 1;
			int col = rand.nextInt(len) + 1;
			per.open(row, col);			
		}
		
		creator.createImage("per.png");
	}
}
