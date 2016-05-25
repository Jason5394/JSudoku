import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

public final class BoardUtils {
	
	private BoardUtils(){
		
	}
	
	/**
	 * Returns a boolean checking if the given number is allowed to be placed on the given row
	 * of the sudoku board.
	 * @param board
	 * @param row
	 * @param num
	 * @param subrowsize
	 * @param subcolsize
	 * @return
	 */
	private static boolean checkRow(int[] board, int row, int num, int subrowsize, int subcolsize){
		int rowsize = subrowsize * subcolsize;
		int startpos = row*rowsize;
		int endpos = (row+1)*rowsize;
		
		for (int i = startpos; i < endpos; ++i){
			if (board[i] == num)
				return false;
		}
		return true;
	}
	
	
	/**
	 * Returns a boolean checking if the given number is allowed to be placed on the given column
	 * of the sudoku board.
	 * @param board
	 * @param col
	 * @param num
	 * @param subrowsize
	 * @param subcolsize
	 * @return
	 */
	private static boolean checkCol(int[] board, int col, int num, int subrowsize, int subcolsize){
		int colsize = subrowsize * subcolsize;
		int startpos = col;
		int endpos = colsize*colsize;
		
		for (int i = startpos; i < endpos; i += colsize){
			if (board[i] == num)
				return false;
		}
		return true;
	}
	
	
	/**
	 * Returns a boolean checking if the given number is allowed to be placed on the given subgrid
	 * of the sudoku board.
	 * @param board
	 * @param grid
	 * @param num
	 * @param subrowsize
	 * @param subcolsize
	 * @return
	 */
	private static boolean checkGrid(int[] board, int grid, int num, int subrowsize, int subcolsize){
		int gridsize = subrowsize * subcolsize;
		
		int index = (grid/subrowsize)*gridsize*subrowsize + (grid%subrowsize)*subcolsize;
		for (int i = 0; i < subrowsize; ++i){
			for (int j = 0; j < subcolsize; ++j){
				if (board[index] == num)
					return false;
				++index;
			}
			index += (gridsize-subcolsize);
		}
		return true;
	}
	
	
	/**
	 * Returns a boolean to determine if the indicated position in the sudoku grid with a given
	 * input number is valid
	 * @param board
	 * @param pos
	 * @param num
	 * @param subrowsize
	 * @param subcolsize
	 * @return
	 */
	private static boolean isValid(int[] board, int pos, int num, int subrowsize, int subcolsize){
		int gridsize = subrowsize*subcolsize;
		int row = pos/gridsize;
		
		int col = pos%gridsize;
		
		int gridx = col/subcolsize;
		int gridy = row/subrowsize;
		int grid = gridy*subrowsize + gridx;
		
		if (!checkRow(board, row, num, subrowsize, subcolsize)){
			//System.out.println("rowerror");
			return false;
		}
		if (!checkCol(board, col, num, subrowsize, subcolsize)){
			//System.out.println("colerror");
			return false;
		}
		if (!checkGrid(board, grid, num, subrowsize, subcolsize)){
			//System.out.println("griderror");
			return false;
		}
		return true;
	}
	
	
	/**
	 * Given an input reference sudoku puzzle, it will find a solution to the puzzle, if one exists.
	 * Also, by having refboard be an empty array, the method will generate a random puzzle every
	 * call.
	 * This is a recursive method.
	 * @param board Array that will be filled in with the solution
	 * @param exclude Array that contains elements that are excluded from calculation
	 * @param subrowsize Size of subrows (a normal sudoku grid has subrowsize = 3)
	 * @param subcolsize Size of subcolumns (a normal sudoku grid has subcolsize = 3)
	 * @return boolean value
	 */
	private static boolean createValidBoard(int[] board, int[] exclude, int subrowsize, int subcolsize){
		int gridsize = subrowsize * subcolsize;
	
		//base case (no more open tiles to search)
		int openpos;
		if ((openpos = findOpenTile(board)) == -1)
			return true;
		
		int validNum = randInteger(1, gridsize);
		
		for (int i = 0; i < gridsize; ++i){
			if (isValid(board, openpos, validNum, subrowsize, subcolsize) && exclude[openpos] != validNum){
				board[openpos] = validNum;
				if (createValidBoard(board, exclude, subrowsize, subcolsize))
					return true;
				else 
					board[openpos] = 0;	//backtrack
			}
			validNum = nextIntWrap(validNum, 1, gridsize);
		}
		
		return false;
	}
	
	
	/**
	 * Returns a stack that contains the numbers that are valid on a given position in the
	 * sudoku grid.
	 * @param board
	 * @param pos
	 * @param subrowsize
	 * @param subcolsize
	 * @return
	 */
	@SuppressWarnings("unused")
	private static Stack<Integer> findValidTiles(int[] board, int pos, int subrowsize, int subcolsize){
		Stack<Integer> validTiles = new Stack<>();
		int gridsize = subrowsize*subcolsize;
		for (int i = 1; i <= gridsize; ++i){
			if (isValid(board, pos, i, subrowsize, subcolsize))
				validTiles.push(i);
		}
		return validTiles;
	}
	
	
	/**
	 * Returns the closest array index to 0 that is unassigned (equals 0)
	 * @param board
	 * @return
	 */
	private static int findOpenTile(int[] board){
		for (int i = 0; i < board.length; ++i){
			if (board[i] == 0)
				return i;
		}
		return -1;
	}
	
	
	/**
	 * Given a valid and filled board, returns a sudoku board that contains only one 
	 * valid solution (the initial board)
	 * @param board
	 * @param totalTilesRemoved
	 * @param subrowsize
	 * @param subcolsize
	 * @return
	 */
	private static boolean createUniquePuzzle(int[] board, int[] exclude, int removedCount, int totalTilesRemoved, int subrowsize, int subcolsize){
		if (totalTilesRemoved > board.length){
			System.err.println("Error: tilesRemoved may not exceed total size of board.");
			return false;
		}
		
		//base case (we've removed the amount of tiles needed)
		if (removedCount >= totalTilesRemoved)
			return true;
		
		//finding a random valid tile to remove
		int removedpos = randInteger(0, board.length-1);
		for (int i = 0; i < board.length; ++i){
			if (board[removedpos] != 0)
				break;
			removedpos = nextIntWrap(removedpos , 0, board.length-1);
		}
		
		//remove tile from board, and add to exclude array
		int removedNum = board[removedpos];
		board[removedpos] = 0;
		exclude[removedpos] = removedNum;
		
		//if a solution can't be found without using the excluded number,
		//we can proceed to remove another one.  Else, backtrack and refill
		//with the removed number.
		if (!createValidBoard(board, exclude, subrowsize, subcolsize)){
			if (createUniquePuzzle(board, exclude, removedCount+1, totalTilesRemoved, subrowsize, subcolsize))
				return true;
		}
	
		//backtrack
		board[removedpos] = removedNum;
		exclude[removedpos] = 0;
		
		return false;
	}
	
	
	/**
	 * Wrapper function for createUniquePuzzle.  Returns a random sudoku puzzle,
	 * with a given number of tiles removed for the player to the solve.  
	 * @param tilesRemoved
	 * @param subrowsize
	 * @param subcolsize
	 * @return
	 */
	public static int[] generateSudokuPuzzle(int tilesRemoved, int subrowsize, int subcolsize){
		int gridsize = subrowsize * subcolsize;
		int totalTiles = gridsize * gridsize;
		int[] refboard = new int[totalTiles];
		int[] exclude = new int[totalTiles];
		int[] filledboard = sudokuSolver(refboard, subrowsize, subcolsize);
		
		System.out.println("Full valid board");
		displaySudoku(filledboard, subrowsize, subcolsize);
		
		if (!createUniquePuzzle(filledboard, exclude, 0, tilesRemoved, subrowsize, subcolsize))
			System.err.println("WARNING: puzzle generated was not valid.");
		
		System.out.println("Generated puzzle board");
		displaySudoku(filledboard, subrowsize, subcolsize);
		return filledboard;
	}
	
	
	/** Wrapper function for createUniquePuzzle, with an argument for a valid Sudoku grid.
	 * Returns a random sudoku puzzle, with a given number of tiles removed for the player to the solve.  
	 * @param tilesRemoved
	 * @param subrowsize
	 * @param subcolsize
	 * @return
	 */
	public static int[] generateSudokuPuzzle(int[] validboard, int tilesRemoved, int subrowsize, int subcolsize){
		int gridsize = subrowsize * subcolsize;
		int totalTiles = gridsize * gridsize;
		int[] exclude = new int[totalTiles];
		int[] puzzleboard = Arrays.copyOf(validboard, validboard.length);
		
		System.out.println("Full valid board");
		displaySudoku(validboard, subrowsize, subcolsize);
		
		if (!createUniquePuzzle(puzzleboard, exclude, 0, tilesRemoved, subrowsize, subcolsize)){
			System.err.println("WARNING: puzzle generated was not valid.");
			return null;
		}
		
		System.out.println("Generated puzzle board");
		displaySudoku(puzzleboard, subrowsize, subcolsize);
		return puzzleboard;
	}
	
