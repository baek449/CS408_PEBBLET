package PEBBLET.boxs;

import javax.swing.JComponent;
import javax.swing.JTextField;

import manager.Node;
import manager.NodeType;

public class StringTextField extends JComponent {
	
	JTextField textfield;
	Node node;
	
	public StringTextField() {
		textfield = new JTextField();
		node = new Node();
		node.set_node_type(NodeType.nd_raw);
	}

	public String getText() {
		// TODO Auto-generated method stub
		
		return textfield.getText();
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
}
