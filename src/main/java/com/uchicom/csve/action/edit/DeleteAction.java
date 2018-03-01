// (c) 2006 uchicom
package com.uchicom.csve.action.edit;
import java.awt.Component;
import java.awt.event.ActionEvent;

import com.uchicom.csve.util.SearchTable;
import com.uchicom.csve.util.UIAbstractAction;
import com.uchicom.csve.window.CsvTagEditorUI;

/**
 *
 * @author uchiyama
 */
public class DeleteAction extends UIAbstractAction {

    public void actionPerformed(ActionEvent actionEvent) {
		CsvTagEditorUI csvTagEditorUI = (CsvTagEditorUI) uiStore
		.getUI(CsvTagEditorUI.UI_KEY);
		Component basisComponent = csvTagEditorUI.getBasisComponent();
		SearchTable searchTable = csvTagEditorUI.getSelectedTable();
		searchTable.removeRow();

    }
}