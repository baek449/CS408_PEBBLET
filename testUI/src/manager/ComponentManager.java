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
				typ1=d.getChildNode(loop2);
				typ2=new Node();
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
					typ2.set_scope_player(true);
					break;
				}
				typ2.setData(b);
				typ1.addChildNode_front(typ2);
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
			if(comp_base.getChildNode(loop).getData().equals(type))
			{
				Node result=comp_base.getChildNode(loop).copy_except_parent(-1);
				result.setData("New "+type+"Card");
				result.set_node_type(NodeType.nd_card);
				return result;
			}
		}
		System.err.println("make_new_card : Cannot find card type - input: "+type);
		comp_base.printAll();
		return null;
	}
	public void delete_card(Node n)
	{
		Node p=n.getParent();
		if(p!=null) p.deleteChildNode(n);
	}
	public void update_dm_and_refresh_cards(DefinitionManager dm)
	{
		Node previous_comp_base=comp_base;
		make_comp_base(dm);
		int type_loop,search_loop;
		Node t,new_cb;
		String tname;
		Node n__;
		System.out.println("Before:");
		component.getRoot().printAll();
		if(previous_comp_base==null)
		{
			System.out.println("previous_comp_base==null");
			for(type_loop=0;type_loop<comp_base.numChildren();type_loop++)
			{
				// 3. 새로 만든 comp_Base를 보고 생성한다.
				tname=(String)comp_base.getChildNode(type_loop).getData();
				if(tname==null) continue;
				n__=new Node(NodeType.nd_def_card,component.getRoot());
				n__.setData(tname);
			}
			System.out.println("After:");
			component.getRoot().printAll();
			return;
		}
		for(type_loop=0;type_loop<previous_comp_base.numChildren();type_loop++)
		{
			tname=(String)previous_comp_base.getChildNode(type_loop).getData();
			if(tname==null) continue;
			t=component.getallcards(tname);
			new_cb=null;
			for(search_loop=0;search_loop<comp_base.numChildren();search_loop++)
			{
				if(tname.equals(comp_base.getChildNode(search_loop).getData().toString()))
				{
					new_cb=comp_base.getChildNode(search_loop);
					break;
				}
			}
			
			// 이전 comp_base를 보고
			// 1. 새로 만든 comp_base에 없으면 component에 들어가서 모든 카드를 날리고 해당 type까지 날린다.
			if(new_cb==null)
			{
				t.getParent().deleteChildNode(t);
				continue;
			}
			new_cb.printAll();previous_comp_base.getChildNode(type_loop).printAll();
			// 2. 새로 만든 comp_base에 있지만 내용이 바뀌었으면 component에 들어가서 모든 카드를 날리고 새로운카드로 대체한다.
			if(!new_cb.equals_except_parent(previous_comp_base.getChildNode(type_loop)))
			{
				String cname;
				for(search_loop=0;search_loop<t.numChildren();search_loop++)
				{
					System.out.println("Replacing");
					t.getChildNode(search_loop).printAll();
					cname=(String)t.getChildNode(search_loop).getData();
					t.getChildNode(search_loop).replace(make_new_card(tname));
					t.getChildNode(search_loop).setData(cname);
					System.out.println("to");
					t.getChildNode(search_loop).printAll();
				}
				continue;
			}
		}
		for(type_loop=0;type_loop<comp_base.numChildren();type_loop++)
		{
			System.out.println("Making");
			// 3. 새로 만든 comp_Base를 보고 이전에 없는 것이면 생성한다.
			tname=(String)comp_base.getChildNode(type_loop).getData();
			if(tname==null) continue;
			new_cb=null;
			for(search_loop=0;search_loop<previous_comp_base.numChildren();search_loop++)
			{
				if(tname.equals(previous_comp_base.getChildNode(search_loop).getData().toString()))
				{
					new_cb=previous_comp_base.getChildNode(search_loop);
					break;
				}
			}
			if(new_cb==null)
			{
				n__=new Node(NodeType.nd_def_card,component.getRoot());
				n__.setData(tname);
			}
		}
		System.out.println("After:");
		component.getRoot().printAll();
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
