package manager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Stack;
import java.util.Random;

public class TestplayModule {
	
	///////////////////// ������ ��� �ִ� ������ /////////////////////
	// �÷��̾� �ڸ�
	private ArrayList<Integer> player_seats;
	// ���������� ������, �ε��� ��.
	private HashMap<String,Integer> global_variable_index;
	// �÷��̾ ������ ������, �ε��� ��.
	private HashMap<String,Integer> player_variable_index;
	// ������ ���� �� ���. ù ��° �ε����� �÷��̾� ��ȣ(0�̸� ����), �� ��° �ε����� dictionary���� ã�� ������ �ε��� ��.
	// ����, ���ڿ�, �Ǵ� Node (Deck�� ���)�� ����ִ�.
	private Object[][] variables;
	
	// ����
	// global_variable_index : [center:0 discard:1]
	// player_variable_index : [hand:0 score:1 current:2]
	// �� ���, variables�� ������ ���� ����ȴ�.
	// [[gl_center gl_discard] [p1_hand p1_score pl_current] [p2_hand p2_score p2_current]...]
	/////////////////////////////////////////////////////////////
	
	private Node def;
	private Stack<Integer> playerStack;
	private Stack<Node> cardStack;
	private Node actionFunc;
	private Node eventFunc;
	private boolean logging;
	private TestLog testlog;
	
	private Random r;
	
	// Constructor
	public TestplayModule()
	{
		r=new Random(System.currentTimeMillis());
		playerStack=new Stack<Integer>();
		cardStack=new Stack<Node>();
		logging=false;
	}
	public TestplayModule(Definition d)
	{
		r=new Random(System.currentTimeMillis());
		playerStack=new Stack<Integer>();
		cardStack=new Stack<Node>();
		logging=false;
		
		// 1. �÷��̾� ������ ���� player_seats�� �ʱ�ȭ�Ѵ�.
		int n=(Integer)d.getRoot().getChildNode(0).getChildNode(0).getData();
		player_seats=new ArrayList<Integer>();
		int loop;
		for(loop=0;loop<n;loop++)
			player_seats.add((Integer)loop+1);
		
		// 2. Definition�� ���� variable index dictionary�� �����ϰ� variables�� �ʱ�ȭ�Ѵ�.
		ArrayList<Node> global_var=d.getRoot().getChildNode(1).getAllNode();
		ArrayList<Node> player_var=d.getRoot().getChildNode(2).getAllNode();
		
		// dictionary ����
		global_variable_index = new HashMap<String,Integer>();
		player_variable_index = new HashMap<String,Integer>();
		
		// variables �ʱ�ȭ
		int index,player_loop;
		variables=new Object[n+1][];
		variables[0]=new Object[global_var.size()];
		for(index=1;index<=n;index++)
			variables[index]=new Object[player_var.size()];
		
		for(index=0;index<global_var.size();index++)
		{
			global_variable_index.put((String)global_var.get(index).getData(), index);
			switch(global_var.get(index).get_node_type())
			{
			case nd_num:
				variables[0][index]=new Integer(0);
				break;
			case nd_str:
				variables[0][index]=new String();
				break;
			case nd_deck:
				variables[0][index]=new Node();
				break;
			case nd_player:
				variables[0][index]=new Integer(-1);
				break;
			default:
				// TODO: Error
				break;
			}
			
		}
		for(index=0;index<player_var.size();index++)
		{
			player_variable_index.put((String)player_var.get(index).getData(), index);
			for(player_loop=1;player_loop<=n;player_loop++)
			{
				switch(global_var.get(index).get_node_type())
				{
				case nd_num:
					variables[player_loop][index]=new Integer(0);
					break;
				case nd_str:
					variables[player_loop][index]=new String();
					break;
				case nd_deck:
					variables[player_loop][index]=new Node(NodeType.nd_deck,null);
					break;
				case nd_player:
					variables[player_loop][index]=new Integer(-1);
					break;
				default:
					// TODO: Error
					break;
				}
			}
		}
	}
	
	// ***
	public int endTestplay()
	{
		// Stops test play and return error code.
		return 0;
	}
	
	public void startRecord()
	{
		logging=true;
	}
	public void endRecord()
	{
		logging=false;
	}
	
	//////////////////////// Total Results //////////////////////
	// Node ���·� ���� �� interpret�Ѵ�.
	
	// TODO: Implement this.
	
