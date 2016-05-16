/**
* @Company 青鸟软通   
* @Title: ColumnConfig.java 
* @Package org.bana.common.util.etl.config 
* @author Liu Wenjie   
* @date 2014-11-10 下午7:54:33 
* @version V1.0   
*/ 
package org.bana.common.util.etl.config;

/** 
 * @ClassName: ColumnConfig 
 * @Description: 数据库中的列配置
 *  
 */
public class ColumnConfig {

	private String columnName; //列名，列类型
	private String columnType;
	private String defaultValue;
	
	
	/*===========getter and setter ============*/
	/**
	 * @Description: 属性 columnName 的get方法 
	 * @return columnName
	 */
	public String getColumnName() {
		return columnName;
	}
	/**
	 * @Description: 属性 columnName 的set方法 
	 * @param columnName 
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	/**
	 * @Description: 属性 columnType 的get方法 
	 * @return columnType
	 */
	public String getColumnType() {
		return columnType;
	}
	/**
	 * @Description: 属性 columnType 的set方法 
	 * @param columnType 
	 */
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	/**
	 * @Description: 属性 defaultValue 的get方法 
	 * @return defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}
	/**
	 * @Description: 属性 defaultValue 的set方法 
	 * @param defaultValue 
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	
}
