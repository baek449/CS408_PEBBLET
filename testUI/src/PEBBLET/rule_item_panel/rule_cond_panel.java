package PEBBLET.rule_item_panel;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

import manager.Node;
import manager.RuleManager;

public class rule_cond_panel {
	private Node rule_cond_node;
	private JComboBox<String> cond_ComboBox;
	private JPanel cond_panel;
	public rule_cond_panel() {
		// TODO Auto-generated constructor stub
		rule_cond_node = new Node();
		cond_ComboBox = new JComboBox<String>();
		cond_ComboBox.setBounds(0, 0, 150, 20);
		cond_panel = new JPanel();
		cond_panel.setLayout(null);
		cond_panel.add(cond_ComboBox);
		
	}
	public rule_cond_panel(Node _input, final RuleManager rm){
		rule_cond_node = _input;
		cond_ComboBox = new JComboBox<String>();
		cond_ComboBox.setBounds(0, 0, 150, 20);
		set_comboitem(rm.getSelectionCases(_input), rm);
		cond_panel = new JPanel();
		cond_panel.setLayout(null);
		cond_panel.add(cond_ComboBox);
		cond_panel.setPreferredSize(new Dimension(500, 30));
		
	}
	
	public void set_comboitem(String[] _list, final RuleManager rm){
		for(int i = 0; i < _list.length; i++){
			cond_ComboBox.addItem(_list[i]);
		}
		cond_ComboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					int box_index = cond_ComboBox.getSelectedIndex();
					Node n = rm.applySelectionCases(rule_cond_node, box_index);
					rule_cond_node.replace(n);
				}
			}
		});
	}
	public void addtoPanel(JComponent _panel, int x, int y ){
		_panel.setLayout(null);
		cond_panel.setBounds(x,y,500,30);
		_panel.add(cond_panel);
		_panel.repaint();
		_panel.validate();
	}

}
