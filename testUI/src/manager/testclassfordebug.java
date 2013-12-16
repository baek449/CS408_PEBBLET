package manager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import PEBBLET.ComponentTableCellRenderer;
import PEBBLET.VerticalLayout;
import PEBBLET.mainUI;
import PEBBLET.panel_inside;
import PEBBLET.tabbedpane;
import PEBBLET.panel.Rule_pane;
import PEBBLET.rule_item_panel.rule_action_panel;



//public class testclassfordebug extends JFrame{
		//just for test
	/*
	public static void main(String[] args)
	{
		//// 정의
		Node def_root = new Node(null, null);
		def_root.setData("Root");
		
		Node player_number = new Node(NodeType.nd_num,def_root);
		player_number.setData("N_player");
		Node player_number_value = new Node(null,player_number);
		player_number_value.setData(3);
		
		Node global_variables = new Node(null, def_root);
		global_variables.setData("Global");
		Node global_center=new Node(NodeType.nd_deck, global_variables);
		global_center.setData("center");
		Node global_discard=new Node(NodeType.nd_deck, global_variables);
		global_discard.setData("discard");
		
		Node player_variables = new Node(null, def_root);
		player_variables.setData("Player");
		Node player_hand=new Node(NodeType.nd_deck, player_variables);
		player_hand.setData("hand");
		
		Node card_variables = new Node(null, def_root);
		card_variables.setData("Card");
		Node card_trump = new Node(NodeType.nd_card,card_variables);
		card_trump.setData(null);
		Node card_trump_shape = new Node(NodeType.nd_str,card_trump);
		card_trump_shape.setData("shape");
		Node card_trump_shape_spade = new Node(null,card_trump_shape);
		card_trump_shape_spade.setData("spade");
		Node card_trump_shape_diamond = new Node(null,card_trump_shape);
		card_trump_shape_diamond.setData("diamond");
		Node card_trump_shape_heart = new Node(null,card_trump_shape);
		card_trump_shape_heart.setData("heart");
		Node card_trump_shape_clover = new Node(null,card_trump_shape);
		card_trump_shape_clover.setData("clover");
		Node card_trump_num=new Node(NodeType.nd_num,card_trump);
		card_trump_num.setData("num");
		
		Definition sample_def=new Definition();
		sample_def.setRoot(def_root);
		
		//// 규칙
		Node rul_root = new Node(null, null);
		rul_root.setData("Root");
		Node act_multiple = new Node(NodeType.nd_action,rul_root);
		act_multiple.setData(RuleCase.action_multiple);
		
		// 카드 불러오기
		Node act_1_load = new Node(NodeType.nd_action, act_multiple);
		act_1_load.setData(RuleCase.action_load);
		Node file_1_1 = new Node(NodeType.nd_str, act_1_load);
		file_1_1.setData(RuleCase.string_raw);
		Node file_1_1_1 = new Node(null,file_1_1);
		file_1_1_1.setData("file");
		Node deck_1_2 = new Node(NodeType.nd_deck, act_1_load);
		deck_1_2.setData("center");
		
		// 카드 섞기
		Node act_2_shuffle = new Node(NodeType.nd_action, act_multiple);
		act_2_shuffle.setData(RuleCase.action_shuffle);
		Node deck_2_1 = new Node(NodeType.nd_deck, act_2_shuffle);
		deck_2_1.setData("center");
		
		// 한 장씩 가져오기
		Node act_3_perplayer = new Node(NodeType.nd_action, act_multiple);
		act_3_perplayer.setData(RuleCase.action_act);
		// 전체 플레이어
		Node player_3_1=new Node(NodeType.nd_player, act_3_perplayer);
		player_3_1.setData(RuleCase.player_all);
		Node act_3_2=new Node(NodeType.nd_action, act_3_perplayer);
		act_3_2.setData(RuleCase.action_move);
		// top카드
		Node card_3_2_1=new Node(NodeType.nd_card, act_3_2);
		card_3_2_1.setData(RuleCase.card_top);
		// 숫자 2
		Node num_3_2_1_1=new Node(NodeType.nd_num, card_3_2_1);
		num_3_2_1_1.setData(2);
		// center 덱
		Node deck_3_2_1_2=new Node(NodeType.nd_deck, card_3_2_1);
		deck_3_2_1_2.setData("center");
		// hand 덱
		Node deck_3_2_2=new Node(NodeType.nd_deck, act_3_2);
		deck_3_2_2.setData("hand");
		
		
		Definition d = new Definition();
		d.setRoot(def_root);
		def_root.printAll();
		/*
		////////////////////// 테스트플레이 ///////////////////
		TestplayModule tpm=new TestplayModule(d);
		// 시작
		tpm.action(rul_root.getChildNode(0));
		*/
		
	/*
		try {
		DefinitionManager dm=new DefinitionManager();
		dm.setDefinition(d);
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("test.dat"));
		dm.save(out);
		
		DefinitionManager dm2=new DefinitionManager();
		ObjectInputStream in;
		in = new ObjectInputStream(new FileInputStream("test.dat"));
		dm2.load(in);
		System.out.print("Done");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("ERR");
		}
		
		// 결과 보기
		System.out.print("Done");
		
	}*/
	
