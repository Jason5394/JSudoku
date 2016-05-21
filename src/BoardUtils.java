
public class BoardUtils {
	
	public static boolean checkRow(int[] board, int row, int subrowsize, int subcolsize){
		boolean isValid = true;
		int rowsize = subrowsize * subcolsize;
		int[] rownums = new int[rowsize+1];
		
		for (int i = 0; i < subrowsize; ++i){
			int index = (row/subrowsize)*(rowsize*subrowsize)	//subgrid offset
					  	+ (row%subrowsize)*subcolsize			//subrow offset
						+ i*rowsize;							//index offset
			for (int j = 0; j < subcolsize; ++j){
				if (rownums[board[index]] == 1)
					return false;
				else 
					rownums[board[index]] = 1;
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
				if (colnums[board[index]] == 1)
					return false;
				else 
					colnums[board[index]] = 1;
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
			if (gridnums[board[index]] == 1)
				return false;
			else 
				gridnums[board[index]] = 1;
			++index;
		}
		return isValid;
	}
	
	public static void main(String args[]){
		/*int[] solution = new int[]{1, 2, 5, 3, 7, 8, 4, 9, 6,
					3, 7, 8, 9, 6, 4, 1, 2, 5,
					9, 4, 6, 2, 1, 5, 8, 3, 7,
					2, 6, 9, 8, 4, 1, 5, 3, 7,
					4, 5, 3, 7, 9, 2, 8, 1, 6,
					1, 7, 8, 6, 5, 3, 4, 9, 2,
					9, 1, 2, 6, 5, 3, 7, 8, 4,
					5, 8, 7, 2, 4, 9, 6, 3, 1,
					3, 6, 4, 7, 8, 1, 5, 2, 9};
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
		System.out.println(checkGrid(solution, 8, 3, 3));
	}
}
