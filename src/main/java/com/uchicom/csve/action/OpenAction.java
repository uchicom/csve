// (c) 2006 uchicom
package com.uchicom.csve.action;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import com.uchicom.csve.CsvTagEditor;
import com.uchicom.csve.CsvTagEditorUI;
import com.uchicom.csve.util.CSVReader;
import com.uchicom.csve.util.CellInfo;
import com.uchicom.csve.util.CellListTableModel;
import com.uchicom.csve.util.CellRenderer;
import com.uchicom.csve.util.SearchTable;
import com.uchicom.csve.util.SortTableColumn;
import com.uchicom.csve.util.SortTableColumnModel;
import com.uchicom.csve.util.TextAreaCellEditor;
import com.uchicom.csve.util.UIAbstractAction;

/**
 *
 * @author uchiyama
 */
public class OpenAction extends UIAbstractAction {



    public void actionPerformed(ActionEvent actionEvent) {
        try {
	        CsvTagEditorUI csvTagEditorUI = (CsvTagEditorUI)uiStore.getUI(CsvTagEditorUI.UI_KEY);
	        Component basisComponent = csvTagEditorUI.getBasisComponent();
	        JTabbedPane tabPane = csvTagEditorUI.getTabb();
	        //開くファイルを指定する画面を開く
	        JFileChooser chooser = new JFileChooser();
	        Properties properties = csvTagEditorUI.getProperties();
	        String path = properties.getProperty("path");
	        if (path != null && !"".equals(path)) {
	        	chooser.setSelectedFile(new File(path));;
	        }
	        int result = chooser.showOpenDialog(basisComponent);
	        //開いたファイルをオブジェクトで取得する
	        if (result == JFileChooser.APPROVE_OPTION) {
	        File selectFile = chooser.getSelectedFile();

	        if (selectFile != null) {
		        if (tabPane.indexOfTab(selectFile.getName()) >= 0) {
		     	   int res =JOptionPane.showConfirmDialog(basisComponent, "すでに表示されています、編集中のタブを閉じてもよろしいですか？");
		     	   switch (res) {
		     	   case JOptionPane.CANCEL_OPTION:
		     		   return;
		     	   case JOptionPane.OK_OPTION:
		     		   tabPane.removeTabAt(tabPane.indexOfTab(selectFile.getName()));
		     		   break;
		     	   case JOptionPane.CLOSED_OPTION:
		     		   return;
		     	   case JOptionPane.NO_OPTION:
		     		   return;
		     		default:
		     			return;

		     	   }


		        }
		        //
		        if (selectFile.exists()) {
		        	properties.put("path", selectFile.getCanonicalPath());
			        //SJIS固定でファイルを開く、そのうち文字コード自動判定とか入れるか。
			        CSVReader reader = new CSVReader(selectFile, "UTF-8");
			        //CSVファイルを格納するリストを作成
			        CellInfo[] lines = reader.getNextCsvLineCellInfo();

			         	//一行ずつCSVを取得する

			         int columnCount = 0;
			         List<CellInfo[]> csvList = new ArrayList<CellInfo[]>();
			         CellInfo[] tmp = lines;
			         while (lines != null) {

			//ファイルのオープンでエラーが発生している。

			             if (columnCount < lines.length) {
			                 columnCount = lines.length;
			                 tmp = lines;
			             }
			             csvList.add(lines);

			             lines = reader.getNextCsvLineCellInfo();
			         }
			         System.out.println(csvList.size());
			         SortTableColumnModel columnModel = new SortTableColumnModel();
		        	 char[] ind = new char[3];

			         for (int i = 0; i < tmp.length; i++) {

			        	SortTableColumn column = new SortTableColumn();

			        	if ( i < 26) {
			        		column.setHeaderValue((char)('A' + (i % 26)));
			        	} else if (i < 26 * 26) {
			        		ind[0] = (char)('A' + (i / 26));
			        		ind[1] = (char)('A' + ((i - 1) % 26));
			        		column.setHeaderValue(String.valueOf(ind));
			        	} else if (i < 26*26 * 26) {
			        		ind[0] = (char)('A' + (i / (26 * 26)));
			        		ind[1] = (char)('A' + (i / 26));
			        		ind[2] = (char)('A' + ((i - 1) % 26));
			        		column.setHeaderValue(String.valueOf(ind));
			        	}
			        	column.setModelIndex(i);
			        	column.setIdentifier(String.valueOf(i));
			        	column.setCellRenderer(new CellRenderer());
			        	//tableColumnを使う場合はcellEditorを設定してあげないとちゃんと動かない
			            column.setCellEditor(new TextAreaCellEditor());
			        	columnModel.addColumn(column);

			         }
			 	        //ファイルのクローズ処理をする
			 	        reader.close();
		 	        //テーブルモデルと、列数を格納する
		 	        SearchTable table = new SearchTable(new CellListTableModel(csvList, columnCount), columnModel);
		 	        //テーブルのリサイズをなしにする
		 	        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		 	        //列選択可能にする
		 	        table.setColumnSelectionAllowed(true);
		 	        //行選択可能にする
		 	        table.setRowSelectionAllowed(true);
		 	        csvTagEditorUI.getTableMap().put(new Integer(tabPane.getTabCount()), csvList);
			 	       csvTagEditorUI.getFileMap().put(tabPane.getTabCount(), selectFile);
		 	        csvTagEditorUI.getTableList().add(table);
		 	        //ファイル名でタブに追加する
		 	        tabPane.add(selectFile.getName(), new JScrollPane(table));
		            tabPane.setSelectedIndex(tabPane.indexOfTab(selectFile.getName()));
		 	        //画面を整形して表示する
		 	        ((CsvTagEditor)basisComponent).pack();
		        }
	        }
	        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
