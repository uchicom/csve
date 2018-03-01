// (c) 2006 uchicom
package com.uchicom.csve.util;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.table.JTableHeader;

/**
 * フィルターの値をクリアしてフィルターかけない方法を考えないと。。ｓ
 * @author uchiyama
 *
 */
public class FilterMouseListener implements MouseListener {

	/* (非 Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON3) {


			JTableHeader header = (JTableHeader) e.getSource();
			SearchTable table = (SearchTable) header.getTable();
			if (table.isMode(SearchTable.MODE_TYPE_FILTER)) {
				FilterIf filterColumn = (FilterIf)header.getColumnModel().getColumn(header.getColumnModel().getColumnIndexAtX(e.getX()));
				if ((e.getModifiersEx() & MouseEvent.CTRL_DOWN_MASK) == 128) {
					JOptionPane.showMessageDialog(null, "フィルター値をクリアしました");
					filterColumn.setFilter(null);
				} else {
					String target = JOptionPane.showInputDialog("絞り込む値を指定してください", filterColumn.getFilter());
					if (target != null) {
						filterColumn.setFilter(target);
						table.filter(filterColumn);
					}
				}
			}
		}

	}

	/* (非 Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	/* (非 Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	/* (非 Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	/* (非 Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
