package org.bana.common.util.poi.template.param;

import org.bana.common.util.poi.param.ExcelConfig;
import org.bana.common.util.poi.template.HeaderFooter;

/** 
* @ClassName: TemplateExcelConfig 
* @Description: 模板类对应的Excel配置信息
* @author liuwenjie   
*/ 
public class TemplateExcelConfig implements ExcelConfig{

	
	/** 
	* @Fields sheetFrom : 从第几个sheet开始生成
	*/ 
	private int sheetFrom = 0;
	
	/** 
	* @Fields sheetNum : 一共处理几个sheet页
	*/ 
	private int sheetNum = -1;
	
	private HeaderFooter headerFooter;
	
	
	/*====getter and setter =====*/

	public int getSheetFrom() {
		return sheetFrom;
	}

	public void setSheetFrom(int sheetFrom) {
		this.sheetFrom = sheetFrom;
	}

	public int getSheetNum() {
		return sheetNum;
	}

	public void setSheetNum(int sheetNum) {
		this.sheetNum = sheetNum;
	}

	public HeaderFooter getHeaderFooter() {
		return headerFooter;
	}

	public void setHeaderFooter(HeaderFooter headerFooter) {
		this.headerFooter = headerFooter;
	}

	
}
