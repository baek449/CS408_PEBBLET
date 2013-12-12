package PEBBLET.boxs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;

import manager.Node;
import manager.NodeType;

public class Player_box extends JComponent {
	private JTextField Player_input;
	private Node node;
	private JButton set_button;
	
	public Player_box(){
		Player_input = new JTextField();
		node = new Node();
		set_button = new JButton("set");
		
		set_button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				set_Player_NodeData(Player_input.getText());
			}
		});
		node.set_node_type(NodeType.nd_player);
	}
	
	public String get_Player_input(){
		return Player_input.getText();
	}
	
	public void set_Player_input(String _input){
		Player_input.setText(_input);
	}
	
	public void addtoPanel(JComponent comp, int x, int y){
		comp.setLayout(null);
		Player_input.setBounds(x,y,120,20);
		set_button.setBounds(x+125, y, 30, 20);
		comp.add(Player_input);
		comp.add(set_button);

	}
	
	public void set_Player_NodeData(String _input){
		node.setData(_input);
	}
	
	public Object get_Player_NodeData(){
		return node.getData();
	}
	
	public Node get_node(){
		return node;
	}
	
	public void set_parent(Node node_parent){
		node_parent.addChildNode(node);
	}
}
