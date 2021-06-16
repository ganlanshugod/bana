package org.bana.common.util.pdf;

import java.io.OutputStream;

import org.bana.common.util.pdf.config.PDFObject;

/** 
* @ClassName: PdfGenerator 
* @Description: PDF的生成方法
* @author liuwenjie   
*/ 
public interface PdfGenerator {


	/** 
	* @Description: 根据pdf的对象，生成pdf文件
	* @author liuwenjie   
	* @date 2021年6月16日 上午11:33:07 
	* @param outputStream
	* @param pdfObject  
	*/ 
	void generatePdf(OutputStream outputStream,PDFObject pdfObject) ;
}
