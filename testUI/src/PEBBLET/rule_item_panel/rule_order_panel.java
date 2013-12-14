package PEBBLET.rule_item_panel;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

import manager.Node;
import manager.RuleManager;

public class rule_order_panel {
	private Node rule_order_node;
	private JComboBox<String> order_ComboBox;
	private JPanel order_panel;
	public rule_order_panel(){
		rule_order_node = new Node();
		order_ComboBox = new JComboBox<String>();
		order_ComboBox.setBounds(0, 0, 150, 20);
		order_panel = new JPanel();
		order_panel.setLayout(null);
		order_panel.add(order_ComboBox);
		
	}
	public rule_order_panel(Node _input, final RuleManager rm){
		rule_order_node = _input;
		order_ComboBox = new JComboBox<String>();
		order_ComboBox.setBounds(0, 0, 150, 20);
		set_comboitem(rm.getSelectionCases(_input), rm);
		order_panel = new JPanel();
		order_panel.setLayout(null);
		order_panel.add(order_ComboBox);
		order_panel.setPreferredSize(new Dimension(500, 30));
		
	}
	
	public void set_comboitem(String[] _list, final RuleManager rm){
		for(int i = 0; i < _list.length; i++){
			order_ComboBox.addItem(_list[i]);
		}
		order_ComboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					int box_index = order_ComboBox.getSelectedIndex();
					Node n = rm.applySelectionCases(rule_order_node, box_index);
					rule_order_node.replace(n);
				}
			}
		});
	}
	public void addtoPanel(JComponent _panel, int x, int y ){
		_panel.setLayout(null);
		order_panel.setBounds(x,y,500,30);
		_panel.add(order_panel);
		_panel.repaint();
		_panel.validate();
	}

}
