// (C) 2006 uchicom
package com.uchicom.csve.util;

import javax.swing.AbstractAction;

/**
 * @author uchiyama
 */
public abstract class UIAbstractAction extends AbstractAction {

  /** */
  private static final long serialVersionUID = 1L;

  /** Creates a new instance of UIAbstractAction */
  //    public UIAbstractAction(UIStore uiStore) {
  //    	System.out.println(getClass().toString());
  //    	this.uiStore = uiStore;
  //        ActionUI actionUI = (ActionUI)uiStore.getUI(ActionUI.UI_KEY);
  //        putValue(NAME, actionUI.getName(getClass().toString()));
  //
  //    }

  public void installUI(UIStore uiStore) {
    if (uiStore == null) {
      throw new IllegalArgumentException("Invalid value: uiStoer is null.");
    }
    this.uiStore = uiStore;
    ActionUI actionUI = (ActionUI) uiStore.getUI(ActionUI.UI_KEY);
    putValue(NAME, actionUI.getName(this));
  }

  protected UIStore uiStore = null;
}
