// (c) 2006 uchicom
package com.uchicom.csve.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author uchiyama
 *
 */
public class CellRenderer extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextArea selectedField = new JTextArea();
	private JTextArea selectedInitField = new JTextArea();
	private JTextArea selectedFocusField = new JTextArea();
	private JTextArea selectedFocusInitField = new JTextArea();
	private JTextArea focusField = new JTextArea();
	/** 通常表示のフィールド */
	private JTextArea normalField = new JTextArea();
	/** 追加した行のフィールド */
	private JTextArea insertField = new JTextArea();
	/** 更新した行のフィールド */
	private JTextArea updateField = new JTextArea();
	private JTextArea updateModifyField = new JTextArea();

	private CellInfo cellInfo = null;
	private StringCellInfo stringCellInfo = new StringCellInfo("");
	private SearchTable searchTable = null;

	public CellRenderer() {
		super();
		selectedField.setBackground(Color.LIGHT_GRAY);
		selectedField.setFont(selectedField.getFont().deriveFont(Font.PLAIN));
		selectedField.setOpaque(true);
		selectedInitField.setBackground(Color.PINK);
		selectedInitField.setFont(selectedField.getFont().deriveFont(Font.PLAIN));
		selectedInitField.setOpaque(true);
		selectedFocusField.setBackground(Color.DARK_GRAY);
		selectedFocusField.setForeground(Color.WHITE);
		selectedFocusField.setOpaque(true);
		selectedFocusInitField.setBackground(Color.RED);
		selectedFocusInitField.setForeground(Color.WHITE);
		selectedFocusInitField.setOpaque(true);
		normalField.setBackground(Color.WHITE);
		normalField.setFont(normalField.getFont().deriveFont(Font.PLAIN));
		normalField.setOpaque(true);

		insertField.setBackground(Color.YELLOW);
		insertField.setFont(insertField.getFont().deriveFont(Font.PLAIN));
		insertField.setOpaque(true);
		updateField.setBackground(Color.GREEN);
		updateField.setFont(updateField.getFont().deriveFont(Font.PLAIN));
		updateField.setOpaque(true);
		updateModifyField.setBackground(Color.GREEN);
		updateModifyField.setFont(updateModifyField.getFont().deriveFont(Font.PLAIN));
		updateModifyField.setBorder(new BasicBorders.FieldBorder(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK));
		updateModifyField.setOpaque(true);

	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see
	 * javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(
	 * javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		searchTable = (SearchTable) table;
		cellInfo = (CellInfo) value;
		if (cellInfo == null) {
			cellInfo = stringCellInfo;
		}
		if (cellInfo instanceof StringCellInfo) {
			if (((StringCellInfo) cellInfo).getLnCount() >= 1) {
				table.setRowHeight(row, table.getRowHeight() * (((StringCellInfo) cellInfo).getLnCount() + 1));
			}
		}
		if (isSelected) {
			if (hasFocus) {
				// フォーカスあり選択ありのセルを返す
				if (searchTable.isInitMode()) {
					if (cellInfo.getBkValue() == null) {
						selectedFocusInitField.setText(stringCellInfo.toString());
					} else {
						selectedFocusInitField.setText(cellInfo.getBkValue().toString());
					}
					return selectedFocusInitField;
				} else {
					selectedFocusField.setText(cellInfo.toString());
					return selectedFocusField;
				}
			} else {
				// 選択ありのセルを返す
				if (searchTable.isInitMode()) {
					if (cellInfo.getBkValue() == null) {
						selectedInitField.setText(stringCellInfo.toString());
					} else {
						selectedInitField.setText(cellInfo.getBkValue().toString());
					}
					return selectedInitField;
				} else {
					selectedField.setText(cellInfo.toString());
					return selectedField;
				}
			}
		} else if (hasFocus) {
			// フォーカスあり状態のセルを返す
			focusField.setText(cellInfo.toString());
			return focusField;
		} else {
			// 通常のセル表示を返す
			if (cellInfo.isInserted()) {
				insertField.setText(cellInfo.toString());
				return insertField;
			} else if (cellInfo.isUpdated()) {
				if (cellInfo.isModified()) {
					updateModifyField.setText(cellInfo.toString());
					return updateModifyField;
				} else {
					updateField.setText(cellInfo.toString());
					return updateField;
				}
			} else {
				normalField.setText(cellInfo.toString());
				return normalField;
			}
		}
	}

}
