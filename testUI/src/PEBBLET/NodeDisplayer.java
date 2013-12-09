package PEBBLET;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import manager.Node;

public abstract class NodeDisplayer extends JComponent implements MouseListener{
	public class WideComboBox<E> extends JComboBox<E>{ 
		 
	    public WideComboBox() { 
	    } 
	 
	    public WideComboBox(final E items[]){ 
	        super(items); 
	    } 
	 
	    public WideComboBox(Vector<E> items) { 
	        super(items); 
	    } 
	 
	    public WideComboBox(ComboBoxModel<E> aModel) { 
	        super(aModel); 
	    } 
	 
	    private boolean layingOut = false; 
	 
	    public void doLayout(){ 
	        try{ 
	            layingOut = true; 
	            super.doLayout(); 
	        }finally{ 
	            layingOut = false; 
	        } 
	    } 
	 
	    public Dimension getSize(){ 
	        Dimension dim = super.getSize(); 
	        if(!layingOut) 
	            dim.width = Math.max(dim.width, getPreferredSize().width); 
	        return dim; 
	    } 
	}
	
	public JScrollPane pane;
	private Graphics g_;
	private JTextField input_string;
	private String textfield_pool;
	private int selection_pool;
	private String textfield_original;
	private WideComboBox<String> input_selection;

	// ǥ���� ��ǥ ���
	private Node target;
	private Position endpoint;
	private Dimension currentDimension;


	public static int xspace=4;
	public static int xtab=50;
	public static int yspace=4;
	public static int xdist=8;
	public static int ydist=8;
	public static int yline=10;
	public static int xchar=8;
	//////////////////////////////////////////////////////////
	//////// 0. ����ڿ� �ַ� ��ȣ�ۿ��ϴ� �Լ���

	// fillupSelection�� �θ��� ���� �Լ�.
	public abstract void call_fillupSelection(Node parent, int index, boolean isName);
	
	// ��ǥ ��� ����
	public void setTargetNode(Node target_)
	{
		target=target_;
	}
	// Node�� �׸��� ����� ��� �ִ� ���� �Լ�. �� �׸� �� ������ �Ʒ� ���� �����Ѵ�.
	public abstract Position drawNode(Node n, Position p);
	
	// �Է��� �޾� ����� �ѷ��ִ� �Լ�
	public synchronized String UI_input_string(AreaRange size, String current_data)
	{
		//input_string.setSize(size.ex-size.sx, size.ey-size.sy);
		//input_string.setLocation(size.sx, size.sy);
		if(current_data==null) current_data="";
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
			System.out.println("Waiting...");
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
	
	// �����ڿ� �׸��� �Լ�
	public NodeDisplayer()
	{
		pane = new JScrollPane();
		
		input_string=new JTextField();
		input_selection=new WideComboBox<String>();
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
		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		pane.add(this);
	}
	public final void paint(Graphics g) {
    	g_=g;
		endpoint=drawNode(target, new Position(0,0));
		
		currentDimension = new Dimension(endpoint.x, endpoint.y);
		System.out.println(currentDimension);
	}
	
	public Dimension getcurrentdimension(){
		return currentDimension;
	}
	
	//////////////////////////////////////////////////////////
	//////// 1. Input RELATED
	// Ŭ���ϸ� �ش� ��带 ã�� fillupSelection�� �ҷ��ִ� ��ҵ�
	private int prev_index=0;
	private boolean click(Node n, Position p)
	{
		// �ش� ����.
		if (!n.ar_current.inside(p))
			return false;
		if(n.ar_name.isvalid() && n.ar_name.inside(p))
		{
			// �̸� �ʵ� ������.
			call_fillupSelection(n.getParent(),prev_index,true);
			repaint();
			return true;
		}
		else if (n.ar_etc.isvalid() && n.ar_etc.inside(p))
		{
			// ... �ʵ� ������.
			call_fillupSelection(n,n.numChildren(),false);
			repaint();
			return true;
		}
		// �ڽ� ������.
		int prv_index=prev_index;
		for(int loop=0;loop<n.numChildren();loop++)
		{
			prev_index=loop;
			if (click(n.getChildNode(loop),p))
				return true;
		}
		prev_index=prv_index;
		
		// ���� ��� ������
		if (!n.ar_current.isvalid())
			return true;
		// valid�� ��� ���� ����
		call_fillupSelection(n.getParent(),prev_index,false);
		repaint();
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
	
	// �̺�Ʈ ������ (Ŭ�� �ν�)	  
	  @Override
	public synchronized void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		  if(w==null || !w.isAlive())
		  {
			  System.out.println("Click");
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
	// ���� ��ҿ� ���� �̺�Ʈ ������
	private class TextfieldListener implements FocusListener, ActionListener {
	@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			input_over(input_string.getText());
			
		}
		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			//input_over(textfield_original);	
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
			// input_selection_over(-1);
		}
	}
	
	private TextfieldListener t_listener;
	private SelectionListener c_listener;
	
	// �Է� �Ϸ� �Լ�
	private synchronized void input_over(String s)
	{
		input_string.setVisible(false);
		input_string.setFocusable(false);
		textfield_pool=s;
		System.out.println(input_string.getText()+" before notify");
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
	//////// 3. Drawing RELATED : ����� ������ drawNode���� �θ��� �Լ�.
	//// �簢���� �ؽ�Ʈ : Lowest Level
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
	
	//// ���� Area�� ������ �Ǵ� �Ʒ��� �׷��� ��ģ Area ���ϱ� : Low Level
	// ���ڿ�
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
	
	// ���ڿ� ����
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
	
	//// ���� Area�� ������ �Ǵ� �Ʒ��� �׷��� ��ģ Area�� ���Ͽ� Node�� �ش� ��� ����ϱ� : High Level
	// �̸�
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
	// ... �ʵ�
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
	
	//// �ش� index���� child �׸��� : Higher Level
	public AreaRange draw_vertical(Node n, Position p, int childstartIndex)
	{
		AreaRange a=new AreaRange(p,p,true);
		for(int loop=childstartIndex;loop<n.numChildren();loop++)
			a=draw_bottom(a,n.getChildNode(loop));
		return a;
	}
	public AreaRange draw_horizontal(Node n, Position p, int childstartIndex)
	{
		AreaRange a=new AreaRange(p,p,true);
		for(int loop=childstartIndex;loop<n.numChildren();loop++)
			a=draw_right(a,n.getChildNode(loop));
		return a;
	}

	//// Node�� �� �׸� �� ���ڿ� ����־� ǥ���ϱ� : Highest Level
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
