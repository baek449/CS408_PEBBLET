package PEBBLET.panel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import manager.RuleManager;

public class Rule_pane extends JComponent {

	private RuleManager rm;
	
	private JPanel rule_pane;
	private JScrollPane rule_sc;
	
	private int[] action_mul_index = {0,0};
	
	private int preStatus = 0;
	private int endof_rule_pane = 30;
	
	private JLabel closeMark;
	
	
	public Rule_pane(){
		rm = new RuleManager();
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
				
				rule_pane.setPreferredSize(new Dimension(900, endof_rule_pane+90));
				
				rule_pane.repaint();
				rule_pane.validate();
				
			}
		});
		
		
		make_action_item();
		
	}
	
	public void make_action_item(){
		final JPanel action_item_pane = new JPanel();
		action_item_pane.setLayout(null);
		
		final JComboBox<String> box_type = new JComboBox<String>();
		
		box_type.setBounds(0,0,120,20);
		
		int i = 1;
		box_type.addItem("Select type");
		
//		for(i = 0; )
		action_item_pane.setBounds(50,endof_rule_pane, 900, 20);
		action_item_pane.add(box_type);
		
		rule_pane.add(action_item_pane);
		rule_pane.repaint();
		rule_pane.validate();
		
		
	}
	
	public JComponent get_scpane(){
		return rule_sc;
	}
	
}
