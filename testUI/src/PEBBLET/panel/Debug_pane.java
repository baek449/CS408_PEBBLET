package PEBBLET.panel;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Debug_pane extends JComponent{
	private JPanel debug_pane;
	private JScrollPane debug_sc;
	private JButton start;
	private boolean completed = false;
	private JButton start_testplay;
	
	private int msg_pos = 20;
	
	public Debug_pane(){
		debug_pane = new JPanel(true);
		debug_pane.setPreferredSize(new Dimension(900, 500));
		
		debug_sc = new JScrollPane(debug_pane);
		debug_sc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		debug_sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		debug_sc.setPreferredSize(new Dimension(900, 600));
		
		start = new JButton("Start Debugging");
		start_testplay = new JButton("Start TestPlay");
		start.setBounds(5, 400, 200,30);
		start_testplay.setBounds(300,400, 200, 30);
		debug_pane.setLayout(null);
		debug_pane.add(start);
		debug_pane.add(start_testplay);
		make_debug_msg("abdef");
		make_debug_msg("이것은 테스트다");
		make_debug_msg("버그가 잇으면 출력한다");
	}
	
	public void make_debug_msg(String msg_){
		JLabel msg = new JLabel(msg_);
//		msg.setPreferredSize(new Dimension(msg_.length()*10, 30));
//		msg.setLocation(5, msg_pos);
		msg.setBounds(5, msg_pos, msg_.length()*15,30);
		msg_pos+=30;
		debug_pane.setLayout(null);
		debug_pane.add(msg);
	}
	
	public JComponent get_scpane(){
		return debug_sc;
	}
}
