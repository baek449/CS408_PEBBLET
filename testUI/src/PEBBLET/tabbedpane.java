package PEBBLET;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import PEBBLET.panel.Component_pane;
import PEBBLET.panel.Debug_pane;
import PEBBLET.panel.Def_pane;
import PEBBLET.panel.Rule_pane;

public class tabbedpane extends JPanel{
	public tabbedpane(){
		super(new GridLayout(1, 1));
	 
		JTabbedPane tabbedpane = new JTabbedPane();
	
//		JComponent definition = makeTextPanel("Definition");
		
		
		Def_pane def = new Def_pane();
		
		tabbedpane.addTab("Definition", null, def.get_scpane(), "make definition");
		
		Rule_pane rule = new Rule_pane();
		tabbedpane.addTab("Rule", null, rule.get_scpane(), "make rule");
	
		Component_pane comp = new Component_pane();
		tabbedpane.addTab("Component", null, comp.get_scpane(), "make component");
		
//		JComponent debug = makePanel();
		Debug_pane debug = new Debug_pane();
		tabbedpane.addTab("Debug", null, debug.get_scpane(), "debugging");
		
		add(tabbedpane);
		tabbedpane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		
	
	}
	
	protected static JPanel makePanel() {
        JPanel panel = new JPanel(true);
        
        panel.setPreferredSize(new Dimension(0,600));
        
//        panel.add(Box.createRigidArea(new Dimension(0, 650)));//default 650
        Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        Border loweredbevel = BorderFactory.createLoweredBevelBorder();
        panel.setBorder(BorderFactory.createCompoundBorder(raisedetched, loweredbevel));


        JScrollPane scrollpane = new JScrollPane(panel);
        scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollpane.setViewportView(panel);
        return panel;
    }
	
	public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("TabbedPaneDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
        //Add content to the window.
        //frame.add(new tabbedpane(), BorderLayout.CENTER);
         
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
	
	

}
