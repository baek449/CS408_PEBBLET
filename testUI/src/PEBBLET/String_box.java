package PEBBLET;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class String_box extends JComponent{
	private JTextField title;
	private JLabel colon;
	private ArrayList<JTextField> values;
	private JButton add_button;
	private JButton remove_button;
	
	private JPanel String_box_pane;
	private int endof_box_pos;
	public String_box(){
		String_box_pane = new JPanel(true);
		
		endof_box_pos = 60;
		
		String_box_pane.setLayout(null);
		

		
		values = new ArrayList<JTextField>();
		
		title = new JTextField();
		title.setBounds(0,0,50,20);
		
		colon = new JLabel(":");
		colon.setBounds(55,0,5,20);
		
		String_box_pane.add(title);
		String_box_pane.add(colon);
		add_values_box();
		
		add_button = new JButton("+");
		add_button.setBounds(endof_box_pos + 5 , 0, 20,20);
		remove_button = new JButton("-");
		remove_button.setBounds(endof_box_pos + 30, 0, 20, 20);
		
		add_button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				add_values_box();
				add_button.setBounds(endof_box_pos + 5 , 0, 20,20);
				remove_button.setBounds(endof_box_pos + 30, 0, 20, 20);
				String_box_pane.setSize(endof_box_pos + 80, 20);
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
					String_box_pane.setSize(endof_box_pos + 80, 20);
				}
			}
		});
		
		String_box_pane.add(add_button);
		String_box_pane.add(remove_button);		
		
		
		
		
	}
	
	public void add_values_box(){
		JTextField new_value = new JTextField();
		
		int i = values.size();
		
		new_value.setBounds(60 + 55*i, 0, 50, 20);
//		String_box_pane.setLayout(null);
		String_box_pane.add(new_value); //add to pane
		values.add(new_value); 			//add to list
		endof_box_pos += 55;
		
	}
	public void remove_value_box(){
		
		String_box_pane.remove(values.get(values.size()-1));
		endof_box_pos -= 55;
		
		values.remove(values.size()-1);
		
	}
	
	
	public int get_endof_boxarray(){
		return endof_box_pos;
	}
	
	public void addtoPanel(JComponent comp, int x, int y){
		comp.setLayout(null);
		String_box_pane.setBounds(x,y,endof_box_pos+80,20);
		comp.add(String_box_pane);
	}
	
	public ArrayList<JTextField> get_values(){
		return values;
	}
	
	public String get_values(int i){
		
		return values.get(i).getText();
	}
	
	public void set_values(ArrayList<JTextField> _inputarray){
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
	
}
