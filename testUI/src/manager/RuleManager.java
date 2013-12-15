package manager;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import PEBBLET.AreaRange;


public class RuleManager {
	private static final String[] selection_action =
		{"(Cancel)", "Multiple Actions", "Move [card] to [deck]", "Load [file] to [deck]", "Shuffle [deck]", "Order [deck] [order]*",
		"Act [player] [action]", "If [cond] [action]", "If [cond] [action] else [action]", "Repeat [num] [action]", "Endgame Win [player]",
		"Endgame Draw", "Endgame Order [order]*", "Show [card] to [player]", "[card].[action]","Set [variable]=[num]","Set [variable]=[string]","Set [variable]=[deck]","Set [variable]=[player]"};
	private static final String[] selection_action_pscope = {"Choose from [namedAction]*"};
	private static final String[] selection_player =
		{"(Cancel)", "Multiple Players", "All Players", "Exclude [player] from [player]", "Left [num]th player from [player]",
		"Right [num]th player from [player]", "Left [num] players from [player]", "Right [num] players from [player]",
		"Satisfying [cond] from [player]", "Most [order]* among [player]","[player].[player]"};
	private static final String[] selection_player_pscope = {"Select [num] Players in [player]"};
	private static final String[] selection_deck = {"(Cancel)","[player].[deck]"};
	private static final String[] selection_deck_pscope = {"Select Deck in [deck]*"};
	private static final String[] selection_card =
		{"(Cancel)", "All cards in [deck]", "Top [num] cards in [deck]", "Bottom [num] cards in [deck]",
		"Satisfying [cond] from [card]"};
	private static final String[] selection_card_pscope = {"Select [num] Cards in [card]"};
	private static final String[] selection_cond =
		{"(Cancel)", "[num] [operation] [num]", "Identical? [card] [card]", "Identical [player] [player]",
		"[string] == [string]", "Typeequal? [card] [card]", "Type? [string] [card]",
		"[cond] and [cond]", "[cond] or [cond]", "Not [cond]", "Empty? [deck]"};
	private static final String[] selection_order = {"(Cancel)", "High [num]", "Low [num]"};
	private static final String[] selection_num =
		{"(Cancel)", "Insert Integer...", "Size [player]", "Size [deck]", "Size [card]",
		"[num] [operation] [num]","[player].[num]","[card].[num]"};
	private static final String[] selection_num_pscope = {"Call from [num] to [num]"};
	private static final String[] selection_str = 		{"(Cancel)", "Insert String...", "[player].[string]","[card].[string]"};
	private static final String[] selection_str_pscope = {"Call from [string]*"};
	private static final String[] selection_namedAction = {"(Cancel)","[string]:[action]"};
	
	
	
	private Rule rule;
	private String[][][] varList;
//	private static RuleDisplayer m;
	
	public RuleManager(){
		rule = new Rule();
	}
	
	public Node search(int[] location)
	{
		Node cur=rule.getRoot();
		int loop;
		for(loop=0;loop<location.length;loop++)
		{
			cur=cur.getChildNode(location[loop]);
		}
		return cur;
	}
	public void modify(int[] location,Node n)
	{
		Node cur=search(location).getParent();
		cur.getAllNode().set(location[location.length-1], n);
	}
	public void updateVariableList(DefinitionManager dm)
	{
		varList=dm.getDefinition().getVariableList();
	}
	
	public static NodeTypewithScope type_multiple_allowed(RuleCase rc)
	{
		switch(rc)
		{
		case action_multiple:
			return new NodeTypewithScope(NodeType.nd_action,false,false);
		case action_order:
			return new NodeTypewithScope(NodeType.nd_order,false,true);
		case action_endgame_order:
		case player_most:
			return new NodeTypewithScope(NodeType.nd_order,true,false);
		case action_choose:
			return new NodeTypewithScope(NodeType.nd_namedAction,false,false);
		case player_multiple:
			return new NodeTypewithScope(NodeType.nd_player,false,false);
		case deck_select:
			return new NodeTypewithScope(NodeType.nd_deck,false,false);
		case string_call:
			return new NodeTypewithScope(NodeType.nd_str,false,false);
		default:
			return null;
		}
	}
	
