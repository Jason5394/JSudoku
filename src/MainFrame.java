import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 * The GUI class which also contains the main method for running 
 * the application.  This handles all of the user interface 
 * aspects of playing sudoku
 * @author jason
 *
 */
public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = -1616367561107980353L;
	private Board board;
	private int[] entries;
	private SudokuGrid grid;
	private Square[] squares;
	private int difficulty;
	
	//Menu bar objects
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem easyMenuItem;
	private JMenuItem mediumMenuItem;
	private JMenuItem hardMenuItem;
	private JMenuItem exitMenuItem;
	
	//Other Swing objects
	private JPanel panel;
	private GridBagConstraints gbc;
	private JButton submit;
	private JButton reset;
	private JLabel winloseMessage;
	
	public MainFrame(){
		
		makeMenuBar();
		
		panel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		this.getContentPane().add(panel);
		panel.setBackground(Color.WHITE);
		
		gbc.gridy = 0;
		gbc.weighty = 0.15;
		gbc.gridy = 1;
		gbc.weighty = 0.10;
		gbc.gridy = 2;
		gbc.weighty = 0.6;
		gbc.gridy = 3;
		gbc.weighty = 0.15;
		
		gbc.gridx = 0;
		gbc.weightx = 0.20;
		gbc.gridx = 1;
		gbc.weightx = 0.20;
		gbc.gridx = 2;
		gbc.weightx = 0.20;
		gbc.gridx = 3;
		gbc.weightx = 0.20;
		gbc.gridx = 4;
		gbc.weightx = 0.20;
		
		
		gbc.gridx = 1; gbc.gridy = 1;
		submit = new JButton("Enter");
		panel.add(submit, gbc);
		
		gbc.gridx = 2; gbc.gridy = 1;
		winloseMessage = new JLabel("");
		panel.add(winloseMessage, gbc);
		
		gbc.gridx = 3; gbc.gridy = 1;
		reset = new JButton("Reset");
		panel.add(reset, gbc);
	
		gbc.gridx = 1; gbc.gridy = 2;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		resetBoard(1);
		
		
		//Adding a listener to submit button (checks if game is won)
		submit.addActionListener(new ActionListener(){
			public void actionPerformed(final ActionEvent e){
				int[] userSolution = new int[81];
				for (int i = 0; i < entries.length; ++i){
					if (squares[i].getUsable())
						userSolution[i] = parseIntCustom(squares[i].getTextField().getText());
					else if (!squares[i].getUsable())
						userSolution[i] = squares[i].getDigit();
				}
				
				if (board.wonGame(userSolution)){
					System.out.println("Won game!");
					winloseMessage.setText("You won!");
				}
				else {
					System.out.println("Lost game.");
					winloseMessage.setText("You lost.");
				}
			}
		});
		
		//Adding a listener to reset button (clears all fields in grid)
		reset.addActionListener(new ActionListener(){
			public void actionPerformed(final ActionEvent e){
				for (int i = 0; i < entries.length; ++i){
					if (squares[i].getUsable()){
						squares[i].removeAll();
						squares[i].setTextField();
						squares[i].repaint();
						squares[i].revalidate();
					}
				}
			}
		});
		
		//Making the main frame
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dim = toolkit.getScreenSize();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800,800);
		this.setVisible(true);
		this.setTitle("JSudoku");
		int ypos = (dim.height/2) - (this.getHeight()/2); 
		int xpos = (dim.width/2) - (this.getWidth()/2); 
		this.setLocation(xpos, ypos);
		
	}
	
	
	/**
	 * Custom parse int method for this sudoku program.  It parses a string and returns
	 * the integer value, if applicable.  This is used in conjunction with the user
	 * entering a value in each tile of the sudoku puzzle.  
	 * 
	 * If the user does not enter anything, i.e. " ", the method returns 0 to indicate
	 * no entry in the tile.
	 */
	private int parseIntCustom(String s){
		int parsedInt = 0;
		try {
			parsedInt = Integer.parseInt(s);
		} catch (NumberFormatException e){
			if (s.equals(" ")){
				parsedInt = 0;
			}
			else {
				e.printStackTrace();
				System.exit(-1);
			}
		}
		return parsedInt;
	}
	
	
	/**
	 * Makes the menu bar of the application, including setting
	 * all of the action listeners
	 */
	private void makeMenuBar(){
		//Making the menu bar
		menuBar = new JMenuBar();
		menu = new JMenu("Menu");
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
		
		//adding menu items
		easyMenuItem = new JMenuItem("Easy");
		menu.add(easyMenuItem);
		mediumMenuItem = new JMenuItem("Medium");
		menu.add(mediumMenuItem);
		hardMenuItem = new JMenuItem("Hard");
		menu.add(hardMenuItem);
		menu.addSeparator();
		exitMenuItem = new JMenuItem("Exit");
		menu.add(exitMenuItem);
		
		//setting action listeners for each menu item
		easyMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				difficulty = 1;
				resetBoard(difficulty);
			}
		});
		
		mediumMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				difficulty = 2;
				resetBoard(difficulty);
			}
		});
		
		hardMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				difficulty = 3;
				resetBoard(difficulty);
			}
		});
		
		exitMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
	}
	
	
	/**
	 * Creates a random new board, with specified difficulty.
	 * Side effects will also clear labels and textfields from the 
	 * main panel, as well as resetting any other resources.
	 */
	private void resetBoard(int difficulty){	
		// clear all content (Labels and TextFields) from each tile
		if (squares != null) {
			for (int i = 0; i < squares.length; ++i){
				squares[i].removeAll();
				squares[i].repaint();
			}
		}	
		
		// clear sudoku grid from panel
		if (grid != null){
			panel.remove(grid);
			panel.repaint();
		}
			
		// clear win/lose message from panel
		if (winloseMessage != null) {
			winloseMessage.setText("");
			panel.repaint();
		}
		
		// creating new Board to hold entries
		board = new Board(difficulty);
		entries = board.getBoard();
		
		grid = new SudokuGrid();
		squares = grid.getSquares();
		panel.add(grid, gbc);
		
		// setting textfield for user interactive tiles, and labels otherwise for 
		// unclickable tiles
		for (int i = 0; i < entries.length; ++i){
			int entry = entries[i];
			if (entry == 0){
				squares[i].setTextField();
				squares[i].revalidate();
			} 
			else if (entry <= 9 && entry >= 1){
				squares[i].setLabel(entry);
				squares[i].revalidate();
			}
			else {
				System.out.println("Array can only contain values 1-9 and 0 for JTextField.");
				break;
			}
		}
		
		panel.revalidate();
	}
	
	
	public static void main(String args[]){
		new MainFrame();
	}
	
}
