// (c) 2006 uchicom
package com.uchicom.csve.util;

import javax.swing.DefaultListModel;
import javax.swing.table.TableModel;

/**
 * @author uchiyama
 *
 */
public class RowHeaderListModel extends DefaultListModel {

	public RowHeaderListModel(TableModel tableModel) {
		this.tableModel = tableModel;
	}
	public Object get(int index) {
		return String.valueOf(index);
	}
	public int size() {
		return tableModel.getRowCount();
	}

	private TableModel tableModel = null;

	public Object[] toArray() {
		System.out.println("toArray");
		return super.toArray();
	}
	public Object getElementAt(int index) {
		return String.valueOf(index);
	}
	public Object elementAt(int index) {
		return String.valueOf(index);
	}
}