	// �׼� ��Ģ�� �����Ѵ�.
	public void action(Node n)
	{
		switch((RuleCase)n.getData())
		{
		case action_multiple:
			action_multiple(n);
			return;
		case action_move:
			action_move(n.getChildNode(0),n.getChildNode(1));
			return;
		case action_load:
			action_load((String)n.getChildNode(0).getChildNode(0).getData(),n.getChildNode(1));
			return;
		case action_shuffle:
			action_shuffle(n.getChildNode(0));
			return;
		case action_order:
			action_order(n.getChildNode(0),(Node[])n.getAllNode().subList(1,n.numChildren()).toArray());
			return;
		case action_act:
			action_act(n.getChildNode(0), n.getChildNode(1));
			return;
		case action_choose:
			System.err.println("Unimplemented action_choose");
			return;
		case action_card:
			action_card(n.getChildNode(0), (String)n.getChildNode(1).getData());
			return;
		case action_if:
			action_if(n.getChildNode(0), n.getChildNode(1));
			return;
		case action_ifelse:
			action_ifelse(n.getChildNode(0), n.getChildNode(1), n.getChildNode(2));
			return;
		case action_repeat:
			action_repeat(n.getChildNode(0), n.getChildNode(1));
			return;
		case action_endgame:
			System.err.println("Unimplemented action_endgame");
			return;
		case action_endgame_draw:
			System.err.println("Unimplemented action_endgame_draw");
			return;
		case action_endgame_order:
			System.err.println("Unimplemented action_endgame_order");
			return;
		case action_show:
			System.err.println("Unimplemented action_show");
			return;
		}
		System.err.println("Action Error");
	}
	
	// �÷��̾��� ����� ����Ѵ�.
	public ArrayList<Integer> players(Node n)
	{
		if(n.getData().getClass()==RuleCase.class)
		{
			switch((RuleCase)n.getData())
			{
			case player_multiple:
				return player_multiple(n.getAllNode());
			case player_current:
				return player_current();
			case player_all:
				return player_all();
			case player_exclude:
				return player_exclude(n.getChildNode(0),n.getChildNode(1));
			case player_left:
				return player_left(false, n.getChildNode(0),n.getChildNode(1));
			case player_right:
				return player_right(false, n.getChildNode(0),n.getChildNode(1));
			case player_left_all:
				return player_left(true, n.getChildNode(0),n.getChildNode(1));
			case player_right_all:
				return player_right(true, n.getChildNode(0),n.getChildNode(1));
			case player_satisfy:
				return player_satisfy(n.getChildNode(0),n.getChildNode(1));
			case player_most:
				return player_most(n.getChildNode(0),(Node[])n.getAllNode().subList(1,n.numChildren()).toArray());
			case player_select:
				System.err.println("Unimplemented player_select");
				return null;
			}
		}
		else if (n.getData().getClass()==String.class)
		{
			Integer result = (Integer)predefined((String)n.getData());
			ArrayList<Integer> result_=new ArrayList<Integer>();
			result_.add(result);
			return result_;
		}
		
		System.out.println("Player Error");
		return null;
	}
	
	// Deck�� ����Ѵ�.
	public Node deck(Node n)
	{
		if(n.getData().getClass()==RuleCase.class)
		{
			switch((RuleCase)n.getData())
			{
			case deck_player:
				ArrayList<Integer> p=players(n.getChildNode(1));
				// TODO: Error Checking: p.size() must be 1.
				return (Node)player_variable_get((String)n.getChildNode(0).getData(),p.get(0));
			case deck_select:
				System.err.println("Unimplemented deck_select");
				return null;
			}
		}
		else if (n.getData().getClass()==String.class)
			return deck_predefined((String)n.getData());
		
		// TODO: Error
		System.out.println("Deck Error");
		return null;
	}
	
	// ī���� ����� ����Ѵ�.
	public ArrayList<Node> cards(Node n)
	{
		if(n.getData().getClass()==RuleCase.class)
		{
			switch((RuleCase)n.getData())
			{
			case card_all:
				return cards_all(n.getChildNode(0));
			case card_top:
				return cards_top(n.getChildNode(0),n.getChildNode(1));
			case card_bottom:
				return cards_bottom(n.getChildNode(0),n.getChildNode(1));
			case card_satisfy:
				return cards_satisfy(n.getChildNode(0),n.getChildNode(1));
			case card_select:
				System.err.println("Unimplemented card_select");
				return null;
			}
		}
		System.out.println("Card Error");
		return null;
	}
	
