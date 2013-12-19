package PEBBLET.panel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import manager.ComponentManager;
import manager.DefinitionManager;
import manager.RuleManager;
import manager.TestplayModule;
import DebugManager.DebugManager;
import PEBBLET.TestplayUI;
import PEBBLET.tabbedpane;

public class Debug_pane extends JComponent{
	private JPanel debug_pane;
	private JScrollPane debug_sc;
	private JButton start;
	private boolean completed = false;
	private JButton start_testplay;
	private JPanel debug_window_pane;
	private JScrollPane dbg_window_sc;
	
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
//		debug_window_pane.setBorder(BorderFactory.createCompoundBorder(raisedetched, loweredbevel));
		debug_window_pane.setBounds(0, 0, 500, 300);
		debug_window_pane.setPreferredSize(new Dimension(500,300) );	
		
		dbg_window_sc  = new JScrollPane(debug_window_pane);
		dbg_window_sc.setBorder(BorderFactory.createCompoundBorder(raisedetched, loweredbevel));
		dbg_window_sc.setPreferredSize(new Dimension(600,350));
		dbg_window_sc.setSize(600, 350);
		dbg_window_sc.setLocation(50, 50);
		
		start = new JButton("Start Debugging");
		start_testplay = new JButton("Start TestPlay");
		start.setBounds(5, 400, 200,30);
		start_testplay.setBounds(300,400, 200, 30);
		debug_pane.setLayout(null);
//		debug_pane.add(debug_window_pane);
		debug_pane.add(dbg_window_sc);
		debug_pane.add(start);
		debug_pane.add(start_testplay);
		
		
	}
	
	private boolean def_complete = false;
	
	public Debug_pane(final DefinitionManager dm_){//for def debug
		debug_pane = new JPanel(true);
		debug_pane.setPreferredSize(new Dimension(900, 500));
		
		debug_sc = new JScrollPane(debug_pane);
		debug_sc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		debug_sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		debug_sc.setPreferredSize(new Dimension(900, 600));
		
		
		Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		debug_window_pane = new JPanel();
//		debug_window_pane.setBorder(BorderFactory.createCompoundBorder(raisedetched, loweredbevel));
		debug_window_pane.setBounds(0, 0, 500, 300);
		debug_window_pane.setPreferredSize(new Dimension(500,300) );	
		
		dbg_window_sc  = new JScrollPane(debug_window_pane);
		dbg_window_sc.setBorder(BorderFactory.createCompoundBorder(raisedetched, loweredbevel));
		dbg_window_sc.setPreferredSize(new Dimension(600,350));
		dbg_window_sc.setLocation(50, 50);
		dbg_window_sc.setSize(600, 350);
		
		start = new JButton("Start Debugging");
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//reset window
				debug_window_pane.removeAll();
				//reset position
				msg_pos = 20;
				
				DebugManager ddm = new DebugManager(dm_);
				
				ArrayList<String> bug_list = ddm.get_bug_list();
				
				if(bug_list.size() == 0 ){
					make_debug_msg("No Error, No Warning");
					def_complete = true;
				}
				
				for(int i=0; i< bug_list.size(); i++){
					make_debug_msg(bug_list.get(i));
				}
				start_testplay = new JButton("Next Step");
				
				start_testplay.setBounds(300,400, 200, 30);
				debug_pane.repaint();
				debug_pane.validate();
				
				if(def_complete){
					
					start_testplay.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							System.out.println(start_testplay.getParent().getParent().getParent().getParent().getParent());
							tabbedpane b = (tabbedpane) start_testplay.getParent().getParent().getParent().getParent().getParent();
							
							b.get_tabpane().addTab("Rule", null, b.get_rule().get_scpane(),"make rule");
							b.get_tabpane().addTab("Component", null, b.get_comp().get_scpane(), "make component");
							b.get_tabpane().addTab("Debug", null, b.get_bug().get_scpane(), "debugging");
							
							b.get_tabpane().setSelectedIndex(2);
							b.repaint();
							b.validate();

						}
					});;
					debug_pane.setLayout(null);
					debug_pane.add(start_testplay);					
					
				}
				
			}
		});
//		start_testplay = new JButton("Start TestPlay");
//		
		start.setBounds(5, 400, 200,30);
