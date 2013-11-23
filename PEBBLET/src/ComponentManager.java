
public class ComponentManager {
	private Component[] component;
	public Node search(int[] location)
	{
		Node cur=component[location[0]].getRoot();
		int loop;
		for(loop=1;loop<location.length;loop++)
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
	public Component[] getComponentArray()
	{
		return component;
	}
	public Component getComponent(int index)
	{
		return component[index];
	}
	public void setComponent(int index,Component component_)
	{
		component[index]=component_;
	}
}
