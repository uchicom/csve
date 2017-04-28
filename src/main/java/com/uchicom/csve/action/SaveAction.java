// (c) 2006 uchicom
package com.uchicom.csve.action;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JTabbedPane;

import com.uchicom.csve.CsvTagEditorUI;
import com.uchicom.csve.util.CSVWriter;
import com.uchicom.csve.util.UIAbstractAction;

/**
 *
 * @author uchiyama
 */
public class SaveAction extends UIAbstractAction {


    public void actionPerformed(ActionEvent actionEvent) {

        CsvTagEditorUI csvTagEditorUI = (CsvTagEditorUI)uiStore.getUI(CsvTagEditorUI.UI_KEY);
        Component basisComponent = csvTagEditorUI.getBasisComponent();
        JTabbedPane tabPane = csvTagEditorUI.getTabb();

        try {
            JFileChooser chooser = new JFileChooser();
            File file = csvTagEditorUI.getFileMap().get(tabPane.getSelectedIndex());
            if (file != null) {
            	chooser.setSelectedFile(file);
            } else {
            	String path = csvTagEditorUI.getProperties().getProperty("path");
            	if (path != null && !"".equals(path)) {
            		file = new File(path);
            		chooser.setSelectedFile(file.getParentFile());
            	}

            }
           int result =  chooser.showSaveDialog(basisComponent);
           if (JFileChooser.APPROVE_OPTION == result) {
            File selectFile = chooser.getSelectedFile();
            tabPane.setTitleAt(tabPane.getSelectedIndex(), selectFile.getName());

            CSVWriter writer = new CSVWriter(selectFile, "SJIS");
            List csvList = (List)csvTagEditorUI.getTableMap().get(new Integer(tabPane.getSelectedIndex()));
            if(csvList == null) {
               System.out.println(tabPane.getSelectedComponent());
            }
            writer.write(csvList);
            writer.close();
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
