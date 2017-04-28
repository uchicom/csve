// (c) 2006 uchicom
package com.uchicom.csve.util;

import java.util.Comparator;
import java.util.List;

/**
 * @author uchiyama
 *
 */
public class CellInfoComparator implements Comparator {

	int iMaxList = 0;
	/** 並び替え列 */
	int[] sortIndexes = null;
	/** 並び替え種別ASC,DESC */
	int[] sortTypes = null;

	/**
	 * 並び替え順を引数に指定したコンストラクタ
	 * @param sortList
	 */
	public CellInfoComparator(List<SortTableColumn> sortList) {

		iMaxList = sortList.size();

		sortIndexes = new int[iMaxList];
		sortTypes = new int[iMaxList];

		for (int iList = 0; iList < iMaxList; iList++) {
			SortTableColumn tableColumn = sortList.get(iList);
			sortIndexes[iList] = tableColumn.getModelIndex();
			sortTypes[iList] = tableColumn.getSortType();
		}
	}
	/* (非 Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object o1, Object o2) {
		CellInfo[] cells1 = (CellInfo[])o1;
		CellInfo[] cells2 = (CellInfo[])o2;


		//空文字は一番大きくしないと、並び替え使いずらい。
		for (int iList = 0; iList < iMaxList; iList++) {
			if (cells1[sortIndexes[iList]].isEmpty() ) {
				if (cells2[sortIndexes[iList]].isEmpty()) {
					return 0;
				} else {
					return sortTypes[iList];
				}
			} else if (cells2[sortIndexes[iList]].isEmpty()) {
				return sortTypes[iList] * -1;
			} else {
				int comp = cells1[sortIndexes[iList]].compareTo(cells2[sortIndexes[iList]]);
				if (comp != 0) {
					return comp * sortTypes[iList];
				}
			}

		}
		return 0;
	}

}
