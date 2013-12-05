package PEBBLET;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import manager.DefinitionManager;

public class Def_pane extends JComponent{
	
	private DefinitionManager dm;
	
	
	private JPanel def_pane;
	private JScrollPane def_sc;
	
	private JPanel global_pane;
	private JPanel players_pane; 
	private ArrayList<JPanel> cards_pane;  
	
	private int players_num;
	
	private int preStatus = 0;
	private int endof_global_pane = 30;

	public Def_pane(){
		
		dm = new DefinitionManager();
		
		def_pane = new JPanel(true);
		def_pane.setSize(new Dimension(900, 600));
		
		def_sc = new JScrollPane(def_pane);
		def_sc.setPreferredSize(new Dimension(900, 600));
		
		global_pane = new JPanel(true);
		players_pane = new JPanel(true);
		cards_pane = new ArrayList<JPanel>();
		players_num = 0;
		make_num_players();
		make_global();		
		
	}
	public void make_num_players(){
		def_pane.setLayout(null);
		
		JLabel title = new JLabel("Number of players :");
		final JTextField input = new JTextField();
		JButton comfirm = new JButton("set");
		comfirm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int i = Integer.parseInt(input.getText());
				set_players_num(i);
				System.out.println(get_players_num());
				
			}
		});
		
		//positioning
		title.setBounds(5, 5, 130, 20);
		input.setBounds(140, 5, 30, 20);
		comfirm.setBounds(175, 5, 30, 20);
		
		
		def_pane.add(title);
		def_pane.add(input);
		def_pane.add(comfirm);
	}
	
	
	public void make_global(){
//		global_pane 여기다가 add
		global_pane.setLayout(null);
		
		make_global_item();
		//add button 추후수정 combobox
		final JButton add_global = new JButton("add"); //... 버튼 디폴
		add_global.setBounds(50,endof_global_pane+5,50,20);
		endof_global_pane += 30;
		global_pane.setSize(new Dimension(890, endof_global_pane));
		global_pane.add(add_global);
		
		add_global.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				global_pane.setLayout(null);
				endof_global_pane -= 30;
				make_global_item();
				add_global.setBounds(50, endof_global_pane + 5, 50,20);
				endof_global_pane += 30;
				global_pane.setSize(new Dimension(890, endof_global_pane));
				global_pane.add(add_global);
				def_pane.repaint();
				def_pane.validate();
			}
		});
		
		
		def_pane.setLayout(null);
		def_pane.add(global_pane);	

	}
	
	public void make_global_item(){
		
		final JPanel global_item_pane = new JPanel();//item_pane {jcombobox, item_box}
		global_item_pane.setLayout(null);
		
		final JComboBox<String> box_type = new JComboBox<String>();
		
		box_type.setBounds(0,0,120,20);

		int i = 1;
		box_type.addItem("Select type");
		while(i <= dm.get_selection_noncard_del().length){
			box_type.addItem(dm.get_selection_noncard_del()[i-1]);
			i++;
		}
		
		global_item_pane.add(box_type);
		
		global_pane.setLayout(null);
		global_item_pane.setBounds(50, endof_global_pane+5, 900, 30);
		endof_global_pane += 30;
		global_pane.add(global_item_pane);
		def_pane.repaint();

		
		box_type.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				String item = (String)e.getItem();
				global_item_pane.setLayout(null);
				if(e.getStateChange() == ItemEvent.SELECTED){
					switch (item) {
						case "(Cancel)":
							box_type.setSelectedItem(box_type.getItemAt(preStatus));
							break;
						case "Deck []":
							preStatus = 1;
							Deck_box deckbox = new Deck_box();
							global_item_pane.removeAll();
							global_item_pane.setLayout(null);
							global_item_pane.add(box_type);
							deckbox.addtoPanel(global_item_pane, 125, 0);
							def_pane.repaint();
							break;
						case "Number []":
							preStatus = 2;
							Number_box numbox = new Number_box();
							global_item_pane.removeAll();
							global_item_pane.setLayout(null);
							global_item_pane.add(box_type);
							numbox.addtoPanel(global_item_pane, 125, 0);
							def_pane.repaint();
							break;
						case "String []":
							preStatus = 3;
							String_box stringbox = new String_box();
							global_item_pane.removeAll();
							global_item_pane.setLayout(null);
							global_item_pane.add(box_type);
							stringbox.addtoPanel(global_item_pane, 125, 0);
							def_pane.repaint();
							break;
						case "Player []":
							preStatus = 4;
							Player_box playerbox = new Player_box();
							global_item_pane.removeAll();
							global_item_pane.setLayout(null);
							global_item_pane.add(box_type);
							playerbox.addtoPanel(global_item_pane, 125, 0);
							def_pane.repaint();
							break;
						case "(Delete)":
							int total = global_pane.getComponentCount();
							int del_box_index = getComponentIndex(global_item_pane);
							global_pane.remove(global_item_pane);
							
							for(int i = del_box_index; i < total-1; i++){
								global_pane.getComponent(i).setLocation(global_pane.getComponent(i).getX(), global_pane.getComponent(i).getY()-30);
								def_pane.repaint();
								def_pane.validate();
							}
							endof_global_pane -= 30;
							global_pane.setSize(new Dimension(890, endof_global_pane + 5));
							
							break;
						default:
							preStatus = 0;
							global_item_pane.removeAll();
							global_item_pane.setLayout(null);
							global_item_pane.add(box_type);
							def_pane.repaint();
							break;
					};
				}
			}
		});	
	}
	
	public void make_players(){
		
	}
	
	public void make_card(){
		
	}
	
	public void remove_card(){
		
	}
	
	public void get_lastchild(){
		
	}
	
	public JComponent get_pane(){
		return def_pane;
	}
	
	public JComponent get_scpane(){
		return def_sc;
	}
	public int get_players_num(){
		return players_num;
	}
	
	public void set_players_num(int i){
		players_num = i;
	}
	
	public static final int getComponentIndex(JComponent component) {
	    if (component != null && component.getParent() != null) {
	      Container c = component.getParent();
	      for (int i = 0; i < c.getComponentCount(); i++) {
	        if (c.getComponent(i) == component)
	          return i;
	      }
	    }

	    return -1;
	  }
	

}
