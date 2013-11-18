
public class testclassfordebug {
		//just for test
	/*
	public static String node_string(Node n)
	{
		switch(n.t)
		{
		case nd_action:
			break;
		default:
			break;
		}
	}*/
	public static void main(String[] args)
	{
		//// 정의
		Node def_root = new Node(null, null);
		Node global_variables = new Node(null, def_root);
		Node player_variables = new Node(null, def_root);
		player_variables.setData(5);
		Node global_center=new Node(NodeType.nd_deck, global_variables);
		global_center.setData("Center");
		Node global_discard=new Node(NodeType.nd_deck, global_variables);
		global_discard.setData("Discard");
		Node player_hand=new Node(NodeType.nd_deck, player_variables);
		player_hand.setData("Hand");
		Definition sample_def=new Definition();
		sample_def.setRoot(def_root);
		
		//// 규칙
		Node rul_root = new Node(NodeType.nd_action, 0, null);
		// 카드 불러오기
		Node act_1_load = new Node(NodeType.nd_action, 2, rul_root);
		Node file_1_1 = new Node(NodeType.nd_str, 0, act_1_load);
		file_1_1.setData("file");
		Node deck_1_2 = new Node(NodeType.nd_deck, 0, act_1_load);
		deck_1_2.setData("Center");
		// 카드 섞기
		Node act_2_shuffle = new Node(NodeType.nd_action, 3, rul_root);
		Node deck_2_1 = new Node(NodeType.nd_deck, 0, act_2_shuffle);
		deck_2_1.setData("Center");
		
		// 한 장씩 가져오기
		Node act_3_perplayer = new Node(NodeType.nd_action, 5, rul_root);
		// 전체 플레이어
		Node player_3_1=new Node(NodeType.nd_player, 1, act_3_perplayer);
		Node act_3_2=new Node(NodeType.nd_action, 1, act_3_perplayer);
		// top카드
		Node card_3_2_1=new Node(NodeType.nd_card, 1, act_3_2);
		// 숫자 1
		Node num_3_2_1_1=new Node(NodeType.nd_num, 0, card_3_2_1);
		num_3_2_1_1.setData(1);
		// center 덱
		Node deck_3_2_1_2=new Node(NodeType.nd_deck, 0, card_3_2_1);
		deck_3_2_1_2.setData("Center");
		// hand 덱
		Node deck_3_2_2=new Node(NodeType.nd_deck, 0, act_3_2);
		deck_3_2_2.setData("Hand");
		
		
		////////////////////// 테스트플레이 ///////////////////
		Definition d = new Definition();
		d.setRoot(def_root);
		TestplayModule tpm=new TestplayModule(d);
		System.out.println("2");
		// 시작
		tpm.action(rul_root);
		
		// 결과 보기
	}
}
