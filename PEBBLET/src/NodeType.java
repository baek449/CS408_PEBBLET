
public enum NodeType {
	nd_action, nd_player, nd_deck, nd_card, nd_cond, nd_num, nd_str
}

/*
 * nd_action
 * 0: Multiple Actions
 * 1: Move [cards] to [deck]
 * 2: Load [file] to [deck]
 * 3: Shuffle [deck]
 * 4: Order [deck] with [order]*
 * 5: Act [player] do [action]
 */

/*
 * nd_deck
 * 0: Predefined
 * 1: Player's
 */