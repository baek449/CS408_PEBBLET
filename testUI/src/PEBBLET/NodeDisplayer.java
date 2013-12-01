package PEBBLET;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;

import manager.Node;

public abstract class NodeDisplayer extends JComponent implements MouseListener{
	private Graphics g_;
	private JTextField input_string;
	private String textfield_pool;
	private int selection_pool;
	private String textfield_original;
	private JComboBox<String> input_selection;

	// 표시할 목표 노드
	private Node target;


	public static int xspace=4;
	public static int xtab=50;
	public static int yspace=4;
	public static int yline=10;
	public static int xchar=8;
	//////////////////////////////////////////////////////////
	//////// 0. 사용자와 주로 상호작용하는 함수들

	// fillupSelection을 부르는 가상 함수.
	public abstract void call_fillupSelection(Node parent, int index, boolean isName);
	
	// 목표 노드 설정
	public void setTargetNode(Node target_)
	{
		target=target_;
	}
	// Node를 그리는 방법을 담고 있는 가상 함수. 다 그림 후 오른쪽 아래 점을 리턴한다.
	public abstract Position drawNode(Node n, Position p);
	
	// 입력을 받아 결과를 뿌려주는 함수
	public synchronized String UI_input_string(AreaRange size, String current_data)
	{
		//input_string.setSize(size.ex-size.sx, size.ey-size.sy);
		//input_string.setLocation(size.sx, size.sy);
		input_string.setBounds(size.sx, size.sy, size.ex-size.sx, size.ey-size.sy);
		input_string.setText(current_data);

		textfield_original=current_data;
		textfield_pool=null;
		input_string.setFocusable(true);
		input_string.setVisible(true);
		input_string.requestFocusInWindow();
		revalidate();
		repaint();
		try {
			wait();
		} catch (InterruptedException e) {
			return textfield_original;
		}
		return textfield_pool;
	}
	public synchronized int UI_input_selection(AreaRange size_, String[] options)
	{
		AreaRange size=new AreaRange(size_);
		input_selection.setSize(size.ex-size.sx, size.ey-size.sy);
		input_selection.setLocation(size.sx, size.sy);
		input_selection.setModel(new DefaultComboBoxModel<String>(options));

		selection_pool=-2;
		input_selection.setFocusable(true);
		input_selection.requestFocusInWindow();
		input_selection.setVisible(true);
		repaint();
		try {
			wait();
		} catch (InterruptedException e) {
			return -1;
		}
		System.out.println("Entered: " +selection_pool);
		return selection_pool;
	}
	
	// 생성자와 그리기 함수
	public NodeDisplayer()
	{
		input_string=new JTextField();
		input_selection=new JComboBox<String>();
		this.setLayout(null);
		this.add(input_string);
		this.add(input_selection);
		input_string.setVisible(false);
		input_string.setFocusable(false);
		input_selection.setVisible(false);
		input_selection.setFocusable(false);
		t_listener=new TextfieldListener();
		input_string.addActionListener(t_listener);
		input_string.addFocusListener(t_listener);
		c_listener=new SelectionListener();
		input_selection.addActionListener(c_listener);
		input_selection.addFocusListener(c_listener);
		
		addMouseListener(this);
	}
	public final void paint(Graphics g) {
    	g_=g;
		drawNode(target, new Position(0,0));
	}
	
	//////////////////////////////////////////////////////////
	//////// 1. Input RELATED
	// 클릭하면 해당 노드를 찾아 fillupSelection을 불러주는 요소들
	private int prev_index=0;
	private boolean click(Node n, Position p)
	{
		// 해당 없음.
		if (!n.ar_current.inside(p))
			return false;
		if(n.ar_name.isvalid() && n.ar_name.inside(p))
		{
			// 이름 필드 선택함.
			call_fillupSelection(n.getParent(),prev_index,true);
			return true;
		}
		else if (n.ar_etc.isvalid() && n.ar_etc.inside(p))
		{
			// ... 필드 선택함.
			call_fillupSelection(n,n.numChildren(),false);
			return true;
		}
		// 자식 선택함.
		int prv_index=prev_index;
		for(int loop=0;loop<n.numChildren();loop++)
		{
			prev_index=loop;
			if (click(n.getChildNode(loop),p))
				return true;
		}
		prev_index=prv_index;
		
		// 현재 노드 선택함
		if (!n.ar_current.isvalid())
			return true;
		// valid한 경우 조작 수행
		call_fillupSelection(n.getParent(),prev_index,false);
		return true;
	}
	private class Worker extends Thread
	{
		Position p;
		
		public Worker(Position p_)
		{
			p=p_;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			click(target,p);
		}
		
	}
	private Worker w;
	
