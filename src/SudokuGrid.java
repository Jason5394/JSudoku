import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class SudokuGrid extends JPanel{
		private static final long serialVersionUID = 6905069188364693825L;
		private SubGrid subgrids[];
		private Square squares[];
		
		SudokuGrid(){
			super(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			
			subgrids = new SubGrid[9];
			squares = new Square[81];
			int index = 0;
			for (int i = 0; i < 3; ++i){
				for (int j = 0; j < 3; ++j){
					gbc.gridx = j;
					gbc.gridy = i;
					gbc.weightx = 1;
					gbc.weighty = 1;
					gbc.fill = GridBagConstraints.BOTH;
					subgrids[index] = new SubGrid(3,3);
					add(subgrids[index], gbc);
					Square subsquares[] = subgrids[index].getSquares();
					int subindex = 0;
					for (int k = index*9; k < (index+1)*9; ++k){
						squares[k] = subsquares[subindex];
						++subindex;
					}
					++index;
				}
			}
			this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		}
		
		public Square[] getSquares(){
			return squares;
		}
		
	}
	