	private String[] preActionSelection(boolean ps, boolean cs, boolean delete)
	{
		//// Start: Ask UI for selection
		
		// 1. Making Selection Options.
		ArrayList<String> options = new ArrayList<String>();
		int loop;
		for(loop=0;loop<selection_action.length;loop++)
			options.add(selection_action[loop]);
		if (ps)
			for(loop=0;loop<selection_action_pscope.length;loop++)
				options.add(selection_action_pscope[loop]);
		if (cs)
			for(loop=0;loop<varList[2][4].length;loop++)
				options.add(varList[2][4][loop]);
		if (delete) options.add("Delete");
		// 2. Getting an input
		return options.toArray(new String[options.size()]);
	}
	
	private Node postActionSelection(boolean ps, boolean cs, boolean delete, String[] options, int input)
	{
		// 3. Making a node
		Node newnode;
		Node o=new Node(NodeType.nd_action,null);
		o.set_scope_player(ps);
		o.set_scope_card(cs);
		switch(input)
		{
		case -1:
		case 0:
			return null;
		case 1: // "Multiple Actions"
			o.setData(RuleCase.action_multiple);
			return o;
		case 2: // "Move [card] to [deck]"
			o.setData(RuleCase.action_move);
			newnode=new Node(NodeType.nd_card,o);
			newnode=new Node(NodeType.nd_deck,o);
			return o;
		case 3: // Load [file] to [deck]"
			o.setData(RuleCase.action_load);
			newnode=new Node(NodeType.nd_raw,o);
			newnode=new Node(NodeType.nd_deck,o);
			return o;
		case 4:	// "Shuffle [deck]"
			o.setData(RuleCase.action_shuffle);
			newnode=new Node(NodeType.nd_deck,o);
			return o;
		case 5: // "Order [deck] [order]*"
			o.setData(RuleCase.action_order);
			newnode=new Node(NodeType.nd_deck,o);
			return o;
		case 6: //	"Act [player] [action]"
			o.setData(RuleCase.action_act);
			newnode=new Node(NodeType.nd_player,o);
			newnode=new Node(NodeType.nd_action,o);
			newnode.set_scope_player(true);
			return o;
		case 7: //	"If [cond] [action]"
			o.setData(RuleCase.action_if);
			newnode=new Node(NodeType.nd_cond,o);
			newnode=new Node(NodeType.nd_action,o);
			return o;
		case 8: //	"If [cond] [action] else [action]"
			o.setData(RuleCase.action_ifelse);
			newnode=new Node(NodeType.nd_cond,o);
			newnode=new Node(NodeType.nd_action,o);
			newnode=new Node(NodeType.nd_action,o);
			return o;
		case 9: //	"Repeat [num] [action]"
			o.setData(RuleCase.action_repeat);
			newnode=new Node(NodeType.nd_num,o);
			newnode=new Node(NodeType.nd_action,o);
			return o;
		case 10: //	"Endgame Win [player]"
			o.setData(RuleCase.action_endgame);
			newnode=new Node(NodeType.nd_player,o);
			return o;
		case 11: // "Endgame Draw"
			o.setData(RuleCase.action_endgame_draw);
			return o;
		case 12: //	"Endgame Order [order]*"
			o.setData(RuleCase.action_endgame_order);
			return o;
		case 13: //	"Show [card] to [player]"
			o.setData(RuleCase.action_show);
			newnode=new Node(NodeType.nd_card,o);
			newnode=new Node(NodeType.nd_player,o);
			return o;
		case 14: // "[card].[action]"
			o.setData(RuleCase.action_card);
			newnode=new Node(NodeType.nd_card,o);
			newnode=new Node(NodeType.nd_action,o);
			newnode.set_scope_card(true);
			return o;
		case 15: //"Set [variable]=[num]"
			o.setData(RuleCase.action_setint);
			newnode=new Node(NodeType.nd_raw,o);
			newnode=new Node(NodeType.nd_num,o);
			return o;
		case 16: //"Set [variable]=[str]"
			o.setData(RuleCase.action_setstr);
			newnode=new Node(NodeType.nd_raw,o);
			newnode=new Node(NodeType.nd_str,o);
			return o;
		case 17: //"Set [variable]=[deck]"
			o.setData(RuleCase.action_setdeck);
			newnode=new Node(NodeType.nd_raw,o);
			newnode=new Node(NodeType.nd_deck,o);
			return o;
		case 18: //"Set [variable]=[player]"
			o.setData(RuleCase.action_setplayer);
			newnode=new Node(NodeType.nd_raw,o);
			newnode=new Node(NodeType.nd_player,o);
			return o;
		case 19: // "Choose from [namedAction]*"
			if(ps)
			{
				o.setData(RuleCase.action_choose);
				return o;
			}
			break;
		}

		if(delete && input==(options.length-1))
		{
			o.set_node_type(NodeType.nd_special_delete);
			return o;
		}
		o.setData(options[input]);
		return o;
	}
	
