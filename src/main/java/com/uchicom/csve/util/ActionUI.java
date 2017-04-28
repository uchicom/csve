// (c) 2006 uchicom
package com.uchicom.csve.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.Action;

/**
 * @author uchiyama
 *
 */
public class ActionUI {

	public static String UI_KEY = "ActionUI";

	public ActionUI(String name) throws FileNotFoundException, IOException {
		File file = null;
		if (name == null) {
	        //プロパティファイルの読み込み
	        file = new File("./name.properties");
		} else {
			file = new File(name);
		}
        if (file.exists()) {
        	properties.load(new FileInputStream(file));
        } else {
        	//エラー処理
        }
	}

	/**
	 *
	 */
	public ActionUI() throws IOException {
		this(null);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public String getName(String actionName) {
		System.out.println(actionName);
		if (properties.containsKey(actionName)) {
			return properties.getProperty(actionName);
		} else {
			return actionName.substring(actionName.lastIndexOf(".") + 1, actionName.length() - 6);
		}
	}

	public String getName(Action action) {
		return getName(action.getClass().getName());
	}

	Properties properties = new Properties();
}
