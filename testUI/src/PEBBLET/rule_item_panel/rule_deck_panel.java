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

public class rule_deck_panel {
	private Node rule_deck_node;
	private JComboBox<String> deck_ComboBox;
	private JPanel deck_panel;
	public rule_deck_panel(){
		rule_deck_node = new Node();
		deck_ComboBox = new JComboBox<String>();
		deck_ComboBox.setBounds(0, 0, 150, 20);
		deck_panel = new JPanel();
		deck_panel.setLayout(new BoxLayout(deck_panel, BoxLayout.LINE_AXIS));
		deck_panel.add(deck_ComboBox);
		
	}
	public rule_deck_panel(Node _input, final RuleManager rm){
		rule_deck_node = _input;
		deck_ComboBox = new JComboBox<String>();
		deck_ComboBox.setBounds(0, 0, 150, 20);
		set_comboitem(rm.getSelectionCases(_input), rm);
		deck_panel = new JPanel();
		deck_panel.setLayout(new BoxLayout(deck_panel, BoxLayout.LINE_AXIS));
		deck_panel.add(deck_ComboBox);
		deck_panel.setMinimumSize(new Dimension(150,30));
		deck_panel.setPreferredSize(new Dimension(150, 30));
		
	}
	
	public void set_comboitem(String[] _list, final RuleManager rm){
		for(int i = 0; i < _list.length; i++){
			deck_ComboBox.addItem(_list[i]);
		}
		deck_ComboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					int box_index = deck_ComboBox.getSelectedIndex();
					Node n = rm.applySelectionCases(rule_deck_node, box_index);
					rule_deck_node.replace(n);
				}
			}
		});
	}
	public void addtoPanel(JComponent _panel, int x, int y ){
		//_panel.setLayout(new BoxLayout(deck_panel, BoxLayout.LINE_AXIS));
		deck_panel.setBounds(x,y,150,30);
		_panel.add(deck_panel);
		_panel.repaint();
		_panel.validate();
	}

}
