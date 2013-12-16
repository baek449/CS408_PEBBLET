package manager;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class ComponentManager {
	private Component component;
	private Node comp_base;
	
	public ComponentManager()
	{
		component=new Component();
	}
	
	public ComponentManager(DefinitionManager dm)
	{
		make_comp_base(dm);
		component=new Component(comp_base);
	}
	
	public void make_comp_base(DefinitionManager dm)
	{
		Node n=dm.getDefinition().getRoot().getChildNode(3).copy_except_parent(-1);
		comp_base=new Node(NodeType.nd_def_card,null);
		comp_base.setData("Component Base");
		comp_base.addChildNode(n);
		Node d,typ1,typ2;
		Object b;
		for(int loop=0;loop<comp_base.numChildren();loop++)
		{
			d=comp_base.getChildNode(loop);
			for(int loop2=0;loop2<d.numChildren();loop2++)
			{
				typ2=new Node(null,d.getChildNode(loop2));
				b=null;
				switch(d.getChildNode(loop2).get_node_type())
				{
				case nd_num:
					b=1;
					break;
				case nd_str:
					b="";
					break;
				case nd_action:
					b=RuleCase.action_multiple;
					typ2.set_node_type(NodeType.nd_action);
					typ2.set_scope_card(true);
					typ2.set_scope_player(false);
					break;
				}
				typ2.setData(b);
			}
			typ1=new Node(NodeType.nd_str,null);
			typ1.setData("_type");
			typ2=new Node(null,typ1);
			typ2.setData(d.getData());
			d.addChildNode_front(typ1);
		}
	}
	
	public Node search(int[] location)
	{
		Node cur=component.getRoot();
		int loop;
		for(loop=1;loop<location.length;loop++)
		{
			cur=cur.getChildNode(location[loop]);
		}
		return cur;
	}
	public Node make_new_card(String type)
	{
		for(int loop=0;loop<comp_base.numChildren();loop++)
		{
			if(comp_base.getChildNode(loop).equals(type))
			{
				Node result=comp_base.getChildNode(loop).copy_except_parent(-1);
				result.setData("New "+type+"Card");
				result.set_node_type(NodeType.nd_card);
				return result;
			}
		}
		System.err.println("make_new_card : Cannot find card type");
		return null;
	}
	public Component getComponent()
	{
		return component;
	}
	public void setComponent(Component component_)
	{
		component=component_;
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
			component=(Component)in.readObject();
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