	private String[] prePlayerSelection(boolean ps, boolean cs, boolean delete)
	{
		//// Start: Ask UI for selection
		
		// 1. Making Selection Options.
		ArrayList<String> options = new ArrayList<String>();
		int loop;
		for(loop=0;loop<selection_player.length;loop++)
			options.add(selection_player[loop]);
		if (ps)
			for(loop=0;loop<selection_player_pscope.length;loop++)
				options.add(selection_player_pscope[loop]);
		for(loop=0;loop<varList[0][3].length;loop++)
			options.add(varList[0][3][loop]);
		if (ps)
			for(loop=0;loop<varList[1][3].length;loop++)
				options.add(varList[1][3][loop]);
		if (cs)
			for(loop=0;loop<varList[2][3].length;loop++)
				options.add(varList[2][3][loop]);
		if(delete) options.add("Delete");
		// 2. Getting an input
		return options.toArray(new String[options.size()]);
		
	}
	private Node postPlayerSelection(boolean ps, boolean cs, boolean delete, String[] options, int input)
	{
		// 3. Making a node
		Node newnode;
		Node o=new Node(NodeType.nd_player,null);
		o.set_scope_player(ps);
		o.set_scope_card(cs);
		switch(input)
		{
		case -1:
		case 0:
			return null;
		case 1: // "Multiple Players"
			o.setData(RuleCase.player_multiple);
			return o;
		case 2:	// "All Players"
			o.setData(RuleCase.player_all);
			return o;
		case 3: // "Exclude [player] from [player]"
			o.setData(RuleCase.player_exclude);
			newnode=new Node(NodeType.nd_player,o);
			newnode=new Node(NodeType.nd_player,o);
			return o;
		case 4: //"Left [num]th player from [player]"
			o.setData(RuleCase.player_left);
			newnode=new Node(NodeType.nd_num,o);
			newnode=new Node(NodeType.nd_player,o);
			return o;
		case 5: //	"Right [num]th player from [player]"
			o.setData(RuleCase.player_right);
			newnode=new Node(NodeType.nd_num,o);
			newnode=new Node(NodeType.nd_player,o);
			return o;
		case 6: // "Left [num] players from [player]"
			o.setData(RuleCase.player_left_all);
			newnode=new Node(NodeType.nd_num,o);
			newnode=new Node(NodeType.nd_player,o);
			return o;
		case 7: //"Right [num] players from [player]"
			o.setData(RuleCase.player_right_all);
			newnode=new Node(NodeType.nd_num,o);
			newnode=new Node(NodeType.nd_player,o);
			return o;
		case 8: //"Satisfying [cond] from [player]"
			o.setData(RuleCase.player_satisfy);
			newnode=new Node(NodeType.nd_cond,o);
			newnode.set_scope_player(true);
			newnode=new Node(NodeType.nd_player,o);
			return o;
		case 9: //"Most [order]* among [player]"
			o.setData(RuleCase.player_most);
			newnode=new Node(NodeType.nd_player,o);
			return o;
		case 10: //"[player].[player]"
			o.setData(RuleCase.player_player);
			newnode=new Node(NodeType.nd_player,o);
			newnode=new Node(NodeType.nd_player,o);
			newnode.set_scope_player(true);
			return o;
		case 11: //"Select [num] Players in [player]"
			if(ps)
			{
				o.setData(RuleCase.player_select);
				newnode=new Node(NodeType.nd_num,o);
				newnode=new Node(NodeType.nd_player,o);
				return o;
			}
			break;
		}
		if(delete && input==(options.length-1))
		{
			o.set_node_type(NodeType.nd_special_delete);
			return o;
		}
		o.setData(options[input]);
		return o;
	}
	
