package PEBBLET;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
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

	private TestplayModule tpm;
	
	private class PlayboardPanel extends JPanel{
		private Graphics g;
		HashMap<String,Integer> global_variable_index, player_variable_index;
		String[][][] variable_name_classification;
		Object[][] variables;
		public PlayboardPanel () {
			// 전역변수의 변수명, 인덱스 값.
			global_variable_index=tpm.get_global_variable_index();
			// 플레이어별 변수의 변수명, 인덱스 값.
			player_variable_index=tpm.get_player_variable_index();
			variable_name_classification=tpm.get_variable_classification();
		}
		public void paint(Graphics g_) {
			g=g_;
			ArrayList<Integer> player_seats=tpm.get_player_seat();
			// 변수의 실제 값 목록. 첫 번째 인덱스는 플레이어 번호(0이면 전역), 두 번째 인덱스는 dictionary에서 찾은 변수의 인덱스 값.
			// 정수, 문자열, 또는 Node (Deck의 경우)를 들고있다.
			variables=tpm.get_variables();
			int current_player=tpm.get_current_player();
			Dimension d= new Dimension(card_name_gap,card_name_gap);
			Dimension result=new Dimension(d);
			int y=0;
			int loop;
			if(current_player==-1)
			{
				result=drawPlayer(0,d);
				if(y<result.height) y=result.height;
				d.width=result.width+card_name_gap;
				for(loop=0;loop<player_seats.size();loop++)
				{
					result=drawPlayer(player_seats.get(loop),d);
					if(y<result.height) y=result.height;
					d.width=result.width+card_name_gap;
				}
				d.height=y+card_name_gap;
				set_play_board_size(d);
				return;
			}
			result=drawPlayer(current_player,d);
			if(y<result.height) y=result.height;
			d.width=result.width+card_name_gap;
			result=drawPlayer(0,d);
			if(y<result.height) y=result.height;
			d.width=result.width+card_name_gap;
			int current_player_index=player_seats.indexOf(current_player);
			for(loop=current_player_index+1;loop<player_seats.size();loop++)
			{
				result=drawPlayer(player_seats.get(loop),d);
				if(y<result.height) y=result.height;
				d.width=result.width+card_name_gap;
			}
			for(loop=0;loop<current_player_index;loop++)
			{
				result=drawPlayer(player_seats.get(loop),d);
				if(y<result.height) y=result.height;
				d.width=result.width+card_name_gap;
			}
			d.height=y+card_name_gap;
			set_play_board_size(d);
			return;
		}
		
		private final Dimension card_size= new Dimension(100,155);
		private final Dimension card_arc_size=new Dimension(10,10);
		private static final int card_name_y=10;
		private static final int card_name_gap=7;
		private Dimension drawCard(Node cardNode, Dimension p)
		{
			g.drawRoundRect(p.width, p.height, card_size.width, card_size.height, card_arc_size.width, card_arc_size.height);
			String s=(String)cardNode.getData();
			if (s.length()>10) s=s.substring(0, 8)+"..";
			g.drawString(s, p.width+card_name_gap, p.height+card_name_y+card_name_gap);
			return new Dimension(p.width+card_size.width,p.height+card_size.height);
		}
		
		private Dimension drawDeck(Node deckNode, String deck_name, Dimension p)
		{
			int loop;
			Dimension p_=new Dimension(p);
			p_.height+=card_name_y;
			g.drawString(deck_name, p_.width, p_.height);
			p_.height+=card_name_gap;
			
			Dimension result=new Dimension(p_.width+card_size.width,p_.height);
			for(loop=0;loop<deckNode.numChildren();loop++)
			{
				result=drawCard(deckNode.getChildNode(loop),p_);
				p_.height+=card_name_y+card_name_gap*2;
			}
			return result;
		}
		private void drawVariable(String s, Dimension p)
		{
			g.drawString(s, p.width, p.height);
		}
		private Dimension drawVariables(int player_num, Dimension p_)
		{
			Dimension p=new Dimension(p_);
			p.height+=card_name_y;
			int loop;
			String[] vars;
			if(player_num==0)
			{
				vars=variable_name_classification[0][0];
				for(loop=0;loop<vars.length;loop++)
				{
					drawVariable("Number "+vars[loop]+" : "+variables[0][global_variable_index.get(vars[loop])], p);
					p.height+=card_name_y+card_name_gap;
				}
				vars=variable_name_classification[0][1];
				for(loop=0;loop<vars.length;loop++)
				{
					drawVariable("String "+vars[loop]+" : "+variables[0][global_variable_index.get(vars[loop])], p);
					p.height+=card_name_y+card_name_gap;
				}
				vars=variable_name_classification[0][3];
				for(loop=0;loop<vars.length;loop++)
				{
					drawVariable("Player "+vars[loop]+" : "+variables[0][global_variable_index.get(vars[loop])], p);
					p.height+=card_name_y+card_name_gap;
				}
				return p;
			}
			vars=variable_name_classification[1][0];
			for(loop=0;loop<vars.length;loop++)
			{
				drawVariable("Number "+vars[loop]+" : "+variables[player_num][player_variable_index.get(vars[loop])], p);
				p.height+=card_name_y+card_name_gap;
			}
			vars=variable_name_classification[1][1];
			for(loop=0;loop<vars.length;loop++)
			{
				drawVariable("String "+vars[loop]+" : "+variables[player_num][player_variable_index.get(vars[loop])], p);
				p.height+=card_name_y+card_name_gap;
			}
			vars=variable_name_classification[1][3];
			for(loop=0;loop<vars.length;loop++)
			{
				drawVariable("Player "+vars[loop]+" : "+variables[player_num][player_variable_index.get(vars[loop])], p);
				p.height+=card_name_y+card_name_gap;
			}
			return p;
		}
		
		private Dimension drawPlayer(int player_num, Dimension p_)
		{
			Dimension p=new Dimension(p_);
			p.height+=card_name_y;
			if(player_num!=0) g.drawString("Player "+player_num, p.width, p.height);
			p.height+=card_name_gap;
			Dimension box_start=new Dimension(p);
			p.width+=card_name_gap;
			p.height+=card_name_gap;
			p=drawVariables(player_num,p);
			Dimension box_end=new Dimension(p);
			p.height+=card_name_gap;
			int y=p.height;
			
			String[] vars;
			if(player_num==0) vars=variable_name_classification[0][2];
			else vars=variable_name_classification[1][2];
			Node target;
			int loop;
			for(loop=0;loop<vars.length;loop++)
			{
				if(player_num==0)
				{
					target=(Node)variables[0][global_variable_index.get(vars[loop])];
				}
				else
				{
					target=(Node)variables[player_num][player_variable_index.get(vars[loop])];
				}
				p=drawDeck(target,vars[loop],p);
				if(box_end.height<p.height) box_end.height=p.height;
				p.height=y;
				p.width+=card_name_gap;
			}
			box_end.width=p.width;
			if(box_end.width<200+box_start.width) box_end.width=200+box_start.width;
			box_end.height+=card_name_gap;
			g.drawRect(box_start.width, box_start.height, box_end.width-box_start.width, box_end.height-box_start.height);
			return box_end;
		}
	}
	
	
	private class SelectionboardPanel extends JPanel{
		private String msg;
		private ArrayList<String> cases;
		private boolean isCallint;
		int n, start, end;

		private static final int card_name_y=10;
		private static final int card_name_gap=7;
		
		private JPanel confirm_panel, button_panel;
		private JLabel msg_label;
		private JButton confirm;
		private JToggleButton[] selections;
		private JTextField input_field;
		private boolean isCurrentSelection;
		
		private class ConfirmListener implements MouseListener {

			@Override
			public synchronized void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				int loop;
				ArrayList<Integer> a=new ArrayList<Integer>();
				if(isCurrentSelection)
				{
					if(selections==null) return;
					for(loop=0;loop<selections.length;loop++)
						if(selections[loop].isSelected())
							a.add(loop);
					if(a.size()!=n) return;
				}
				else
				{
					try {
						a.add(Integer.parseInt(input_field.getText()));
					} catch(NumberFormatException e_) {
						return;
					}
				}
				tpm.set_selection_result(a);
				reset();
				notifyAll();
				return;
			}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}
			
		}
		
		public SelectionboardPanel()
		{
			this.setLayout(new BorderLayout());
			confirm_panel=new JPanel();
			confirm_panel.setPreferredSize(new Dimension(100,50));
			add(confirm_panel,BorderLayout.WEST);
			
			confirm=new JButton("Confirm");
			confirm.addMouseListener(new ConfirmListener());
			//confirm.setPreferredSize(new Dimension(90,30));
			confirm.setVisible(false);
			confirm_panel.add(confirm);
			
			msg_label=new JLabel(msg);
			msg_label.setPreferredSize(new Dimension(300,20));
			msg_label.setVisible(false);
			add(msg_label,BorderLayout.NORTH);
			
			button_panel=new JPanel();
			button_panel.setLayout(new BoxLayout(button_panel, BoxLayout.X_AXIS));
			button_panel.setPreferredSize(new Dimension(700,50));
			add(button_panel,BorderLayout.CENTER);
			
			input_field=new JTextField();
			input_field.setPreferredSize(new Dimension(100,30));
			isCurrentSelection=false;
		}

		public void reset()
		{
			isCallint=false;
			cases=null;
			isCurrentSelection=false;
			msg_label.setVisible(false);
			confirm.setVisible(false);
			remove(input_field);
			if(selections!=null)
			{
				int loop;
				for(loop=0;loop<selections.length;loop++)
					remove(selections[loop]);
			}
			selections=null;
			revalidate();
		}
		public void set_select_cases(String msg_,ArrayList<String> cases_, int n_)
		{
			msg="Select "+n_+" "+msg_+" :";
			cases=cases_;
			n=n_;
			isCallint=false;
			if(!isCurrentSelection)
			{
				msg_label.setText(msg);
				msg_label.setVisible(true);
				confirm.setVisible(true);
				isCurrentSelection=true;
				
				selections=new JToggleButton[cases.size()];
				int loop;
				for(loop=0;loop<selections.length;loop++)
				{
					selections[loop]=new JToggleButton(cases.get(loop));
					selections[loop].setPreferredSize(new Dimension(100,50));
					button_panel.add(selections[loop]);
				}
				set_selection_board_size(new Dimension((selections.length*80),80));
				revalidate();
			}
		} 
		public void set_call_int(int start_, int end_)
		{
			msg="Call a number from "+start+" to "+end+" :";
			start=start_;
			end=end_;
			isCallint=true;
			if(!isCurrentSelection)
			{
				msg_label.setText(msg);
				msg_label.setVisible(true);
				confirm.setVisible(true);
				button_panel.add(input_field,BorderLayout.CENTER);
				isCurrentSelection=true;
				revalidate();
			}
			return;
		}
	}
	public void set_select_cases(String msg_,ArrayList<String> cases_, int n_)
	{
		selection_board.set_select_cases(msg_,cases_, n_);
	}
	public void set_call_int(int start_, int end_)
	{
		selection_board.set_call_int(start_, end_);
	}
	
	
	private PlayboardPanel play_board;
	private SelectionboardPanel selection_board;
	private JPanel control_board, button_board;
	private JScrollPane scroll_play_board;
	private JScrollPane scroll_selection_board;
	
	private JButton button_viewRule, button_record, button_exitTestplay;

	public TestplayUI (TestplayModule tpm_) {
		tpm=tpm_;
		setTitle("PEBBLET Testplay");
		setSize(1000,500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout(7,7));
		
		
		// Initializing Panels
		Container contentpane = getContentPane();
		
		
		/////////////////// Testplayboard scroll bar
		play_board=new PlayboardPanel();
		play_board.setBorder(BorderFactory.createLoweredBevelBorder());
		scroll_play_board=new JScrollPane(play_board);
		contentpane.add(scroll_play_board, BorderLayout.CENTER);
		///////////////////
		
		
		contentpane.repaint();
		
		control_board=new JPanel();
		control_board.setLayout(new BorderLayout(7,7));
		contentpane.add(control_board, BorderLayout.SOUTH);
		control_board.setPreferredSize(new Dimension(1000,100));
		
		button_board=new JPanel();
		button_board.setBorder(BorderFactory.createLoweredBevelBorder());
		control_board.add(button_board, BorderLayout.WEST);
		button_board.setPreferredSize(new Dimension(100,100));
		
		/////////////////// selection_board scroll bar
		selection_board=new SelectionboardPanel();
		selection_board.setBorder(BorderFactory.createLoweredBevelBorder());
		
		scroll_selection_board=new JScrollPane(selection_board);
		control_board.add(scroll_selection_board, BorderLayout.CENTER);
		
		set_play_board_size(new Dimension(2000,2000));
		set_selection_board_size(new Dimension(600,80));
		///////////////////
		
		button_viewRule=new JButton("Rule..");
		button_viewRule.setPreferredSize(new Dimension(80,25));
		button_record=new JButton("Record");
		button_record.setPreferredSize(new Dimension(80,25));
		button_exitTestplay=new JButton("Exit");
		button_exitTestplay.setPreferredSize(new Dimension(80,25));
		button_board.add(button_viewRule);
		button_board.add(button_record);
		button_board.add(button_exitTestplay);
		
		ArrayList<String> ar=new ArrayList<String>();
		ar.add("test1234567");
		ar.add("test1234567");
		ar.add("test1234567");
		ar.add("test1234567");
		ar.add("test1234567");
		ar.add("test1234567");
		ar.add("test1234567");
		ar.add("test1");
		ar.add("test1");
		ar.add("test1");
		ar.add("test1");
		ar.add("test1");
		ar.add("test1");
		ar.add("test1");
		ar.add("test1");
		ar.add("test1");
		ar.add("test1");
		ar.add("test1");
		ar.add("test1");
		ar.add("test1");
		ar.add("test1");
		ar.add("test1");
		ar.add("test1");
		selection_board.set_select_cases("Deck",ar, 1);
		selection_board.revalidate();
		setVisible(true);		
    }
    
	
	private void set_play_board_size(Dimension d)
	{
		play_board.setPreferredSize(d);
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
			tpm.action(rul.getRoot().getChildNode(0));
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
