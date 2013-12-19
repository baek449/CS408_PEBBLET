package PEBBLET;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import manager.Node;
import manager.Rule;
import manager.TestplayModule;

public class TestplayUI extends JFrame {

	// 테스트플레이 모듈
	private TestplayModule tpm;
	
	// UI Components
	private JPanel play_board; // 플레이 장면이 표시되는 곳 :center
	///////////////////////////// play_board dynamic components
	private ArrayList<Integer> player_order;
	private int cur_player;
	private HashMap<String,Integer> global_variable_index, player_variable_index;
	private String[][][] variable_name_classification;
	private Object[][] variables;
	
	private JPanel total;
	private JPanel[] per_player_total; // 플레이어별 보드 :center:#
	private JLabel[] per_player_name; // 플레이어별 이름 :center:#
	private JPanel[] per_player_board; // 플레이어별 보드 :center:#
	private JPanel[] per_player_deck_board; // 플레이어별 덱 보드 :center:#
	private JPanel[][] per_var_board; // 1개 변수 :center:#:#
	private JLabel[][] per_var_name; // 1개 변수 이름칸 :center:#:#
	private JLabel[][] per_var_type; // 1개 변수 type칸 :center:#:#
	private JLabel[][] per_var_colon; // 1개 변수 colon칸 :center:#:#
	private JLabel[][] per_var_value; // 1개 변수 값칸 :center:#:#
	private JPanel[][] per_deck_board; // 덱별 보드 :center:#:#
	private JLabel[][] per_deck_name; // 덱 이름칸 :center:#:#
	private JPanel[][][] per_card_board; // 카드별 보드
	private JLabel[][][] per_card_name; // 카드별 이름
	/////////////////////////////
	private JScrollPane scroll_play_board; // 스크롤 :center:scroll

	private JPanel control_board; // 사용자가 컨트롤하는 곳 :south
	private JPanel button_board; // 테스트플레이 제어 버튼이 나열되어 있는 곳 :south:west
	private JButton button_viewRule, button_record, button_exitTestplay; // 버튼들 :south:west:#
	
	private JPanel info_board; // 현재 상황 정보가 표시되는 곳 :south:center
	private JPanel confirm_board; // 사용자 선택 정보와 확인 버튼이 표현되는 곳 :south:center:west
	///////////////////////////// confirm_panel components
	private String msg; // 메시지. 예시: "Players", "Cards", "Deck"
	private JLabel msg_label; // 메시지 라벨
	private JButton confirm; // 확인 버튼
	private JLabel err_msg_label; // 에러 메시지 라벨
	/////////////////////////////
	
	private JPanel input_board; // 사용자 선택이 표시되는 곳 :south:center:center // Card Layout
	private static final String[] input_board_layouts={"Empty","Selection", "Number", "Endgame"};
	private String currently_visible="Empty";

	private JPanel empty_board; // 사용자 선택지가 표현되는 곳 :south:center:center:"Empty"
	private JPanel selection_board; // 사용자 선택지가 표현되는 곳 :south:center:center:"Selection"

	///////////////////////////// selection_board dynamic components
	private int n; // 선택할 목표 개수
	private ArrayList<String> cases; // 버튼으로 만들 예시 string 목록
	private JToggleButton[] selections; // 버튼 목록
	/////////////////////////////
	
	
	private JScrollPane scroll_selection_board; // 스크롤 :south:center:center:"Selection":scroll
	private JPanel number_board; // 사용자 선택지가 표현되는 곳 :south:center:center:"Number"
	private int start, end; // 시작 끝 값
	private JTextField number_field; // 숫자 입력 창

	private JPanel endgame_board; // 사용자 선택지가 표현되는 곳 :south:center:center:"Endgame"
	private JLabel endgame_message; // 게임 종료 메시지
	

	public void paint(Graphics g){
		// Update Data
		super.paint(g);
	}
	
	public void get_tpm_info(){
		global_variable_index=tpm.get_global_variable_index();
		player_variable_index=tpm.get_player_variable_index();
		variable_name_classification=tpm.get_variable_classification();
		variables=tpm.get_variables();
	}
	
	public TestplayUI (TestplayModule tpm_) {
		tpm=tpm_;
		tpm.setUI(this);
		get_tpm_info();
		setTitle("PEBBLET Testplay");
		setSize(1000,500);
		setLocationRelativeTo(null);
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout(7,7));
		
		
		// Initializing Panels
		Container contentpane = getContentPane();
		int player_loop,player_loop_limit;
		int var_loop,var_nums, var_strs, var_players, var_loop_limit,i;
		
