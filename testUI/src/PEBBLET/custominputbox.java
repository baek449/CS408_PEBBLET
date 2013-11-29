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
	
	public custominputbox(JComponent comp, int x, int y){
		JPanel inputbox = new JPanel();
		inputbox.setLayout(null);
		inputbox.setBounds(x, y, 200, 30);
		Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        Border loweredbevel = BorderFactory.createLoweredBevelBorder();
        inputbox.setBorder(BorderFactory.createCompoundBorder(raisedetched, loweredbevel));
//		JComboBox name = new JComboBox();
		name.setBounds(5,5,100,20);
        name.setBorder(BorderFactory.createCompoundBorder(raisedetched, loweredbevel));
//		JTextField detail = new JTextField();
		detail.setBounds(110, 5, 80, 20);
        detail.setBorder(BorderFactory.createCompoundBorder(raisedetched, loweredbevel));
		
		inputbox.add(name);
		inputbox.add(detail);
		
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
	
}
