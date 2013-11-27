import java.io.Serializable;


public class Definition implements Serializable{
	private Node root;
	public Definition()
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
