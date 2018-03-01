// (c) 2006 uchicom
package com.uchicom.csve.action.edit;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JOptionPane;

import com.uchicom.csve.util.SearchTable;
import com.uchicom.csve.util.UIAbstractAction;
import com.uchicom.csve.window.CsvTagEditorUI;

/**
 *
 * @author uchiyama
 */
public class ReplaceAction extends UIAbstractAction {


    public void actionPerformed(ActionEvent actionEvent) {
		CsvTagEditorUI csvTagEditorUI = (CsvTagEditorUI) uiStore
		.getUI(CsvTagEditorUI.UI_KEY);
		Component basisComponent = csvTagEditorUI.getBasisComponent();
		//基本設定ファイルから取得するようにする
		String target = JOptionPane.showInputDialog(basisComponent, "検索対象文字列を入力してください。", "検索", JOptionPane.INFORMATION_MESSAGE);

		//基本設定ファイルから取得するようにする
		String replace = JOptionPane.showInputDialog(basisComponent, "置換文字列を入力してください。", "検索", JOptionPane.INFORMATION_MESSAGE);
		if (target != null) {
			System.out.println("target=" + target);
			//OK:検索処理
			List<SearchTable> tableList =  csvTagEditorUI.getTableList();
			for (SearchTable table :  tableList) {
				//table
				System.out.println("target");
				if (table.searchAndReplace(target, replace)) {
					JOptionPane.showMessageDialog(basisComponent, "[" + table.getName() + "]シートに検索対象文字列（" + target + "）があります。");
				}
				//JTableを拡張した検索できるテーブルオブジェクトを作成する
			}

		} else {
			//キャンセル:画面を閉じて、元の画面に戻る
		}

    }
}