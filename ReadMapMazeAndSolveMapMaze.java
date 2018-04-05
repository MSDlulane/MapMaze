import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class ReadMapMazeAndSolveMapMaze {
	private static char[][] maze;
	private static int startrow, startcol, finishrow, finishcol;
	private static ArrayList<String> mazeBuffer;
	private static Scanner file;

	public static void initializeMaze(String fileName) {
		startrow = startcol = finishrow = finishcol = -1;

		mazeBuffer = new ArrayList<String>();
		int numcols = 0;
		try {
			file = new Scanner(new File(fileName));
			while (file.hasNext()) {
				String nextLine = file.nextLine();
				mazeBuffer.add(nextLine);
				if (nextLine.length() > numcols)
					numcols = nextLine.length();
			}
		} catch (Exception e) {
			System.out.println(fileName + " has an issue");
		}
		int numrows = mazeBuffer.size();
		maze = new char[numrows][numcols];
		for (int r = 0; r < numrows; r++) {
			String row = mazeBuffer.get(r);
			for (int c = 0; c < numcols; c++) {
				if (row.length() >= c)
					maze[r][c] = row.charAt(c);
				else
					maze[r][c] = '*';

				// getting the point where to begin
				if (maze[r][c] == 'S') {
					startrow = r;
					startcol = c;
				}

				// getting the finish line/goal
				if (maze[r][c] == 'E') {
					finishrow = r;
					finishcol = c;
				}
			}
		}
		System.out.println("---- LOADING MAP MAZE ----");
		System.out.println("");
	}

	public static void printMaze() {
		for (char[] row : maze) {
			for (char c : row)
				System.out.print(c);
			System.out.println();
		}
		System.out.println();

	}

	public static void main(String[] args) {
		initializeMaze("src/MazeMap.dat");
		printMaze();
		
		System.out.println("---- TRYING TO SOLVE MAP MAZE ----");

		if (solveMaze(startrow, startcol)) {
			printMaze();
		} else {
			System.out.println("Unsolvable.");
		}
	}

	private static boolean solveMaze(int row, int column) {
		int startRowX = row;
		int startColY = column;
		// checking if you are at the bonders or not
		if (row < 0 || column < 0 || row >= maze.length || column >= maze[0].length) {
			//System.out.println("---- FALSE 1 ----");
			return false;
		}

		// checking if i have already reached the goal or the end
		if (maze[row][column] == 'E') {
			return true;

		}

		// checking if it is still in the right path
		if (maze[row][column] != '.' && maze[row][column] != 'S') {
			//System.out.println("---- FALSE 2 ----");
			return false;
		}
		//marking the path as part of the solution
		maze[row][column] = '"';
		
		/*
		 * PATH(x=1 - 1, y=1) UP
    	 * PATH(x=1, y=1 - 1) LEFT
    	 * PATH(x=1 + 1, y=1) DOWN
    	 * PATH(x=1, y=1 + 1) West 
		 * 
		 * 
		 */
		
		// checking the row above
		if (solveMaze(row - 1, column)) {
			maze[row][column] = '*';
			return true;
		}

		// checking the row below
		if (solveMaze(row + 1, column)) {
			maze[row][column] = '*';
			return true;
		}

		// checking the column left
		if (solveMaze(row, column - 1)) {
			maze[row][column] = '*';
			return true;
		}

		// checking the column right
		if (solveMaze(row, column + 1)) {
			maze[row][column] = '*';
			return true;
		}
		//want to see visited paths
		maze[row][column] = '"';
		//maze[startRowX][startColY] = 'S'; //bring back the start point
		// either way not part of the solution
		//System.out.println("---- FALSE 3 at the end ----");
		//printMaze();
		return false;
	}

}
