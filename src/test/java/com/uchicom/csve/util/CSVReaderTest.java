// (C) 2017 uchicom
package com.uchicom.csve.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * @author uchicom: Shigeki Uchiyama
 */
public class CSVReaderTest {

  /** */
  @Test
  public void getNextCsvLine1() {
    String string = "1,2,3,4";
    ByteArrayInputStream bais = new ByteArrayInputStream(string.getBytes());
    try (CSVReader reader = new CSVReader(bais, StandardCharsets.UTF_8); ) {

      String[] result = reader.getNextCsvLine(4, true);
      assertThat(result).hasSize(4);
      assertThat(result[0]).isEqualTo("1");
      assertThat(result[1]).isEqualTo("2");
      assertThat(result[2]).isEqualTo("3");
      assertThat(result[3]).isEqualTo("4");
      assertThat(reader.getRecordFromIndex()).isEqualTo(0);
      assertThat(reader.getRecordToIndex()).isEqualTo(7);
      assertThat(reader.getRecordLength()).isEqualTo(7);
      assertThat(reader.getNextCsvLine(4, true)).isNull();
    } catch (Exception e) {
      fail(e);
    }
  }

  @Test
  public void getNextCsvLine2() {
    String string = "あ,い,う,え";
    ByteArrayInputStream bais = new ByteArrayInputStream(string.getBytes());
    try (CSVReader reader = new CSVReader(bais, StandardCharsets.UTF_8); ) {

      String[] result = reader.getNextCsvLine(4, true);
      assertThat(result).hasSize(4);
      assertThat(result[0]).isEqualTo("あ");
      assertThat(result[1]).isEqualTo("い");
      assertThat(result[2]).isEqualTo("う");
      assertThat(result[3]).isEqualTo("え");
      assertThat(reader.getRecordFromIndex()).isEqualTo(0);
      assertThat(reader.getRecordToIndex()).isEqualTo(7);
      assertThat(reader.getRecordLength()).isEqualTo(7);
      assertThat(reader.getNextCsvLine(4, true)).isNull();
      ;
    } catch (Exception e) {
      fail(e);
    }
  }

  @Test
  public void getNextCsvLine3() {
    String string = "あ,い,う,え\n1,2,3,4";
    ByteArrayInputStream bais = new ByteArrayInputStream(string.getBytes());
    try (CSVReader reader = new CSVReader(bais, StandardCharsets.UTF_8); ) {

      String[] result = reader.getNextCsvLine(4, true);
      assertThat(result).hasSize(4);
      assertThat(result[0]).isEqualTo("あ");
      assertThat(result[1]).isEqualTo("い");
      assertThat(result[2]).isEqualTo("う");
      assertThat(result[3]).isEqualTo("え");
      assertThat(reader.getRecordFromIndex()).isEqualTo(0);
      assertThat(reader.getRecordToIndex()).isEqualTo(8);
      assertThat(reader.getRecordLength()).isEqualTo(8);
      result = reader.getNextCsvLine(4, true);
      assertThat(result).hasSize(4);
      assertThat(result[0]).isEqualTo("1");
      assertThat(result[1]).isEqualTo("2");
      assertThat(result[2]).isEqualTo("3");
      assertThat(result[3]).isEqualTo("4");
      assertThat(reader.getRecordFromIndex()).isEqualTo(8);
      assertThat(reader.getRecordToIndex()).isEqualTo(15);
      assertThat(reader.getRecordLength()).isEqualTo(7);
      assertThat(reader.getNextCsvLine(4, true)).isNull();
    } catch (Exception e) {
      fail(e);
    }
  }

  @Test
  public void getNextCsvLine4() {
    String string = ",,,";
    ByteArrayInputStream bais = new ByteArrayInputStream(string.getBytes());
    try (CSVReader reader = new CSVReader(bais, StandardCharsets.UTF_8); ) {

      String[] result = reader.getNextCsvLine(4, true);
      assertThat(result).hasSize(4);
      assertThat(result[0]).isEqualTo("");
      assertThat(result[1]).isEqualTo("");
      assertThat(result[2]).isEqualTo("");
      assertThat(result[3]).isEqualTo("");
      assertThat(reader.getRecordFromIndex()).isEqualTo(0);
      assertThat(reader.getRecordToIndex()).isEqualTo(3);
      assertThat(reader.getRecordLength()).isEqualTo(3);
      assertThat(reader.getNextCsvLine(4, true)).isNull();
    } catch (Exception e) {
      fail(e);
    }
  }

