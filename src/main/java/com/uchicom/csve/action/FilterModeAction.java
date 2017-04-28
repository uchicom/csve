// (c) 2006 uchicom
package com.uchicom.csve.action;

import java.awt.Component;
import java.awt.event.ActionEvent;

import com.uchicom.csve.CsvTagEditorUI;
import com.uchicom.csve.util.SearchTable;
import com.uchicom.csve.util.UIAbstractAction;

/**
 * フィルターモードにテーブルを変更する
 * @author uchiyama
 *
 */
public class FilterModeAction extends UIAbstractAction {

	/* (非 Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		CsvTagEditorUI csvTagEditorUI = (CsvTagEditorUI) uiStore
		.getUI(CsvTagEditorUI.UI_KEY);
		Component basisComponent = csvTagEditorUI.getBasisComponent();
		SearchTable searchTable = csvTagEditorUI.getSelectedTable();
		searchTable.addMode(SearchTable.MODE_TYPE_FILTER);
	}

}
