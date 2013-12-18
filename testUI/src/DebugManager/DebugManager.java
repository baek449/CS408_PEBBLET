package DebugManager;

import java.util.ArrayList;

import PEBBLET.panel.Debug_pane;
import manager.ComponentManager;
import manager.Definition;
import manager.DefinitionManager;
import manager.Node;
import manager.NodeType;
import manager.NodeTypewithScope;
import manager.RuleCase;
import manager.RuleManager;

public class DebugManager {
	ArrayList<String> bug_list;
	
	DefinitionManager dm;
	RuleManager rm;
	ComponentManager cm;
	Debug_pane dbg_pane;
	
	public DebugManager(){
		dm = new DefinitionManager();
		rm = new RuleManager();
		rm.updateVariableList(dm);
		cm = new ComponentManager(dm);
		
		
	}
	
	public DebugManager(DefinitionManager dm_){//for def debug
		dm = dm_;
		dbg_pane = new Debug_pane(dm);
		bug_list = new ArrayList<String>();
		check_null(0, dm_.getDefinition().getRoot(), true, 4);
		check_positive_players(dm);
		check_duplication(dm);
		warning_one_domain(dm);
		
		
	}
	
	public DebugManager(DefinitionManager dm, RuleManager rm, ComponentManager cm){
		this.dm = dm;
		this.rm = rm;
		this.cm = cm;
		
	}
/*---------------general case------------------------------------------*/	
	public void check_null(int indent,  Node node_input, boolean is_def,int index){
		int loop;
		if(is_def){
			if(indent !=0 && node_input.numChildren() !=0 ){
				if(node_input.get_node_type() == null){
					for(loop = 0; loop<indent; loop++){//set align
						System.out.print("\t");
					}
					
					//node type is null
					String msg = ""+node_input.getParent() +"/" + index + "/ NodeType is null";
					System.out.println(msg);
					bug_list.add(msg);
				}
				if(node_input.getData() == null){
					for(loop = 0; loop<indent; loop++){//set align
						System.out.print("\t");
					}
					
					//node data is null
					String msg = ""+node_input.getParent() + "/" + index + "/ NodeData is null";
					System.out.println(msg);
					bug_list.add(msg);
				}
				
				
				for(loop= 0 ; loop<node_input.numChildren(); loop++){
					check_null(indent+1, node_input.getChildNode(loop), is_def, loop);
				}
			}
			else if(indent != 0 && node_input.numChildren() ==0 ){
				if(node_input.getData() == null){
					for(loop = 0; loop<indent; loop++){//set align
						System.out.print("\t");
					}
					
					//node data is null
					String msg = ""+node_input.getParent() + "/" + index + "/ NodeData is null";
					System.out.println(msg);
					bug_list.add(msg);
				}
			}
			else if(indent == 0){
				for(loop= 0 ; loop<node_input.numChildren(); loop++){
					check_null(indent+1, node_input.getChildNode(loop), is_def, loop);
				}
			}
		}
		else{
			if(node_input.get_node_type() == null){
				
				for(loop = 0; loop<indent; loop++){//set align
					System.out.print("\t");
				}
				
				//node type is null
				String msg = ""+node_input.getParent() +"/" + index + "/ NodeType is null";
				System.out.println(msg);
				bug_list.add(msg);
			}
			if(node_input.getData() == null){
				for(loop = 0; loop<indent; loop++){//set align
					System.out.print("\t");
				}
				
				//node data is null
				String msg = ""+node_input.getParent() + "/" + index  + "/ NodeData is null";
				System.out.println(msg);
				bug_list.add(msg);
			}
			
			
			for(loop= 0 ; loop<node_input.numChildren(); loop++){ // root node!
				check_null(indent+1, node_input.getChildNode(loop), is_def, loop);
			}
			
		}
	}	
	
	
	
	
/*-----------------------definition check only-------------------------------------*/

	
	public void check_positive_players(DefinitionManager dm_){
		int[] location = {0,0};
		if((int)dm_.search(location).getData() < 1){
			String msg = "Player Number is not positive or 0, In (Definition Tab, Player Number)";
			bug_list.add(msg);
			System.out.println(msg);
		}
	}
	