		// UI Components
		play_board=new JPanel();
		play_board.setLayout(new BoxLayout(play_board,BoxLayout.LINE_AXIS));
		play_board.setBorder(BorderFactory.createLoweredBevelBorder());
		scroll_play_board=new JScrollPane(play_board);
		scroll_play_board.setPreferredSize(new Dimension(1000,500));
		contentpane.add(scroll_play_board, BorderLayout.CENTER);
		///////////////////////////// play_board dynamic components
		
		total=new JPanel();
		total.setLayout(new BoxLayout(total,BoxLayout.LINE_AXIS));
		total.add(Box.createRigidArea(new Dimension(10,10)));
		play_board.add(total);
		
		player_loop_limit=tpm.get_player_seat().size()+1;
		per_player_total=new JPanel[player_loop_limit];
		per_player_name=new JLabel[player_loop_limit];
		per_player_board=new JPanel[player_loop_limit];
		per_player_deck_board=new JPanel[player_loop_limit];
		per_var_board=new JPanel[player_loop_limit][];
		per_var_name=new JLabel[player_loop_limit][];
		per_var_type=new JLabel[player_loop_limit][];
		per_var_colon=new JLabel[player_loop_limit][];
		per_var_value=new JLabel[player_loop_limit][];
		per_deck_board=new JPanel[player_loop_limit][];
		per_deck_name=new JLabel[player_loop_limit][];
		per_card_board=new JPanel[player_loop_limit][][];
		per_card_name=new JLabel[player_loop_limit][][];
		String tmp,tmptype;
		Object tmp2;
		for(player_loop=0;player_loop<player_loop_limit;player_loop++)
		{
			per_player_total[player_loop]=new JPanel();
			per_player_total[player_loop].setLayout(new BoxLayout(per_player_total[player_loop],BoxLayout.PAGE_AXIS));
			per_player_total[player_loop].setAlignmentY(java.awt.Component.TOP_ALIGNMENT);
			total.add(per_player_total[player_loop]);
			total.add(Box.createRigidArea(new Dimension(10,10)));
			
			per_player_name[player_loop]=new JLabel("Player "+player_loop);
			if(player_loop==0) per_player_name[player_loop].setText(" ");
			per_player_total[player_loop].add(per_player_name[player_loop]);
			per_player_name[player_loop].setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
			
			per_player_board[player_loop]=new JPanel();
			per_player_board[player_loop].setLayout(new BoxLayout(per_player_board[player_loop],BoxLayout.PAGE_AXIS));
			if(player_loop!=0) per_player_board[player_loop].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			per_player_total[player_loop].add(per_player_board[player_loop]);
			per_player_board[player_loop].setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
			
			per_player_deck_board[player_loop]=new JPanel();
			per_player_deck_board[player_loop].setLayout(new BoxLayout(per_player_deck_board[player_loop],BoxLayout.LINE_AXIS));
			per_player_deck_board[player_loop].setBorder(BorderFactory.createLineBorder(Color.BLUE));
			per_player_deck_board[player_loop].setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
			
			i=1;
			if(player_loop==0) i=0;
			var_nums=variable_name_classification[i][0].length;
			var_strs=variable_name_classification[i][1].length;
			var_players=variable_name_classification[i][3].length;
			var_loop_limit=var_nums+var_strs+var_players;
			per_var_board[player_loop]=new JPanel[var_loop_limit];
			per_var_name[player_loop]=new JLabel[var_loop_limit];
			per_var_type[player_loop]=new JLabel[var_loop_limit];
			per_var_colon[player_loop]=new JLabel[var_loop_limit];
			per_var_value[player_loop]=new JLabel[var_loop_limit];
			for(var_loop=0;var_loop<var_loop_limit;var_loop++) // Deck이 아닌 변수 그리기
			{
				per_var_board[player_loop][var_loop]=new JPanel();
				per_var_board[player_loop][var_loop].setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
				//per_var_board[player_loop][var_loop].setBorder(BorderFactory.createLineBorder(Color.GREEN));
				per_var_board[player_loop][var_loop].setLayout(new BoxLayout(per_var_board[player_loop][var_loop],BoxLayout.LINE_AXIS));
				tmp="";
				tmptype="";
				if(var_loop<var_nums)
				{
					tmp=variable_name_classification[i][0][var_loop];
					tmptype="Number   ";
				}
				else if(var_loop<var_nums+var_strs)
				{
					tmp=variable_name_classification[i][1][var_loop-var_nums];
					tmptype="String   ";
				}
				else
				{
					tmp=variable_name_classification[i][3][var_loop-var_nums-var_strs];
					tmptype="Player   ";
				}
				per_var_name[player_loop][var_loop]=new JLabel(tmp);
				per_var_type[player_loop][var_loop]=new JLabel(tmptype);
				per_var_type[player_loop][var_loop].setForeground(new Color(0,0,200));
				per_var_colon[player_loop][var_loop]=new JLabel("   :   ");
				per_var_board[player_loop][var_loop].add(Box.createRigidArea(new Dimension(10,10)));
				per_var_board[player_loop][var_loop].add(per_var_type[player_loop][var_loop]);
				per_var_board[player_loop][var_loop].add(per_var_name[player_loop][var_loop]);
				per_var_board[player_loop][var_loop].add(per_var_colon[player_loop][var_loop]);
				
				
				if(i==0) tmp2=variables[player_loop][global_variable_index.get(tmp)];
				else tmp2=variables[player_loop][player_variable_index.get(tmp)];
				tmp="";
				if(tmp2!=null) tmp=tmp2.toString();
				per_var_value[player_loop][var_loop]=new JLabel(tmp);
				per_var_board[player_loop][var_loop].add(per_var_value[player_loop][var_loop]);
				per_player_board[player_loop].add(per_var_board[player_loop][var_loop]);
				per_var_board[player_loop][var_loop].add(Box.createRigidArea(new Dimension(10,10)));
			}
			
			
			var_loop_limit=variable_name_classification[i][2].length;
			per_deck_board[player_loop]=new JPanel[var_loop_limit];
			per_deck_name[player_loop]=new JLabel[var_loop_limit];
			per_card_board[player_loop]=new JPanel[var_loop_limit][];
			per_card_name[player_loop]=new JLabel[var_loop_limit][];
			per_player_deck_board[player_loop].add(Box.createRigidArea(new Dimension(10,10)));
			for(var_loop=0;var_loop<var_loop_limit;var_loop++) // Deck 변수 그리기
			{
				per_deck_board[player_loop][var_loop]=new JPanel();
				per_deck_board[player_loop][var_loop].setLayout(new BoxLayout(per_deck_board[player_loop][var_loop],BoxLayout.PAGE_AXIS));
				per_deck_board[player_loop][var_loop].setBounds(50, 50, 100, 100);
				per_deck_board[player_loop][var_loop].setAlignmentY(java.awt.Component.TOP_ALIGNMENT);
				
				tmp=variable_name_classification[i][2][var_loop];
				per_deck_name[player_loop][var_loop]=new JLabel(tmp);
				per_deck_name[player_loop][var_loop].setBounds(0, 0, 80, 15);
				per_deck_name[player_loop][var_loop].setAlignmentX(0.5f);
				per_deck_board[player_loop][var_loop].add(per_deck_name[player_loop][var_loop]);
				//per_deck_board[player_loop][var_loop].setBorder(BorderFactory.createLineBorder(Color.RED));
				per_player_deck_board[player_loop].add(per_deck_board[player_loop][var_loop]);
				per_player_deck_board[player_loop].add(Box.createRigidArea(new Dimension(10,10)));
			}
			
			
			
			per_player_board[player_loop].add(per_player_deck_board[player_loop]);
		}
		pack();
		update_variable_UI();
		
