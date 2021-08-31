package org.bana.common.util.poi;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/** 
* @ClassName: POIUtil 
* @Description: 封装POI的一些共用类方法
* @author liuwenjie   
*/ 
public class POIUtil {
	
	/** 
	* @Description: 根据输入模板流创建WorkBook对象
	* @author liuwenjie   
	* @date 2021年8月30日 下午8:18:29 
	* @param templateFile
	* @return  
	*/ 
	public static Workbook getWorkbook(InputStream templateFile) {
		try {
			return WorkbookFactory.create(templateFile);
		} catch (IOException e) {
			throw new BanaPoiException("初始化workBook出错",e);
		} 
	}
}
