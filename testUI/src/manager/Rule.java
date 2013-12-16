package manager;
import java.io.Serializable;


public class Rule implements Serializable{
	private Node root;
	public Rule()
	{
		root=new Node(NodeType.nd_action, null);
		root.setData(RuleCase.action_multiple);
		
	}
	public Rule(Node root_)
	{
		root=root_;
	}
	public Node getRoot()
	{
		return root;
	}
	public void setRoot(Node root_)
	{
		root=root_;
	}
}
