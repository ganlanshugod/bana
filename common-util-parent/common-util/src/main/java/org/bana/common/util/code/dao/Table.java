/**
* @Company 青鸟软通   
* @Title: Table.java 
* @Package org.bana.common.util.code.dao 
* @author Liu Wenjie   
* @date 2014-10-30 下午6:56:33 
* @version V1.0   
*/ 
package org.bana.common.util.code.dao;

import java.util.ArrayList;
import java.util.List;

import org.bana.common.util.exception.BanaUtilException;

/** 
 * @ClassName: Table 
 * @Description: 
 *  
 */
public class Table {
	private String tableSchama;
	private String tableName;
	private boolean tableSchamaUseInSql;
	/** 
	* @Fields hasPrimaryKey : 判断表中是否有主键，information_schema.table_constraints 中包含CONSTRAINT_TYPE = 'PRIMARY KEY'的列
	*/ 
	private boolean hasPrimaryKey;
	private List<Column> columnList;
	private List<Column> pkColumnList;
	/*=========业务方法===============*/
	
	/** 
	* @Description: 获取主键列
	* @author Liu Wenjie   
	* @date 2014-10-31 下午3:22:19 
	* @return  
	*/ 
	public List<Column> findPriColumnList(){
		if(pkColumnList == null){
			pkColumnList = new ArrayList<Column>();
			if(columnList != null && hasPrimaryKey){
				for (Column column : this.columnList) {
					if(Column.COLUMN_PRI_KEY.equalsIgnoreCase(column.getColumnKey())){
						pkColumnList.add(column);
					}
				}
			}
		}
		return pkColumnList;
	}
	
	/** 
	* @Description: 是否包含联合主键对象
	* @author Liu Wenjie   
	* @date 2015-7-13 下午6:43:09 
	* @return  
	*/ 
	public boolean hasPkDomain(){
		return findPriColumnList().size() > 1;
	}
	
	/** 
	* @Description: 获取主键类型对应的实体类
	* @author Liu Wenjie   
	* @date 2014-10-31 下午4:34:22 
	* @return  
	*/ 
	public String findIdType(){
		List<Column> priList = findPriColumnList();
		if(priList.isEmpty()){
			throw new BanaUtilException("不能生成主键类型，因为数据库中没有主键");
		}
		if(priList.size() == 1){
			return priList.get(0).getJavaType();
		}else{
			throw new BanaUtilException("目前暂不支持使用联合主键生成对象,"+ this.tableName + " 表目前有联合主键");
		}
	}
	
	
	/*============getter and setter ================*/
	/**
	 * @Description: 属性 tableSchama 的get方法 
	 * @return tableSchama
	 */
	public String getTableSchama() {
		return tableSchama;
	}
	/**
	 * @Description: 属性 tableSchama 的set方法 
	 * @param tableSchama 
	 */
	public void setTableSchama(String tableSchama) {
		this.tableSchama = tableSchama;
	}
	/**
	 * @Description: 属性 tableName 的get方法 
	 * @return tableName
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * @Description: 属性 tableName 的set方法 
	 * @param tableName 
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * @Description: 属性 columnList 的get方法 
	 * @return columnList
	 */
	public List<Column> getColumnList() {
		return columnList;
	}
	/**
	 * @Description: 属性 columnList 的set方法 
	 * @param columnList 
	 */
	public void setColumnList(List<Column> columnList) {
		this.columnList = columnList;
	}

	/**
	 * @Description: 属性 hasPrimaryKey 的get方法 
	 * @return hasPrimaryKey
	 */
	public boolean getHasPrimaryKey() {
		return hasPrimaryKey;
	}

	/**
	 * @Description: 属性 hasPrimaryKey 的set方法 
	 * @param hasPrimaryKey 
	 */
	public void setHasPrimaryKey(boolean hasPrimaryKey) {
		this.hasPrimaryKey = hasPrimaryKey;
	}

	/**
	 * @Description: 属性 tableSchamaUseInSql 的get方法 
	 * @return tableSchamaUseInSql
	 */
	public boolean isTableSchamaUseInSql() {
		return tableSchamaUseInSql;
	}

	/**
	 * @Description: 属性 tableSchamaUseInSql 的set方法 
	 * @param tableSchamaUseInSql 
	 */
	public void setTableSchamaUseInSql(boolean tableSchamaUseInSql) {
		this.tableSchamaUseInSql = tableSchamaUseInSql;
	}
	

}
