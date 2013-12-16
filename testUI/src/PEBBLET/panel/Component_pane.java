package PEBBLET.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import PEBBLET.panel_inside;
import manager.ComponentManager;
import manager.DefinitionManager;
import manager.Node;
import manager.RuleManager;

public class Component_pane extends JComponent {

	private DefinitionManager dm;
	private ComponentManager cm;
	
	private JPanel total, cardview,valueview,typebox,cardlist,buttonbox;
	private JButton b_card_add, b_card_delete;
	private JScrollPane comp_sc;
	private JComboBox<String> type_select;
	private JList<Node> card_list;
	private JScrollPane card_list_scroller;
	//private panel_inside p;
	
	public Component_pane(){
		dm = new DefinitionManager();
		//dm.updateVariableList(new DefinitionManager());
		
		cm = new ComponentManager();
		
		total=new JPanel();
		total.setPreferredSize(new Dimension(1000,600));
		total.setLayout(new BorderLayout(7,7));
		
		cardview=new JPanel();
		cardview.setLayout(new BorderLayout(7,7));
		cardview.setPreferredSize(new Dimension(210,600));
		total.add(cardview,BorderLayout.WEST);

		valueview=new JPanel();
		//valueview.setLayout(new BorderLayout());
		valueview.setBorder(BorderFactory.createLoweredBevelBorder());
		valueview.setPreferredSize(new Dimension(790,600));
		total.add(valueview,BorderLayout.CENTER);

		typebox=new JPanel();
		//typebox.setBorder(BorderFactory.createLoweredBevelBorder());
		typebox.setPreferredSize(new Dimension(210,40));
		cardview.add(typebox,BorderLayout.NORTH);
		
		type_select=new JComboBox<String>();
		type_select.setPreferredSize(new Dimension(100,30));
		typebox.add(new JLabel("Card Type "));
		typebox.add(type_select);

		cardlist=new JPanel();
		cardlist.setBorder(BorderFactory.createLoweredBevelBorder());
		cardlist.setPreferredSize(new Dimension(210,50));
		cardview.add(cardlist,BorderLayout.CENTER);
		
		ArrayList<Node> a=new ArrayList<Node>();
		Node temp=new Node();
		temp.setData("MyMy");
		a.add(temp);
		temp=new Node();
		temp.setData("MyMy2");
		a.add(temp);
		temp=new Node();
		temp.setData("MyMy3");
		a.add(temp);
		temp=new Node();
		temp.setData("MyMy4");
		a.add(temp);
		temp=new Node();
		temp.setData("MyMy5");
		a.add(temp);
		card_list = new JList<Node>(a.toArray(new Node[a.size()])); //data has type Object[]
		card_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		card_list.setLayoutOrientation(JList.VERTICAL);
		//card_list.setVisibleRowCount(-1);
		card_list_scroller = new JScrollPane(card_list);
		card_list_scroller.setPreferredSize(new Dimension(180, 470));
		cardlist.add(card_list_scroller);
		
		buttonbox=new JPanel();
		buttonbox.setBorder(BorderFactory.createLoweredBevelBorder());
		buttonbox.setPreferredSize(new Dimension(210,60));
		cardview.add(buttonbox,BorderLayout.SOUTH);
		
		b_card_add=new JButton("Add Card");
		b_card_add.setPreferredSize(new Dimension(90,50));
		buttonbox.add(b_card_add);
		b_card_delete=new JButton("Delete Card");
		b_card_delete.setPreferredSize(new Dimension(100,50));
		buttonbox.add(b_card_delete);
		
		
		this.setLayout(new BorderLayout());
		this.add(total);
		this.revalidate();
		
	}
	
	public JComponent get_scpane(){
		return this;
	}
	
	
	public void update_window_size()
	{
		//set_board_size();
	}
    
	
	private void set_board_size(Dimension d)
	{
		System.out.println(d);
		//rule_pane.setPreferredSize(d);
		//rule_pane.revalidate();
		//rule_pane.repaint();
	}

}
