package PEBBLET;

import manager.DefinitionManager;
import manager.Node;

public class DefinitionDisplayer extends NodeDisplayer{
	private DefinitionManager dm;
	
	public DefinitionDisplayer(DefinitionManager dm_)
	{
		super();
		dm=dm_;
		dm.setUI(this);
		setTargetNode(dm.getDefinition().getRoot());
	}
	@Override
	public void call_fillupSelection(Node parent, int index, boolean isName) {
		// TODO Auto-generated method stub
		DefinitionManager.fillupSelection(parent, index, isName);
	}

	@Override
	// MyCanvas m 상의 위치 p에 node n을 그리고 box의 좌표를 업데이트한다. 오른쪽 아래 좌표를 리턴한다.
	public Position drawNode(Node n, Position p)
	{
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
			a= def_vertical_draw(n,p_,0);
			return enclose(n,a,false).getEnd();
		}
		if(n.get_node_type()==null) // Leaf
		{
			a=new AreaRange(p,p,false);
			a=draw_name_right(n,a);
			a.valid=false;
			n.ar_current=new AreaRange(a);
			return a.getEnd();
		}
		switch(n.get_node_type())
		{
		case nd_def_global: // Parent of global variables
			a=draw_bottom_text(a,"Global {");
			b=def_vertical_draw(n, a.nextBottom().addTab(), 0); // draw all variable nodes
			b=draw_etc_bottom(n,b);
			a=a.merge(b);
			a=draw_bottom_text(a,"}");
			a=enclose(n, a, false);
			return a.getEnd();
		case nd_def_player:
			a=draw_bottom_text(a,"Player {");
			b=def_vertical_draw(n, a.nextBottom().addTab(), 0); // draw all variable nodes
			b=draw_etc_bottom(n,b);
			a=a.merge(b);
			a=draw_bottom_text(a,"}");
			a=enclose( n, a, false);
			return a.getEnd();
		case nd_def_card:
			// Draw card list
			a=def_vertical_draw(n, p_, 0); // draw all card nodes
			a=draw_etc_bottom(n,a);
			a=enclose(n, a, false);
			return a.getEnd();
		case nd_card:
			// Draw Card
			a=draw_right_text(a,"Card ");
			a=draw_name_right(n,a);
			a=draw_right_text(a,"{ ");
			b=def_vertical_draw(n, a.nextBottom().addTab(), 0);
			b=draw_etc_bottom(n,b);
			a=a.merge(b);
			a=draw_bottom_text(a,"}");
			a=enclose(n, a, true);
			return a.getEnd();
		case nd_num:
			boolean b_=n.getParent().get_node_type()!=null;
			if(b_) // not N_players
			{
				a=draw_right_text(a,"Number ");
				a=draw_name_right(n,a);
			}
			else
				a=draw_right_text(a,"# of Players");
			a=draw_right_text(a,"  :");
			b=def_horizontal_draw(n, new AreaRange(a).nextRight(), 0);
			if(b_) b=draw_etc_right(n,b);
			a=a.merge(b);
			a=enclose(n, a, b_);
			return a.getEnd();
		case nd_str:
			a=draw_right_text(a,"String ");
			a=draw_name_right(n,a);
			a=draw_right_text(a,"  :");
			b=def_horizontal_draw(n, new AreaRange(a).nextRight(), 0);
			b=draw_etc_right(n,b);
			a=a.merge(b);
			a=enclose(n, a, true);
			return a.getEnd();
		case nd_player:
			a=draw_right_text(a,"Player ");
			a=draw_name_right(n,a);
			a=enclose(n, a, true);
			return a.getEnd();
		case nd_deck:
			a=draw_right_text(a,"Deck ");
			a=draw_name_right(n,a);
			a=enclose(n, a, true);
			return a.getEnd();
		case nd_action:
			a=draw_right_text(a,"Action ");
			a=draw_name_right(n,a);
			a=enclose(n, a, true);
			return a.getEnd();
		default:
			System.err.println("Err");
			return null;
		}
	}	
}