	private String[] preDeckSelection(boolean ps, boolean cs, boolean delete)
	{
		//// Start: Ask UI for selection
		
		// 1. Making Selection Options.
		ArrayList<String> options = new ArrayList<String>();
		int loop;
		for(loop=0;loop<selection_deck.length;loop++)
			options.add(selection_deck[loop]);
		if (ps)
			for(loop=0;loop<selection_deck_pscope.length;loop++)
				options.add(selection_deck_pscope[loop]);
		for(loop=0;loop<varList[0][2].length;loop++)
			options.add(varList[0][2][loop]);
		if (ps)
			for(loop=0;loop<varList[1][2].length;loop++)
				options.add(varList[1][2][loop]);
		if (cs)
			for(loop=0;loop<varList[2][2].length;loop++)
				options.add(varList[2][2][loop]);
		if(delete) options.add("Delete");
		// 2. Getting an input
		return options.toArray(new String[options.size()]);
	}
	private Node postDeckSelection(boolean ps, boolean cs, boolean delete, String[] options, int input)
	{
		// 3. Making a node
		Node newnode;
		Node o=new Node(NodeType.nd_deck,null);
		o.set_scope_player(ps);
		o.set_scope_card(cs);
		switch(input)
		{
		case -1:
		case 0:
			return null;
		case 1: //"[player].[deck]"
			o.setData(RuleCase.deck_player);
			newnode=new Node(NodeType.nd_player,o);
			newnode=new Node(NodeType.nd_deck,o);
			newnode.set_scope_player(true);
			return o;
		case 2: //"Select Deck in [deck]*"
			if(ps)
			{
				o.setData(RuleCase.deck_select);
				return o;
			}
			break;
		}
		if(delete && input==(options.length-1))
		{
			o.set_node_type(NodeType.nd_special_delete);
			return o;
		}
		o.setData(options[input]);
		return o;
	}
	
