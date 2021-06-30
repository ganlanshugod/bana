package org.bana.common.util.pdf.config;

import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

/** 
* @ClassName: Watermark 
* @Description: pdf的水印
* @author liuwenjie   
*/ 
public class Watermark extends PdfPageEventHelper {

	private static Font defaultFont = new Font(Font.FontFamily.HELVETICA, 30, Font.BOLD, new GrayColor(0.95f));
	private Font font ;
    private String waterCont;//水印内容
    
    public Watermark() {
    	init();
    }
    public void init(){
        try {
			BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			this.font = new Font(bfChinese, 30, Font.BOLD,new GrayColor(0.95f));
		} catch (DocumentException e) {
			e.printStackTrace();
			this.font = defaultFont;
		} catch (IOException e) {
			this.font = defaultFont;
			e.printStackTrace();
		}
    }
    public Watermark(String waterCont) {
        init();
        this.waterCont = waterCont;
    }

    public Font getFont(){
        return this.font;
    }

    public void setFont(Font font){
        this.font = font;
    }
 
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        for(int i=0 ; i<5; i++) {
            for(int j=0; j<5; j++) {
                ColumnText.showTextAligned(writer.getDirectContentUnder(),
                        Element.ALIGN_CENTER,
                        new Phrase(this.waterCont == null ? "HELLO WORLD" : this.waterCont, this.getFont()),
                        (50.5f+i*350),
                        (40.0f+j*150),
                        writer.getPageNumber() % 2 == 1 ? 45 : -45);
            }
        }
    }
}
