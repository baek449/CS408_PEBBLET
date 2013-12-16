package PEBBLET.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import PEBBLET.panel_inside;
import manager.ComponentManager;
import manager.Definition;
import manager.DefinitionManager;
import manager.Node;
import manager.NodeType;
import manager.RuleManager;

public class Component_pane extends JComponent {

	private DefinitionManager dm;
	private ComponentManager cm;
	
	private JPanel total, cardview,valueview,typebox,cardlist,buttonbox;
	private JButton b_card_add, b_card_delete;
	//private NewTable comp_table;
	private JScrollPane comp_sc;
	private JComboBox<String> type_select;
	private JList<Node> card_list;
	private DefaultListModel<Node> card_list_model;
	private JScrollPane card_list_scroller;
	//private panel_inside p;
	
	public Component_pane(){
		//// Á¤ÀÇ
		Node def_root = new Node(null, null);
		def_root.setData("Root");
		
		Node player_number = new Node(NodeType.nd_num,def_root);
		player_number.setData("N_player");
		Node player_number_value = new Node(NodeType.nd_raw,player_number);
		player_number_value.setData(3);
		
		Node global_variables = new Node(NodeType.nd_def_global, def_root);
		global_variables.setData("Global");
		Node global_center=new Node(NodeType.nd_deck, global_variables);
		global_center.setData("center");
		Node global_discard=new Node(NodeType.nd_deck, global_variables);
		global_discard.setData("discard");
		Node global_num=new Node(NodeType.nd_num, global_variables);
		global_num.setData("test_num");
		
		
		Node player_variables = new Node(NodeType.nd_def_player, def_root);
		player_variables.setData("Player");
		Node player_hand=new Node(NodeType.nd_deck, player_variables);
		player_hand.setData("hand#");
		Node player_str=new Node(NodeType.nd_str, player_variables);
		player_str.setData("test_str");
		Node player_num=new Node(NodeType.nd_num, player_variables);
		player_num.setData("test_num2");
		
		//Node card_variables = new Node(NodeType.nd_def_card, def_root);
		//card_variables.setData("Card");
		Node card_trump = new Node(NodeType.nd_card,def_root);
		card_trump.setData("Trump");
		Node card_trump_shape = new Node(NodeType.nd_str,card_trump);
		card_trump_shape.setData("shape");
		Node card_trump_shape_spade = new Node(NodeType.nd_raw,card_trump_shape);
		card_trump_shape_spade.setData("spade");
		Node card_trump_shape_diamond = new Node(NodeType.nd_raw,card_trump_shape);
		card_trump_shape_diamond.setData("diamond");
		Node card_trump_shape_heart = new Node(NodeType.nd_raw,card_trump_shape);
		card_trump_shape_heart.setData("heart");
		Node card_trump_shape_clover = new Node(NodeType.nd_raw,card_trump_shape);
		card_trump_shape_clover.setData("clover");
		Node card_trump_num=new Node(NodeType.nd_num,card_trump);
		card_trump_num.setData("num");
		
		Definition sample_def = new Definition();
		sample_def.setRoot(def_root);
		dm = new DefinitionManager();
		dm.setDefinition(sample_def);
		//dm.updateVariableList(new DefinitionManager());
		
		cm = new ComponentManager(dm);
		
		total=new JPanel();
		total.setPreferredSize(new Dimension(1000,600));
		total.setLayout(new BorderLayout(7,7));
		
		cardview=new JPanel();
		cardview.setLayout(new BorderLayout(7,7));
		cardview.setPreferredSize(new Dimension(210,600));
		total.add(cardview,BorderLayout.WEST);

		valueview=new JPanel();
		//valueview.setLayout(new BorderLayout());
		valueview.setBorder(BorderFactory.createLoweredBevelBorder());
		valueview.setPreferredSize(new Dimension(790,600));
		total.add(valueview,BorderLayout.CENTER);

		typebox=new JPanel();
		//typebox.setBorder(BorderFactory.createLoweredBevelBorder());
		typebox.setPreferredSize(new Dimension(210,40));
		cardview.add(typebox,BorderLayout.NORTH);
		
		ArrayList<Node> types=cm.getComponent().get_card_type_list();
		String[] types_ = new String[types.size()];
		for(int loop=0;loop<types.size();loop++)
			types_[loop]=types.get(loop).getData().toString();
		
		type_select=new JComboBox<String>(types_);
		type_select.setPreferredSize(new Dimension(100,30));
		type_select.setSelectedIndex(0);
		typebox.add(new JLabel("Card Type "));
		typebox.add(type_select);

		cardlist=new JPanel();
		cardlist.setBorder(BorderFactory.createLoweredBevelBorder());
		cardlist.setPreferredSize(new Dimension(210,50));
		cardview.add(cardlist,BorderLayout.CENTER);
		
		ArrayList<Node> a=cm.getComponent().getallcards((String)type_select.getSelectedItem()).getAllNode();
		card_list_model = new DefaultListModel<Node>();
		for(int loop=0;loop<a.size();loop++)
		{
			card_list_model.addElement(a.get(loop));
		}
		card_list = new JList<Node>(a.toArray(new Node[a.size()])); //data has type Object[]
		card_list=new JList<Node>(card_list_model);
		card_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		card_list.setLayoutOrientation(JList.VERTICAL);
		//card_list.setVisibleRowCount(-1);
		card_list_scroller = new JScrollPane(card_list);
		card_list_scroller.setPreferredSize(new Dimension(180, 470));
		cardlist.add(card_list_scroller);
		
		buttonbox=new JPanel();
		buttonbox.setBorder(BorderFactory.createLoweredBevelBorder());
		buttonbox.setPreferredSize(new Dimension(210,60));
		cardview.add(buttonbox,BorderLayout.SOUTH);
		
		b_card_add=new JButton("Add Card");
		b_card_add.setPreferredSize(new Dimension(90,50));
		b_card_add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String type=(String)type_select.getSelectedItem();
				Node new_node=cm.make_new_card(type);
				new_node.setData("New "+type+" Card");
				cm.getComponent().getallcards(type).addChildNode(new_node);
				card_list_model.addElement(new_node);
				// TODO: Update
			}});
		buttonbox.add(b_card_add);
		b_card_delete=new JButton("Delete Card");
		b_card_delete.setPreferredSize(new Dimension(100,50));
		b_card_delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String type=(String)type_select.getSelectedItem();
				Node cur_node=card_list.getSelectedValue();
				cm.delete_card(cur_node);
				System.out.println(card_list_model.removeElement(cur_node));
				// TODO: Update
			}});
		buttonbox.add(b_card_delete);
		
		//comp_table=new NewTable();
		// TODO: Initialize comp_table
		//comp_sc=new JScrollPane();
		//comp_sc.add(comp_table);
		//valueview.add(comp_table);
		
		this.setLayout(new BorderLayout());
		this.add(total);
		this.revalidate();
		
	}
	
	public JComponent get_scpane(){
		return this;
	}
	
	
	public void update_window_size()
	{
		//set_board_size();
	}
    
	
	private void set_board_size(Dimension d)
	{
		System.out.println(d);
		//rule_pane.setPreferredSize(d);
		//rule_pane.revalidate();
		//rule_pane.repaint();
	}

}
