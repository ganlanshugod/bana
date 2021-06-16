package org.bana.common.util.pdf.impl;

import java.io.IOException;

import org.bana.common.util.exception.BanaUtilException;
import org.bana.common.util.pdf.config.PDFStyle;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;

/**
 * @ClassName: SimplePDFStyle
 * @Description: 一个简单的pdf样式对象
 * @author liuwenjie
 */
public class SimplePDFStyle implements PDFStyle {

	// 不同字体（这里定义为同一种字体：包含不同字号、不同style）
	private BaseFont bfChinese;
	private Font titlefont;
	private Font headfont;
	private Font textfont;

	public SimplePDFStyle() {
		try {
			bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new BanaUtilException("设置默认字体时报错",e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new BanaUtilException("设置默认字体时报错",e);
		}
		titlefont = new Font(bfChinese, 16, Font.BOLD);
		headfont = new Font(bfChinese, 14, Font.BOLD);
		textfont = new Font(bfChinese, 10, Font.NORMAL);
	}

	public Font getTitleFont() {
		return titlefont;
	}

	public Font getHeadFont() {
		return headfont;
	}

	public Font getTextFont() {
		return textfont;
	}

}
