import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/*
 * DefinitionManager�� ȣ���ϴ� UI �Լ���
 * String UI_input_string(); // Textbox�� �Էµ� string�� �޾ƿ´�.
 * int UI_input_selection(String[] selection); // selection �������� �ְ� fillup box�� ����� ���õ� �׸��� index�� �����Ѵ�.
 * void UI_update(); // �ٲ� Defintion object�� ȭ�鿡 �ݿ��Ѵ�.
 */



public class DefinitionManager {
	private static final String[] selection_noncard = {"Deck []","Number []","String []","Player []","(Cancel)"};
	private static final String[] selection_noncard_del = {"Deck []","Number []","String []","Player []","(Delete)","(Cancel)"};
	private static final String[] selection_card = {"Number []","String []","Action []","(Cancel)"};
	private static final String[] selection_card_del = {"Number []","String []","Action []","(Delete)","(Cancel)"};
	private static final String[] selection_del = {"(Delete)","(Cancel)"};
	
	private Definition definition;
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
	
	public void fillupSelection(int[] location, boolean isName)
	{
		int[] location_parent = new int[location.length-1];
		int last_location=location[location.length-1];
		int loop;
		for(loop=0;loop<location_parent.length-1;loop++)
			location_parent[loop]=location[loop];
		Node n=search(location_parent);
		NodeType selection_result;
		String string_entered;
		Node newnode;
		NodeType t=n.get_node_type();
		switch(t)
		{
		case nd_def_global:
		case nd_def_player:
		case nd_card:
			boolean iscard=(t==NodeType.nd_card);
			if(last_location>=n.numChildren())
			{
				selection_result=show_def_selections(false,iscard);
				if(selection_result==null) // cancel
					return;
				newnode=new Node(selection_result,n);
				break;
			}
			else if(isName)
			{
				string_entered=UI_input_string();
				if(string_entered.equals("")) return; // cancel
				n.getChildNode(last_location).setData(string_entered);
				break;
			}
			else
			{
				selection_result=show_def_selections(true,iscard);
				if(selection_result==null) // cancel
					return;
				else if (selection_result==NodeType.nd_special_delete) // delete
					n.deleteChildNode(last_location);
				else
					newnode=new Node(selection_result,null);
					n.setChildNode(last_location, newnode);
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
				string_entered=UI_input_string();
				if(string_entered.equals("")) return; // cancel
				n.getChildNode(last_location).setData(string_entered);
				break;
			}
			else
			{
				selection_result=show_delete();
				if(selection_result==null) // cancel
					return;
				else if (selection_result==NodeType.nd_special_delete)
					n.deleteChildNode(last_location); // delete
				else
					// TODO: Error
				break;
			}

		case nd_num:
			if(last_location>=n.numChildren())
			{
				string_entered=UI_input_string();
				newnode=new Node();
				// �Է� �ܰ迡�� string�� integer�� ��ȯ. ���� �ٲ� �� ����.
				newnode.setData(Integer.parseInt(string_entered));
				n.addChildNode(newnode);
				break;
			}
			else
			{
				string_entered=UI_input_string();
				if(string_entered==null) // delete
					n.deleteChildNode(last_location); // delete
				else
					// �Է� �ܰ迡�� string�� integer�� ��ȯ. ���� �ٲ� �� ����.
					n.getChildNode(last_location).setData(Integer.parseInt(string_entered));
				break;
			}
		case nd_str:
			if(last_location>=n.numChildren())
			{
				string_entered=UI_input_string();
				newnode=new Node();
				newnode.setData(string_entered);
				n.addChildNode(newnode);
				break;
			}
			else
			{
				string_entered=UI_input_string();
				if(string_entered==null) // delete
					n.deleteChildNode(last_location); // delete
				else
					n.getChildNode(last_location).setData(string_entered);
				break;
			}
		default:
			System.err.println("error! Invalid node on definition");

		}
		UI_update();
	}
	
	public NodeType show_delete()
	{
		int receive=UI_input_selection(selection_del);
		if(receive==0) return NodeType.nd_special_delete;
		return null;
	}
	// show_def_selections(boolean delete,boolean iscard) : ������ ������ UI�� ��� ����� �Է¿� ���� NodeType�� �����Ѵ�.
	// cancel�� ��� null�� �����Ѵ�.
	public NodeType show_def_selections(boolean delete,boolean iscard)
	{
		int receive=-1;
		if(iscard)
		{
			if(delete)
			{
				receive=UI_input_selection(selection_card_del);
				if(receive==3) return NodeType.nd_special_delete;
				if(receive==4) return null;
			}
			else
			{
				receive=UI_input_selection(selection_card);
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
				receive=UI_input_selection(selection_noncard_del);
				if(receive==4) return NodeType.nd_special_delete;
				if(receive==5) return null;
			}
			else
			{
				receive=UI_input_selection(selection_noncard);
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
