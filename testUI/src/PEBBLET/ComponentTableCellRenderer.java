package PEBBLET;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.EventObject;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;

import PEBBLET.panel.Rule_pane;
import manager.DefinitionManager;
import manager.Node;
import manager.NodeType;
import manager.RuleCase;

public class ComponentTableCellRenderer extends DefaultTableCellRenderer implements TableCellEditor{
	
	private DefinitionManager dm;
	public void setDefinitionManager(DefinitionManager dm_)
	{
		dm=dm_;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,int row,int col) {
	
		Object target = table.getModel().getValueAt(row, col);
		if(target!=null && target.getClass() == Node.class)
		{
			Node n=(Node)target;
			Object tmp = n.getData();
			if (n.get_node_type()==NodeType.nd_action && (tmp==null || tmp.getClass()==RuleCase.class))
			{
				Rule_pane rp = new Rule_pane(n,dm);
				//rp.setPreferredSize(new Dimension(200,50));
				return rp.get_scpane();
			}
		}
	    return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
	}

	@Override
	public void addCellEditorListener(CellEditorListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelCellEditing() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCellEditable(EventObject arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeCellEditorListener(CellEditorListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean shouldSelectCell(EventObject arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean stopCellEditing() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
		return super.getta
	}
}