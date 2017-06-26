// (c) 2006 uchicom
package com.uchicom.csve;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.uchicom.csve.util.ActionUI;
import com.uchicom.csve.util.CSVReader;
import com.uchicom.csve.util.CellInfo;
import com.uchicom.csve.util.CellListTableModel;
import com.uchicom.csve.util.CellRenderer;
import com.uchicom.csve.util.JMenuBarFactory;
import com.uchicom.csve.util.SearchTable;
import com.uchicom.csve.util.SortTableColumn;
import com.uchicom.csve.util.SortTableColumnModel;
import com.uchicom.csve.util.StringCellInfo;
import com.uchicom.csve.util.TextAreaCellEditor;
import com.uchicom.csve.util.UIStore;
import com.uchicom.ui.FileOpener;

/**
 *
 * @author uchiyama
 */
public class CsvTagEditor extends JFrame implements CsvTagEditorUI {

	private Properties properties = new Properties();
	/** Creates a new instance of CsvTagEditor */
	public CsvTagEditor() {
		super("CsvTagEditor");
		this.self = this;
		initComponents();
	}

	private void initComponents() {
		initProperties();
		setWindowPosition(this, Constants.PROP_KEY_WINDOW_CSVE_POSITION);
		setWindowState(this, Constants.PROP_KEY_WINDOW_CSVE_STATE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				if (CsvTagEditor.this.getExtendedState() == JFrame.NORMAL) {
					// 画面の位置を保持する
					storeWindowPosition(CsvTagEditor.this, Constants.PROP_KEY_WINDOW_CSVE_POSITION);
				} else {
					storeWindowState(CsvTagEditor.this, Constants.PROP_KEY_WINDOW_CSVE_STATE);
				}
				storeProperties();
			}

		});
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent ce) {
				if (getExtendedState() == JFrame.NORMAL) {
					storeWindowPosition(CsvTagEditor.this, Constants.PROP_KEY_WINDOW_CSVE_POSITION);
				}
			}
			@Override
			public void componentResized(ComponentEvent ce) {
				if (getExtendedState() == JFrame.NORMAL) {
					storeWindowPosition(CsvTagEditor.this, Constants.PROP_KEY_WINDOW_CSVE_POSITION);
				}
			}
		});
		try {
			uiStore.putUI(ActionUI.UI_KEY, new ActionUI());
		} catch (IOException e) {
			return;
		}
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		uiStore.putUI(UI_KEY, this);


		FileOpener.installDragAndDrop(tabPane, this);
		getContentPane().add(tabPane);
		JMenuBarFactory menuBarFactory = new JMenuBarFactory();
		JMenuBar menuBar = menuBarFactory.createJMenuBar(uiStore);

		setJMenuBar(menuBar);
        pack();
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

			for (int i = 0; i < col; i++) {
				SortTableColumn column = new SortTableColumn();
				column.setHeaderValue(getColumnName(i));
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
		return properties;
	}

	@Override
	public Map<Integer, File> getFileMap() {
		return fileMap;
	}

	/* (非 Javadoc)
	 * @see com.uchicom.ui.FileOpener#open(java.io.File)
	 */
	@Override
	public void open(File file) throws IOException {
		if (tabPane.indexOfTab(file.getName()) >= 0) {
			int res = JOptionPane.showConfirmDialog(this, "すでに表示されています、編集中のタブを閉じてもよろしいですか？");
			switch (res) {
			case JOptionPane.CANCEL_OPTION:
				return;
			case JOptionPane.OK_OPTION:
				tabPane.removeTabAt(tabPane.indexOfTab(file.getName()));
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
		if (file.exists()) {
			properties.put("path", file.getCanonicalPath());
			//SJIS固定でファイルを開く、そのうち文字コード自動判定とか入れるか。
			CSVReader reader = new CSVReader(file, "UTF-8");
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
			getTableMap().put(new Integer(tabPane.getTabCount()), csvList);
			getFileMap().put(tabPane.getTabCount(), file);
			getTableList().add(table);
			//ファイル名でタブに追加する
			tabPane.add(file.getName(), new JScrollPane(table));
			tabPane.setSelectedIndex(tabPane.indexOfTab(file.getName()));
			//画面を整形して表示する
			pack();
		}
	}

	/* (非 Javadoc)
	 * @see com.uchicom.ui.FileOpener#open(java.util.List)
	 */
	@Override
	public void open(List<File> fileList) {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		for (File file : fileList) {
			try {
				open(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * 0スタート
	 *
	 * @param index
	 * @return
	 */
	public static String getColumnName(int index) {
		if (index / 26 > 0) {
			return new String(new char[] { (char) ('A' + (index / 26) - 1), (char) ('A' + (index % 26)) });
		} else {
			return new String(new char[] { (char) ('A' + index) });
		}
	}

	/**
	 * 0スタート
	 *
	 * @param columnName
	 * @return
	 */
	public static int getColumnIndex(String columnName) {
		if (columnName.length() > 1) {
			int up = columnName.charAt(0) - 'A';
			int down = columnName.charAt(1) - 'A';
			return (up + 1) * 26 + down;
		} else {
			int down = columnName.charAt(1) - 'A';
			return down;
		}
	}

	/**
	 * 画面の位置をプロパティに設定する。
	 *
	 * @param frame
	 * @param key
	 */
	private void storeWindowPosition(JFrame frame, String key) {
		String value = frame.getLocation().x + Constants.PROP_SPLIT_CHAR + frame.getLocation().y + Constants.PROP_SPLIT_CHAR
				+ frame.getWidth() + Constants.PROP_SPLIT_CHAR + frame.getHeight() + Constants.PROP_SPLIT_CHAR;
		properties.setProperty(key, value);
	}
	/**
	 * 画面の位置をプロパティに設定する。
	 *
	 * @param frame
	 * @param key
	 */
	private void storeWindowState(JFrame frame, String key) {
		String value = frame.getState() + Constants.PROP_SPLIT_CHAR
				+ frame.getExtendedState();
		properties.setProperty(key, value);
	}

	/**
	 * 画面のサイズをプロパティから設定する。
	 *
	 * @param frame
	 * @param key
	 */
	public void setWindowPosition(JFrame frame, String key) {
		if (properties.containsKey(key)) {
			String initPoint = properties.getProperty(key);
			String[] points = initPoint.split(Constants.PROP_SPLIT_CHAR);
			if (points.length > 3) {
				frame.setLocation(Integer.parseInt(points[0]), Integer.parseInt(points[1]));
				frame.setPreferredSize(new Dimension(Integer.parseInt(points[2]), Integer.parseInt(points[3])));
			}
		}
	}
	public void setWindowState(JFrame frame, String key) {
		if (properties.containsKey(key)) {
			String initPoint = properties.getProperty(key);
			String[] points = initPoint.split(Constants.PROP_SPLIT_CHAR);
			if (points.length > 1) {
				frame.setState(Integer.parseInt(points[0]));
				frame.setExtendedState(Integer.parseInt(points[1]));
			}
		}
	}

	/**
	 *
	 */

	private void initProperties() {
		if (Constants.CONF_FILE.exists() && Constants.CONF_FILE.isFile()) {
			try (FileInputStream fis = new FileInputStream(Constants.CONF_FILE);) {
				properties.load(fis);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private void storeProperties() {
		try {
			if (!Constants.CONF_FILE.exists()) {
				Constants.CONF_FILE.getParentFile().mkdirs();
				Constants.CONF_FILE.createNewFile();
			}
			try (FileOutputStream fos = new FileOutputStream(Constants.CONF_FILE);) {
				properties.store(fos, Constants.APP_NAME + " Ver" + Constants.VERSION);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