		/////////////////////////////////////////////////////////////////////////////////////////
		
		control_board=new JPanel();
		control_board.setLayout(new BorderLayout(7,7));
		contentpane.add(control_board, BorderLayout.SOUTH);
		control_board.setPreferredSize(new Dimension(1000,100));

		button_board=new JPanel();
		button_board.setBorder(BorderFactory.createLoweredBevelBorder());
		control_board.add(button_board, BorderLayout.WEST);
		button_board.setPreferredSize(new Dimension(100,100));
		button_viewRule=new JButton("Rule...");
		button_viewRule.setPreferredSize(new Dimension(80,25));
		button_record=new JButton("Record");
		button_record.setPreferredSize(new Dimension(80,25));
		button_exitTestplay=new JButton("Exit");
		button_exitTestplay.setPreferredSize(new Dimension(80,25));
		button_board.add(button_viewRule);
		button_board.add(button_record);
		button_board.add(button_exitTestplay);
		
		info_board=new JPanel();
		info_board.setLayout(new BorderLayout(7,7));
		control_board.add(info_board, BorderLayout.CENTER);
		
		confirm_board=new JPanel();
		//confirm_board.setLayout(new BoxLayout(confirm_board,BoxLayout.LINE_AXIS));
		confirm_board.setPreferredSize(new Dimension(100,100));
		confirm_board.setBorder(BorderFactory.createLoweredBevelBorder());
		msg_label=new JLabel("msg");
		confirm_board.add(msg_label);
		confirm=new JButton("Confirm");
		confirm.addMouseListener(new MouseAdapter() {
			public synchronized void mouseClicked(MouseEvent e) {
				int loop;
				ArrayList<Integer> a=new ArrayList<Integer>();
				if(currently_visible.equals(input_board_layouts[1])) // Selection
				{
					if(selections==null)
					{
						err_msg_label.setText("Null Selections");
						return;
					}
					for(loop=0;loop<selections.length;loop++)
						if(selections[loop].isSelected())
							a.add(loop);
					if(a.size()!=n)
					{
						err_msg_label.setText("Selected "+a.size());
						return;
					}
				}
				else if(currently_visible.equals(input_board_layouts[2])) // Integer Input
				{
					try {
						a.add(Integer.parseInt(number_field.getText()));
					} catch(NumberFormatException e_) {
						err_msg_label.setText("Not a number");
						return;
					}
					if(a.get(0)<start || a.get(0)>end)
					{
						err_msg_label.setText("Invalid number");
						return;
					}
				}
				else
				{
					return;
				}
				tpm.set_selection_result(a);
				reset();
				t.interrupt();
				return;
			}
		});
		confirm_board.add(confirm);
		err_msg_label=new JLabel("");
		err_msg_label.setForeground(Color.RED);
		confirm_board.add(err_msg_label);
		info_board.add(confirm_board, BorderLayout.WEST);
		
		
		input_board=new JPanel();
		input_board.setLayout(new CardLayout());
		input_board.setBorder(BorderFactory.createLoweredBevelBorder());
		info_board.add(input_board, BorderLayout.CENTER);
		
