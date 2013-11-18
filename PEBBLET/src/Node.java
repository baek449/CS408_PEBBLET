import java.util.ArrayList;

public class Node {
	public NodeType t;
	public int t_sub;
	private Object data;
	private int numChildren;
	private Node parent;
	private ArrayList<Node> children;
	
	public Node()
	{
		children=new ArrayList<Node>();
	}
	public Node(NodeType t_, Node parent_)
	{
		t_sub=-1;
		t=t_;
		parent=parent_;
		children=new ArrayList<Node>();
		if (parent_!=null) parent_.addChildNode(this);
	}
	public Node(NodeType t_, int subtype, Node parent_)
	{
		t_sub=subtype;
		t=t_;
		parent=parent_;
		children=new ArrayList<Node>();
		if (parent_!=null) parent_.addChildNode(this);
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
		parent.deleteChildNode(this);
		parent=parent_;
		parent.addChildNode(this);	
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
	}
	// New
	public Node getLastChildNode()
	{
		return children.get(children.size()-1);
	}
	public void setChildNode(int index_, Node n)
	{
		children.set(index_,n);
	}
	// New end
	public void deleteChildNode(Node n)
	{
		children.remove(n);
	}
}
