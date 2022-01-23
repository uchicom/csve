// (C) 2022 uchicom
package com.uchicom.csve.util;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

public class StringCellInfoTest {

  @Test
  public void checkEscape() {
    StringCellInfo info = new StringCellInfo("test");
    assertEquals(true, info.checkEscape(","));
    assertEquals(true, info.checkEscape("\""));
    assertEquals(true, info.checkEscape("\r"));
    assertEquals(true, info.checkEscape("\n"));
    assertEquals(true, info.checkEscape("t,st"));
    assertEquals(true, info.checkEscape("t\"st"));
    assertEquals(true, info.checkEscape("t\rst"));
    assertEquals(true, info.checkEscape("t\nst"));
  }

  @Test
  public void checkEscape2() {
    StringCellInfo info = new StringCellInfo("test");
    assertEquals(true, info.checkEscape2(","));
    assertEquals(true, info.checkEscape2("\""));
    assertEquals(true, info.checkEscape2("\r"));
    assertEquals(true, info.checkEscape2("\n"));
    assertEquals(true, info.checkEscape2("t,st"));
    assertEquals(true, info.checkEscape2("t\"st"));
    assertEquals(true, info.checkEscape2("t\rst"));
    assertEquals(true, info.checkEscape2("t\nst"));
  }

  @Test
  public void checkExe() {
    StringCellInfo info = new StringCellInfo("test");
    assertEquals(true, info.checkExe(","));
    assertEquals(true, info.checkExe("\""));
    assertEquals(true, info.checkExe("\r"));
    assertEquals(true, info.checkExe("\n"));
    assertEquals(true, info.checkExe("t,st"));
    assertEquals(true, info.checkExe("t\"st"));
    assertEquals(true, info.checkExe("t\rst"));
    assertEquals(true, info.checkExe("t\nst"));
  }

  @Test
  public void getOutputLength() {
    assertEquals(4, new StringCellInfo("test").getOutputLength());
    assertEquals(4 + 2, new StringCellInfo("t,st").getOutputLength());
    assertEquals(4 + 2 + 1, new StringCellInfo("t\"st").getOutputLength());
  }

  @Ignore
  @Test
  public void test() {
    StringCellInfo info = new StringCellInfo("test");
    long start = System.currentTimeMillis();
    for (int i = 0; i < 1000_000; i++) {
      info.checkExe("aaaaaaaaaaaaaa,");
      info.checkExe("bbbbbbbbbbbbbb\"");
      info.checkExe("cccccccccccccc\r");
      info.checkExe("dddddddddddddd\n");
      info.checkExe("eeeeeeeeeeeeeet,st");
      info.checkExe("fffffffffffffft\"st");
      info.checkExe("ggggggggggggggggt\rst");
      info.checkExe("hhhhhhhhhhhhhhhht\nst");
    }
    long checkExe = System.currentTimeMillis() - start;
    start = System.currentTimeMillis();
    for (int i = 0; i < 1000_000; i++) {
      info.checkEscape("aaaaaaaaaaaaaa,");
      info.checkEscape("bbbbbbbbbbbbbb\"");
      info.checkEscape("cccccccccccccc\r");
      info.checkEscape("dddddddddddddd\n");
      info.checkEscape("eeeeeeeeeeeeeet,st");
      info.checkEscape("fffffffffffffft\"st");
      info.checkEscape("ggggggggggggggggt\rst");
      info.checkEscape("hhhhhhhhhhhhhhhht\nst");
    }
    long checkEscape = System.currentTimeMillis() - start;
    start = System.currentTimeMillis();
    for (int i = 0; i < 1000_000; i++) {
      info.checkEscape2("aaaaaaaaaaaaaa,");
      info.checkEscape2("bbbbbbbbbbbbbb\"");
      info.checkEscape2("cccccccccccccc\r");
      info.checkEscape2("dddddddddddddd\n");
      info.checkEscape2("eeeeeeeeeeeeeet,st");
      info.checkEscape2("fffffffffffffft\"st");
      info.checkEscape2("ggggggggggggggggt\rst");
      info.checkEscape2("hhhhhhhhhhhhhhhht\nst");
    }
    long checkEscape2 = System.currentTimeMillis() - start;
    System.out.println("checkEscape:" + checkEscape);
    System.out.println("checkEscape2:" + checkEscape2);
    System.out.println("checkExe:" + checkExe);
  }
}