//		start_testplay.setBounds(300,400, 200, 30);
		debug_pane.setLayout(null);
//		debug_pane.add(debug_window_pane);
		debug_pane.add(dbg_window_sc);
		debug_pane.add(start);
		
		
		
	}
	
	public Debug_pane(final DefinitionManager dm_, final RuleManager rm_, final ComponentManager cm_){
		debug_pane = new JPanel(true);
		debug_pane.setPreferredSize(new Dimension(900, 500));
		
		debug_sc = new JScrollPane(debug_pane);
		debug_sc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		debug_sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		debug_sc.setPreferredSize(new Dimension(900, 600));
		
		
		Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		debug_window_pane = new JPanel();
//		debug_window_pane.setBorder(BorderFactory.createCompoundBorder(raisedetched, loweredbevel));
		debug_window_pane.setBounds(0, 0, 500, 300);
		debug_window_pane.setPreferredSize(new Dimension(500,300) );	
		
		dbg_window_sc  = new JScrollPane(debug_window_pane);
		dbg_window_sc.setBorder(BorderFactory.createCompoundBorder(raisedetched, loweredbevel));
		dbg_window_sc.setPreferredSize(new Dimension(600,350));
		dbg_window_sc.setLocation(50, 50);
		dbg_window_sc.setSize(600, 350);
		
		start = new JButton("Start Debugging");
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//reset window
				debug_window_pane.removeAll();
				//reset position
				msg_pos = 20;
				
				start_testplay = new JButton("Test Play");
				
				start_testplay.setBounds(300,400, 200, 30);
				debug_pane.setLayout(null);
				debug_pane.add(start_testplay);	
				debug_pane.repaint();
				debug_pane.validate();
				
//				DebugManager ddm = new DebugManager(dm_);
				DebugManager dbgm = new DebugManager(dm_, rm_, cm_);
				
				ArrayList<String> bug_list = dbgm.get_bug_list();
				
				if(bug_list.size() == 0 ){
					make_debug_msg("No Error, No Warning");
					def_complete = true;
				}
				
				for(int i=0; i< bug_list.size(); i++){
					make_debug_msg(bug_list.get(i));
				}
				
				
				if(def_complete){
					
					start_testplay.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							JFrame frame = new JFrame("Test Play");
							TestplayModule tpm = new TestplayModule(dm_.getDefinition(), cm_.getComponent());//insert dm
							TestplayUI testplay = new TestplayUI(tpm);
							testplay.run_testplay(rm_.getRule());//insert rm
							frame.setVisible(true);
							frame.addWindowListener(new WindowListener() {
								
								@Override
								public void windowOpened(WindowEvent e) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void windowIconified(WindowEvent e) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void windowDeiconified(WindowEvent e) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void windowDeactivated(WindowEvent e) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void windowClosing(WindowEvent e) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void windowClosed(WindowEvent e) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void windowActivated(WindowEvent e) {
									// TODO Auto-generated method stub
									
								}
							});
							

						}
					});;
									
					
				}
				
			}
		});
//		start_testplay = new JButton("Start TestPlay");
//		
		start.setBounds(5, 400, 200,30);
//		start_testplay.setBounds(300,400, 200, 30);
		debug_pane.setLayout(null);
//		debug_pane.add(debug_window_pane);
		debug_pane.add(dbg_window_sc);
		debug_pane.add(start);
		
		
	}
	
	public void make_debug_msg(String msg_){
		JLabel msg = new JLabel(msg_);
//		msg.setPreferredSize(new Dimension(msg_.length()*10, 30));
//		msg.setLocation(5, msg_pos);
		msg.setBounds(5, msg_pos, msg_.length()*15,30);
		msg_pos+=30;
		debug_window_pane.setPreferredSize(new Dimension(450, msg_pos+20));
		debug_window_pane.setLayout(null);
		debug_window_pane.add(msg);
		debug_window_pane.repaint();
		debug_window_pane.validate();
	}
	
	public JComponent get_scpane(){
		return debug_sc;
	}
	
	public void set_def_complete(boolean b){
		def_complete = b;
	}
	
	public boolean get_def_complete(){
		return def_complete;
	}
	
}
