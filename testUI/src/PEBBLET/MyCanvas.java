package PEBBLET;

import java.awt.Graphics;
import java.awt.GridLayout;
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

import manager.*;

public class MyCanvas extends JComponent implements MouseListener{
	private Graphics g_;
	private DefinitionManager dm;
	private JTextField input_string;
	private String textfield_pool;
	private int selection_pool;
	private String textfield_original;
	private JComboBox<String> input_selection;
	
		public class TextfieldListener implements FocusListener, ActionListener {

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
			//input_over(textfield_original);
			
		}
		
	}
	public synchronized void input_over(String s)
	{
		input_string.setVisible(false);
		input_string.setFocusable(false);
		textfield_pool=s;
		notifyAll();
	}

	public synchronized void input_selection_over(int index)
	{
		System.out.println("Over");
		input_selection.setVisible(false);
		input_selection.setFocusable(false);
		selection_pool=index;
		notifyAll();
	}

	public class SelectionListener implements FocusListener, ActionListener {

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
	TextfieldListener t_listener;
	SelectionListener c_listener;
	public MyCanvas(DefinitionManager dm_)
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
		
		dm=dm_;
		dm.setUI(this);
		addMouseListener(this);
	}
	  public void paint(Graphics g) {
		  g_=g;
		  dm.drawAll(new Position(0,0));
	  }
	  
	  public void rect(int x, int y, int dx, int dy)
	  {
		  g_.drawRect(x, y, dx, dy);
	  }
	  public void text(int x, int y, String msg)
	  {
		  g_.drawString(msg, x, y+Node.yline);
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
		  //System.out.println(msg+ ": in "+start.x+ ","+start.y);
		  g_.drawString(msg, start.x, start.y+Node.yline);
		  return new AreaRange(start.x, start.y, start.x+msg.length()*Node.xchar,start.y+Node.yline,false);
	  }
	  public AreaRange text_and_rect(Position start, String msg)
	  {
		  //System.out.println(msg+ ": inside "+start.x+ ","+start.y);
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
	public class Worker extends Thread
	{
		Position p;
		
		public Worker(Position p_)
		{
			p=p_;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			dm.def_click(dm.getDefinition().getRoot(),p);
		}
		
	}
	Worker w;
	  
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
			return "";
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
	
}
