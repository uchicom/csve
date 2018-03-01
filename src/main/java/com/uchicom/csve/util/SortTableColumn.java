// (c) 2006 uchicom
package com.uchicom.csve.util;

import java.util.List;

import javax.swing.table.TableColumn;

/**
 * @author uchiyama
 *
 */
public class SortTableColumn extends TableColumn implements FilterIf{

	String filterValue = null;

	boolean isSort = false;
	int sortType = 0;
	public static int SORT_TYPE_ASC = 1;
	public static int SORT_TYPE_DESC = -1;
	private boolean hold = false;
	public void setSortEnd() {
		this.isSort = false;
	}

	public int getSortType() {
		return sortType;
	}
	public int getSorted() {
		if (!isSort) {
			//未ソートの場合は0
			return 0;
		} else if (sortType > 0) {
			//ソート済みで昇順なら1
			return 1;
		} else {
			//ソート済みで降順なら
			return 2;
		}
	}


	public void setSort(boolean bKeep) {
		if (isSort) {
			sortType = sortType * -1;
//			if (sortType == SORT_TYPE_ASC) {
//				setHeaderValue(getIdentifier() + "▲");
//			} else {
//				setHeaderValue(getIdentifier() + "▼");
//			}
		} else {
			sortType = SORT_TYPE_ASC;
//			setHeaderValue(getIdentifier() + "▲");
			isSort = true;

		}
	}

	public void setHold(boolean hold) {
		this.hold = hold;
		if (!hold) {
			sortList.remove(this);
		}
	}

	public boolean isHold() {
		return hold;
	}

	public Object getHeaderValue() {
		String obj = super.getHeaderValue().toString();
		if (isSort) {
		if (hold) {
			if (sortType == SORT_TYPE_ASC) {
				obj = obj + "△" + sortList.indexOf(this);
			} else if (sortType == SORT_TYPE_DESC){
				obj = obj + "▽" + sortList.indexOf(this);
			}
		} else {
			if (sortType == SORT_TYPE_ASC) {
				obj = obj + "▲";
			} else if (sortType == SORT_TYPE_DESC){
				obj = obj + "▼";
			}
		}
		} else {
			return super.getHeaderValue();
		}
		return obj;
	}

	public void setSortIndex(List<SortTableColumn> sortList) {
		this.sortList = sortList;
	}

	List<SortTableColumn> sortList = null;
	/* (非 Javadoc)
	 * @see cymsgk.util.FilterIf#setFilter(java.lang.String)
	 */
	public void setFilter(String filterValue) {
		if (filterValue != null) {
			//ここでフィルター用のレンダラーを設定するか、レンダラーの中の設定でフィルター値を設定するかだな。
			//setHeaderRenderer(headerRenderer)
		}
		this.filterValue = filterValue;

	}

	public String getFilter() {
		return this.filterValue;
	}


}
