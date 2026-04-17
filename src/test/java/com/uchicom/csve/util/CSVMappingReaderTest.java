// (C) 2026 uchicom
package com.uchicom.csve.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * @author uchicom: Shigeki Uchiyama
 */
public class CSVMappingReaderTest {

  static class PersonDto {
    final String name;
    final String age;
    final String city;

    PersonDto(String name, String age, String city) {
      this.name = name;
      this.age = age;
      this.city = city;
    }
  }

  /** ヘッダ順と同じ順序でDTOにマッピングされること. */
  @Test
  public void getNext_mapsHeadersToDto() {
    String csv = "name,age,city\nAlice,30,Tokyo\nBob,25,Osaka";
    ByteArrayInputStream bais = new ByteArrayInputStream(csv.getBytes(StandardCharsets.UTF_8));
    try (CSVMappingReader reader = new CSVMappingReader(bais, StandardCharsets.UTF_8)) {
      reader.analyzeHeader(new String[] {"name", "age", "city"});

      PersonDto first = reader.getNext(r -> new PersonDto(r[0], r[1], r[2]));
      assertThat(first).isNotNull();
      assertThat(first.name).isEqualTo("Alice");
      assertThat(first.age).isEqualTo("30");
      assertThat(first.city).isEqualTo("Tokyo");

      PersonDto second = reader.getNext(r -> new PersonDto(r[0], r[1], r[2]));
      assertThat(second).isNotNull();
      assertThat(second.name).isEqualTo("Bob");
      assertThat(second.age).isEqualTo("25");
      assertThat(second.city).isEqualTo("Osaka");

      assertThat(reader.<PersonDto>getNext(r -> new PersonDto(r[0], r[1], r[2]))).isNull();
    } catch (Exception e) {
      fail(e);
    }
  }

  /** ヘッダの列順がDTOフィールドの定義順と異なっても正しくマッピングされること. */
  @Test
  public void getNext_mapsOutOfOrderHeaders() {
    String csv = "city,name,age\nKyoto,Carol,22";
    ByteArrayInputStream bais = new ByteArrayInputStream(csv.getBytes(StandardCharsets.UTF_8));
    try (CSVMappingReader reader = new CSVMappingReader(bais, StandardCharsets.UTF_8)) {
      reader.analyzeHeader(new String[] {"name", "age", "city"});

      PersonDto dto = reader.getNext(r -> new PersonDto(r[0], r[1], r[2]));
      assertThat(dto).isNotNull();
      assertThat(dto.name).isEqualTo("Carol");
      assertThat(dto.age).isEqualTo("22");
      assertThat(dto.city).isEqualTo("Kyoto");
    } catch (Exception e) {
      fail(e);
    }
  }
}
