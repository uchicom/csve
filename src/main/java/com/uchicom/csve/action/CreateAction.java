/*
 * CreateAction.java
 *
 * Created on 2006/09/05, 21:08
 *
 */

package com.uchicom.csve.action;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.uchicom.csve.CsvTagEditor;
import com.uchicom.csve.CsvTagEditorUI;
import com.uchicom.csve.util.CellInfo;
import com.uchicom.csve.util.CellListTableModel;
import com.uchicom.csve.util.CellRenderer;
import com.uchicom.csve.util.SearchTable;
import com.uchicom.csve.util.SortTableColumn;
import com.uchicom.csve.util.SortTableColumnModel;
import com.uchicom.csve.util.StringCellInfo;
import com.uchicom.csve.util.UIAbstractAction;

/**
 *
 * @author uchiyama
 */
public class CreateAction extends UIAbstractAction {



    public void actionPerformed(ActionEvent actionEvent) {
        CsvTagEditorUI csvTagEditorUI = (CsvTagEditorUI)uiStore.getUI(CsvTagEditorUI.UI_KEY);
        Component basisComponent = csvTagEditorUI.getBasisComponent();
        String inputValue = JOptionPane.showInputDialog(basisComponent, "row,column format (ex:3,3) point", "Create(point size)", JOptionPane.QUESTION_MESSAGE);
        JTabbedPane tabPane = csvTagEditorUI.getTabb();
        if (inputValue != null) {
	        StringTokenizer tokenizer = new StringTokenizer(inputValue, ",");
	        int row = 0;
	        int col = 0;
	        if (tokenizer.hasMoreTokens()) {
	            row = Integer.parseInt(tokenizer.nextToken());
	        }
	        if (tokenizer.hasMoreTokens()) {
	            col = Integer.parseInt(tokenizer.nextToken());
	        }
	        try {
	            List<CellInfo[]> cvsList = new ArrayList<CellInfo[]>(row);
	            String[]rowHeads = new String[row];
	            for (int i = 0; i < row; i++) {
	                CellInfo[] strings = new CellInfo[col];
	                for (int j = 0; j < col; j++) {
	                    strings[j] = new StringCellInfo("");
	                }
	                cvsList.add(strings);
	                rowHeads[i] = String.valueOf(i);
	            }

	            //テーブルの列モデルを作成する
	            SortTableColumnModel columnModel = new SortTableColumnModel();

	        	 char[] ind = new char[3];
	            for (int i = 0; i < col; i++) {
	            	SortTableColumn column = new SortTableColumn();

		        	if ( i < 26) {
		        		column.setHeaderValue((char)('A' + (i % 26)));
		        	} else if (i < 26 * 26) {
		        		ind[0] = (char)('A' + (i / 26));
		        		ind[1] = (char)('A' + ((i - 1) % 26));
		        		column.setHeaderValue(String.valueOf(ind));
		        	} else if (i < 26*26 * 26) {
		        		ind[0] = (char)('A' + (i / (26 * 26)));
		        		ind[1] = (char)('A' + (i / 26));
		        		ind[2] = (char)('A' + ((i - 1) % 26));
		        		column.setHeaderValue(String.valueOf(ind));
		        	}
	            	column.setModelIndex(i);
	            	column.setIdentifier(String.valueOf(i));
	            	column.setCellRenderer(new CellRenderer());
	            	//tableColumnを使う場合はcellEditorを設定してあげないとちゃんと動かない
	                JTextField editableTextField = new JTextField();
	                editableTextField.setEditable(true);
	                column.setCellEditor(new DefaultCellEditor(editableTextField));
	            	columnModel.addColumn(column);


	            }
	            CellListTableModel tableModel = new CellListTableModel(cvsList,  col);
	            SearchTable table = new SearchTable(tableModel, columnModel);
	            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	            table.setColumnSelectionAllowed(true);
	            table.setRowSelectionAllowed(true);

	            csvTagEditorUI.getTableList().add(table);
	            csvTagEditorUI.getTableMap().put(new Integer(tabPane.getTabCount()), cvsList);
	            JScrollPane scrollPane = new JScrollPane(table);
	            //行ヘッダはまだ敷居が高いな
	            //scrollPane.setRowHeaderView(new JList(rowHeads));
	            String tabName = "* New(" + (tabPane.getTabCount() + 1) + ")";
	            tabPane.add(tabName, scrollPane);
	            tabPane.setSelectedIndex(tabPane.indexOfTab(tabName));
	            ((CsvTagEditor)basisComponent).pack();
	            } catch (Error error) {
	                error.printStackTrace();
	            }
        }
    }

}
