// (C) 2012 uchicom
package com.uchicom.csve;

import org.junit.Test;

public class MainTest extends TestBase {

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  @Test
  public void testMainNormal01() {
    try {
      Main.main(new String[] {"1", "2"});
    } catch (TestException e) {
      assertEquals(1, e.getExitStatus());
    } catch (Throwable e) {
      fail();
    }
  }
}
