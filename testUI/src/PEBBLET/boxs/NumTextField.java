package PEBBLET.boxs;

import javax.swing.JComponent;
import javax.swing.JTextField;

import manager.Node;
import manager.NodeType;

public class NumTextField extends JComponent{
	
	JTextField textfield;
	Node node;
	
	public NumTextField(){
		textfield = new JTextField();
		node = new Node();
		node.set_node_type(NodeType.nd_raw);
	}
	public NumTextField(String input, Node inputNode){
		textfield = new JTextField(input);
		node = inputNode;
		node.set_node_type(NodeType.nd_raw);
	}

	public int getText() {
		// TODO Auto-generated method stub
		
		return Integer.parseInt(textfield.getText());
	}

	public void setText(String _input) {
		// TODO Auto-generated method stub
		textfield.setText(_input);
		
	}
	
	public void addtoPanel(JComponent comp, int x, int y){
		comp.setLayout(null);
		textfield.setBounds(x,y,50,20);
		comp.add(textfield);
	}
	
	public void set_parent(Node node_parent){
		node_parent.addChildNode(node);
	}

	public Node get_node() {
		// TODO Auto-generated method stub
		return node;
	}
	public void set_node(Node input){
		node = input;
	}
}
