// (c) 2006 uchicom
package com.uchicom.csve.action;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.uchicom.csve.util.CellInfo;
import com.uchicom.csve.util.SearchTable;
import com.uchicom.csve.util.StringCellInfo;
import com.uchicom.csve.util.UIAbstractAction;
import com.uchicom.csve.window.CsvTagEditorUI;

/**
 * @author uchiyama
 *
 */
public class CreatePatternAction extends UIAbstractAction {

	/* (非 Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		CsvTagEditorUI csvTagEditorUI = (CsvTagEditorUI) uiStore
		.getUI(CsvTagEditorUI.UI_KEY);
		Component basisComponent = csvTagEditorUI.getBasisComponent();
		SearchTable searchTable = csvTagEditorUI.getSelectedTable();
		//選択している行を解析してパターンを作成する
		int selectedRow = searchTable.getSelectedRow();
		int columnCount = searchTable.getColumnCount();
		list = new ArrayList<CellInfo[]>();

		if (selectedRow >= 0) {
			getNext(0, columnCount - 1, new CellInfo[columnCount], searchTable, selectedRow);
			searchTable.insertRow(selectedRow + 1, list);
			searchTable.repaint();
		} else {
			JOptionPane.showMessageDialog(basisComponent, "パターンを選択してください");
		}
	}


	List<CellInfo[]> list = null;

	/**
	 * 再帰呼び出しでパターンを作成する
	 * @param col
	 * @param lastCount
	 * @param cells
	 * @param table
	 * @param selectedRow
	 */
	private void getNext(int col, int lastCount, CellInfo[] cells, SearchTable table, int selectedRow) {
		if (lastCount < 0) return;
		System.out.println(col+":"+lastCount);
		CellInfo cell = (CellInfo)table.getValueAt(selectedRow, col);
		String[] pats = cell.toString().split("/");
		for (int i = 0; i < pats.length; i++) {
			if (lastCount == 0) {
				//最後のセルならパターンを設定して行追加
				CellInfo[] copyCells = copyCellInfo(cells);
				copyCells[col] = new StringCellInfo(pats[i]);
				copyCells[col].addStatus(CellInfo.INSERTED);
				list.add(copyCells);
			} else {
				//最後のセルじゃなければ再帰呼び出し
				cells[col] = new StringCellInfo(pats[i]);
				getNext(col+1, lastCount-1, cells, table, selectedRow);
			}
		}
	}

	/**
	 * セルのコピーを作成する
	 * @param cells
	 * @return
	 */
	private CellInfo[] copyCellInfo(CellInfo[] cells) {
		CellInfo[] copyCells = new CellInfo[cells.length];
		for (int i = 0; i < cells.length; i++) {
			if (cells[i] == null) {
				copyCells[i] = new StringCellInfo("");
			} else {
				copyCells[i] = new StringCellInfo(cells[i].toString());
			}
			copyCells[i].addStatus(CellInfo.INSERTED);
		}
		return copyCells;
	}

}
