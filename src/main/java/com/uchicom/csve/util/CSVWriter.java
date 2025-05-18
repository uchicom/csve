// (C) 2006 uchicom
package com.uchicom.csve.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * @author uchiyama
 */
public class CSVWriter {

  /** Creates a new instance of CSVWriter */
  public CSVWriter(String fileName, String enc) {}

  public CSVWriter(File file, String enc) throws IOException {
    if (!file.exists()) {
      file.createNewFile();
    }
    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), enc));
  }

  public void write(List<CellInfo[]> csvList) throws IOException {
    int iMaxList = csvList.size();
    for (int iList = 0; iList < iMaxList; iList++) {
      CellInfo[] strings = (CellInfo[]) csvList.get(iList);
      int iMaxArray = strings.length;
      for (int iArray = 0; iArray < iMaxArray; iArray++) {
        if (iArray != 0) {
          writer.write(",");
        }
        writer.write(strings[iArray].toString());
      }
      writer.newLine();
      writer.flush();
    }
  }

  public void close() throws IOException {
    if (writer != null) {
      writer.flush();
      writer.close();
    }
  }

  public BufferedWriter writer = null;
}
