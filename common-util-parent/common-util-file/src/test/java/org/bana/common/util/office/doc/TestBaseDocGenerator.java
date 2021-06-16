package org.bana.common.util.office.doc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.bana.common.util.office.doc.config.DocObject;
import org.bana.common.util.office.doc.impl.BaseDocGenerator;
import org.junit.Test;

/** 
* @ClassName: TestBaseDocGenerator 
* @Description: 测试word文件的生成
* @author liuwenjie   
*/ 
public class TestBaseDocGenerator {

	@Test
	public void testGenerator() throws IOException {
		File docFile = new File("office/doc/test02.docx");
		if(!docFile.getParentFile().exists()) {
			docFile.getParentFile().mkdirs();
		}
		FileOutputStream fileos = new FileOutputStream(docFile);
		DocObject docObj = null;
		BaseDocGenerator generator = new BaseDocGenerator();
		generator.generatorDoc(fileos, docObj);
		fileos.close();
	}
	
	@Test
	public void testPdfGenerator() throws FileNotFoundException, IOException{
		File pdfFile = new File("office/pdf/test01.pdf");
		if(!pdfFile.getParentFile().exists()) {
			pdfFile.getParentFile().mkdirs();
		}
		File docFile = new File("office/doc/test01.docx");
		FileInputStream is = new FileInputStream(docFile);
		XWPFDocument doc =  new XWPFDocument(is);
//		PdfOptions options = PdfOptions.create();
//		FileOutputStream os = new FileOutputStream(pdfFile);
//		PdfConverter.getInstance().convert(doc, os, options);
//		is.close();
//		os.close();
	}
	
	@Test
	public void testPdf() throws FileNotFoundException {
//		String DEST = "office/pdf/test02.pdf";
//		PdfDocument pdf = new PdfDocument(new PdfWriter(DEST));
//	    Document document = new Document(pdf);
//	    String line = "Hello! Welcome to iTextPdf";
//	    document.add(new Paragraph(line));
//	    document.close();
//
//	    System.out.println("Awesome PDF just got created.");
	}
	
	
}
