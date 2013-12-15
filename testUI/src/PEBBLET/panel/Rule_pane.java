package PEBBLET.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;

import PEBBLET.rule_item_panel.rule_action_panel;
import PEBBLET.rule_item_panel.rule_card_panel;
import PEBBLET.rule_item_panel.rule_cond_panel;
import PEBBLET.rule_item_panel.rule_deck_panel;
import PEBBLET.rule_item_panel.rule_namedaction_panel;
import PEBBLET.rule_item_panel.rule_number_panel;
import PEBBLET.rule_item_panel.rule_order_panel;
import PEBBLET.rule_item_panel.rule_player_panel;
import PEBBLET.rule_item_panel.rule_raw_panel;
import PEBBLET.rule_item_panel.rule_string_panel;
import manager.DefinitionManager;
import manager.Node;
import manager.RuleManager;

public class Rule_pane extends JComponent {

	private RuleManager rm;
	
	private JPanel rule_pane;
	private JScrollPane rule_sc;
	
	private int[] action_mul_index = {0};
	private Node action_mul_node;
	
	private int preStatus = 0;
	private int endof_rule_pane = 30;
	private int count_all = 0;
	
	private JLabel closeMark;
	
	private int xpos =0;
	
	public Rule_pane(){
		rm = new RuleManager();
		rm.updateVariableList(new DefinitionManager());
		action_mul_node = rm.search(action_mul_index);
		closeMark = new JLabel("}");
		rule_pane = new JPanel(true);
		rule_pane.setPreferredSize(new Dimension(900, 500));
		
		rule_sc = new JScrollPane(rule_pane);
		rule_sc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		rule_sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		rule_sc.setPreferredSize(new Dimension(900, 600));
		
		make_action();
		
	}
	
