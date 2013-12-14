package PEBBLET.rule_item_panel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

import manager.Node;
import manager.RuleManager;

public class rule_card_panel {
	private Node rule_card_node;
	private JComboBox<String> card_ComboBox;
	private JPanel card_panel;
	public rule_card_panel(){
		rule_card_node = new Node();
		card_ComboBox = new JComboBox<String>();
		card_ComboBox.setBounds(0, 0, 150, 20);
		card_panel = new JPanel();
		card_panel.setLayout(new BoxLayout(card_panel, BoxLayout.LINE_AXIS));
		card_panel.add(card_ComboBox);
		
	}
	public rule_card_panel(Node _input, final RuleManager rm){
		rule_card_node = _input;
		card_ComboBox = new JComboBox<String>();
		card_ComboBox.setBounds(0, 0, 150, 20);
		set_comboitem(rm.getSelectionCases(_input), rm);
		card_panel = new JPanel();
		card_panel.setLayout(new BoxLayout(card_panel, BoxLayout.LINE_AXIS));
		card_panel.add(card_ComboBox);
		card_panel.setMinimumSize(new Dimension(150,30));
		card_panel.setPreferredSize(new Dimension(150, 30));
		
	}
	
	public void set_comboitem(String[] _list, final RuleManager rm){
		for(int i = 0; i < _list.length; i++){
			card_ComboBox.addItem(_list[i]);
		}
		card_ComboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					int box_index = card_ComboBox.getSelectedIndex();
					Node n = rm.applySelectionCases(rule_card_node, box_index);
					rule_card_node.replace(n);
					
				}
			}
		});
	}
	public void addtoPanel(JComponent _panel, int x, int y ){
		//_panel.setLayout(new BoxLayout(card_panel, BoxLayout.LINE_AXIS));
		card_panel.setBounds(x,y,150,30);
		_panel.add(card_panel);
		_panel.repaint();
		_panel.validate();
	}

}
