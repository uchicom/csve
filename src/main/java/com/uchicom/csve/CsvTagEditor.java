// (c) 2006 uchicom
package com.uchicom.csve;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.DefaultCellEditor;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.uchicom.csve.util.ActionUI;
import com.uchicom.csve.util.CellInfo;
import com.uchicom.csve.util.CellListTableModel;
import com.uchicom.csve.util.CellRenderer;
import com.uchicom.csve.util.JMenuBarFactory;
import com.uchicom.csve.util.SearchTable;
import com.uchicom.csve.util.SortTableColumn;
import com.uchicom.csve.util.SortTableColumnModel;
import com.uchicom.csve.util.StringCellInfo;
import com.uchicom.csve.util.UIStore;

/**
 *
 * @author uchiyama
 */
public class CsvTagEditor extends JFrame implements CsvTagEditorUI {
	private File confFile = new File("./conf/csve.properties");
	private Properties conf = new Properties();

	/** Creates a new instance of CsvTagEditor */
	public CsvTagEditor() {
		super("CsvTagEditor");
		this.self = this;
		try {
			uiStore.putUI(ActionUI.UI_KEY, new ActionUI());
		} catch (IOException e) {
			return;
		}
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		uiStore.putUI(UI_KEY, this);

		try (FileInputStream fis = new FileInputStream(confFile)) {
			conf.load(fis);


				String bounds = conf.getProperty("bounds");
				if (bounds != null && !"".equals(bounds)) {
					String[] boundsValue = bounds.split(",");
					if (boundsValue.length == 4) {
						this.setLocation(Integer.parseInt(boundsValue[0]),
						Integer.parseInt(boundsValue[1]));
						this.setPreferredSize(new Dimension(Integer.parseInt(boundsValue[2]),
								Integer.parseInt(boundsValue[3])));
					}
				}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		getContentPane().add(tabPane);
		JMenuBarFactory menuBarFactory = new JMenuBarFactory();
		JMenuBar menuBar = menuBarFactory.createJMenuBar(uiStore);

		setJMenuBar(menuBar);
	}

	/**
	 * テーブル作成
	 */
	public void createTable(int row, int col) {
		try {
			List<CellInfo[]> cvsList = new ArrayList<CellInfo[]>(row);
			String[] rowHeads = new String[row];
			for (int i = 0; i < row; i++) {
				CellInfo[] strings = new CellInfo[col];
				for (int j = 0; j < col; j++) {
					strings[j] = new StringCellInfo("");
				}
				cvsList.add(strings);
				rowHeads[i] = String.valueOf(i);
			}

			// テーブルの列モデルを作成する
			SortTableColumnModel columnModel = new SortTableColumnModel();

			char[] ind = new char[3];
			for (int i = 0; i < col; i++) {
				SortTableColumn column = new SortTableColumn();

				if (i < 26) {
					column.setHeaderValue((char) ('A' + (i % 26)));
				} else if (i < 26 * 26) {
					ind[0] = (char) ('A' + (i / 26));
					ind[1] = (char) ('A' + ((i - 1) % 26));
					column.setHeaderValue(String.valueOf(ind));
				} else if (i < 26 * 26 * 26) {
					ind[0] = (char) ('A' + (i / (26 * 26)));
					ind[1] = (char) ('A' + (i / 26));
					ind[2] = (char) ('A' + ((i - 1) % 26));
					column.setHeaderValue(String.valueOf(ind));
				}
				column.setModelIndex(i);
				column.setIdentifier(String.valueOf(i));
				column.setCellRenderer(new CellRenderer());
				// tableColumnを使う場合はcellEditorを設定してあげないとちゃんと動かない
				JTextField editableTextField = new JTextField();
				editableTextField.setEditable(true);
				column.setCellEditor(new DefaultCellEditor(editableTextField));
				columnModel.addColumn(column);

			}
			CellListTableModel tableModel = new CellListTableModel(cvsList, col);
			SearchTable table = new SearchTable(tableModel, columnModel);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.setColumnSelectionAllowed(true);
			table.setRowSelectionAllowed(true);

			list.add(table);
			map.put(new Integer(tabPane.getTabCount()), cvsList);
			JScrollPane scrollPane = new JScrollPane(table);
			// 行ヘッダはまだ敷居が高いな
			// scrollPane.setRowHeaderView(new JList(rowHeads));
			tabPane.add("Create(" + (tabPane.getTabCount() + 1) + ")", scrollPane);
			self.pack();
		} catch (Error error) {
			error.printStackTrace();
		}
	}

	/**
	 * このオブジェクトを返す
	 */
	public Component getBasisComponent() {
		return this;
	}

	/**
	 * タブを追加する
	 */
	public void addTab(String tabName, JTable table, List csvList) {
		map.put(new Integer(tabPane.getTabCount()), csvList);
		// ファイル名でタブに追加する
		tabPane.add(tabName, new JScrollPane(table));
	}

	/**
	 * 画面終了時に、画面位置を設定ファイルに保存する。
	 */
	public void dispose() {
		// メモリ上の設定情報を取得する。
		// Window位置を取得する
		// ファイルに書き出す
		try (FileOutputStream fos = new FileOutputStream(confFile)) {
			Rectangle rectangle = this.getBounds();
			conf.put("bounds", ((int)rectangle.getX()) + "," + ((int)rectangle.getY()) + "," + ((int)rectangle.getWidth()) + ","
					+ ((int)rectangle.getHeight()));
			conf.store(fos, "properties");
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		super.dispose();

	}

	/** メイン画面 */
	private JFrame self = null;
	/** テーブル情報を格納しているタブ */
	private JTabbedPane tabPane = new JTabbedPane(JTabbedPane.BOTTOM);
	/** テーブルを保持するマップ */
	private Map<Integer, List> map = new HashMap<>();

	/** ファイルパスを保持するマップ */
	private Map<Integer,  File> fileMap = new HashMap<>();
	/** ユーザーインターフェース保持クラス */
	private UIStore uiStore = new UIStore();

	List<SearchTable> list = new ArrayList<SearchTable>();

	/*
	 * (非 Javadoc)
	 *
	 * @see cymsgk.CsvTagEditorUI#getTableList()
	 */
	public List<SearchTable> getTableList() {
		// マップのコレクションをリストに入れ替える
		return list;
	}

	public SearchTable getSelectedTable() {
		return list.get(tabPane.getSelectedIndex());

	}

	public CellInfo[] createStringCellInfo(String[] lines, int column) {
		StringCellInfo[] cells = new StringCellInfo[column];
		for (int i = 0; i < column; i++) {
			if (i < lines.length) {
				cells[i] = new StringCellInfo(lines[i]);
			} else {
				cells[i] = new StringCellInfo("");
			}
		}
		return cells;
	}

	public void sortTable(int columnIndex, boolean isHold) {
		SearchTable table = getSelectedTable();
		table.sortTable(columnIndex, isHold);
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see cymsgk.CsvTagEditorUI#getTabb()
	 */
	public JTabbedPane getTabb() {
		return tabPane;
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see cymsgk.CsvTagEditorUI#getTableMap()
	 */
	public Map<Integer, List> getTableMap() {
		return map;
	}

	@Override
	public Properties getProperties() {
		return conf;
	}

	@Override
	public Map<Integer, File> getFileMap() {
		return fileMap;
	}
}
