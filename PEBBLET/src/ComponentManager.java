import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


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
	public boolean save(ObjectOutputStream out)
	{
		try {
			out.writeObject(component);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	public boolean load(ObjectInputStream in)
	{
		try {
			component=(Component[])in.readObject();
			return true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}

}
