package manager;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import PEBBLET.AreaRange;
import PEBBLET.DefinitionDisplayer;
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
	
	public String[] get_selection_noncard(){
		return selection_noncard;
	}
	public String[] get_selection_noncard_del(){
		return selection_noncard_del;
	}
	
	public String[] get_selection_card(){
		return selection_card;
	}
	
	public String[] get_selection_card_del(){
		return selection_card_del;
	}
	
	public String[] get_selection_del(){
		return selection_del;
	}
	
	public DefinitionManager(){
		definition = new Definition();
	}
	
	private Definition definition;
	private static DefinitionDisplayer m;
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
	public void setUI(DefinitionDisplayer m_)
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
	
}