	/**
	 * Wrapper function for createValidBoard(), using a reference board.  
	 * Essentially, this solves the reference board and returns it.
	 * The solved board is non-deterministic - solutions may differ 
	 * everytime the method is run.
	 */
	public static int[] sudokuSolver(int[] refboard, int subrowsize, int subcolsize){
		int gridsize = subrowsize * subcolsize;
		int totalTiles = gridsize * gridsize;	
		int[] board = Arrays.copyOf(refboard, refboard.length);
		int[] exclude = new int[totalTiles];
		
		if (!createValidBoard(board, exclude, subrowsize, subcolsize))
			System.err.println("WARNING: board created was not valid.");
		return board;
	}
	
	
	/**
	 * Wrapper function for createValidBoard() without reference board as an argument.
	 * This method generates a random filled sudoku grid.
	 */
	public static int[] sudokuSolver(int subrowsize, int subcolsize){
		int gridsize = subrowsize * subcolsize;
		int totalTiles = gridsize * gridsize;
		int[] board = new int[totalTiles];
		int[] exclude = new int[totalTiles];

		if (!createValidBoard(board, exclude, subrowsize, subcolsize))
			System.err.println("WARNING: board created was not valid.");
		return board;
	}
	
	
	/**
	 * Generates a random integer between the arguments
	 * min and max, both inclusive.
	 * @param max maximum 
	 * @param min minimum
	 * @return random integer between min and max inclusive
	 */
	public static int randInteger(int min, int max){
		Random random = new Random();
		return random.nextInt(max-min+1) + min;
	}
	
