// (C) 2006 uchicom
package com.uchicom.csve.util;

import java.io.IOException;
import java.util.Properties;
import javax.swing.Action;

/** @author uchiyama */
public class ActionUI {

  public static String UI_KEY = "ActionUI";

  /** */
  public ActionUI(Properties resource) throws IOException {
    this.resource = resource;
  }

  public String getName(String actionName) {
    System.out.println(actionName);
    if (resource.containsKey(actionName + ".NAME")) {
      return resource.getProperty(actionName + ".NAME");
    } else {
      return actionName.substring(actionName.lastIndexOf(".") + 1, actionName.length() - 6);
    }
  }

  public String getName(Action action) {
    return getName(action.getClass().getName());
  }

  Properties resource;
}
