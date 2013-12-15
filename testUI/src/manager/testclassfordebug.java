package manager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import PEBBLET.VerticalLayout;
import PEBBLET.mainUI;
import PEBBLET.panel_inside;
import PEBBLET.tabbedpane;
import PEBBLET.rule_item_panel.rule_action_panel;



public class testclassfordebug extends JFrame{
		//just for test
	/*
	public static void main(String[] args)
	{
		//// 정의
		Node def_root = new Node(null, null);
		def_root.setData("Root");
		
		Node player_number = new Node(NodeType.nd_num,def_root);
		player_number.setData("N_player");
		Node player_number_value = new Node(null,player_number);
		player_number_value.setData(3);
		
		Node global_variables = new Node(null, def_root);
		global_variables.setData("Global");
		Node global_center=new Node(NodeType.nd_deck, global_variables);
		global_center.setData("center");
		Node global_discard=new Node(NodeType.nd_deck, global_variables);
		global_discard.setData("discard");
		
		Node player_variables = new Node(null, def_root);
		player_variables.setData("Player");
		Node player_hand=new Node(NodeType.nd_deck, player_variables);
		player_hand.setData("hand");
		
		Node card_variables = new Node(null, def_root);
		card_variables.setData("Card");
		Node card_trump = new Node(NodeType.nd_card,card_variables);
		card_trump.setData(null);
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
		
		Definition sample_def=new Definition();
		sample_def.setRoot(def_root);
		
		//// 규칙
		Node rul_root = new Node(null, null);
		rul_root.setData("Root");
		Node act_multiple = new Node(NodeType.nd_action,rul_root);
		act_multiple.setData(RuleCase.action_multiple);
		
		// 카드 불러오기
		Node act_1_load = new Node(NodeType.nd_action, act_multiple);
		act_1_load.setData(RuleCase.action_load);
		Node file_1_1 = new Node(NodeType.nd_str, act_1_load);
		file_1_1.setData(RuleCase.string_raw);
		Node file_1_1_1 = new Node(null,file_1_1);
		file_1_1_1.setData("file");
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
		act_3_2.setData(RuleCase.action_move);
		// top카드
		Node card_3_2_1=new Node(NodeType.nd_card, act_3_2);
		card_3_2_1.setData(RuleCase.card_top);
		// 숫자 2
		Node num_3_2_1_1=new Node(NodeType.nd_num, card_3_2_1);
		num_3_2_1_1.setData(2);
		// center 덱
		Node deck_3_2_1_2=new Node(NodeType.nd_deck, card_3_2_1);
		deck_3_2_1_2.setData("center");
		// hand 덱
		Node deck_3_2_2=new Node(NodeType.nd_deck, act_3_2);
		deck_3_2_2.setData("hand");
		
		
		Definition d = new Definition();
		d.setRoot(def_root);
		def_root.printAll();
		/*
		////////////////////// 테스트플레이 ///////////////////
		TestplayModule tpm=new TestplayModule(d);
		// 시작
		tpm.action(rul_root.getChildNode(0));
		*/
		
	/*
		try {
		DefinitionManager dm=new DefinitionManager();
		dm.setDefinition(d);
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("test.dat"));
		dm.save(out);
		
		DefinitionManager dm2=new DefinitionManager();
		ObjectInputStream in;
		in = new ObjectInputStream(new FileInputStream("test.dat"));
		dm2.load(in);
		System.out.print("Done");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("ERR");
		}
		
		// 결과 보기
		System.out.print("Done");
		
	}*/
	

	
	public JPanel total;
	public testclassfordebug(){		
//			
		setTitle("PEBBLET");
		setSize(1000,800);
		setPreferredSize(new Dimension(1000, 1000));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
		
		
		//// 정의
		Node def_root = new Node(null, null);
		def_root.setData("Root");
		
		Node player_number = new Node(NodeType.nd_num,def_root);
		player_number.setData("N_player");
		Node player_number_value = new Node(null,player_number);
		player_number_value.setData(3);
		
		Node global_variables = new Node(null, def_root);
		global_variables.setData("Global");
		Node global_center=new Node(NodeType.nd_deck, global_variables);
		global_center.setData("center");
		Node global_discard=new Node(NodeType.nd_deck, global_variables);
		global_discard.setData("discard");
		
		Node player_variables = new Node(null, def_root);
		player_variables.setData("Player");
		Node player_hand=new Node(NodeType.nd_deck, player_variables);
		player_hand.setData("hand");
		
		Node card_variables = new Node(null, def_root);
		card_variables.setData("Card");
		Node card_trump = new Node(NodeType.nd_card,card_variables);
		card_trump.setData(null);
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
		
		Definition sample_def=new Definition();
		sample_def.setRoot(def_root);
		
		//// 규칙
		Node rul_root = new Node(null, null);
		rul_root.setData("Root");
		Node act_multiple = new Node(NodeType.nd_action,rul_root);
		act_multiple.setData(RuleCase.action_multiple);
		
		// 카드 불러오기
		Node act_1_load = new Node(NodeType.nd_action, act_multiple);
		act_1_load.setData(RuleCase.action_load);
		Node file_1_1 = new Node(NodeType.nd_raw, act_1_load);
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
		act_3_2.setData(RuleCase.action_move);
		// top카드
		Node card_3_2_1=new Node(NodeType.nd_card, act_3_2);
		card_3_2_1.setData(RuleCase.card_top);
		// 숫자 2
		Node num_3_2_1_1=new Node(NodeType.nd_raw, card_3_2_1);
		num_3_2_1_1.setData("2");
		// center 덱
		Node deck_3_2_1_2=new Node(NodeType.nd_deck, card_3_2_1);
		deck_3_2_1_2.setData("center");
		// hand 덱
		Node deck_3_2_2=new Node(NodeType.nd_deck, act_3_2);
		deck_3_2_2.setData("hand");
		
		Rule r=new Rule();
		r.setRoot(rul_root);
		RuleManager rm=new RuleManager();
		rm.setRule(r);
		
		DefinitionManager dm=new DefinitionManager();
		dm.setDefinition(sample_def);
		
		rm.updateVariableList(dm);
		
		
		panel_inside p=new panel_inside(rm,true,false,true);
		p.setNode(r.getRoot().getChildNode(0));
		p.remove_combo();
		
		total=new JPanel();
		FlowLayout total_fl=new FlowLayout(FlowLayout.LEFT);
		total_fl.setAlignment(FlowLayout.LEADING);
		total.setLayout(total_fl);
		total.add(p);
		
		getContentPane().add(total);

		
	}
	
	
	
	
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){			
				
				testclassfordebug ex = new testclassfordebug();
				
				Container contentpane = ex.getContentPane();
				ex.setVisible(true);
				
			}
		});
	}

}
