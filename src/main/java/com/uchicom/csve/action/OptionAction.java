// (C) 2006 uchicom
package com.uchicom.csve.action;

import com.uchicom.csve.util.CellInfo;
import com.uchicom.csve.util.ListTableModel;
import com.uchicom.csve.util.SeparateReader;
import com.uchicom.csve.util.StringCellInfo;
import com.uchicom.csve.util.UIAbstractAction;
import com.uchicom.csve.window.CsvTagEditorUI;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * オプション画面を表示するクラス
 *
 * @author uchiyama
 */
public class OptionAction extends UIAbstractAction {

  /** */
  private static final long serialVersionUID = 1L;

  /** 設定情報を表示するアクション処理 */
  public void actionPerformed(ActionEvent e) {
    CsvTagEditorUI csvTagEditorUI = (CsvTagEditorUI) uiStore.getUI(CsvTagEditorUI.UI_KEY);

    // プロパティファイルの読み込み
    File file = new File("./conf/csve.properties");
    if (file.exists()) {
      // ファイルがある場合は設定ファイルを読み込む
      openOptionFile(file, csvTagEditorUI);
    } else {
      // ファイルがない場合は作成する。
      createOptionFile(file, csvTagEditorUI);
    }
  }

  /**
   * 設定ファイル作成処理
   *
   * @param file
   * @param csvTagEditorUI
   */
  private void createOptionFile(File file, CsvTagEditorUI csvTagEditorUI) {
    try {
      file.createNewFile();
    } catch (IOException ioe) {
      JOptionPane.showMessageDialog(
          csvTagEditorUI.getBasisComponent(), "設定ファイルが作成できませんでした。(" + file.getAbsoluteFile() + ")");
    }
  }

  /**
   * 設定ファイル読込処理
   *
   * @param file
   * @param csvTagEditorUI
   */
  private void openOptionFile(File file, CsvTagEditorUI csvTagEditorUI) {
    // =でセパレートして行を作成
    SeparateReader reader = new SeparateReader(file, "SJIS", '=');

    // プロパティファイルのシート作成
    List<CellInfo[]> csvList = new ArrayList<>();
    // 一行ずつCSVを取得する
    String[] lines = reader.getNextSeparateLine();
    int columnCount = 0;
    while (lines != null) {
      if (columnCount < lines.length) {
        columnCount = lines.length;
      }
      CellInfo[] cells = new CellInfo[columnCount];
      csvList.add(cells);
      for (int j = 0; j < columnCount; j++) {
        cells[j] = new StringCellInfo(lines[j]);
      }
      lines = reader.getNextSeparateLine();
    }
    // テーブル列モデル作成
    TableColumnModel columnModel = new DefaultTableColumnModel();
    TableColumn tableColumn = new TableColumn();
    tableColumn.setHeaderValue("プロパティ名");
    tableColumn.setModelIndex(0);

    columnModel.addColumn(tableColumn);

    tableColumn = new TableColumn();
    tableColumn.setHeaderValue("設定値");
    tableColumn.setModelIndex(1);
    columnModel.addColumn(tableColumn);

    // テーブルモデルと、列数を格納する
    JTable table = new JTable(new ListTableModel(csvList, columnCount), columnModel);
    // テーブルのリサイズをなしにする
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    // 列選択可能にする
    table.setColumnSelectionAllowed(true);
    // 行選択可能にする
    table.setRowSelectionAllowed(true);
    csvTagEditorUI.addTab("設定", table, csvList);
    // ファイルのクローズ処理をする
    reader.close();
  }
}
