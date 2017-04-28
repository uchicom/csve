// (c) 2006 uchicom
package com.uchicom.csve;
import java.applet.Applet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.uchicom.csve.util.ListTableModel;


/*
<applet code="MainApplet" width=200 height=200></applet>
*/
public class MainApplet extends Applet {

	public void init() {
		try {
        CsvTagEditor frame = new CsvTagEditor();
            frame.createTable(1000, 100);
        frame.pack();
        frame.setVisible(true);
        try {
        Thread.sleep(5000);
        }catch(Exception e){}
	}catch (Exception e) {
		e.printStackTrace();
	}}


public JComponent createTable(int row, int col) {
JScrollPane scrollPane = null;
        try {
        List<String[]> cvsList = new ArrayList<String[]>(row);
        for (int i = 0; i < row; i++) {
            String[] strings = new String[col];
            for (int j = 0; j < col; j++) {
                strings[j] = "";
            }
            cvsList.add(strings);
        }
        JTable table = new JTable(new ListTableModel(cvsList,  col));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setColumnSelectionAllowed(true);
        table.setRowSelectionAllowed(true);
//        Map map = new HashMap();
        //map.put(new Integer(tabPane.getTabCount()), cvsList);
        scrollPane = new JScrollPane(table);


        } catch (Error error) {
            error.printStackTrace();
        }
        return scrollPane;
    }
}
