import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class SubGrid extends JPanel {
		private static final long serialVersionUID = 5950070388609031043L;
		private Square squares[];
		
		SubGrid(int height, int width){
			super(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			
			squares = new Square[height*width];
			
			int index = 0;
			for (int i = 0; i < height; ++i){
				for (int j = 0; j < width; ++j){
					gbc.gridx = j;
					gbc.gridy = i;
					gbc.weightx = 1;
					gbc.weighty = 1;
					gbc.fill = GridBagConstraints.BOTH;
					squares[index] = new Square();
					add(squares[index], gbc);
					++index;
				}
			}
			this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		}
		
		public Square[] getSquares(){
			return squares;
		}
		
	}
	