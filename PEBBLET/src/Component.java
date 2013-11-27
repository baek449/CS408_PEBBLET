import java.io.Serializable;


public class Component implements Serializable{
	private Node root;
	public Component()
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
