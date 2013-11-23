
/* 
 * NodeType : Node의 data가 가리키는 type. 
 */
public enum NodeType {
	nd_action, nd_player, nd_deck, nd_card, nd_cond, nd_num, nd_str,
	// definition의 global, player, card의 node_type
	nd_def_global, nd_def_player, nd_def_card,
	// 특별 사용
	nd_special_delete
}
