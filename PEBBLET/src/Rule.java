import java.io.Serializable;


public class Rule implements Serializable{
	private Node root;
	public Rule()
	{
		root=null;
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
