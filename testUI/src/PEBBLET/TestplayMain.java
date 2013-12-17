package PEBBLET;

import javax.swing.SwingUtilities;

import manager.Component;
import manager.Definition;
import manager.DefinitionManager;
import manager.Node;
import manager.NodeType;
import manager.Rule;
import manager.RuleCase;
import manager.RuleManager;
import manager.TestplayModule;


public class TestplayMain {
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				//////////////////////////TODO: DefinitionManager
				//// 정의
				Node def_root = new Node(null, null);
				def_root.setData("Root");
				
				Node player_number = new Node(NodeType.nd_num,def_root);
				player_number.setData("N_player");
				Node player_number_value = new Node(NodeType.nd_raw,player_number);
				player_number_value.setData(3);
				
				Node global_variables = new Node(NodeType.nd_def_global, def_root);
				global_variables.setData("Global");
				Node global_center=new Node(NodeType.nd_deck, global_variables);
				global_center.setData("center");
				Node global_discard=new Node(NodeType.nd_deck, global_variables);
				global_discard.setData("discard");
				Node global_num=new Node(NodeType.nd_num, global_variables);
				global_num.setData("test_num");
				
				
				Node player_variables = new Node(NodeType.nd_def_player, def_root);
				player_variables.setData("Player");
				Node player_hand=new Node(NodeType.nd_deck, player_variables);
				player_hand.setData("hand#");
				Node player_str=new Node(NodeType.nd_str, player_variables);
				player_str.setData("test_str");
				Node player_num=new Node(NodeType.nd_num, player_variables);
				player_num.setData("test_num2");
				
				Node card_variables = new Node(NodeType.nd_def_card, def_root);
				card_variables.setData("Card");
				Node card_trump = new Node(NodeType.nd_card,card_variables);
				card_trump.setData("Trump");
				Node card_trump_shape = new Node(NodeType.nd_str,card_trump);
				card_trump_shape.setData("shape");
				Node card_trump_shape_spade = new Node(NodeType.nd_raw,card_trump_shape);
				card_trump_shape_spade.setData("spade");
				Node card_trump_shape_diamond = new Node(NodeType.nd_raw,card_trump_shape);
				card_trump_shape_diamond.setData("diamond");
				Node card_trump_shape_heart = new Node(NodeType.nd_raw,card_trump_shape);
				card_trump_shape_heart.setData("heart");
				Node card_trump_shape_clover = new Node(NodeType.nd_raw,card_trump_shape);
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
				
				// 두 장씩 가져오기
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
				deck_3_2_2.setData("hand#");
				
				// 한 장씩 버리기
				Node act_4_perplayer = new Node(NodeType.nd_action, act_multiple);
				act_4_perplayer.setData(RuleCase.action_act);
				// 전체 플레이어
				Node player_4_1=new Node(NodeType.nd_player, act_4_perplayer);
				player_4_1.setData(RuleCase.player_all);
				Node act_4_2=new Node(NodeType.nd_action, act_4_perplayer);
				act_4_2.set_scope_player(true);
				act_4_2.setData(RuleCase.action_move);
				// top카드
				Node card_4_2_1=new Node(NodeType.nd_card, act_4_2);
				card_4_2_1.setData(RuleCase.card_select);
				// 숫자 2
				Node num_4_2_1_1=new Node(NodeType.nd_num, card_4_2_1);
				num_4_2_1_1.setData(RuleCase.num_raw);
				Node num_4_2_1_1_1=new Node(NodeType.nd_raw, num_4_2_1_1);
				num_4_2_1_1_1.setData(1);
				// hand 덱의 모든 카드
				Node card_4_2_1_2=new Node(NodeType.nd_card,card_4_2_1);
				card_4_2_1_2.setData(RuleCase.card_all);
				Node deck_4_2_1_2_1=new Node(NodeType.nd_deck, card_4_2_1_2);
				deck_4_2_1_2_1.setData("hand#");
				// discard 덱
				Node deck_4_2_2=new Node(NodeType.nd_deck, act_4_2);
				deck_4_2_2.setData("discard");
				
				// 모두 승리
				Node act_5_end = new Node(NodeType.nd_action, act_multiple);
				act_5_end.setData(RuleCase.action_endgame);
				// 전체 플레이어
				Node player_5_1=new Node(NodeType.nd_player, act_5_end);
				player_5_1.setData(RuleCase.player_all);
				
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
				
				
				
				
				
				//////////////////
				TestplayModule tpm=new TestplayModule(sample_def,new Component());
				
				TestplayUI ex = new TestplayUI(tpm);
				ex.run_testplay(sample_rul);
				//tpm.action(rul_root.getChildNode(0));
			}
		});
    }
}
