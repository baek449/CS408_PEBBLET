package PEBBLET;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import PEBBLET.panel.Rule_pane;
import manager.Node;
import manager.RuleCase;
import manager.RuleManager;

public class panel_inside extends JPanel
{
	private JComboBox<String> combo;
	private JPanel inside;
	private JButton add_button;
	
	private JTextField text;
	
	private Node n;
	private boolean isAddable;
	
	private RuleManager rm;
	private Rule_pane rulepane;
	
	public void remove_combo()
	{
		this.remove(combo);
	}
	
	public void replace(Node to_be_replaced)
	{
		if(to_be_replaced==null) return;
		n.replace(to_be_replaced);
		if(!rm.isSelection(n))
		{
			text.setText((String)n.getData());
			return;
		}
		buildup_with_node(n);
		reset_children_nodes(rm);
		inside.revalidate();
		rulepane.update_window_size();
	}
	public void setNode(Node n_)
	{
		n=n_;
		if(!rm.isSelection(n))
		{
			text.setText((String)n.getData());
			return;
		}
		buildup_with_node(n);
		reset_children_nodes(rm);
		
	}
	
	public panel_inside() // 직접 부르는건 Discouraged. 여기서 모든 컴포넌트의 크기를 조절함.
	{
		combo=new JComboBox<String>();
		combo.setPreferredSize(new Dimension(150,20));

		
		inside=new JPanel();
		//inside.setLayout(new FlowLayout(FlowLayout.LEFT));
		inside.setLayout(new VerticalLayout(5,VerticalLayout.LEFT,VerticalLayout.TOP));
		inside.setMinimumSize(new Dimension(50,20));
		//inside.setBorder(BorderFactory.createLineBorder(Color.RED));
		
		add_button=new JButton("add");
		add_button.setPreferredSize(new Dimension(70,20));
		text=new JTextField("Insert Here");
		text.setPreferredSize(new Dimension(40,20));
		
		//this.setLayout(new FlowLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		this.add(combo);
		this.add(inside);
		this.add(add_button);
		//this.setAlignmentX(LEFT_ALIGNMENT);
		buildup(false, false, true);
	}
	
	public panel_inside(RuleManager rm_, boolean isVertical, boolean isTextfield, boolean isAddable_, Rule_pane rulepane_)
	{
		this();
		rm=rm_;
		rulepane=rulepane_;
		add_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				onAdd(rm);
			}});
		text.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				n.setData(text.getText());
				text.revalidate();
				rulepane.update_window_size();
			}});
		combo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					onSelect(rm, combo.getSelectedIndex());
				}
			}});
		buildup(isVertical, isTextfield, isAddable_);
	}
	
	public void set_inside_layout(boolean isvertical)
	{
		if(isvertical) inside.setLayout(new VerticalLayout(VerticalLayout.TOP,5,5));
		else inside.setLayout(new FlowLayout(FlowLayout.LEFT));
	}
	public void add_inside(panel_inside a)
	{
		inside.add(a);
	}

	private void buildup(boolean isVertical, boolean isTextfield, boolean isAddable_)
	{
		if(isTextfield)
		{
			this.removeAll();
			this.add(text);
			return;
		}
		isAddable=isAddable_;
		
		if (isVertical) inside.setLayout(new VerticalLayout(5,VerticalLayout.LEFT,VerticalLayout.TOP));
		else inside.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		this.remove(add_button);
		if(isAddable) this.add(add_button);
	}
	
	private void buildup_with_node(Node n)
	{
		boolean v,s;
		v=false;
		if(n.getData()!=null) v=n.getData().equals(RuleCase.action_multiple) || n.getData().equals(RuleCase.action_act);
		s=rm.isSelection(n);
		buildup(v, !s, rm.isAddAvailable(n));
	}

	private void reset_children_nodes(RuleManager rm)
	{
		inside.removeAll();
		if(combo.getItemCount()==0)
		{
			String[] box_item = rm.getSelectionCases(n);
			for(int i = 0; i < box_item.length ; i++){
				combo.addItem(box_item[i]);
			}
		}
		
		Node c;
		panel_inside p;
		boolean v,s;
		for(int loop=0;loop<n.numChildren();loop++)
		{
			c=n.getChildNode(loop);
			v=false;
			if(c.getData()!=null) v=c.getData().equals(RuleCase.action_multiple) || c.getData().equals(RuleCase.action_act);
			c.printAll();
			System.out.println(v);
			s=rm.isSelection(c);
			p=new panel_inside(rm, v, !s, rm.isAddAvailable(c),rulepane);
			p.setNode(c);
			inside.add(p);
		}
		if(isAddable) inside.add(add_button);
	}
	
	private void onAdd(RuleManager rm)
	{
		Node r=rm.onAddNew(n);
		if (r==null) return;
		n.addChildNode(r);
		inside.remove(add_button);

		panel_inside p;
		boolean v=false,s;
		if(r.getData()!=null) v=r.getData().equals(RuleCase.action_multiple) || r.getData().equals(RuleCase.action_act);
		s=rm.isSelection(r);
		p=new panel_inside(rm, v, !s, rm.isAddAvailable(r),rulepane);
		p.setNode(r);
		inside.add(p);
		
		if(isAddable) inside.add(add_button);
		inside.revalidate();
		rulepane.update_window_size();
	}

	private void onSelect(RuleManager rm, int index)
	{
		Node r=rm.applySelectionCases(n, index);
		replace(r);
		inside.revalidate();
		rulepane.update_window_size();
	}

}