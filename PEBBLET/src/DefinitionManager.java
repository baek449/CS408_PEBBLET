
public class DefinitionManager {
	private static final Node node_delete=new Node(NodeType.nd_special_delete,null);
	private boolean is_node_delete(Node n)
	{
		return n.get_node_type()==NodeType.nd_special_delete;
	}
	
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
		Node selection_result;
		String string_entered;
		for(loop=0;loop<location_parent.length-1;loop++)
			location_parent[loop]=location[loop];
		Node n=search(location_parent);
		NodeType t=n.get_node_type();
		// show_global_selections(boolean delete) : 전역 변수의 종류(deck, int, string, player)를 UI에 띄워 사용자 입력에 따라 적절한 Node를 생성해 리턴한다.
		switch(t)
		{
		case nd_def_global:
			if(last_location>=n.numChildren())
			{
				selection_result=show_global_selections(delete=false);
				if(selection_result==null) // cancel
					return;
				n.addChildNode(selection_result);
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
				selection_result=show_global_selections(delete=true);
				if(selection_result==null) // cancel
					return;
				else if (is_node_delete(selection_result)) // delete
					n.deleteChildNode(last_location);
				else
					n.setChildNode(last_location, selection_result);
				break;
			}
		case nd_def_player:
			if(last_location>=n.numChildren())
			{
				selection_result=show_player_selections(delete=false);
				if(selection_result==null) // cancel
					return;
				n.addChildNode(selection_result);
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
				selection_result=show_player_selections(delete=true);
				if(selection_result==null) // cancel
					return;
				else if (is_node_delete(selection_result)) // delete
					n.deleteChildNode(last_location);
				else
					n.setChildNode(last_location, selection_result);
				break;
			}
		case nd_def_card:
			if(last_location>=n.numChildren())
			{
				selection_result=new Node();
				selection_result.set_node_type(NodeType.nd_card);
				n.addChildNode(selection_result);
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
				selection_result=show_delete(delete=true);
				if(selection_result==null) // cancel
					return;
				else if (is_node_delete(selection_result))
					n.deleteChildNode(last_location); // delete
				else
					// TODO: Error
				break;
			}

		}
	}
	public Definition getDefinition()
	{
		return definition;
	}
	public void setDefinition(Definition definition_)
	{
		definition=definition_;
	}
}
