package PEBBLET;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CardPane extends JPanel{
	private JPanel card_pane;
	
	private int endof_card_pane;
	
	public CardPane(){
		endof_card_pane = 30;
		card_pane = new JPanel();
		JPanel title_pane = new JPanel();
		title_pane.setBounds(0,0, 330,20);
		title_pane.setLayout(null);
		JLabel title = new JLabel("Card");
		title.setBounds(0,0,100,20);
		title_pane.add(title);
		
		JTextField title_text = new JTextField();
		title_text.setBounds(50,0,150,20);
		title_pane.add(title_text);
		
		JLabel open_mark = new JLabel("{");
		open_mark.setBounds(200,0,20,20);
		title_pane.add(open_mark);
		
		card_pane.setLayout(null);
		card_pane.add(title_pane);
		
		final JComboBox<String> box_type = new 
		
	}
	
	
	public int get_endof(){
		return endof_card_pane;
	}
	
	public void add_card_item(){
		
	}
	
	public void addtoPanel(JComponent comp, int x, int y){
		comp.setLayout(null);
		card_pane.setBounds(x,y,800,endof_card_pane);
//		set_button.setBounds(x+125, y, 30, 20);
		comp.add(card_pane);
//		comp.add(set_button);
	}

}
