package org.bana.common.util.poi.param;

/** 
* @ClassName: ExcelObject 
* @Description: 生成Excel的接口对象
* @author liuwenjie   
*/ 
public interface ExcelObject {

	/** 
	* @Description: 获取生成Excel的配置文件
	* @author liuwenjie   
	* @date 2021年8月30日 下午8:30:38 
	* @return  
	*/ 
	ExcelConfig getExcelConfig();
	
	
	/** 
	* @Description: 获取生成Excel的data对象
	* @author liuwenjie   
	* @date 2021年8月30日 下午8:31:27 
	* @return  
	*/ 
	Object getExcelData();
}
