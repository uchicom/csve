// (c) 2006 uchicom
package com.uchicom.csve.action;

import java.awt.Component;
import java.awt.event.ActionEvent;

import com.uchicom.csve.util.SearchTable;
import com.uchicom.csve.util.UIAbstractAction;
import com.uchicom.csve.window.CsvTagEditorUI;

/**
 * @author uchiyama
 *
 */
public class AddRowAction extends UIAbstractAction {

	/* (非 Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO 自動生成されたメソッド・スタブ

		CsvTagEditorUI csvTagEditorUI = (CsvTagEditorUI) uiStore
		.getUI(CsvTagEditorUI.UI_KEY);
		Component basisComponent = csvTagEditorUI.getBasisComponent();
		SearchTable searchTable = csvTagEditorUI.getSelectedTable();
		searchTable.addRow();
	}

}