	// ������ ����Ѵ�.
	public boolean cond(Node n)
	{	
		if(n.getData().getClass()==RuleCase.class)
		{
			switch((RuleCase)n.getData())
			{
			case cond_numcompare:
				return cond_numcompare(n.getChildNode(0),(String)n.getChildNode(1).getData(),n.getChildNode(2));
			case cond_samecard:
				return cond_samecard(n.getChildNode(0));
			case cond_sameplayer:
				return cond_sameplayer(n.getChildNode(0));
			case cond_samestring:
				return cond_samestring(n.getChildNode(0), n.getChildNode(1));
			case cond_typeequal:
				return cond_typeequal(n.getChildNode(0));
			case cond_istype:
				return cond_istype(n.getChildNode(0),n.getChildNode(1));
			case cond_and:
				return cond_and(n.getChildNode(0),n.getChildNode(1));
			case cond_or:
				return cond_or(n.getChildNode(0),n.getChildNode(1));
			case cond_not:
				return cond_not(n.getChildNode(0));
			case cond_emptydeck:
				return cond_emptydeck(n.getChildNode(0));
			}
		}
		System.out.println("Cond Error");
		return false;
	}
	
	// ��ġ�� ����Ѵ�.
	public int num(Node n)
	{
		if(n.getData().getClass()==RuleCase.class)
		{
			switch((RuleCase)n.getData())
			{
			case num_size_player:
				return num_size_player(n.getChildNode(0));
			case num_size_deck:
				return num_size_deck(n.getChildNode(0));
			case num_size_card:
				return num_size_cards(n.getChildNode(0));
			case num_operation:
				return num_operation(n.getChildNode(0),(String)n.getChildNode(1).getData(),n.getChildNode(2));
			case num_call:
				System.err.println("Unimplemented num_call");
				return 0;
			}
		}
		else if (n.getData().getClass()==String.class)
		{
			Integer i=(Integer)predefined((String)n.getData());
			if (i!=null) return i;
			return (Integer)card_predefined((String)n.getData());
		}
		else if (n.getData().getClass()==Integer.class)
			return (Integer)n.getData();
		
		System.out.println("num Error");
		return 0;
	}
	
	// ���ڸ� ����Ѵ�.
	public String str(Node n)
	{
		if(n.getData().getClass()==RuleCase.class)
		{
			switch((RuleCase)n.getData())
			{
			case string_raw:
				return (String)n.getChildNode(0).getData();
			case string_call:
				System.err.println("Unimplemented string_call");
				return null;
			}
		}
		else if (n.getData().getClass()==String.class)
		{
			String i=(String)predefined((String)n.getData());
			if (i!=null) return i;
			return (String)card_predefined((String)n.getData());
		}
		
		System.out.println("String Error");
		return null;
	}
	
	
	///////////////////////// ACTION EVALUATION FUNCTIONS ///////////////////////
	// Node�� �����ϴ� �׼��� �����Ѵ�.
	// ���� : void
	
	// card_raw�� �����ϴ� ī�带 deck_raw�� �����δ�.
	public void action_move(Node card_raw, Node deck_raw)
	{
		ArrayList<Node> card_=cards(card_raw);
		Node deck_=deck(deck_raw);
		int loop;
		for(loop=0;loop<card_.size();loop++)
		{
			card_.get(loop).setParent(deck_);
		}
	}
	
	// �־��� ���Ͽ��� ī�带 �ҷ��� deck_raw�� �ִ´�.
	public void action_load(String file, Node deck_raw)
	{
		// TODO: ����� dummy card 10���� ����־� �׽�Ʈ�ϴ� ����
		Node deck_=deck(deck_raw);
		Node temp;
		for(int loop=0;loop<10;loop++)
		{
			temp=new Node(NodeType.nd_card,deck_);
			temp.setData(loop);
		}
	}
	
	// deck_raw�� �����ϴ� ���� ���´�.
	public void action_shuffle(Node deck_raw)
	{
		Node deck_=deck(deck_raw);
		Node temp;
		int loop, randloc;
		int numCards=deck_.numChildren();
		for (loop=0;loop<numCards;loop++)
		{
			randloc=r.nextInt(numCards);
			if(loop!=randloc)
			{
				temp=deck_.getChildNode(loop);
				deck_.setChildNode(loop, deck_.getChildNode(randloc));
				deck_.setChildNode(randloc, temp);
			}
		}
	}
	
