// (c) 2005 uchicom
package com.uchicom.csve.util;

/**
 *
 * @author  uchiyama
 */
public interface CellInfo extends java.lang.Comparable {

    public Object getValue();
    public Object getBkValue();
    public void setValue(Object value);
    public boolean isModified();
    public boolean isUpdated();
    public boolean isInserted();
    public int UPDATED = 0x00002;
    public int INSERTED = 0x00004;
    public void addStatus(int addStatus);
    public void removeStatus(int removeStatus);
    /** 並び替えで使用する */
    public boolean isEmpty();
}
