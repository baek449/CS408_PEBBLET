package PEBBLET;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class custominputbox extends JComponent{	
	
	private JComboBox<String> name = new JComboBox<String>();
	private JTextField detail = new JTextField();
	
	public custominputbox(int x, int y){
		JPanel inputbox = new JPanel();
		inputbox.setLayout(null);
		inputbox.setSize(80,30);
		Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        Border loweredbevel = BorderFactory.createLoweredBevelBorder();
        inputbox.setBorder(BorderFactory.createCompoundBorder(raisedetched, loweredbevel));
//		JComboBox name = new JComboBox();
		name.setBounds(5,5,30,20);
        name.setBorder(BorderFactory.createCompoundBorder(raisedetched, loweredbevel));
//		JTextField detail = new JTextField();
		detail.setBounds(40, 5, 35, 20);
        detail.setBorder(BorderFactory.createCompoundBorder(raisedetched, loweredbevel));
		
		inputbox.add(name);
		inputbox.add(detail);
		
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
	
}
