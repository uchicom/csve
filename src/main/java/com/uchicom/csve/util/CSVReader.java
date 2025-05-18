// (C) 2006 uchicom
package com.uchicom.csve.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author UCHIYAMA
 */
public class CSVReader implements Closeable {

  private CharHelper charHelper = new CharHelper();
  private BufferedReader bis;
  private int readFileLength;
  private int fileIndex;
  boolean crFlg;
  private char[] chars = new char[1024 * 4 * 1024];
  private List<char[]> charsList = new ArrayList<>();
  boolean escapeOnFlg = false;
  boolean escapeOffFlg = false;
  int escapeCount = 0;
  char[] lastChars;
  int lnCount = 0;
  int maxLnCount = 0;
  String enc;

  StringBuffer columnBuff = new StringBuffer(128);
  private static final int IS_EOF = -1;

  /**
   * Creates a new instance of CVSReader
   *
   * @throws FileNotFoundException
   */
  public CSVReader(String fileName, String enc) throws Exception {
    this(new File(fileName), enc);
  }

  public CSVReader(File file, String enc) throws Exception {
    this(new FileInputStream(file), enc);
  }

  public CSVReader(File file, Charset charset) throws FileNotFoundException {
    this(new FileInputStream(file), charset);
  }

  public CSVReader(InputStream is, String enc) throws Exception {
    this(is, Charset.forName(enc));
  }

  public CSVReader(InputStream is, Charset charset) {
    bis = new BufferedReader(new InputStreamReader(is, charset));
  }

  /**
   * @param url
   * @param enc
   * @throws IOException
   */
  public CSVReader(URL url, String enc) throws Exception {
    this(url.openStream(), enc);
  }

  public class CharHelper {
    public int length;
    public int index;
    public char[] chars = new char[1024 * 4 * 1024];

    public CharHelper() {}

    boolean needReload() {
      return index >= length;
    }
  }

  private CharHelper getChars() throws IOException {

    if (charHelper.needReload()) {
      charHelper.length = bis.read(chars);
      charHelper.index = 0;
    }

    return charHelper;
  }

  /**
   * "は囲み文字化かエスケープ"
   *
   * @return
   */
  public CellInfo[] getNextLine() {
    CellInfo[] cells = null;

    // エスケープしたら、escaping();
    // エスケープしていない場合は、normal();
    return cells;
  }