	// 이벤트 리스너 (클릭 인식)	  
	  @Override
	public synchronized void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		  System.out.println("Click");
		  if(w==null || !w.isAlive())
		  {
			  w=new Worker(new Position(e.getX(),e.getY()));
			  w.start();
		  }
		  else
		  {
			  System.out.println("interrupt");
			input_string.setVisible(false);
			input_string.setFocusable(false);
			input_selection.setVisible(false);
			input_selection.setFocusable(false);
			  w.interrupt();
		  }
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	//////////////////////////////////////////////////////////
	//////// 2. TextField / ComboBox RELATED
	// 하위 요소에 대한 이벤트 리스너
	private class TextfieldListener implements FocusListener, ActionListener {
	@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println(e.toString());
			input_over(input_string.getText());
			
		}
		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			input_over(textfield_original);	
		}
	}
	private class SelectionListener implements FocusListener, ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			input_selection_over(input_selection.getSelectedIndex());
		}
		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
		}
		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			input_selection_over(-1);
		}
	}
	
	private TextfieldListener t_listener;
	private SelectionListener c_listener;
	
	// 입력 완료 함수
	private synchronized void input_over(String s)
	{
		input_string.setVisible(false);
		input_string.setFocusable(false);
		textfield_pool=s;
		notifyAll();
	}
	private synchronized void input_selection_over(int index)
	{
		System.out.println("Over");
		input_selection.setVisible(false);
		input_selection.setFocusable(false);
		selection_pool=index;
		notifyAll();
	}
	
	//////////////////////////////////////////////////////////
	//////// 3. Drawing RELATED : 사용자 정의한 drawNode에서 부르는 함수.
	//// 사각형과 텍스트 : Lowest Level
	public void rect(int x, int y, int dx, int dy)
	{
		g_.drawRect(x, y, dx, dy);
	}
	public void text(int x, int y, String msg)
	{
		g_.drawString(msg, x, y+yline);
	}
	public Position rect(Position start, Position end)
	{
		g_.drawRect(start.x, start.y, end.x-start.x, end.y-start.y);
		return end;
	}
	public Position rect(AreaRange a)
	{
		g_.drawRect(a.sx, a.sy, a.ex-a.sx, a.ey-a.sy);
		return a.getEnd();
	}
	public AreaRange text(Position start, String msg)
	{
		g_.drawString(msg, start.x, start.y+yline);
		return new AreaRange(start.x, start.y, start.x+msg.length()*xchar,start.y+yline,false);
	}
	public AreaRange text_and_rect(Position start, String msg)
	{
		Position p=start.addSpace();
		AreaRange a;
		String msg2=msg;
		if(msg==null) msg2="??";
		a=text(p, msg2);
		a.enclose();
		a.valid=true;
		rect(a);
		return a;
	}
	
	//// 기존 Area의 오른쪽 또는 아래에 그려서 합친 Area 구하기 : Low Level
	// 문자열
	public AreaRange draw_right_text(AreaRange prev, String msg)
	{
		AreaRange a=prev.merge(text(prev.nextRight(), msg));
		a.valid=false;
		return a;	
	}
	public AreaRange draw_bottom_text(AreaRange prev, String msg)
	{
		AreaRange a=prev.merge(text(prev.nextBottom(), msg));
		a.valid=false;
		return a;	
	}
	
	// 문자열 상자
	public AreaRange draw_right_textbox(AreaRange prev, String msg)
	{
		AreaRange a=prev.merge(text_and_rect(prev.nextRight(), msg));
		a.valid=true;
		return a;	
	}
	public AreaRange draw_bottom_textbox(AreaRange prev, String msg)
	{
		AreaRange a=prev.merge(text_and_rect(prev.nextBottom(), msg));
		a.valid=true;
		return a;	
	}
	
	//// 기존 Area의 오른쪽 또는 아래에 그려서 합친 Area를 구하여 Node에 해당 요소 등록하기 : High Level
	// 이름
	public AreaRange draw_name_right(Node n, AreaRange p)
	{
		String s="";
		if(n.getData()!=null) s=n.getData().toString();
		AreaRange a=text_and_rect(p.nextRight(), s);
		n.ar_name=a;
		return p.merge(a);
	}
	public AreaRange draw_name_bottom(Node n, AreaRange p)
	{
		String s="";
		if(n.getData()!=null) s=n.getData().toString();
		AreaRange a=text_and_rect(p.nextBottom(), s);
		n.ar_name=a;
		return p.merge(a);
	}
	// ... 필드
	public AreaRange draw_etc_right(Node n, AreaRange p)
	{
		AreaRange a=text_and_rect(p.nextRight(), "...");
		n.ar_etc=a;
		return p.merge(a);
	}
	public AreaRange draw_etc_bottom(Node n, AreaRange p)
	{
		AreaRange a=text_and_rect(p.nextBottom(), "...");
		n.ar_etc=a;
		return p.merge(a);
	}
	
	// Node
	public AreaRange draw_right(AreaRange prev, Node n)
	{
		Position draw=drawNode(n, prev.nextRight());
		return prev.merge(draw);
	}
	public AreaRange draw_bottom(AreaRange prev, Node n)
	{
		Position draw=drawNode(n, prev.nextBottom());
		return prev.merge(draw);
	}
	
	//// 해당 index부터 child 그리기 : Higher Level
	public AreaRange def_vertical_draw(Node n, Position p, int childstartIndex)
	{
		AreaRange a=new AreaRange(p,p,true);
		for(int loop=childstartIndex;loop<n.numChildren();loop++)
			a=draw_bottom(a,n.getChildNode(loop));
		return a;
	}
	public AreaRange def_horizontal_draw(Node n, Position p, int childstartIndex)
	{
		AreaRange a=new AreaRange(p,p,true);
		for(int loop=childstartIndex;loop<n.numChildren();loop++)
			a=draw_right(a,n.getChildNode(loop));
		return a;
	}

	//// Node를 다 그린 후 상자에 집어넣어 표시하기 : Highest Level
	public AreaRange enclose(Node n, AreaRange data, boolean valid)
	{
		data.enclose();
		data.valid=valid;
		if (valid)
			rect(data);
		if(n!=null)
			n.ar_current=data;
		return data;
	}
	public AreaRange draw_node_in_box(Node n, Position p, boolean valid)
	{
		Position p_=new Position(p.x,p.y);
		if(valid) p_.addSpace();
		Position data_=drawNode(n,p_);
		AreaRange data=new AreaRange(p_,data_,valid);
		if (valid)
		{
			data.enclose();
			rect(data);
		}
		if(n!=null)
			n.ar_current=data;
		return data;
	}

	//////////////////////////////////////////////////////////
}
