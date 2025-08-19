// (C) 2017 uchicom
package com.uchicom.csve.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * @author uchicom: Shigeki Uchiyama
 */
public class RandomAccessCSVReaderTest {

  /** */
  @Test
  public void getNextCsvLine() {
    try (var randomAccessFile = new RandomAccessFile("src/test/resources/test.csv", "r");
        var csvReader = new RandomAccessCSVReader(randomAccessFile, StandardCharsets.UTF_8); ) {

      String[] result = csvReader.getNextCsvLine(4, true);
      assertThat(result).hasSize(4);
      assertThat(result[0]).isEqualTo("a");
      assertThat(result[1]).isEqualTo("b");
      assertThat(result[2]).isEqualTo("c");
      assertThat(result[3]).isEqualTo("d");
      result = csvReader.getNextCsvLine(4, true);
      assertThat(result).hasSize(4);
      assertThat(result[0]).isEqualTo("1");
      assertThat(result[1]).isEqualTo("2");
      assertThat(result[2]).isEqualTo("3");
      assertThat(result[3]).isEqualTo("4");
      result = csvReader.getNextCsvLine(4, true);
      assertThat(result).hasSize(4);
      assertThat(result[0]).isEqualTo("e");
      assertThat(result[1]).isEqualTo("f");
      assertThat(result[2]).isEqualTo("g");
      assertThat(result[3]).isEqualTo("h");
      result = csvReader.getNextCsvLine(4, true);
      assertThat(result).hasSize(5);
      assertThat(result[0]).isEqualTo("あ");
      assertThat(result[1]).isEqualTo("い");
      assertThat(result[2]).isEqualTo("う");
      assertThat(result[3]).isEqualTo("え");
      assertThat(result[4]).isEqualTo("お");
      result = csvReader.getNextCsvLine(4, true);
      assertThat(result).isNull();
    } catch (Exception e) {
      fail(e);
    }
  }
}
