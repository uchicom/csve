// (c) 2006 uchicom
package com.uchicom.csve.util;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.table.JTableHeader;

/**
 * @author uchiyama
 *
 */
public class SortMouseListener implements MouseListener {


	/*
	 * (非 Javadoc)
	 *
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {

		if (e.getClickCount() == 1 && e.getButton() == 1) {
		// クリック時に押しているボタンを取得する
		JTableHeader header = (JTableHeader) e.getSource();

		SearchTable table = (SearchTable) header.getTable();
		//コントロールかそうでないかで呼び出しを変える
		table.sortTable(header.getColumnModel().getColumnIndexAtX(e.getX()), ((e
				.getModifiersEx() & MouseEvent.CTRL_DOWN_MASK) == 128));
		}
		System.out.println(e.getButton());
		System.out.println(e.getModifiersEx());
		System.out.println(e.getX() + ":" + e.getY());
		System.out.println(e);
		System.out.println(e.getSource());
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	/*
	 * (非 Javadoc)
	 *
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	/*
	 * (非 Javadoc)
	 *
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	/*
	 * (非 Javadoc)
	 *
	 * @see
	 * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
