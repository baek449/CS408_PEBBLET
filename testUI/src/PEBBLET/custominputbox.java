package PEBBLET;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class custominputbox extends JComponent{	
	
	private JComboBox<String> name = new JComboBox<String>();
	private JTextField detail = new JTextField();
	private JButton addbutton = new JButton("+");
	private custominputbox lastchild = null;
	private custominputbox parent = null;
	private int level = 0;
	private JPanel inputbox = new JPanel();
	private ArrayList<Integer> index = new ArrayList<Integer>();
	private Integer child_index_no = 0;
	
	public custominputbox(final JComponent comp){
		inputbox.setLayout(null);
		
//		JComboBox name = new JComboBox();
		name.setBounds(5,5,100,20);
//        name.setBorder(BorderFactory.createCompoundBorder(raisedetched, loweredbevel));
//		JTextField detail = new JTextField();
		detail.setBounds(110, 5, 80, 20);
//        detail.setBorder(BorderFactory.createCompoundBorder(raisedetched, loweredbevel));
		addbutton.setBounds(195, 5, 20, 20);
		
		inputbox.add(name);
		inputbox.add(detail);
		inputbox.add(addbutton);
		
		final custominputbox me = this;
		
		addbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				me.child_index_no++;
				mainUI.addsubbox(comp, me);
				mainUI.update_panel(comp);
			}
		});
		
		
//		comp.setLayout(null);
//		comp.add(inputbox);
		
	}
	
	public void addtoPanel(JComponent comp, int x, int y){
		inputbox.setBounds(x, y, 230, 30);
		Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        Border loweredbevel = BorderFactory.createLoweredBevelBorder();
        inputbox.setBorder(BorderFactory.createCompoundBorder(raisedetched, loweredbevel));
		comp.setLayout(null);
		comp.add(inputbox);
	}
	
	public void additem(custominputbox inputbox, String item_name){
		JComboBox<String> name = inputbox.getname();
		name.addItem(item_name);
	}
	
	public JComboBox<String> getname(){
		return name;
	}
	
	public String getdetail(){
		return detail.getText();
	}
	
	public void setlastchild(custominputbox child){
		lastchild = child;
	}
	public custominputbox getlastchild(){
		return lastchild;
	}
	public void increase_level(){
		level++;
	}
	public void decrease_level(){
		if(level ==0){
			level = 0;
		}
		else{
			level--;
		}
	}
	public int get_level(){
		return level;
	}
	
	public void set_parent(custominputbox _parent){
		parent = _parent;
	}
	public custominputbox get_parent(){
		return parent;
	}
	

	public ArrayList<Integer> get_index(){
		return index;
	}
	
	public void set_index(ArrayList<Integer> _index){
		index = _index;
	}

	//	public void set_index(int x){
//		index = x;
//	}
//	public void increase_index(){
//		index++;
//	}
//	public void decrease_index(){
//		if(index ==0){
//			index = 0;
//		}
//		else{
//			index--;
//		}
//	}
	public void set_level(int _level){
		level = _level;
	}
	
	public Integer get_child_index_no(){
		return child_index_no;
	}
	
}