		empty_board=new JPanel();
		empty_board.setName(input_board_layouts[0]);
		input_board.add(empty_board, input_board_layouts[0]);
		
		selection_board=new JPanel();
		selection_board.setLayout(new BoxLayout(selection_board,BoxLayout.LINE_AXIS));
		selection_board.setBorder(BorderFactory.createLoweredBevelBorder());
		
		scroll_selection_board=new JScrollPane(selection_board);
		scroll_selection_board.setName(input_board_layouts[1]);
		input_board.add(scroll_selection_board, input_board_layouts[1]);
		
		number_board=new JPanel();
		number_board.setLayout(new BoxLayout(number_board,BoxLayout.PAGE_AXIS));
		number_field=new JTextField();
		number_field.setPreferredSize(new Dimension(100,20));
		number_field.setAlignmentX(0.0f);
		number_board.add(number_field);
		number_board.setName(input_board_layouts[2]);
		input_board.add(number_board, input_board_layouts[2]);
		
		endgame_board=new JPanel();
		endgame_message=new JLabel("Game Ended");
		endgame_board.add(endgame_message);
		endgame_board.setName(input_board_layouts[3]);
		input_board.add(endgame_board, input_board_layouts[3]);
		
		
		
		set_play_board_size(new Dimension(2000,2000));
		set_selection_board_size(new Dimension(600,80));
		setVisible(true);
		
    }
	public void update_player_order_UI()
	{
		player_order=tpm.get_player_seat();
		cur_player=tpm.get_current_player();
		total.removeAll();
		total.add(Box.createRigidArea(new Dimension(10,10)));

		int loop;
		if(cur_player==-1)
		{
			total.add(per_player_total[0]);
			total.add(Box.createRigidArea(new Dimension(10,10)));

			for(loop=0;loop<player_order.size();loop++)
			{
				total.add(per_player_total[player_order.get(loop)]);
				total.add(Box.createRigidArea(new Dimension(10,10)));
			}
			return;
		}
		cur_player=player_order.indexOf(cur_player);
		total.add(per_player_total[player_order.get(cur_player)]);
		total.add(Box.createRigidArea(new Dimension(10,10)));
		total.add(per_player_total[0]);
		total.add(Box.createRigidArea(new Dimension(10,10)));

		for(loop=cur_player+1;loop<player_order.size();loop++)
		{
			total.add(per_player_total[player_order.get(loop)]);
			total.add(Box.createRigidArea(new Dimension(10,10)));
		}
		for(loop=0;loop<cur_player;loop++)
		{
			total.add(per_player_total[player_order.get(loop)]);
			total.add(Box.createRigidArea(new Dimension(10,10)));
		}
	}
	public void update_variable_UI()
	{
		int player_loop, var_loop, card_loop, card_num, card_data_loop;
		boolean i,j,h;
		Object t;
		Node n,crd;
		String tmp,card_data;
		update_player_order_UI();
		for(player_loop=0;player_loop<per_player_board.length;player_loop++)
		{
			i=(player_loop==0);
			j=(player_loop==tpm.get_current_player());
			for(var_loop=0;var_loop<per_var_name[player_loop].length;var_loop++) // variables
			{
				if(i) t=variables[player_loop][global_variable_index.get(per_var_name[player_loop][var_loop].getText())];
				else t=variables[player_loop][player_variable_index.get(per_var_name[player_loop][var_loop].getText())];
				tmp="";
				if(t!=null) tmp=t.toString();
				per_var_value[player_loop][var_loop].setText(tmp);
			}
			for(var_loop=0;var_loop<per_deck_name[player_loop].length;var_loop++) // decks
			{
				per_deck_board[player_loop][var_loop].removeAll();
				per_deck_board[player_loop][var_loop].add(per_deck_name[player_loop][var_loop]);
				h=per_deck_name[player_loop][var_loop].getText().endsWith("#");
				per_card_board[player_loop][var_loop]=null;
				per_card_name[player_loop][var_loop]=null;
				if(i) t=variables[player_loop][global_variable_index.get(per_deck_name[player_loop][var_loop].getText())];
				else t=variables[player_loop][player_variable_index.get(per_deck_name[player_loop][var_loop].getText())];
				if(t!=null)
				{
					n=(Node)t;
					card_num=n.numChildren();
					if(card_num==0)
					{
						per_card_board[player_loop][var_loop]=new JPanel[1];
						per_card_name[player_loop][var_loop]=new JLabel[1];
						per_card_board[player_loop][var_loop][0]=new JPanel();
						//per_card_board[player_loop][var_loop][0].setBounds(5, 20, 60, 15);
						per_deck_board[player_loop][var_loop].add(per_card_board[player_loop][var_loop][0]);
						per_card_name[player_loop][var_loop][0]=new JLabel("<EMPTY>");
						per_card_board[player_loop][var_loop][0].add(per_card_name[player_loop][var_loop][0]);
						per_deck_board[player_loop][var_loop].add(Box.createVerticalGlue());
						continue;
					}
					per_card_board[player_loop][var_loop]=new JPanel[card_num];
					per_card_name[player_loop][var_loop]=new JLabel[card_num];
					for(card_loop=0;card_loop<card_num;card_loop++)
					{
						per_card_board[player_loop][var_loop][card_loop]=new JPanel();
						//per_card_board[player_loop][var_loop][card_loop].setBounds(5, 20+card_loop*20, 60, 15);
						per_card_board[player_loop][var_loop][card_loop].setBorder(BorderFactory.createLineBorder(Color.BLACK));
						per_deck_board[player_loop][var_loop].add(per_card_board[player_loop][var_loop][card_loop]);
						per_card_name[player_loop][var_loop][card_loop]=new JLabel(" ");
						if(!j && h)
						{
							per_card_name[player_loop][var_loop][card_loop].setText("???");
							per_card_board[player_loop][var_loop][card_loop].setToolTipText("Hidden!");
						}
						else
						{
							crd=n.getChildNode(card_loop);
							per_card_name[player_loop][var_loop][card_loop].setText((String)crd.getData());
							card_data="<html>";
							for(card_data_loop=0;card_data_loop<crd.numChildren();card_data_loop++)
							{
								card_data+=crd.getChildNode(card_data_loop).getData()+" : "+crd.getChildNode(card_data_loop).getChildNode(0).getData()+"<br>";
							}
							card_data+="</html>";
							per_card_board[player_loop][var_loop][card_loop].setToolTipText(card_data);
						}
						per_card_board[player_loop][var_loop][card_loop].add(per_card_name[player_loop][var_loop][card_loop]);
					}
					per_deck_board[player_loop][var_loop].add(Box.createVerticalGlue());
					per_deck_board[player_loop][var_loop].add(Box.createVerticalGlue());
				}
			}
		}
		update_window_size();
	}
	
	
	public void reset()
	{
		currently_visible=input_board_layouts[0];
		((CardLayout)input_board.getLayout()).show(input_board, input_board_layouts[0]);
		cases=null;
		//confirm_board.setVisible(false);
		
		msg_label.setVisible(false);
		confirm.setVisible(false);
		err_msg_label.setVisible(false);
	}
	public void set_select_cases(String msg_,ArrayList<String> cases_, int n_)
	{
		update_variable_UI();
		msg="Select "+n_+" "+msg_+" :";
		cases=cases_;
		n=n_;
		msg_label.setText(msg);
		err_msg_label.setText("");
		msg_label.setVisible(true);
		confirm.setVisible(true);
		err_msg_label.setVisible(true);
		//confirm_board.setVisible(true);
		
		selections=new JToggleButton[cases.size()];
		selection_board.removeAll();
		selection_board.add(Box.createRigidArea(new Dimension(10,10)));
		int loop;
		for(loop=0;loop<selections.length;loop++)
		{
			selections[loop]=new JToggleButton(cases.get(loop));
			selections[loop].setMinimumSize(new Dimension(100,50));
			selections[loop].setMaximumSize(new Dimension(100,50));
			selection_board.add(selections[loop]);
			selection_board.add(Box.createRigidArea(new Dimension(10,10)));
		}

		selection_board.add(Box.createHorizontalGlue());
		set_selection_board_size(new Dimension((selections.length*110)+10,70));
		((CardLayout)input_board.getLayout()).show(input_board, input_board_layouts[1]);
		currently_visible=input_board_layouts[1];
	} 
	public void set_call_int(int start_, int end_)
	{
		update_variable_UI();
		start=start_;
		end=end_;
		msg="Call "+start+" ~ "+end+" :";
		
		msg_label.setText(msg);
		err_msg_label.setText("");
		number_field.setText(" ");
		msg_label.setVisible(true);
		confirm.setVisible(true);
		err_msg_label.setVisible(true);
		//confirm_board.setVisible(true);
		
		((CardLayout)input_board.getLayout()).show(input_board, input_board_layouts[2]);
		currently_visible=input_board_layouts[2];
	}
	public void set_endgame(String message)
	{
		tpm.stopRun();
		update_variable_UI();
		msg_label.setText("Game End");
		err_msg_label.setText("");
		msg_label.setVisible(true);
		confirm.setVisible(false);
		err_msg_label.setVisible(true);
		//confirm_board.setVisible(true);
		
		endgame_message.setText(message);
		
		((CardLayout)input_board.getLayout()).show(input_board, input_board_layouts[3]);
		currently_visible=input_board_layouts[3];
		
	}
	private void update_window_size()
	{
//		pack();
		Rectangle r=total.getVisibleRect();
		set_play_board_size(r.getSize());
	}
    
	
	private void set_play_board_size(Dimension d)
	{
		play_board.setSize(d);
		play_board.revalidate();
	}
	private void set_selection_board_size(Dimension d)
	{
		selection_board.setPreferredSize(d);
		selection_board.revalidate();
	}
	
	////////////////////////// 테스트플레이 실행 관련 ///////////////////////
	// 테스트플레이를 돌릴 규칙
	private Rule rul;
	public void set_rule(Rule rul_)
	{
		rul=rul_;
	}
	private class TPThread extends Thread {
		public void run() {
			tpm.makeRun();
			//tpm.action(rul.getRoot().getChildNode(0));
			tpm.action(rul.getRoot());
			if(!currently_visible.equals(input_board_layouts[3]))
				set_endgame("The game was ended without explicit termination.");
		}
	}
	
	private TPThread t;
	// 돌리는 함수
	public void run_testplay(Rule rul_)
	{
		set_rule(rul_);
		t=new TPThread();
		t.start();
	}
    
    
}
