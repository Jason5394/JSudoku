import java.util.Random;

public final class BoardUtils {
	
	private BoardUtils(){
		
	}
	
	public static boolean checkRow(int[] board, int row, int subrowsize, int subcolsize){
		boolean isValid = true;
		int rowsize = subrowsize * subcolsize;
		int[] rownums = new int[rowsize+1];
		
		for (int i = 0; i < subrowsize; ++i){
			int index = (row/subrowsize)*(rowsize*subrowsize)	//subgrid offset
					  	+ (row%subrowsize)*subcolsize			//subrow offset
						+ i*rowsize;							//index offset
			for (int j = 0; j < subcolsize; ++j){
				if (board[index] != 0){
					if (rownums[board[index]] == 1)
						return false;
					else 
						rownums[board[index]] = 1;
				}
				index += 1;
			}
		}
		return isValid;
	}
	
	public static boolean checkCol(int[] board, int col, int subrowsize, int subcolsize){
		boolean isValid = true;
		int colsize = subrowsize * subcolsize;
		int[] colnums = new int[colsize+1];
		
		for (int j = 0; j < subcolsize; ++j){
			int index = (col/subcolsize)*(colsize)		//subgrid offset
						+ (col%subcolsize)				//index offset
						+ j*(colsize*subrowsize);		//subcol offset
			for (int i = 0; i < subrowsize; ++i){
				if (board[index] != 0){
					if (colnums[board[index]] == 1)
						return false;
					else 
						colnums[board[index]] = 1;
				}
				index += subcolsize;
			}
		}
		
		return isValid;
	}
	
	public static boolean checkGrid(int[] board, int grid, int subrowsize, int subcolsize){
		boolean isValid = true;
		int gridsize = subrowsize * subcolsize;
		int[] gridnums = new int[gridsize+1];
		int index = grid * gridsize;
		for (int i = 0; i < gridsize; ++i){
			if (board[index] != 0){		//excludes when board has "0" (or unmarked grid)
				if (gridnums[board[index]] == 1)
					return false;
				else 
					gridnums[board[index]] = 1;
			}
			++index;
		}
		return isValid;
	}
	
	/**
	 * Checks whether input board is correct given the rules of Sudoku.
	 * @param board Array containing board numbers
	 * @param subrowsize Size of subrows (e.g. a "normal" sudoku grid has subrowsize = 3)
	 * @param subcolsize Size of subcolumns
	 * @return True if board is completely valid, false otherwise
	 */
	public static boolean checkBoard(int[] board, int subrowsize, int subcolsize){
		int gridsize = subrowsize * subcolsize;
		for (int i = 0; i < gridsize; ++i){
			if (!checkRow(board, i, subrowsize, subcolsize))
				return false;
			if (!checkCol(board, i, subrowsize, subcolsize))
				return false;
			if (!checkGrid(board, i, subrowsize, subcolsize))
				return false;
		}
		return true;
	}
	
	private static int iter = 0;
	private static boolean createValidBoard(int[] board, int[] refboard, int startpos, int subrowsize, int subcolsize){
		++iter;
		int gridsize = subrowsize * subcolsize;
		int totalTiles = gridsize * gridsize;
		
		boolean isValid = checkBoard(board, subrowsize, subcolsize);
		if (isValid && startpos == totalTiles)
			return true;
		else if (!isValid && startpos == totalTiles)
			return false;

		int startNum = randInteger(1, gridsize);
		int endNum = prevIntWrap(startNum, 1, gridsize);
		int validNum = startNum;
		
		while (validNum != endNum){
			board[startpos] = validNum;
			if (checkBoard(board, subrowsize, subcolsize)){
				if (refboard[startpos] != 0)
					board[startpos] = refboard[startpos];
				if (createValidBoard(board, refboard, startpos+1, subrowsize, subcolsize))
					return true;
				board[startpos] = 0;	//backtrack
			}
			else {
				board[startpos] = 0;
			}
			validNum = nextIntWrap(validNum, 1, gridsize);
		}
		
		return false;
	}
	
	/**
	 * Wrapper function for createValidBoard(), which checks if the reference
	 * board is a valid sudoku board.
	 */
	public static boolean sudokuSolver(int[] board, int[] refboard, int subrowsize, int subcolsize){
		if (!checkBoard(refboard, subrowsize, subcolsize)){
			System.err.println("Error: the reference sudoku board has failed the validity check.");
			return false;
		}
		else {
			int startpos = 0;
			return (createValidBoard(board, refboard, startpos, subrowsize, subcolsize));
		}
		
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
	
	public static int prevIntWrap(int current, int min, int max){
		if (current > max || current < min){
			System.err.println("Current int must be between min and max inclusive.");
			return -1;
		}
		return current == min ? max : current-1;
	}
	
	public static void displaySudoku(int board[], int subrowsize, int subcolsize){
		int gridsize = subrowsize * subcolsize;
		for (int i = 0; i < gridsize; ++i){
			System.out.print("Grid " + i + ": ");
			for (int j = 0; j < gridsize; ++j){
				System.out.printf("%3d", board[i*gridsize+j]);
			}
			System.out.println();
		}
	}
	
	public static void main(String args[]){
		long begin = System.currentTimeMillis();
		int[] solution = new int[]{1, 2, 5, 3, 7, 8, 4, 9, 6,
					3, 0, 8, 9, 6, 0, 1, 2, 5,
					9, 4, 6, 2, 1, 5, 8, 3, 7,
					2, 6, 9, 0, 4, 1, 0, 3, 7,
					4, 5, 3, 0, 9, 2, 8, 1, 6,
					1, 7, 8, 0, 5, 3, 4, 9, 2,
					9, 1, 0, 0, 0, 3, 7, 8, 4,
					5, 0, 7, 2, 4, 9, 6, 3, 1,
					3, 6, 4, 7, 8, 1, 5, 2, 0};
					
		int[] testSudoku = new int[81];
		//int[] refBoard = new int[81];
		sudokuSolver(testSudoku, solution, 3, 3);
		System.out.println("Testing validity: " + checkBoard(testSudoku, 3, 3));
		System.out.println("Recursions: " + iter);
		displaySudoku(testSudoku, 3, 3);
		long end = System.currentTimeMillis();
		System.out.println("Time elapsed: " + Double.toString((end - begin)/1000.0));

	}
}
