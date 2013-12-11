package PEBBLET;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sun.corba.se.spi.ior.MakeImmutable;

import manager.DefinitionManager;

public class CardPane extends JPanel{
	private JPanel card_pane;
	
	private int endof_card_pane;
	
	public CardPane(final DefinitionManager dm, final int i, final JButton add_card){
		
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
		
		endof_card_pane +=30;
		final JButton add_item = new JButton("add");
		add_item.setBounds(50, endof_card_pane+5, 100,20);
		endof_card_pane +=30;
		card_pane.add(add_item);
		
		final JLabel closeMark = new JLabel("}");
		closeMark.setBounds(0, endof_card_pane + 5, 20,20);
		card_pane.add(closeMark);
		endof_card_pane -=60;
		
		add_card_item(dm, i);
		
		endof_card_pane += 60;
		
		add_item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				card_pane.setLayout(null);
				endof_card_pane -=60;
				add_card_item(dm, i);
				add_item.setBounds(50, endof_card_pane+5, 100,20);
				endof_card_pane +=30;
				closeMark.setBounds(0, endof_card_pane +5, 20,20);
				endof_card_pane+=30;
				card_pane.setSize(new Dimension(800,endof_card_pane));
				card_pane.setPreferredSize(new Dimension(800,endof_card_pane));

				add_card.setBounds(add_card.getX(), add_card.getY()+30,add_card.getWidth(), add_card.getHeight());
				card_pane.repaint();
				card_pane.validate();
				repaint();
				validate();
				
				
			}
		});

		
	}
	
	
	public int get_endof(){
		return endof_card_pane;
	}
	
	public void add_card_item(DefinitionManager dm, int index){
		
		final JPanel card_item_pane = new JPanel();
		card_item_pane.setLayout(null);

		final JComboBox<String> box_type = new JComboBox<String>();
		int[] temp = {index};
		box_type.setBounds(0,0,120,20);
		int i = 1;
		box_type.addItem("Select type");
		Action_box select = new Action_box();
		select.set_parent(dm.search(temp));
		while(i <= dm.get_selection_card_del().length){
			box_type.addItem(dm.get_selection_card_del()[i-1]);
			i++;
		}
		card_item_pane.add(box_type);
		
		card_pane.setLayout(null);
		card_item_pane.setBounds(50, endof_card_pane + 5, 900, 30);
		endof_card_pane += 30;
		card_pane.add(card_item_pane);
		
	}
	
	public void addtoPanel(JComponent comp, int x, int y){
		comp.setLayout(null);
		card_pane.setBounds(x,y,800,endof_card_pane);
//		set_button.setBounds(x+125, y, 30, 20);
		comp.add(card_pane);
//		comp.add(set_button);
	}

}
