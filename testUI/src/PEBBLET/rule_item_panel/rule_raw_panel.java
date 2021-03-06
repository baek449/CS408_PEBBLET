package PEBBLET.rule_item_panel;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import manager.Node;
import manager.RuleManager;

public class rule_raw_panel {
	private Node rule_raw_node;
	private JTextField raw_text;
	private JPanel raw_panel;
	
	public rule_raw_panel(){
		rule_raw_node = new Node();
		raw_text = new JTextField();
		raw_text.setBounds(0, 0, 150, 20);
		raw_panel = new JPanel();
		raw_panel.setLayout(new BoxLayout(raw_panel, BoxLayout.LINE_AXIS));
		raw_panel.add(raw_text);
		
	}
	
	public rule_raw_panel(Node input, RuleManager rm){
		rule_raw_node = input;
		raw_text = new JTextField();
		raw_text.setBounds(0, 0, 150, 20);
		raw_panel = new JPanel();
		raw_panel.setLayout(new BoxLayout(raw_panel, BoxLayout.LINE_AXIS));
		raw_panel.add(raw_text);
		raw_panel.setMinimumSize(new Dimension(150,30));
		raw_panel.setPreferredSize(new Dimension(150,30));
	}
	
	public void addtoPanel(JComponent _panel, int x, int y ){
		//_panel.setLayout(new BoxLayout(raw_panel, BoxLayout.LINE_AXIS));
		raw_panel.setBounds(x,y,150,30);
		_panel.add(raw_panel);
//		_panel.setPreferredSize(new Dimension(_panel.getWidth()+150, _panel.getHeight()+20));
		_panel.repaint();
		_panel.validate();
	}
}
