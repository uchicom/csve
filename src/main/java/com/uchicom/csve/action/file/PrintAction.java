// (c) 2006 uchicom
package com.uchicom.csve.action.file;

import java.awt.event.ActionEvent;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;

import com.uchicom.csve.util.UIAbstractAction;

/**
 *
 * @author uchiyama
 */
public class PrintAction extends UIAbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * プリントする(テスト用にプリンターが必要だけどどうしようかなGorstscriptが使えるか？)
	 */
	public void actionPerformed(ActionEvent actionEvent) {

		DocFlavor flavor = DocFlavor.INPUT_STREAM.POSTSCRIPT;
		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
		aset.add(MediaSizeName.ISO_A4);
		PrintService[] pservices = PrintServiceLookup.lookupPrintServices(flavor, aset);
		PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();

		System.out.println("DefaultService:" + defaultService);
		System.out.println("PrintService:" + pservices.length);
		if (pservices.length > 0) {
			DocPrintJob pj = pservices[0].createPrintJob();
			// InputStreamDoc is an implementation of the Doc interface //
			Doc doc = new SimpleDoc("test.ps", flavor, null);
			try {
				pj.print(doc, aset);
			} catch (PrintException e) {
			}
		}

		// これでダイアログが表示される。
//    	   PrintService service =  ServiceUI.printDialog(null, 50, 50,
//                   new PrintService[]{defaultService},defaultService,
//                   null,
//                   aset);

	}

}
