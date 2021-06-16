package org.bana.common.util.office.doc;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

/** 
* @ClassName: TestItextPdf 
* @Description: 
* @author liuwenjie   
*/ 
public class TestItextPdf {

	@Test
	public void testItexCreate() throws FileNotFoundException, DocumentException {
		String desc = "office/pdf/test03.pdf";
		Document pdfdoc = new Document(PageSize.A4, 72, 72, 72, 72);
		// create a pdf writer object to write text to mypdf.pdf file
		PdfWriter pwriter = PdfWriter.getInstance(pdfdoc, new FileOutputStream(desc));
		// specify the vertical space between the lines of text
		pwriter.setInitialLeading(20);
		pdfdoc.open();
	    String line = "Hello! Welcome to iTextPdf 12345678,你好啊，什么情况";
	    
	    Font font = FontFactory.getFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED,10f, Font.NORMAL, BaseColor.BLACK);
	    pdfdoc.add(new Paragraph(line,font));
	    //	    Font f = FontFactory.getFont(FontFactory.TIMES_ROMAN, Font.DEFAULTSIZE, Font.BOLD,
//				new BaseColor(Color.black.getRGB()));
//	    Chunk chObj1 = new Chunk(line);
//		pdfdoc.add(chObj1);
	    pdfdoc.close();
	}
	
	@Test
	public void testPdfCreate() throws DocumentException, IOException {
		// 基础的字体
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);//jar包
		
		Font topfont = new Font(bfChinese,14,Font.BOLD);
		Font textfont =new Font(bfChinese,10,Font.BOLD|Font.UNDERLINE);
		
		
		
	}
}
