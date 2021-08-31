package org.bana.common.util.poi.template.parser;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/** 
* @ClassName: TemplateParser 
* @Description: 模板解析器，用于解析Excel中的模板
* @author liuwenjie   
*/ 
public interface TemplateParser {

	/** 
	* @Description: 解析模板中的值和内容
	* @author liuwenjie   
	* @date 2021年8月31日 上午9:26:08 
	* @param cell
	* @param row
	* @param data  
	*/ 
	void parserTemplate(Cell cell,Row row,Object data);
}
