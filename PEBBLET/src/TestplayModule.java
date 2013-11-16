import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.Stack;
import java.util.Random;

public class TestplayModule {
	
	///////////////////// 실제로 들고 있는 데이터 /////////////////////
	// 플레이어 자리
	private ArrayList<Integer> player_seats;
	// 전역변수의 변수명, 인덱스 값.
	private Dictionary<String,Integer> global_variable_index;
	// 플레이어별 변수의 변수명, 인덱스 값.
	private Dictionary<String,Integer> player_variable_index;
	// 변수의 실제 값 목록. 첫 번째 인덱스는 플레이어 번호(0이면 전역), 두 번째 인덱스는 dictionary에서 찾은 변수의 인덱스 값.
	// 정수, 문자열, 또는 Node (Deck의 경우)를 들고있다.
	private Object[][] variables;
	/////////////////////////////////////////////////////////////
	
	
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
	public void action_order(Node deck_raw, final Node[] crit)
	{
		Node d=deck(deck_raw);
		// TODO: 의심가는 부분.
		Collections.sort(d.getAllNode(), new Comparator<Node>() {
		    public int compare(Node a, Node b) {
		    	int loop;
		    	int a_value, b_value;
		    	for(loop=0;loop<crit.length;loop++)
		    	{
		    		// 비교할 수치를 구한다.
		    		cardStack.push(a);
		    		a_value=num(crit[loop].getChildNode(0));
		    		cardStack.pop();
		    		cardStack.push(b);
		    		b_value=num(crit[loop].getChildNode(0));
		    		cardStack.pop();
		    		
		    		// 같으면, 다음 조건에서 비교한다.
		    		if(a_value==b_value) continue;
		    		// 크면서 high, 작으면서 low이면 1
		    		if(a_value>b_value && ((String)crit[loop].getData()).equals("High")) return 1;
		    		if(a_value<b_value && ((String)crit[loop].getData()).equals("Low")) return 1;
		    		return -1;
		    	}
		    	// 완전히 같음.
		    	return 0;
		    }
		});
	}
	
	// player_raw가 지정하는 플레이어별로 action_raw의 액션을 수행한다.
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
	
	// player_all : 전체 플레이어의 목록을 자리 순서대로 돌려준다.
	public ArrayList<Integer> player_all()
	{
		return player_seats;
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
	
	// 플레이어 인덱스값 조정하는 함수.
	public int player_index_arrange(int i)
	{
		int n=player_seats.size();
		if(i>n) return i%n;
		if(i<0) return (i%n)+n;
		return i;
	}

	// player_raw 왼쪽의 n_raw 번째 사람들(all=false), 또는 n_raw명의 사람들(all=true)
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
	// player_raw 오른쪽의 n_raw 번째 사람들(all=false), 또는 n_raw명의 사람들(all=true)
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
	
	public Node deck_get(name:String, player:Integer)
	
	
}
