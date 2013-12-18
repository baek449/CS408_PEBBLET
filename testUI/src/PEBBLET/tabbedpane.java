package PEBBLET;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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

import manager.ComponentManager;
import manager.DefinitionManager;
import manager.RuleManager;
import PEBBLET.panel.Component_pane;
import PEBBLET.panel.Debug_pane;
import PEBBLET.panel.Def_pane;
import PEBBLET.panel.Rule_pane;

public class tabbedpane extends JPanel{
	
	DefinitionManager dm_master;
	RuleManager rm_master;
	ComponentManager cm_master;
	
	Def_pane def;
	Debug_pane dbg_def;
	Rule_pane rule;
	Component_pane comp;
	Debug_pane debug;
	
	public boolean save(ObjectOutputStream out)
	{
		if(!dm_master.save(out)) return false;
		if(!rm_master.save(out)) return false;
		if(!cm_master.save(out)) return false;
		System.out.println("Master: ");
		rm_master.getRule().getRoot().printAll();
		return true;
	}
	public boolean load(ObjectInputStream in)
	{
		if(!dm_master.load(in)) return false;
		if(!rm_master.load(in)) return false;
		if(!cm_master.load(in)) return false;
		
		
		System.out.println("Master: ");
		rm_master.getRule().getRoot().printAll();
		tabpane.removeAll();
		tabpane = new JTabbedPane();
		
		removeAll();
		def = new Def_pane(dm_master);
		def.setparent_(this);
		tabpane.addTab("Definition", null, def.get_scpane(), "make new definition");
		
		dbg_def = new Debug_pane(dm_master);
		

		tabpane.addTab("Definition debug", null, dbg_def.get_scpane(), "check definition error");
		
		rule = new Rule_pane(dm_master, rm_master);
		tabpane.addTab("Rule", null, rule.get_scpane(), "make rule");
	
		comp = new Component_pane(dm_master,cm_master);
		tabpane.addTab("Component", null, comp.get_scpane(), "make component");
		
//		JComponent debug = makePanel();
		debug = new Debug_pane();
		tabpane.addTab("Debug", null, debug.get_scpane(), "debugging");
		
		tabpane.revalidate();
		add(tabpane);
		comp.reset_dm(dm_master);
		
		return true;
	}
	
	JTabbedPane tabpane;
	public tabbedpane(){
		super(new GridLayout(1, 1));
	 
		tabpane = new JTabbedPane();
	
//		JComponent definition = makeTextPanel("Definition");
		
		dm_master = new DefinitionManager();
		rm_master = new RuleManager();
		cm_master = new ComponentManager();
		
		def = new Def_pane(dm_master);
		def.setparent_(this);
		tabpane.addTab("Definition", null, def.get_scpane(), "make definition");
		
		dbg_def = new Debug_pane(dm_master);
		

		tabpane.addTab("Definition debug", null, dbg_def.get_scpane(), "check definition error");
		
		rule = new Rule_pane(dm_master, rm_master);
		tabpane.addTab("Rule", null, rule.get_scpane(), "make rule");
	
		comp = new Component_pane(dm_master,cm_master);
		tabpane.addTab("Component", null, comp.get_scpane(), "make component");
		
//		JComponent debug = makePanel();
		debug = new Debug_pane();
		tabpane.addTab("Debug", null, debug.get_scpane(), "debugging");
		
		add(tabpane);
		tabpane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		
	
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
	
	public DefinitionManager get_dm(){
		return dm_master;
	}
	
	public void update_manager_status(){
		//update managers status
		// called when DefinitionManager is updated and error checked.
		rm_master.updateVariableList(dm_master);
		comp.reset_dm(dm_master);
		
	}
	
	

}
