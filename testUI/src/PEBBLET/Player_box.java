package PEBBLET;

import javax.swing.JComponent;
import javax.swing.JTextField;

public class Player_box extends JComponent {
	private JTextField Player_input;
	public Player_box(){
		Player_input = new JTextField();
	}
	
	public String get_Player_input(){
		return Player_input.getText();
	}
	
	public void set_Player_input(String _input){
		Player_input.setText(_input);
	}
	
	public void addtoPanel(JComponent comp, int x, int y){
		comp.setLayout(null);
		Player_input.setBounds(x,y,120,20);
		comp.add(Player_input);

	}
}
