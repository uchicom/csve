// (c) 2006 uchicom
package com.uchicom.csve.action;
import java.awt.Component;
import java.awt.event.ActionEvent;

import com.uchicom.csve.CsvTagEditorUI;
import com.uchicom.csve.util.SearchTable;
import com.uchicom.csve.util.UIAbstractAction;

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
