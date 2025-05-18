// (C) 2006 uchicom
package com.uchicom.csve.action.edit;

import com.uchicom.csve.util.UIAbstractAction;
import java.awt.event.ActionEvent;

/**
 * @author uchiyama
 */
public class UndoAction extends UIAbstractAction {

  /** */
  private static final long serialVersionUID = 1L;

  public UndoAction() {
    setEnabled(false);
  }

  public void actionPerformed(ActionEvent actionEvent) {}
}
