/**
* @Company 青鸟软通   
* @Title: SheetConfig.java 
* @Package org.bana.common.util.office.impl 
* @author Liu Wenjie   
* @date 2015-7-7 下午3:48:17 
* @version V1.0   
*/ 
package org.bana.common.util.office.impl.config;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/** 
 * @ClassName: SheetConfig 
 * @Description: 每个sheet页的配置信息
 *  
 */
public class SheetConfig implements Serializable {

	/** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 2429970413211699400L;

	/** 
	* @Fields name : sheet页的名称
	*/ 
	private String name;
	/** 
	* @Fields rowConfigList : 行配置的集合
	*/ 
	private List<RowConfig> rowConfigList;
	/** 
    * @Fields style : 设置的默认样式
    */ 
    private Map<String,String> style;	
    
    /** 
    * @Fields checkTitle : 是否校验标题名称
    */ 
    private boolean checkTitle;
    
	/*=========getter and setter ===========*/
	/**
	 * @Description: 属性 name 的get方法 
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @Description: 属性 name 的set方法 
	 * @param name 
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @Description: 属性 rowConfigList 的get方法 
	 * @return rowConfigList
	 */
	public List<RowConfig> getRowConfigList() {
		return rowConfigList;
	}
	/**
	 * @Description: 属性 rowConfigList 的set方法 
	 * @param rowConfigList 
	 */
	public void setRowConfigList(List<RowConfig> rowConfigList) {
		this.rowConfigList = rowConfigList;
	}
	/**
	 * @Description: 属性 style 的get方法 
	 * @return style
	 */
	public Map<String,String> getStyle() {
		return style;
	}
	/**
	 * @Description: 属性 style 的set方法 
	 * @param style 
	 */
	public void setStyle(Map<String,String> style) {
		this.style = style;
	}
	
	/**
	 * @Description: 属性 checkTitle 的get方法 
	 * @return checkTitle
	 */
	public boolean isCheckTitle() {
		return checkTitle;
	}
	/**
	 * @Description: 属性 checkTitle 的set方法 
	 * @param checkTitle 
	 */
	public void setCheckTitle(boolean checkTitle) {
		this.checkTitle = checkTitle;
	}
	
	/**
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2015-7-8 上午11:28:29 
	* @return 
	* @see java.lang.Object#toString() 
	*/ 
	@Override
	public String toString() {
		return "SheetConfig [name=" + name + ", rowConfigList=" + rowConfigList + ", style=" + style + "]";
	}
}
