// (c) 2006 uchicom
package com.uchicom.csve.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author UCHIYAMA
 */
public class CSVReader implements Closeable {

	private BufferedReader bis;
	private int length;
	private int index;
	private char[] chars = new char[1024 * 4 * 1024];
	private List<char[]> charsList = new ArrayList<>();
	boolean escapeOnFlg = false;
	boolean escapeOffFlg = false;
	char[] lastChars;
	int lnCount = 0;
	int maxLnCount = 0;
	String enc;
	private ByteArrayOutputStream baos = new ByteArrayOutputStream(4 * 1024 * 1024);

	/**
	 * Creates a new instance of CVSReader
	 * 
	 * @throws FileNotFoundException
	 */
	public CSVReader(String fileName, String enc) throws FileNotFoundException {
		this(new File(fileName), enc);
	}

	public CSVReader(File file, String enc) throws FileNotFoundException {
		this(new FileInputStream(file), enc);
	}

	public CSVReader(InputStream is, String enc) {
		try {
			bis = new BufferedReader(new InputStreamReader(is, enc));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param url
	 * @param enc
	 * @throws IOException
	 */
	public CSVReader(URL url, String enc) throws IOException {
		this(url.openStream(), enc);
	}

	public CellInfo[] getNextCsvLineCellInfo() {
		CellInfo[] cells = null;
		// System.out.println("getNextCsvLineCellInfo");
		//
		try {
			FOR1: if (index < length) {
				// System.out.println("for");
				for (int iByte = index; iByte < length; iByte++) {
					if (escapeOnFlg && !escapeOffFlg) {
						switch (chars[iByte]) {
						case '\"':
							escapeOffFlg = true;
							break;
						case '\n':
							lnCount++;
							break;
						}
					} else {
						switch (chars[iByte]) {
						case '\"':
							escapeOnFlg = true;
							index++;
							break;
						case ',':
							if (escapeOffFlg) {
								escapeOnFlg = false;
								escapeOffFlg = false;
								if (maxLnCount < lnCount) {
									maxLnCount = lnCount;
								}
								lnCount = 0;
								charsList.add(Arrays.copyOfRange(chars, index, iByte - 1));
								index = iByte + 1;
							} else if (!escapeOnFlg) {
								if (lastChars != null) {
									char[] temp = new char[lastChars.length + iByte - index];
									StringBuffer strBuff = new StringBuffer(lastChars.length + iByte - index);
									strBuff.append(lastChars);
									if (iByte != 0) {
										strBuff.append(chars, index, iByte);
									}
									charsList.add(strBuff.toString().toCharArray());
									lastChars = null;
								} else {
									charsList.add(Arrays.copyOfRange(chars, index, iByte));
								}
								index = iByte + 1;
							}
							break;
						case '\n':
							if (escapeOffFlg) {
								escapeOnFlg = false;
								escapeOffFlg = false;
								if (maxLnCount < lnCount) {
									maxLnCount = lnCount;
								}
								charsList.add(Arrays.copyOfRange(chars, index, iByte - 1));
								index = iByte + 1;
							} else if (!escapeOnFlg) {
								charsList.add(Arrays.copyOfRange(chars, index, iByte));
								index = iByte + 1;
							} else {
								lnCount++;
							}
							break FOR1;
						}
					}
				}
				if (index < length) {
					// System.out.println("a");
					lastChars = Arrays.copyOfRange(chars, index, length);
					index = 0;
					length = 0;
					if (maxLnCount < lnCount) {
						maxLnCount = lnCount;
					}
				}
			} else {
				// System.out.println("while");
				READ: while ((length = bis.read(chars)) > 0) {
					index = 0;
					// System.out.println("index:" + index + " length:" +
					// length);
					if (lastChars != null) {
						// System.out.println("lastChars:" + String.valueOf(
						// lastChars));
					}
					// System.out.println(new String(chars, 0, length));
					for (int iByte = index; iByte < length; iByte++) {
						if (escapeOnFlg && !escapeOffFlg) {
							switch (chars[iByte]) {
							case '\"':
								escapeOffFlg = true;
								break;
							case '\n':
								lnCount++;
								break;
							}
						} else {
							switch (chars[iByte]) {
							case '\"':
								escapeOnFlg = true;
								index++;
								break;
							case ',':
								if (escapeOffFlg) {
									escapeOnFlg = false;
									escapeOffFlg = false;
									if (maxLnCount < lnCount) {
										maxLnCount = lnCount;
									}
									lnCount = 0;
									charsList.add(Arrays.copyOfRange(chars, index, iByte - 1));
									index = iByte + 1;
								} else if (!escapeOnFlg) {
									if (lastChars != null) {

										char[] temp = new char[lastChars.length + iByte - index];
										StringBuffer strBuff = new StringBuffer(lastChars.length + iByte - index);
										strBuff.append(lastChars);
										if (iByte != 0) {
											strBuff.append(chars, index, iByte);
										}
										charsList.add(strBuff.toString().toCharArray());
										lastChars = null;
									} else {
										charsList.add(Arrays.copyOfRange(chars, index, iByte));
									}
									index = iByte + 1;
								}
								break;
							case '\n':
								if (escapeOffFlg) {
									escapeOnFlg = false;
									escapeOffFlg = false;
									if (maxLnCount < lnCount) {
										maxLnCount = lnCount;
									}
									charsList.add(Arrays.copyOfRange(chars, index, iByte - 1));
									index = iByte + 1;
								} else if (!escapeOnFlg) {
									charsList.add(Arrays.copyOfRange(chars, index, iByte));
									index = iByte + 1;
								}
								break READ;
							}
						}
					}
					if (index < length) {
						// System.out.println("indx < length");
						if (escapeOffFlg) {
							escapeOnFlg = false;
							escapeOffFlg = false;
							if (maxLnCount < lnCount) {
								maxLnCount = lnCount;
							}
							lastChars = Arrays.copyOfRange(chars, index, length - 1);
						} else {
							lastChars = Arrays.copyOfRange(chars, index, length);
						}
					}
				}
			}
			if (length <= 0 && lastChars != null) {
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
	 * 高速化のためにエスケープする列を固定したい
	 * 
	 * @param maxArray
	 * @param force
	 * @return
	 * @throws IOException
	 */
	public String[] getNextCsvLine(int maxArray, boolean force) throws IOException {

		int start = index;
		int l = 0;
		int arraySize = 0;// 返却するサイズ
		String[] strings = new String[maxArray];

		boolean escape = false;
		// 3パターン 足りない、ぴったり、多い
		StringBuffer strBuff = new StringBuffer();
		while (true) {
			System.out.println(index + ":" + length);
			// 取得したデータが終わるまで
			if (index >= length) {
				System.out.println("a");
				if (start < length) {
					strBuff.append(new String(chars, start, l));
				}
				length = bis.read(chars);
				if (length <= 0) {
					System.out.println("b");
					break;
				}
				start = 0;
				index = 0;
				l = 0;
			}
			System.out.println("b");
			if (escape) {
				// lengthチェックが必要
				if (chars[index] == '\"') {
					escape = false;
					index++;
					continue;
				}
			} else if (chars[index] == '\"') {
				escape = true;
				start = index + 1;
				index++;
				continue;
			} else if (chars[index] == ',' || chars[index] == '\n') {
				System.out.println("d");
				if (arraySize >= maxArray) {
					if (force) {
						// 配列作り直し
						strings = Arrays.copyOf(strings, strings.length + 1);
					} else {
						// エラー
						throw new RuntimeException("パース失敗");
					}
				}
				if (strBuff.length() > 0) {
					strBuff.append(new String(chars, start, l));
					strings[arraySize] = strBuff.toString();
					System.out.println(strings[arraySize]);
					strBuff.setLength(0);
				} else {
					strings[arraySize] = new String(chars, start, l);
					System.out.println(arraySize + ":" + strings[arraySize]);
				}
				arraySize++;
				// 初期化
				start = index + 1;
				l = 0;
				if (chars[index] == '\n') {
					index++;
					// ここでデータ終了
					return strings;
				}
				index++;
				continue;
			}
			index++;
			l++;
			System.out.println("c");
		}
		// データの最後に来た場合は変換してないデータがある

		if (arraySize >= maxArray) {
			if (force) {
				// 配列作り直し
				strings = Arrays.copyOf(strings, strings.length + 1);
			} else {
				// エラー
				throw new RuntimeException("パース失敗");
			}
		}

		if (strBuff.length() > 0) {
			strBuff.append(new String(chars, start, l));
			strings[arraySize] = strBuff.toString();
			System.out.println(arraySize + ":" + strings[arraySize]);
			strBuff.setLength(0);
		} else {
			strings[arraySize] = new String(chars, start, l);
		}
		if (arraySize == 0) {
			return null;
		}
		System.out.println("e");
		return strings;
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