  public CellInfo[] getNextCsvLineCellInfo() {
    CellInfo[] cells = null;
    // System.out.println("getNextCsvLineCellInfo");
    //
    try {
      FOR1:
      if (fileIndex < readFileLength) {
        // System.out.println("for");
        for (int iByte = fileIndex; iByte < readFileLength; iByte++) {
          //					System.out.println("............." + chars[iByte]);
          if (escapeOnFlg && !escapeOffFlg) {
            switch (chars[iByte]) {
              case '"':
                //							System.out.println("a");
                if (readFileLength > iByte + 1 && '"' == chars[iByte + 1]) {
                  escapeCount++;
                  iByte++;
                } else {
                  //								System.out.println(",,,,,,,,,,,,,");
                  escapeOffFlg = true;
                }
                break;
              case '\n':
                lnCount++;
                break;
            }
          } else {
            switch (chars[iByte]) {
              case '"':
                escapeOnFlg = true;
                fileIndex++;
                break;
              case ',':
                if (escapeOffFlg) {
                  escapeOnFlg = false;
                  escapeOffFlg = false;
                  if (maxLnCount < lnCount) {
                    maxLnCount = lnCount;
                  }
                  lnCount = 0;
                  if (escapeCount > 0) {
                    charsList.add(
                        escape(Arrays.copyOfRange(chars, fileIndex, iByte - 1), '"', escapeCount));
                    escapeCount = 0;
                  } else {
                    charsList.add(Arrays.copyOfRange(chars, fileIndex, iByte - 1));
                  }
                  fileIndex = iByte + 1;
                } else if (!escapeOnFlg) {
                  if (lastChars != null) {
                    StringBuffer strBuff = new StringBuffer(lastChars.length + iByte - fileIndex);
                    strBuff.append(lastChars);
                    if (iByte != 0) {
                      strBuff.append(chars, fileIndex, iByte);
                    }
                    charsList.add(strBuff.toString().toCharArray());
                    lastChars = null;
                  } else {
                    charsList.add(Arrays.copyOfRange(chars, fileIndex, iByte));
                  }
                  fileIndex = iByte + 1;
                }
                break;
              case '\r':
                crFlg = true;
                break;
              case '\n':

                //							System.out.println("ここだよね" + escapeCount);
                if (escapeOffFlg) {
                  escapeOnFlg = false;
                  escapeOffFlg = false;
                  if (maxLnCount < lnCount) {
                    maxLnCount = lnCount;
                  }
                  if (escapeCount > 0) {
                    charsList.add(
                        escape(
                            Arrays.copyOfRange(chars, fileIndex, iByte - (crFlg ? 2 : 1)),
                            '"',
                            escapeCount));
                    escapeCount = 0;
                  } else {
                    charsList.add(Arrays.copyOfRange(chars, fileIndex, iByte - (crFlg ? 2 : 1)));
                  }
                  crFlg = false;
                  fileIndex = iByte + 1;
                } else if (!escapeOnFlg) {
                  charsList.add(Arrays.copyOfRange(chars, fileIndex, iByte));
                  fileIndex = iByte + 1;
                } else {
                  lnCount++;
                }
                break FOR1;
            }
          }
        }
        if (fileIndex < readFileLength) {
          // System.out.println("a");
          lastChars = Arrays.copyOfRange(chars, fileIndex, readFileLength);
          fileIndex = 0;
          readFileLength = 0;
          if (maxLnCount < lnCount) {
            maxLnCount = lnCount;
          }
        }
      } else {
        // System.out.println("while");
        READ:
        while ((readFileLength = bis.read(chars)) > 0) {
          fileIndex = 0;
          // System.out.println("index:" + index + " length:" +
          // length);
          if (lastChars != null) {
            // System.out.println("lastChars:" + String.valueOf(
            // lastChars));
          }
          // System.out.println(new String(chars, 0, length));
          for (int iByte = fileIndex; iByte < readFileLength; iByte++) {
            if (escapeOnFlg && !escapeOffFlg) {
              //							System.out.println("if");
              switch (chars[iByte]) {
                case '"':
                  //								System.out.println("b");
                  if (readFileLength > iByte + 1 && '"' == chars[iByte + 1]) {
                    iByte++;
                    escapeCount++;
                    //									System.out.println("c");
                  } else {
                    //									System.out.println("d");
                    escapeOffFlg = true;
                  }
                  break;
                case '\n':
                  lnCount++;
                  break;
              }
            } else {
              //							System.out.println("else");
              switch (chars[iByte]) {
                case '"':
                  //								System.out.println("\"");
                  escapeOnFlg = true;
                  fileIndex++;
                  break;
                case ',':
                  //								System.out.println(",");
                  if (escapeOffFlg) {
                    escapeOnFlg = false;
                    escapeOffFlg = false;
                    if (maxLnCount < lnCount) {
                      maxLnCount = lnCount;
                    }
                    lnCount = 0;
                    if (escapeCount > 0) {
                      charsList.add(
                          escape(
                              Arrays.copyOfRange(chars, fileIndex, iByte - 1), '"', escapeCount));
                      escapeCount = 0;
                    } else {
                      charsList.add(Arrays.copyOfRange(chars, fileIndex, iByte - 1));
                    }
                    //									System.out.println("list:" + new
                    // String(charsList.get(charsList.size() - 1)));
                    fileIndex = iByte + 1;
                    escapeCount = 0;
                  } else if (!escapeOnFlg) {
                    if (lastChars != null) {

                      StringBuffer strBuff = new StringBuffer(lastChars.length + iByte - fileIndex);
                      strBuff.append(lastChars);
                      if (iByte != 0) {
                        strBuff.append(chars, fileIndex, iByte);
                      }
                      charsList.add(strBuff.toString().toCharArray());
                      lastChars = null;
                    } else {
                      charsList.add(Arrays.copyOfRange(chars, fileIndex, iByte));
                    }
                    fileIndex = iByte + 1;
                  }
                  break;
                case '\r':
                  crFlg = true;
                  break;
                case '\n':
                  //								System.out.println("\\n");
                  if (escapeOffFlg) {
                    escapeOnFlg = false;
                    escapeOffFlg = false;
                    if (maxLnCount < lnCount) {
                      maxLnCount = lnCount;
                    }
                    if (escapeCount > 0) {
                      charsList.add(
                          escape(
                              Arrays.copyOfRange(chars, fileIndex, iByte - (crFlg ? 2 : 1)),
                              '"',
                              escapeCount));
                      escapeCount = 0;
                    } else {
                      charsList.add(Arrays.copyOfRange(chars, fileIndex, iByte - (crFlg ? 2 : 1)));
                    }
                    crFlg = false;
                    fileIndex = iByte + 1;
                  } else if (!escapeOnFlg) {
                    charsList.add(Arrays.copyOfRange(chars, fileIndex, iByte));
                    fileIndex = iByte + 1;
                  }
                  break READ;
              }
            }
            //						System.out.println("z");
          }
          //					 System.out.println("index:" + index);
          //					 System.out.println("length:" + length);
          if (fileIndex < readFileLength) {
            //						 System.out.println("indx < length");
            if (escapeOffFlg) {
              escapeOnFlg = false;
              escapeOffFlg = false;
              if (maxLnCount < lnCount) {
                maxLnCount = lnCount;
              }
              lastChars = Arrays.copyOfRange(chars, fileIndex, readFileLength - 1);
            } else {
              lastChars = Arrays.copyOfRange(chars, fileIndex, readFileLength);
            }
          }
        }
      }
      if (readFileLength <= 0 && lastChars != null) {
        // ファイルの最後の場合
        charsList.add(lastChars);
        lastChars = null;
      }
      if (charsList.size() > 0) {
        cells = new CellInfo[charsList.size()];
        for (int i = 0; i < cells.length; i++) {
          // System.out.print(maxLnCount);
          cells[i] = new StringCellInfo(new String(charsList.get(i)), maxLnCount);
        }
        maxLnCount = 0;
        charsList.clear();
      }
    } catch (FileNotFoundException exception) {
    } catch (IOException exception) {
    } catch (Exception e) {
      e.printStackTrace();
    }
    return cells;
  }

