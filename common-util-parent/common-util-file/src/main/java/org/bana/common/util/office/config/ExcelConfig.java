/**
* @Company weipu   
* @Title: ExcelConfig.java 
* @Package org.bana.common.util.poi 
* @author Liu Wenjie   
* @date 2015-7-2 上午11:03:06 
* @version V1.0   
*/ 
package org.bana.common.util.office.config;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/** 
 * @ClassName: ExcelConfig 
 * @Description: Excel的配置项
 *  
 */
public interface ExcelConfig extends Serializable{


	List<SheetConfig> getSheetConfigList();
	
	
	/**
	 * @return 动态列的配置对象
	 */
	Map<String, List<ColumnConfig>> getMutiTitleMap();
}
