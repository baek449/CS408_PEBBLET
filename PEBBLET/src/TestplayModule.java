import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.Stack;
import java.util.Random;

public class TestplayModule {
	
	///////////////////// ������ ��� �ִ� ������ /////////////////////
	// �÷��̾� �ڸ�
	private ArrayList<Integer> player_seats;
	// ���������� ������, �ε��� ��.
	private Dictionary<String,Integer> global_variable_index;
	// �÷��̾ ������ ������, �ε��� ��.
	private Dictionary<String,Integer> player_variable_index;
	// ������ ���� �� ���. ù ��° �ε����� �÷��̾� ��ȣ(0�̸� ����), �� ��° �ε����� dictionary���� ã�� ������ �ε��� ��.
	// ����, ���ڿ�, �Ǵ� Node (Deck�� ���)�� ����ִ�.
	private Object[][] variables;
	/////////////////////////////////////////////////////////////
	
	private Node def_;
	
	
	private Node def;
	private Stack<Integer> playerStack;
	private Stack<Node> cardStack;
	private Node actionFunc;
	private Node eventFunc;
	private boolean logging;
	private TestLog testlog;
	
	public TestplayModule()
	{
		// Def
		//variables=new Object[1][1];
		playerStack=new Stack<Integer>();
		cardStack=new Stack<Node>();
		logging=false;
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
	public void action(Node n)
	{
	}
	public ArrayList<Integer> players(Node n)
	{
		return null;
	}
	public Node deck(Node n)
	{
		return null;
	}
	public Node[] cards(Node n)
	{
		return null;
	}
	public boolean cond(Node n)
	{
		return true;
	}
	public int num(Node n)
	{
		return 0;
	}
	public String str(Node n)
	{
		return null;
	}
	
	
	//////////////////////// Actions ////////////////////////
	// card_raw�� �����ϴ� ī�带 deck_raw�� �����δ�.
	public void action_move(Node card_raw, Node deck_raw)
	{
		Node[] card_=cards(card_raw);
		Node deck_=deck(deck_raw);
		int loop;
		for(loop=0;loop<card_.length;loop++)
		{
			card_[loop].setParent(deck_);
		}
	}
	
	// �־��� ���Ͽ��� ī�带 �ҷ��� deck_raw�� �ִ´�.
	public void action_load(String file, Node deck_raw)
	{
		Node deck_=deck(deck_raw);
	}
	
	// deck_raw�� �����ϴ� ���� ���´�.
	public void action_shuffle(Node deck_raw)
	{
		Node deck_=deck(deck_raw);
		Random r=new Random(System.currentTimeMillis());
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
	
	// crit�� �����ϴ� ���ǿ� ���� deck_raw�� �����ϴ� ���� ī�带 ���´�.
	public void action_order(Node deck_raw, final Node[] crit)
	{
		Node d=deck(deck_raw);
		// TODO: �ǽɰ��� �κ�.
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
		for(loop=0;loop<players_.size();loop++)
		{
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
		// TODO: Ask action selection
	}
	
	
	
	
	///////////////////////// PLAYER EVALUATION FUNCTIONS ///////////////////////
	// Node�� ����Ͽ� �÷��̾ �����ش�.
	// ���� : ArrayList<Integer>
	
	// player_multiple : ���� �÷��̾��� ����� �ռ��Ͽ� �����ش�.
	public ArrayList<Integer> player_multiple(Node players)
	{
		ArrayList<Node> p=players.getAllNode();
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
	// player_all : ���� �÷��̾ �����ش�.
	public ArrayList<Integer> player_current()
	{
		ArrayList<Integer> l=new ArrayList<Integer>();
		l.add(playerStack.lastElement());
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
			if(result.contains(player2insert))
				result.add(player2insert);
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
		for(loop=0;loop<p.size();loop++)
		{
			index=player_seats.indexOf(p.get(loop));
			result.add(player_seats.get(player_index_arrange(index+n)));
			if(all)
				for(loop2=index-1;loop2>0;loop2--)
					result.add(player_seats.get(player_index_arrange(index+loop2)));
		}
		return result;
	}
	
	// players_raw �߿��� condition_raw ������ �����ϴ� �÷��̾��� ����� �����ش�.
	public ArrayList<Integer> player_condition (Node condition_raw, Node players_raw)
	{
		ArrayList<Integer> p = players(players_raw);
		int loop;
		boolean b;
		for(loop=0;loop<p.size();loop++)
		{
			playerStack.push(p.get(loop));
			b=cond(condition_raw);
			playerStack.pop();
			if (!b)
			{
				p.remove(loop);
				loop--;
			}
		}
		return p;
	}
	
	// players_raw�� order ���ǿ� ���� �����Ѵ�
	public ArrayList<Integer> player_most(final Node[] order,Node players_raw)
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
	}
	
	public ArrayList<Integer> select_player(int n, Node players_raw)
	{
	}
	
	public Node deck_get(name:String, player:Integer)
	
	
}
