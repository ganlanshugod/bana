/**
* @Company weipu   
* @Title: RowConfig.java 
* @Package org.bana.common.util.office.impl 
* @author Liu Wenjie   
* @date 2015-7-7 下午3:48:59 
* @version V1.0   
*/ 
package org.bana.common.util.office.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bana.common.util.basic.StringUtils;


/** 
 * @ClassName: RowConfig 
 * @Description: excel的行配置信息
 *  
 */
public class RowConfig implements Serializable {

	/** 
	* @Fields serialVersionUID :  
	*/ 
	private static final long serialVersionUID = 2271378252498371408L;

	private RowType type;
	private Integer rowIndex;
	private String className;
    private Map<String,String> style;	
	private List<ColumnConfig> columnConfigList;
	private List<ColumnConfig> mutiColumnConfigList;
	private Map<String,ColumnConfig> columnConfigMap;
	 /** 
    * @Fields mutiTitle : 此标签是否是一个动态表头的设置
    */ 
    private boolean mutiTitle;
    
    /** 
    * @Fields rowHieght : 每行的高度值
    */ 
    private float rowHieght = 17.5f;
    
    
    /** 
    * @Description: 获取配置项按照名字的map集合
    * @author Liu Wenjie   
    * @date 2015-11-29 下午2:17:36 
    * @return  
    */ 
    public Map<String,ColumnConfig> getColumnConfigMap(){
    	if(columnConfigMap == null){
    		columnConfigMap = new HashMap<String, ColumnConfig>();
    		if(columnConfigList != null){
    			for (ColumnConfig config : columnConfigList) {
    				columnConfigMap.put(config.getName(), config);
    			}
    		}
    	}
    	return columnConfigMap;
    }
    /** 
    * @Description: 获取mutiColumn的列
    * @author Liu Wenjie   
    * @date 2015-11-29 下午12:14:29 
    * @return  
    */ 
    public List<ColumnConfig> getMutiColumnList(){
    	if(mutiColumnConfigList == null){
    		mutiColumnConfigList = new ArrayList<ColumnConfig>();
    		if(columnConfigList != null){
        		for (ColumnConfig columnConfig : columnConfigList) {
    				if(!StringUtils.isBlank(columnConfig.getMutiMap())){
    					mutiColumnConfigList.add(columnConfig);
    				}
    			}
        	}
    	}
    	return mutiColumnConfigList;
    }
    
    /** 
	* @Description: 获取所有因为mutiMap指向的列
	* @author Liu Wenjie   
	* @date 2015-11-29 下午5:00:46 
	* @return  
	*/ 
	public List<String> getMutiMapColumnName() {
		List<String> nameList = new ArrayList<String>();
    	List<ColumnConfig> mutiColumnList = getMutiColumnList();
    	for (ColumnConfig columnConfig : mutiColumnList) {
    		nameList.add(columnConfig.getMutiMap());
		}
    	return nameList;
	}
	
	/** 
	* @Description: 获取指定mutiMap所在的columnConfigName
	* @author Liu Wenjie   
	* @date 2015-11-29 下午5:13:09 
	* @param configName
	* @return  
	*/ 
	public String getColumnNameUseMutiMapName(String configName) {
		if(StringUtils.isBlank(configName)){
			return null;
		}
		List<ColumnConfig> mutiColumnList = this.getMutiColumnList();
		if(mutiColumnList != null){
			for (ColumnConfig columnConfig : mutiColumnList) {
				if(configName.equals(columnConfig.getMutiMap())){
					return columnConfig.getName();
				}
			}
		}
		return null;
	}
    
    /** 
    * @Description: 获取配置指定的所有列名
    * @author Liu Wenjie   
    * @date 2015-11-29 下午12:34:40 
    * @return  
    */ 
    public List<String> getAllColumnName(){
    	List<String> nameList = new ArrayList<String>();
    	if(columnConfigList != null){
    		for (ColumnConfig columnConfig : columnConfigList) {
    			nameList.add(columnConfig.getName());
			}
    	}
    	return nameList;
    }
    
    /** 
    * @Description: 获取包含动态列配置的名字，包含匹配的列名
    * @author Liu Wenjie   
    * @date 2015-11-29 下午12:46:45 
    * @return  
    */ 
    public List<String> getMutiIncludeMapColumnName(){
    	List<String> nameList = new ArrayList<String>();
    	List<ColumnConfig> mutiColumnList = getMutiColumnList();
    	for (ColumnConfig columnConfig : mutiColumnList) {
    		nameList.add(columnConfig.getName());
    		nameList.add(columnConfig.getMutiMap());
		}
    	return nameList;
    }
	
	/*=============getter and setter -===========*/
	/**
	 * @Description: 属性 type 的get方法 
	 * @return type
	 */
	public RowType getType() {
		return type;
	}



	/**
	 * @Description: 属性 type 的set方法 
	 * @param type 
	 */
	public void setType(RowType type) {
		this.type = type;
	}

	

	/**
	 * @Description: 属性 mutiTitle 的get方法 
	 * @return mutiTitle
	 */
	public boolean isMutiTitle() {
		return mutiTitle;
	}



	/**
	 * @Description: 属性 mutiTitle 的set方法 
	 * @param mutiTitle 
	 */
	public void setMutiTitle(boolean mutiTitle) {
		this.mutiTitle = mutiTitle;
	}
	

	/**
	 * @Description: 属性 rowHieght 的get方法 
	 * @return rowHieght
	 */
	public float getRowHieght() {
		return rowHieght;
	}
	/**
	 * @Description: 属性 rowHieght 的set方法 
	 * @param rowHieght 
	 */
	public void setRowHieght(float rowHieght) {
		this.rowHieght = rowHieght;
	}
	/**
	 * @Description: 属性 rowIndex 的get方法 
	 * @return rowIndex
	 */
	public Integer getRowIndex() {
		return rowIndex;
	}



	/**
	 * @Description: 属性 rowIndex 的set方法 
	 * @param rowIndex 
	 */
	public void setRowIndex(Integer rowIndex) {
		this.rowIndex = rowIndex;
	}



	/**
	 * @Description: 属性 className 的get方法 
	 * @return className
	 */
	public String getClassName() {
		return className;
	}



	/**
	 * @Description: 属性 className 的set方法 
	 * @param className 
	 */
	public void setClassName(String className) {
		this.className = className;
	}



	/**
	 * @Description: 属性 columnConfigList 的get方法 
	 * @return columnConfigList
	 */
	public List<ColumnConfig> getColumnConfigList() {
		return columnConfigList;
	}



	/**
	 * @Description: 属性 columnConfigList 的set方法 
	 * @param columnConfigList 
	 */
	public void setColumnConfigList(List<ColumnConfig> columnConfigList) {
		this.columnConfigList = columnConfigList;
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
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2015-7-8 上午11:27:51 
	* @return 
	* @see java.lang.Object#toString() 
	*/ 
	@Override
	public String toString() {
		return "RowConfig [type=" + type + ", rowIndex=" + rowIndex + ", className=" + className + ", style=" + style + ", columnConfigList=" + columnConfigList + "]";
	}


	public enum RowType{
		数据("data"),标题("title");
		private String value;
		private RowType(String value){
			this.value = value;
		}
		public String getValue() {
			return value;
		}
		public static RowType getInstance(String value){
			for (RowType rowType : values()) {
				if(rowType.getValue().equals(value)){
					return rowType;
				}
			}
			return 数据;
		}
	}


}