	public void check_duplication(DefinitionManager dm_){
		// [1,0] [1,1] ... ect , [2,0] [2,1] ...ect, [3,0] [3,1]... ect
		int[] global = {1};
		int[] player = {2};
		int[] card = {3};
		
		
		ArrayList<String> check_list = new ArrayList<String>();

		for(int i = 0; i < dm_.search(global).numChildren(); i++){
			if(check_list.size()==0){
				if(!(dm_.search(global).getChildNode(0).getData()==null)){
					check_list.add((String)dm_.search(global).getChildNode(0).getData());
				}
			}
			else{
				boolean dp = false;
				for(int j = 0; j < check_list.size(); j++){
					if(!(dm_.search(global).getChildNode(i).getData()==null)){
						if(((String)dm_.search(global).getChildNode(i).getData()).equals(check_list.get(j))){
							//duplication!!
							String msg = "Duplication Error, in Definition, Global, " + i + ":" + (String)dm_.search(global).getChildNode(i).getData();
							System.out.println(msg);
							bug_list.add(msg);
							dp = true;
							
						}
					}
				}
				if(!dp){
					if(!(dm_.search(global).getChildNode(i).getData()==null))
						check_list.add((String)dm_.search(global).getChildNode(i).getData());
				}
			}
		}
		
		for(int i = 0; i <dm_.search(player).numChildren(); i++){
			if(check_list.size()==0){
				if(!(dm_.search(player).getChildNode(0).getData()==null)){
					check_list.add((String)dm_.search(player).getChildNode(0).getData());
				}
			}
			else{
				boolean dp = false;
				for(int j = 0; j < check_list.size(); j++){
					if(!(dm_.search(player).getChildNode(i).getData() == null)){
						if(((String)dm_.search(player).getChildNode(i).getData()).equals(check_list.get(j))){
							//duplication!!
							String msg = "Duplication Error, in Definition, Player, " + i + ":" + (String)dm_.search(global).getChildNode(i).getData();
							System.out.println(msg);
							bug_list.add(msg);
							dp = true;
							
						}
					}
				}
				if(!dp){
					if(!(dm_.search(player).getChildNode(i).getData() ==null))
						check_list.add((String)dm_.search(player).getChildNode(i).getData());
				}
			}
		}
		for(int i = 0; i < dm_.search(card).numChildren(); i++){
			if(check_list.size()==0){
				if(!(dm_.search(card).getChildNode(0).getData()==null)){
					check_list.add((String)dm_.search(card).getChildNode(0).getData());
				}
			}
			else{
				boolean dp = false;
				for(int j = 0; j < check_list.size(); j++){
					if(!(dm_.search(card).getChildNode(i).getData()==null)){
						if(((String)dm_.search(card).getChildNode(i).getData()).equals(check_list.get(j))){
							//duplication!!
							String msg = "Duplication Error, in Definition, Card, " + i + ":" + (String)dm_.search(global).getChildNode(i).getData();
							System.out.println(msg);
							bug_list.add(msg);
							dp = true;
						}
					}
				}
				if(!dp){
					if(!(dm_.search(card).getChildNode(i).getData() == null)){
						check_list.add((String)dm_.search(card).getChildNode(i).getData());
					}
				}
			}
		}
		
	}
	
	public void warning_one_domain(DefinitionManager dm_){
		int[] global = {1};
		int[] player = {2};
		int[] card = {3};
		
		for(int i = 0; i < dm_.search(global).numChildren(); i++){
			if(dm_.search(global).getChildNode(i).numChildren() == 1){
				String msg = "Warning, in Definition, Global, " + i + ": Set only one attribute";
				System.out.println(msg);
				bug_list.add(msg);
			}
		}
		for(int i = 0; i < dm_.search(player).numChildren(); i++){
			if(dm_.search(player).getChildNode(i).numChildren() == 1){
				String msg = "Warning, in Definition, Player, " + i + ": Set only one attribute";
				System.out.println(msg);
				bug_list.add(msg);
			}
		}
		for(int i = 0; i < dm_.search(card).numChildren(); i++){
			if(dm_.search(card).getChildNode(i).numChildren() == 1){
				String msg = "Warning, in Definition, Card, " + i + ": Set only one attribute";
				System.out.println(msg);
				bug_list.add(msg);
			}
		}
		
	}

/*----------------------Rule & Comp case ---------------------*/
	
