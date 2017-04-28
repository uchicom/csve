// (c) 2005 uchicom
package com.uchicom.csve.util;


/**
 *
 * @author  uchiyama
 */
public class StringCellInfo extends AbstractCellInfo {

	private int lnCount;
    /** Creates a new instance of CellInfo */
    public StringCellInfo(String value, boolean bNumber) {
        this.value = value;
        super.bkValue = value;
        this.bNumber = bNumber;
    }
    public StringCellInfo(String value) {
        this.value = value;
        this.bkValue = value;
    }
    public StringCellInfo(String value, int lnCount) {
        this.value = value;
        this.bkValue = value;
        this.lnCount = lnCount;
    }
    public int getLnCount() {
    	return lnCount;
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

    public void setValue(Object value) {
        this.value = (String)value;
    }
    public Object getValue() {
        return value;
    }

    public String toString() {
        if (value == null) return "";
        return value.toString();
    }

    public int compareTo(Object obj) {
        StringCellInfo cell = (StringCellInfo)obj;
        if (cell == null) return -1;
        if (value == null) {
            if (cell.value == null) {
                return 0;
            } else {
                return 1;
            }
        } else if (cell.value == null) {
            return -1;
        } else {
            if (bNumber) {
                //??????
                if(value.length() > cell.value.length()) {
                    return 1;
                } else if (value.length() == cell.value.length()) {
                    return value.compareTo(cell.value);
                } else {
                    return -1;
                }
            } else {
                //??????
                return value.compareTo(cell.value);
            }
        }
    }

    public boolean isEmpty() {
    	return value == null || "".equals(value);
    }

    private boolean bNumber = false;
    private String value = null;
}
