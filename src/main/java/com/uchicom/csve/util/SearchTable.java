// (C) 2006 uchicom
package com.uchicom.csve.util;

import java.util.List;
import javax.swing.JTable;

/**
 * @author uchiyama
 */
public class SearchTable extends JTable {

  /** */
  private static final long serialVersionUID = 1L;

  public SearchTable(ListTableModel tableModel) {
    super(tableModel);
  }

  public SearchTable(CellListTableModel tableModel) {
    super(tableModel);
    this.setDefaultRenderer(this.getColumnClass(1), new CellRenderer());
    this.tableHeader.addMouseListener(new SortMouseListener());
    this.tableHeader.addMouseListener(new FilterMouseListener());
    this.dataModel = tableModel;
  }

  protected CellListTableModel dataModel;
  protected SortTableColumnModel columnModel;

  public SearchTable(CellListTableModel tableModel, SortTableColumnModel columnModel) {
    super(tableModel, columnModel);
    this.setDefaultRenderer(this.getColumnClass(1), new CellRenderer());
    this.tableHeader.addMouseListener(new SortMouseListener());
    this.tableHeader.addMouseListener(new FilterMouseListener());
    this.dataModel = tableModel;
    this.columnModel = columnModel;
  }

  /**
   * 対象の文字列を検索する。
   *
   * @param target
   * @return
   */
  public boolean searchAndSelect(String target) {
    // 検索結果をフラグで格納する

    // 選択セル以降の中で見つける
    int y = this.getSelectedColumn();
    int x = this.getSelectedRow();
    if (x < 0) {
      x = 0;
    }
    if (y < 0) {
      y = 0;
    }
    CellListTableModel tableModel = (CellListTableModel) getModel();
    Point searchPoint = tableModel.searchAndSelect(target, x, y, false);
    if (searchPoint.isExist()) {
      this.getSelectionModel().clearSelection();
      this.setColumnSelectionInterval(searchPoint.getY(), searchPoint.getY());
      this.setRowSelectionInterval(searchPoint.getX(), searchPoint.getX());
      // while(searchPoint.isExist()) {
      //			this.getSelectionModel().clearSelection();
      //			this.setColumnSelectionInterval(searchPoint.getY(), searchPoint.getY());
      //			this.setRowSelectionInterval(searchPoint.getX(), searchPoint.getX());
      // これだと縦選択になっちゃうよ。
      // searchPoint = tableModel.searchAndSelect(target, searchPoint.getX(),
      // searchPoint.getY());

    }

    return false;
  }

  /**
   * 対象の文字列を検索する。
   *
   * @param target
   * @return
   */
  public boolean searchAndReplace(String target, String replace) {
    // 検索結果をフラグで格納する

    // 選択セル以降の中で見つける
    int y = this.getSelectedColumn();
    int x = this.getSelectedRow();
    if (x < 0) {
      x = 0;
    }
    if (y < 0) {
      y = 0;
    }
    CellListTableModel tableModel = (CellListTableModel) getModel();
    Point searchPoint = tableModel.searchAndSelect(target, x, y, true);
    if (searchPoint.isExist()) {
      this.getSelectionModel().clearSelection();
      this.setColumnSelectionInterval(searchPoint.getY(), searchPoint.getY());
      this.setRowSelectionInterval(searchPoint.getX(), searchPoint.getX());
      this.setValueAt(replace, searchPoint.getX(), searchPoint.getY());
      this.repaint();
    }
    return false;
  }

  /**
   * 対象の文字列を検索する。
   *
   * @param target
   * @return
   */
  public boolean searchAndReplaceAll(String target, String replace) {
    // 検索結果をフラグで格納する

    // 選択セル以降の中で見つける
    int y = this.getSelectedColumn();
    int x = this.getSelectedRow();
    if (x < 0) {
      x = 0;
    }
    if (y < 0) {
      y = 0;
    }
    CellListTableModel tableModel = (CellListTableModel) getModel();
    Point searchPoint = tableModel.searchAndSelect(target, 0, 0, true);
    this.getSelectionModel().clearSelection();
    while (searchPoint.isExist()) {
      this.setValueAt(replace, searchPoint.getX(), searchPoint.getY());
      searchPoint =
          tableModel.searchAndSelect(target, searchPoint.getX(), searchPoint.getY(), false);
    }
    this.repaint();
    return false;
  }

  public void removeRow() {
    CellListTableModel tableModel = (CellListTableModel) getModel();
    tableModel.removeRows(this.getSelectedRows());
  }

  public void addRow() {
    CellListTableModel tableModel = (CellListTableModel) getModel();
    tableModel.addRows(this.getSelectedRows());
  }

  /**
   * 並び替えを維持した状態での並び替えが可能 途中で選択されている場合は選択しているところの上下を変更する。
   *
   * @param col
   * @param isHold
   */
  public void sortTable(int col, boolean isHold) {
    columnModel.setSort(col, isHold);

    dataModel.sort(columnModel.getComparator());

    this.repaint();
  }

  public int mode = 0;

  public int init = 1;

  public static final int MODE_TYPE_NORMAL = 0;
  public static final int MODE_TYPE_INIT = 1;
  public static final int MODE_TYPE_FILTER = 2;
  public SortTableColumn tmpColumn = null;

  public boolean isInitMode() {
    if (mode == init) {
      return true;
    } else {
      return false;
    }
  }

  public boolean isMode(int mode) {
    return (this.mode & mode) != 0;
  }

  public void setMode(int mode) {
    this.mode = mode;
    this.repaint();
  }

  public void addMode(int mode) {
    this.mode = this.mode | mode;
    this.repaint();
  }

  public void removeMode(int mode) {
    this.mode = this.mode & ~mode;
  }

  public void insertRow(int col, List<CellInfo[]> addList) {
    ((CellListTableModel) dataModel).addList(col, addList);
  }

  /**
   * filterしたリストをdatamodelにリストで保持して、モード選択によってリストを切り替える。
   *
   * @param filterIf
   */
  public void filter(FilterIf filterIf) {}
}