	/* NaN!, Missing Domain, UnAcceptable condition 
	 * Type miss matching : variable type miss, data type miss, invalid operation, cond!! 
	 * Action Multiple Warning
	 * Description detail error
	 * */
	
	public void invalid_operation(RuleManager rm_){
		invalid_operation_rc(0, rm_.getRule().getRoot());
	}
	
	public void invalid_operation_rc(int intend, Node input){
		
		String[] op = {"+", "-", "/", "//", "/^", "%", "*"};
		
		boolean flag = false;
		
		if(input.getData().equals(RuleCase.num_operation)){
			flag = false;
			for(int i = 0; i < 7; i++){
				if( input.getChildNode(1).equals(op[i]) ){
					flag = true;
				}
			}
			if(!flag){
				//invalid operation
				String msg = "Invalid Operation, In Rule Tab, in Level :" + intend + ":" + input;
				System.out.println(msg);
				bug_list.add(msg);
			}
		}
		
		for(int i = 0; i < input.numChildren(); i++ ){
			invalid_operation_rc(intend+1, input.getChildNode(i));
		}
	}

	public void condition_op_error(RuleManager rm_){
		condition_op_error_rc(0, rm_.getRule().getRoot());
	}


	public void condition_op_error_rc(int intend, Node input) {

		String[] op = {"==", "!=", "<", "<=", ">", ">="};
		
		boolean flag = false;
		
		if(input.getData().equals(RuleCase.cond_numcompare)){
			flag = false;
			for(int i = 0; i < 6; i++){
				if( input.getChildNode(1).equals(op[i]) ){
					flag = true;
				}
			}
			if(!flag){
				//invalid operation
				String msg = "Invalid Condition Operation, In Rule Tab, in Level :" + intend + ":" + input;
				System.out.println(msg);
				bug_list.add(msg);
			}
		}
		
		for(int i = 0; i < input.numChildren(); i++ ){
			condition_op_error_rc(intend+1, input.getChildNode(i));
		}
	}

	public void NaN_error(RuleManager rm_){
		NaN_error_rc(0, rm_.getRule().getRoot());
	}
	
	public void NaN_error_rc(int intend, Node input){
	String[] op = {"/", "//", "/^", "%"};
		if(input.getData().equals(RuleCase.num_operation)){	
			for(int i = 0; i < 4; i++){
				if( input.getChildNode(1).equals(op[i]) ){
					if((input.getChildNode(2).getData()).equals(RuleCase.num_raw)){
						if(input.getChildNode(2).getChildNode(0).equals("0")){
							//NaN Error check
							String msg = "Devided By O, Not a Number Error, in Rule Tad, in Level :" + intend + ":"+ input;
							System.out.println(msg);
							bug_list.add(msg);
							
						}
					}
				}
			}
		}
		
		for(int i = 0; i < input.numChildren(); i++ ){
			NaN_error_rc(intend+1, input.getChildNode(i));
		}
	}
	
	private ArrayList<Node> check_list;
	
	public void missing_domain(DefinitionManager dm_,RuleManager rm_){
		int[] global = {1};
		int[] player = {2};
		int[] card = {3};
		
		
		check_list = new ArrayList<Node>();
		
		for(int i = 0; i < dm_.search(global).numChildren(); i++){
			check_list.add(dm_.search(global).getChildNode(i));
		}		
//		for(int i = 0; i <dm_.search(player).numChildren(); i++){
//			check_list.add(dm_.search(player).getChildNode(i));
//		}
//		for(int i = 0; i < dm_.search(card).numChildren(); i++){
//			check_list.add(dm_.search(card).getChildNode(i));
//		}
		
		missing_domain_rc(0, rm_.getRule().getRoot());
		
	}
	
