//Ryan Potter and Patrick Eddy
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class DrawMaze extends JPanel{

	/**
	 * Randomly generated long for serialization purposes.
	 */
	private static final long serialVersionUID = -3187581231531291553L;
	
	private char[][] maze;
	boolean showPath;
	
	/**
	 * Contructs a drawing panel to draw a maze with swing.
	 * @param maze a 2d array representing a maze.
	 * @param width the width of the maze
	 * @param depth the peth of the maze
	 * @param showPath true if you want to solution shown false if not.
	 */
	public DrawMaze(char[][] maze, int width, int depth, boolean showPath) {
		this.maze = maze;
		this.showPath = showPath;
		this.setSize(new Dimension(1 * width, 1 * depth));
		this.setBackground(Color.WHITE);
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d= (Graphics2D) g;
		for(int i = 0; i < maze.length; i ++) {
			for(int j = 0; j < maze[0].length; j ++) {
				Shape curr = new Rectangle2D.Double(10 * j, 10 * i, 10, 10);
				if(maze[i][j] == 'X') { 
					g2d.setColor(Color.BLACK); 
					}
				else if(maze[i][j] == ' ') { 
					g2d.setColor(Color.WHITE); 
					}
				else if(maze[i][j] == '+' && showPath) { 
					g2d.setColor(Color.GREEN); 
					curr = new Ellipse2D.Double(10 * j, 10 * i, 10, 10);
					}
				else if(maze[i][j] == '+' && !showPath){ 
					g2d.setColor(Color.WHITE); 
					}
				g2d.fill(curr);
			}
		}
	}
}
