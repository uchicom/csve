// (c) 2006 uchicom
package com.uchicom.csve.action.file;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Properties;

import javax.swing.JFileChooser;

import com.uchicom.csve.util.UIAbstractAction;
import com.uchicom.csve.window.CsvTagEditorUI;

/**
 *
 * @author uchiyama
 */
public class OpenAction extends UIAbstractAction {

	public void actionPerformed(ActionEvent actionEvent) {
		try {
			CsvTagEditorUI csvTagEditorUI = (CsvTagEditorUI) uiStore.getUI(CsvTagEditorUI.UI_KEY);
			Component basisComponent = csvTagEditorUI.getBasisComponent();
			//開くファイルを指定する画面を開く
			JFileChooser chooser = new JFileChooser();
			Properties properties = csvTagEditorUI.getProperties();
			String path = properties.getProperty("path");
			if (path != null && !"".equals(path)) {
				chooser.setSelectedFile(new File(path));
			}
			int result = chooser.showOpenDialog(basisComponent);
			//開いたファイルをオブジェクトで取得する
			if (result == JFileChooser.APPROVE_OPTION) {
				File selectFile = chooser.getSelectedFile();

				if (selectFile != null) {
					csvTagEditorUI.open(selectFile);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
