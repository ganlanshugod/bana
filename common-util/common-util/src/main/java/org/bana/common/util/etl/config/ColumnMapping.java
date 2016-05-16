/**
* @Company 青鸟软通   
* @Title: ColumnMapping.java 
* @Package org.bana.common.util.etl.config 
* @author Liu Wenjie   
* @date 2014-11-10 下午7:53:32 
* @version V1.0   
*/ 
package org.bana.common.util.etl.config;

/** 
 * @ClassName: ColumnMapping 
 * @Description: 列与列的对应关系表
 *  
 */
public class ColumnMapping {
	private String sourceColumnName;
	private String destColumnName;
	private ColumnType columnType;
	private String defaultValue;
	private String dateFormat;
	
	
	public enum ColumnType{
		STRING,DOUBLE,FLOAT,LONG,INT,SHORT,BYTE,DATE,DATETIME,TIMESTAMP;
		
		public static ColumnType getInstance(String name){
			return valueOf(name.toUpperCase());
		}
	}
	/**
	 * @Description: 属性 sourceColumnName 的get方法 
	 * @return sourceColumnName
	 */
	public String getSourceColumnName() {
		return sourceColumnName;
	}
	/**
	 * @Description: 属性 sourceColumnName 的set方法 
	 * @param sourceColumnName 
	 */
	public void setSourceColumnName(String sourceColumnName) {
		this.sourceColumnName = sourceColumnName;
	}
	/**
	 * @Description: 属性 destColumnName 的get方法 
	 * @return destColumnName
	 */
	public String getDestColumnName() {
		return destColumnName;
	}
	/**
	 * @Description: 属性 destColumnName 的set方法 
	 * @param destColumnName 
	 */
	public void setDestColumnName(String destColumnName) {
		this.destColumnName = destColumnName;
	}
	
	/**
	 * @Description: 属性 columnType 的get方法 
	 * @return columnType
	 */
	public ColumnType getColumnType() {
		return columnType;
	}
	/**
	 * @Description: 属性 columnType 的set方法 
	 * @param columnType 
	 */
	public void setColumnType(ColumnType columnType) {
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
	
	
	/**
	 * @Description: 属性 dateFormat 的get方法 
	 * @return dateFormat
	 */
	public String getDateFormat() {
		return dateFormat;
	}
	/**
	 * @Description: 属性 dateFormat 的set方法 
	 * @param dateFormat 
	 */
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	/**
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2014-11-10 下午9:02:31 
	* @return 
	* @see java.lang.Object#toString() 
	*/ 
	@Override
	public String toString() {
		return "ColumnMapping [sourceColumnName=" + sourceColumnName
				+ ", destColumnName=" + destColumnName + ", columnType="
				+ columnType + ", defaultValue=" + defaultValue + "]";
	}
	
}
