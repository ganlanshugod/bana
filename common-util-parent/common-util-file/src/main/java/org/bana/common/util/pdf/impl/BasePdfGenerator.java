package org.bana.common.util.pdf.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import org.bana.common.util.exception.BanaUtilException;
import org.bana.common.util.pdf.PdfGenerator;
import org.bana.common.util.pdf.config.PDFObject;
import org.bana.common.util.pdf.config.table.CellData;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/** 
* @ClassName: BasePdfGenerator 
* @Description: 一个基本的简单实现生成文件的方法
* @author liuwenjie   
*/ 
public class BasePdfGenerator implements PdfGenerator{

	// 最大宽度
	private static int maxWidth = 528;
	/**
	* <p>Description: </p> 
	* @author liuwenjie   
	* @date 2021年6月16日 上午11:36:56 
	* @param outputStream
	* @param pdfObject 
	* @see org.bana.common.util.pdf.PdfGenerator#generatorPdf(java.io.OutputStream, org.bana.common.util.pdf.config.PDFObject) 
	*/ 
	@Override
	public void generatePdf(OutputStream outputStream, PDFObject pdfObject) {
		try {
            // 1.新建document对象
            Document document = new Document(PageSize.A4);// 建立一个Document对象
 
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            
            setPdfWriterEvent(writer,pdfObject);
            
            // 3.打开文档
            document.open();
            writeDocInfo(document,pdfObject);
			
            // 4.向文档中添加内容
            generatePDFContent(document,pdfObject);
 
            // 5.关闭文档
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BanaUtilException(e);
        }
	}

	private void writeDocInfo(Document document, PDFObject pdfObject) {
		document.addTitle("Title@PDF-Java");// 标题
		document.addAuthor("Author@umiz");// 作者
		document.addSubject("Subject@iText pdf sample");// 主题
		document.addKeywords("Keywords@iTextpdf");// 关键字
		document.addCreator("Creator@umiz`s");// 创建者
	}

	protected void generatePDFContent(Document document, PDFObject pdfObject) throws DocumentException, IOException {
		// TODO Auto-generated method stub
		if (!(pdfObject instanceof SimplePdfObject)) {
			throw new BanaUtilException("不支持的对象类型");
			
		}
		SimplePdfObject simplePdfObject = (SimplePdfObject) pdfObject;
		SimplePDFContent pdfContent = simplePdfObject.getPdfContent();
		SimplePDFStyle pdfStyle = simplePdfObject.getPDFStyle();
		// 标题
		Font titleFont = pdfStyle.getTitleFont();
		String title = pdfContent.getTitle();
		Paragraph paragraph = new Paragraph(title, titleFont);
		paragraph.setAlignment(1); //设置文字居中 0靠左   1，居中     2，靠右
		paragraph.setIndentationLeft(12); //设置左缩进
		paragraph.setIndentationRight(12); //设置右缩进
		paragraph.setFirstLineIndent(24); //设置首行缩进
		paragraph.setLeading(20f); //行间距
		paragraph.setSpacingBefore(5f); //设置段落上空白
		paragraph.setSpacingAfter(10f); //设置段落下空白
		document.add(paragraph);
		
		// 加入表格
		Font textFont = pdfStyle.getTextFont();
//		pdfStyle.getHeadFont();
//		List<CellData> pdfContent.getTable();
		String tableTitle = "委托方信息Applicant";
		
		BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font headfont = new Font(bfChinese, 14, Font.BOLD);
        Font keyfont = new Font(bfChinese, 10, Font.BOLD);
        Font textfont = new Font(bfChinese, 10, Font.NORMAL);
		
		PdfPTable table = createTable(tableClospan(6,18));
		table.addCell(createCell("美好的一天", headfont, Element.ALIGN_LEFT, 2, false));
		List<CellData> cellList = new ArrayList<>();
		cellList.add(new CellData("委托方名称", "微谱集团"));
		cellList.add(new CellData("委托方地址", "国伟路135号"));
		cellList.add(new CellData("联系人", "刘文杰"));
		cellList.add(new CellData("联系电话", "18763906395"));
		cellList.add(new CellData("邮箱地址", "liuwenjiegod@126.com"));
		table.addCell(createCell("委托方信息\nApplicant", headfont, Element.ALIGN_CENTER,cellList.size()));
		
		for (CellData cellData : cellList) {
			table.addCell(createCell(cellData.getTitle()+":"+cellData.getValue(), textFont,Element.ALIGN_CENTER));
		}
		
		// 表格
//		table.addCell(createCell("早上9:00", keyfont, Element.ALIGN_CENTER));
//		table.addCell(createCell("中午11:00", keyfont, Element.ALIGN_CENTER));
//		table.addCell(createCell("中午13:00", keyfont, Element.ALIGN_CENTER));
//		table.addCell(createCell("下午15:00", keyfont, Element.ALIGN_CENTER));
//		table.addCell(createCell("下午17:00", keyfont, Element.ALIGN_CENTER));
//		table.addCell(createCell("晚上19:00", keyfont, Element.ALIGN_CENTER));
		
		
		
		document.add(table);
		
//		Integer totalQuantity = 0;
//		for (int i = 0; i < 5; i++) {
//			table.addCell(createCell("起床", textFont));
//			table.addCell(createCell("吃午饭", textFont));
//			table.addCell(createCell("午休", textfont));
//			table.addCell(createCell("下午茶", textfont));
//			table.addCell(createCell("回家", textfont));
//			table.addCell(createCell("吃晚饭", textfont));
//			totalQuantity ++;
//		}
//		table.addCell(createCell("总计", keyfont));
//		table.addCell(createCell("", textfont));
//		table.addCell(createCell("", textfont));
//		table.addCell(createCell("", textfont));
//		table.addCell(createCell(String.valueOf(totalQuantity) + "件事", textfont));
//		table.addCell(createCell("", textfont));
		
	}
	
