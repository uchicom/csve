// (C) 2022 uchicom
package com.uchicom.csve.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CSVHelper {
  private Charset charset = StandardCharsets.UTF_8;

  public CSVHelper() {}

  public List<CSVRow> read(File file) {
    try (CSVReader reader = new CSVReader(file, StandardCharsets.UTF_8)) {
      // CSVファイルを格納するリストを作成
      CellInfo[] lines = reader.getNextCsvLineCellInfo();

      // 一行ずつCSVを取得する
      int columnCount = 0;
      List<CSVRow> csvList = new ArrayList<CSVRow>();
      while (lines != null) {

        // ファイルのオープンでエラーが発生している。
        if (columnCount < lines.length) {
          columnCount = lines.length;
        }
        csvList.add(new CSVRow(lines));

        lines = reader.getNextCsvLineCellInfo();
      }
      return csvList;
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  public void read(File file, Consumer<CSVRow> consumer) {
    try (CSVReader reader = new CSVReader(file, StandardCharsets.UTF_8)) {
      // CSVファイルを格納するリストを作成
      CellInfo[] lines = reader.getNextCsvLineCellInfo();

      // 一行ずつCSVを取得する
      int columnCount = 0;
      while (lines != null) {

        // ファイルのオープンでエラーが発生している。
        if (columnCount < lines.length) {
          columnCount = lines.length;
        }

        consumer.accept(new CSVRow(lines));
        lines = reader.getNextCsvLineCellInfo();
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void write(List<CSVRow> rowList, File file) {
    try (BufferedWriter writer =
        new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset))) {
      StringBuilder builder = new StringBuilder(2024);
      for (CSVRow row : rowList) {
        builder.setLength(0);
        row.write(builder);
        writer.write(builder.toString());
        writer.newLine();
        writer.flush();
      }
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
