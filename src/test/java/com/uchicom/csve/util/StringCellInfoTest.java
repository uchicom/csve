// (C) 2022 uchicom
package com.uchicom.csve.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class StringCellInfoTest {

  @Test
  public void checkEscape() {
    StringCellInfo info = new StringCellInfo("test");
    assertThat(info.checkEscape(",")).isTrue();
    assertThat(info.checkEscape("\"")).isTrue();
    assertThat(info.checkEscape("\r")).isTrue();
    assertThat(info.checkEscape("\n")).isTrue();
    assertThat(info.checkEscape("t,st")).isTrue();
    assertThat(info.checkEscape("t\"st")).isTrue();
    assertThat(info.checkEscape("t\rst")).isTrue();
    assertThat(info.checkEscape("t\nst")).isTrue();
  }

  @Test
  public void checkEscape2() {
    StringCellInfo info = new StringCellInfo("test");
    assertThat(info.checkEscape2(",")).isTrue();
    assertThat(info.checkEscape2("\"")).isTrue();
    assertThat(info.checkEscape2("\r")).isTrue();
    assertThat(info.checkEscape2("\n")).isTrue();
    assertThat(info.checkEscape2("t,st")).isTrue();
    assertThat(info.checkEscape2("t\"st")).isTrue();
    assertThat(info.checkEscape2("t\rst")).isTrue();
    assertThat(info.checkEscape2("t\nst")).isTrue();
  }

  @Test
  public void checkExe() {
    StringCellInfo info = new StringCellInfo("test");
    assertThat(info.checkExe(",")).isTrue();
    assertThat(info.checkExe("\"")).isTrue();
    assertThat(info.checkExe("\r")).isTrue();
    assertThat(info.checkExe("\n")).isTrue();
    assertThat(info.checkExe("t,st")).isTrue();
    assertThat(info.checkExe("t\"st")).isTrue();
    assertThat(info.checkExe("t\rst")).isTrue();
    assertThat(info.checkExe("t\nst")).isTrue();
  }

  @Test
  public void getOutputLength() {
    assertThat(new StringCellInfo("test").getOutputLength()).isEqualTo(4);
    assertThat(new StringCellInfo("t,st").getOutputLength()).isEqualTo(4 + 2);
    assertThat(new StringCellInfo("t\"st").getOutputLength()).isEqualTo(4 + 2 + 1);
  }

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
