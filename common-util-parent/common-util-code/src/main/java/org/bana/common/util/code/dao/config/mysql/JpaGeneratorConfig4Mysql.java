/**
* @Company weipu   
* @Title: JpaGeneratorConfig4Mysql.java 
* @Package org.bana.common.util.code.jpa.mysql
* @author Huang Nana 
* @date 2017-12-11 14:53:22
* @version V1.0   
*/ 
package org.bana.common.util.code.dao.config.mysql;

import java.util.Map;

/** 
 * @ClassName: JpaGeneratorConfig4Mysql 
 * @Description: 生成entity与repository
 *  
 */
public class JpaGeneratorConfig4Mysql extends org.bana.common.util.code.dao.jpa.mysql.JpaGeneratorConfig4Mysql {

	
	/**
	 * @param tableName
	 * @param databaseName
	 * @param baseEntityName
	 */
	public JpaGeneratorConfig4Mysql(String tableName, String databaseName, Map<String, String> baseName,
			boolean hasCatalog) {
		super(tableName, databaseName, baseName,hasCatalog);
	}


}
