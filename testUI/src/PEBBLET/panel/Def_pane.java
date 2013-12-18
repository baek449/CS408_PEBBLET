package PEBBLET.panel;

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

import PEBBLET.tabbedpane;
import PEBBLET.boxs.Deck_box;
import PEBBLET.boxs.Number_box;
import PEBBLET.boxs.Player_box;
import PEBBLET.boxs.StringTextField;
import PEBBLET.boxs.String_box;

import com.sun.org.apache.xerces.internal.impl.dtd.models.DFAContentModel;

import manager.DefinitionManager;
import manager.Node;
import manager.NodeType;

public class Def_pane extends JComponent{
	
	private DefinitionManager dm;
	
	
	private JPanel def_pane;
	private JScrollPane def_sc;
	
	private JPanel global_pane;
//	private ArrayList<Integer> global_pane_index;
	private int[] global_pane_index = {1};
	private JPanel players_pane; 
//	private ArrayList<Integer> players_pane_index;
	private int[] players_pane_index = {2};
	private int[] cards_pane_index = {3};
	private JPanel cards_pane;
//	private ArrayList<E>
		
	private int players_num;
	
	private int preStatus = 0;
	private int endof_global_pane = 30;
	private int endof_player_pane = 30;
	private int endof_cards_pane = 0 ;

	private int total_endof = 0;

	private JLabel closeMark;
	private JLabel closeMarkP;


	private tabbedpane parent_;
	public void setparent_(tabbedpane parent__)
	{
		parent_=parent__;
	}
	

