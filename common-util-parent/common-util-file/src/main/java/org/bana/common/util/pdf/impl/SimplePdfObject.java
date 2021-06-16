package org.bana.common.util.pdf.impl;

import org.bana.common.util.pdf.config.HeaderFooter;
import org.bana.common.util.pdf.config.PDFContent;
import org.bana.common.util.pdf.config.PDFObject;
import org.bana.common.util.pdf.config.Watermark;

public class SimplePdfObject implements PDFObject{
	
	private SimplePDFContent pdfContent;

	@Override
	public Watermark getWatermark() {
		return new Watermark("hello");
	}

	@Override
	public HeaderFooter getHeaderFooter() {
		return new HeaderFooter();
	}

	@Override
	public SimplePDFStyle getPDFStyle() {
		return new SimplePDFStyle();
	}

	@Override
	public SimplePDFContent getPdfContent() {
		return pdfContent;
	}

	public void setPdfContent(SimplePDFContent pdfContent) {
		this.pdfContent = pdfContent;
	}
	

}
