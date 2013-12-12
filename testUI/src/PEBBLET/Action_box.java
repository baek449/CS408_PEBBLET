package PEBBLET;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;

import manager.Node;
import manager.NodeType;

public class Action_box extends JComponent {
	private JTextField Action_input;
	private Node node;
	private JButton set_button;
	
	public Action_box(){
		Action_input = new JTextField();
		set_button = new JButton("set");
		node = new Node();
		set_button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				set_Action_NodeData(Action_input.getText());
			}
		});
		node.set_node_type(NodeType.nd_action);
	}
	
	public String get_Action_input(){
		return Action_input.getText();
	}
	public void set_Action_input(String _input){
		Action_input.setText(_input);
	}
	
	public void addtoPanel(JComponent comp, int x, int y){
		comp.setLayout(null);
		Action_input.setBounds(x,y,120,20);
		set_button.setBounds(x+125, y, 30, 20);
		comp.add(Action_input);
		comp.add(set_button);
	}
	
	public void set_Action_NodeData(String _input){
		node.setData(_input);
	}
	
	public Object get_Action_NodeData(){
		return node.getData();
	}
	
	public Node get_node(){
		return node;
	}
	
	public void set_parent(Node node_parent){
		node_parent.addChildNode(node);
	}

}