	// crit�� �����ϴ� ���ǿ� ���� deck_raw�� �����ϴ� ���� ī�带 �����Ѵ�.
	public void action_order(Node deck_raw, final Node[] crit)
	{
		Node d=deck(deck_raw);
		// TODO: �ǽɰ��� �κ�. deck�� �޾ƿͼ� �����ϸ� ���� variable�� ����ִ� node�� ���ĵǴ°�.
		Collections.sort(d.getAllNode(), new Comparator<Node>() {
		    public int compare(Node a, Node b) {
		    	int loop;
		    	int a_value, b_value;
		    	for(loop=0;loop<crit.length;loop++)
		    	{
		    		// ���� ��ġ�� ���Ѵ�.
		    		cardStack.push(a);
		    		a_value=num(crit[loop].getChildNode(0));
		    		cardStack.pop();
		    		cardStack.push(b);
		    		b_value=num(crit[loop].getChildNode(0));
		    		cardStack.pop();
		    		
		    		// ������, ���� ���ǿ��� ���Ѵ�.
		    		if(a_value==b_value) continue;
		    		// ũ�鼭 high, �����鼭 low�̸� 1
		    		if(a_value>b_value && ((String)crit[loop].getData()).equals("High")) return 1;
		    		if(a_value<b_value && ((String)crit[loop].getData()).equals("Low")) return 1;
		    		return -1;
		    	}
		    	// ������ ����.
		    	return 0;
		    }
		});
	}
	
	// player_raw�� �����ϴ� �÷��̾�� action_raw�� �׼��� �����Ѵ�.
	public void action_act(Node players_raw, Node action_raw)
	{
		ArrayList<Integer> players_=players(players_raw);
		int loop;
		// �� �÷��̾�� �ݺ�
		for(loop=0;loop<players_.size();loop++)
		{
			// �÷��̾ stack�� �ְ� �׼��� ������ ���� ����.
			playerStack.push(players_.get(loop));
			action(action_raw);
			playerStack.pop();
		}
	}
	
	// action_raw�� �ڽ� ���� ������ �ִ� �ټ��� �׼��� �����Ѵ�. 
	public void action_multiple(Node action_raw)
	{
		ArrayList<Node> n=action_raw.getAllNode();
		int loop;
		for(loop=0;loop<n.size();loop++)
			action(n.get(loop));
	}
	
	// condition_raw�� �����ϴ� ������ �����Ǹ� action_raw�� �����Ѵ�.
	public void action_if(Node condition_raw, Node action_raw)
	{
		boolean condition=cond(condition_raw);
		if(condition) action(action_raw);
	}
	// condition_raw�� �����ϴ� ������ �����Ǹ� action_raw��, �׷��� ������ elseaction_raw�� �����Ѵ�.
	public void action_ifelse(Node condition_raw, Node action_raw, Node elseaction_raw)
	{
		boolean condition=cond(condition_raw);
		if(condition) action(action_raw);
		else action(elseaction_raw);
	}
	
	// n_raw�� �����ϴ� ���ڸ�ŭ  action_raw �׼��� �ݺ� �����Ѵ�.
	public void action_repeat(Node n_raw, Node action_raw)
	{
		int n=num(n_raw);
		int loop;
		for(loop=0;loop<n;loop++)
			action(action_raw);
	}
	
	public void action_card(Node c_raw, String varname)
	{
		ArrayList<Node> c=cards(c_raw);
		Node target;
		for(int loop=0;loop<c.size();loop++)
		{
			target=(Node)card_variable_get(varname,c.get(loop));
			cardStack.push(c.get(loop));
			action(target);
			cardStack.pop();
		}
	}
	// �̸� ������ �׼��� �����Ѵ�.
	public void action_func(String name, String[] args)
	{
		// TODO: Action Function
	}
	public void action_endgame(boolean win, Node players)
	{
		// TODO: End Game
	}
	public void action_endgame_draw()
	{
		// TODO: End Draw Game
	}
	public void action_endgame_order(Node[] order)
	{
		// TODO: End game with order
	}
	public void select_action(String[] options, Node[] actions)
	{
		// TODO: UI�� ��� ������ �䱸�Ѵ�.
	}
	
	
	
	
	///////////////////////// PLAYER EVALUATION FUNCTIONS ///////////////////////
	// Node�� ����Ͽ� �÷��̾ �����ش�.
	// ���� : ArrayList<Integer>
	
