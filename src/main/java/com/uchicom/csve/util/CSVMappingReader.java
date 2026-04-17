// (C) 2026 uchicom
package com.uchicom.csve.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.function.Function;

/**
 * ヘッダ指定によるDTOマッピング機能を持つCSVリーダー.
 *
 * @author uchicom: Shigeki Uchiyama
 */
public class CSVMappingReader extends CSVReader {

  private int[] mappingIndex;
  private int headerCount;
  private String[] recordBuffer;

  public CSVMappingReader(String fileName, String enc) throws Exception {
    this(new File(fileName), enc);
  }

  public CSVMappingReader(File file, String enc) throws Exception {
    this(new FileInputStream(file), enc);
  }

  public CSVMappingReader(File file, Charset charset) throws FileNotFoundException {
    this(new FileInputStream(file), charset);
  }

  public CSVMappingReader(InputStream is, String enc) throws Exception {
    this(is, Charset.forName(enc));
  }

  public CSVMappingReader(InputStream is, Charset charset) {
    super(is, charset);
  }

  public CSVMappingReader(URL url, String enc) throws Exception {
    this(url, Charset.forName(enc));
  }

  public CSVMappingReader(URL url, Charset charset) throws Exception {
    this(url.openStream(), charset);
  }

  /**
   * 1行目をヘッダ行として読み込み、指定ヘッダ配列とCSVカラムのマッピングを構築する.
   *
   * <p>recordBufferは指定ヘッダ配列のサイズで1度だけ生成する.
   *
   * @param desiredHeaders 取得したいヘッダ名の配列
   * @throws IOException 入出力エラー発生時
   */
  public void analyzeHeader(String[] desiredHeaders) throws IOException {
    String[] csvHeaders = getNextCsvLine(desiredHeaders.length, true);
    headerCount = csvHeaders.length;
    mappingIndex = new int[desiredHeaders.length];
    recordBuffer = new String[desiredHeaders.length];

    for (int i = 0; i < desiredHeaders.length; i++) {
      mappingIndex[i] = -1;
      for (int j = 0; j < csvHeaders.length; j++) {
        if (desiredHeaders[i].equals(csvHeaders[j])) {
          mappingIndex[i] = j;
          break;
        }
      }
    }
  }

  /**
   * 次の行を読み込み、ヘッダマッピングに基づいて並び替えたString[]をmapperに渡してDTOを返却する.
   *
   * <p>recordBufferは使い回す.
   *
   * @param <T> 返却するDTOの型
   * @param mapper String[]を受け取りDTOを生成するFunction
   * @return DTOインスタンス。ファイル終端に達した場合はnull
   * @throws IOException 入出力エラー発生時
   */
  public <T> T getNext(Function<String[], T> mapper) throws IOException {
    String[] record = getNextCsvLine(headerCount, false);
    if (record == null) return null;
    for (int i = 0; i < mappingIndex.length; i++) {
      recordBuffer[i] = mappingIndex[i] >= 0 ? record[mappingIndex[i]] : null;
    }
    return mapper.apply(recordBuffer);
  }
}