  @Test
  public void getNextCsvLine5() {
    String string = "\"a\",\",\",\"\n\",a\"\\\\\"b";
    ByteArrayInputStream bais = new ByteArrayInputStream(string.getBytes());
    try (CSVReader reader = new CSVReader(bais, StandardCharsets.UTF_8); ) {

      String[] result = reader.getNextCsvLine(4, true);
      assertThat(result).hasSize(4);
      assertThat(result[0]).isEqualTo("a");
      assertThat(result[1]).isEqualTo(",");
      assertThat(result[2]).isEqualTo("\n");
      assertThat(result[3]).isEqualTo("a\"\\\\\"b");
      assertThat(reader.getRecordFromIndex()).isEqualTo(0);
      assertThat(reader.getRecordToIndex()).isEqualTo(18);
      assertThat(reader.getRecordLength()).isEqualTo(18);
      assertThat(reader.getNextCsvLine(4, true)).isNull();
    } catch (Exception e) {
      fail(e);
    }
  }

  @Test
  public void getNextCsvLine6() {
    String string = "\"a\",\",\",\"\n\",a\"\\\\\"b\n";
    ByteArrayInputStream bais = new ByteArrayInputStream(string.getBytes());
    try (CSVReader reader = new CSVReader(bais, StandardCharsets.UTF_8); ) {

      String[] result = reader.getNextCsvLine(4, true);
      assertThat(result).hasSize(4);
      assertThat(result[0]).isEqualTo("a");
      assertThat(result[1]).isEqualTo(",");
      assertThat(result[2]).isEqualTo("\n");
      assertThat(result[3]).isEqualTo("a\"\\\\\"b");
      assertThat(reader.getRecordFromIndex()).isEqualTo(0);
      assertThat(reader.getRecordToIndex()).isEqualTo(19);
      assertThat(reader.getRecordLength()).isEqualTo(19);
      assertThat(reader.getNextCsvLine(4, true)).isNull();
    } catch (Exception e) {
      fail(e);
    }
  }

  @Test
  public void getNextCsvLine7() {
    System.out.println("getNextCsvLine7");
    String string = "\"a\",\",\",\"\r\n\",a\"\\\\\"b\r\n";
    ByteArrayInputStream bais = new ByteArrayInputStream(string.getBytes());
    try (CSVReader reader = new CSVReader(bais, StandardCharsets.UTF_8); ) {

      String[] result = reader.getNextCsvLine(4, true);
      assertThat(result).hasSize(4);
      assertThat(result[0]).isEqualTo("a");
      assertThat(result[1]).isEqualTo(",");
      assertThat(result[2]).isEqualTo("\r\n");
      assertThat(result[3]).isEqualTo("a\"\\\\\"b");
      assertThat(reader.getRecordFromIndex()).isEqualTo(0);
      assertThat(reader.getRecordToIndex()).isEqualTo(21);
      assertThat(reader.getRecordLength()).isEqualTo(21);
      assertThat(reader.getNextCsvLine(4, true)).isNull();
    } catch (Exception e) {
      fail(e);
    }
  }

  @Test
  public void escape() {
    assertThat(new String(CSVReader.escape("test".toCharArray(), '"', 0))).isEqualTo("test");
    assertThat(new String(CSVReader.escape("\"\"".toCharArray(), '"', 1))).isEqualTo("\"");
    assertThat(new String(CSVReader.escape("t\"\"st".toCharArray(), '"', 1))).isEqualTo("t\"st");
    assertThat(new String(CSVReader.escape("\"\"\"\"".toCharArray(), '"', 2))).isEqualTo("\"\"");
  }
}