	 /**
     * 创建单元格（指定字体、水平..）
     * @param value
     * @param font
     * @param align
     * @return
     */
	private PdfPCell createCell(String value, Font font, int align, int rowspan) {
		PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
//        cell.setColspan(colspan);
        cell.setRowspan(rowspan);
        cell.setPhrase(new Phrase(value, font));
        return cell;
	}
	
	/**
     * 创建单元格(指定字体)
     * @param value
     * @param font
     * @return
     */
	private PdfPCell createCell(String value, Font font) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }
	
	/**
     * 创建单元格（指定字体、水平..）
     * @param value
     * @param font
     * @param align
     * @return
     */
	private PdfPCell createCell(String value, Font font, int align) {
		PdfPCell cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(align);
		cell.setPhrase(new Phrase(value, font));
		return cell;
	}
	/**
     * 创建单元格（指定字体、水平居..、单元格跨x列合并、设置单元格内边距）
     * @param value
     * @param font
     * @param align
     * @param colspan
     * @param boderFlag
     * @return
     */
	private PdfPCell createCell(String value, Font font, int align, int colspan, boolean boderFlag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value, font));
        cell.setPadding(3.0f);
        if (!boderFlag) {
            cell.setBorder(0);
            cell.setPaddingTop(15.0f);
            cell.setPaddingBottom(8.0f);
        } else if (boderFlag) {
            cell.setBorder(1);
            cell.setPaddingTop(0.0f);
            cell.setPaddingBottom(15.0f);
        }
        return cell;
    }
    
	/**
     * 创建指定列宽、列数的表格
     * @param widths
     * @return
     */
	protected PdfPTable createTable(float[] widths) {
		PdfPTable table = new PdfPTable(widths);
		table.setTotalWidth(maxWidth);
		table.setLockedWidth(true);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.getDefaultCell().setBorder(1);
		table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
		return table;
	}
	
	//按照24行进行拆分表格宽度
	protected float[] tableClospan(float... widths) {
		for (int i = 0; i < widths.length; i++) {
			widths[i] *= 22;
		}
		return widths;
	}
	

	private void setPdfWriterEvent(PdfWriter writer, PDFObject pdfObject) {
		if(pdfObject.getWatermark() != null) {
			writer.setPageEvent(pdfObject.getWatermark());// 水印
		}
		
		if(pdfObject.getHeaderFooter() != null) {
			writer.setPageEvent(pdfObject.getHeaderFooter());// 页眉/页脚
		}
		
		
	}

}
