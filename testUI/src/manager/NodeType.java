package manager;

/* 
 * NodeType : Node�� data�� ����Ű�� type. 
 */
public enum NodeType {
	nd_action, nd_player, nd_deck, nd_card, nd_cond, nd_num, nd_str, nd_order, nd_namedAction,
	// definition�� global, player, card�� node_type
	nd_def_global, nd_def_player, nd_def_card,
	// raw��
	nd_raw,
	// Ư�� ���
	nd_special_delete
}
