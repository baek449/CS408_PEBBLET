package manager;

import java.util.EventObject;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;

import PEBBLET.panel.Rule_pane;

public class ComponentTablePanel extends JPanel {
	private Node c1;
	private JTable jt;
	public ComponentTablePanel(DefinitionManager dm) {

		/*
		c1=new Node(NodeType.nd_card,null);
		c1.setData("Test Card");
		Node c11=new Node(NodeType.nd_str,c1);
		c11.setData("_type");
		Node c111=new Node(null,c11);
		c111.setData("Trump");
		Node c12=new Node(NodeType.nd_str,c1);
		c12.setData("string_test");
		Node c121=new Node(null,c12);
		c121.setData("testtest");
		Node c13=new Node(NodeType.nd_num,c1);
		c13.setData("number_test");
		Node c131=new Node(null,c13);
		c131.setData(60);
		Node c14=new Node(NodeType.nd_action,c1);
		c14.setData("action_test");
		Node c141=new Node(NodeType.nd_action,c14);
		c141.setData(RuleCase.action_multiple);
		*/
		final ComponentTableModel test = new ComponentTableModel(null);
		jt = new JTable(test)
        {
            //  Determine editor to be used by row
            public TableCellEditor getCellEditor(int row, int column)
            {
            	int row_=convertRowIndexToModel(row);
            	int column_=convertColumnIndexToModel(column);
    			if(column_==2 && "Action".equals(data[row][0]))
    				return new ComponentTableCellEditor();
                else
                    return new DefaultCellEditor(new JTextField());
            }
        };
		jt.getColumnModel().getColumn(0).setMaxWidth(100);
		jt.getColumnModel().getColumn(1).setMaxWidth(100);
		jt.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		
		jt.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		ComponentTableCellRenderer ctcr = new ComponentTableCellRenderer();
		ctcr.setDefinitionManager(dm);
		jt.setDefaultRenderer(Node.class, ctcr);
		jt.setDefaultEditor(Node.class, new ComponentTableCellEditor());
		
		// Row dealing
		for(int loop=0;loop<data.length;loop++)
		{
			if("Action".equals(data[loop][0]))
			{
				jt.setRowHeight(loop, 200);
			}
			else
			{
				jt.setRowHeight(loop,25);
			}
		}
				
		setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
		JScrollPane jsp = new JScrollPane(jt);
		add(jsp);
	}
	
	public void reset(Node cardnode)
	{
		ComponentTableModel test=new ComponentTableModel(cardnode);
		jt.setModel(test);
		jt.getColumnModel().getColumn(0).setMaxWidth(100);
		jt.getColumnModel().getColumn(1).setMaxWidth(100);
		jt.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		// Row dealing
		for(int loop=0;loop<data.length;loop++)
		{
			if("Action".equals(data[loop][0]))
			{
				jt.setRowHeight(loop, 200);
			}
			else
			{
				jt.setRowHeight(loop,25);
			}
		}
		panes=new java.awt.Component[data.length];
	}
  
  
	class ComponentTableCellEditor extends Rule_pane implements TableCellEditor {

		protected transient Vector listeners;
		
		public ComponentTableCellEditor() {
			super((new Rule()).getRoot(), new DefinitionManager());
			listeners = new Vector();
		}

		public java.awt.Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			return panes[row];
		}

		public void cancelCellEditing() {
			fireEditingCanceled();
		}

		public Object getCellEditorValue() {
			Node n=this.rm.getRule().getRoot();
			return n;
		}

		public boolean isCellEditable(EventObject eo) {
			return true;
		}

		public boolean shouldSelectCell(EventObject eo) {
			return true;
		}

		public boolean stopCellEditing() {
			fireEditingStopped();
			return true;
		}

		public void addCellEditorListener(CellEditorListener cel) {
			listeners.addElement(cel);
		}

		public void removeCellEditorListener(CellEditorListener cel) {
			listeners.removeElement(cel);
		}

		protected void fireEditingCanceled() {
			ChangeEvent ce = new ChangeEvent(this);
			for (int i = listeners.size() - 1; i >= 0; i--) {
				((CellEditorListener) listeners.elementAt(i)).editingCanceled(ce);
			}
		}

		protected void fireEditingStopped() {
			ChangeEvent ce = new ChangeEvent(this);
			for (int i = listeners.size() - 1; i >= 0; i--) {
				((CellEditorListener) listeners.elementAt(i)).editingStopped(ce);
			}
		}
	}
  
  
	java.awt.Component[] panes;
  
	public class ComponentTableCellRenderer extends DefaultTableCellRenderer{
		
		private DefinitionManager dm;
		public void setDefinitionManager(DefinitionManager dm_)
		{
			dm=dm_;
		}

		public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,int row,int col) {
		
			
			Object target = table.getModel().getValueAt(row, col);
			if(target!=null && target.getClass() == Node.class)
			{
				Node n=(Node)target;
				Object tmp = n.getData();
				if (n.get_node_type()==NodeType.nd_action && (tmp==null || tmp.getClass()==RuleCase.class))
				{
					if(panes[row]!=null) return panes[row];
					Rule_pane rp = new Rule_pane(n,dm);
					panes[row]=rp.get_scpane();
					return panes[row];
				}
			}
			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
		}
	}


	Object data[][];
	class ComponentTableModel extends AbstractTableModel {
	
		String headers[] = { "Type", "Name", "Value"};
	
		Class columnClasses[] = { String.class, String.class, Node.class};
	

		
		public ComponentTableModel(Node card_node)
		{
			if(card_node==null)
			{
				data=new Object[0][];
				return;
			}

			data=new Object[card_node.numChildren()][];
			int loop;
			Node c;
			for(loop=0;loop<card_node.numChildren();loop++)
			{
				c=card_node.getChildNode(loop);
				data[loop]=new Object[3];
				switch(c.get_node_type())
				{
				case nd_str:
					data[loop][0]="String";
					break;
				case nd_num:
					data[loop][0]="Number";
					break;
				case nd_action:
					data[loop][0]="Action";
					break;
				default:
					data[loop][0]="";
					break;
				}
				data[loop][1]=c.getData();
				data[loop][2]=c.getChildNode(0);
			}
			data[0][0]="";
			data[0][1]="Card Name";
			data[0][2]=card_node;
		}
		public int getRowCount() {
			return data.length;
		}
	
		public int getColumnCount() {
			return headers.length;
		}
	
		public Class getColumnClass(int c) {
			return columnClasses[c];
		}
	
		public String getColumnName(int c) {
			return headers[c];
		}
	
		public boolean isCellEditable(int r, int c) {
			return (c>=2);
		}
	
		public Object getValueAt(int r, int c) {
			return data[r][c];
		}
	
		public void setValueAt(Object value, int r, int c) {
			if (c==2 && value.getClass()==String.class ) {
				Object value2=value;
				if("Number".equals(data[r][0]))
				{
					try {
						value2=Integer.parseInt((String)value);
					} catch (NumberFormatException e) {
						return;
					}
				}
				((Node)data[r][2]).setData(value2);
			}
		}
	}
}



