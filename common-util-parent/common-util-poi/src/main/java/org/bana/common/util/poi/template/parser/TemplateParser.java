package org.bana.common.util.poi.template.parser;

import org.apache.poi.ss.usermodel.Sheet;

/** 
* @ClassName: TemplateParser 
* @Description: 模板解析器，用于解析Excel中的模板
* @author liuwenjie   
*/ 
public interface TemplateParser {

	/** 
	* @Description:  解析模板中的值和内容
	* @author liuwenjie   
	* @date 2021年8月31日 下午5:14:43 
	* @param sheet
	* @param excelData  
	*/ 
	public void parseTemplate(Sheet sheet,Object excelData);
}
