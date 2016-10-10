//Ryan Potter and Patrick Eddy
public class Main {

	/**
	 * Driver for maze class.
	 * @param args
	 */
	public static void main(String[] args) {
		Maze maze = new Maze(5, 5, true);
		maze.display();
		maze = new Maze(67, 15, false);
		maze.display();
//		maze = new Maze(32, 7, false);
		maze.display();
		maze.showGraphics(false);
		maze.showGraphics(true);

	}

}
