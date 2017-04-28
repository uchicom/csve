// (c) 2006 uchicom
package com.uchicom.csve.util;

import java.util.List;

import javax.swing.table.DefaultTableModel;
/**
 *
 * @author UCHIYAMA
 */
public class ListTableModel extends DefaultTableModel {

    /** Creates a new instance of ListTableModel */
    public ListTableModel(List rowList, int columnCount) {
        this.rowList = rowList;
        this.columnCount = columnCount;
    }
    public Object getValueAt(int row, int col) {
        String[] cells = (String[])rowList.get(row);
        if (cells.length > col) {
            return cells[col];
        } else {
            return null;

        }
    }
    public void setValueAt(Object value, int row, int col) {
        String[] cells = (String[])rowList.get(row);
        if (cells.length > col) {
            cells[col] = (String)value;
        }
    }
    public Class getColumnClass(int columnIndex) {
        return String.class;
    }
    public int  getColumnCount() {
        return columnCount;
    }
    public int getRowCount() {
        if (rowList != null) {
            return rowList.size();
        } else {
            return 0;
        }
    }

    /**
     * 文字列を検索して位置を返す
     * @param target
     * @param startRow
     * @param startCol
     * @return
     */
    public Point searchAndSelect(String target, int startRow, int startCol, boolean contain) {

    	//検索値からテーブルの中を検索する
    	boolean result = false;
    	for (int i = startRow; i < rowList.size(); i++) {
    		String[] cells = rowList.get(i);
    		for (int j = 0; j < cells.length; j++) {
    			if (!contain && i == startRow && j == 0) {
    				j = startCol + 1;
    			}
    			if (target.equals(cells[j])) {
        			return new Point(i,j);
    			}
    		}
    	}

    	return new Point();
    }

    public void removeRows(int[] aRow) {
    	for (int i = aRow.length - 1; i >= 0; i--) {
	    	rowList.remove(aRow[i]);
	    	fireTableRowsDeleted(aRow[i], aRow[i]);
    	}
    }


    public void addRows(int[] aRow) {
    	for (int i = aRow.length - 1; i >= 0; i--) {
	    	rowList.add(aRow[i], new String[columnCount]);
	    	fireTableRowsInserted(aRow[i], aRow[i]);
    	}
    }


    /** データ格納リスト */
    private List<String[]> rowList;

    /** 列最大数 */
    private int columnCount = 0;
}
