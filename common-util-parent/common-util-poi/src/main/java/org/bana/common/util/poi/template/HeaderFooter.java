package org.bana.common.util.poi.template;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/** 
* @ClassName: HeaderFooter 
* @Description: 模板文件编写headerFooter的设置
* @author liuwenjie   
*/ 
public interface HeaderFooter {

	/** 
	* @Description: 设置header和footer的方法
	* @author liuwenjie   
	* @date 2021年9月2日 上午11:15:03 
	* @param workbook
	* @param sheet
	* @param excelConfig
	* @param excelData
	* @param index  
	*/ 
	void onEndPage(Workbook workbook, Sheet sheet, Object excelData,
			int index);

}