	private String[] preCardSelection(boolean ps, boolean cs, boolean delete)
	{
		//// Start: Ask UI for selection
		
		// 1. Making Selection Options.
		ArrayList<String> options = new ArrayList<String>();
		int loop;
		for(loop=0;loop<selection_card.length;loop++)
			options.add(selection_card[loop]);
		if (ps)
			for(loop=0;loop<selection_card_pscope.length;loop++)
				options.add(selection_card_pscope[loop]);
		if(delete) options.add("Delete");
		// 2. Getting an input
		return options.toArray(new String[options.size()]);
	}
	private Node postCardSelection(boolean ps, boolean cs, boolean delete, String[] options, int input)
	{
		// 3. Making a node
		Node newnode;
		Node o=new Node(NodeType.nd_card,null);
		o.set_scope_player(ps);
		o.set_scope_card(cs);
		switch(input)
		{
		case -1:
		case 0:
			return null;
		case 1: // "All cards in [deck]"
			o.setData(RuleCase.card_all);
			newnode=new Node(NodeType.nd_deck,o);
			return o;
		case 2: // "Top [num] cards in [deck]"
			o.setData(RuleCase.card_top);
			newnode=new Node(NodeType.nd_num,o);
			newnode=new Node(NodeType.nd_deck,o);
			return o;
		case 3: // "Bottom [num] cards in [deck]"
			o.setData(RuleCase.card_bottom);
			newnode=new Node(NodeType.nd_num,o);
			newnode=new Node(NodeType.nd_deck,o);
			return o;
		case 4: // "Satisfying [cond] from [card]"
			o.setData(RuleCase.card_satisfy);
			newnode=new Node(NodeType.nd_cond,o);
			newnode.set_scope_card(true);
			newnode=new Node(NodeType.nd_card,o);
			return o;

		case 5: //"Select [num] Cards in [card]"
			if(ps)
			{
				o.setData(RuleCase.card_select);
				newnode=new Node(NodeType.nd_num,o);
				newnode=new Node(NodeType.nd_card,o);
				return o;
			}
			break;
		}
		if(delete && input==(options.length-1))
		{
			o.set_node_type(NodeType.nd_special_delete);
			return o;
		}
		o.setData(options[input]);
		return o;
	}
	
	
	private String[] preCondSelection(boolean ps, boolean cs, boolean delete)
	{
		//// Start: Ask UI for selection
		
		// 1. Making Selection Options.
		ArrayList<String> options = new ArrayList<String>();
		int loop;
		for(loop=0;loop<selection_cond.length;loop++)
			options.add(selection_cond[loop]);
		if(delete) options.add("Delete");
		// 2. Getting an input
		return options.toArray(new String[options.size()]);
	}
	private Node postCondSelection(boolean ps, boolean cs, boolean delete, String[] options, int input)
	{
		// 3. Making a node
		Node newnode;
		Node o=new Node(NodeType.nd_cond,null);
		o.set_scope_player(ps);
		o.set_scope_card(cs);
		switch(input)
		{
		case -1:
		case 0:
			return null;
		case 1: // "[num] [operation] [num]"
			o.setData(RuleCase.cond_numcompare);
			newnode=new Node(NodeType.nd_num,o);
			newnode=new Node(NodeType.nd_raw,o);
			newnode=new Node(NodeType.nd_num,o);
			return o;
		case 2: // "Identical? [card] [card]"
			o.setData(RuleCase.cond_samecard);
			newnode=new Node(NodeType.nd_card,o);
			newnode=new Node(NodeType.nd_card,o);
			return o;
		case 3: // "Identical [player] [player]"
			o.setData(RuleCase.cond_sameplayer);
			newnode=new Node(NodeType.nd_player,o);
			newnode=new Node(NodeType.nd_player,o);
			return o;
		case 4: // "[string] == [string]"
			o.setData(RuleCase.cond_samestring);
			newnode=new Node(NodeType.nd_str,o);
			newnode=new Node(NodeType.nd_str,o);
			return o;
		case 5: // "Typeequal? [card] [card]"
			o.setData(RuleCase.cond_typeequal);
			newnode=new Node(NodeType.nd_card,o);
			newnode=new Node(NodeType.nd_card,o);
			return o;
		case 6: // "Type? [string] [card]"
			o.setData(RuleCase.cond_istype);
			newnode=new Node(NodeType.nd_raw,o);
			newnode=new Node(NodeType.nd_card,o);
			return o;
		case 7: // "[cond] and [cond]"
			o.setData(RuleCase.cond_and);
			newnode=new Node(NodeType.nd_cond,o);
			newnode=new Node(NodeType.nd_cond,o);
			return o;
		case 8: // "[cond] or [cond]"
			o.setData(RuleCase.cond_or);
			newnode=new Node(NodeType.nd_cond,o);
			newnode=new Node(NodeType.nd_cond,o);
			return o;
		case 9: // "Not [cond]"
			o.setData(RuleCase.cond_not);
			newnode=new Node(NodeType.nd_cond,o);
			return o;
		case 10: // "Empty? [deck]"
			o.setData(RuleCase.cond_emptydeck);
			newnode=new Node(NodeType.nd_deck,o);
			return o;
		}
		if(delete && input==(options.length-1))
		{
			o.set_node_type(NodeType.nd_special_delete);
			return o;
		}
		o.setData(options[input]);
		return o;
	}
	
	private String[] preOrderSelection(boolean ps, boolean cs, boolean delete)
	{
		//// Start: Ask UI for selection
		
		// 1. Making Selection Options.
		ArrayList<String> options = new ArrayList<String>();
		int loop;
		for(loop=0;loop<selection_order.length;loop++)
			options.add(selection_order[loop]);
		if(delete) options.add("Delete");
		// 2. Getting an input
		return options.toArray(new String[options.size()]);
	}
	private Node postOrderSelection(boolean ps, boolean cs, boolean delete, String[] options, int input)
	{
		// 3. Making a node
		Node newnode;
		Node o=new Node(NodeType.nd_order,null);
		o.set_scope_player(ps);
		o.set_scope_card(cs);
		switch(input)
		{
		case -1:
		case 0:
			return null;
		case 1: //"High [num]"
			o.setData(RuleCase.order_high);
			newnode=new Node(NodeType.nd_num,o);
			return o;
		case 2: //"Low [num]"
			o.setData(RuleCase.order_low);
			newnode=new Node(NodeType.nd_num,o);
			return o;
		}
		if(delete && input==(options.length-1))
		{
			o.set_node_type(NodeType.nd_special_delete);
			return o;
		}
		o.setData(options[input]);
		return o;
	}
	
