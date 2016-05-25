import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

/**
 * Square class which acts as either an interactive tile or a
 * static one.
 * @author jason
 *
 */
public class Square extends JPanel {
		private static final long serialVersionUID = 2190129928773946896L;
		private Integer displayed_digit;
		private JLabel label;
		private JFormattedTextField textfield;
		private boolean isUsable; 
		
		Square(){
			super();
			this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			this.setBackground(Color.WHITE);
			this.setPreferredSize(new Dimension(50,50));
		}
		
		public Integer getDigit(){
			return displayed_digit;
		}

		public JFormattedTextField getTextField(){
			return textfield;
		}
		
		public void setLabel(int digit){
			//Square can only have either label or textfield, not both
			if (textfield != null)
				return;
			isUsable = false;
			displayed_digit = digit;
			label = new JLabel();
			label.setPreferredSize(new Dimension(40,40));
			label.setFont(new Font("Serif", Font.BOLD, 22));
			label.setText(displayed_digit.toString());
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setVerticalAlignment(JLabel.CENTER);
			this.add(label);
		}
		
		public void setTextField(){
			//Square can only have either label or textfield, not both
			if (label != null) 
				return;
			isUsable = true;
			textfield = new JFormattedTextField(createFormatter("#"));
			textfield.setBorder(BorderFactory.createEmptyBorder());
			textfield.setPreferredSize(new Dimension(40,40));
			textfield.setFont(new Font("Serif", Font.BOLD, 22));
			textfield.setForeground(Color.BLUE);
			textfield.setHorizontalAlignment(JTextField.CENTER);
			this.add(textfield);
		}
		
		public void setText(String s){
			textfield.setText(s);
		}
		
		public JLabel getLabel(){
			return label;
		}
		
		public boolean getUsable(){
			return isUsable;
		}
		
		private MaskFormatter createFormatter(String s){
			MaskFormatter formatter = null;
			try{
				formatter = new MaskFormatter(s);
			} catch (java.text.ParseException e){
				e.printStackTrace();
				System.exit(-1);
			}
			return formatter;
		}
		
	}