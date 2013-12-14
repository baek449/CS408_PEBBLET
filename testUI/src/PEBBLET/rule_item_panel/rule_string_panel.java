package PEBBLET.rule_item_panel;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

import manager.Node;
import manager.RuleManager;

public class rule_string_panel {
	private Node rule_string_node;
	private JComboBox<String> string_ComboBox;
	private JPanel string_panel;
	public rule_string_panel(){
		rule_string_node = new Node();
		string_ComboBox = new JComboBox<String>();
		string_ComboBox.setBounds(0, 0, 150, 20);
		string_panel = new JPanel();
		string_panel.setLayout(null);
		string_panel.add(string_ComboBox);
		
	}
	public rule_string_panel(Node _input, final RuleManager rm){
		rule_string_node = _input;
		string_ComboBox = new JComboBox<String>();
		string_ComboBox.setBounds(0, 0, 150, 20);
		set_comboitem(rm.getSelectionCases(_input), rm);
		string_panel = new JPanel();
		string_panel.setLayout(null);
		string_panel.add(string_ComboBox);
		string_panel.setPreferredSize(new Dimension(500, 30));
		
	}
	
	public void set_comboitem(String[] _list, final RuleManager rm){
		for(int i = 0; i < _list.length; i++){
			string_ComboBox.addItem(_list[i]);
		}
		string_ComboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					int box_index = string_ComboBox.getSelectedIndex();
					Node n = rm.applySelectionCases(rule_string_node, box_index);
					rule_string_node.replace(n);
				}
			}
		});
	}
	public void addtoPanel(JComponent _panel, int x, int y ){
		_panel.setLayout(null);
		string_panel.setBounds(x,y,500,30);
		_panel.add(string_panel);
		_panel.repaint();
		_panel.validate();
	}

}