	/**
	 * Returns the current integer incremented, and wraps around
	 * to min if it exceeds maximum.
	 * @param current Current integer
	 * @param min 
	 * @param max 
	 * @return current == max ? min : current+1
	 */
	public static int nextIntWrap(int current, int min, int max){
		if (current > max || current < min){
			System.err.println("Current int must be between min and max inclusive.");
			return -1;
		}
		return current == max ? min : current+1;
	}
	
	/**
	 * Returns the current integer decremented, and wraps around
	 * to max if it goes below minimum value.
	 * @param current
	 * @param min
	 * @param max
	 * @return current == min ? max : current-1
	 */
	public static int prevIntWrap(int current, int min, int max){
		if (current > max || current < min){
			System.err.println("Current int must be between min and max inclusive.");
			return -1;
		}
		return current == min ? max : current-1;
	}
	
	/**
	 * Displays the sudoku board of a given subrow size X subcolumn size
	 * @param board
	 * @param subrowsize
	 * @param subcolsize
	 */
	public static void displaySudoku(int board[], int subrowsize, int subcolsize){
		int gridsize = subrowsize * subcolsize;
		for (int i = 0; i < gridsize; ++i){
			for (int j = 0; j < gridsize; ++j){
				System.out.printf("%3d", board[i*gridsize+j]);
				if (j%subcolsize == subcolsize-1)
					System.out.print("  |");
			}
			System.out.println();
			if (i%subrowsize == subrowsize-1){
				for (int k = 0; k < 3*gridsize + 3*subrowsize; ++k)
					System.out.print("-");
				System.out.println();
			}
		}
	}
	
	
	//Trying out test cases
	@SuppressWarnings("unused")
	public static void main(String args[]){
		long begin = System.currentTimeMillis();
		
		//sample sudoku grid
		int[] sol = new int[]
				{	1,3,9,8,4,6,2,5,7,
					8,4,5,2,7,9,6,1,3,
					6,2,7,5,1,3,8,4,9,
					3,5,6,1,2,4,7,9,8,
					7,1,2,9,8,5,3,6,4,
					9,8,4,6,3,7,5,2,1,
					2,6,1,3,9,8,4,7,5,
					4,9,3,7,5,2,1,8,6,
					5,7,8,4,6,1,9,3,2
				};

		int[] test2 = generateSudokuPuzzle(60, 3, 3);
		
		long end = System.currentTimeMillis();
		System.out.println("Time elapsed: " + Double.toString((end - begin)/1000.0));
	}
}