	public void missing_domain_rc(int intend, Node input){		
		if(input.getData().equals(RuleCase.action_setint)){
			boolean flag = true;
			int j;
			for(j =0; j<check_list.size(); j++){
				if(((String)input.getChildNode(0).getData()).equals(check_list.get(j))){
					flag = false;
					break;
				}
			}
			if(flag){
				String msg = "Invalid variable Error, In Rule tab, in Level :" + intend + ": " + input;
				System.out.println(msg);
				bug_list.add(msg);
			}
			else{
				
				if(!(NodeType.nd_num.equals(check_list.get(j).get_node_type()))){
					String msg = "Type miss in Rule tab, in Level :"+ intend + ": " + input + "Should be Integer Type";
					System.out.println(msg);
					bug_list.add(msg);
				}
				
				else if(input.getChildNode(1).getData().equals(RuleCase.num_raw)){
					boolean flag_ = true;
					for(int a = 0; a < check_list.get(j).numChildren();a++){
						if((check_list.get(j).getChildNode(a).getData().equals( input.getChildNode(1).getChildNode(0).getData()))){
							flag_ = false;
							break;
						}
					}
					if(flag_){
						String msg = "Domain Miss Matching Error, In Rule tab, in Level :" + intend + ": " +input.getChildNode(1).getChildNode(0);
						System.out.println(msg);
						bug_list.add(msg);
					}
				}
			}
		}
		else if(input.getData().equals(RuleCase.action_setstr)){
			boolean flag = true;
			int j;
			for(j =0; j<check_list.size(); j++){
				if(((String)input.getChildNode(0).getData()).equals(check_list.get(j))){
					flag = false;
					break;
				}
			}
			if(flag){
				String msg = "Invalid variable Error, In Rule tab, in Level :" + intend + ": " + input;
				System.out.println(msg);
				bug_list.add(msg);
			}
			else{
				
				if(!(NodeType.nd_str.equals(check_list.get(j).get_node_type()))){
					String msg = "Type miss in Rule tab, in Level :"+ intend + ": " + input + "Should be String Type";
					System.out.println(msg);
					bug_list.add(msg);
				}
				
				else if(input.getChildNode(1).getData().equals(RuleCase.string_raw)){
					boolean flag_ = true;
					for(int a = 0; a < check_list.get(j).numChildren();a++){
						if((check_list.get(j).getChildNode(a).getData().equals( input.getChildNode(1).getChildNode(0).getData()))){
							flag_ = false;
							break;
						}
					}
					if(flag_){
						String msg = "Domain Miss Matching Error, In Rule tab, in Level :" + intend + ": " +input.getChildNode(1).getChildNode(0);
						System.out.println(msg);
						bug_list.add(msg);
					}
				}
			}
		}
		
		else if(input.getData().equals(RuleCase.action_setdeck)){
			boolean flag = true;
			int j;
			for(j =0; j<check_list.size(); j++){
				if(((String)input.getChildNode(0).getData()).equals(check_list.get(j))){
					flag = false;
					break;
				}
			}
			if(flag){
				String msg = "Invalid variable Error, In Rule tab, in Level :" + intend + ": " + input;
				System.out.println(msg);
				bug_list.add(msg);
			}
			else{
				
				if(!(NodeType.nd_deck.equals(check_list.get(j).get_node_type()))){
					String msg = "Type miss in Rule tab, in Level :"+ intend + ": " + input + "Should be Deck Type";
					System.out.println(msg);
					bug_list.add(msg);
				}
			}
		}
		
		else if(input.getData().equals(RuleCase.action_setplayer)){
			boolean flag = true;
			int j;
			for(j =0; j<check_list.size(); j++){
				if(((String)input.getChildNode(0).getData()).equals(check_list.get(j))){
					flag = false;
					break;
				}
			}
			if(flag){
				String msg = "Invalid variable Error, In Rule tab, in Level :" + intend + ": " + input;
				System.out.println(msg);
				bug_list.add(msg);
			}
			else{
				
				if(!(NodeType.nd_player.equals(check_list.get(j).get_node_type()))){
					String msg = "Type miss in Rule tab, in Level : "+ intend + ": " + input + "Should be Player Type";
					System.out.println(msg);
					bug_list.add(msg);
				}
			}
		}
		
		for(int i = 0 ;i < input.numChildren(); i++){
			missing_domain_rc(intend++, input.getChildNode(i));
		}
	}
	
