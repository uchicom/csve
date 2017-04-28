// (c) 2006 uchicom
package com.uchicom.csve;


import java.awt.Component;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.JTabbedPane;
import javax.swing.JTable;

import com.uchicom.csve.util.SearchTable;

/**
 *
 * @author uchiyama
 */
public interface CsvTagEditorUI {
    String UI_KEY = "CsvTagEditorUI";
    public void createTable(int row, int col);

    /**
     * 親コンポーネントを取得する。
     * @return
     */
    public Component getBasisComponent();

    public List<SearchTable> getTableList();
    public  Map<Integer, List> getTableMap();
    public Map<Integer, File> getFileMap();
    /**
     * タブペインを取得します。
     * @return
     */
    public JTabbedPane getTabb();

    /**
     * テーブルを追加します。
     * @param tabName
     * @param table
     * @param list
     */
    public void addTab(String tabName, JTable table, List list);

    public SearchTable getSelectedTable();

    public void sortTable(int columnIndex, boolean isHold);

    public Properties getProperties();
}
