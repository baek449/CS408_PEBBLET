import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;
import java.util.Random;

public class TestplayModule {
	private Node def;
	private Stack<Integer> playerStack;
	private Stack<Node> cardStack;
	private Node actionFunc;
	private Node eventFunc;
	private boolean logging;
	private TestLog testlog;
	
	public TestplayModule()
	{
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
	public void action_order(Node deck_raw, Node[] crit)
	{
		Node deck_ = deck(deck_raw);
		ArrayList<Node> cards=deck_.getAllNode();
		int cardloop,critloop;
		int cardnum=deck_.numChildren();
		int critnum=crit.length;
		for (cardloop=0;cardloop<cardnum;cardloop++)
		{
			cards.get(cardloop).addChildNode(new Node());
		}
		
		// �˰��� ���� �ʿ�. ����� �켱������ ���� ��Һ��� ������ �ݺ�.
		// �켱������ ���� �͸� �ؼ� ���� ���� ��� �켱������ ���� ������ �����ϵ��� �ϸ� �� ���� ������ �� ����.
		for(critloop=critnum-1;critloop>=0;critloop--)
		{
			// Value Calculation
			for(cardloop=0;cardloop<cardnum;cardloop++)
			{
				cardStack.push(cards.get(cardloop));
				cards.get(cardloop).getLastChildNode().setData(num(crit[critloop].getChildNode(1)));
				cardStack.pop();
			}
			// Bubble Sorting
			int loop1,loop2,value;
			Node temp;
			for (loop1=cardloop-1;loop1>=0;loop1--)
			{
				for(loop2=0;loop2<loop1-1;loop2++)
				{
					value=(Integer)cards.get(loop2).getLastChildNode().getData()-(Integer)cards.get(loop2+1).getLastChildNode().getData();
					if((value>0 && ((String)crit[critloop].getChildNode(0).getData()).equals("Low")) ||
							(value<0 && ((String)crit[critloop].getChildNode(0).getData()).equals("High")))
					{
						temp=cards.get(loop2);
						cards.set(loop2, cards.get(loop2+1));
						cards.set(loop2+1, temp);
					}
				}
			}
		}
	}
	
	// player_raw�� �����ϴ� �÷��̾�� action_raw�� �׼��� �����Ѵ�.
	public void action_act(Node players_raw, Node action_raw)
	{
		int[] players_=players(players_raw);
		int loop;
		for(loop=0;loop<players_.length;loop++)
		{
			playerStack.push(players_[loop]);
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
	
	// player_all : ��ü �÷��̾��� ����� �����ش�.
	public ArrayList<Integer> player_all()
	{
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
	
	// TODO: implement
	public ArrayList<Integer> player_left(boolean all, Node n_raw, Node player_raw)
	{
		return null;
	}
	public ArrayList<Integer> player_right(boolean all, Node n_raw, Node player_raw)
	{
		return null;
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
	
	
}
