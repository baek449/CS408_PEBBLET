package manager;
import java.io.Serializable;
import java.util.ArrayList;

import PEBBLET.AreaRange;
import PEBBLET.MyCanvas;
import PEBBLET.Position;

public class Node implements Serializable{
	private NodeType node_type;
	private Object data;
	private Node parent;
	private ArrayList<Node> children;
	private boolean scope_player;
	private boolean scope_card;

	public boolean get_scope_player() {
		return scope_player;
	}
	public void set_scope_player(boolean scope_player) {
		this.scope_player = scope_player;
	}
	public boolean get_scope_card() {
		return scope_card;
	}
	public void set_scope_card(boolean scope_card) {
		this.scope_card = scope_card;
	}
	public NodeType get_node_type() {
		return node_type;
	}
	public void set_node_type(NodeType node_type) {
		this.node_type = node_type;
	}
	
	public Node()
	{
		scope_player=false;
		scope_card=false;
		children=new ArrayList<Node>();
	}
	public Node(NodeType node_type_, Node parent_)
	{
		scope_player=false;
		scope_card=false;
		node_type=node_type_;
		parent=parent_;
		children=new ArrayList<Node>();
		if (parent_!=null) parent_.addChildNode(this);
	}
	
	
	// ���� ����������, �Լ��� �ٲ�.
	public int numChildren()
	{
		return children.size();
	}
	public Object getData()
	{
		return data;
	}
	public void setData(Object data_)
	{
		data=data_;
	}
	public Node getParent()
	{
		return parent;
	}
	public void setParent(Node parent_)
	{
		if(parent.equals(parent_)) return;
		if(parent!=null) parent.deleteChildNode(this);
		parent=parent_;
		if(parent!=null) parent.addChildNode(this);	
	}
	public ArrayList<Node> getAllNode()
	{
		return children;
	}
	public Node getChildNode(int index_)
	{
		return children.get(index_);
	}
	public void addChildNode(Node n)
	{
		children.add(n);
		n.parent=this;
	}
	public Node getLastChildNode()
	{
		return children.get(children.size()-1);
	}
	public void setChildNode(int index_, Node n)
	{
		children.set(index_,n);
		n.parent=this;
	}
	// �� node�� leaf���� �Ǻ��ϴ� �Լ�. �Ŀ� ���� ���·� �ٲ� ���� ����.
	public boolean isLeaf()
	{
		return numChildren()==0;
	}
	public void deleteChildNode(int n)
	{
		children.remove(n);
	}
	public void deleteChildNode(Node n)
	{
		children.remove(n);
	}
	
	
	
	
	//// For UI
	public AreaRange ar_current;
	public AreaRange ar_name;
	public AreaRange ar_etc;
	public static int xspace=4;
	public static int xtab=50;
	public static int yspace=4;
	public static int yline=10;
	public static int xchar=8;

}
