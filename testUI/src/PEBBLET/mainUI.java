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
//import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
//import javax.swing.UIManager;

//import com.sun.corba.se.spi.ior.MakeImmutable;

//import com.sun.tools.javah.MainDoclet;


public class mainUI extends JFrame{
	public mainUI(){
		
//		
//		JFrame frame = new JFrame("Menu");
		
		JMenuBar menubar= new JMenuBar();
		//ImageIcon exit_icon = new ImageIcon(getClass().getResource("exit.png")); 
		
//		frame.setJMenuBar(menubar);
//		
//		menubar.setBorder(new BevelBorder(BevelBorder.RAISED));
//		frame.getContentPane().add(menubar, BorderLayout.SOUTH);
		
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
        
        file.add(file_new_MenuItem);
        file.add(file_load_MenuItem);
        file.add(file_save_MenuItem);
        file.add(file_save_as_MenuItem);
        file.addSeparator();
        file.add(file_exit_MenuItem);
        menubar.add(file);
        
        
        //edit
        JMenu edit = new JMenu("Edit");
        edit.setMnemonic(KeyEvent.VK_E);
        JMenuItem edit_undo_MenuItem = new JMenuItem("Undo"); 
        edit.add(edit_undo_MenuItem);
        JMenuItem edit_redo_MenuItem = new JMenuItem("Redo");
        edit.add(edit_redo_MenuItem);
        JMenuItem edit_cut_MenuItem = new JMenuItem("Cut");
        edit.add(edit_cut_MenuItem);
        JMenuItem edit_copy_MenuItem = new JMenuItem("Copy");
        edit.add(edit_copy_MenuItem);
        JMenuItem edit_paste_MenuItem = new JMenuItem("Paste");
        edit.add(edit_paste_MenuItem);
        JMenuItem edit_find_MenuItem = new JMenuItem("Find");
        edit.add(edit_find_MenuItem);
        //edit.add(eMenuItem);
        menubar.add(edit);
        
        //view
        JMenu view = new JMenu("View");
        view.setMnemonic(KeyEvent.VK_V);
        JMenuItem view_comment_MenuItem = new JMenuItem("Comment");
        view.add(view_comment_MenuItem);
        view.addSeparator();
        
        JMenu view_viewtab_MenuItem = new JMenu("View Tab");
        view.add(view_viewtab_MenuItem);
        menubar.add(view);
        
        //view tab submenu
        JMenuItem viewtab_Def = new JMenuItem("Definition");
        view_viewtab_MenuItem.add(viewtab_Def);
        JMenuItem viewtab_rule = new JMenuItem("Rule");
        view_viewtab_MenuItem.add(viewtab_rule);
        JMenuItem viewtab_comp = new JMenuItem("Component");
        view_viewtab_MenuItem.add(viewtab_comp);
        JMenuItem viewtab_debug = new JMenuItem("Debug");
        view_viewtab_MenuItem.add(viewtab_debug);
        
      //debug/testplay
        JMenu debug_test = new JMenu("Debug / Testplay");
        debug_test.setMnemonic(KeyEvent.VK_O);
        JMenuItem debug_startdebug_MenuItem = new JMenuItem("Start Debug");
        debug_test.add(debug_startdebug_MenuItem);
        JMenuItem debug_starttestplay_MenuItem = new JMenuItem("Start Testplay");
        debug_test.add(debug_starttestplay_MenuItem);
        JMenuItem debug_startboth_MenuItem = new JMenuItem("Start Both");
        debug_test.add(debug_startboth_MenuItem);
        
        menubar.add(debug_test);
        
        //help
        JMenu help = new JMenu("Help");
        help.setMnemonic(KeyEvent.VK_H);
		
        JMenuItem help_helpcontents_MenuItem = new JMenuItem("Help Contents");
        help.add(help_helpcontents_MenuItem);
        
        JMenu help_langdescript_MenuItem = new JMenu("Language Description");
        help.add(help_langdescript_MenuItem);
        
        //language description submenu
        JMenuItem langdescript_def = new JMenuItem("Definition");
        help_langdescript_MenuItem.add(langdescript_def);
        
        JMenuItem langdescript_rule = new JMenuItem("Rule");
        help_langdescript_MenuItem.add(langdescript_rule);
        
        JMenuItem help_debugmsg = new JMenuItem("Debug Message");
        help.add(help_debugmsg);
        help.addSeparator();
        
        JMenuItem help_about = new JMenuItem("About PEBBLET");
        help.add(help_about);
        
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