// (c) 2006 uchicom
package com.uchicom.csve.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author uchiyama
 *
 */
public class SeparateReader {


	/**
	 * コンストラクタ
	 * @param fileName
	 * @param enc
	 */
	public SeparateReader(String fileName, String enc, char sepChar ) {
		this.sepChar = sepChar;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), enc));

		} catch (FileNotFoundException exception) {
		} catch (Exception e) {

		}
	}


    public SeparateReader(File file, String enc, char sepChar) {
    	this.sepChar = sepChar;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), enc));

        } catch (FileNotFoundException exception) {
        } catch (Exception e) {

        }
    }

    public String[] getNextSeparateLine() {
        String line = null;

        try {
            int ch = reader.read();
            if (ch > -1) {
                StringBuffer strBuff = new StringBuffer();
                while (ch > -1 ) {
                    if (((char)ch) == '\n') break;
                    strBuff.append((char)ch);
                    ch = reader.read();
                }
                line = strBuff.toString();
            }
        } catch (FileNotFoundException exception) {

        } catch (IOException exception) {
        } catch(Exception e) {
            e.printStackTrace();
        }
        List<String> charList = new ArrayList<>();
        if (line != null) {
            char[] lineChar = line.toCharArray();
            int iMaxChar = lineChar.length;
            int beginIndex = 0;
            for (int iChar = 0; iChar < iMaxChar; iChar++) {

                if (lineChar[iChar] == sepChar) {
                    charList.add(line.substring(beginIndex, iChar));
                    beginIndex = iChar + 1;
                }
            }
            if (beginIndex < iMaxChar) {
                charList.add(line.substring(beginIndex, iMaxChar));
            }

            return (String[]) charList.toArray(new String[0]);
        } else {
            return null;
        }
    }

    public void close() {
        if (reader != null) {
            try {
                reader.close();
            } catch (Exception e) {
            } finally {
                reader = null;
            }
        }
    }

    private char sepChar = ',';
    BufferedReader reader = null;
}
