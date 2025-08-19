// (C) 2006 uchicom
package com.uchicom.csve.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.Arrays;

public class RandomAccessCSVReader implements Closeable {

  RandomAccessFile randomAccessFile;
  // 解析位置
  int fileIndex;
  int BUFFER_SIZE = 4 * 1024 * 1024;
  int readLength = 0; // 読み込んだ長さ
  byte[] chars = new byte[BUFFER_SIZE];
  byte[] tmpChars = new byte[BUFFER_SIZE];

  StringBuffer columnBuff = new StringBuffer(128);
  static final int IS_EOF = -1;
  Charset charset;

  public RandomAccessCSVReader(RandomAccessFile randomAccessFile, Charset charset)
      throws Exception {
    this.randomAccessFile = randomAccessFile;
    this.charset = charset;
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

  /**
   * 高速化のためにエスケープする列を固定したい.
   *
   * @param columnSize カラムサイズ
   * @param isForceSizeFix サイズ調整
   * @return 1行分の文字列配列
   * @throws IOException 入出力エラー発生時
   */
  public String[] getNextCsvLine(int columnSize, boolean isForceSizeFix) throws IOException {

    // カラム抽出用
    int charStartIndex = fileIndex; // 次の行のレコード開始indexを現在のfileIndexから取得
    int charCount = 0; // カラム文字列抽出時の文字数

    // カラム格納用
    int columnIndex = 0; // 列Index
    String[] columns = new String[columnSize]; // 返却する文字列配列
    boolean isEscape = false; // エスケープ中か否か
    while (true) {
      // CSVレコードを取得するか、ファイルが終わるまで繰り返す、繰り返し単位は1文字
      if (fileIndex >= readLength) { // 次のバッファを読み込みたいとき
        if (charStartIndex < readLength) { // 取得中のデータがある場合はカラムバッファに格納
          readLength = readLength - charStartIndex;
          System.arraycopy(chars, charStartIndex, tmpChars, 0, readLength);
          byte[] tmpChars2 = chars;
          chars = tmpChars;
          tmpChars = tmpChars2;
          fileIndex = charCount;
        } else {
          fileIndex = 0;
          readLength = 0;
        }
        charStartIndex = 0;
        // 読み込み
        int length = randomAccessFile.read(chars, readLength, chars.length - readLength);

        // 読み込みできない場合はループを抜ける
        if (length == IS_EOF) {
          break;
        }
        readLength += length;
      }
      if (isEscape) {
        // lengthチェックが必要
        if (chars[fileIndex] == (byte) '\"') {
          isEscape = false;
          fileIndex++;
          continue;
        }
      } else if (chars[fileIndex] == (byte) '\"') {
        if (charStartIndex >= fileIndex) {
          charStartIndex = fileIndex + 1;
          isEscape = true;
        } else {
          charCount++;
        }
        fileIndex++;
        continue;
      } else if (chars[fileIndex] == (byte) ',' || chars[fileIndex] == (byte) '\n') {
        if (columnIndex >= columnSize) {
          if (isForceSizeFix) {
            // 配列作り直し
            columns = Arrays.copyOf(columns, columns.length + 1);
          } else {
            // エラー
            throw new RuntimeException("パース失敗");
          }
        }
        if (fileIndex > 0 && chars[fileIndex - 1] == (byte) '\r') {
          columns[columnIndex] = new String(chars, charStartIndex, charCount - 1, charset);
        } else {
          columns[columnIndex] = new String(chars, charStartIndex, charCount, charset);
        }

        if (chars[fileIndex] == (byte) '\n') {
          fileIndex++;
          // ここでレコード終了
          return columns;
        }
        fileIndex++;
        // 初期化
        columnIndex++;
        charStartIndex = fileIndex;
        charCount = 0;
        continue;
      }
      // 文字列カウントアップ
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
    columns[columnIndex] = new String(chars, charStartIndex, charCount);

    if (columnIndex == 0) {
      return null;
    }
    return columns;
  }

  @Override
  public void close() {
    if (randomAccessFile == null) {
      return;
    }
    try {
      randomAccessFile.close();
    } catch (Exception e) {
    } finally {
      randomAccessFile = null;
    }
  }
}
