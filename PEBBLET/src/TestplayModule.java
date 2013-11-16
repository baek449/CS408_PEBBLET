import java.util.ArrayList;
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
	public int[] players(Node n)
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
	
	// player_multiple : ���� �÷��̾��� ����� �ռ��Ͽ� �����ش�.
	public int[] player_multiple(Node players)
	{
		ArrayList<Node> p=players.getAllNode();
		ArrayList<Integer> a=new ArrayList<Integer>();
		int loop,loop2;
		for(loop=0;loop<p.size();loop++)
		{
			int[] tmplist=players(p.get(loop));
			for(loop2=0;loop2<tmplist.length;loop2++)
				if(!a.contains(tmplist[loop2]))
					a.add(tmplist[loop2]);
		}
		
		// Converting to integer array
		int[] result = new int[a.size()];
		for(loop=0;loop<a.size();loop++)
			result[loop]=a.get(loop);
		return result;
	}
	// player_all : ���� �÷��̾ �����ش�.
	public int[] player_current()
	{
		int[] l=new int[1];
		l[0]=playerStack.lastElement();
		return l;
	}
	
	// player_all : ��ü �÷��̾��� ����� �����ش�.
	public int[] player_all()
	{
		int[] l=new int[total_players];
		int loop;
		for(loop=0;loop<total_players;loop++)
			l[loop]=loop;
		return l;
	}
	
	// player_exclude : domain_raw�� �÷��̾�� excluded_raw�� �÷��̾ ������ ����� �����ش�.
	public int[] player_exclude(Node excluded_raw, Node domain_raw)
	{
		int[] excluded = players(excluded_raw);
		int[] domain = players(domain_raw);
		int[] result_raw = new int[domain.length];
		int hit=0;
		int loop,loop2;
		boolean found;
		for(loop=0;loop<domain.length;loop++)
		{
			found=false;
			for(loop2=0;loop2<excluded.length;loop2++)
			{
				if(excluded[loop2]==domain[loop])
				{
					found=true;
					break;
				}
			}
			if(!found)
			{
				result_raw[hit]=domain[loop];
				hit++;
			}
		}
		int[] result = new int[hit];
		for(loop=0;loop<hit;loop++)
			result[loop]=result_raw[loop];
		return result;
	}
	
	// TODO: implement
	public int[] player_left(boolean all, Node n_raw, Node player_raw)
	{
		return null;
	}
	public int[] player_right(boolean all, Node n_raw, Node player_raw)
	{
		return null;
	}
	
	// players_raw �߿��� condition_raw ������ �����ϴ� �÷��̾��� ����� �����ش�.
	public int[] player_condition (Node condition_raw, Node players_raw)
	{
		int[] p = players(players_raw);
		int[] result_ = new int[p.length];
		int hit=0;
		int loop;
		boolean b;
		for(loop=0;loop<p.length;loop++)
		{
			playerStack.push(p[loop]);
			b=cond(condition_raw);
			playerStack.pop();
			if (b)
			{
				result_[hit]=p[loop];
				hit++;
			}
		}
		int[] result = new int[hit];
		for(loop=0;loop<hit;loop++)
			result[loop]=result_[loop];
		return result;
	}
}
