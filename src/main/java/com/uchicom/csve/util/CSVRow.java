package com.uchicom.csve.util;

/**
 * beanをCSVRowに変換する処理を追加する。
 * @author shigeki
 *
 */
public class CSVRow {
	private final CellInfo[] cells;
	public CSVRow(CellInfo[] cells) {
		this.cells = cells;
	}

	public int getOutputLength() {
		int length = 0;
		if (cells.length > 1) {
			length += cells.length - 1;
		}
		for (CellInfo cell : cells) {
			length += cell.getOutputLength();
		}
		return length;
	}

	public void write(StringBuilder builder) {
		for (int i = 0; i < cells.length; i++) {
			if (i > 0) {
				builder.append(",");
			}
			cells[i].write(builder);
		}
	}

	public CellInfo get(int i) {
		return cells[i];
	}
}
