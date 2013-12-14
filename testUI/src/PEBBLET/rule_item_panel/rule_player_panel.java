package PEBBLET.rule_item_panel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

import manager.Node;
import manager.RuleManager;

public class rule_player_panel extends JPanel {
	private Node rule_player_node;
	private JComboBox<String> player_ComboBox;
	private JPanel player_panel_;
	
	public rule_player_panel(){
		rule_player_node = new Node();
		player_ComboBox = new JComboBox<String>();
		player_ComboBox.setBounds(0, 0, 150, 20);
		player_panel_ = new JPanel();
		player_panel_.setLayout(new GridLayout(1,0));
		player_panel_.add(player_ComboBox);
		
	}
	public rule_player_panel(Node _input, final RuleManager rm){
		rule_player_node = _input;
		player_ComboBox = new JComboBox<String>();
		player_ComboBox.setBounds(0, 0, 150, 20);
		set_comboitem(rm.getSelectionCases(_input), rm);
		player_panel_ = new JPanel();
		player_panel_.setLayout(new GridLayout(1,0));
		player_panel_.add(player_ComboBox);
		player_panel_.setPreferredSize(new Dimension(150, 30));
		
	}
	
	public void set_comboitem(String[] _list, final RuleManager rm){
		for(int i = 0; i < _list.length; i++){
			player_ComboBox.addItem(_list[i]);
		}
		player_ComboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					int box_index = player_ComboBox.getSelectedIndex();
					Node n = rm.applySelectionCases(rule_player_node, box_index);
					rule_player_node.replace(n);
					for(int i = 0; i < rule_player_node.numChildren(); i++){
						switch(rule_player_node.getChildNode(i).get_node_type()){
						case nd_action:
							rule_action_panel _action_panel = new rule_action_panel(rule_player_node.getChildNode(i), rm);
							_action_panel.addtoPanel(player_panel_, 150, 0);
							break;
						case nd_player:
							rule_player_panel player_panel = new rule_player_panel(rule_player_node.getChildNode(i), rm);
							player_panel.addtoPanel(player_panel_, 150, 0);
							break;
						case nd_deck:
							rule_deck_panel deck_panel = new rule_deck_panel(rule_player_node.getChildNode(i), rm);
							deck_panel.addtoPanel(player_panel_, 150, 0);
							break;
						case nd_card:
							rule_card_panel card_panel = new rule_card_panel(rule_player_node.getChildNode(i), rm);
							card_panel.addtoPanel(player_panel_, 150, 0);
							break;
						case nd_cond:
							rule_cond_panel cond_panel = new rule_cond_panel(rule_player_node.getChildNode(i), rm);
							cond_panel.addtoPanel(player_panel_, 150, 0);
							break;
						case nd_num:
							rule_number_panel num_panel = new rule_number_panel(rule_player_node.getChildNode(i), rm);
							num_panel.addtoPanel(player_panel_, 150, 0);
							break;
						case nd_str:
							rule_string_panel str_panel = new rule_string_panel(rule_player_node.getChildNode(i), rm);
							str_panel.addtoPanel(player_panel_, 150, 0);
							break;
						case nd_order:
							rule_order_panel ord_panel = new rule_order_panel(rule_player_node.getChildNode(i), rm);
							ord_panel.addtoPanel(player_panel_, 150, 0);
							break;
						case nd_namedAction:
							rule_namedaction_panel natc_panel = new rule_namedaction_panel(rule_player_node.getChildNode(i), rm);
							natc_panel.addtoPanel(player_panel_, 150, 0);
							break;
						case nd_raw:
							rule_raw_panel raw_panel = new rule_raw_panel(rule_player_node.getChildNode(i), rm);
							raw_panel.addtoPanel(player_panel_, 150, 0);
							break;
						default:
							break;
						}
					}
				}
			}
		});
	}
	public void addtoPanel(JComponent _panel, int x, int y ){
		//_panel.setLayout(new GridLayout(1,0));
		player_panel_.setBounds(x,y,150,30);
		_panel.add(player_panel_);
		_panel.repaint();
		_panel.validate();
	}

}
