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
    public ListTableModel(List<CellInfo[]> rowList, int columnCount) {
        this.rowList = rowList;
        this.columnCount = columnCount;
    }
    public Object getValueAt(int row, int col) {
    	CellInfo[] cells = rowList.get(row);
        if (cells.length > col) {
            return cells[col].toString();
        } else {
            return null;

        }
    }
    public void setValueAt(Object value, int row, int col) {
    	CellInfo[] cells = rowList.get(row);
        if (cells.length > col) {
            cells[col].setValue(value);
        }
    }
    @Override
    public Class<CellInfo> getColumnClass(int columnIndex) {
        return CellInfo.class;
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
    	for (int i = startRow; i < rowList.size(); i++) {
    		CellInfo[] cells = rowList.get(i);
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
    		CellInfo[] cells = new CellInfo[columnCount];
    		for (int j = 0; j < cells.length; j++) {
    			cells[j] = new StringCellInfo("");
    		}
	    	rowList.add(aRow[i], cells);
	    	fireTableRowsInserted(aRow[i], aRow[i]);
    	}
    }


    /** データ格納リスト */
    private List<CellInfo[]> rowList;

    /** 列最大数 */
    private int columnCount = 0;
}
