package org.bana.common.util.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.bana.common.util.office.doc.WordToPdfConverter;
import org.bana.common.util.pdf.impl.BasePdfGenerator;
import org.bana.common.util.pdf.impl.SimplePDFContent;
import org.bana.common.util.pdf.impl.SimplePdfObject;
import org.junit.Test;

/** 
* @ClassName: TestPdfGenerator 
* @Description: 测试Pdf的生成类
* @author liuwenjie   
*/ 
public class TestPdfGenerator {

	@Test
	public void testGenerator() throws FileNotFoundException {
		File file = new File("office/pdf/PDF2.pdf");
		PdfGenerator generator = new BasePdfGenerator();
		SimplePdfObject pdfObject = new SimplePdfObject();
		
		SimplePDFContent content = new SimplePDFContent();
		content.setTitle("测博士技术服务委托表");
		
		pdfObject.setPdfContent(content);
		generator.generatePdf(new FileOutputStream(file),pdfObject);
		
	}
	
	@Test
	public void testCoverpdf() {
		String wordfile = "office/doc/test01.docx";
		WordToPdfConverter.convertWordToPdf(wordfile, wordfile.substring(0, wordfile.indexOf('.')) + ".pdf");
	}
	
}
