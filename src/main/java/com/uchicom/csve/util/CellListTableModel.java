// (c) 2006 uchicom
package com.uchicom.csve.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.table.DefaultTableModel;

/**
 * CellInfoのテーブルモデル
 * 
 * @author UCHIYAMA
 */
public class CellListTableModel extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Creates a new instance of ListTableModel */
	public CellListTableModel(List<CellInfo[]> rowList, int columnCount) {

		this.rowList = rowList;
		this.columnCount = columnCount;
	}

	public Object getValueAt(int row, int col) {
		CellInfo[] cells = (CellInfo[]) rowList.get(row);
//        System.out.println("getValue(" + row + "," + col + ")");
		if (cells.length > col) {
			if (cells[col] == null) {
				return null;
			} else {
				return cells[col];
			}
		} else {
			return null;

		}
	}

	public void setValueAt(Object value, int row, int col) {

		CellInfo[] cells = (CellInfo[]) rowList.get(row);

		if (cells.length > col) {
			cells[col].setValue(value);
			if (!cells[col].isUpdated()) {
				cells[col].addStatus(CellInfo.UPDATED);
			}
		}

	}

	public Class<CellInfo> getColumnClass(int columnIndex) {
		return CellInfo.class;
	}

	public int getColumnCount() {
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
	 * 
	 * @param target
	 * @param startRow
	 * @param startCol
	 * @return
	 */
	public Point searchAndSelect(String target, int startRow, int startCol, boolean contain) {

		// 検索値からテーブルの中を検索する
		for (int i = startRow; i < rowList.size(); i++) {
			CellInfo[] cells = rowList.get(i);
			for (int j = 0; j < cells.length; j++) {
				if (!contain && i == startRow && j == 0) {
					j = startCol + 1;
					j = j / cells.length;
				}
				if (target.equals(cells[j].toString())) {
					return new Point(i, j);
				}
			}
		}

		return new Point();
	}

	/**
	 * 削除する場合は下から追加
	 * 
	 * @param aRow
	 */
	public void removeRows(int[] aRow) {
		for (int i = aRow.length - 1; i >= 0; i--) {
			rowList.remove(aRow[i]);
			fireTableRowsDeleted(aRow[i], aRow[i]);
		}
	}

	/**
	 * 追加する場合は上から追加
	 * 
	 * @param aRow
	 */
	public void addRows(int[] aRow) {
		for (int i = 0; i < aRow.length; i++) {
			CellInfo[] cells = new CellInfo[columnCount];
			rowList.add(aRow[i], cells);
			for (int j = 0; j < columnCount; j++) {
				cells[j] = new StringCellInfo("");
				cells[j].addStatus(CellInfo.INSERTED);
			}

			fireTableRowsInserted(aRow[i], aRow[i]);
		}
	}

	public void addList(int col, List<CellInfo[]> cellList) {
		rowList.addAll(col, cellList);
	}

	/** データ格納リスト */
	private List<CellInfo[]> rowList;

	/** 列最大数 */
	private int columnCount = 0;

	/**
	 * 並び替え1
	 * 
	 * @param comparator
	 */
	public void sort(Comparator<CellInfo[]> comparator) {
		Collections.sort(rowList, comparator);
	}

	List<CellInfo[]> filterList = new ArrayList<CellInfo[]>();
}