	private String[] preNumSelection(boolean ps, boolean cs, boolean delete)
	{
		//// Start: Ask UI for selection
		
		// 1. Making Selection Options.
		ArrayList<String> options = new ArrayList<String>();
		int loop;
		for(loop=0;loop<selection_num.length;loop++)
			options.add(selection_num[loop]);
		if (ps)
			for(loop=0;loop<selection_num_pscope.length;loop++)
				options.add(selection_num_pscope[loop]);
		for(loop=0;loop<varList[0][0].length;loop++)
			options.add(varList[0][0][loop]);
		if (ps)
			for(loop=0;loop<varList[1][0].length;loop++)
				options.add(varList[1][0][loop]);
		if (cs)
			for(loop=0;loop<varList[2][0].length;loop++)
				options.add(varList[2][0][loop]);
		if(delete) options.add("Delete");
		// 2. Getting an input
		return options.toArray(new String[options.size()]);
	}
	private Node postNumSelection(boolean ps, boolean cs, boolean delete, String[] options, int input)
	{
		// 3. Making a node
		Node newnode;
		Node o=new Node(NodeType.nd_num,null);
		o.set_scope_player(ps);
		o.set_scope_card(cs);
		switch(input)
		{
		case -1:
		case 0:
			return null;
		case 1: // "Insert Integer..."
			o.setData(RuleCase.num_raw);
			newnode=new Node(NodeType.nd_raw,o);
			return o;
		case 2: // "Size [player]"
			o.setData(RuleCase.num_size_player);
			newnode=new Node(NodeType.nd_player,o);
			return o;
		case 3: // "Size [deck]"
			o.setData(RuleCase.num_size_deck);
			newnode=new Node(NodeType.nd_deck,o);
			return o;
		case 4: // "Size [card]"
			o.setData(RuleCase.num_size_card);
			newnode=new Node(NodeType.nd_card,o);
			return o;
		case 5: // "[num] [operation] [num]"
			o.setData(RuleCase.num_operation);
			newnode=new Node(NodeType.nd_num,o);
			newnode=new Node(NodeType.nd_raw,o);
			newnode=new Node(NodeType.nd_num,o);
			return o;
		case 6: // "[player].[num]"
			o.setData(RuleCase.num_player);
			newnode=new Node(NodeType.nd_player,o);
			newnode=new Node(NodeType.nd_num,o);
			newnode.set_scope_player(true);
			return o;
		case 7: // "[card].[num]"
			o.setData(RuleCase.num_card);
			newnode=new Node(NodeType.nd_card,o);
			newnode=new Node(NodeType.nd_num,o);
			newnode.set_scope_card(true);
			return o;
		case 8: // "Call from [num] to [num]"
			if (ps)
			{
				o.setData(RuleCase.num_call);
				newnode=new Node(NodeType.nd_num,o);
				newnode=new Node(NodeType.nd_num,o);
				return o;
			}
			break;
		}
		if(delete && input==(options.length-1))
		{
			o.set_node_type(NodeType.nd_special_delete);
			return o;
		}
		o.setData(options[input]);
		return o;
	}
	
