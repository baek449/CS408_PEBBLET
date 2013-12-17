package PEBBLET.boxs;

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

public class String_box extends JComponent{
	private JTextField title;
	private JLabel colon;
	private ArrayList<StringTextField> values;
	private JButton add_button;
	private JButton remove_button;
	private JButton set_button;
	
	private Node Strbox_node;
	
	private JPanel String_box_pane;
	private int endof_box_pos;
	public String_box(){
		String_box_pane = new JPanel(true);
		
		endof_box_pos = 60;
		
		String_box_pane.setLayout(null);
		
		Strbox_node = new Node();
		Strbox_node.set_node_type(NodeType.nd_str);

		
		values = new ArrayList<StringTextField>();
		
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
		
		add_button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				add_values_box();
				add_button.setBounds(endof_box_pos + 5 , 0, 20,20);
				remove_button.setBounds(endof_box_pos + 30, 0, 20, 20);
				set_button.setBounds(endof_box_pos + 55, 0, 30, 20);
				String_box_pane.setSize(endof_box_pos + 110, 20);
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
					String_box_pane.setSize(endof_box_pos + 110, 20);
				}
			}
		});
		
		set_button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Strbox_node.setData(title.getText());
				for(int a =0; a<values.size(); a++){
					values.get(a).get_node().setData(values.get(a).getText());
				}
				
			}
		});
		
		String_box_pane.add(add_button);
		String_box_pane.add(remove_button);	
		String_box_pane.add(set_button);
		String_box_pane.add(title);
		String_box_pane.add(colon);
//		add_values_box();
		
		
		
	}
	
	public void add_values_box(){
		StringTextField new_value = new StringTextField();
		
		int i = values.size();
		
		new_value.setBounds(60 + 55*i, 0, 50, 20);
//		String_box_pane.setLayout(null);
//		String_box_pane.add(new_value); //add to pane
		new_value.addtoPanel(String_box_pane, 60+55*i, 0);
		
		new_value.set_parent(Strbox_node);
		values.add(new_value); 			//add to list
		endof_box_pos += 55;
		
	}
	public void add_values_box(String input, Node inputNode){
		StringTextField new_value = new StringTextField(input, inputNode);
		
		int i = values.size();
		
		new_value.setBounds(60 + 55*i, 0, 50, 20);
//		String_box_pane.setLayout(null);
//		String_box_pane.add(new_value); //add to pane
		new_value.addtoPanel(String_box_pane, 60+55*i, 0);
		
		values.add(new_value); 			//add to list
		endof_box_pos += 55;
	}
	public void remove_value_box(){
		
//		String_box_pane.remove(values.get(values.size()-1));
		String_box_pane.remove(String_box_pane.getComponentCount()-1);
		endof_box_pos -= 55;
		
		Strbox_node.deleteChildNode(values.size()-1);
		
		values.remove(values.size()-1);
		
	}
	
	
	public int get_endof_boxarray(){
		return endof_box_pos;
	}
	
	public void addtoPanel(JComponent comp, int x, int y){
		comp.setLayout(null);
		String_box_pane.setBounds(x,y,endof_box_pos+110,20);
		comp.add(String_box_pane);
	}
	
	public ArrayList<StringTextField> get_values(){
		return values;
	}
	
	public String get_values(int i){
		
		return values.get(i).getText();
	}
	
	public void set_values(ArrayList<StringTextField> _inputarray){
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
	
	public void set_values(int i, String _input){
		values.get(i).setText(_input);
	}
	
	public void set_title(String _input){
		title.setText(_input);
	}
	public String get_title(){
		return title.getText();
	}
	
	public void set_Str_NodeData(String _input){
		Strbox_node.setData(_input);
	}
	
	public Object get_Num_NodeData(){
		return Strbox_node.getData();
	}
	
	public Node get_node(){
		return Strbox_node;
	}
	public void set_node(Node input){
		Strbox_node = input;
	}
	public void set_parent(Node node_parent){
		node_parent.addChildNode(Strbox_node);
	}
	
}
