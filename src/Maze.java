//Ryan Potter and Patrick Eddy

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import javax.swing.JFrame;


public class Maze {

	final static char WALL = 'X';
	final static char PATH = '+';

	private Vertex[][] graph;
	private char[][] printer;

	private int width; /// Think of these like columns
	private int depth; // Think of these like rows.
	private boolean debug;

	/**
	 * Contructs and then builds a new randomly generated maze.
	 * @param width columns of the maze
	 * @param depth rows of the maze
	 * @param debug display debugging.
	 */
	public Maze(int width, int depth, boolean debug) {
		this.width = width;
		this.depth = depth;
		this.debug = debug;

		graph = new Vertex[depth][width];
		printer = new char[2 * depth + 1][2 * width + 1];
		for (int i = 0; i < depth; i++) {
			for (int j = 0; j < width; j++) {
				graph[i][j] = new Vertex(i, j);
			}
		}
		Vertex start = graph[0][0];
		Vertex end = graph[depth - 1][width - 1];
		start.above = false;
		end.below = false;

		buildMaze(start, end);
		
		

	}

	/**
	 * Show a Graphical representation of the maze along with the solution
	 */
	public void showGraphics(boolean showPath) {
		DrawMaze draw = new DrawMaze(printer, width, depth, showPath);
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(10 * printer[0].length, 10 * printer.length + 20));
		frame.add(draw);
		frame.setVisible(true);
		frame.setResizable(false);
		
	}

	/**
	 * Builds the maze
	 * @param start starting point of the maze.
	 * @param ending point of the maze.
	 */
	private void buildMaze(Vertex start, Vertex end) {
		// depth first search so we use a stack
		Stack<Vertex> st = new Stack<>();
		start.visited = true;
		st.push(start);
		Vertex curr = start;
		boolean foundEnd = false;
		while (!st.isEmpty()) {
			if(debug) {
				display();
			}
			List<Vertex> surrVert = getSurrounding(curr);
//			System.out.println(surrVert);
			curr.visited = true;

			if (!surrVert.isEmpty()) { //has valid neighbors
				st.push(curr);
				curr = surrVert.get(SingleRandom.nextInt(surrVert.size()));
				
				//Figure out which neighbor was actually chosen.
				if (curr.x == st.peek().x) {
					if (curr.y < st.peek().y) {
						curr.right = false;
						st.peek().left = false;	
					} else {
						curr.left = false;
						st.peek().right = false;
					}
				} else {
					if (curr.x < st.peek().x) {
						curr.below = false;
						st.peek().above = false;
					} else {
						curr.above = false;
						st.peek().below = false;
					}
				}
				//calculating the path through the maze.
				if(!foundEnd) {
					curr.parOfPath = true;
					st.peek().parOfPath = true;
					if(curr == end) {
						foundEnd = true;
					}
				}

			} else {
				//removes bad paths
				if(!foundEnd) {
					curr.parOfPath = false;
				}
				curr = st.pop();
			}

		}
//		display();
	}

	/**
	 * Displays the maze.
	 */
	public void display() {
//		printer = new char[depth * 2 + 1][width * 2 + 1];
		int height = printer.length;
		int width = printer[0].length;

		for (int i = 0; i < height; i++) {

			if (i == 0) {
				for (int j = 0; j < width; j++) {
					if (!(j % 2 == 0) && !graph[i][j / 2].above) {
						printer[i][j] = ' ';
					} else {
						printer[i][j] = WALL;
					}
				}
			} else {
				for (int j = 0; j < width; j++) {
					if (i % 2 == 0) {
						if (j % 2 == 0) {
							printer[i][j] = WALL;
						} else {
							if (graph[i / 2 - 1][j / 2].below) {
								printer[i][j] = WALL;
							} else {
								printer[i][j] = ' ';
							}
						}
					} else {
						if (j % 2 == 0) {
							if (j < width - 1) {
								if (graph[i / 2][j / 2].left) {
									printer[i][j] = WALL;
								} else {
									printer[i][j] = ' ';
								}
							} else {
								if (graph[i / 2][j / 2 - 1].right) {
									printer[i][j] = WALL;
								} else {
									printer[i][j] = ' ';
								}
							}
						} else {
							if (graph[i / 2][j / 2].parOfPath) {
								printer[i][j] = '+';
							} else {
								printer[i][j] = ' ';
							}		 
						}
					}
				}
			}
		}

		System.out.println("\nthe maze:");
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				System.out.print(printer[i][j] + " ");
			}
			System.out.println();
		}
		
		
		
	}

	/**
	 * Finds all the valid neighbors surrounding the passed vertex.
	 * @param curr the vertex you wan to find the neighbors of.
	 * @return A list of valid neighboring vertexes
	 */
	private List<Vertex> getSurrounding(Vertex curr) {
		List<Vertex> surr = new ArrayList<>();
		Vertex above = null;
		Vertex below = null;
		Vertex left = null;
		Vertex right = null;
		if (curr.x + 1 < depth) {
			
			above = graph[curr.x + 1][curr.y];
		}
		if (curr.x - 1 >= 0) {
			
			below = graph[curr.x - 1][curr.y];
		}
		if (curr.y - 1 >= 0) {
			
			left = graph[curr.x][curr.y - 1];
		}
		if (curr.y + 1 < width) {
			
			right = graph[curr.x][curr.y + 1];
		}

		if (above != null && !above.visited) {
			surr.add(above);
		}

		if (below != null && !below.visited) {
			surr.add(below);
		}

		if (left != null && !left.visited) {
			surr.add(left);
		}

		if (right != null && !right.visited) {
			surr.add(right);
		}

		return surr;
	}
	
	
	
	
	
	
/**
 * A vertex class storing information about the vertexes making up the maze graph.
 * @author Ryan Potter and Patrick Eddy
 * @version 1.0
 */
	private class Vertex {
		int x;
		int y;
		boolean above;
		boolean below;
		boolean left;
		boolean right;
		boolean visited;
		boolean parOfPath;

		/**
		 * Construct a Vertex object.
		 * @param x x location in the graph.
		 * @param y y location in the graph.
		 */
		public Vertex(int x, int y) {
			this.x = x;
			this.y = y;
			above = true;
			below = true;
			left = true;
			right = true;
			visited = false;
			parOfPath = false;
		}

		@Override
		public String toString() {
			return "Position: " + x + ", " + y + "\nWall above: " + above + "\nWall below: " + below + "Wall left: "
					+ left + "Wall right: " + right;

		}

	}
}
