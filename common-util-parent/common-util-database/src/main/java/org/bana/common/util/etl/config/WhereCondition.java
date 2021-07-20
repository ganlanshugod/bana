/**
* @Company weipu   
* @Title: WhereCondition.java 
* @Package org.bana.common.util.etl.config 
* @author Liu Wenjie   
* @date 2014-11-24 上午11:56:03 
* @version V1.0   
*/ 
package org.bana.common.util.etl.config;

/** 
 * @ClassName: WhereCondition 
 * @Description: ETL同步工具的where条件
 *  
 */
public class WhereCondition {
	/** 
	* @Fields type : where 条件的类型 and or 
	*/ 
	private String type;
	
	/** 
	* @Fields whereValue : where 语句
	*/ 
	private String whereValue;

	/**
	 * @Description: 属性 type 的get方法 
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @Description: 属性 type 的set方法 
	 * @param type 
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @Description: 属性 whereValue 的get方法 
	 * @return whereValue
	 */
	public String getWhereValue() {
		return whereValue;
	}

	/**
	 * @Description: 属性 whereValue 的set方法 
	 * @param whereValue 
	 */
	public void setWhereValue(String whereValue) {
		this.whereValue = whereValue;
	}
	
	
}
