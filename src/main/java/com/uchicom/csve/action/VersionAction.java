// (c) 2006 uchicom
package com.uchicom.csve.action;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import com.uchicom.csve.CsvTagEditorUI;
import com.uchicom.csve.util.UIAbstractAction;

/**
 *
 * @author uchiyama
 */
public class VersionAction extends UIAbstractAction {


	public void actionPerformed(ActionEvent actionEvent) {
		CsvTagEditorUI csvTagEditorUI = (CsvTagEditorUI) uiStore
				.getUI(CsvTagEditorUI.UI_KEY);
		Component basisComponent = csvTagEditorUI.getBasisComponent();
		//基本設定ファイルから取得するようにする
		JOptionPane.showMessageDialog(csvTagEditorUI.getBasisComponent(),
				"CSVエディター　バージョン0");

	}
}
