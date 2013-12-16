package PEBBLET.panel;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import PEBBLET.panel_inside;
import manager.DefinitionManager;
import manager.Node;
import manager.RuleManager;

public class Rule_pane extends JComponent {

	protected RuleManager rm;
	protected JPanel rule_pane;
	protected JScrollPane rule_sc;
	private panel_inside p;
	
	public Rule_pane(){
		rm = new RuleManager();
		rm.updateVariableList(new DefinitionManager());
		rule_pane = new JPanel(true);
		rule_pane.setPreferredSize(new Dimension(900, 500));
		
		rule_sc = new JScrollPane(rule_pane);
		rule_sc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		rule_sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		rule_sc.setPreferredSize(new Dimension(900, 500));
		
		make_action();
		
	}

	public Rule_pane(DefinitionManager dm){
		rm = new RuleManager();
		rm.updateVariableList(dm);
		rule_pane = new JPanel(true);
		rule_pane.setPreferredSize(new Dimension(900, 500));
		
		rule_sc = new JScrollPane(rule_pane);
		rule_sc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		rule_sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		rule_sc.setPreferredSize(new Dimension(900, 500));
		
		make_action();
		
	}

	public Rule_pane(Node target, DefinitionManager dm){
		rm = new RuleManager(target);
		rm.updateVariableList(dm);
		rule_pane = new JPanel(true);
		rule_pane.setPreferredSize(new Dimension(900, 500));
		
		rule_sc = new JScrollPane(rule_pane);
		rule_sc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		rule_sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		rule_sc.setPreferredSize(new Dimension(900, 500));
		
		make_action();
		
	}
	
	public void make_action(){
		p=new panel_inside(rm,true,false,true,this);
		p.setNode(rm.getRule().getRoot());
		p.remove_combo();
		
		rule_pane.setLayout(new FlowLayout(FlowLayout.LEFT));
		rule_pane.add(p);
	}
	
	public JComponent get_scpane(){
		return rule_sc;
	}
	
	
	public void update_window_size()
	{
		set_board_size(new Dimension(p.getSize().width*9/5,p.getSize().height*7/5));
	}
    
	
	private void set_board_size(Dimension d)
	{
		rule_pane.setPreferredSize(d);
		rule_pane.revalidate();
		rule_pane.repaint();
	}

}
