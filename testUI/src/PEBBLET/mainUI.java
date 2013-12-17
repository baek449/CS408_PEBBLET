package PEBBLET;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.Container;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
//import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import PEBBLET.boxs.custominputbox;
import manager.Definition;
import manager.DefinitionManager;
import manager.Node;
import manager.NodeType;
import manager.Rule;
import manager.RuleCase;
import manager.RuleManager;
import manager.TestplayModule;
import sun.util.EmptyListResourceBundle;
//import javax.swing.UIManager;

//import com.sun.corba.se.spi.ior.MakeImmutable;

//import com.sun.tools.javah.MainDoclet;


public class mainUI extends JFrame{
	
	private static ArrayList<int[]> def_box_position = new ArrayList<int[]>();
	private static ArrayList<custominputbox> def_box = new ArrayList<custominputbox>();
	private static Integer def_root_index = 0;
	
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
        final JCheckBoxMenuItem view_comment_MenuItem = new JCheckBoxMenuItem("Comment");
        view_comment_MenuItem.setState(false);
        final JFrame comment_frame = new JFrame("Comment");
        comment_frame.addWindowListener(new WindowListener() {
			
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
				view_comment_MenuItem.setState(false);
				
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});;
        JLabel comment_label = new JLabel();
//        comment_label.setSize(100, 100);
        comment_frame.getContentPane().add(comment_label, BorderLayout.CENTER);
        comment_frame.setSize(400, 400);;
        view_comment_MenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(comment_frame.isVisible()){
					comment_frame.setVisible(false);
					view_comment_MenuItem.setState(false);
				} else{
					comment_frame.setVisible(true);
					view_comment_MenuItem.setState(true);
					
				}
				
				
			}
		});
        	
        
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
        debug_test.setMnemonic(KeyEvent.VK_D);
        JMenuItem debug_startdebug_MenuItem = new JMenuItem("Start Debug");
        debug_test.add(debug_startdebug_MenuItem);
        JMenuItem debug_starttestplay_MenuItem = new JMenuItem("Start Testplay");
        debug_starttestplay_MenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//				public static void testplay(){
//					JFrame tesetplayframe = new JFrame("Test Play");
//					TestplayModule tpm = new TestplayModule();//insert dm
//					TestplayUI testplay = new TestplayUI(tpm);
//					testplay.run_testplay(null);//insert rm
//				}
				
				JFrame frame = new JFrame("Test Play");
				TestplayModule tpm = new TestplayModule();//insert dm
				TestplayUI testplay = new TestplayUI(tpm);
				testplay.run_testplay(null);//insert rm
				frame.setVisible(true);
				
			}
		});
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
		setSize(1000,800);
//		setPreferredSize(new Dimension(1000, 1000));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
		
		
	}
	
	public static void addbox(JComponent comp, int x, int y){

		
		custominputbox input = new custominputbox(comp);
		////adding root index***********************************/
	
		int[] position = {x,y};
		def_box_position.add(position);
		def_box.add(input);
		
//		return input;
	}

	
	public static void addsubbox(JComponent comp, custominputbox target){

		
		custominputbox subinput = new custominputbox(comp);

		
		//index setting
		ArrayList<Integer> subinput_index = new ArrayList<Integer>(target.get_index());
		subinput_index.add(target.get_child_index_no()-1);
		subinput.set_index(subinput_index);
		System.out.println(subinput.get_index().toString());
		
		subinput.set_level(target.get_level());
		subinput.increase_level();
		int target_index = def_box.indexOf(target);
		int[] position = {target.getX()+30*(subinput.get_level()), target.getY()+35*(target_index+1) +10};
		def_box_position.add(target_index+1, position);
		for(int i = target_index+2 ; i<def_box_position.size();i++){
			def_box_position.get(i)[1]+=35;
		}
		def_box.add(target_index+1, subinput);
		

        
	}
	
	private static void createAndshowGUI(){
		mainUI ex = new mainUI();
		
		tabbedpane pane = new tabbedpane();
		

		Container contentpane = ex.getContentPane();
		contentpane.add(pane, BorderLayout.NORTH);
		ex.setVisible(true);
	}
	
	public static void update_panel(JComponent comp){
		int i = 0;
		while(i<def_box_position.size()){
			custominputbox box = def_box.get(i);
			int x = def_box_position.get(i)[0];
			int y = def_box_position.get(i)[1];
			box.addtoPanel(comp, x, y);
			i++;
		}
	}
	
	
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){			
				
				//// ±ÔÄ¢
//				display_rule(display_def());
				
				////////////////////////// TODO: End
				
				
				

				
//				JTextField mainbox = addbox(def,0,0);
//				addsubbox(def, mainbox);

//				Container contentpane = ex.getContentPane();
//				contentpane.add(pane, BorderLayout.NORTH);
//				ex.setVisible(true);
				createAndshowGUI();
				
			}
		});
	}
}
