// (c) 2006 uchicom
package com.uchicom.csve.action.edit;

import java.awt.event.ActionEvent;

import com.uchicom.csve.util.SearchTable;
import com.uchicom.csve.util.UIAbstractAction;
import com.uchicom.csve.window.CsvTagEditorUI;

/**
 *
 * @author uchiyama
 */
public class DeleteAction extends UIAbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void actionPerformed(ActionEvent actionEvent) {
		CsvTagEditorUI csvTagEditorUI = (CsvTagEditorUI) uiStore.getUI(CsvTagEditorUI.UI_KEY);

		SearchTable searchTable = csvTagEditorUI.getSelectedTable();
		searchTable.removeRow();

	}
}
