package manager;
import java.io.Serializable;
import java.util.ArrayList;

import PEBBLET.AreaRange;

public class Node implements Serializable{
	private NodeType node_type;
	private Object data;
	private Node parent;
	private ArrayList<Node> children;
	private boolean scope_player;
	private boolean scope_card;
	
	private int selection_value;
	public int get_selection_value() {return selection_value;}
	public void set_selection_value(int selection_value_) {selection_value=selection_value_;}

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
		if (parent_!=null)
		{
			parent_.addChildNode(this);
			scope_player=parent.scope_player;
			scope_card=parent.scope_card;
		}
	}
	
	public Node copy_except_parent(int additional_copy_depth)
	{
		Node n=new Node();
		n.setData(getData());
		n.set_node_type(get_node_type());
		n.set_scope_player(get_scope_player());
		n.set_scope_card(get_scope_card());
		n.set_selection_value(get_selection_value());
		if(additional_copy_depth==0) return n;
		for(int i = 0 ; i < numChildren(); i++){
			n.addChildNode(getChildNode(i).copy_except_parent(additional_copy_depth-1));
		}
		return n;
	}
	
	// 원래 변수였으나, 함수로 바꿈.
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
	public void addChildNode_front(Node n)
	{
		children.add(0,n);
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
	// 현 node가 leaf인지 판별하는 함수. 후에 변수 형태로 바뀔 수도 있음.
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
	
	public void printAll()
	{
		printAll(0,0);
	}
	
	public void replace(Node _input){
		this.setData(_input.getData());
		this.set_node_type(_input.get_node_type());
		this.set_scope_player(_input.get_scope_player());
		this.set_scope_card(_input.get_scope_card());
		this.set_selection_value(_input.get_selection_value());
		this.remove_all_children();
		for(int i = 0 ; i < _input.numChildren(); i++){
			this.addChildNode(_input.getChildNode(i));
		}
	}
	public void remove_all_children(){
		this.children.clear();
	}
	
	private void printAll(int indent, int child_index)
	{
		int loop;
		for(loop=0;loop<indent;loop++)
			System.out.print("\t");
		System.out.println(child_index+" / NodeType "+node_type+" / "+data + "("+selection_value+")");
		for(loop=0;loop<numChildren();loop++)
		{
			getChildNode(loop).printAll(indent+1,loop);
		}
		
	}
	public String toString()
	{
		if(data==null) return "Null Node";
		return data.toString();
	}

	public boolean equals_except_parent(Node n)
	{
		if(n==null) return false;
		if(get_node_type()!=n.get_node_type()) return false;
		if(get_scope_player()!=n.get_scope_player()) return false;
		if(get_scope_card()!=n.get_scope_card()) return false;
		if(getData()!=n.getData()) return false;
		int k=numChildren();
		if(k!=n.numChildren()) return false;
		for(int loop=0;loop<k;loop++)
		{
			if(!getChildNode(loop).equals_except_parent(n.getChildNode(loop))) return false;
		}
		return true;
	}
	
	//// For UI
	public AreaRange ar_current;
	public AreaRange ar_name;
	public AreaRange ar_etc;

}
