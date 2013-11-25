package PEBBLET;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.Container;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.sun.corba.se.spi.ior.MakeImmutable;

//import com.sun.tools.javah.MainDoclet;


public class mainUI extends JFrame{
	public mainUI(){
		
		JMenuBar menubar= new JMenuBar();
		//ImageIcon exit_icon = new ImageIcon(getClass().getResource("exit.png")); 
		
		
		//file menu
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		
		JMenuItem file_new_MenuItem = new JMenuItem("New");
		file_new_MenuItem.setMnemonic(KeyEvent.VK_N);
		file_new_MenuItem.setToolTipText("New application");
		
		JMenuItem file_load_MenuItem = new JMenuItem("Load");
		file_load_MenuItem.setMnemonic(KeyEvent.VK_L);
		file_load_MenuItem.setToolTipText("Load application");
		
		JMenuItem file_save_MenuItem = new JMenuItem("Save");
		file_save_MenuItem.setMnemonic(KeyEvent.VK_S);
		file_save_MenuItem.setToolTipText("Save application");
		
		JMenuItem file_save_as_MenuItem = new JMenuItem("Save As");
		file_save_as_MenuItem.setMnemonic(KeyEvent.VK_A);
		//file_save_as_MenuItem.setMnemonic(KeyStroke.getKeyStroke(KeyEvent.VK_S,
		//										java.awt.event.InputEvent.CTRL_DOWN_MASK));
		file_new_MenuItem.setToolTipText("Save application as different name");
		
		
		JMenuItem file_exit_MenuItem= new JMenuItem("Exit");
		file_exit_MenuItem.setMnemonic(KeyEvent.VK_E);
		file_exit_MenuItem.setToolTipText("Exit application");
	    file_exit_MenuItem.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        
        file.add(file_exit_MenuItem);
        file.add(file_new_MenuItem);
        file.add(file_load_MenuItem);
        file.add(file_save_MenuItem);
        file.add(file_save_as_MenuItem);
        menubar.add(file);
        
        
        //edit
        JMenu edit = new JMenu("Edit");
        edit.setMnemonic(KeyEvent.VK_E);
        JMenuItem edit_find_MenuItem = new JMenuItem("find"); 
        edit.add(edit_find_MenuItem);
        //edit.add(eMenuItem);
        menubar.add(edit);
        
        
        //format
        JMenu format = new JMenu("Format");
        format.setMnemonic(KeyEvent.VK_O);
        JMenuItem format_text_MenuItem = new JMenuItem("text");
        format.add(format_text_MenuItem);
        menubar.add(format);
        
        //view
        JMenu view = new JMenu("View");
        view.setMnemonic(KeyEvent.VK_V);
        JMenuItem view_window_MenuItem = new JMenuItem("window");
        view.add(view_window_MenuItem);
        menubar.add(view);
        
        
        //help
        JMenu help = new JMenu("Help");
        help.setMnemonic(KeyEvent.VK_H);
		
		//help.add(eMenuItem);
		menubar.add(help);

		
		setJMenuBar(menubar);
		

        
		setTitle("PEBBLET");
		setSize(1400,1024);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
		
		
	}
	
	protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }
	

	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				mainUI ex = new mainUI();
				
				JTabbedPane pane = new JTabbedPane();
				
				tabbedpane tpane = new tabbedpane();
				
				JComponent def = tpane.makeTextPanel("Definition");
				JComponent rule = tpane.makeTextPanel("Rule");
				JComponent comp = tpane.makeTextPanel("Component");
				JComponent debug = tpane.makeTextPanel("Debug"); 
				
				
				pane.addTab("Definition", null, def, "make definition");
				pane.addTab("Rule", null, rule, "make rule");
				pane.addTab("Component", null, comp, "make component");
				pane.addTab("Debugging", null, debug, "start debugging");
				
				Container contentpane = ex.getContentPane();
				contentpane.add(pane, BorderLayout.NORTH);
				
				ex.setVisible(true);
				
			}
		});
	}
}