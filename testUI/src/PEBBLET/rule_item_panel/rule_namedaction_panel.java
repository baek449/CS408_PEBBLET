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

public class rule_namedaction_panel {
	private Node rule_namedaction_node;
	private JComboBox<String> namedaction_ComboBox;
	private JPanel namedaction_panel;
	public rule_namedaction_panel(){
		rule_namedaction_node = new Node();
		namedaction_ComboBox = new JComboBox<String>();
		namedaction_ComboBox.setBounds(0, 0, 150, 20);
		namedaction_panel = new JPanel();
		namedaction_panel.setLayout(new BoxLayout(namedaction_panel, BoxLayout.LINE_AXIS));
		namedaction_panel.add(namedaction_ComboBox);
		
	}
	public rule_namedaction_panel(Node _input, final RuleManager rm){
		rule_namedaction_node = _input;
		namedaction_ComboBox = new JComboBox<String>();
		namedaction_ComboBox.setBounds(0, 0, 150, 20);
		set_comboitem(rm.getSelectionCases(_input), rm);
		namedaction_panel = new JPanel();
		namedaction_panel.setLayout(new BoxLayout(namedaction_panel, BoxLayout.LINE_AXIS));
		namedaction_panel.add(namedaction_ComboBox);
		namedaction_panel.setMinimumSize(new Dimension(150,30));
		namedaction_panel.setPreferredSize(new Dimension(150, 30));
		
	}
	
	public void set_comboitem(String[] _list, final RuleManager rm){
		for(int i = 0; i < _list.length; i++){
			namedaction_ComboBox.addItem(_list[i]);
		}
		namedaction_ComboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					int box_index = namedaction_ComboBox.getSelectedIndex();
					Node n = rm.applySelectionCases(rule_namedaction_node, box_index);
					rule_namedaction_node.replace(n);
				}
			}
		});
	}
	public void addtoPanel(JComponent _panel, int x, int y ){
		//_panel.setLayout(new BoxLayout(namedaction_panel, BoxLayout.LINE_AXIS));
		namedaction_panel.setBounds(x,y,150,30);
		_panel.add(namedaction_panel);
		_panel.repaint();
		_panel.validate();
	}

}