	public Def_pane(){
		
		dm = new DefinitionManager();
		closeMark = new JLabel("}");
		closeMarkP = new JLabel("}");

		def_pane = new JPanel(true);
//		def_pane.setSize(new Dimension(900, 600));
		def_pane.setPreferredSize(new Dimension(900, 500));
		
		def_sc = new JScrollPane(def_pane);
		def_sc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		def_sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		def_sc.setPreferredSize(new Dimension(900, 600));
		
		global_pane = new JPanel(true);
		players_pane = new JPanel(true);
		cards_pane = new JPanel(true);
		players_num = 0;
		make_num_players();
		make_global();	
		make_players();
		make_card();
		
	}
	public Def_pane(DefinitionManager dm_){
			
			dm = dm_;
			closeMark = new JLabel("}");
			closeMarkP = new JLabel("}");
	
			def_pane = new JPanel(true);
	//		def_pane.setSize(new Dimension(900, 600));
			def_pane.setPreferredSize(new Dimension(900, 500));
			
			def_sc = new JScrollPane(def_pane);
			def_sc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			def_sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			def_sc.setPreferredSize(new Dimension(900, 600));
			
			global_pane = new JPanel(true);
			players_pane = new JPanel(true);
			cards_pane = new JPanel(true);
			players_num = 0;
			make_num_players(dm);
			make_global(dm.search(global_pane_index));	
			make_players(dm.search(players_pane_index));
			make_card(dm.search(cards_pane_index), dm);
			dm.getDefinition().getRoot().printAll();
			
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
	public void make_num_players(DefinitionManager dm_){
		def_pane.setLayout(null);
		
		JLabel title = new JLabel("Number of players :");
		int[] location = {0,0};
		final JTextField input;
		if(!(dm_.search(location).getData()==null))
			input = new JTextField(Integer.toString((int)dm_.search(location).getData()));
		else
			input = new JTextField();
		JButton comfirm = new JButton("set");
		comfirm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int i = Integer.parseInt(input.getText());
				set_players_num(i);
				
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
		JLabel Global = new JLabel("Global {");
		Global.setBounds(5, endof_global_pane+5, 100, 20);
		endof_global_pane += 25;
		//add button 추후수정 combobox
		global_pane.add(Global);

		endof_global_pane +=30;

		final JButton add_global = new JButton("add"); //... 버튼 디폴
		add_global.setBounds(50,endof_global_pane+5,100,20);
		endof_global_pane += 30;
		global_pane.add(add_global);
		closeMark.setBounds(5, endof_global_pane+5, 20, 20);
		endof_global_pane += 30;
		global_pane.add(closeMark);
		endof_global_pane -=90;
		
		make_global_item();

		endof_global_pane +=60;
		
		global_pane.setSize(new Dimension(890, endof_global_pane));

		add_global.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				global_pane.setLayout(null);
				endof_global_pane -= 60;
				make_global_item();
				add_global.setBounds(50, endof_global_pane + 5, 100,20);
				endof_global_pane +=30;
				closeMark.setBounds(5, endof_global_pane + 5, 20, 20);
				endof_global_pane += 30;
				global_pane.setSize(new Dimension(890, endof_global_pane));
				
				down_players_pane();//추가로 cards
				down_card_pane();
				
				def_pane.setPreferredSize(new Dimension(900, get_total_end() + 20));

				def_sc.repaint();
				def_sc.validate();
				def_pane.repaint();
				def_pane.validate();
				dm.getDefinition().getRoot().printAll();//for debugging
				System.out.println(global_pane.getComponentCount());
				
			}
		});
		

		
		def_pane.setLayout(null);
		def_pane.add(global_pane);	

	}
	public void make_global(Node input){
//		global_pane 여기다가 add
		endof_global_pane = 30;
		global_pane.setLayout(null);
		JLabel Global = new JLabel("Global {");
		Global.setBounds(5, endof_global_pane+5, 100, 20);
		endof_global_pane += 25;
		//add button 추후수정 combobox
		global_pane.add(Global);

		endof_global_pane +=30;

		final JButton add_global = new JButton("add"); //... 버튼 디폴
		add_global.setBounds(50,endof_global_pane+5,100,20);
		endof_global_pane += 30;
		global_pane.add(add_global);
		closeMark.setBounds(5, endof_global_pane+5, 20, 20);
		endof_global_pane += 30;
		global_pane.add(closeMark);
		endof_global_pane -=90;

		endof_global_pane +=60;
		
		for(int j = 0; j<input.numChildren();j++){
			endof_global_pane -= 60;
			make_global_item(input.getChildNode(j));
			add_global.setBounds(50,endof_global_pane +5, 100, 20);
			endof_global_pane += 30;
			closeMarkP.setBounds(5, endof_global_pane + 5, 30, 20);
			endof_global_pane +=30;
			global_pane.setSize(new Dimension(890, endof_global_pane));
			def_pane.setPreferredSize(new Dimension(900, get_total_end() + 20));
			down_card_pane();
			down_players_pane();
			def_sc.repaint();
			def_sc.validate();
			def_pane.repaint();
			def_pane.validate();
		}
		
		global_pane.setSize(new Dimension(890, endof_global_pane));

		add_global.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				global_pane.setLayout(null);
				endof_global_pane -= 60;
				make_global_item();
				add_global.setBounds(50, endof_global_pane + 5, 100,20);
				endof_global_pane +=30;
				closeMark.setBounds(5, endof_global_pane + 5, 20, 20);
				endof_global_pane += 30;
				global_pane.setSize(new Dimension(890, endof_global_pane));
				
				down_players_pane();//추가로 cards
				down_card_pane();
				
				def_pane.setPreferredSize(new Dimension(900, get_total_end() + 20));

				def_sc.repaint();
				def_sc.validate();
				def_pane.repaint();
				def_pane.validate();
				dm.getDefinition().getRoot().printAll();//for debugging
				System.out.println(global_pane.getComponentCount());
				
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
		Deck_box select = new Deck_box();
		select.set_parent(dm.search(global_pane_index));
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
		final Deck_box deckbox = new Deck_box();
		final Number_box numbox = new Number_box();
		final String_box stringbox = new String_box();
		final Player_box playerbox = new Player_box();
		box_type.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				String item = (String)e.getItem();
				global_item_pane.setLayout(null);
				if(e.getStateChange() == ItemEvent.SELECTED){
					
					int del_box_index = getComponentIndex(global_item_pane)-3;
					switch (item) {
						case "(Cancel)":
							if(preStatus<5)
								box_type.setSelectedItem(box_type.getItemAt(preStatus));
							break;
						case "Deck []":
							preStatus = 1;
//							dm.search(global_pane_index).deleteChildNode(deckbox.get_node());
							dm.search(global_pane_index).deleteChildNode(del_box_index);
							global_item_pane.removeAll();
							global_item_pane.setLayout(null);
							global_item_pane.add(box_type);
							deckbox.addtoPanel(global_item_pane, 125, 0);
							def_pane.repaint();
							deckbox.set_parent(dm.search(global_pane_index));
							break;
						case "Number []":
							preStatus = 2;
//							dm.search(global_pane_index).deleteChildNode(numbox.get_node());
							dm.search(global_pane_index).deleteChildNode(del_box_index);
							global_item_pane.removeAll();
							global_item_pane.setLayout(null);
							global_item_pane.add(box_type);
							numbox.addtoPanel(global_item_pane, 125, 0);
							def_pane.repaint();
							numbox.set_parent(dm.search(global_pane_index));
							break;
						case "String []":
							preStatus = 3;
//							dm.search(global_pane_index).deleteChildNode(stringbox.get_node());
							dm.search(global_pane_index).deleteChildNode(del_box_index);
							global_item_pane.removeAll();
							global_item_pane.setLayout(null);
							global_item_pane.add(box_type);
							stringbox.addtoPanel(global_item_pane, 125, 0);
							def_pane.repaint();
							stringbox.set_parent(dm.search(global_pane_index));
							break;
						case "Player []":
							preStatus = 4;
//							dm.search(global_pane_index).deleteChildNode(playerbox.get_node());
							dm.search(global_pane_index).deleteChildNode(del_box_index);
							global_item_pane.removeAll();
							global_item_pane.setLayout(null);
							global_item_pane.add(box_type);
							playerbox.addtoPanel(global_item_pane, 125, 0);
							def_pane.repaint();
							playerbox.set_parent(dm.search(global_pane_index));
							break;
						case "(Delete)":
							int total = global_pane.getComponentCount();
							global_pane.remove(global_item_pane);
							switch(preStatus){
								case 1:
									dm.search(global_pane_index).deleteChildNode(deckbox.get_node());
									break;
								case 2:
									dm.search(global_pane_index).deleteChildNode(numbox.get_node());
									break;
								case 3:
									dm.search(global_pane_index).deleteChildNode(stringbox.get_node());
									break;
								case 4:
									dm.search(global_pane_index).deleteChildNode(playerbox.get_node());
									break;
								case 0:
									dm.search(global_pane_index).deleteChildNode(del_box_index);
									break;
							}

							global_pane.getComponent(2).setLocation(global_pane.getComponent(2).getX(), global_pane.getComponent(2).getY()-30);
							global_pane.getComponent(1).setLocation(global_pane.getComponent(1).getX(), global_pane.getComponent(1).getY()-30);

							
							for(int i = del_box_index+3; i < total-1; i++){
								global_pane.getComponent(i).setLocation(global_pane.getComponent(i).getX(), global_pane.getComponent(i).getY()-30);
								def_pane.repaint();
								def_pane.validate();
							}
							endof_global_pane -= 30;
							global_pane.setSize(new Dimension(890, endof_global_pane + 5));
							up_players_pane(); //cards 도 똑같
							up_card_pane();
							def_pane.setPreferredSize(new Dimension(900, get_total_end() + 20));

							def_sc.repaint();
							def_sc.validate();
							
							break;
						default:
							preStatus = 0;
							global_item_pane.removeAll();
							global_item_pane.setLayout(null);
							global_item_pane.add(box_type);
							def_pane.repaint();
							break;
					};
					preStatus = 0;
				}
			}
		});	
	}
	public void make_global_item(Node input){
		final JPanel global_item_pane = new JPanel();//item_pane {jcombobox, item_box}
		global_item_pane.setLayout(null);
		
		final JComboBox<String> box_type = new JComboBox<String>();
		
		box_type.setBounds(0,0,120,20);

		int i = 1;
		box_type.addItem("Select type");
		Deck_box select = new Deck_box();
//		select.set_parent(dm.search(global_pane_index));
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
		def_pane.validate();
		final Deck_box deckbox = new Deck_box();
		final Number_box numbox = new Number_box();
		final String_box stringbox = new String_box();
		final Player_box playerbox = new Player_box();
		box_type.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				String item = (String)e.getItem();
				global_item_pane.setLayout(null);
				if(e.getStateChange() == ItemEvent.SELECTED){
					
					int del_box_index = getComponentIndex(global_item_pane)-3;
					switch (item) {
						case "(Cancel)":
							if(preStatus<5)
								box_type.setSelectedItem(box_type.getItemAt(preStatus));
							break;
						case "Deck []":
							preStatus = 1;
//							dm.search(global_pane_index).deleteChildNode(deckbox.get_node());
							dm.search(global_pane_index).deleteChildNode(del_box_index);
							global_item_pane.removeAll();
							global_item_pane.setLayout(null);
							global_item_pane.add(box_type);
							deckbox.addtoPanel(global_item_pane, 125, 0);
							def_pane.repaint();
							deckbox.set_parent(dm.search(global_pane_index));
							break;
						case "Number []":
							preStatus = 2;
//							dm.search(global_pane_index).deleteChildNode(numbox.get_node());
							dm.search(global_pane_index).deleteChildNode(del_box_index);
							global_item_pane.removeAll();
							global_item_pane.setLayout(null);
							global_item_pane.add(box_type);
							numbox.addtoPanel(global_item_pane, 125, 0);
							def_pane.repaint();
							numbox.set_parent(dm.search(global_pane_index));
							break;
						case "String []":
							preStatus = 3;
//							dm.search(global_pane_index).deleteChildNode(stringbox.get_node());
							dm.search(global_pane_index).deleteChildNode(del_box_index);
							global_item_pane.removeAll();
							global_item_pane.setLayout(null);
							global_item_pane.add(box_type);
							stringbox.addtoPanel(global_item_pane, 125, 0);
							def_pane.repaint();
							stringbox.set_parent(dm.search(global_pane_index));
							break;
						case "Player []":
							preStatus = 4;
//							dm.search(global_pane_index).deleteChildNode(playerbox.get_node());
							dm.search(global_pane_index).deleteChildNode(del_box_index);
							global_item_pane.removeAll();
							global_item_pane.setLayout(null);
							global_item_pane.add(box_type);
							playerbox.addtoPanel(global_item_pane, 125, 0);
							def_pane.repaint();
							playerbox.set_parent(dm.search(global_pane_index));
							break;
						case "(Delete)":
							int total = global_pane.getComponentCount();
							global_pane.remove(global_item_pane);
							switch(preStatus){
								case 1:
									dm.search(global_pane_index).deleteChildNode(deckbox.get_node());
									break;
								case 2:
									dm.search(global_pane_index).deleteChildNode(numbox.get_node());
									break;
								case 3:
									dm.search(global_pane_index).deleteChildNode(stringbox.get_node());
									break;
								case 4:
									dm.search(global_pane_index).deleteChildNode(playerbox.get_node());
									break;
								case 0:
									dm.search(global_pane_index).deleteChildNode(del_box_index);
									break;
							}

							global_pane.getComponent(2).setLocation(global_pane.getComponent(2).getX(), global_pane.getComponent(2).getY()-30);
							global_pane.getComponent(1).setLocation(global_pane.getComponent(1).getX(), global_pane.getComponent(1).getY()-30);

							
							for(int i = del_box_index+3; i < total-1; i++){
								global_pane.getComponent(i).setLocation(global_pane.getComponent(i).getX(), global_pane.getComponent(i).getY()-30);
								def_pane.repaint();
								def_pane.validate();
							}
							endof_global_pane -= 30;
							global_pane.setSize(new Dimension(890, endof_global_pane + 5));
							up_players_pane(); //cards 도 똑같
							up_card_pane();
							def_pane.setPreferredSize(new Dimension(900, get_total_end() + 20));

							def_sc.repaint();
							def_sc.validate();
							
							break;
						default:
							preStatus = 0;
							global_item_pane.removeAll();
							global_item_pane.setLayout(null);
							global_item_pane.add(box_type);
							def_pane.repaint();
							break;
					};
					preStatus = 0;
				}
			}
		});	
		
		switch(input.get_node_type()){//set selected item menu from node
			case nd_deck:
	//			box_type.setSelectedIndex(1);
				//set data from node
				if(!(input.getData()==null))
					deckbox.set_Deck_input((String)input.getData());
				deckbox.set_node(input);
				deckbox.addtoPanel(global_item_pane, 125, 0);
				break;
			case nd_num:
	//			box_type.setSelectedIndex(2);
				//set data from node
				if(!(input.getData()==null))
					numbox.set_title((String)input.getData());
				for(int j = 0; j < input.numChildren(); j++){
					if(!(input.getChildNode(j).getData()==null))
						numbox.add_values_box(Integer.toString((Integer)input.getChildNode(j).getData()), input.getChildNode(j));
					else
						numbox.add_values_box("", input.getChildNode(j));
				}
				numbox.set_node(input);
				numbox.addtoPanel(global_item_pane, 125, 0);
				numbox.reset_pos();
				break;
			case nd_str:
	//			box_type.setSelectedIndex(3);
				//set data from node
				if(!(input.getData()==null))
					stringbox.set_title((String)input.getData());
				for(int j = 0; j < input.numChildren();j++){
					if(!(input.getChildNode(j).getData()==null))
						stringbox.add_values_box((String)input.getChildNode(j).getData(), input.getChildNode(j));
					else
						stringbox.add_values_box("", input.getChildNode(j));
				}
				stringbox.set_node(input);
				stringbox.addtoPanel(global_item_pane, 125, 0);
				stringbox.reset_pos();
				break;
			case nd_player:
	//			box_type.setSelectedIndex(4);
				if(!(input.getData()==null))
					playerbox.set_Player_input((String)input.getData());
				playerbox.set_node(input);
				playerbox.addtoPanel(global_item_pane, 125, 0);
				break;
			default:
				break;
	}
		
		
	}
	
	public void make_players(){
		players_pane.setLayout(null);
		JLabel Players = new JLabel("Players {");
		Players.setBounds(5, 5, 100, 20);
		players_pane.add(Players);
		endof_player_pane +=30;
		final JButton add_players = new JButton("add");
		add_players.setBounds(50, endof_player_pane + 5, 100, 20);
		endof_player_pane +=30;
		players_pane.add(add_players);
		
		closeMarkP.setBounds(5, endof_player_pane + 5 , 30, 20);
		endof_player_pane += 30;
		players_pane.add(closeMarkP);
		endof_player_pane -=90;
		make_players_item();
		endof_player_pane += 60;

		players_pane.setBounds(0,endof_global_pane,890, endof_player_pane);

		add_players.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				players_pane.setLayout(null);
				endof_player_pane -= 60;
				make_players_item();
				add_players.setBounds(50,endof_player_pane +5, 100, 20);
				endof_player_pane += 30;
				closeMarkP.setBounds(5, endof_player_pane + 5, 30, 20);
				endof_player_pane +=30;
				players_pane.setSize(new Dimension(890, endof_player_pane));
				def_pane.setPreferredSize(new Dimension(900, get_total_end() + 20));
				down_card_pane();
				def_sc.repaint();
				def_sc.validate();
//add???
				def_pane.repaint();
				def_pane.validate();
				dm.getDefinition().getRoot().printAll();//for debugging
				System.out.println(players_pane.getComponentCount());				
				
				
			}
		});
		
		def_pane.setLayout(null);
		def_pane.add(players_pane);
		
		
	}
	public void make_players(Node input){
		players_pane.setLayout(null);
		JLabel Players = new JLabel("Players {");
		Players.setBounds(5, 5, 100, 20);
		players_pane.add(Players);
		endof_player_pane +=30;
		final JButton add_players = new JButton("add");
		add_players.setBounds(50, endof_player_pane + 5, 100, 20);
		endof_player_pane +=30;
		players_pane.add(add_players);
		
		closeMarkP.setBounds(5, endof_player_pane + 5 , 30, 20);
		endof_player_pane += 30;
		players_pane.add(closeMarkP);
		endof_player_pane -=90;

		
		endof_player_pane += 60;
		for(int j = 0; j<input.numChildren();j++){
			endof_player_pane -= 60;
			make_players_item(j, input.getChildNode(j));
			add_players.setBounds(50,endof_player_pane +5, 100, 20);
			endof_player_pane += 30;
			closeMarkP.setBounds(5, endof_player_pane + 5, 30, 20);
			endof_player_pane +=30;
			players_pane.setSize(new Dimension(890, endof_player_pane));
			def_pane.setPreferredSize(new Dimension(900, get_total_end() + 20));
			down_card_pane();
			def_sc.repaint();
			def_sc.validate();
			def_pane.repaint();
			def_pane.validate();
		}
		

		players_pane.setBounds(0,endof_global_pane,890, endof_player_pane);

		add_players.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				players_pane.setLayout(null);
				endof_player_pane -= 60;
				make_players_item();
				add_players.setBounds(50,endof_player_pane +5, 100, 20);
				endof_player_pane += 30;
				closeMarkP.setBounds(5, endof_player_pane + 5, 30, 20);
				endof_player_pane +=30;
				players_pane.setSize(new Dimension(890, endof_player_pane));
				def_pane.setPreferredSize(new Dimension(900, get_total_end() + 20));
				down_card_pane();
				def_sc.repaint();
				def_sc.validate();
//add???
				def_pane.repaint();
				def_pane.validate();
				dm.getDefinition().getRoot().printAll();//for debugging
				System.out.println(players_pane.getComponentCount());				
				
				
			}
		});
		
		def_pane.setLayout(null);
		def_pane.add(players_pane);
		
		
	}
	
	public void make_players_item(){
		final JPanel players_item_pane = new JPanel();
		players_item_pane.setLayout(null);
		
		final JComboBox<String> box_type = new JComboBox<String>();
		
		box_type.setBounds(0, 0 , 120, 20);
		
		int i = 1;
		box_type.addItem("Select type");
		Deck_box select = new Deck_box();
		select.set_parent(dm.search(players_pane_index));
		while(i<= dm.get_selection_noncard_del().length){
			box_type.addItem(dm.get_selection_noncard_del()[i-1]);
			i++;
		}
		players_item_pane.add(box_type);
		
		players_pane.setLayout(null);
		players_item_pane.setBounds(50, endof_player_pane +5, 900, 30);
		endof_player_pane += 30;
		players_pane.add(players_item_pane);
		def_pane.repaint();
		def_pane.validate();
		final Deck_box deckbox = new Deck_box();
		final Number_box numbox = new Number_box();
		final String_box strbox = new String_box();
		final Player_box playerbox = new Player_box();		
		box_type.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				String item = (String)e.getItem();
				players_item_pane.setLayout(null);
				if(e.getStateChange()==ItemEvent.SELECTED){
					int del_box_index = getComponentIndex(players_item_pane)-3;
					switch(item){
						case "(Cancle)":
							if(preStatus > 4){
								box_type.setSelectedItem(box_type.getItemAt(preStatus-4));
							}
							break;
						case "Deck []":
							preStatus = 5;
							
							dm.search(players_pane_index).deleteChildNode(del_box_index);
							players_item_pane.removeAll();
							players_item_pane.setLayout(null);
							players_item_pane.add(box_type);
							deckbox.addtoPanel(players_item_pane, 125, 0);
							def_pane.repaint();
							def_pane.validate();
							deckbox.set_parent(dm.search(players_pane_index));
							break;

						case "Number []":
							preStatus = 6;
							dm.search(players_pane_index).deleteChildNode(del_box_index);
							players_item_pane.removeAll();
							players_item_pane.setLayout(null);
							players_item_pane.add(box_type);
							numbox.addtoPanel(players_item_pane, 125, 0);
							def_pane.repaint();
							def_pane.validate();
							numbox.set_parent(dm.search(players_pane_index));
							break;

						case "String []":
							preStatus = 7;
							dm.search(players_pane_index).deleteChildNode(del_box_index);
							players_item_pane.removeAll();
							players_item_pane.setLayout(null);
							players_item_pane.add(box_type);
							strbox.addtoPanel(players_item_pane, 125, 0);
							def_pane.repaint();
							def_pane.validate();
							strbox.set_parent(dm.search(players_pane_index));
							break;

						case "Player []":
							preStatus = 8;

							dm.search(players_pane_index).deleteChildNode(del_box_index);
							players_item_pane.removeAll();
							players_item_pane.setLayout(null);
							players_item_pane.add(box_type);
							playerbox.addtoPanel(players_item_pane, 125, 0);
							def_pane.repaint();
							def_pane.validate();
							playerbox.set_parent(dm.search(players_pane_index));
							break;

						case "(Delete)":
							int total = players_pane.getComponentCount();
							players_pane.remove(players_item_pane);
														
							switch(preStatus){
								case 5:
									dm.search(players_pane_index).deleteChildNode(deckbox.get_node());
									break;
								case 6:
									dm.search(players_pane_index).deleteChildNode(numbox.get_node());
									break;
								case 7:
									dm.search(players_pane_index).deleteChildNode(strbox.get_node());
									break;
								case 8:
									dm.search(players_pane_index).deleteChildNode(playerbox.get_node());
									break;
								case 0:
									dm.search(players_pane_index).deleteChildNode(del_box_index);
									break;
							}
							players_pane.getComponent(2).setLocation(players_pane.getComponent(2).getX(), players_pane.getComponent(2).getY()-30);
							players_pane.getComponent(1).setLocation(players_pane.getComponent(1).getX(), players_pane.getComponent(1).getY()-30);
							
							for(int i = del_box_index+3; i < total-1; i++){
								players_pane.getComponent(i).setLocation(players_pane.getComponent(i).getX(), players_pane.getComponent(i).getY()-30);
								def_pane.repaint();
								def_pane.validate();
							}
							endof_player_pane -= 30;
							players_pane.setSize(new Dimension(890, endof_player_pane + 5));
							def_pane.setPreferredSize(new Dimension(900, get_total_end() + 20));
							up_card_pane();
							def_sc.repaint();
							def_sc.validate();
							break;

						default:
							preStatus=0;
							players_item_pane.removeAll();
							players_item_pane.setLayout(null);
							players_item_pane.add(box_type);
							def_pane.repaint();
							def_pane.validate();
							break;

					}
					preStatus = 0;
				}
				
			}
		});
	}
	
	public void make_players_item(int intend, Node input){
		final JPanel players_item_pane = new JPanel();
		players_item_pane.setLayout(null);
		
		final JComboBox<String> box_type = new JComboBox<String>();
		
		box_type.setBounds(0, 0 , 120, 20);
		
		int i = 1;
		box_type.addItem("Select type");
		Deck_box select = new Deck_box();
//		select.set_parent(dm.search(players_pane_index));
		while(i<= dm.get_selection_noncard_del().length){
			box_type.addItem(dm.get_selection_noncard_del()[i-1]);
			i++;
		}
		
		players_item_pane.add(box_type);
		
		players_pane.setLayout(null);
		players_item_pane.setBounds(50, endof_player_pane +5, 900, 30);
		endof_player_pane += 30;
		players_pane.add(players_item_pane);
		def_pane.repaint();
		def_pane.validate();
		final Deck_box deckbox = new Deck_box();
		final Number_box numbox = new Number_box();
		final String_box strbox = new String_box();
		final Player_box playerbox = new Player_box();		
		box_type.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				String item = (String)e.getItem();
				players_item_pane.setLayout(null);
				if(e.getStateChange()==ItemEvent.SELECTED){
					int del_box_index = getComponentIndex(players_item_pane)-3;
					switch(item){
						case "(Cancle)":
							if(preStatus > 4){
								box_type.setSelectedItem(box_type.getItemAt(preStatus-4));
							}
							break;
						case "Deck []":
							preStatus = 5;
							
							dm.search(players_pane_index).deleteChildNode(del_box_index);
							players_item_pane.removeAll();
							players_item_pane.setLayout(null);
							players_item_pane.add(box_type);
							deckbox.addtoPanel(players_item_pane, 125, 0);
							def_pane.repaint();
							def_pane.validate();
							deckbox.set_parent(dm.search(players_pane_index));
							break;

						case "Number []":
							preStatus = 6;
							dm.search(players_pane_index).deleteChildNode(del_box_index);
							players_item_pane.removeAll();
							players_item_pane.setLayout(null);
							players_item_pane.add(box_type);
							numbox.addtoPanel(players_item_pane, 125, 0);
							def_pane.repaint();
							def_pane.validate();
							numbox.set_parent(dm.search(players_pane_index));
							break;

						case "String []":
							preStatus = 7;
							dm.search(players_pane_index).deleteChildNode(del_box_index);
							players_item_pane.removeAll();
							players_item_pane.setLayout(null);
							players_item_pane.add(box_type);
							strbox.addtoPanel(players_item_pane, 125, 0);
							def_pane.repaint();
							def_pane.validate();
							strbox.set_parent(dm.search(players_pane_index));
							break;

						case "Player []":
							preStatus = 8;

							dm.search(players_pane_index).deleteChildNode(del_box_index);
							players_item_pane.removeAll();
							players_item_pane.setLayout(null);
							players_item_pane.add(box_type);
							playerbox.addtoPanel(players_item_pane, 125, 0);
							def_pane.repaint();
							def_pane.validate();
							playerbox.set_parent(dm.search(players_pane_index));
							break;

						case "(Delete)":
							int total = players_pane.getComponentCount();
							players_pane.remove(players_item_pane);
														
							switch(preStatus){
								case 5:
									dm.search(players_pane_index).deleteChildNode(deckbox.get_node());
									break;
								case 6:
									dm.search(players_pane_index).deleteChildNode(numbox.get_node());
									break;
								case 7:
									dm.search(players_pane_index).deleteChildNode(strbox.get_node());
									break;
								case 8:
									dm.search(players_pane_index).deleteChildNode(playerbox.get_node());
									break;
								case 0:
									dm.search(players_pane_index).deleteChildNode(del_box_index);
									break;
							}
							players_pane.getComponent(2).setLocation(players_pane.getComponent(2).getX(), players_pane.getComponent(2).getY()-30);
							players_pane.getComponent(1).setLocation(players_pane.getComponent(1).getX(), players_pane.getComponent(1).getY()-30);
							
							for(int i = del_box_index+3; i < total-1; i++){
								players_pane.getComponent(i).setLocation(players_pane.getComponent(i).getX(), players_pane.getComponent(i).getY()-30);
								def_pane.repaint();
								def_pane.validate();
							}
							endof_player_pane -= 30;
							players_pane.setSize(new Dimension(890, endof_player_pane + 5));
							def_pane.setPreferredSize(new Dimension(900, get_total_end() + 20));
							up_card_pane();
							def_sc.repaint();
							def_sc.validate();
							break;

						default:
							preStatus=0;
							players_item_pane.removeAll();
							players_item_pane.setLayout(null);
							players_item_pane.add(box_type);
							def_pane.repaint();
							def_pane.validate();
							break;

					}
					preStatus = 0;
				}
				
			}
		});
		
		switch(input.get_node_type()){//set selected item menu from node
	
			case nd_deck:
				//			box_type.setSelectedIndex(1);
							//set data from node
				deckbox.set_Deck_input((String)input.getData());
				deckbox.set_node(input);
				deckbox.addtoPanel(players_item_pane, 125, 0);
				break;
			case nd_num:
	//			box_type.setSelectedIndex(2);
				//set data from node
				if(!(input.getData()==null))
					numbox.set_title((String)input.getData());
				for(int j = 0; j < input.numChildren(); j++){
					if(!(input.getChildNode(j).getData()==null))
						numbox.add_values_box(Integer.toString((Integer)input.getChildNode(j).getData()), input.getChildNode(j));
					else
						numbox.add_values_box("", input.getChildNode(j));
				}
				numbox.set_node(input);
				numbox.addtoPanel(players_item_pane, 125, 0);
				numbox.reset_pos();
				break;
			case nd_str:
	//			box_type.setSelectedIndex(3);
				//set data from node
				if(!(input.getData() ==null))
					strbox.set_title((String)input.getData());
				for(int j = 0; j < input.numChildren();j++){
					if(!(input.getChildNode(j).getData()==null))
						strbox.add_values_box((String)input.getChildNode(j).getData(), input.getChildNode(j));
					else
						strbox.add_values_box("", input.getChildNode(j));
				}
				strbox.set_node(input);
				strbox.addtoPanel(players_item_pane, 125, 0);
				strbox.reset_pos();
				break;
			case nd_player:
	//			box_type.setSelectedIndex(4);
				if(!(input.getData()==null))
					playerbox.set_Player_input((String)input.getData());
				playerbox.set_node(input);
				playerbox.addtoPanel(players_item_pane, 125, 0);
				break;
			default:
				break;
		}
	}
	
	public void up_players_pane(){
		
		players_pane.setBounds(players_pane.getX(), players_pane.getY()-30, players_pane.getWidth(),players_pane.getHeight());

	}
	public void down_players_pane(){
		players_pane.setBounds(players_pane.getX(), players_pane.getY()+30, players_pane.getWidth(),players_pane.getHeight());

	}
	
	public void make_card(){
		int endof = get_total_end();
		def_pane.setLayout(null);
		
		cards_pane.setLayout(null);
		cards_pane.setBounds(5, endof, 890, 400);
		
		JButton add_card = new JButton("add_card");
		add_card.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("Test" + parent_);
				parent_.update_manager_status();
			}});
		
		
		CardPane cardpane = new CardPane(dm, 3, add_card, this);
		endof_cards_pane = cardpane.get_endof();
