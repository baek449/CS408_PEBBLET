package PEBBLET;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class tabbedpane extends JPanel{
	public tabbedpane(){
//		super(new GridLayout(1 1));
	 
		JTabbedPane tabbedpane = new JTabbedPane();
	
		JComponent definition = makeTextPanel("Definition");
		tabbedpane.addTab("Definition", null, definition, "make definition");
	
		JComponent rule = makeTextPanel("Rule");
		tabbedpane.addTab("Rule", null, rule, "make rule");
	
		JComponent component = makeTextPanel("Component");
		tabbedpane.addTab("Component", null, component, "make component");
	
		JComponent debug = makeTextPanel("Debug");
		tabbedpane.addTab("Debug", null, debug, "debugging");
	
	}
	
	protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }
	
	public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("TabbedPaneDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
        //Add content to the window.
        frame.add(new tabbedpane(), BorderLayout.CENTER);
         
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
	
	

}
