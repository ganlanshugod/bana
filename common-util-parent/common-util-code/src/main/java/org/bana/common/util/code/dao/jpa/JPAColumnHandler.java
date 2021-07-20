/**
* @Company weipu
* @Title: JPAColumnHandler.java 
* @Package org.bana.common.util.code.dao.jpa 
* @author liuwenjie   
* @date Sep 22, 2020 12:54:38 PM 
* @version V1.0   
*/ 
package org.bana.common.util.code.dao.jpa;

import java.util.HashMap;
import java.util.Map;

import org.bana.common.util.code.dao.ColumnHandler;

/** 
* @ClassName: JPAColumnHandler 
* @Description: 
* @author liuwenjie   
*/
public class JPAColumnHandler implements ColumnHandler {
	
	private Map<String,ColumnCover> coverMap = new HashMap<>();

	/**
	* <p>Description: </p> 
	* @author liuwenjie   
	* @date Sep 22, 2020 12:54:38 PM 
	* @param dataType
	* @return 
	* @see org.bana.common.util.code.dao.ColumnHandler#handleCover(java.lang.String) 
	*/
	@Override
	public ColumnCover handleCover(String dataType) {
		ColumnCover columnCover = coverMap.get(dataType.toUpperCase());
		if("json".equalsIgnoreCase(dataType)) {
			System.out.println(dataType);
		}
		return columnCover;
	}
	
	public void addCover(ColumnCover columnCover) {
		coverMap.put(columnCover.getCoverJdbcTypeName(), columnCover);
	}

}
