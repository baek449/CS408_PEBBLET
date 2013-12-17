package PEBBLET.panel;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import manager.DefinitionManager;
import DebugManager.DebugManager;

public class Debug_pane extends JComponent{
	private JPanel debug_pane;
	private JScrollPane debug_sc;
	private JButton start;
	private boolean completed = false;
	private JButton start_testplay;
	private JPanel debug_window_pane;
	
	private int msg_pos = 20;
	
	public Debug_pane(){
		debug_pane = new JPanel(true);
		debug_pane.setPreferredSize(new Dimension(900, 500));
		
		debug_sc = new JScrollPane(debug_pane);
		debug_sc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		debug_sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		debug_sc.setPreferredSize(new Dimension(900, 600));
		
		
		Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		debug_window_pane = new JPanel();
		debug_window_pane.setBorder(BorderFactory.createCompoundBorder(raisedetched, loweredbevel));
		debug_window_pane.setBounds(50, 50, 500, 300);
		
		start = new JButton("Start Debugging");
		start_testplay = new JButton("Start TestPlay");
		start.setBounds(5, 400, 200,30);
		start_testplay.setBounds(300,400, 200, 30);
		debug_pane.setLayout(null);
		debug_pane.add(debug_window_pane);
		debug_pane.add(start);
		debug_pane.add(start_testplay);
		
		
	}
	public Debug_pane(DefinitionManager dm_){
		debug_pane = new JPanel(true);
		debug_pane.setPreferredSize(new Dimension(900, 500));
		
		debug_sc = new JScrollPane(debug_pane);
		debug_sc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		debug_sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		debug_sc.setPreferredSize(new Dimension(900, 600));
		
		
		Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		debug_window_pane = new JPanel();
		debug_window_pane.setBorder(BorderFactory.createCompoundBorder(raisedetched, loweredbevel));
		debug_window_pane.setBounds(50, 50, 500, 300);
		
		start = new JButton("Start Debugging");
		start_testplay = new JButton("Start TestPlay");
		start.setBounds(5, 400, 200,30);
		start_testplay.setBounds(300,400, 200, 30);
		debug_pane.setLayout(null);
		debug_pane.add(debug_window_pane);
		debug_pane.add(start);
		debug_pane.add(start_testplay);
		
	}
	
	public void make_debug_msg(String msg_){
		JLabel msg = new JLabel(msg_);
//		msg.setPreferredSize(new Dimension(msg_.length()*10, 30));
//		msg.setLocation(5, msg_pos);
		msg.setBounds(5, msg_pos, msg_.length()*15,30);
		msg_pos+=30;
		debug_pane.setLayout(null);
		debug_pane.add(msg);
	}
	
	public JComponent get_scpane(){
		return debug_sc;
	}
}
