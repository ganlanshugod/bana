package org.bana.common.util.pdf.config;

/** 
* @ClassName: PdfObject 
* @Description: pdf对象文件
* @author liuwenjie   
*/ 
public interface PDFObject {

	/** 
	* @Description: pdf的水印
	* @author liuwenjie   
	* @date 2021年6月16日 下午1:04:13 
	* @return  
	*/ 
	Watermark getWatermark();
	
	/** 
	* @Description: 页眉页脚
	* @author liuwenjie   
	* @date 2021年6月16日 下午1:04:21 
	* @return  
	*/ 
	HeaderFooter getHeaderFooter();
	
	
	/** 
	* @Description: pdf内容对象
	* @author liuwenjie   
	* @date 2021年6月16日 下午2:02:02 
	* @return  
	*/ 
	PDFContent getPdfContent();
	
	
	/** 
	* @Description: PDF的样式配置
	* @author liuwenjie   
	* @date 2021年6月16日 下午1:35:37 
	* @return  
	*/ 
	PDFStyle getPDFStyle();
}
