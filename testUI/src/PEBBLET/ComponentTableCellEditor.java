package PEBBLET;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

import PEBBLET.panel.Rule_pane;
import manager.DefinitionManager;
import manager.Node;
import manager.NodeType;
import manager.RuleCase;

public class ComponentTableCellEditor implements TableCellEditor {
	
	private Node n;
	private DefinitionManager dm;
	public ComponentTableCellEditor(DefinitionManager dm_)
	{
		setDefinitionManager(dm_);
	}
	public void setDefinitionManager(DefinitionManager dm_)
	{
		dm=dm_;
	}

	@Override
	public void addCellEditorListener(CellEditorListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelCellEditing() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return n;
	}

	@Override
	public boolean isCellEditable(EventObject anEvent) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void removeCellEditorListener(CellEditorListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean shouldSelectCell(EventObject anEvent) {
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
		Object target = table.getModel().getValueAt(row, column);
		if(target!=null && target.getClass() == Node.class)
		{
			n=(Node)target;
			Object tmp = n.getData();
			if (n.get_node_type()==NodeType.nd_action && (tmp==null || tmp.getClass()==RuleCase.class))
			{
				Rule_pane rp = new Rule_pane(n,dm);
				//rp.setPreferredSize(new Dimension(200,50));
				return rp.get_scpane();
			}
		}
		
	    return null;
	}
}
