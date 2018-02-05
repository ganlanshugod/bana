/**
* @Company 青鸟软通   
* @Title: ExcelConfig.java 
* @Package org.bana.common.util.poi 
* @author Liu Wenjie   
* @date 2015-7-2 上午11:03:06 
* @version V1.0   
*/ 
package org.bana.common.util.office.config;

import java.util.List;

/** 
 * @ClassName: ExcelConfig 
 * @Description: Excel的配置项
 *  
 */
public interface ExcelConfig {

	
	List<SheetConfig> getSheetConfigList();

}
