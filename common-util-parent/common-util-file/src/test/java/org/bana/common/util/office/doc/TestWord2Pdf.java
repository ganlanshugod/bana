package org.bana.common.util.office.doc;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.junit.Test;

public class TestWord2Pdf {

	@Test
	public void testCoverpdfAllFiles() {

		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Microsoft Word 2007+", "docx");
		chooser.setFileFilter(filter);
		chooser.setMultiSelectionEnabled(true);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File[] Files = chooser.getSelectedFiles();
			System.out.println("Please wait...");
			for (int i = 0; i < Files.length; i++) {
				String wordfile = Files[i].toString();
				WordToPdfConverter.convertWordToPdf(wordfile, wordfile.substring(0, wordfile.indexOf('.')) + ".pdf");
			}
			System.out.println("Conversion complete");
		}

	}

	@Test
	public void testCoverpdf() {
		String wordfile = "office/doc/test01.docx";
		WordToPdfConverter.convertWordToPdf(wordfile, wordfile.substring(0, wordfile.indexOf('.')) + ".pdf");

	}
}
