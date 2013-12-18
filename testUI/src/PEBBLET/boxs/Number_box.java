package PEBBLET.boxs;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import manager.Node;
import manager.NodeType;

public class Number_box extends JComponent {
	
	private JTextField title;
	private JLabel colon;
	private ArrayList<NumTextField> values;
	private JButton add_button;
	private JButton remove_button;
	private JButton set_button;
	
	private JPanel Number_box_pane;
	private int endof_box_pos;
	
	private Node Numbox_node;
	
	
	public Number_box(){
		Number_box_pane = new JPanel(true);
		
		endof_box_pos = 60;
		
		Numbox_node = new Node();
		Numbox_node.set_node_type(NodeType.nd_num);
		Number_box_pane.setLayout(null);
		
		Number_box_pane.setSize(900,30);
		

		
		values = new ArrayList<NumTextField>();
		
		title = new JTextField();
		title.setBounds(0,0,50,20);
		
		colon = new JLabel(":");
		colon.setBounds(55,0,5,20);
		
		
		
		add_button = new JButton("+");
		add_button.setBounds(endof_box_pos + 5 , 0, 20,20);
		remove_button = new JButton("-");
		remove_button.setBounds(endof_box_pos + 30, 0, 20, 20);
		set_button = new JButton("set");
		set_button.setBounds(endof_box_pos + 55, 0, 30, 20);
//		Number_box_pane.setSize(endof_box_pos + 165, 20);
//		System.out.println(Number_box_pane.getSize());

		
		add_button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				add_values_box();
				add_button.setBounds(endof_box_pos + 5 , 0, 20,20);
				remove_button.setBounds(endof_box_pos + 30, 0, 20, 20);
				set_button.setBounds(endof_box_pos + 55, 0, 30, 20);
				Number_box_pane.setSize(endof_box_pos + 110, 20);
			}
		});

		remove_button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(values.size() > 0){
					remove_value_box();				
					add_button.setBounds(endof_box_pos + 5 , 0, 20,20);
					remove_button.setBounds(endof_box_pos + 30, 0, 20, 20);
					set_button.setBounds(endof_box_pos + 55, 0, 30, 20);
					Number_box_pane.setSize(endof_box_pos + 110, 20);
				}
			}
		});
		
		set_button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Numbox_node.setData(title.getText());
				System.out.println(Numbox_node.getData());
				System.out.println(Numbox_node);
				for(int a =0; a<values.size(); a++){
					values.get(a).get_node().setData(values.get(a).getText());
					System.out.println(values.get(a).get_node().getData());
					System.out.println(values.get(a).get_node());
				}
				
			}
		});
		
		
		Number_box_pane.add(add_button);
		Number_box_pane.add(remove_button);		
		Number_box_pane.add(set_button);
		Number_box_pane.add(title);
		Number_box_pane.add(colon);
//		add_values_box();
		
		
		
	}
	
	public void add_values_box(){
		NumTextField new_value = new NumTextField();
		
		int i = values.size();
		
		new_value.setBounds(60 + 55*i, 0, 50, 20);
//		Number_box_pane.setLayout(null);
//		Number_box_pane.add(new_value); //add to pane
		
		new_value.addtoPanel(Number_box_pane, 60+55*i, 0);
		new_value.set_parent(Numbox_node);
		
		values.add(new_value); 			//add to list
		endof_box_pos += 55;
		
		
	}
	public void add_values_box(String input, Node inputNode){
		NumTextField new_value = new NumTextField(input, inputNode);
		
		int i = values.size();
		
		new_value.setBounds(60 + 55*i, 0, 50, 20);
//		Number_box_pane.setLayout(null);
//		Number_box_pane.add(new_value); //add to pane
		
		new_value.addtoPanel(Number_box_pane, 60+55*i, 0);
		
		values.add(new_value); 			//add to list
		endof_box_pos += 55;
		
		
	}
	public void remove_value_box(){
//		Number_box_pane.remove(values.get(values.size()-1));
		Number_box_pane.remove(Number_box_pane.getComponentCount()-1);

		endof_box_pos -= 55;
		
		Numbox_node.deleteChildNode(values.size()-1);
		
		values.remove(values.size()-1);
		
	}
	
	
	public int get_endof_boxarray(){
		return endof_box_pos;
	}
	
	public void addtoPanel(JComponent comp, int x, int y){
		comp.setLayout(null);
		Number_box_pane.setBounds(x,y,endof_box_pos+110,20);
		comp.add(Number_box_pane);
	}
	
	public ArrayList<NumTextField> get_values(){
		return values;
	}
	
	public int get_values(int i){
		
		return values.get(i).getText();
	}

	
	public void set_values(ArrayList<NumTextField> _inputarray){
		int i = 0;
		values.clear();
		while(i < _inputarray.size()){
			values.add(_inputarray.get(i));
			
			i++;
		}
	}
	
//	public void set_values(ArrayList<String> _inputarray){
//		
//	}
	
	public void set_values(int i, int _input){
		values.get(i).setText(Integer.toString(_input));
	}
	
	public void set_title(String _input){
		title.setText(_input);
	}
	public String get_title(){
		return title.getText();
	}
	
	public void set_Num_NodeData(String _input){
		Numbox_node.setData(_input);
	}
	
	public Object get_Num_NodeData(){
		return Numbox_node.getData();
	}
	
	public Node get_node(){
		return Numbox_node;
	}
	public void set_node(Node input){
		Numbox_node = input;
	}
	public void set_parent(Node node_parent){
		node_parent.addChildNode(Numbox_node);
	}
	
	public void reset_pos(){
		add_button.setBounds(endof_box_pos + 5 , 0, 20,20);
		remove_button.setBounds(endof_box_pos + 30, 0, 20, 20);
		set_button.setBounds(endof_box_pos + 55, 0, 30, 20);
		Number_box_pane.setSize(endof_box_pos + 110, 20);
	}
	
	
}
