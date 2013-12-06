package PEBBLET;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;

import manager.Node;
import manager.NodeType;

public class Deck_box extends JComponent{
	private JTextField Deck_input;
	private Node node;
	private JButton set_button;
	
	public Deck_box(){
		Deck_input = new JTextField();
		set_button = new JButton("set");
		node = new Node();
		set_button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				set_Deck_NodeData(Deck_input.getText());
			}
		});
		node.set_node_type(NodeType.nd_deck);
	}
	
	public String get_Deck_input(){
		return Deck_input.getText();
	}
	public void set_Deck_input(String _input){
		Deck_input.setText(_input);
	}
	
	public void addtoPanel(JComponent comp, int x, int y){
		comp.setLayout(null);
		Deck_input.setBounds(x,y,120,20);
		set_button.setBounds(x+125, y, 30, 20);
		comp.add(Deck_input);
		comp.add(set_button);
	}
	
	public void set_Deck_NodeData(String _input){
		node.setData(_input);
	}
	
	public Object get_Deck_NodeData(){
		return node.getData();
	}
	
	public Node get_node(){
		return node;
	}
	
	public void set_parent(Node node_parent){
		node_parent.addChildNode(node);
	}

}