	// player_multiple : ���� �÷��̾��� ����� �ռ��Ͽ� �����ش�.
	public ArrayList<Integer> player_multiple(ArrayList<Node> p)
	{
		ArrayList<Integer> a=new ArrayList<Integer>();
		int loop,loop2;
		for(loop=0;loop<p.size();loop++)
		{
			// child ������ evaluate�Ѵ�.
			ArrayList<Integer> tmplist=players(p.get(loop));
			
			// evaluate�� child���� �ߺ��� �ƴ� ���Ҹ� ������Ų��.
			for(loop2=0;loop2<tmplist.size();loop2++)
			{
				int elem = tmplist.get(loop2);
				if(!a.contains(elem))
					a.add(elem);
			}
		}
		return a;
	}
	
	// player_all : ���� �÷��̾�, �� stack�� �� ���� �ִ� �÷��̾ �����ش�.
	public ArrayList<Integer> player_current()
	{
		ArrayList<Integer> l=new ArrayList<Integer>();
		l.add(playerStack.peek());
		return l;
	}
	
	// player_all : ��ü �÷��̾��� ����� �ڸ� ������� �����ش�.
	public ArrayList<Integer> player_all()
	{
		return player_seats;
	}
	
	// player_exclude : domain_raw�� �÷��̾�� excluded_raw�� �÷��̾ ������ ����� �����ش�.
	public ArrayList<Integer> player_exclude(Node excluded_raw, Node domain_raw)
	{
		ArrayList<Integer> excluded = players(excluded_raw);
		ArrayList<Integer> domain = players(domain_raw);
		int loop;
		for(loop=0;loop<excluded.size();loop++)
			domain.remove(excluded.get(loop));
		return domain;
	}
	
	// �÷��̾� �ε����� �����ϴ� �Լ�.
	public int player_index_arrange(int i)
	{
		int n=player_seats.size();
		if(i>n) return i%n;
		if(i<0) return (i%n)+n;
		return i;
	}

	// player_raw ������ n_raw ��° �����(all=false), �Ǵ� n_raw���� �����(all=true)
	public ArrayList<Integer> player_left(boolean all, Node n_raw, Node player_raw)
	{
		ArrayList<Integer> p = players(player_raw);
		int n = num(n_raw);
		ArrayList<Integer> result = new ArrayList<Integer>();
		int loop,loop2,index;
		Integer player2insert;
		for(loop=0;loop<p.size();loop++)
		{
			index=player_seats.indexOf(p.get(loop));
			player2insert=player_seats.get(player_index_arrange(index-n));
			// ���� n��° ���
			if(result.contains(player2insert))
				result.add(player2insert);
			// all�� ���� ���: ���� n-1��°���� 1��°������ ���
			if (all)
				for(loop2=index-1;loop2>0;loop2--)
					result.add(player_seats.get(player_index_arrange(index-loop2)));
		}
		return result;
	}
	// player_raw �������� n_raw ��° �����(all=false), �Ǵ� n_raw���� �����(all=true)
	public ArrayList<Integer> player_right(boolean all, Node n_raw, Node player_raw)
	{
		ArrayList<Integer> p = players(player_raw);
		int n = num(n_raw);
		ArrayList<Integer> result = new ArrayList<Integer>();
		int loop,loop2,index;
		Integer player2insert;
		for(loop=0;loop<p.size();loop++)
		{
			index=player_seats.indexOf(p.get(loop));
			player2insert=player_seats.get(player_index_arrange(index+n));
			// ������ n��° ���
			if(result.contains(player2insert))
				result.add(player2insert);
			// all�� ���� ���: ������ n-1��°���� 1��°������ ���
			if (all)
				for(loop2=index-1;loop2>0;loop2--)
					result.add(player_seats.get(player_index_arrange(index+loop2)));
		}
		return result;
	}
	
	// players_raw �߿��� condition_raw ������ �����ϴ� �÷��̾��� ����� �����ش�.
	public ArrayList<Integer> player_satisfy (Node condition_raw, Node players_raw)
	{
		ArrayList<Integer> p = players(players_raw);
		int loop;
		boolean b;
		for(loop=0;loop<p.size();loop++)
		{
			// player�� stack�� �÷� ������ ���Ѵ�.
			playerStack.push(p.get(loop));
			b=cond(condition_raw);
			playerStack.pop();
			
			// ������ �����̸� ���ش�.
			if (!b)
			{
				p.remove(loop);
				loop--;
			}
		}
		return p;
	}
	
