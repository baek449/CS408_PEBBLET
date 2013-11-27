import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class RuleManager {
	private Rule rule;
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
	
	public void processActionSelection(Node parent, int last_location)
	{
		Object o; // data Received to here.
		Node child=parent.getChildNode(last_location);
		// Start: Ask UI for selection
		
		// End
		
		Node newnode;
		child.setData(o);
		if(o.getClass()==RuleCase.class)
		{
			switch((RuleCase)o)
			{
			case action_move:
				newnode=new Node(NodeType.nd_card,child);
				newnode=new Node(NodeType.nd_deck,child);
				break;
			case action_load:
				newnode=new Node(NodeType.nd_str,child);
				newnode=new Node(NodeType.nd_deck,child);
				break;
			case action_shuffle:
			case action_order:
				newnode=new Node(NodeType.nd_deck,child);
				break;
			case action_act:
				newnode=new Node(NodeType.nd_player,child);
				newnode=new Node(NodeType.nd_action,child);
				break;
			case action_if:
				newnode=new Node(NodeType.nd_cond,child);
				newnode=new Node(NodeType.nd_action,child);
				break;
			case action_ifelse:
				newnode=new Node(NodeType.nd_cond,child);
				newnode=new Node(NodeType.nd_action,child);
				newnode=new Node(NodeType.nd_action,child);
				break;
			case action_repeat:
				newnode=new Node(NodeType.nd_num,child);
				newnode=new Node(NodeType.nd_action,child);
				break;
			case action_endgame:
				newnode=new Node(NodeType.nd_player,child);
				break;
			case action_choose:
				newnode=new Node(NodeType.nd_num,child);
				break;
			case action_show:
				newnode=new Node(NodeType.nd_card,child);
				newnode=new Node(NodeType.nd_player,child);
				break;
			}
		}
	}
	
	// t type에 대한 선택지를 띄워주고 플레이어에게 선택을 받은 후 적절한 node를 만들어 리턴해주는 함수. 취소시 null.
	public Node processSelection(NodeType t, boolean ps, boolean cs, boolean delete)
	{
		// TODO
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
		}
		
		child=n.getChildNode(last_location);
		if(nts==null)
			o=processSelection(child.get_node_type(),child.get_scope_player(),child.get_scope_card(),false);
		else
			o=processSelection(child.get_node_type(),child.get_scope_player(),child.get_scope_card(),nts.nt==child.get_node_type());
		if(o!=null) 
			n.setChildNode(last_location,o);
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
