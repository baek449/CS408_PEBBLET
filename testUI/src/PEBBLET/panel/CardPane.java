package PEBBLET.panel;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import PEBBLET.boxs.Action_box;
import PEBBLET.boxs.Number_box;
import PEBBLET.boxs.String_box;

import com.sun.corba.se.spi.ior.MakeImmutable;

import manager.DefinitionManager;
import manager.Node;

public class CardPane extends JPanel{
	private JPanel card_pane;
	private int preStatus = 0;
	private int endof_card_pane;
	private JButton add_item;
	private JLabel closeMark;
	public CardPane(final DefinitionManager dm, final int i, final JButton add_card, final Def_pane _this){
		
		endof_card_pane = 30;
		card_pane = new JPanel();
		JPanel title_pane = new JPanel();
		title_pane.setBounds(0,0, 330,20);
		title_pane.setLayout(null);
		JLabel title = new JLabel("Card");
		title.setBounds(0,0,100,20);
		title_pane.add(title);
		
		final JTextField title_text = new JTextField();
		title_text.setBounds(50,0,120,20);
		title_pane.add(title_text);
		
		JButton set_button = new JButton("Set");
		set_button.setBounds(180, 0, 70, 20);
		title_pane.add(set_button);
		final int[] temp = {i};
		set_button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dm.search(temp).setData(title_text.getText());
			}
		});
		
		JLabel open_mark = new JLabel("{");
		open_mark.setBounds(260,0,20,20);
		title_pane.add(open_mark);
		
		card_pane.setLayout(null);
		card_pane.add(title_pane);
		
		endof_card_pane +=30;
		add_item = new JButton("add");
		add_item.setBounds(50, endof_card_pane+5, 100,20);
		endof_card_pane +=30;
		card_pane.add(add_item);
		
		closeMark = new JLabel("}");
		closeMark.setBounds(0, endof_card_pane + 5, 20,20);
		card_pane.add(closeMark);
		endof_card_pane -=60;
		
		add_card_item(dm, i, add_card, _this);
		
		endof_card_pane += 60;
		
		add_item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				card_pane.setLayout(null);
				endof_card_pane -=60;
				add_card_item(dm, i, add_card, _this);
				add_item.setBounds(50, endof_card_pane+5, 100,20);
				endof_card_pane +=30;
				closeMark.setBounds(0, endof_card_pane +5, 20,20);
				endof_card_pane+=30;
				card_pane.setSize(new Dimension(800,endof_card_pane));
				card_pane.setPreferredSize(new Dimension(800,endof_card_pane));
				
//				_this.get_card_pane().setPreferredSize(new Dimension(800,endof_card_pane) );
				_this.set_card_pane_presize(800, endof_card_pane + 30);
				repaint();
				validate();
				
				_this.set_cards_pane(endof_card_pane );
				_this.set_total_end(_this.get_total_end());
//				_this

				add_card.setBounds(add_card.getX(), add_card.getY()+30,add_card.getWidth(), add_card.getHeight());
				
				_this.get_pane().setPreferredSize(new Dimension(900, _this.get_total_end() +30));
//				_this.get_pane().setSize(new Dimension(900, _this.get_total_end() + 60));
				
				card_pane.repaint();
				card_pane.validate();
				dm.getDefinition().getRoot().printAll();//for debugging
			}
		});

		
	}

	public CardPane(final DefinitionManager dm, final JButton add_card, final Def_pane _this, final Node input){
		
		endof_card_pane = 30;
		card_pane = new JPanel();
		JPanel title_pane = new JPanel();
		title_pane.setBounds(0,0, 330,20);
		title_pane.setLayout(null);
		JLabel title = new JLabel("Card");
		title.setBounds(0,0,100,20);
		title_pane.add(title);
		final JTextField title_text;
		if(!(input.getData()==null))
			title_text = new JTextField((String)input.getData());
		else
			title_text = new JTextField();
		title_text.setBounds(50,0,120,20);
		title_pane.add(title_text);
		
		JButton set_button = new JButton("Set");
		set_button.setBounds(180, 0, 70, 20);
		title_pane.add(set_button);
		set_button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				input.setData(title_text.getText());
			}
		});
		
		JLabel open_mark = new JLabel("{");
		open_mark.setBounds(260,0,20,20);
		title_pane.add(open_mark);
		
		card_pane.setLayout(null);
		card_pane.add(title_pane);
		
		endof_card_pane +=30;
		add_item = new JButton("add");
		add_item.setBounds(50, endof_card_pane+5, 100,20);
		endof_card_pane +=30;
		card_pane.add(add_item);
		
		closeMark = new JLabel("}");
		closeMark.setBounds(0, endof_card_pane + 5, 20,20);
		card_pane.add(closeMark);
		endof_card_pane -=60;
		for(int j = 0; j < input.numChildren(); j++){
			add_card_item(dm, add_card, _this, input.getChildNode(j));
		}
		endof_card_pane += 60;
		
		add_item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				card_pane.setLayout(null);
				endof_card_pane -=60;
				add_card_item(dm, 3, add_card, _this);
				add_item.setBounds(50, endof_card_pane+5, 100,20);
				endof_card_pane +=30;
				closeMark.setBounds(0, endof_card_pane +5, 20,20);
				endof_card_pane+=30;
				card_pane.setSize(new Dimension(800,endof_card_pane));
				card_pane.setPreferredSize(new Dimension(800,endof_card_pane));
				
