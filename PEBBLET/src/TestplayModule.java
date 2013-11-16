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
	// Node 형태로 쓰인 언어를 interpret한다.
	
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
	// card_raw가 지정하는 카드를 deck_raw로 움직인다.
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
	
	// 주어진 파일에서 카드를 불러와 deck_raw에 넣는다.
	public void action_load(String file, Node deck_raw)
	{
		Node deck_=deck(deck_raw);
	}
	
	// deck_raw가 지정하는 덱을 섞는다.
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
	
	// crit이 지정하는 조건에 따라 deck_raw가 지정하는 덱의 카드를 섞는다.
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
		
		// 알고리즘 개선 필요. 현재는 우선순위가 낮은 요소부터 정렬을 반복.
		// 우선순위가 높은 것만 해서 값이 같은 경우 우선순위가 낮은 정렬을 수행하도록 하면 더 빨리 수행할 수 있음.
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
	
	// player_raw가 지정하는 플레이어별로 action_raw의 액션을 수행한다.
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
	
	// action_raw가 자식 노드로 가지고 있는 다수의 액션을 수행한다. 
	public void action_multiple(Node action_raw)
	{
		ArrayList<Node> n=action_raw.getAllNode();
		int loop;
		for(loop=0;loop<n.size();loop++)
			action(n.get(loop));
	}
	
	// condition_raw가 지정하는 조건이 만족되면 action_raw를 수행한다.
	public void action_if(Node condition_raw, Node action_raw)
	{
		boolean condition=cond(condition_raw);
		if(condition) action(action_raw);
	}
	// condition_raw가 지정하는 조건이 만족되면 action_raw를, 그렇지 않으면 elseaction_raw를 수행한다.
	public void action_ifelse(Node condition_raw, Node action_raw, Node elseaction_raw)
	{
		boolean condition=cond(condition_raw);
		if(condition) action(action_raw);
		else action(elseaction_raw);
	}
	
	// n_raw가 지정하는 숫자만큼  action_raw 액션을 반복 수행한다.
	public void action_repeat(Node n_raw, Node action_raw)
	{
		int n=num(n_raw);
		int loop;
		for(loop=0;loop<n;loop++)
			action(action_raw);
	}
	
	// 미리 지정한 액션을 수행한다.
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
	// Node를 계산하여 플레이어를 돌려준다.
	// 리턴 : ArrayList<Integer>
	
	// player_multiple : 여러 플레이어의 목록을 합성하여 돌려준다.
	public ArrayList<Integer> player_multiple(Node players)
	{
		ArrayList<Node> p=players.getAllNode();
		ArrayList<Integer> a=new ArrayList<Integer>();
		int loop,loop2;
		for(loop=0;loop<p.size();loop++)
		{
			// child 각각을 evaluate한다.
			ArrayList<Integer> tmplist=players(p.get(loop));
			
			// evaluate한 child에서 중복이 아닌 원소만 누적시킨다.
			for(loop2=0;loop2<tmplist.size();loop2++)
			{
				int elem = tmplist.get(loop2);
				if(!a.contains(elem))
					a.add(elem);
			}
		}
		return a;
	}
	// player_all : 현재 플레이어를 돌려준다.
	public ArrayList<Integer> player_current()
	{
		ArrayList<Integer> l=new ArrayList<Integer>();
		l.add(playerStack.lastElement());
		return l;
	}
	
	// player_all : 전체 플레이어의 목록을 돌려준다.
	public ArrayList<Integer> player_all()
	{
	}
	
	// player_exclude : domain_raw의 플레이어에서 excluded_raw의 플레이어를 제외한 결과를 돌려준다.
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
	
	// players_raw 중에서 condition_raw 조건을 만족하는 플레이어의 목록을 돌려준다.
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
	
	// players_raw를 order 조건에 따라 정렬한다
	public ArrayList<Integer> player_most(final Node[] order,Node players_raw)
	{
		ArrayList<Integer> p=players(players_raw);
		Collections.sort(p, new Comparator<Integer>() {
		    public int compare(Integer a, Integer b) {
		    	int loop;
		    	int a_value, b_value;
		    	for(loop=0;loop<order.length;loop++)
		    	{
		    		// 비교할 수치를 구한다.
		    		playerStack.push(a);
		    		a_value=num(order[loop].getChildNode(0));
		    		playerStack.pop();
		    		playerStack.push(b);
		    		b_value=num(order[loop].getChildNode(0));
		    		playerStack.pop();
		    		
		    		// 같으면, 다음 조건에서 비교한다.
		    		if(a_value==b_value) continue;
		    		// 크면서 high, 작으면서 low이면 1
		    		if(a_value>b_value && ((String)order[loop].getData()).equals("High")) return 1;
		    		if(a_value<b_value && ((String)order[loop].getData()).equals("Low")) return 1;
		    		return -1;
		    	}
		    	// 완전히 같음.
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