	public void check_rawContent(RuleManager rm_){
		check_rawContent_rc(0, rm_.getRule().getRoot());
	}
	
	public void check_rawContent_rc(int intend, Node input){
		if(input.getData().equals(RuleCase.num_raw)){
			if(input.numChildren()!=1){
				String msg = "Set " + input + "content more than 1";
				System.out.println(msg);
				bug_list.add(msg);
			}
			else{
				if(!(input.getChildNode(0).get_node_type().equals(NodeType.nd_raw))){
					String msg = "Node Type error, in Rule tab, in Level : " + intend + ": " + input;
					System.out.println(msg);
					bug_list.add(msg);
				}
				else{
					// TODO: may be string
					if(input.getChildNode(0).getData().getClass() != Integer.class){
						String msg = "Node data type error, in Rule tab, in Level : " + intend + ": " + input +"Should be Integer type";
						System.out.println(msg);
						bug_list.add(msg);
					}
				}
			}
		}
		
		else if(input.getData().equals(RuleCase.string_raw)){
			if(input.numChildren()!=1){
				String msg = "Set " + input + "content more than 1";
				System.out.println(msg);
				bug_list.add(msg);
			}
			else{
				if(!(input.getChildNode(0).get_node_type().equals(NodeType.nd_raw))){
					String msg = "Node Type error, in Rule tab, in Level : " + intend + ": " + input;
					System.out.println(msg);
					bug_list.add(msg);
				}
				else{
					if(input.getChildNode(0).getData().getClass() != String.class){
						String msg = "Node data type error, in Rule tab, in Level : " + intend + ": " + input +"Should be String type";
						System.out.println(msg);
						bug_list.add(msg);
					}
				}
			}
		}
		
		for(int i = 0 ;i < input.numChildren(); i++){
			check_rawContent_rc(intend++, input.getChildNode(i));
		}
		
	}
	
	
	private int player_num;
	public void check_dp_player(RuleManager rm_, DefinitionManager dm_){
		int[] pn = {0,0};
		player_num = (int)dm_.search(pn).getData();
		
		check_dp_player_rc(0, rm_.getRule().getRoot());
	}
	