	private String[] preStringSelection(boolean ps, boolean cs, boolean delete)
	{
		//// Start: Ask UI for selection
		
		// 1. Making Selection Options.
		ArrayList<String> options = new ArrayList<String>();
		int loop;
		for(loop=0;loop<selection_str.length;loop++)
			options.add(selection_str[loop]);
		if (ps)
			for(loop=0;loop<selection_str_pscope.length;loop++)
				options.add(selection_str_pscope[loop]);
		for(loop=0;loop<varList[0][1].length;loop++)
			options.add(varList[0][1][loop]);
		if (ps)
			for(loop=0;loop<varList[1][1].length;loop++)
				options.add(varList[1][1][loop]);
		if (cs)
			for(loop=0;loop<varList[2][1].length;loop++)
				options.add(varList[2][1][loop]);
		if(delete) options.add("Delete");
		// 2. Getting an input
		return options.toArray(new String[options.size()]);
	}
	private Node postStringSelection(boolean ps, boolean cs, boolean delete, String[] options, int input)
	{
		// 3. Making a node
		Node newnode;
		Node o=new Node(NodeType.nd_str,null);
		o.set_scope_player(ps);
		o.set_scope_card(cs);
		switch(input)
		{
		case -1:
		case 0:
			return null;
		case 1: // "Insert String..."
			o.setData(RuleCase.string_raw);
			newnode=new Node(NodeType.nd_raw,o);
			o.setData("input");
			return o;
		case 2: // "[player].[string]"
			o.setData(RuleCase.string_player);
			newnode=new Node(NodeType.nd_player,o);
			newnode=new Node(NodeType.nd_str,o);
			newnode.set_scope_player(true);
			return o;
		case 3: // "[card].[string]"
			o.setData(RuleCase.string_card);
			newnode=new Node(NodeType.nd_card,o);
			newnode=new Node(NodeType.nd_str,o);
			newnode.set_scope_card(true);
			return o;
		case 4: // "Call from [string]*"
			if (ps)
			{
				o.setData(RuleCase.string_call);
				return o;
			}
			break;
		}
		if(delete && input==(options.length-1))
		{
			o.set_node_type(NodeType.nd_special_delete);
			return o;
		}
		o.setData(options[input]);
		return o;
	}
	
	
	private String[] preNamedActionSelection(boolean ps, boolean cs, boolean delete)
	{
		//// Start: Ask UI for selection
		
		// 1. Making Selection Options.
		ArrayList<String> options = new ArrayList<String>();
		int loop;
		for(loop=0;loop<selection_namedAction.length;loop++)
			options.add(selection_namedAction[loop]);
		if(delete) options.add("Delete");
		// 2. Getting an input
		return options.toArray(new String[options.size()]);
	}
	private Node postNamedActionSelection(boolean ps, boolean cs, boolean delete, String[] options, int input)
	{
		// 3. Making a node
		Node newnode;
		Node o=new Node(NodeType.nd_namedAction,null);
		o.set_scope_player(ps);
		o.set_scope_card(cs);
		switch(input)
		{
		case -1:
		case 0:
			return null;
		case 1: //"[string]:[action]"
			o.setData(RuleCase.namedAction_namedAction);
			newnode=new Node(NodeType.nd_raw,o);
			newnode=new Node(NodeType.nd_action,o);
			return o;
		}
		if(delete && input==(options.length-1))
		{
			o.set_node_type(NodeType.nd_special_delete);
			return o;
		}
		o.setData(options[input]);
		return o;
	}

	// t type에 대한 선택지를 띄워주고 플레이어에게 선택을 받은 후 적절한 node를 만들어 리턴해주는 함수. 취소시 null.
	private String[] preSelections(NodeType t, boolean ps, boolean cs, boolean delete)
	{
		// TODO
		switch(t)
		{
		case nd_action:
			return preActionSelection(ps, cs, delete);
		case nd_player:
			return prePlayerSelection(ps, cs, delete);
		case nd_deck:
			return preDeckSelection(ps, cs, delete);
		case nd_card:
			return preCardSelection(ps, cs, delete);
		case nd_cond:
			return preCondSelection(ps, cs, delete);
		case nd_order:
			return preOrderSelection(ps, cs, delete);
		case nd_num:
			return preNumSelection(ps, cs, delete);
		case nd_str:
			return preStringSelection(ps, cs, delete);
		case nd_namedAction:
			return preNamedActionSelection(ps, cs, delete);
		}
		System.err.println("preSelection: Invalid node type.");
		return null;
	}

	private String[] previous_selection;
	private Node postSelections(NodeType t, boolean ps, boolean cs, boolean delete, int input)
	{
		// TODO
		switch(t)
		{
		case nd_action:
			return postActionSelection(ps, cs, delete, previous_selection, input);
		case nd_player:
			return postPlayerSelection(ps, cs, delete, previous_selection, input);
		case nd_deck:
			return postDeckSelection(ps, cs, delete, previous_selection, input);
		case nd_card:
			return postCardSelection(ps, cs, delete, previous_selection, input);
		case nd_cond:
			return postCondSelection(ps, cs, delete, previous_selection, input);
		case nd_order:
			return postOrderSelection(ps, cs, delete, previous_selection, input);
		case nd_num:
			return postNumSelection(ps, cs, delete, previous_selection, input);
		case nd_str:
			return postStringSelection(ps, cs, delete, previous_selection, input);
		case nd_namedAction:
			return postNamedActionSelection(ps, cs, delete, previous_selection, input);
		}
		System.err.println("postSelection: Invalid node type.");
		return null;
	}
	
