// (c) 2006 uchicom
package com.uchicom.csve.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.uchicom.csve.DefaultCsvTagEditorMenuBar;

/**
 * @author uchiyama
 *
 */
public class JMenuBarFactory {


    /**
     * メニューバーの作成を行う
     * @return
     */
    public JMenuBar createJMenuBar(UIStore uiStore) {
    	JMenuBar menuBar = new JMenuBar();
    	Properties properties = new Properties();
    	try {
    	properties.load(new FileInputStream(new File("menu.properties")));
    	} catch (Exception e) {
    		return new DefaultCsvTagEditorMenuBar(uiStore);
    	}
    	JMenu menu = null;
    	//1_0～メニュー情報を取りだす
    	for (int i = 0; i < properties.size(); i++) {
    		for (int j = 0; j < properties.size(); j++) {
	    		if (properties.containsKey(String.valueOf(i + "_" + j))) {
	    			String val = properties.getProperty(String.valueOf(i + "_" + j));
	    			if (val.startsWith("Menu,")) {
	    				//メニューを追加する
	    				//,で分割するサブストリングでもいいね
	    				menu = new JMenu(val.substring(5));
	    				menuBar.add(menu);
	    			} else if (val.startsWith("MenuItem,")) {
	    				//アクションを作成してメニューアイテムを作成する
	    				if (menu != null) {
	    					try {
		    			        UIAbstractAction action = (UIAbstractAction) Class.forName(val.substring(9).split(",")[0]).newInstance();
		    			        action.installUI(uiStore);
		    			        action.putValue(UIAbstractAction.NAME, val.substring(9).split(",")[1]);
		    			        JMenuItem menuItem = new JMenuItem(action);
		    					menu.add(menuItem);
		    			        uiStore.putUI(val.substring(9), menuItem);
	    					} catch (Exception e) {
	    						//とりあえずエラーはいとく
	    						e.printStackTrace();
	    					}

	    				}
	    			} else if (val.startsWith("CheckBoxMenuItem,")) {
	    				//アクションを作成してメニューアイテムを作成する
	    				if (menu != null) {
	    					try {
		    			        UIAbstractAction action = (UIAbstractAction) Class.forName(val.substring(17).split(",")[0]).newInstance();
		    			        action.installUI(uiStore);
		    			        action.putValue(UIAbstractAction.NAME, val.substring(17).split(",")[1]);
		    			        JMenuItem menuItem = new JCheckBoxMenuItem(action);
		    					menu.add(menuItem);
		    			        uiStore.putUI(val.substring(17), menuItem);
	    					} catch (Exception e) {
	    						//とりあえずエラーはいとく
	    						e.printStackTrace();
	    					}

	    				}
	    			} else if (val.startsWith("Separator")) {
	    				//セパレータを追加する
	    				if (menu != null) {
	    					menu.addSeparator();
	    				}
	    			}
	    		}
    		}
    	}
        return menuBar;

    }
}
