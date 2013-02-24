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
	
	private final int siteSize = 15;
	private final int size;
	
	public PercolationImageCreator(Percolation per) {
		this.per = per;
		size = per.length * siteSize;
		
		image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		graph = image.createGraphics();				
		clean();
	}
	
	public void createImage(String filename) {
		File file = new File(filename);
		try {
			draw();
			ImageIO.write(image, "png", file);
		} catch (IOException e) {			// 
			e.printStackTrace();
		}
	}
	
	private void draw() {
		for (int i = 1; i <= per.length; i++)
			for (int j = 1; j <= per.length; j++)
				drawSite(i, j);
	}
	
	private void drawSite(int row, int col) {	
		Point locate = new Point((col-1) * siteSize, (row-1) * siteSize); 
		if (per.isFull(row, col)) {
			graph.setColor(Color.magenta);		
			graph.fillRect(locate.x, locate.y, siteSize, siteSize);
			graph.setColor(Color.black);
			graph.drawRect(locate.x, locate.y, siteSize, siteSize);
		} else if (per.isOpen(row, col)) {
			graph.setColor(Color.white);
			graph.fillRect(locate.x, locate.y, siteSize, siteSize);
			graph.setColor(Color.black);			
			graph.drawRect(locate.x, locate.y, siteSize, siteSize);
		}
	}
	
	private void clean() {
		graph.setColor(Color.black);
		graph.fillRect(0, 0, size, size);
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
