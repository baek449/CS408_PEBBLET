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

public class tabbedpane extends JPanel{
	public tabbedpane(){
		super(new GridLayout(1, 1));
	 
		JTabbedPane tabbedpane = new JTabbedPane();
	
//		JComponent definition = makeTextPanel("Definition");
	
		
		JScrollPane defsc = new JScrollPane();
		JScrollPane rule = new JScrollPane();
		
		defsc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		defsc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);		
		
		
		tabbedpane.addTab("Definition", null, defsc, "make definition");
		
//		JComponent rule = makeTextPanel("Rule");
		tabbedpane.addTab("Rule", null, rule, "make rule");
	
//		JComponent component = makePanel();//임시로 def를 display
		Def_pane def1 = new Def_pane();
		tabbedpane.addTab("Component", null, def1.get_scpane(), "make component");
		
		JComponent debug = makePanel();
		tabbedpane.addTab("Debug", null, debug, "debugging");
		
		add(tabbedpane);
		tabbedpane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		
	
	}
	
	protected static JScrollPane makePanel() {
        JPanel panel = new JPanel(true);
        
        panel.setPreferredSize(new Dimension(0,600));
        
//        panel.add(Box.createRigidArea(new Dimension(0, 650)));//default 650
        Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        Border loweredbevel = BorderFactory.createLoweredBevelBorder();
        panel.setBorder(BorderFactory.createCompoundBorder(raisedetched, loweredbevel));


        JScrollPane scrollpane = new JScrollPane(panel);
        scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollpane.setViewportView(panel);
        return scrollpane;
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
