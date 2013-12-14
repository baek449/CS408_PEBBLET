package PEBBLET.rule_item_panel;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

import manager.Node;
import manager.RuleManager;

public class rule_number_panel {
	private Node rule_number_node;
	private JComboBox<String> number_ComboBox;
	private JPanel number_panel;
	public rule_number_panel(){
		rule_number_node = new Node();
		number_ComboBox = new JComboBox<String>();
		number_ComboBox.setBounds(0, 0, 150, 20);
		number_panel = new JPanel();
		number_panel.setLayout(null);
		number_panel.add(number_ComboBox);
		
	}
	public rule_number_panel(Node _input, final RuleManager rm){
		rule_number_node = _input;
		number_ComboBox = new JComboBox<String>();
		number_ComboBox.setBounds(0, 0, 150, 20);
		set_comboitem(rm.getSelectionCases(_input), rm);
		number_panel = new JPanel();
		number_panel.setLayout(null);
		number_panel.add(number_ComboBox);
		number_panel.setPreferredSize(new Dimension(500, 30));
		
	}
	
	public void set_comboitem(String[] _list, final RuleManager rm){
		for(int i = 0; i < _list.length; i++){
			number_ComboBox.addItem(_list[i]);
		}
		number_ComboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					int box_index = number_ComboBox.getSelectedIndex();
					Node n = rm.applySelectionCases(rule_number_node, box_index);
					rule_number_node.replace(n);
				}
			}
		});
	}
	public void addtoPanel(JComponent _panel, int x, int y ){
		_panel.setLayout(null);
		number_panel.setBounds(x,y,500,30);
		_panel.add(number_panel);
		_panel.repaint();
		_panel.validate();
	}

}
