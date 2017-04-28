// (c) 2006 uchicom
package com.uchicom.csve;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.uchicom.csve.action.ConvertAction;
import com.uchicom.csve.action.CreateAction;
import com.uchicom.csve.action.CutAction;
import com.uchicom.csve.action.DeleteAction;
import com.uchicom.csve.action.EncryptAction;
import com.uchicom.csve.action.OpenAction;
import com.uchicom.csve.action.OptionAction;
import com.uchicom.csve.action.PasteAction;
import com.uchicom.csve.action.PrintAction;
import com.uchicom.csve.action.PrintOptionAction;
import com.uchicom.csve.action.RedoAction;
import com.uchicom.csve.action.ReplaceAction;
import com.uchicom.csve.action.SaveAction;
import com.uchicom.csve.action.SaveAllAction;
import com.uchicom.csve.action.SearchAction;
import com.uchicom.csve.action.UndoAction;
import com.uchicom.csve.action.VersionAction;
import com.uchicom.csve.util.UIAbstractAction;
import com.uchicom.csve.util.UIStore;

/**
 * @author uchiyama
 *
 */
public class DefaultCsvTagEditorMenuBar extends JMenuBar {

	public DefaultCsvTagEditorMenuBar(UIStore uiStore) {
		JMenu menu = new JMenu("File");
        this.add(menu);
        UIAbstractAction action = new CreateAction();
        action.installUI(uiStore);
        //作成
        menu.add(new JMenuItem(action));
        action = new OpenAction();
        action.installUI(uiStore);
        JMenuItem menuItem = new JMenuItem(action);
        menu.add(menuItem);
        //保存
        action = new SaveAction();
        action.installUI(uiStore);
        menuItem = new JMenuItem(action);
        menu.add(menuItem);
        //全保存
        action = new SaveAllAction();
        action.installUI(uiStore);
        menuItem = new JMenuItem(action);
        menu.add(menuItem);
        menu.addSeparator();
        //印刷
        action = new PrintAction();
        action.installUI(uiStore);
        menuItem = new JMenuItem(action);
        menu.add(menuItem);
        //印刷設定
        action = new PrintOptionAction();
        action.installUI(uiStore);
        menuItem = new JMenuItem(action);
        menu.add(menuItem);

        //EditMenu

        menu = new JMenu("Edit");
        this.add(menu);

        //undo redo
        action = new UndoAction();
        action.installUI(uiStore);
        menuItem = new JMenuItem(action);
        menu.add(menuItem);

        //undo redo
        action = new RedoAction();
        action.installUI(uiStore);
        menuItem = new JMenuItem(action);
        menu.add(menuItem);
        menu.addSeparator();
        //undo redo
        action = new CutAction();
        action.installUI(uiStore);
        menuItem = new JMenuItem(action);
        menu.add(menuItem);
        //undo redo
        action = new PasteAction();
        action.installUI(uiStore);
        menuItem = new JMenuItem(action);
        menu.add(menuItem);
        //undo redo
        action = new DeleteAction();
        action.installUI(uiStore);
        menuItem = new JMenuItem(action);
        menu.add(menuItem);
        menu.addSeparator();
        //undo redo
        action = new SearchAction();
        action.installUI(uiStore);
        menuItem = new JMenuItem(action);
        menu.add(menuItem);
        //undo redo
        action = new ReplaceAction();
        action.installUI(uiStore);
        menuItem = new JMenuItem(action);
        menu.add(menuItem);
        //undo redo
        action = new ConvertAction();
        action.installUI(uiStore);
        menuItem = new JMenuItem(action);
        menu.add(menuItem);
        //undo redo
        action = new EncryptAction();
        action.installUI(uiStore);
        menuItem = new JMenuItem(action);
        menu.add(menuItem);

        //EditMenu

        menu = new JMenu("Help");
        this.add(menu);

        //undo redo
        action = new VersionAction();
        action.installUI(uiStore);
        menuItem = new JMenuItem(action);
        menu.add(menuItem);


        //設定
        action = new OptionAction();
        action.installUI(uiStore);
        menuItem = new JMenuItem(action);
        menu.add(menuItem);
	}
}
