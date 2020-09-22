/**
* @Company 全域旅游
* @Title: ColumnHandler.java 
* @Package org.bana.common.util.code.dao 
* @author liuwenjie   
* @date Sep 22, 2020 12:27:31 PM 
* @version V1.0   
*/ 
package org.bana.common.util.code.dao;

import org.bana.common.util.code.dao.jpa.ColumnCover;

/** 
* @ClassName: ColumnHandler 
* @Description: 一个数据库列的数据处理接口
* @author liuwenjie   
*/
public interface ColumnHandler {

	/** 
	* @Description: 
	* @author liuwenjie   
	* @date Sep 22, 2020 12:52:39 PM 
	* @param dataType
	* @return  
	*/ 
	ColumnCover handleCover(String dataType);
}