	// players_raw�� order ���ǿ� ���� �����Ѵ�
	public ArrayList<Integer> player_most(Node players_raw, final Node[] order)
	{
		ArrayList<Integer> p=players(players_raw);
		Collections.sort(p, new Comparator<Integer>() {
		    public int compare(Integer a, Integer b) {
		    	int loop;
		    	int a_value, b_value;
		    	for(loop=0;loop<order.length;loop++)
		    	{
		    		// ���� ��ġ�� ���Ѵ�.
		    		playerStack.push(a);
		    		a_value=num(order[loop].getChildNode(0));
		    		playerStack.pop();
		    		playerStack.push(b);
		    		b_value=num(order[loop].getChildNode(0));
		    		playerStack.pop();
		    		
		    		// ������, ���� ���ǿ��� ���Ѵ�.
		    		if(a_value==b_value) continue;
		    		// ũ�鼭 high, �����鼭 low�̸� 1
		    		if(a_value>b_value && ((String)order[loop].getData()).equals("High")) return 1;
		    		if(a_value<b_value && ((String)order[loop].getData()).equals("Low")) return 1;
		    		return -1;
		    	}
		    	// ������ ����.
		    	return 0;
		    }
		});
		return p;
	}
	public ArrayList<Integer> player_owner(Node[] cards)
	{
		// TODO: ���� �ȵ�
		return null;
	}
	
	public ArrayList<Integer> select_player(int n, Node players_raw)
	{
		// TODO: UI�� ��� ������ �䱸�Ѵ�.
		return null;
	}
	

	
	
	///////////////////////// DECK EVALUATION FUNCTIONS ///////////////////////
	// Node�� ����Ͽ� ���� �����ش�.
	// ���� : Node
	
	// Ư�� �÷��̾��� ��
	public Node deck_get(String name, Node player_raw)
	{
		// �÷��̾ ����Ѵ�.
		ArrayList<Integer> p = players(player_raw);
		// TODO: 1���� �ƴϸ� ������ ����.
		// �÷��̾��� ���� �ҷ��´�.
		return (Node)variables[p.get(0)][player_variable_index.get(name)];
	}
	
	// �̹� ���ǵ� ��
	public Node deck_predefined(String name)
	{
		// ���� ������ ������ �ҷ��´�.
		Integer index = global_variable_index.get(name);
		if (index!=null)
			return (Node)variables[0][index];
		// �÷��̾� ������ ������ ���� �÷��̾� �������� �ҷ��´�.
		index = player_variable_index.get(name);
		if (index!=null)
			return (Node)variables[playerStack.peek()][index];
		// TODO: ���� �˻� ����. ���� ó�� �ʿ�.
		return null;
	}
	
	// ���� �� �� ����
	public Node select_deck(Node[] decks)
	{
		// TODO: UI�� ��� ������ �䱸�Ѵ�.
		return null;
	}
	
	///////////////////////// CARD EVALUATION FUNCTIONS ///////////////////////
	// Node�� ����Ͽ� ī�� ����� �����ش�.
	// ���� : ArrayList<Node>
	
	// Ư�� ���� ī��
	public ArrayList<Node> cards_all(Node deck_raw)
	{
		return deck(deck_raw).getAllNode();
	}
	
	// �� ���� n��
	public ArrayList<Node> cards_top(Node n_raw, Node deck_raw)
	{
		int n=num(n_raw);
		ArrayList<Node> total=deck(deck_raw).getAllNode();
		// TODO: ���� n�庸�� ũ�� ���� ó��
		return new ArrayList<Node>(total.subList(total.size()-n, total.size()));
	}
	// �� �Ʒ��� n��
	public ArrayList<Node> cards_bottom(Node n_raw, Node deck_raw)
	{
		int n=num(n_raw);
		ArrayList<Node> total=deck(deck_raw).getAllNode();
		// TODO: ���� n�庸�� ũ�� ���� ó��
		return new ArrayList<Node>(total.subList(0, n));
	}
	// �� ���� n��
	public ArrayList<Node> cards_random(Node n_raw, Node deck_raw)
	{
		int n=num(n_raw);
		ArrayList<Node> total=deck(deck_raw).getAllNode();
		// TODO: ���� n�庸�� ũ�� ���� ó��
		// TODO: Not Implemented.
		return null;
	}
	
