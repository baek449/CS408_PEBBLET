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
		// 1. Definition�� �ε��Ѵ�. �Ǵ� ���ڷ� ���´�.
		
		// 2. Definition�� ���� variable index dictionary�� �����ϰ� variables�� �ʱ�ȭ�Ѵ�.
		
		// 3. �÷��̾� ����� ���� player_seats�� �ʱ�ȭ�Ѵ�.
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
	}
	
	// �÷��̾��� ����� ����Ѵ�.
	public ArrayList<Integer> players(Node n)
	{
		return null;
	}
	
	// Deck�� ����Ѵ�.
	public Node deck(Node n)
	{
		return null;
	}
	
	// ī���� ����� ����Ѵ�.
	public ArrayList<Node> cards(Node n)
	{
		return null;
	}
	
	// ������ ����Ѵ�.
	public boolean cond(Node n)
	{
		return true;
	}
	
	// ��ġ�� ����Ѵ�.
	public int num(Node n)
	{
		return 0;
	}
	
	// ���ڸ� ����Ѵ�.
	public String str(Node n)
	{
		return null;
	}
	
	
	///////////////////////// ACTION EVALUATION FUNCTIONS ///////////////////////
	// Node�� �����ϴ� �׼��� �����Ѵ�.
	// ���� : void
	
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
	public ArrayList<Integer> player_condition (Node condition_raw, Node players_raw)
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
	public ArrayList<Node> cards_alldeck(Node deck_raw)
	{
		return deck(deck_raw).getAllNode();
	}
	
	// �� ���� n��
	public ArrayList<Node> cards_top(Node n_raw, Node deck_raw)
	{
		int n=num(n_raw);
		ArrayList<Node> total=deck(deck_raw).getAllNode();
		// TODO: ���� n�庸�� ũ�� ���� ó��
		return (ArrayList<Node>) total.subList(total.size()-n, total.size());
	}
	// �� �Ʒ��� n��
	public ArrayList<Node> cards_bottom(Node n_raw, Node deck_raw)
	{
		int n=num(n_raw);
		ArrayList<Node> total=deck(deck_raw).getAllNode();
		// TODO: ���� n�庸�� ũ�� ���� ó��
		return (ArrayList<Node>) total.subList(0, n);
	}
	// �� ���� n��
	public ArrayList<Node> cards_random(Node n_raw, Node deck_raw)
	{
		int n=num(n_raw);
		ArrayList<Node> total=deck(deck_raw).getAllNode();
		// TODO: ���� n�庸�� ũ�� ���� ó��
		return (ArrayList<Node>) total.subList(0, n);
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
	
	
}