	public static boolean isDeletable(Node n)
	{
		Node p=n.getParent();
		if(p==null || p.getData()==null || p.getData().getClass()!=RuleCase.class) return false;
		NodeTypewithScope nts=type_multiple_allowed((RuleCase)p.getData());
		if(nts==null) return false;
		return nts.nt==n.get_node_type();
	}
	
	public static boolean isAddAvailable(Node n)
	{
		if(n.getData()==null || n.getData().getClass()!=RuleCase.class) return false;
		return (type_multiple_allowed((RuleCase)n.getData()))!=null;
	}
	
	public static Node onAddNew(Node n)
	{
		if(n.getData()==null || n.getData().getClass()!=RuleCase.class) return null;
		NodeTypewithScope nts=type_multiple_allowed((RuleCase)n.getData());
		Node result=new Node(nts.nt,null);
		result.set_scope_card(nts.card_scope || result.get_scope_card());
		result.set_scope_player(nts.player_scope || result.get_scope_player());
		return result;
	}
	
	public static boolean isSelection(Node n)
	{
		return n.get_node_type()!=NodeType.nd_raw;
	}
	
	public String[] getSelectionCases(Node n)
	{
		String[] result = preSelections(n.get_node_type(),n.get_scope_player(),n.get_scope_card(),isDeletable(n));
//		System.out.println(n.get_node_type());
//		System.out.println(n.get_scope_player());
//		System.out.println(n.get_scope_card());
//		System.out.println(isDeletable(n));
		previous_selection=result;
		return result;
	}
	
	public Node applySelectionCases(Node n, int input)
	{
		return postSelections(n.get_node_type(),n.get_scope_player(),n.get_scope_card(),isDeletable(n),input);
	}
	
	/*
	private String[] getSelectionCases(Node n, int last_location, boolean isName)
	{
		Node child;
		Node o;
		NodeTypewithScope nts;
		if(n.getData().getClass()!=RuleCase.class)
		{
			child=n.getChildNode(last_location);
			ar=child.ar_current;
			o=processSelection(NodeType.nd_action,n.get_scope_player(), n.get_scope_card(), false);
			if(o!=null) 
				n.setChildNode(last_location,o);
			return;
		}
		
		nts=type_multiple_allowed((RuleCase)n.getData());
		
		if(n.numChildren()<=last_location) // ...으로 생성
		{
			System.out.println(""+n.numChildren()+last_location);
			// Start: Ask UI for selection
			ar=n.ar_etc;
			o=processSelection(nts.nt,n.get_scope_player() || nts.player_scope, n.get_scope_card() || nts.card_scope,false);
			// End
			if(o!=null) 
				n.addChildNode(o);
			return;
		}
		
		child=n.getChildNode(last_location);
		if(child.get_node_type()==NodeType.nd_raw) // Raw Data
		{
			String s="0";
			if(child.getData()!=null) s=child.getData().toString();
			child.setData(m.UI_input_string(child.ar_name,s));
			return;

		}
		ar=child.ar_current;
		if(nts==null)
			o=processSelection(child.get_node_type(),child.get_scope_player(),child.get_scope_card(),false);
		else
			o=processSelection(child.get_node_type(),child.get_scope_player(),child.get_scope_card(),nts.nt==child.get_node_type());
		if(o!=null)
		{
			if(o.get_node_type()==NodeType.nd_special_delete)
				n.deleteChildNode(last_location);
			else
				n.setChildNode(last_location,o);
		}
	}
	*/
	
	public Rule getRule()
	{
		return rule;
	}
	public void setRule(Rule rule_)
	{
		rule=rule_;
	}
	public boolean save(ObjectOutputStream out)
	{
		try {
			out.writeObject(rule);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	public boolean load(ObjectInputStream in)
	{
		try {
			rule=(Rule)in.readObject();
			return true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
//	public void setUI(RuleDisplayer m_)
//	{
//		m=m_;
//	}
}
