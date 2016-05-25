import java.util.Arrays;


public final class Board {
	private final int[] board;
	private final int[] solution;
	
	public Board(int difficulty){
		if (difficulty > 3 || difficulty < 1){
			System.err.print("Difficulty can only be 1-3 in value.");
			System.exit(-1);
		}
		
		solution = populateSolution();
		System.out.println("Solution:");
		BoardUtils.displaySudoku(solution, 3, 3);
		System.out.printf("\n");
		
		
		board = populateBoard(difficulty, solution);
		System.out.println("Puzzle:");
		BoardUtils.displaySudoku(board, 3, 3);
		System.out.printf("\n");
	}
	
	/**
	 * Given a sudoku-sized array of filled numbers, returns a 
	 * game array that removes random tiles, setting each as -1.  
	 * The number of tiles removed depends on the difficulty parameter
	 * from 1-3, with 1 being the easiest and 3 the hardest.
	 */
	private final int[] populateBoard(int difficulty, int[] solution){
		int tilesRemoved = 0;
		int[] sudokuBoard;
		
		//setting the difficulty level
		if (difficulty == 1){
			//tilesRemoved = 0;
			tilesRemoved = solution.length/2;
		}
		else if (difficulty == 2){
			tilesRemoved = solution.length/2 + solution.length/8;
		} 
		else if (difficulty == 3){
			tilesRemoved = solution.length/2 + solution.length/6;
		}
		else
			return null;
		
		//make array of sudoku grid indices, shuffle, and pick a number of tiles to remove (set as 0)
		/*indices = new ArrayList<>();
		for (int i = 0; i < 81; ++i){
			indices.add(i);
		}
		Collections.shuffle(indices);
		for (int i = 0; i < tilesRemoved; ++i){
			sudokuBoard[indices.get(i)] = 0;
		}
		*/
		sudokuBoard = BoardUtils.generateSudokuPuzzle(solution, tilesRemoved, 3, 3);
		return sudokuBoard;
	}
	
	/** Generates a valid and filled sudoku board randomly and returns it. */
	private final int[] populateSolution(){
		int[] validSudoku;
		int[] refBoard = new int[81];
		validSudoku = BoardUtils.sudokuSolver(refBoard, 3, 3);
		return Arrays.copyOf(validSudoku, validSudoku.length);
	}
	
	public final int[] getSolution(){
		return Arrays.copyOf(solution, solution.length);
	}
	
	public final int[] getBoard(){
		return Arrays.copyOf(board, board.length);
	}
	
	/** Checks if the user submitted array is the same as the solution.
	 * Returns true if the two arrays match, and false if doesn't. 
	 */
	public final boolean wonGame(int[] submitted){
		if (submitted.length != solution.length)
			return false;
		for (int i = 0; i < solution.length; ++i){
			if (solution[i] != submitted[i])
				return false;
		}
		return true;
	}
	
}
