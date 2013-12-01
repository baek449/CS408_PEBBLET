package manager;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import PEBBLET.AreaRange;
import PEBBLET.MyCanvas;
import PEBBLET.Position;

/*
 * DefinitionManager가 호출하는 UI 함수들
 * String UI_input_string(); // Textbox에 입력된 string을 받아온다.
 * int UI_input_selection(String[] selection); // selection 선택지를 주고 fillup box를 띄워서 선택된 항목의 index를 리턴한다.
 * void UI_update(); // 바뀐 Defintion object를 화면에 반영한다.
 */



public class DefinitionManager {
	private static final String[] selection_noncard = {"Deck []","Number []","String []","Player []","(Cancel)"};
	private static final String[] selection_noncard_del = {"Deck []","Number []","String []","Player []","(Delete)","(Cancel)"};
	private static final String[] selection_card = {"Number []","String []","Action []","(Cancel)"};
	private static final String[] selection_card_del = {"Number []","String []","Action []","(Delete)","(Cancel)"};
	private static final String[] selection_del = {"(Delete)","(Cancel)"};
	
	private Definition definition;
	private static MyCanvas m;
	public Node search(int[] location)
	{
		Node cur=definition.getRoot();
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
	public void setUI(MyCanvas m_)
	{
		m=m_;
	}
	public void fillupSelection(int[] location, boolean isName)
	{
		int[] location_parent = new int[location.length-1];
		int last_location=location[location.length-1];
		int loop;
		for(loop=0;loop<location_parent.length-1;loop++)
			location_parent[loop]=location[loop];
		Node n=search(location_parent);
		fillupSelection(n, last_location, isName);
	}
	public static void fillupSelection(Node n, int last_location, boolean isName)
	{
		NodeType selection_result;
		String string_entered = null;
		Node newnode=null;
		NodeType t=n.get_node_type();
		switch(t)
		{
		case nd_def_global:
		case nd_def_player:
		case nd_card:
			boolean iscard=(t==NodeType.nd_card);
			if(last_location>=n.numChildren())
			{
				selection_result=show_def_selections(n.ar_etc,false,iscard);
				if(selection_result==null) // cancel
					return;
				newnode=new Node(selection_result,n);
				break;
			}
			else if(isName)
			{
				string_entered=m.UI_input_string(n.getChildNode(last_location).ar_name,(String)n.getChildNode(last_location).getData());
				if(string_entered.equals("")) return; // cancel
				n.getChildNode(last_location).setData(string_entered);
				break;
			}
			else
			{
				selection_result=show_def_selections(n.getChildNode(last_location).ar_current, true,iscard);
				if(selection_result==null) // cancel
					return;
				else if (selection_result==NodeType.nd_special_delete) // delete
					n.deleteChildNode(last_location);
				else
				{
					newnode=new Node(selection_result,null);
					n.setChildNode(last_location, newnode);
				}
				break;
			}
		case nd_def_card:
			if(last_location>=n.numChildren())
			{
				newnode=new Node(NodeType.nd_card,n);
				break;
			}
			else if(isName)
			{
				string_entered=m.UI_input_string(n.getChildNode(last_location).ar_name,(String)n.getChildNode(last_location).getData());
				if(string_entered.equals("")) return; // cancel
				n.getChildNode(last_location).setData(string_entered);
				break;
			}
			else
			{
				selection_result=show_delete(n.getChildNode(last_location).ar_current);
				if(selection_result==null) // cancel
					return;
				else if (selection_result==NodeType.nd_special_delete)
					n.deleteChildNode(last_location); // delete
				else
				{
					// TODO: Error
				}
				break;
			}

		case nd_num:
			if(last_location>=n.numChildren())
			{
				string_entered=m.UI_input_string(n.ar_etc,"");
				newnode=new Node();
				// 입력 단계에서 string을 integer로 변환. 향후 바뀔 수 있음.
				try
				{
					newnode.setData(Integer.parseInt(string_entered));
				} catch(NumberFormatException e) {
					return;
				}
				n.addChildNode(newnode);
				break;
			}
			else
			{
				String s="";
				if(n.getChildNode(last_location).getData()!=null)
					s=n.getChildNode(last_location).getData().toString();
				string_entered=m.UI_input_string(n.getChildNode(last_location).ar_name, s);
				if(string_entered==null || string_entered.equals("")) // delete
				{
					if (n.getParent().get_node_type()==null)
						return;
					n.deleteChildNode(last_location); // delete
				}
				else
				{
					// 입력 단계에서 string을 integer로 변환. 향후 바뀔 수 있음.
					try
					{
						n.getChildNode(last_location).setData(Integer.parseInt(string_entered));
					} catch (NumberFormatException e) {
						return;
					}
				}
				break;
			}
		case nd_str:
			if(last_location>=n.numChildren())
			{
				string_entered=m.UI_input_string(n.ar_etc,"");
				newnode=new Node();
				newnode.setData(string_entered);
				n.addChildNode(newnode);
				break;
			}
			else
			{
				String s="";
				if(n.getChildNode(last_location).getData()!=null)
					s=n.getChildNode(last_location).getData().toString();
				string_entered=m.UI_input_string(n.getChildNode(last_location).ar_name, s);
				if(string_entered==null || string_entered.equals("")) // delete
					n.deleteChildNode(last_location); // delete
				else
					n.getChildNode(last_location).setData(string_entered);
				break;
			}
		default:
			System.err.println("error! Invalid node on definition");

		}
		m.repaint();
	}
	
	public static NodeType show_delete(AreaRange r)
	{
		int receive=m.UI_input_selection(r, selection_del);
		if(receive==0) return NodeType.nd_special_delete;
		return null;
	}
	// show_def_selections(boolean delete,boolean iscard) : 변수의 종류를 UI에 띄워 사용자 입력에 따라 NodeType을 리턴한다.
	// cancel일 경우 null을 리턴한다.
	public static NodeType show_def_selections(AreaRange r, boolean delete,boolean iscard)
	{
		int receive=-1;
		if(iscard)
		{
			if(delete)
			{
				receive=m.UI_input_selection(r, selection_card_del);
				if(receive==3) return NodeType.nd_special_delete;
				if(receive==4) return null;
			}
			else
			{
				receive=m.UI_input_selection(r, selection_card);
				if(receive==3) return null;
			}
			switch(receive)
			{
			case 0:
				return NodeType.nd_num;
			case 1:
				return NodeType.nd_str;
			case 2:
				return NodeType.nd_action;
			}
		}
		else
		{
			if(delete)
			{
				receive=m.UI_input_selection(r, selection_noncard_del);
				if(receive==4) return NodeType.nd_special_delete;
				if(receive==5) return null;
			}
			else
			{
				receive=m.UI_input_selection(r, selection_noncard);
				if(receive==4) return null;
			}
			switch(receive)
			{
			case 0:
				return NodeType.nd_deck;
			case 1:
				return NodeType.nd_num;
			case 2:
				return NodeType.nd_str;
			case 3:
				return NodeType.nd_player;
			}
		}
		return null;
	}
	public Definition getDefinition()
	{
		return definition;
	}
	public void setDefinition(Definition definition_)
	{
		definition=definition_;
	}
	
	public boolean save(ObjectOutputStream out)
	{
		try {
			out.writeObject(definition);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	public boolean load(ObjectInputStream in)
	{
		try {
			definition=(Definition)in.readObject();
			return true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	
	private int prev_index=0;
	public boolean def_click(Node n, Position p)
	{
		// 해당 없음.
		if (!n.ar_current.inside(p))
			return false;
		if(n.ar_name.isvalid() && n.ar_name.inside(p))
		{
			// 이름 필드 선택함.
			fillupSelection(n.getParent(),prev_index,true);
			return true;
		}
		else if (n.ar_etc.isvalid() && n.ar_etc.inside(p))
		{
			// ... 필드 선택함.
			fillupSelection(n,n.numChildren(),false);
			return true;
		}
		// 자식 선택함.
		int prv_index=prev_index;
		for(int loop=0;loop<n.numChildren();loop++)
		{
			prev_index=loop;
			if (def_click(n.getChildNode(loop),p))
				return true;
		}
		prev_index=prv_index;
		
		// 현재 노드 선택함
		if (!n.ar_current.isvalid())
			return true;
		// valid한 경우 조작 수행
		fillupSelection(n.getParent(),prev_index,false);
		return true;
	}
	private AreaRange def_vertical_draw(Node n, Position p, int childstartIndex)
	{
		AreaRange a=new AreaRange(p,p,true);
		for(int loop=childstartIndex;loop<n.numChildren();loop++)
			a=draw_bottom(a,n.getChildNode(loop));
		return a;
	}
	private AreaRange def_horizontal_draw(Node n, Position p, int childstartIndex)
	{
		AreaRange a=new AreaRange(p,p,true);
		for(int loop=childstartIndex;loop<n.numChildren();loop++)
			a=draw_right(a,n.getChildNode(loop));
		return a;
	}
	
	public void drawAll(Position p)
	{
		drawNode(definition.getRoot(),p);
	}
	
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
	public AreaRange draw_name_right(Node n, AreaRange p)
	{
		String s="";
		if(n.getData()!=null) s=n.getData().toString();
		AreaRange a=m.text_and_rect(p.nextRight(), s);
		n.ar_name=a;
		return p.merge(a);
	}
	public AreaRange draw_name_bottom(Node n, AreaRange p)
	{
		String s="";
		if(n.getData()!=null) s=n.getData().toString();
		AreaRange a=m.text_and_rect(p.nextBottom(), s);
		n.ar_name=a;
		return p.merge(a);
	}
	public AreaRange draw_etc_right(Node n, AreaRange p)
	{
		AreaRange a=m.text_and_rect(p.nextRight(), "...");
		n.ar_etc=a;
		return p.merge(a);
	}
	public AreaRange draw_etc_bottom(Node n, AreaRange p)
	{
		AreaRange a=m.text_and_rect(p.nextBottom(), "...");
		n.ar_etc=a;
		return p.merge(a);
	}
	
	// prev의 오른쪽/아래에 노드 n을 그린다.
	public AreaRange draw_right(AreaRange prev, Node n)
	{
		Position draw=drawNode(n, prev.nextRight());
		return prev.merge(draw);
	}
	public AreaRange draw_bottom(AreaRange prev, Node n)
	{
		Position draw=drawNode(n, prev.nextBottom());
		return prev.merge(draw);
	}
	
	// prev의 오른쪽/아래에 문자열을 그린다.
	public AreaRange draw_right_text(AreaRange prev, String msg)
	{
		AreaRange a=prev.merge(m.text(prev.nextRight(), msg));
		a.valid=false;
		return a;	
	}
	public AreaRange draw_bottom_text(AreaRange prev, String msg)
	{
		AreaRange a=prev.merge(m.text(prev.nextBottom(), msg));
		a.valid=false;
		return a;	
	}
	
	// prev의 오른쪽/아래에 문자열 상자를 그린다.
	public AreaRange draw_right_textbox(AreaRange prev, String msg)
	{
		AreaRange a=prev.merge(m.text_and_rect(prev.nextRight(), msg));
		a.valid=true;
		return a;	
	}
	public AreaRange draw_bottom_textbox(AreaRange prev, String msg)
	{
		AreaRange a=prev.merge(m.text_and_rect(prev.nextBottom(), msg));
		a.valid=true;
		return a;	
	}
	
	
	public AreaRange enclose(Node n, AreaRange data, boolean valid)
	{
		data.enclose();
		data.valid=valid;
		if (valid)
			m.rect(data);
		if(n!=null)
			n.ar_current=data;
		return data;
	}
}
