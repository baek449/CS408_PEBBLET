import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class ComponentManager {
	private Component component;
	private ArrayList<String[]> current_variables_and_domain;
	
	public Node search(int[] location)
	{
		Node cur=component.getRoot();
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
	public Component getComponent()
	{
		return component;
	}
	public void setComponent(Component component_)
	{
		component=component_;
	}
	public void fillupSelection(int[] location, boolean isName)
	{
		int[] location_parent = new int[location.length-1];
		int last_location=location[location.length-1];
		boolean isRule = location.length>=4 && component.getRoot().getChildNode(location[0]).getChildNode(location[1]).getChildNode(location[2]).get_node_type()==NodeType.nd_action;

		int loop;
		for(loop=0;loop<location_parent.length-1;loop++)
			location_parent[loop]=location[loop];
		Node n=search(location_parent);
		
		if(isRule)
		{
			RuleManager.fillupSelection(n, last_location, isName);
			return;
		}
		System.err.println("Component: fillupSelection Error");
	}
	
	// Card Type을 선택했을 때 (예시 : Trump) 불려지는 함수. return: 화면에 표시할 내용.
	public ArrayList<String[]> selectCardType(DefinitionManager def, int type_index)
	{
		Node n;
		Node child;
		int loop,loop2;
		
		// 1. UI에 현재 존재하는 카드 목록을 던져서 먼저 표시를 요청한다.
		//cardlist: 해당 type에서 존재하는 카드 목록.
		ArrayList<String> cardlist = new ArrayList<String>();
		n=component.getRoot().getChildNode(type_index);
		for(loop=0;loop<n.numChildren();loop++)
			cardlist.add((String)n.getChildNode(loop).getData());
		UI_show_cardlist(cardlist);
		
		// 2. (변수type, 변수이름, Domain)를 모든 변수에 대해 모아서 리턴해준다. UI는 이를 받아서 화면에 표시할 내용 및 박스 생성에 사용한다.
		n=def.getDefinition().getRoot().getChildNode(3).getChildNode(type_index);
		String[] a;
		current_variables_and_domain.clear();
		for(loop=0;loop<n.numChildren();loop++)
		{
			child=n.getChildNode(loop);
			if (child.get_node_type()==NodeType.nd_action)
			{
				a= new String[2];
				a[0]="Action";
				a[1]=(String)child.getData();
				current_variables_and_domain.add(a);
				continue;
			}
			a=new String[child.numChildren()+2];
			switch (child.get_node_type())
			{
			case nd_str:
				a[0]="String";
				break;
			case nd_num:
				a[0]="Number";
				break;
			default:
				System.err.println("ComponentManager selectCardType error: invalid nodetype");
				return null;
			}
			a[1]=(String)child.getData();
			for(loop2=0;loop2<child.numChildren();loop2++)
				a[loop2+2]=child.getChildNode(loop2).getData().toString();
			current_variables_and_domain.add(a);
		}
		return current_variables_and_domain;
	}
	
	// Card 목록에서 하나를 선택했을 때 (예시 : Spade_A) 불려지는 함수. return: 해당 카드의 변수값 (정수인 경우에는 string화됨).
	public ArrayList<Object> selectCardData(int[] card_address)
	{
		ArrayList<Object> result = new ArrayList<Object>();
		Node n=search(card_address);
		int loop;
		Node child;
		for(loop=1;loop<n.numChildren();loop++)
		{
			child=n.getChildNode(loop);
			switch (child.get_node_type())
			{
			case nd_action:
				result.add(child.getChildNode(0));
				break;
			case nd_num:
			case nd_str:
				result.add(child.getChildNode(0).getData().toString());
			default:
				System.err.println("ComponentManager selectCardData error: invalid nodetype");
			}
		}
		return result;		
	}
	public void addCard(int type)
	{
		Node newnode=new Node(NodeType.nd_card,component.getRoot().getChildNode(type));
		newnode.setData("new_card");
		
		Node _type=new Node(NodeType.nd_str,newnode);
		_type.setData("_type");
		_type.addChildNode(new Node());
		_type.getChildNode(0).setData(newnode.getParent().getData());
		
		int loop;
		Node varnode,varnode2;
		for(loop=0;loop<current_variables_and_domain.size();loop++)
		{
			if(current_variables_and_domain.get(loop)[0].equals("Action"))
			{
				varnode=new Node(NodeType.nd_action,newnode);
				varnode2=new Node(NodeType.nd_action,varnode);
				varnode2.set_scope_card(true);
			}
			else if(current_variables_and_domain.get(loop)[0].equals("String"))
			{
				varnode=new Node(NodeType.nd_str,newnode);
				varnode.addChildNode(new Node());
			}
			else if(current_variables_and_domain.get(loop)[0].equals("Number"))
			{
				varnode=new Node(NodeType.nd_num,newnode);
				varnode.addChildNode(new Node());
			}
			else
			{
				System.err.println("ComponentManager addCard error: invalid nodetype");
				return;
			}
			varnode.setData(current_variables_and_domain.get(loop)[1]);
		}
	}
	
	public void renameCard(int[] address, String newname)
	{
		search(address).setData(newname);
	}

	public void deleteCard(int[] address)
	{
		search(address).getParent().deleteChildNode(address[address.length-1]);
	}
	
	public void setCardData(int[] address, String data)
	{
		Node n=search(address);
		switch(n.getParent().get_node_type())
		{
		case nd_num:
			n.setData(Integer.parseInt(data));
			break;
		case nd_str:
			n.setData(data);
			break;
		default:
			System.err.println("ComponentManager setCardData error: invalid nodetype");
		}
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
