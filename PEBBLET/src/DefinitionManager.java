
public class DefinitionManager {
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
	public Definition getDefinition()
	{
		return definition;
	}
	public void setDefinition(Definition definition_)
	{
		definition=definition_;
	}
}