  /**
   * エスケープ文字を除去する.
   *
   * @param chars
   * @param escape
   * @param escapeCount
   * @return
   */
  public static char[] escape(char[] chars, char escape, int escapeCount) {

    char[] escaped = new char[chars.length - escapeCount];
    int escapeIndex = 0;
    int startIndex = 0;
    for (int i = 0; i < chars.length; i++) {
      if (escape == chars[i]) {
        for (int j = startIndex; j <= i; j++) {
          escaped[escapeIndex++] = chars[j];
        }
        startIndex = i + 2;
        i++;
      }
    }
    if (escapeIndex < escaped.length) {
      for (int j = startIndex; j < chars.length; j++) {
        escaped[escapeIndex++] = chars[j];
      }
    }
    return escaped;
  }

  /**
   * 高速化のためにエスケープする列を固定したい.
   *
   * @param columnSize カラムサイズ
   * @param isForceSizeFix サイズ固定強制
   * @return 1行分の文字列配列
   * @throws IOException 入出力エラー発生時
   */
  public String[] getNextCsvLine(int columnSize, boolean isForceSizeFix) throws IOException {

    int charStartIndex = fileIndex; // 次の行のカラムinexを現在のfileIndexから取得
    int charCount = 0; // 文字列抽出時の文字数
    int columnIndex = 0; // 列Index
    String[] columns = new String[columnSize]; // 返却する文字列配列
    boolean isEscape = false; // エスケープ中か否か
    while (true) {
      // CSVレコードを取得するか、ファイルが終わるまで繰り返す、繰り返し単位は1文字
      if (fileIndex >= readFileLength) { // 次のバッファを読み込みたいとき
        if (charStartIndex < readFileLength) { // 取得中のデータがある場合はカラムバッファに格納
          columnBuff.append(new String(chars, charStartIndex, charCount));
        }
        // 読み込み
        int readLength = bis.read(chars);
        // 読み込みできない場合はループを抜ける
        if (readLength == IS_EOF) {
          break;
        }
        readFileLength += readLength;
      }
      if (isEscape) {
        // lengthチェックが必要
        if (chars[fileIndex] == '\"') {
          isEscape = false;
          fileIndex++;
          continue;
        }
      } else if (chars[fileIndex] == '\"') {
        if (charStartIndex >= fileIndex) {
          charStartIndex = fileIndex + 1;
          isEscape = true;
        } else {
          charCount++;
        }
        fileIndex++;
        continue;
      } else if (chars[fileIndex] == ',' || chars[fileIndex] == '\n') {
        if (columnIndex >= columnSize) {
          if (isForceSizeFix) {
            // 配列作り直し
            columns = Arrays.copyOf(columns, columns.length + 1);
          } else {
            // エラー
            throw new RuntimeException("パース失敗");
          }
        }
        if (columnBuff.length() > 0) {
          if (fileIndex > 0 && chars[fileIndex - 1] == '\r') {
            columnBuff.append(new String(chars, charStartIndex, charCount - 1));
          } else {
            columnBuff.append(new String(chars, charStartIndex, charCount));
          }
          columns[columnIndex] = columnBuff.toString();
          columnBuff.setLength(0);
        } else {
          if (fileIndex > 0 && chars[fileIndex - 1] == '\r') {
            columns[columnIndex] = new String(chars, charStartIndex, charCount - 1);
          } else {
            columns[columnIndex] = new String(chars, charStartIndex, charCount);
          }
        }
        if (chars[fileIndex] == '\n') {
          fileIndex++;
          // ここでデータ終了
          return columns;
        }
        fileIndex++;
        // 初期化
        columnIndex++;
        charStartIndex = fileIndex;
        charCount = 0;
        continue;
      }
      fileIndex++;
      charCount++;
    }

    // ファイルの最後に来た場合
    if (columnIndex >= columnSize) {
      if (isForceSizeFix) {
        // 配列作り直し
        columns = Arrays.copyOf(columns, columns.length + 1);
      } else {
        // エラー
        throw new RuntimeException("fail parse");
      }
    }

    if (columnBuff.length() > 0) {
      columns[columnIndex] = columnBuff.toString();
      columnBuff.setLength(0);
    } else {
      columns[columnIndex] = new String(chars, charStartIndex, charCount);
    }
    if (columnIndex == 0) {
      return null;
    }
    return columns;
  }

  @Override
  public void close() {
    if (bis != null) {
      try {
        bis.close();
      } catch (Exception e) {
      } finally {
        bis = null;
      }
    }
  }
}
