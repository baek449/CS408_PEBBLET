import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import oracle.jrockit.jfr.Options;


public class RuleManager {
	private static final String[] selection_action =
		{"(Cancel)", "Multiple Actions", "Move [card] to [deck]", "Load [file] to [deck]", "Shuffle [deck]", "Order [deck] [order]*",
		"Act [player] [action]", "If [cond] [action]", "If [cond] [action] else [action]", "Repeat [num] [action]", "Endgame Win [player]",
		"Endgame Draw", "Endgame Order [order]*", "Show [card] to [player]"};
	private static final String[] selection_action_pscope = {"Choose from [namedAction]*"};
	private static final String[] selection_player =
		{"(Cancel)", "Multiple Players", "All Players", "Exclude [player] from [player]", "Left [num]th player from [player]",
		"Right [num]th player from [player]", "Left [num] players from [player]", "Right [num] players from [player]",
		"Satisfying [cond] from [player]", "Most [order]* among [player]"};
	private static final String[] selection_player_pscope = {"Select [num] Players in [player]"};
	private static final String[] selection_deck = {"(Cancel)"};
	private static final String[] selection_deck_pscope = {"Select Deck in [deck]*"};
	private static final String[] selection_card =
		{"(Cancel)", "All cards in [deck]", "Top [num] cards in [deck]", "Bottom [num] cards in [deck]",
		"Satisfying [cond] from [card]"};
	private static final String[] selection_card_pscope = {"Select [num] Cards in [card]"};
	private static final String[] selection_cond =
		{"(Cancel)", "[num] [operation] [num]", "Identical? [card] [card]", "Identical [player] [player]",
		"[string] == [string]", "Typeequal? [card] [card]", "Type? [string] [card]",
		"[cond] and [cond]", "[cond] or [cond]", "Not [cond]", "Empty? [deck]"};
	private static final String[] selection_num =
		{"(Cancel)", "Insert Integer...", "Size [player]", "Size [deck]", "Size [card]",
		"[num] [operation] [num]"};
	private static final String[] selection_num_pscope = {"Call from [num] to [num]"};
	private static final String[] selection_str = 		{"(Cancel)", "Insert String..."};
	private static final String[] selection_str_pscope = {"Call from [string]*"};

	
	
	
	private Rule rule;
	private String[][][] varList;
	
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
	
	private class NodeTypewithScope
	{
		public NodeType nt;
		public boolean player_scope;
		public boolean card_scope;
		public NodeTypewithScope() {}
		public NodeTypewithScope(NodeType nt_, boolean ps, boolean cs)
		{
			nt=nt_;
			player_scope=ps;
			card_scope=cs;
		}
	}
	
	public NodeTypewithScope type_multiple_allowed(RuleCase rc)
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
	
	public Node processActionSelection(boolean ps, boolean cs, boolean delete)
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
		int input = UI_input_selection((String[])options.toArray());
		
		// 3. Making a node
		Node newnode;
		Node o=new Node(NodeType.nd_action,null);
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
			newnode=new Node(NodeType.nd_str,o);
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
		case 14: // "Choose from [namedAction]*"
			if(ps)
			{
				o.setData(RuleCase.action_choose);
				return o;
			}
			break;
		}

		if(delete && input==(options.size()-1))
		{
			o.set_node_type(NodeType.nd_special_delete);
			return o;
		}
		o.setData(options.get(input));
		return o;
	}
	
	public Node processPlayerSelection(boolean ps, boolean cs, boolean delete)
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
		int input = UI_input_selection((String[])options.toArray());
		
		// 3. Making a node
		Node newnode;
		Node o=new Node(NodeType.nd_action,null);
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
			newnode=new Node(NodeType.nd_player,o);
			return o;
		case 9: //"Most [order]* among [player]"
			o.setData(RuleCase.player_most);
			newnode=new Node(NodeType.nd_player,o);
			return o;
		case 10: //"Select [num] Players in [player]"
			if(ps)
			{
				o.setData(RuleCase.player_select);
				newnode=new Node(NodeType.nd_num,o);
				newnode=new Node(NodeType.nd_player,o);
				return o;
			}
			break;
		}
		if(delete && input==(options.size()-1))
		{
			o.set_node_type(NodeType.nd_special_delete);
			return o;
		}
		o.setData(options.get(input));
		return o;
	}
	
	// t type에 대한 선택지를 띄워주고 플레이어에게 선택을 받은 후 적절한 node를 만들어 리턴해주는 함수. 취소시 null.
	public Node processSelection(NodeType t, boolean ps, boolean cs, boolean delete)
	{
		// TODO
		switch(t)
		{
		case nd_action:
			return processActionSelection(ps, cs, delete);
		case nd_player:
			return processPlayerSelection(ps, cs, delete);
		case nd_deck:
			return process_Selection(ps, cs, delete);
		case nd_card:
			return process_Selection(ps, cs, delete);
		case nd_cond:
			return process_Selection(ps, cs, delete);
		case nd_order:
			return process_Selection(ps, cs, delete);
		case nd_num:
			return process_Selection(ps, cs, delete);
		case nd_str:
			return process_Selection(ps, cs, delete);
		case nd_namedAction:
			return process_Selection(ps, cs, delete);
		}
		System.err.println("processSelection: Invalid node type.");
		return null;
	}
	
	public void fillupSelection(int[] location, boolean isName)
	{
		int[] location_parent = new int[location.length-1];
		int last_location=location[location.length-1];
		int loop;
		for(loop=0;loop<location_parent.length-1;loop++)
			location_parent[loop]=location[loop];
		Node n=search(location_parent);
		Node child;
		Node o;
		NodeTypewithScope nts;
		
		if(n.getData().getClass()!=RuleCase.class)
		{
			child=n.getChildNode(last_location);
			o=processSelection(NodeType.nd_action,n.get_scope_player(), n.get_scope_card(), false);
			if(o!=null) 
				n.setChildNode(last_location,o);
			return;
		}
		
		nts=type_multiple_allowed((RuleCase)n.getData());
		
		if(n.numChildren()>=last_location) // ...으로 생성
		{
			// Start: Ask UI for selection
			o=processSelection(nts.nt,n.get_scope_player() || nts.player_scope, n.get_scope_card() || nts.card_scope,false);
			// End
			if(o!=null) 
				n.addChildNode(o);
			return;
		}
		
		child=n.getChildNode(last_location);
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
}
