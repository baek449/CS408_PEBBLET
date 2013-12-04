package PEBBLET;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Def_pane extends JComponent{
	
	private JPanel def_pane;
	private JScrollPane def_sc;
	
	private JPanel global_pane;
	private JPanel players_pane; 
	private ArrayList<JPanel> cards_pane;  
	
	public Def_pane(){
		def_pane = new JPanel(true);
		def_pane.setSize(new Dimension(0, 600));
		
		def_sc = new JScrollPane(def_pane);
		def_sc.setPreferredSize(new Dimension(0, 600));
		
		global_pane = new JPanel(true);
		players_pane = new JPanel(true);
		cards_pane = new ArrayList<JPanel>();
		
		make_num_players();
		
	}
	public void make_num_players(){
		JLabel title = new JLabel("Number of players      :");
		JTextField input = new JTextField();
		JButton comfirm = new JButton("set");
		
		//positioning
		title.setBounds(5, 5, 100, 20);
		input.setBounds(110, 5, 30, 20);
		comfirm.setBounds(145, 5, 30, 20);
		
		
		def_pane.add(input);
		def_pane.add(title);
		def_pane.add(comfirm);
	}
	
	public void make_global(){
		
	}
	
	public void make_players(){
		
	}
	
	public void make_card(){
		
	}
	
	public void remove_card(){
		
	}
	
	public void add_custominputbox(){
		
	}
	
	public void remove_custominputbox(){
		
	}
	
	public void get_lastchild(){
		
	}
	
	public JComponent get_pane(){
		return def_pane;
	}
	
	public JComponent get_scpane(){
		return def_sc;
	}
	
	

}
