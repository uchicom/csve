// (C) 2015 uchicom
package com.uchicom.csve.util;

import java.awt.Component;
import java.util.EventObject;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;

/**
 * @author uchicom: Shigeki Uchiyama
 */
public class TextAreaCellEditor implements TableCellEditor {

  private JTextArea textArea = new JTextArea();
  private CellEditorListener listener;

  /** */
  public TextAreaCellEditor() {
    // TODO 自動生成されたコンストラクター・スタブ
  }

  /* (非 Javadoc)
   * @see javax.swing.CellEditor#getCellEditorValue()
   */
  @Override
  public Object getCellEditorValue() {
    return textArea.getText();
  }

  /* (非 Javadoc)
   * @see javax.swing.CellEditor#isCellEditable(java.util.EventObject)
   */
  @Override
  public boolean isCellEditable(EventObject anEvent) {
    return true;
  }

  /* (非 Javadoc)
   * @see javax.swing.CellEditor#shouldSelectCell(java.util.EventObject)
   */
  @Override
  public boolean shouldSelectCell(EventObject anEvent) {
    return true;
  }

  /* (非 Javadoc)
   * @see javax.swing.CellEditor#stopCellEditing()
   */
  @Override
  public boolean stopCellEditing() {
    listener.editingStopped(new ChangeEvent(this));
    return true;
  }

  /* (非 Javadoc)
   * @see javax.swing.CellEditor#cancelCellEditing()
   */
  @Override
  public void cancelCellEditing() {
    listener.editingStopped(new ChangeEvent(this));
  }

  /* (非 Javadoc)
   * @see javax.swing.CellEditor#addCellEditorListener(javax.swing.event.CellEditorListener)
   */
  @Override
  public void addCellEditorListener(CellEditorListener l) {
    this.listener = l;
  }

  /* (非 Javadoc)
   * @see javax.swing.CellEditor#removeCellEditorListener(javax.swing.event.CellEditorListener)
   */
  @Override
  public void removeCellEditorListener(CellEditorListener l) {
    listener = null;
  }

  /* (非 Javadoc)
   * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
   */
  @Override
  public Component getTableCellEditorComponent(
      JTable table, Object value, boolean isSelected, int row, int column) {
    textArea.setText(((StringCellInfo) value).toString());
    return textArea;
  }
}
