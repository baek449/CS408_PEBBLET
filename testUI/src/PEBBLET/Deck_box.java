package PEBBLET;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JTextField;

public class Deck_box extends JComponent{
	private JTextField Deck_input;
	public Deck_box(){
		Deck_input = new JTextField();
	}
	
	public String get_Deck_input(){
		return Deck_input.getText();
	}
	public void set_Deck_input(String _input){
		Deck_input.setText(_input);
	}
	
	public void addtoPanel(JComponent comp, int x, int y){
		comp.setLayout(null);
		Deck_input.setBounds(x,y,120,20);
		comp.add(Deck_input);

	}

}
