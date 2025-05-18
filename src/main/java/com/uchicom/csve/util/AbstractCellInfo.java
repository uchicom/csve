// (C) 2005 uchicom
package com.uchicom.csve.util;

/**
 * @author uchiyama
 */
public abstract class AbstractCellInfo implements CellInfo {

  /** Creates a new instance of AbstractCellInfo */
  public AbstractCellInfo() {}

  public void setValue(Object value) {
    this.value = value;
  }

  public Object getValue() {
    return value;
  }

  public Object getBkValue() {
    return bkValue;
  }

  public boolean isModified() {
    if (bkValue != null) {
      return !bkValue.equals(value);
    } else if (value != null) {
      return true;
    } else {
      return false;
    }
  }

  public int hashCode() {
    if (value == null) return 0;
    return value.hashCode();
  }

  protected Object value = null;
  protected Object bkValue = null;

  public boolean isUpdated() {
    return (status & UPDATED) == UPDATED;
  }

  public boolean isInserted() {
    return (status & INSERTED) == INSERTED;
  }

  /**
   * ステータスから指定した引数を消します。
   *
   * @param addStatus
   */
  public void addStatus(int addStatus) {
    status = status | addStatus;
  }

  /**
   * ステータスから指定した引数を消します。
   *
   * @param removeStatus
   */
  public void removeStatus(int removeStatus) {
    status = status & (~removeStatus);
  }

  /** セルの状態を表す */
  protected int status = 0;
}
