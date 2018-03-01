// (c) 2006 uchicom
package com.uchicom.csve;

import com.uchicom.csve.window.CsvTagEditor;

/**
 *
 * @author UCHIYAMA
 */
public class Main {
	
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CsvTagEditor frame = new CsvTagEditor();
        if (args.length > 1) {
            for (int i = 0; i < args.length; i++) {
                System.out.println(args[i]);
            }
        } else {
            frame.createTable(1000, 100);
        }
        frame.setVisible(true);
    }

}