//}
	
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

//package components;

/*
 * SimpleTableDemo.java requires no other files.
 */









import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class testclassfordebug extends JPanel {
   private boolean DEBUG = false;

   public testclassfordebug() {
       super(new GridLayout(1,0));

       JTable table = new JTable(new MyTableModel()) {
		    public TableCellRenderer getCellRenderer(int row, int column)
		    {
		        ComponentTableCellRenderer c= new ComponentTableCellRenderer();
		        c.setDefinitionManager(new DefinitionManager());
		        return c;
		    }
       };
       table.setPreferredScrollableViewportSize(new Dimension(500, 70));
       table.setFillsViewportHeight(true);
       table.setRowHeight(100);

       //Create the scroll pane and add the table to it.
       JScrollPane scrollPane = new JScrollPane(table);

       //Set up column sizes.
       initColumnSizes(table);

       //Fiddle with the Sport column's cell editors/renderers.
       setUpSportColumn(table, table.getColumnModel().getColumn(2));

       //Add the scroll pane to this panel.
       add(scrollPane);
       
   }

   /*
    * This method picks good column sizes.
    * If all column heads are wider than the column's cells'
    * contents, then you can just use column.sizeWidthToFit().
    */
   private void initColumnSizes(JTable table) {
       MyTableModel model = (MyTableModel)table.getModel();
       TableColumn column = null;
       java.awt.Component comp = null;
       int headerWidth = 0;
       int cellWidth = 0;
       Object[] longValues = model.longValues;
       TableCellRenderer headerRenderer =
           table.getTableHeader().getDefaultRenderer();

       for (int i = 0; i < 5; i++) {
           column = table.getColumnModel().getColumn(i);

           comp = headerRenderer.getTableCellRendererComponent(
                                null, column.getHeaderValue(),
                                false, false, 0, 0);
           headerWidth = comp.getPreferredSize().width;

           comp = table.getDefaultRenderer(model.getColumnClass(i)).
                            getTableCellRendererComponent(
                                table, longValues[i],
                                false, false, 0, i);
           cellWidth = comp.getPreferredSize().width;

           if (DEBUG) {
               System.out.println("Initializing width of column "
                                  + i + ". "
                                  + "headerWidth = " + headerWidth
                                  + "; cellWidth = " + cellWidth);
           }

           column.setPreferredWidth(Math.max(headerWidth, cellWidth));
       }
   }

   public void setUpSportColumn(JTable table,
                                TableColumn sportColumn) {
       //Set up the editor for the sport cells.
       JComboBox comboBox = new JComboBox();
       comboBox.addItem("Snowboarding");
       comboBox.addItem("Rowing");
       comboBox.addItem("Knitting");
       comboBox.addItem("Speed reading");
       comboBox.addItem("Pool");
       comboBox.addItem("None of the above");
       sportColumn.setCellEditor(new DefaultCellEditor(comboBox));

       //Set up tool tips for the sport cells.
       ComponentTableCellRenderer renderer =
               new ComponentTableCellRenderer();
       renderer.setToolTipText("Click for combo box");
       sportColumn.setCellRenderer(renderer);
   }

   class MyTableModel extends AbstractTableModel {
       private String[] columnNames = {"First Name",
                                       "Last Name",
                                       "Sport",
                                       "# of Years",
                                       "Vegetarian"};
       private Object[][] data = {
	    {"Kathy", "Smith",
	     "Snowboarding", new Integer(5), new Boolean(false)},
	    {"John", "Doe",
	     "Rowing", new Integer(3), new Boolean(true)},
	    {"Sue", null,
	     "Knitting", new Integer(2), new Boolean(false)},
	    {"Jane", "White",
	     "Speed reading", new Integer(20), new Boolean(true)},
	    {"Joe", "Brown",
	     "Pool", new Integer(10), new Boolean(false)}
       };

       public MyTableModel()
       {
    	   super();
    	   data[2][1]=new Rule().getRoot();
       }
       public final Object[] longValues = {"Jane", "Kathy",
                                           "None of the above",
                                           new Integer(20), Boolean.TRUE};

       public int getColumnCount() {
           return columnNames.length;
       }

       public int getRowCount() {
           return data.length;
       }

       public String getColumnName(int col) {
           return columnNames[col];
       }

       public Object getValueAt(int row, int col) {
           return data[row][col];
       }

       /*
        * JTable uses this method to determine the default renderer/
        * editor for each cell.  If we didn't implement this method,
        * then the last column would contain text ("true"/"false"),
        * rather than a check box.
        */
       public Class getColumnClass(int c) {
           return getValueAt(0, c).getClass();
       }

       /*
        * Don't need to implement this method unless your table's
        * editable.
        */
       public boolean isCellEditable(int row, int col) {
           //Note that the data/cell address is constant,
           //no matter where the cell appears onscreen.
           if (col < 0) {
               return false;
           } else {
               return true;
           }
       }

       /*
        * Don't need to implement this method unless your table's
        * data can change.
        */
       public void setValueAt(Object value, int row, int col) {
           if (DEBUG) {
               System.out.println("Setting value at " + row + "," + col
                                  + " to " + value
                                  + " (an instance of "
                                  + value.getClass() + ")");
           }

           data[row][col] = value;
           fireTableCellUpdated(row, col);

           if (DEBUG) {
               System.out.println("New value of data:");
               printDebugData();
           }
       }

       private void printDebugData() {
           int numRows = getRowCount();
           int numCols = getColumnCount();

           for (int i=0; i < numRows; i++) {
               System.out.print("    row " + i + ":");
               for (int j=0; j < numCols; j++) {
                   System.out.print("  " + data[i][j]);
               }
               System.out.println();
           }
           System.out.println("--------------------------");
       }
   }

   /**
    * Create the GUI and show it.  For thread safety,
    * this method should be invoked from the
    * event-dispatching thread.
    */
   private static void createAndShowGUI() {
       //Create and set up the window.
       JFrame frame = new JFrame("TableRenderDemo");
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       //Create and set up the content pane.
       testclassfordebug newContentPane = new testclassfordebug();
       newContentPane.setOpaque(true); //content panes must be opaque
       frame.setContentPane(newContentPane);

       //Display the window.
       frame.pack();
       frame.setVisible(true);
   }

   public static void main(String[] args) {
       //Schedule a job for the event-dispatching thread:
       //creating and showing this application's GUI.
       javax.swing.SwingUtilities.invokeLater(new Runnable() {
           public void run() {
               createAndShowGUI();
           }
       });
   }
}
        /*table.getColumnModel().getColumn(0).setMaxWidth(100);
        table.getColumnModel().getColumn(1).setMaxWidth(100);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);*/
