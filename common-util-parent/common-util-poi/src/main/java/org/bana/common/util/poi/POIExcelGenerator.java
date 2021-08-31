package org.bana.common.util.poi;

import java.io.OutputStream;

import org.bana.common.util.poi.param.ExcelObject;

/** 
* @ClassName: POIExcelGenerator 
* @Description: 使用POI生成excel的接口
* @author liuwenjie   
*/ 
public interface POIExcelGenerator {

	/** 
	* @Description: 使用POI生成Excel文件
	* @author liuwenjie   
	* @date 2021年8月30日 下午7:04:03 
	* @param outputStream
	* @param excelObj  
	*/ 
	void generatorExcel(OutputStream outputStream,ExcelObject excelObj);
}
