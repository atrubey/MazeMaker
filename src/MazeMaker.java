import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MazeMaker {

	private static int width;
	private static int height;

	private static Maze maze;

	private static Random randGen = new Random();
	private static Stack<Cell> uncheckedCells = new Stack<Cell>();

	public static Maze generateMaze(int w, int h) {
		width = w;
		height = h;
		maze = new Maze(width, height);

		// select a random cell to start
		int rnd = randGen.nextInt(maze.getWidth()-1);
		Cell randCell = maze.getCell(rnd, rnd);

		// call selectNextPath method with the randomly selected cell
		selectNextPath(randCell);

		return maze;
	}

	private static void selectNextPath(Cell currentCell) {
		// mark current cell as visited
		currentCell.setBeenVisited(true);

		// check for unvisited neighbors
		getUnvisitedNeighbors(currentCell);

		// if has unvisited neighbors,
		if (getUnvisitedNeighbors(currentCell).size() != 0) {
			// select one at random.
			int rndNeighbor = randGen.nextInt(getUnvisitedNeighbors(currentCell).size());
			Cell unvisitedNeighbor = getUnvisitedNeighbors(currentCell).get(rndNeighbor);

			// push it to the stack
			uncheckedCells.push(unvisitedNeighbor);

			// remove the wall between the two cells
			removeWalls(currentCell, unvisitedNeighbor); 
			
			// make the new cell the current cell and mark it as visited
			currentCell = unvisitedNeighbor; 
			currentCell.setBeenVisited(true);
			
			// call the selectNextPath method with the current cell
			selectNextPath(currentCell);
		} else {
		// if all neighbors are visited
			if (!uncheckedCells.empty()) {
				// if the stack is not empty
				// pop a cell from the stack
				// make that the current cell
				currentCell = uncheckedCells.pop();
				
				// call the selectNextPath method with the current cell
				selectNextPath(currentCell);
			}
	
		}
	}

	private static void removeWalls(Cell c1, Cell c2) {
		if (c1.getX() == c2.getX() && c1.getY() > c2.getY()) {
			c2.setSouthWall(false);
			c1.setNorthWall(false);
		} else if (c1.getX() == c2.getX() && c1.getY() < c2.getY()) {
			c2.setNorthWall(false);
			c1.setSouthWall(false);
		} else if (c1.getY() == c2.getY() && c1.getX() < c2.getX()) {
			c2.setWestWall(false);
			c1.setEastWall(false);
		} else if (c1.getY() == c2.getY() && c1.getX() > c2.getX()) {
			c2.setEastWall(false);
			c1.setWestWall(false);
		}

	}

	private static ArrayList<Cell> getUnvisitedNeighbors(Cell c) {

		ArrayList<Cell> unvisitedNeighbors = new ArrayList();
		
		if (c.getX() != 0 && !maze.getCell(c.getX() - 1, c.getY()).hasBeenVisited()) {
			unvisitedNeighbors.add(maze.getCell(c.getX()-1, c.getY()));
		} 
		if (c.getY() != 0 && !maze.getCell(c.getX(), c.getY()-1).hasBeenVisited()) {
			unvisitedNeighbors.add(maze.getCell(c.getX(), c.getY()-1));
		}
		if (c.getX() != maze.getWidth() - 1 && !maze.getCell(c.getX() + 1, c.getY()).hasBeenVisited()) {
			unvisitedNeighbors.add(maze.getCell(c.getX()+1, c.getY()));
		}
		if (c.getY() != maze.getHeight() - 1 && !maze.getCell(c.getX(), c.getY()+1).hasBeenVisited()) {
			unvisitedNeighbors.add(maze.getCell(c.getX(), c.getY()+1));
		}
		
		return unvisitedNeighbors;
	}
}