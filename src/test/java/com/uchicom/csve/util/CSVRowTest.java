// (C) 2022 uchicom
package com.uchicom.csve.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class CSVRowTest {

  @Test
  public void getOutputLength() {
    StringCellInfo[] cells =
        new StringCellInfo[] {
          new StringCellInfo("test"), // 4
          new StringCellInfo(","), // 3
          new StringCellInfo("\""), // 4
          new StringCellInfo("\r"), // 3
          new StringCellInfo("\n"), // 3
          new StringCellInfo("t,st"), // 6
          new StringCellInfo("t\"st"), // 7
          new StringCellInfo("t\rst"), // 6
          new StringCellInfo("t\nst"), // 6
        };
    CSVRow row = new CSVRow(cells);
    assertThat(row.getOutputLength()).isEqualTo(4 + 3 + 4 + 3 + 3 + 6 + 7 + 6 + 6 + 8);
  }
}