	public ArrayList<Node> cards_satisfy(Node condition_raw, Node cards_raw)
	{
		ArrayList<Node> c = cards(cards_raw);
		ArrayList<Node> result=new ArrayList<Node>();
		boolean b;
		int loop;
		for(loop=0;loop<c.size();loop++)
		{
			cardStack.push(c.get(loop));
			b=cond(condition_raw);
			cardStack.pop();
			if(b)
			{
				result.add(c.get(loop));
				c.remove(loop);
				loop--;
			}
		}
		return result;
	}
	public ArrayList<Node> select_card(Node n_raw, Node deck)
	{
		// TODO: UI�� ��� ������ �䱸�Ѵ�.
		return null;
	}

	
	///////////////////////// CONDITION EVALUATION FUNCTIONS ///////////////////////
	// Node�� ����Ͽ� �ش��ϴ� bool����  �����ش�.
	// ���� :boolean

	public boolean cond_numcompare(Node n1_raw, String comparator, Node n2_raw)
	{
		if(comparator.equals("=="))
			return num(n1_raw)==num(n2_raw);
		else if(comparator.equals("!="))
			return num(n1_raw)!=num(n2_raw);
		else if(comparator.equals(">"))
			return num(n1_raw)>num(n2_raw);
		else if(comparator.equals("<"))
			return num(n1_raw)<num(n2_raw);
		else if(comparator.equals(">="))
			return num(n1_raw)>=num(n2_raw);
		else if(comparator.equals("<="))
			return num(n1_raw)<=num(n2_raw);
		
		// TODO: �߸��� �� ������ ���� ó��
		return false;
	}
	public boolean cond_samecard(Node card_raw)
	{
		ArrayList<Node> card=cards(card_raw);
		if(card.size()==0)
			// TODO: ī�尡 ���� ��� ���ٰ� ���ƾ� �ϴ°�?
			return true;
		int loop;
		String cardname=(String)card.get(0).getData();
		for(loop=1;loop<card.size();loop++)
			if(!cardname.equals((String)card.get(loop).getData()))
				return false;
		return true;
	}
	
	public boolean cond_sameplayer(Node player_raw)
	{
		ArrayList<Integer> player=players(player_raw);
		if(player.size()==0)
			// TODO: �÷��̾ ���� ��� ���ٰ� ���ƾ� �ϴ°�?
			return true;
		int loop;
		Integer playername=player.get(0);
		for(loop=1;loop<player.size();loop++)
			if(!playername.equals(player.get(loop)))
				return false;
		return true;
	}
	
	public boolean cond_samestring(Node s1_raw, Node s2_raw)
	{
		return str(s1_raw).equals(str(s2_raw));
	}
	
	public boolean cond_typeequal(Node card_raw)
	{
		ArrayList<Node> card = cards(card_raw);
		if(card.size()==0)
			// TODO: ī�尡 ���� ��� ���ٰ� ���ƾ� �ϴ°�?
			return true;
		int loop;
		// TODO: _type�� ������ 0�� �ε������ ����.
		String cardname=(String)card.get(0).getChildNode(0).getChildNode(0).getData();
		for(loop=1;loop<card.size();loop++)
			if(!cardname.equals((String)card.get(loop).getChildNode(0).getChildNode(0).getData()))
				return false;
		return true;
	}
	
	public boolean cond_istype(Node card_raw, Node type_raw)
	{
		ArrayList<Node> card = cards(card_raw);
		String t=str(type_raw);
		int loop;
		// TODO: _type�� ������ 0�� �ε������ ����.
		for(loop=0;loop<card.size();loop++)
			if(!t.equals((String)card.get(loop).getChildNode(0).getChildNode(0).getData()))
				return false;
		return true;
	}
	
	public boolean cond_and(Node c1_raw, Node c2_raw)
	{
		return cond(c1_raw) && cond(c2_raw);
	}
	public boolean cond_or(Node c1_raw, Node c2_raw)
	{
		return cond(c1_raw) || cond(c2_raw);
	}
	public boolean cond_not(Node c1_raw)
	{
		return !cond(c1_raw);
	}
	public boolean cond_emptydeck(Node d_raw)
	{
		Node d=deck(d_raw);
		return d.numChildren()==0;
	}
	
