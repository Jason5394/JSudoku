import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
		int[] sudokuBoard = Arrays.copyOf(solution, solution.length);
		List<Integer> indices;
		
		//setting the difficulty level
		if (difficulty == 1){
			tilesRemoved = 0;
			//tilesRemoved = solution.length/4;
		}
		else if (difficulty == 2){
			tilesRemoved = solution.length/2 + solution.length/5;
		} 
		else if (difficulty == 3){
			tilesRemoved = solution.length/2 + solution.length/4;
		}
		else
			return null;
		
		//make array of sudoku grid indices, shuffle, and pick a number of tiles to remove (set as 0)
		indices = new ArrayList<>();
		for (int i = 0; i < 81; ++i){
			indices.add(i);
		}
		Collections.shuffle(indices);
		for (int i = 0; i < tilesRemoved; ++i){
			sudokuBoard[indices.get(i)] = 0;
		}
		
		return sudokuBoard;
	}
	
	/** Generates a valid and filled sudoku board randomly and returns it. */
	private final int[] populateSolution(){
		/* TODO: Still need to create the algorithm to generate a 
		 * random puzzle 
		 */
		int[] solution = new int[]{1, 2, 5, 3, 7, 8, 4, 9, 6,
				 					3, 7, 8, 9, 6, 4, 1, 2, 5,
				 					9, 4, 6, 2, 1, 5, 8, 3, 7,
				 					2, 6, 9, 8, 4, 1, 5, 3, 7,
				 					4, 5, 3, 7, 9, 2, 8, 1, 6,
				 					1, 7, 8, 6, 5, 3, 4, 9, 2,
				 					9, 1, 2, 6, 5, 3, 7, 8, 4,
				 					5, 8, 7, 2, 4, 9, 6, 3, 1,
				 					3, 6, 4, 7, 8, 1, 5, 2, 9};
		
		/*int[] validSudoku;
		int[] refBoard = new int[81];
		validSudoku = BoardUtils.sudokuSolver(refBoard, 3, 3);
		return Arrays.copyOf(validSudoku, validSudoku.length);*/
		return Arrays.copyOf(solution, solution.length);
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