	public void make_action(){
		rule_pane.setLayout(null);
		
		JLabel open_mark = new JLabel("{");
		open_mark.setBounds(5,5,50,20);
		
		closeMark.setBounds(5, 90, 50, 20);
		
		rule_pane.add(open_mark);
		rule_pane.add(closeMark);
		
		final JButton add_action = new JButton("add");
		add_action.setBounds(50, 60, 50, 20);
		rule_pane.add(add_action);
		add_action.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				endof_rule_pane +=30;
				make_action_item();
				add_action.setLocation(add_action.getX(), add_action.getY()+30);
				closeMark.setLocation(closeMark.getX(), closeMark.getY()+30);
				
				rule_pane.setPreferredSize(new Dimension(900+xpos, endof_rule_pane+90));
				
				rule_pane.repaint();
				rule_pane.validate();
				
				rm.getRule().getRoot().printAll();
				System.out.println("getallcount "+get_all_count(rule_pane));
				
				
				
			}
		});
		
		
		make_action_item();
		
	}
	
	public void make_action_item(){
		final JPanel action_item_pane = new JPanel();
		final Node action_item_node = rm.onAddNew(action_mul_node);
		action_mul_node.addChildNode(action_item_node);
		
		//action_item_pane.setLayout(new BoxLayout(action_item_pane,BoxLayout.LINE_AXIS));
		
		
		final JComboBox<String> box_type = new JComboBox<String>();
		
		box_type.setBounds(0,0,120,20);
		
		box_type.addItem("Select type");
		String[] box_item = rm.getSelectionCases(action_item_node);
		for(int i = 0; i < box_item.length ; i++){
			box_type.addItem(box_item[i]);
		}
		
		box_type.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					int box_index = box_type.getSelectedIndex()-1;
					Node n = rm.applySelectionCases(action_item_node, box_index);
					System.out.println(box_index);
					action_item_node.replace(n);
					for(int i = 0; i < action_item_node.numChildren(); i++){
						switch(action_item_node.getChildNode(i).get_node_type()){
						case nd_action:
							rule_action_panel _action_panel = new rule_action_panel(action_item_node.getChildNode(i), rm);
//							rule_action_panel _action_panel = new rule_action_panel();
							xpos += 150;
							//action_item_pane.setPreferredSize(new Dimension(900, 30));
							_action_panel.addtoPanel(action_item_pane, xpos, 0);
							/*_action_panel.setBorder(BorderFactory.createLineBorder(Color.RED));
							action_item_pane.add(_action_panel);
							System.out.println(action_item_pane.getLayout());
							action_item_pane.setBorder(BorderFactory.createLineBorder(Color.BLUE));
							//_action_panel.setPreferredSize(new Dimension(500,30));
							//_action_panel.setSize(500, 30);
							action_item_pane.revalidate();
							action_item_pane.repaint();*/
							
							break;
						case nd_player:
							rule_player_panel player_panel = new rule_player_panel(action_item_node.getChildNode(i), rm);
							xpos += 150;
							player_panel.addtoPanel(action_item_pane, xpos, 0);
							break;
						case nd_deck:
							rule_deck_panel deck_panel = new rule_deck_panel(action_item_node.getChildNode(i), rm);
							xpos += 150;
							deck_panel.addtoPanel(action_item_pane, xpos, 0);
							break;
						case nd_card:
							rule_card_panel card_panel = new rule_card_panel(action_item_node.getChildNode(i), rm);
							xpos += 150;
							card_panel.addtoPanel(action_item_pane, xpos, 0);
							break;
						case nd_cond:
							rule_cond_panel cond_panel = new rule_cond_panel(action_item_node.getChildNode(i), rm);
							xpos += 150;
							cond_panel.addtoPanel(action_item_pane, xpos, 0);
							break;
						case nd_num:
							rule_number_panel num_panel = new rule_number_panel(action_item_node.getChildNode(i), rm);
							xpos += 150;
							num_panel.addtoPanel(action_item_pane, xpos, 0);
							break;
						case nd_str:
							rule_string_panel str_panel = new rule_string_panel(action_item_node.getChildNode(i), rm);
							xpos += 150;
							str_panel.addtoPanel(action_item_pane, xpos, 0);
							break;
						case nd_order:
							rule_order_panel ord_panel = new rule_order_panel(action_item_node.getChildNode(i), rm);
							xpos += 150;
							ord_panel.addtoPanel(action_item_pane, xpos, 0);
							break;
						case nd_namedAction:
							rule_namedaction_panel natc_panel = new rule_namedaction_panel(action_item_node.getChildNode(i), rm);
							xpos += 150;
							natc_panel.addtoPanel(action_item_pane, xpos, 0);
							break;
						case nd_raw:
							rule_raw_panel raw_panel = new rule_raw_panel(action_item_node.getChildNode(i), rm);
							xpos += 150;
							raw_panel.addtoPanel(action_item_pane, xpos, 0);
							break;
						default:
							break;
							
						}
					}
					
					action_item_pane.setPreferredSize(new Dimension(150,30));
					rule_pane.setPreferredSize(new Dimension(900,30));
					//check_and_resize(action_item_pane);
					System.out.println(action_item_pane.getPreferredSize());
					System.out.println(action_item_pane.getSize());

				}
			}
		});
		
		
		action_item_pane.setBounds(50,endof_rule_pane, 900+xpos, 30);
		//action_item_pane.setSize(new Dimension(150,30));
		action_item_pane.add(box_type);
		//action_item_pane.add(Box.createHorizontalGlue());
		
		rule_pane.add(action_item_pane);
		rule_pane.repaint();
		rule_pane.validate();
		
		
	}
	
	public void check_and_resize(JComponent _action_item_pane){
		int i = _action_item_pane.getComponentCount();
//		count_all = _action_item_pane.getComponentCount();
		System.out.println(i);
//		i*150 == weight
		_action_item_pane.setPreferredSize(new Dimension(i*300, 30));
		_action_item_pane.setSize(new Dimension(i*300, 30));
		if(i*300>=rule_pane.getWidth()){
			rule_pane.setPreferredSize(new Dimension(i*300,rule_pane.getHeight()));
			rule_pane.setSize(new Dimension(count_all*300, rule_pane.getHeight()));
		}
		System.out.println(_action_item_pane.getSize());
		System.out.println(_action_item_pane.getPreferredSize());
		_action_item_pane.revalidate();
		rule_pane.revalidate();
		System.out.println("after revalidate");
		System.out.println(_action_item_pane.getSize());
		System.out.println(_action_item_pane.getPreferredSize());
	}
	
	public JComponent get_scpane(){
		return rule_sc;
	}
	
	public int get_all_count(JComponent comp){
		int sum = 0;
		for(int i = 0; i < comp.getComponentCount(); i++){
			Component c=comp.getComponent(i);
			if(c.getClass()==JComboBox.class)
			{
				sum++;
				continue;
			}
			if(!JComponent.class.isAssignableFrom(c.getClass())) continue;
			JComponent child = (JComponent) c;
			sum += get_all_count(child);
		}
		
		return sum+1;
	}
	
}