	public void check_dp_player_rc(int intend, Node input){
		//deck_player or player_player rule should target only one card or player
		
		if(input.getData().equals(RuleCase.deck_player)||input.getData().equals(RuleCase.player_player)
				||input.getData().equals(RuleCase.num_player)||input.getData().equals(RuleCase.string_player)){
			if(!(input.getChildNode(0).get_node_type().equals(NodeType.nd_player))){
				//input.getChildNode(0) node type is not player!
				String msg = "Note type error, in Rule tab, in Level : " + (intend+1) + ": "+ input.getChildNode(0) + "Should be Player type";
				System.out.println(msg);
				bug_list.add(msg);
			}
			else{
				if(input.getChildNode(0).getData().equals(RuleCase.player_select)
					||input.getChildNode(0).getData().equals(RuleCase.player_left_all)
					||input.getChildNode(0).getData().equals(RuleCase.player_right_all)){
					
					if(!(input.getChildNode(0).getChildNode(0).get_node_type().equals(NodeType.nd_num))){
						//input.getChildNode(0).getChildNode(0) node type is not number!!
						String msg = "Node type error, in Rule tab, in Level : " + (intend +2) + ": "+ input.getChildNode(0).getChildNode(0) + "Should be Number type";
						System.out.println(msg);
						bug_list.add(msg);
						
					}
					else{
//						if(!(input.getChildNode(0).getChildNode(0).getData().equals(RuleCase.num_raw))){
//							//input.getChildNode(0).getChildNode(0) data type is not num_raw!
//							String msg = "Node data type error, in Rule tab, in Level : " + (intend+2) + ": " + input.getChildNode(0).getChildNode(0);
//							System.out.println(msg);
//							bug_list.add(msg);
//						}
						if(input.getChildNode(0).getChildNode(0).getData().equals(RuleCase.num_raw)){
							if((int)input.getChildNode(0).getChildNode(0).getChildNode(0).getData() != 1){
								//input.getChildNode(0).getChildNode(0).getChildNode(0) data is not 1!!!
								String msg = "Found more than 1 player, in Rule tab, Level : " + intend + ": " + input;
								System.out.println(msg);
								bug_list.add(msg);
							}
						}
					}
					
				}
				else if(input.getChildNode(0).getData().equals(RuleCase.player_all)){
					if(player_num == 1){
						//in definition, player number is 1, are you sure player_all option?
						String msg = "Conflit error, in Definition number of player is 1, in Rule tab, in Level :" + intend + ": " + input.getChildNode(0);
						System.out.println(msg);
						bug_list.add(msg);
					}
				}
				
				else if(input.getChildNode(0).getData().equals(RuleCase.player_multiple)){
					//player multiple select warning
					String msg = "Warning, Set \"select multiple Players\", in Rule tab, in Level : " + intend + ": "+ input.getChildNode(0);
					System.out.println(msg);
					bug_list.add(msg);
				}
				
			}
		}
		else if(input.getData().equals(RuleCase.action_card)||input.getData().equals(RuleCase.num_card)||input.getData().equals(RuleCase.string_card)){
			if(!(input.getChildNode(0).get_node_type().equals(NodeType.nd_card))){
				//input.getChildNode(0) node type is not Card!
				String msg = "Note type error, in Rule tab, in Level : " + (intend+1) + ": "+ input.getChildNode(0) + "Should be Card type";
				System.out.println(msg);
				bug_list.add(msg);
			}
			else{
				if(input.getChildNode(0).getData().equals(RuleCase.card_select)
					||input.getChildNode(0).getData().equals(RuleCase.card_top)
					||input.getChildNode(0).getData().equals(RuleCase.card_bottom)){
					
					if(!(input.getChildNode(0).getChildNode(0).get_node_type().equals(NodeType.nd_raw))){
						//input.getChildNode(0).getChildNode(0) node type is not number!!
						String msg = "Node type error, in Rule tab, in Level : " + (intend +2) + ": "+ input.getChildNode(0).getChildNode(0) +"Should be Number type";
						System.out.println(msg);
						bug_list.add(msg);
						
					}
					else{
//						if(!(input.getChildNode(0).getChildNode(0).getData().equals(RuleCase.num_raw))){
//							//input.getChildNode(0).getChildNode(0) data type is not num_raw!
//							String msg = "Node data type error, in Rule tab, in Level : " + (intend+2) + ": " + input.getChildNode(0).getChildNode(0);
//							System.out.println(msg);
//							bug_list.add(msg);
//						}
						if(input.getChildNode(0).getChildNode(0).getData().equals(RuleCase.num_raw)){
							if((int)input.getChildNode(0).getChildNode(0).getChildNode(0).getData() != 1){
								//input.getChildNode(0).getChildNode(0).getChildNode(0) data is not 1!!!
								String msg = "Found more than 1 player, in Rule tab, Level : " + intend + ": " + input;
								System.out.println(msg);
								bug_list.add(msg);
							}
						}
					}
					
				}
				else if(input.getChildNode(0).getData().equals(RuleCase.card_all)){
					//all of card selected warning
					String msg = "Warning, Set \"select all of card\", in Rule tab, in Level : " + intend + ": "+ input.getChildNode(0);
					System.out.println(msg);
					bug_list.add(msg);
				}
			}
		}
		
		for(int i = 0; i < input.numChildren();i++){
			check_dp_player_rc(intend++, input.getChildNode(i));
		}
	}
	
	
	// 카드 타입 입력에 대한 체크.
	private String card_name;
	public void check_cardtype(DefinitionManager dm_ ,RuleManager rm_){
		int[] location ={3};
		//get card name from definition manager dm_
		card_name = (String)(dm_.search(location).getData());
		
		check_cardtype_rc(0, rm_.getRule().getRoot());
	}
	
