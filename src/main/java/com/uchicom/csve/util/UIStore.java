// (C) 2006 uchicom
package com.uchicom.csve.util;

import java.util.HashMap;
import java.util.Map;

/** @author uchiyama */
public class UIStore {

  /** Creates a new instance of UIStore */
  public UIStore() {}

  public Object getUI(Object key) {
    return map.get(key);
  }

  public void putUI(Object key, Object uiObject) {
    this.map.put(key, uiObject);
  }

  Map<Object, Object> map = new HashMap<>();
}