//				_this.get_card_pane().setPreferredSize(new Dimension(800,endof_card_pane) );
				_this.set_card_pane_presize(800, endof_card_pane + 30);
				repaint();
				validate();
				
				_this.set_cards_pane(endof_card_pane );
				_this.set_total_end(_this.get_total_end());
//				_this

				add_card.setBounds(add_card.getX(), add_card.getY()+30,add_card.getWidth(), add_card.getHeight());
				
				_this.get_pane().setPreferredSize(new Dimension(900, _this.get_total_end() +30));
//				_this.get_pane().setSize(new Dimension(900, _this.get_total_end() + 60));
				
				card_pane.repaint();
				card_pane.validate();
				dm.getDefinition().getRoot().printAll();//for debugging
			}
		});

		
	}
	
	
	public int get_endof(){
		return endof_card_pane;
	}
	
	public void add_card_item(final DefinitionManager dm, int index, final JButton add_button, final Def_pane _this){
		
		final JPanel card_item_pane = new JPanel();
		card_item_pane.setLayout(null);

		final JComboBox<String> box_type = new JComboBox<String>();
		final int[] temp = {index};
		box_type.setBounds(0,0,120,20);
		int i = 1;
		box_type.addItem("Select type");
		Action_box select = new Action_box();
		select.set_parent(dm.search(temp));
		while(i <= dm.get_selection_card_del().length){
			box_type.addItem(dm.get_selection_card_del()[i-1]);
			i++;
		}
		card_item_pane.add(box_type);
		
		card_pane.setLayout(null);
		card_item_pane.setBounds(50, endof_card_pane + 5, 900, 30);
		endof_card_pane += 30;
		card_pane.add(card_item_pane);
		final Number_box numbox = new Number_box();
		final String_box stringbox = new String_box();
		final Action_box actbox = new Action_box();
		
		box_type.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				String item = (String)e.getItem();
				card_item_pane.setLayout(null);
				if(e.getStateChange()==ItemEvent.SELECTED){
					int del_box_index = getComponentIndex(card_item_pane)-3;
					switch(item){
						case "(Cancle)":
							box_type.setSelectedItem(box_type.getItemAt(preStatus));
							break;
						case "Number []":
							preStatus = 1;
							dm.search(temp).deleteChildNode(del_box_index);
							card_item_pane.removeAll();
							card_item_pane.setLayout(null);
							card_item_pane.add(box_type);
							numbox.addtoPanel(card_item_pane, 125, 0);
							card_pane.repaint();
							card_pane.validate();
							numbox.set_parent(dm.search(temp));
							break;
						case "String []":
							preStatus = 2;
							dm.search(temp).deleteChildNode(del_box_index);
							card_item_pane.removeAll();
							card_item_pane.setLayout(null);
							card_item_pane.add(box_type);
							stringbox.addtoPanel(card_item_pane, 125, 0);
							card_pane.repaint();
							card_pane.validate();
							stringbox.set_parent(dm.search(temp));
							break;
						case "Action []":
							preStatus = 3;
							dm.search(temp).deleteChildNode(del_box_index);
							card_item_pane.removeAll();
							card_item_pane.setLayout(null);
							card_item_pane.add(box_type);
							actbox.addtoPanel(card_item_pane, 125, 0);
							card_pane.repaint();
							card_pane.validate();
							actbox.set_parent(dm.search(temp));
							break;
						case "(Delete)":
							int total = card_pane.getComponentCount();
							card_pane.remove(card_item_pane);
							switch(preStatus){
								case 1:
									dm.search(temp).deleteChildNode(numbox.get_node());
									break;
								case 2:
									dm.search(temp).deleteChildNode(stringbox.get_node());
									break;
								case 3:
									dm.search(temp).deleteChildNode(actbox.get_node());
									break;
								case 0:
									dm.search(temp).deleteChildNode(del_box_index);
									break;
							}
							
							card_pane.getComponent(2).setLocation(card_pane.getComponent(2).getX(), card_pane.getComponent(2).getY()-30);
							card_pane.getComponent(1).setLocation(card_pane.getComponent(1).getX(), card_pane.getComponent(1).getY()-30);
							add_button.setLocation(add_button.getX(), add_button.getY()-30);
							
							for(int i = del_box_index+3; i < total-1; i++){
								card_pane.getComponent(i).setLocation(card_pane.getComponent(i).getX(), card_pane.getComponent(i).getY()-30);
								card_pane.repaint();
								card_pane.validate();
							}
							int[] temp = {3};
							
							endof_card_pane -= 30;
							card_pane.setSize(new Dimension(890, endof_card_pane + 5));
							_this.get_pane().setPreferredSize(new Dimension(900, _this.get_total_end() + 20));
							repaint();
							validate();
														
							break;
						default:
							preStatus = 0;
							card_item_pane.removeAll();
							card_item_pane.setLayout(null);
							card_item_pane.add(box_type);
							card_pane.repaint();
							card_pane.validate();
							break;
					}
					preStatus = 0;
				}
			}
		});
		
	}
	
