// (c) 2006 uchicom
package com.uchicom.csve.action.help;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import com.uchicom.csve.util.UIAbstractAction;
import com.uchicom.csve.window.CsvTagEditorUI;

/**
 *
 * @author uchiyama
 */
public class VersionAction extends UIAbstractAction {


	public void actionPerformed(ActionEvent actionEvent) {
		CsvTagEditorUI csvTagEditorUI = (CsvTagEditorUI) uiStore
				.getUI(CsvTagEditorUI.UI_KEY);
		//基本設定ファイルから取得するようにする
		JOptionPane.showMessageDialog(csvTagEditorUI.getBasisComponent(),
				"CSVエディター　バージョン0");

	}
}
