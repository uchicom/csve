// (C) 2012 uchicom
package com.uchicom.csve;

public class TestException extends RuntimeException {

  public TestException(int exitStatus) {
    this.exitStatus = exitStatus;
  }

  private int exitStatus;

  public int getExitStatus() {
    return exitStatus;
  }
}
