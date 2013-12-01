package PEBBLET;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
//import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import manager.Definition;
import manager.DefinitionManager;
import manager.Node;
import manager.NodeType;
import manager.Rule;
import manager.RuleCase;
import manager.RuleManager;
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
		setSize(1000,500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
		
		
	}
	
	public static void addbox(JComponent comp, int x, int y){
//		JPanel inputbox = new JPanel();
//		JTextField detail = new JTextField();
//		JComboBox<String> name = new JComboBox<String>();
//		inputbox.setLayout(null);
//		name.setBounds(5,5,100,20);
//		inputbox.add(name);
//		detail.setBounds(110,5,80,20);
//		inputbox.add(detail);
//		name.addItem("text1");
//		name.addItem("test2");
//		
//		comp.setLayout(null);
//		Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
//        Border loweredbevel = BorderFactory.createLoweredBevelBorder();
//        inputbox.setBorder(BorderFactory.createCompoundBorder(raisedetched, loweredbevel));
//        inputbox.setBounds(x, y, 200, 30);
//		comp.add(inputbox);
//		return inputbox;
		
		custominputbox input = new custominputbox(comp);
		////adding root index***********************************/
//		input.set_index();
		
		
//		input.addtoPanel(comp, x, y);		
		int[] position = {x,y};
		def_box_position.add(position);
		def_box.add(input);
		
//		return input;
	}

	
	public static void addsubbox(JComponent comp, custominputbox target){
//		JTextField subbox = new JTextField();
//		comp.setLayout(null);
//		Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
//        Border loweredbevel = BorderFactory.createLoweredBevelBorder();
//        subbox.setBorder(BorderFactory.createCompoundBorder(raisedetched, loweredbevel));
//        subbox.setBounds(main.getBounds().x+10, main.getBounds().y+25, 40, 20);
//		comp.add(subbox);
		
//		main.increase_level();
//		int level = main.get_level();
		
		custominputbox subinput = new custominputbox(comp);
//		subinput.addtoPanel(comp, main.getX() + 30, main.getY() + 35*level +10);
//		subinput.set_parent(main);
//		main.setlastchild(subinput);
		
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
		
//		return subinput;
        
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
				mainUI ex = new mainUI();
				
				JTabbedPane pane = new JTabbedPane();
				
				tabbedpane tpane = new tabbedpane();
				
				JComponent def = tpane.makeTextPanel("def");
				JComponent rule = tpane.makeTextPanel("");
				JComponent comp = tpane.makeTextPanel("");
				JComponent debug = tpane.makeTextPanel("");				
				
				
				
				////////////////////////// TODO: DefinitionManager
				//// 정의
				Node def_root = new Node(null, null);
				def_root.setData("Root");
				
				Node player_number = new Node(NodeType.nd_num,def_root);
				player_number.setData("N_player");
				Node player_number_value = new Node(null,player_number);
				player_number_value.setData(3);
				
				Node global_variables = new Node(NodeType.nd_def_global, def_root);
				global_variables.setData("Global");
				Node global_center=new Node(NodeType.nd_deck, global_variables);
				global_center.setData("center");
				Node global_discard=new Node(NodeType.nd_deck, global_variables);
				global_discard.setData("discard");
				
				Node player_variables = new Node(NodeType.nd_def_player, def_root);
				player_variables.setData("Player");
				Node player_hand=new Node(NodeType.nd_deck, player_variables);
				player_hand.setData("hand");
				
				Node card_variables = new Node(NodeType.nd_def_card, def_root);
				card_variables.setData("Card");
				Node card_trump = new Node(NodeType.nd_card,card_variables);
				card_trump.setData("Trump");
				Node card_trump_shape = new Node(NodeType.nd_str,card_trump);
				card_trump_shape.setData("shape");
				Node card_trump_shape_spade = new Node(null,card_trump_shape);
				card_trump_shape_spade.setData("spade");
				Node card_trump_shape_diamond = new Node(null,card_trump_shape);
				card_trump_shape_diamond.setData("diamond");
				Node card_trump_shape_heart = new Node(null,card_trump_shape);
				card_trump_shape_heart.setData("heart");
				Node card_trump_shape_clover = new Node(null,card_trump_shape);
				card_trump_shape_clover.setData("clover");
				Node card_trump_num=new Node(NodeType.nd_num,card_trump);
				card_trump_num.setData("num");
				
				
				//// 규칙
				Node rul_root = new Node(null, null);
				rul_root.setData("Root");
				Node act_multiple = new Node(NodeType.nd_action,rul_root);
				act_multiple.setData(RuleCase.action_multiple);
				
				// 카드 불러오기
				Node act_1_load = new Node(NodeType.nd_action, act_multiple);
				act_1_load.setData(RuleCase.action_load);
				Node file_1_1 = new Node(NodeType.nd_raw,act_1_load);
				file_1_1.setData("file");
				Node deck_1_2 = new Node(NodeType.nd_deck, act_1_load);
				deck_1_2.setData("center");
				
				// 카드 섞기
				Node act_2_shuffle = new Node(NodeType.nd_action, act_multiple);
				act_2_shuffle.setData(RuleCase.action_shuffle);
				Node deck_2_1 = new Node(NodeType.nd_deck, act_2_shuffle);
				deck_2_1.setData("center");
				
				// 한 장씩 가져오기
				Node act_3_perplayer = new Node(NodeType.nd_action, act_multiple);
				act_3_perplayer.setData(RuleCase.action_act);
				// 전체 플레이어
				Node player_3_1=new Node(NodeType.nd_player, act_3_perplayer);
				player_3_1.setData(RuleCase.player_all);
				Node act_3_2=new Node(NodeType.nd_action, act_3_perplayer);
				act_3_2.set_scope_player(true);
				act_3_2.setData(RuleCase.action_move);
				// top카드
				Node card_3_2_1=new Node(NodeType.nd_card, act_3_2);
				card_3_2_1.setData(RuleCase.card_top);
				// 숫자 2
				Node num_3_2_1_1=new Node(NodeType.nd_num, card_3_2_1);
				num_3_2_1_1.setData(RuleCase.num_raw);
				Node num_3_2_1_1_1=new Node(NodeType.nd_raw, num_3_2_1_1);
				num_3_2_1_1_1.setData(2);
				// center 덱
				Node deck_3_2_1_2=new Node(NodeType.nd_deck, card_3_2_1);
				deck_3_2_1_2.setData("center");
				// hand 덱
				Node deck_3_2_2=new Node(NodeType.nd_deck, act_3_2);
				deck_3_2_2.setData("hand");
				
				
				Definition sample_def=new Definition();
				sample_def.setRoot(def_root);
				DefinitionManager dm = new DefinitionManager();
				dm.setDefinition(sample_def);
				Rule sample_rul=new Rule();
				sample_rul.setRoot(rul_root);
				RuleManager rm = new RuleManager();
				rm.setRule(sample_rul);
				rm.updateVariableList(dm);

				////////////////////////// TODO: End
				
				DefinitionDisplayer test_def = new DefinitionDisplayer(dm);
				RuleDisplayer test_rul = new RuleDisplayer(rm);
				
				
				
				
				//addbox(def,10,10);

				//update_panel(def);
				
//				JTextField mainbox = addbox(def,0,0);
//				addsubbox(def, mainbox);
				
				pane.addTab("Definition", null, test_def, "make definition");
				pane.addTab("Rule", null, test_rul, "make rule");
				pane.addTab("Component", null, comp, "make component");
				pane.addTab("Debugging", null, debug, "start debugging");
				
				
				Container contentpane = ex.getContentPane();
				contentpane.add(pane, BorderLayout.NORTH);
				ex.setVisible(true);
				
			}
		});
	}
}