public void add_card_item(final DefinitionManager dm, final JButton add_button, final Def_pane _this,final Node input){
		
		final JPanel card_item_pane = new JPanel();
		card_item_pane.setLayout(null);

		final JComboBox<String> box_type = new JComboBox<String>();
		
		box_type.setBounds(0,0,120,20);
		int i = 1;
		box_type.addItem("Select type");
		Action_box select = new Action_box();
		while(i <= dm.get_selection_card_del().length){
			box_type.addItem(dm.get_selection_card_del()[i-1]);
			i++;
		}
		card_item_pane.add(box_type);
		
		card_pane.setLayout(null);
		card_item_pane.setBounds(50, endof_card_pane + 5, 900, 30);
		endof_card_pane += 30;
		card_pane.add(card_item_pane);
		final Number_box numbox = new Number_box();
		final String_box stringbox = new String_box();
		final Action_box actbox = new Action_box();
		
		box_type.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				String item = (String)e.getItem();
				card_item_pane.setLayout(null);
				if(e.getStateChange()==ItemEvent.SELECTED){
					int del_box_index = getComponentIndex(card_item_pane)-3;
					switch(item){
						case "(Cancle)":
							box_type.setSelectedItem(box_type.getItemAt(preStatus));
							break;
						case "Number []":
							preStatus = 1;
							input.deleteChildNode(del_box_index);
							card_item_pane.removeAll();
							card_item_pane.setLayout(null);
							card_item_pane.add(box_type);
							numbox.addtoPanel(card_item_pane, 125, 0);
							card_pane.repaint();
							card_pane.validate();
							numbox.set_parent(input);
							break;
						case "String []":
							preStatus = 2;
							input.deleteChildNode(del_box_index);
							card_item_pane.removeAll();
							card_item_pane.setLayout(null);
							card_item_pane.add(box_type);
							stringbox.addtoPanel(card_item_pane, 125, 0);
							card_pane.repaint();
							card_pane.validate();
							stringbox.set_parent(input);
							break;
						case "Action []":
							preStatus = 3;
							input.deleteChildNode(del_box_index);
							card_item_pane.removeAll();
							card_item_pane.setLayout(null);
							card_item_pane.add(box_type);
							actbox.addtoPanel(card_item_pane, 125, 0);
							card_pane.repaint();
							card_pane.validate();
							actbox.set_parent(input);
							break;
						case "(Delete)":
							int total = card_pane.getComponentCount();
							card_pane.remove(card_item_pane);
							switch(preStatus){
								case 1:
									input.deleteChildNode(numbox.get_node());
									break;
								case 2:
									input.deleteChildNode(stringbox.get_node());
									break;
								case 3:
									input.deleteChildNode(actbox.get_node());
									break;
								case 0:
									input.deleteChildNode(del_box_index);
									break;
							}
							
							card_pane.getComponent(2).setLocation(card_pane.getComponent(2).getX(), card_pane.getComponent(2).getY()-30);
							card_pane.getComponent(1).setLocation(card_pane.getComponent(1).getX(), card_pane.getComponent(1).getY()-30);
							add_button.setLocation(add_button.getX(), add_button.getY()-30);
							
							for(int i = del_box_index+3; i < total-1; i++){
								card_pane.getComponent(i).setLocation(card_pane.getComponent(i).getX(), card_pane.getComponent(i).getY()-30);
								card_pane.repaint();
								card_pane.validate();
							}
							
							endof_card_pane -= 30;
							card_pane.setSize(new Dimension(890, endof_card_pane + 5));
							_this.get_pane().setPreferredSize(new Dimension(900, _this.get_total_end() + 20));
							repaint();
							validate();
														
							break;
						default:
							preStatus = 0;
							card_item_pane.removeAll();
							card_item_pane.setLayout(null);
							card_item_pane.add(box_type);
							card_pane.repaint();
							card_pane.validate();
							break;
					}
					preStatus = 0;
				}
			}
		});
		
		switch(input.get_node_type()){//set selected item menu from node
		
			case nd_num:
	//			box_type.setSelectedIndex(2);
				//set data from node
				if(!(input.getData() == null))
						numbox.set_title((String)input.getData());
				for(int j = 0; j < input.numChildren(); j++){
					if(!(input.getChildNode(j).getData()==null))
						numbox.add_values_box(Integer.toString((Integer)input.getChildNode(j).getData()), input.getChildNode(j));
					else
						numbox.add_values_box("", input.getChildNode(j));
				}
				numbox.set_node(input);
				numbox.addtoPanel(card_item_pane, 125, 0);
				numbox.reset_pos();
				break;
			case nd_str:
	//			box_type.setSelectedIndex(3);
				//set data from node
				if(!(input.getData() == null))
					stringbox.set_title((String)input.getData());
				for(int j = 0; j < input.numChildren();j++){
					if(!(input.getChildNode(j).getData()==null))
						stringbox.add_values_box((String)input.getChildNode(j).getData(), input.getChildNode(j));
					else
						stringbox.add_values_box("", input.getChildNode(j));
				}
				stringbox.set_node(input);
				stringbox.addtoPanel(card_item_pane, 125, 0);
				stringbox.reset_pos();
				break;
			case nd_action:
	//			box_type.setSelectedIndex(4);
				if(!(input.getData()==null))
					actbox.set_Action_input((String)input.getData());
				actbox.set_node(input);
				actbox.addtoPanel(card_item_pane, 125, 0);
				break;
			default:
				break;
		}
		reset_pos(add_button, _this);
		
		
	}
	
	public static final int getComponentIndex(JComponent component) {
	    if (component != null && component.getParent() != null) {
	      Container c = component.getParent();
	      for (int i = 0; i < c.getComponentCount(); i++) {
	        if (c.getComponent(i) == component)
	          return i;
	      }
	    }

	    return -1;
	}
	
	public void addtoPanel(JComponent comp, int x, int y){
		comp.setLayout(null);
		card_pane.setBounds(x,y,800,endof_card_pane);
//		set_button.setBounds(x+125, y, 30, 20);
		comp.add(card_pane);
//		comp.add(set_button);
	}
	
	public int get_card_endof(){
		
		return endof_card_pane;
	}
	public void reset_pos(JButton add_card, Def_pane _this){
		add_item.setBounds(50, endof_card_pane+5, 100,20);
		endof_card_pane +=30;
		closeMark.setBounds(0, endof_card_pane +5, 20,20);
		endof_card_pane+=30;
		card_pane.setSize(new Dimension(800,endof_card_pane));
		card_pane.setPreferredSize(new Dimension(800,endof_card_pane));
		
//		_this.get_card_pane().setPreferredSize(new Dimension(800,endof_card_pane) );
		_this.set_card_pane_presize(800, endof_card_pane + 30);
		repaint();
		validate();
		
		_this.set_cards_pane(endof_card_pane );
		_this.set_total_end(_this.get_total_end());
//		_this

		add_card.setBounds(add_card.getX(), add_card.getY()+30,add_card.getWidth(), add_card.getHeight());
		
		_this.get_pane().setPreferredSize(new Dimension(900, _this.get_total_end() +30));
//		_this.get_pane().setSize(new Dimension(900, _this.get_total_end() + 60));
		
		card_pane.repaint();
		card_pane.validate();
		endof_card_pane -=60;
	}

}
