package org.bana.common.util.pdf.impl.annotation;

import org.bana.common.util.exception.BanaUtilException;
import org.bana.common.util.pdf.config.PDFObject;
import org.bana.common.util.pdf.impl.BasePdfGenerator;
import org.bana.common.util.pdf.impl.SimplePdfObject;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

/** 
* @ClassName: AnnotationPDFGeneratoer 
* @Description: 通过对象中的注解，来解析对应的配置信息
* @author liuwenjie   
*/ 
public class AnnotationPDFGeneratoer extends BasePdfGenerator{

	@Override
	protected void generatePDFContent(Document document, PDFObject pdfObject) throws DocumentException {
		if (!(pdfObject instanceof SimplePdfObject)) {
			throw new BanaUtilException("不支持的对象类型");
		}
	}
}
