// (c) 2006 uchicom
package com.uchicom.csve.action;

import java.awt.event.ActionEvent;

import com.uchicom.csve.util.SearchTable;
import com.uchicom.csve.util.UIAbstractAction;
import com.uchicom.csve.window.CsvTagEditorUI;

/**
 * @author uchiyama
 *
 */
public class InitModeAction extends UIAbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * (非 Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO 自動生成されたメソッド・スタブ

		CsvTagEditorUI csvTagEditorUI = (CsvTagEditorUI) uiStore.getUI(CsvTagEditorUI.UI_KEY);
		SearchTable searchTable = csvTagEditorUI.getSelectedTable();
		searchTable.setMode(1);

		// initmodeじゃなくて、changeModeとかにしてるほうがいいのかな？？
		// まあ微妙だな。。
	}

}
