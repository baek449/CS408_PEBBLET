import java.io.Serializable;
import java.util.ArrayList;


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
	public String[][][] getVariableList()
	{
		// First Index - 0:Global, 1:Player, 2:Card
		// Second Index - 0:Int, 1:String, 2:Deck, 3:Player, 4:Action
		
		String[][][] result=new String[3][5][];
		
		ArrayList<String> int_var=new ArrayList<String>();
		ArrayList<String> str_var=new ArrayList<String>();
		ArrayList<String> deck_var=new ArrayList<String>();
		ArrayList<String> player_var=new ArrayList<String>();
		ArrayList<String> action_var=new ArrayList<String>();
		
		int loop;
		
		// Global
		Node n=root.getChildNode(1);
		for(loop=0;loop<n.numChildren();loop++)
		{
			switch(n.getChildNode(loop).get_node_type())
			{
			case nd_num:
				int_var.add((String)n.getChildNode(loop).getData());
				break;
			case nd_str:
				str_var.add((String)n.getChildNode(loop).getData());
				break;
			case nd_deck:
				deck_var.add((String)n.getChildNode(loop).getData());
				break;
			case nd_player:
				player_var.add((String)n.getChildNode(loop).getData());
				break;
			default:
				System.err.println("makeVariableList: Invalid global variable type.");
				break;
			}
		}
		result[0][0]=(String[])int_var.toArray();
		result[0][1]=(String[])str_var.toArray();
		result[0][2]=(String[])deck_var.toArray();
		result[0][3]=(String[])player_var.toArray();
		result[0][4]=new String[0];

		// Player
		int_var.clear();str_var.clear();deck_var.clear();player_var.clear();
		n=root.getChildNode(2);
		for(loop=0;loop<n.numChildren();loop++)
		{
			switch(n.getChildNode(loop).get_node_type())
			{
			case nd_num:
				int_var.add((String)n.getChildNode(loop).getData());
				break;
			case nd_str:
				str_var.add((String)n.getChildNode(loop).getData());
				break;
			case nd_deck:
				deck_var.add((String)n.getChildNode(loop).getData());
				break;
			case nd_player:
				player_var.add((String)n.getChildNode(loop).getData());
				break;
			default:
				System.err.println("makeVariableList: Invalid player variable type.");
				break;
			}
		}
		result[1][0]=(String[])int_var.toArray();
		result[1][1]=(String[])str_var.toArray();
		result[1][2]=(String[])deck_var.toArray();
		result[1][3]=(String[])player_var.toArray();
		result[1][4]=new String[0];

		// Card
		int_var.clear();str_var.clear();deck_var.clear();player_var.clear();
		n=root.getChildNode(3);
		int looptype;
		Node n2;
		for(looptype=0;looptype<n.numChildren();looptype++)
		{
			n2=n.getChildNode(looptype);
			for(loop=0;loop<n2.numChildren();loop++)
			{
				switch(n2.getChildNode(loop).get_node_type())
				{
				case nd_num:
					int_var.add((String)n2.getChildNode(loop).getData());
					break;
				case nd_str:
					str_var.add((String)n2.getChildNode(loop).getData());
					break;
				case nd_action:
					action_var.add((String)n2.getChildNode(loop).getData());
					break;
				default:
					System.err.println("makeVariableList: Invalid card variable type.");
					break;
				}
			}
		}
		result[2][0]=(String[])int_var.toArray();
		result[2][1]=(String[])str_var.toArray();
		result[2][2]=new String[0];
		result[2][3]=new String[0];
		result[2][4]=(String[])action_var.toArray();

		
		return result;
	}
}
