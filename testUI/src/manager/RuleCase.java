package manager;

/*
 * RuleCase: Rule�� ǥ���ϴ� tree���� ���� node�� � fillup �������� �ش��ϴ����� ��Ÿ��.
 */
public enum RuleCase {
	action_multiple, action_move, action_load, action_shuffle, action_order, action_act, action_choose, action_card,
	action_if, action_ifelse, action_repeat, action_endgame, action_endgame_draw, action_endgame_order, action_show,
	player_multiple, player_current, player_all, player_exclude, player_left, player_right, player_left_all, player_right_all, player_satisfy, player_most, player_select, player_player,
	deck_player, deck_select, card_all, card_top, card_bottom, card_satisfy, card_select,
	cond_numcompare, cond_samecard, cond_sameplayer, cond_samestring, cond_typeequal, cond_istype, cond_and, cond_or, cond_not, cond_emptydeck,
	num_size_player, num_size_deck, num_size_card, num_operation, num_call, num_player, num_card, string_raw, string_call, string_player, string_card,
	order_high, order_low, namedAction_namedAction
}

// action_card: Ư�� ī���� �׼� ������ �����Ѵ�.