/**
* @Company 青鸟软通   
* @Title: SortCond.java 
* @Package org.bana.system.common.dao 
* @author liuwenjie   
* @date 2016-3-3 下午3:49:25 
* @version V1.0   
*/ 
package org.bana.common.util.page;

/** 
 * @ClassName: SortCond 
 * @Description: 排序对象
 *  
 */
public class SortCond {

	/** 
	* @ClassName: SortType 
	* @Description: 排序类型
	*/ 
	public enum SortType{
		ASC,DESC
	}
	
	private SortType sortType;
	private String sortColumn;
	
	public SortCond(String sortColumn,SortType type) {
		this.sortColumn = sortColumn;
		this.sortType = type;
	}

	/**
	 * @Description: 属性 sortType 的get方法 
	 * @return sortType
	 */
	public String getSortType() {
		return sortType.toString();
	}

	/**
	 * @Description: 属性 sortColumn 的get方法 
	 * @return sortColumn
	 */
	public String getSortColumn() {
		return sortColumn;
	}
	
}