	///////////////////////// NUMBER EVALUATION FUNCTIONS ///////////////////////
	// Node�� ����Ͽ� �ش��ϴ� int����  �����ش�.
	// ���� :int

	//�÷��̾� ��
	public int num_size_player(Node players_raw)
	{
		ArrayList<Integer> p = players(players_raw);
		return p.size();
	}
	//�� ũ�� 
	public int num_size_deck(Node deck_raw)
	{
		Node d = deck(deck_raw);
		return d.numChildren();
	}
	//ī�� �� 
	public int num_size_cards(Node cards_raw)
	{
		ArrayList<Node> c = cards(cards_raw);
		return c.size();
	}
	//�÷��̾��� Ư�� ����(����)
	public int num_get_player(String name, Node player_raw)
	{
		// �÷��̾ ����Ѵ�.
				ArrayList<Integer> p = players(player_raw);
				// TODO: 1���� �ƴϸ� ������ ����.
				// �÷��̾��� �ش��ϴ� ���� �ҷ��´�.
				return (Integer)variables[p.get(0)][player_variable_index.get(name)];
	}
	//ī���� Ư�� ����(����)
	public int num_get_card(String name, Node card_raw)
	{
		// ī�带 ����Ѵ�.
		ArrayList<Node> c = cards(card_raw);
		// TODO: 1���� �ƴϸ� ������ ����.
		Node temp_card = c.get(0);
		int loop;
		for(loop = 0; loop<temp_card.numChildren(); loop++)
		{
			if(name.equals(temp_card.getChildNode(loop).getData()))
			{
				return (Integer)(temp_card.getChildNode(loop).getChildNode(0).getData());
			}
		}
		
		// TODO: ī���� Ư�� attribute�� �����´�.
		
		return 0;
	}
	//���� ���� ���(��Ģ����)
	public int num_operation(Node num1, String op, Node num2)
	{
		//TODO: ��Ģ����.
		return 0;
	}
	//�÷��̾ ���� ����
	public int call_num(Node min, Node max)
	{
		// TODO: �÷��̾�� min�� max������ �� �� �����ϵ��� �ϴ� UI�� ����.
		return 0;
	}
	
	///////////////////////// STRING EVALUATION FUNCTIONS ///////////////////////
	// Node�� ����Ͽ� �ش��ϴ� String����  �����ش�.
	// ���� :String

	//�÷��̾��� Ư�� ��
	public String string_get_player(String name, Node player_raw)
	{
		// �÷��̾ ����Ѵ�.
		ArrayList<Integer> p = players(player_raw);
		// TODO: 1���� �ƴϸ� ������ ����.
		// �÷��̾��� �ش��ϴ� ���� �ҷ��´�.
		return (String)variables[p.get(0)][player_variable_index.get(name)];
	}
	
	public String string_get_card(String name, Node card_raw)
	{
		// ī�带 ����Ѵ�.
		ArrayList<Node> c = cards(card_raw);
		// TODO: 1���� �ƴϸ� ������ ����.
		// TODO: ī���� Ư�� attribute�� �����´�. : ���� num_get_card�� ����.
		c.get(0).numChildren();
		return null;
	}
	
	public String call_string(String[] range)
	{
		// TODO: �÷��̾�� range �� �ϳ��� �����ϵ��� �ϴ� UI�� ����.
		return null;
	}
	
	
	////////////////////// PREDEFINED VARIABLES AND VARIABLE GET ////////////////////////
	// global�� ���� player���� ã�� �Լ�
	public Object predefined(String variable_name)
	{
		Integer index=global_variable_index.get(variable_name);
		if(index!=null) return variables[0][index];
		index=player_variable_index.get(variable_name);
		if(index!=null) return variables[playerStack.peek()][index];
		// Search Fail
		return null;
	}
	public Object card_predefined(String variable_name)
	{
		Node c=cardStack.peek();
		for(int loop=1;loop<c.numChildren();loop++)
		{
			if(variable_name.equals((String)c.getChildNode(loop).getData()))
				return c.getChildNode(loop).getChildNode(0).getData();
		}
		// Search Fail
		return null;
	}
	public Object player_variable_get(String variable_name, Integer player_num)
	{
		playerStack.push(player_num);
		Object result=predefined(variable_name);
		playerStack.pop();
		return result;
	}
	public Object card_variable_get(String variable_name, Node card_actual)
	{
		cardStack.push(card_actual);
		Object result=card_predefined(variable_name);
		cardStack.pop();
		return result;
	}
}