//		cardpane.add_card_item(dm, 3);

		cardpane.addtoPanel(cards_pane, 0, 0);
		
		
		

		add_card.setBounds(20,cardpane.get_endof(),100,20);

		cards_pane.add(add_card);
		
		def_pane.add(cards_pane);
		
		def_pane.setPreferredSize(new Dimension(900, get_total_end() + 20));

		
		def_pane.repaint();
		def_pane.validate();
		
	}
	public void make_card(Node input, DefinitionManager dm_){
//		Node input = dm_.search(cards_pane_index);
		int endof = get_total_end();
		def_pane.setLayout(null);
		
		cards_pane.setLayout(null);
		cards_pane.setBounds(5, endof, 890, 400);
		
		JButton add_card = new JButton("add_card");
		add_card.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("Test" + parent_);
				parent_.update_manager_status();
			}});
		
		
		CardPane cardpane = new CardPane(dm_, add_card, this, input);
		endof_cards_pane = cardpane.get_endof();
//		cardpane.add_card_item(dm, 3);

		cardpane.addtoPanel(cards_pane, 0, 0);
		
		
		

		add_card.setBounds(20,cardpane.get_endof(),100,20);
		
//		cardpane.reset_pos(add_card, this);

		cards_pane.add(add_card);
		
		def_pane.add(cards_pane);
		
		def_pane.setPreferredSize(new Dimension(900, get_total_end() + 20));
		
		
		
		def_pane.repaint();
		def_pane.validate();
		
	}
	
	public void up_card_pane(){
		cards_pane.setLocation(cards_pane.getX(), cards_pane.getY()-30);
	}
	public void down_card_pane(){
		cards_pane.setLocation(cards_pane.getX(), cards_pane.getY()+30);
	}
	
	public JComponent get_card_pane(){
		return cards_pane;
	}
	
	public void set_card_pane_presize(int x, int y){
		cards_pane.setPreferredSize(new Dimension(x, y));
		cards_pane.setSize(new Dimension(x, y));
	}
	
	public void set_def_pane_size(int x, int y){
		def_pane.setPreferredSize(new Dimension(x, y));
	}
	
	public void make_card_item(){
		
	}
	
	public void remove_card_item(){
		
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
		int[] a={0,0};
		dm.search(a).setData(i);
	}
	
	public void addNodetoTarger(JComponent target){
		
	}
	
	public void getNodeFromTarget(JComponent target){
		
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
	
	public void set_cards_pane(int i){
		endof_cards_pane = i;
	}
	
	public int get_total_end(){
		total_endof = endof_global_pane + endof_player_pane + endof_cards_pane;
		return total_endof;
	}
	
	public void set_total_end(int i){
		total_endof = i ;
	}
	
	public void update_def_pane(){
		closeMark = new JLabel("}");
		closeMarkP = new JLabel("}");

		def_pane = new JPanel(true);
//		def_pane.setSize(new Dimension(900, 600));
		def_pane.setPreferredSize(new Dimension(900, 500));
		
		def_sc = new JScrollPane(def_pane);
		def_sc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		def_sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		def_sc.setPreferredSize(new Dimension(900, 600));
		
		global_pane = new JPanel(true);
		players_pane = new JPanel(true);
		cards_pane = new JPanel(true);
		players_num = 0;
		make_num_players(dm);
		make_players(dm.search(players_pane_index));
		
		
	}

}
