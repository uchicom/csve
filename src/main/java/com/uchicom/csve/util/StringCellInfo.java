// (C) 2005 uchicom
package com.uchicom.csve.util;

import java.util.regex.Pattern;

/** @author uchiyama */
public class StringCellInfo extends AbstractCellInfo {

  private int lnCount;
  /** Creates a new instance of CellInfo */
  public StringCellInfo(String value, boolean bNumber) {
    setValue(value);
    super.bkValue = value;
    this.bNumber = bNumber;
  }

  public StringCellInfo(String value) {
    setValue(value);
    this.bkValue = value;
  }

  public StringCellInfo(String value, int lnCount) {
    setValue(value);
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
    this.value = (String) value;
    mustEscape = checkExe(this.value);
    countByEnclosingCharacter = 0;
    if (mustEscape) {
      // 囲むべき文字がある場合は調査する
      for (char c : this.value.toCharArray()) {
        if (c == '\"') {
          countByEnclosingCharacter++;
        }
      }
    }
  }

  public Object getValue() {
    return value;
  }

  public String toString() {
    if (value == null) return "";
    return value.toString();
  }

  public int compareTo(CellInfo obj) {
    StringCellInfo cell = (StringCellInfo) obj;
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
        // ??????
        if (value.length() > cell.value.length()) {
          return 1;
        } else if (value.length() == cell.value.length()) {
          return value.compareTo(cell.value);
        } else {
          return -1;
        }
      } else {
        // ??????
        return value.compareTo(cell.value);
      }
    }
  }

  public boolean isEmpty() {
    return value == null || "".equals(value);
  }

  public boolean mustEscape() {
    return mustEscape;
  }

  int match = ~('\r' | '\n' | '"' | ',');

  protected boolean checkExe(String value) {
    if (value == null) return false;
    for (char c : value.toCharArray()) {
      if ((c & match) == 0) {
        if (c == '\r' || c == '\n' || c == '"' || c == ',') {
          return true;
        }
      }
    }
    return false;
  }

  Pattern pat = Pattern.compile(".*[\\,\\r\\n\"].*");
  /** カンマ、改行、ダブルクォートを含む場合はtrue */
  protected boolean checkEscape(String value) {
    if (value == null) return false;
    return value.matches(".*[\\,\\r\\n\"].*");
  }

  protected boolean checkEscape2(String value) {
    if (value == null) return false;
    return pat.matcher(value).find();
  }

  protected int getCountByEnclosingCharacter() {
    return countByEnclosingCharacter;
  }

  /**
   * 文字数が割るのはダブルクォートが存在した時だけ。
   *
   * @return
   */
  public String getOutput() {
    return mustEscape ? "\"" + value + "\"" : value;
  }

  /**
   * 囲み文字やエスケープした結果の長さ。
   *
   * @return
   */
  public int getOutputLength() {
    int length = 0;
    if (value == null) return 0;
    length += value.length();
    if (mustEscape) {
      length += 2;
      length += countByEnclosingCharacter;
    }
    return length;
  }

  public void write(StringBuilder builder) {
    if (mustEscape) {
      builder.append("\"");
      if (countByEnclosingCharacter > 0) {
        builder.append(value.replaceAll("\"", "\"\""));
      } else {
        builder.append(value);
      }
      builder.append("\"");
    } else {
      builder.append(value);
    }
  }

  private boolean bNumber = false;
  private String value = null;
  private boolean mustEscape;
  private int countByEnclosingCharacter;
}
