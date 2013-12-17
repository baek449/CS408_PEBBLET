package manager;
import java.io.Serializable;
import java.util.ArrayList;


public class Component implements Serializable{
	private Node root;
	public Component()
	{
		root=new Node();
		root.setData("Component Root");
	}
	public Component(Node comp_base)
	{
		root=comp_base.copy_except_parent(1);
		root.set_node_type(NodeType.nd_def_card);
		root.setData("Component Root");
	}
	public Node getRoot()
	{
		return root;
	}
	public Node getallcards(String typename)
	{
		for(int loop=0;loop<root.numChildren();loop++)
			if (root.getChildNode(loop).toString().equals(typename))
				return root.getChildNode(loop);
		System.err.println("Cannot load cards of specific type : "+typename);
		return new Node();
	}
	public void setRoot(Node root_)
	{
		root=root_;
	}
	public ArrayList<Node> get_card_type_list()
	{
		return root.getAllNode();
	}
	public ArrayList<Node> get_card_list(Node type)
	{
		return type.getAllNode();
	}
	public ArrayList<Node> get_var_list(Node card)
	{
		return card.getAllNode();
	}
}
