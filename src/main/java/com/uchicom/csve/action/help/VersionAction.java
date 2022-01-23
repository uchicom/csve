// (C) 2006 uchicom
package com.uchicom.csve.action.help;

import com.uchicom.csve.util.UIAbstractAction;
import com.uchicom.csve.window.CsvTagEditorUI;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/** @author uchiyama */
public class VersionAction extends UIAbstractAction {

  /** */
  private static final long serialVersionUID = 1L;

  public void actionPerformed(ActionEvent actionEvent) {
    CsvTagEditorUI csvTagEditorUI = (CsvTagEditorUI) uiStore.getUI(CsvTagEditorUI.UI_KEY);
    // 基本設定ファイルから取得するようにする
    JOptionPane.showMessageDialog(csvTagEditorUI.getBasisComponent(), "CSVエディター　バージョン0");
  }
}
