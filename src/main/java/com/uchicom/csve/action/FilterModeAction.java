// (C) 2006 uchicom
package com.uchicom.csve.action;

import com.uchicom.csve.util.SearchTable;
import com.uchicom.csve.util.UIAbstractAction;
import com.uchicom.csve.window.CsvTagEditorUI;
import java.awt.event.ActionEvent;

/**
 * フィルターモードにテーブルを変更する
 *
 * @author uchiyama
 */
public class FilterModeAction extends UIAbstractAction {

  /** */
  private static final long serialVersionUID = 1L;

  /*
   * (非 Javadoc)
   *
   * @see
   * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  public void actionPerformed(ActionEvent e) {
    CsvTagEditorUI csvTagEditorUI = (CsvTagEditorUI) uiStore.getUI(CsvTagEditorUI.UI_KEY);
    SearchTable searchTable = csvTagEditorUI.getSelectedTable();
    searchTable.addMode(SearchTable.MODE_TYPE_FILTER);
  }
}