	public void check_cardtype_rc(int intend, Node input){
		if(input.getData()!=null && input.getData().getClass()==RuleCase.class)
		{
			RuleCase rc=(RuleCase)input.getData();
			if(rc==RuleCase.action_load || rc==RuleCase.cond_istype)
			{
				Node n=input.getChildNode(0);
				if(n.get_node_type()!=NodeType.nd_raw)
				{
					String msg = "Node type error, in Rule tab, in Level : " + (intend +1) + ": "+ input.getChildNode(0) +"Should be Raw type";
					System.out.println(msg);
					bug_list.add(msg);
				}
				else if(!n.getData().equals(card_name))
				{
					String msg = "Card type error, in Rule tab, in Level : " + (intend +1) + ": "+ input.getChildNode(0) +"Should be "+card_name;
					System.out.println(msg);
					bug_list.add(msg);
				}
			}
		}
		for(int i = 0; i < input.numChildren();i++){
			check_cardtype_rc(intend++, input.getChildNode(i));
		}
	}

	// multiple 안의 multiple check. action_multiple / player_multiple
	public void check_nested_multiple(DefinitionManager dm_ ,RuleManager rm_){
		int[] location ={3};
		//get card name from definition manager dm_
		card_name = (String)(dm_.search(location).getData());
		
		check_nested_multiple_rc(0, rm_.getRule().getRoot());
	}
	
	public void check_nested_multiple_rc(int intend, Node input){
		if(input.getData()!=null && input.getData().getClass()==RuleCase.class)
		{
			RuleCase rc=(RuleCase)input.getData();
			int k;
			Node n;
			if(rc==RuleCase.action_multiple)
			{
				for(k=0;k<input.numChildren();k++)
				{
					n=input.getChildNode(0);
					if(n.getData()==RuleCase.action_multiple)
					{
						String msg = "Warning, in Rule tab, in Level : " + (intend +1) + ": "+ input.getChildNode(0) +"Nested 'Multiple Actions'";
						System.out.println(msg);
						bug_list.add(msg);
					}
				}
			}
			if(rc==RuleCase.player_multiple)
			{
				for(k=0;k<input.numChildren();k++)
				{
					n=input.getChildNode(0);
					if(n.getData()==RuleCase.player_multiple)
					{
						String msg = "Warning, in Rule tab, in Level : " + (intend +1) + ": "+ input.getChildNode(0) +"Nested 'Multiple Players'";
						System.out.println(msg);
						bug_list.add(msg);
					}
				}
			}
		}
		for(int i = 0; i < input.numChildren();i++){
			check_nested_multiple_rc(intend++, input.getChildNode(i));
		}
	}
	
	// 여러 값이 들어갈 수 있는 경우 값이 0개 들어가면 에러. 예외: action_multiple, player_multiple(warning)
	public void check_multiple(DefinitionManager dm_ ,RuleManager rm_){
		int[] location ={3};
		//get card name from definition manager dm_
		card_name = (String)(dm_.search(location).getData());
		
		check_multiple_rc(0, rm_.getRule().getRoot());
	}
	
	public void check_multiple_rc(int intend, Node input){
		if(input.getData()!=null && input.getData().getClass()==RuleCase.class)
		{
			RuleCase rc=(RuleCase)input.getData();
			int limit=0;
			int err_level=0;
			switch(rc)
			{
			case action_order:
			case player_most:
				limit=1;
				err_level=2;
				break;
			case action_multiple:
				err_level=0;
				break;
			case action_endgame_order:
			case action_choose:
			case deck_select:
			case string_call:
				err_level=2;
				break;
			case player_multiple:
				err_level=1;
				break;
			default:
				break;
			}
			if(err_level==2 && input.numChildren()<=limit)
			{
				String msg = "Error, in Rule tab, in Level : " + (intend +1) + ": "+ input.getChildNode(0) +"At least one instance of multiple cases should be provided.";
				System.out.println(msg);
				bug_list.add(msg);
			}
			if(err_level==1 && input.numChildren()<=limit)
			{
				String msg = "Warning, in Rule tab, in Level : " + (intend +1) + ": "+ input.getChildNode(0) +"At least one multiple player cases are recommended to be provided.";
				System.out.println(msg);
				bug_list.add(msg);
			}
		}
		
		for(int i = 0; i < input.numChildren();i++){
			check_multiple_rc(intend++, input.getChildNode(i));
		}
	}
	public ArrayList<String> get_bug_list(){
		return bug_list;
	}

}
