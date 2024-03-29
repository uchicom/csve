// (C) 2022 uchicom
package com.uchicom.csve.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CSVHelperTest {

  @Test
  public void write() {
    List<CSVRow> rowList = new ArrayList<>();
    rowList.add(
        new CSVRow(
            new StringCellInfo[] {
              new StringCellInfo("test"), // 4
              new StringCellInfo(","), // 3
              new StringCellInfo("\""), // 4
              new StringCellInfo("\r"), // 3
              new StringCellInfo("\n"), // 3
              new StringCellInfo("\r\n"), // 4
              new StringCellInfo("t,st"), // 6
              new StringCellInfo("t\"st"), // 7
              new StringCellInfo("t\rst"), // 6
              new StringCellInfo("t\nst"), // 6
            }));
    rowList.add(
        new CSVRow(
            new StringCellInfo[] {
              new StringCellInfo("test1"), // 4
              new StringCellInfo("2,"), // 3
              new StringCellInfo("3\""), // 4
              new StringCellInfo("4\r"), // 3
              new StringCellInfo("5\n"), // 3
              new StringCellInfo("\r\n"), // 4
              new StringCellInfo("6t,st"), // 6
              new StringCellInfo("7t\"st"), // 7
              new StringCellInfo("8t\rst"), // 6
              new StringCellInfo("9t\nst"), // 6
            }));
    CSVHelper helper = new CSVHelper();
    helper.write(rowList, new File("test.csv"));
    //		assertThat(4 + 3 + 4 + 3 + 3 + 4 + 6 + 7 + 6 + 6 + 8, row.getOutputLength());
  }

  @Test
  public void read() {
    CSVHelper helper = new CSVHelper();
    List<CSVRow> rowList = helper.read(new File("test.csv"));
    CSVRow row = rowList.get(0);
    assertThat(row.get(0).getValue()).isEqualTo("test");
    assertThat(row.get(1).getValue()).isEqualTo(",");
    assertThat(row.get(2).getValue()).isEqualTo("\"");
    assertThat(row.get(3).getValue()).isEqualTo("\r");
    assertThat(row.get(4).getValue()).isEqualTo("\n");
    assertThat(row.get(5).getValue()).isEqualTo("\r\n");
    assertThat(row.get(6).getValue()).isEqualTo("t,st");
    assertThat(row.get(7).getValue()).isEqualTo("t\"st");
    assertThat(row.get(9).getValue()).isEqualTo("t\nst");
  }

  @Test
  public void read2() {
    CSVHelper helper = new CSVHelper();
    List<CSVRow> rowList = new ArrayList<>();
    helper.read(
        new File("test.csv"),
        row -> {
          rowList.add(row);
        });
    CSVRow row = rowList.get(0);
    assertThat(row.get(0).getValue()).isEqualTo("test");
    assertThat(row.get(1).getValue()).isEqualTo(",");
    assertThat(row.get(2).getValue()).isEqualTo("\"");
    assertThat(row.get(3).getValue()).isEqualTo("\r");
    assertThat(row.get(4).getValue()).isEqualTo("\n");
    assertThat(row.get(5).getValue()).isEqualTo("\r\n");
    assertThat(row.get(6).getValue()).isEqualTo("t,st");
    assertThat(row.get(7).getValue()).isEqualTo("t\"st");
    assertThat(row.get(8).getValue()).isEqualTo("t\rst");
    assertThat(row.get(9).getValue()).isEqualTo("t\nst");
  }
}
