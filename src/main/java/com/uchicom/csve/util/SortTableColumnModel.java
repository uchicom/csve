// (c) 2006 uchicom
package com.uchicom.csve.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.table.DefaultTableColumnModel;

/**
 * @author uchiyama
 *
 */
public class SortTableColumnModel extends DefaultTableColumnModel {

	public void setSort(int col, boolean isHold) {

		SortTableColumn column = (SortTableColumn) this.getColumn(col);

		//columnModelに並び替え情報持たせたいな
		column.setSort(false);
    	if (isHold) {
    		//今の並び替え優先順位を維持しつつ並び替え
    		column.setHold(!column.isHold());
    		//並び替えリストになければ追加
    		if (!sortList.contains(column)) {
    			sortList.add(column);
        		column.setSortIndex(sortList);
    		}
    	}

    	//一時退避領域に格納しておく
		if (tmpColumn != null && column != tmpColumn && !tmpColumn.isHold()) {
			tmpColumn.setSortEnd();
		}
		tmpColumn = column;
	}

	public Comparator<CellInfo[]> getComparator() {

		sortingList.clear();
		sortingList.addAll(sortList);
		if (!sortList.contains(tmpColumn)) {
			sortingList.add(tmpColumn);
		}
    	//並び替えの処理を行う。
    	return new CellInfoComparator(sortingList);
	}

	List<SortTableColumn> sortingList = new ArrayList<SortTableColumn>();
	List<SortTableColumn> sortList = new ArrayList<SortTableColumn>();

	public SortTableColumn tmpColumn = null;

}
