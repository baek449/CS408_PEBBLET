package PEBBLET;

import manager.DefinitionManager;
import manager.Node;
import manager.NodeType;
import manager.RuleCase;
import manager.RuleManager;

public class RuleDisplayer extends NodeDisplayer {
	private RuleManager rm;
	
	public RuleDisplayer(RuleManager rm_)
	{
		super();
		rm=rm_;
		rm.setUI(this);
		setTargetNode(rm.getRule().getRoot());
	}

	@Override
	public void call_fillupSelection(Node parent, int index, boolean isName) {
		// TODO Auto-generated method stub
		rm.fillupSelection(parent, index, isName);
	}

	@Override
	public Position drawNode(Node n, Position p) {
		// TODO Auto-generated method stub
		if (n==null)
			System.err.println("Null Node");
		n.ar_current=new AreaRange(0,0,0,0,false);
		n.ar_etc=new AreaRange(0,0,0,0,false);
		n.ar_name=new AreaRange(0,0,0,0,false);
		Position p_=p.addSpace();
		AreaRange a=new AreaRange(p_,p_,false);
		AreaRange b;
		if(n.getParent()==null) // Root
		{
			// Draw root's children
			a=text(p_, "Game");
			a=draw_vertical(n,a.nextBottom(),0);
			a=enclose(n,a,false);
			return a.getEnd();
		}
		if(n.get_node_type()==NodeType.nd_raw) // Raw Data, use name field.
		{
			a=draw_name_right(n,new AreaRange(p,p,true));
			a=enclose(n,a,false);
			return a.getEnd();
		}
		Object d_=n.getData();
		if(d_==null || d_.getClass()!=RuleCase.class) // Variable
		{
			String s="";
			if(d_!=null) s=d_.toString();
			a=text(a.getEnd(),s);
			a=enclose(n,a,true);
			return a.getEnd();
		}
		Position newp;
		switch((RuleCase)d_)
		{
		case action_card:
		case player_player:
		case deck_player:
		case num_player:
		case num_card:
		case string_player:
		case string_card:
			a=draw_right(a,n.getChildNode(0));
			a=draw_right_text(a, ".");
			a=draw_right(a,n.getChildNode(1));
			a=enclose(n,a,true);
			return a.getEnd();

		case action_multiple:
			a=draw_bottom_text(a, "{");
			newp=a.nextBottom().addTab();
			b=draw_vertical(n,newp,0);
			b=draw_etc_bottom(n,b);
			a=a.merge(b);
			a=draw_bottom_text(a, "}");
			a=enclose(n,a,true);
			return a.getEnd();
		case action_move:
			a=draw_right_text(a, "Move ");
			a=draw_right(a,n.getChildNode(0));
			a=draw_right_text(a, " to ");
			a=draw_right(a,n.getChildNode(1));
			a=enclose(n,a,true);
			return a.getEnd();
		case action_load:
			a=draw_right_text(a, "Load ");
			a=draw_right(a,n.getChildNode(0));
			a=draw_right_text(a, " to ");
			a=draw_right(a,n.getChildNode(1));
			a=enclose(n,a,true);
			return a.getEnd();
		case action_shuffle:
			a=draw_right_text(a, "Shuffle ");
			a=draw_right(a,n.getChildNode(0));
			a=enclose(n,a,true);
			return a.getEnd();
		case action_order:
			a=draw_right_text(a, "Order ");
			a=draw_right(a,n.getChildNode(0));
			a=draw_right_text(a, " with ");
			newp=a.nextRight();
			b=draw_horizontal(n,newp,1);
			a=a.merge(b);
			a=draw_etc_right(n,a);
			a=enclose(n,a,true);
			return a.getEnd();
		case action_act:
			a=draw_right_text(a, "Act ");
			a=draw_right(a,n.getChildNode(0));
			a=draw_right_text(a, " :");
			newp=a.nextBottom();
			b=draw_right(new AreaRange(newp,newp,true),n.getChildNode(1));
			a=a.merge(b);
			a=enclose(n,a,true);
			return a.getEnd();
		case action_choose:
			a=draw_bottom_text(a, "Choose from - ");
			newp=a.nextBottom();
			b=draw_vertical(n,newp,0);
			a=a.merge(b);
			a=draw_etc_bottom(n,a);
			a=enclose(n,a,true);
			return a.getEnd();
		case action_if:
			a=draw_right_text(a, "If ");
			a=draw_right(a,n.getChildNode(0));
			a=draw_right_text(a, ":");
			a=draw_bottom(a,n.getChildNode(1));
			a=enclose(n,a,true);
			return a.getEnd();
		case action_ifelse:
			a=draw_right_text(a, "If ");
			a=draw_right(a,n.getChildNode(0));
			a=draw_right_text(a, ":");
			a=draw_bottom(a,n.getChildNode(1));
			a=draw_bottom_text(a, "Else : ");
			a=draw_bottom(a,n.getChildNode(2));
			a=enclose(n,a,true);
			return a.getEnd();
		case action_repeat:
			a=draw_right_text(a, "Repeat ");
			a=draw_right(a,n.getChildNode(0));
			a=draw_right_text(a, ":");
			a=draw_bottom(a,n.getChildNode(1));
			a=enclose(n,a,true);
			return a.getEnd();
		case action_endgame:
			a=draw_right_text(a, "Endgame Win: ");
			a=draw_right(a,n.getChildNode(0));
			a=enclose(n,a,true);
			return a.getEnd();
		case action_endgame_draw:
			a=draw_right_text(a, "Endgame Draw");
			a=enclose(n,a,true);
			return a.getEnd();
		case action_endgame_order:
			a=draw_right_text(a, "Endgame Order: ");
			newp=a.nextRight();
			b=draw_horizontal(n,newp,0);
			a=a.merge(b);
			a=draw_etc_right(n,a);
			a=enclose(n,a,true);
			return a.getEnd();
		case action_show:
			a=draw_right_text(a, "Show ");
			a=draw_right(a,n.getChildNode(0));
			a=draw_right_text(a, " to ");
			a=draw_right(a,n.getChildNode(1));
			a=enclose(n,a,true);
			return a.getEnd();
			
		case player_multiple:
			a=draw_right_text(a, "(");
			newp=a.nextRight();
			b=draw_horizontal(n,newp,0);
			a=a.merge(b);
			a=draw_etc_right(n,a);
			a=draw_right_text(a, ")");
			a=enclose(n,a,true);
			return a.getEnd();
		case player_current:
			a=draw_right_text(a, "current player");
			a=enclose(n,a,true);
			return a.getEnd();
		case player_all:
			a=draw_right_text(a, "all player");
			a=enclose(n,a,true);
			return a.getEnd();
		case player_exclude:
			a=draw_right_text(a, "Exclude ");
			a=draw_right(a,n.getChildNode(0));
			a=draw_right_text(a, " from ");
			a=draw_right(a,n.getChildNode(1));
			a=enclose(n,a,true);
			return a.getEnd();
		case player_left:
			a=draw_right_text(a, "Left ");
			a=draw_right(a,n.getChildNode(0));
			a=draw_right_text(a, " th player from ");
			a=draw_right(a,n.getChildNode(1));
			a=enclose(n,a,true);
			return a.getEnd();
		case player_right:
			a=draw_right_text(a, "Right ");
			a=draw_right(a,n.getChildNode(0));
			a=draw_right_text(a, " th player from ");
			a=draw_right(a,n.getChildNode(1));
			a=enclose(n,a,true);
			return a.getEnd();
		case player_left_all:
			a=draw_right_text(a, "Left ");
			a=draw_right(a,n.getChildNode(0));
			a=draw_right_text(a, " players from ");
			a=draw_right(a,n.getChildNode(1));
			a=enclose(n,a,true);
			return a.getEnd();
		case player_right_all:
			a=draw_right_text(a, "Right ");
			a=draw_right(a,n.getChildNode(0));
			a=draw_right_text(a, " players from ");
			a=draw_right(a,n.getChildNode(1));
			a=enclose(n,a,true);
			return a.getEnd();
		case player_satisfy:
			a=draw_right_text(a, "Satisfying ");
			a=draw_right(a,n.getChildNode(0));
			a=draw_right_text(a, " players from ");
			a=draw_right(a,n.getChildNode(1));
			a=enclose(n,a,true);
			return a.getEnd();
		case player_most:
			a=draw_right_text(a, "Most ");
			a=draw_horizontal(n,a.nextRight(),1);
			a=draw_etc_right(n,a);
			newp=a.nextBottom().addTab();
			b=new AreaRange(newp,newp,false);
			b=draw_right_text(b, "players from ");
			b=draw_right(b,n.getChildNode(1));
			a=a.merge(b);
			a=enclose(n,a,true);
			return a.getEnd();
		case player_select:
			a=draw_right_text(a, "Select ");
			a=draw_right(a,n.getChildNode(0));
			a=draw_right_text(a, " players from ");
			a=draw_right(a,n.getChildNode(1));
			a=enclose(n,a,true);
			return a.getEnd();
	
		case deck_select:
			a=draw_right_text(a, "Select a deck from ");
			a=draw_horizontal(n,a.nextRight(),0);
			a=draw_etc_right(n,a);
			a=enclose(n,a,true);
			return a.getEnd();
		case card_all:
			a=draw_right_text(a, "All cards in ");
			a=draw_right(a,n.getChildNode(0));
			a=enclose(n,a,true);
			return a.getEnd();
		case card_top:
			a=draw_right_text(a, "Top ");
			a=draw_right(a,n.getChildNode(0));
			a=draw_right_text(a, " cards from ");
			a=draw_right(a,n.getChildNode(1));
			a=enclose(n,a,true);
			return a.getEnd();
		case card_bottom:
			a=draw_right_text(a, "Bottom ");
			a=draw_right(a,n.getChildNode(0));
			a=draw_right_text(a, " cards from ");
			a=draw_right(a,n.getChildNode(1));
			a=enclose(n,a,true);
			return a.getEnd();
		case card_satisfy:
			a=draw_right_text(a, "Cards satisfying ");
			a=draw_right(a,n.getChildNode(0));
			a=draw_right_text(a, " from ");
			a=draw_right(a,n.getChildNode(1));
			a=enclose(n,a,true);
			return a.getEnd();
		case card_select:
			a=draw_right_text(a, "Select ");
			a=draw_right(a,n.getChildNode(0));
			a=draw_right_text(a, " cards from ");
			a=draw_horizontal(n,a.nextRight(),1);
			a=draw_etc_right(n,a);
			a=enclose(n,a,true);
			return a.getEnd();
			
		case cond_numcompare:
			a=draw_right_text(a, "Comparison: ");
			a=draw_right(a,n.getChildNode(0));
			a=draw_right(a,n.getChildNode(1));
			a=draw_right(a,n.getChildNode(2));
			a=enclose(n,a,true);
			return a.getEnd();
		case cond_samecard:
		case cond_sameplayer:
			a=draw_right_text(a, "Identical? ");
			a=draw_right(a,n.getChildNode(0));
			a=draw_right(a,n.getChildNode(1));
			a=enclose(n,a,true);
			return a.getEnd();
		case cond_samestring:
			a=draw_right(a,n.getChildNode(0));
			a=draw_right_text(a, "==");
			a=draw_right(a,n.getChildNode(1));
			a=enclose(n,a,true);
			return a.getEnd();
		case cond_typeequal:
			a=draw_right_text(a, "TypeEqual?");
			a=draw_right(a,n.getChildNode(0));
			a=draw_right(a,n.getChildNode(1));
			a=enclose(n,a,true);
			return a.getEnd();
		case cond_istype:
			a=draw_right_text(a, "Type ");
			a=draw_right(a,n.getChildNode(0));
			a=draw_right_text(a, "? ");
			a=draw_right(a,n.getChildNode(1));
			a=enclose(n,a,true);
			return a.getEnd();
		case cond_and:
			a=draw_right(a,n.getChildNode(0));
			a=draw_right_text(a, "and");
			a=draw_right(a,n.getChildNode(1));
			a=enclose(n,a,true);
			return a.getEnd();
		case cond_or:
			a=draw_right(a,n.getChildNode(0));
			a=draw_right_text(a, "or");
			a=draw_right(a,n.getChildNode(1));
			a=enclose(n,a,true);
			return a.getEnd();
		case cond_not:
			a=draw_right_text(a, "!");
			a=draw_right(a,n.getChildNode(0));
			a=enclose(n,a,true);
			return a.getEnd();
		case cond_emptydeck:
			a=draw_right_text(a, "Empty?");
			a=draw_right(a,n.getChildNode(0));
			a=enclose(n,a,true);
			return a.getEnd();
			
		case num_size_player:
		case num_size_deck:
		case num_size_card:
			a=draw_right_text(a, "Size of ");
			a=draw_right(a,n.getChildNode(0));
			a=enclose(n,a,true);
			return a.getEnd();
		case num_operation:
			a=draw_right(a,n.getChildNode(0));
			a=draw_right(a,n.getChildNode(1));
			a=draw_right(a,n.getChildNode(2));
			a=enclose(n,a,true);
			return a.getEnd();
		case num_call:
			a=draw_right_text(a, "Call number from ");
			a=draw_right(a,n.getChildNode(0));
			a=draw_right_text(a, "to ");
			a=draw_right(a,n.getChildNode(1));
			a=enclose(n,a,true);
			return a.getEnd();
		case string_call:
			a=draw_right_text(a, "Call string from ");
			a=draw_horizontal(n,a.nextRight(),0);
			a=draw_etc_right(n,a);
			a=enclose(n,a,true);
			return a.getEnd();
		case order_high:
			a=draw_right_text(a, "high ");
			a=draw_right(a,n.getChildNode(0));
			a=enclose(n,a,true);
			return a.getEnd();
		case order_low:
			a=draw_right_text(a, "low ");
			a=draw_right(a,n.getChildNode(0));
			a=enclose(n,a,true);
			return a.getEnd();
		case namedAction_namedAction:
			a=draw_right(a,n.getChildNode(0));
			a=draw_right_text(a, ":");
			a=draw_right(a,n.getChildNode(1));
			a=enclose(n,a,true);
			return a.getEnd();
		case string_raw:
		case num_raw:
			a=draw_right(a,n.getChildNode(0));
			a=enclose(n,a,true);
			return a.getEnd();
		default:
			System.err.println("Invalid RuleCase "+((RuleCase)d_).toString());
			return p;
		}
	}

}
