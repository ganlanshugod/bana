/**
* @Company 青鸟软通   
* @Title: ColumnConfig.java 
* @Package org.bana.common.util.office.impl 
* @author Liu Wenjie   
* @date 2015-7-7 下午3:49:32 
* @version V1.0   
*/ 
package org.bana.common.util.office.config;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.bana.common.util.basic.DateUtil;
import org.bana.common.util.basic.StringUtils;
import org.bana.common.util.exception.BanaUtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/** 
 * @ClassName: ColumnConfig 
 * @Description: 列配置信息 
 *  
 */
public class ColumnConfig implements Serializable {
	
	private static final Logger LOG = LoggerFactory.getLogger(ColumnConfig.class);

	/** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = -8631063741810490588L;
	
	/**
	 * 列的排序值
	 */
	private int sort;

	/** 
	* @Fields name : 列名
	*/ 
	private String name;
	/** 
	* @Fields colspan : 单元格占的行个数
	*/ 
	private Integer colspan;
	/** 
	* @Fields mappedBy : 映射的类对应的属性，支持 "." 获取下一级的方式 
	*/ 
	private String mappedBy;
	
	/** 
	* @Fields dataType : 指定数据类型的格式
	*/ 
	private String type;
	
    /** 
    * @Fields style : 设置的默认样式
    */ 
    private Map<String,String> style;	
    
    /** 
    * @Fields mutiMap : 当多列时，此列映射的列值
    */ 
    private String mutiMap;
    
    /** 
    * @Fields useDic : 导出时的值，是否使用字典项
    */ 
    private boolean useDic;
    
    /**
     * 只有useDic为true时，此字段有效，展示字典对应的可选择区域值
     */
    private boolean showSelectList;
    
    /**
     * 导入列是否是动态列
     */
    private boolean isMuti;
    
    /** 
    * @Fields dicType : 如果使用字典项，那么使用的字典项的值是什么
    */ 
    private String dicType;
    
    
    /** 
	* @Description: 根据传入的对象和当前列的配置，获取对应的属性
	* @author Liu Wenjie   
	* @date 2015-7-9 上午11:36:54 
	* @param object  
     * @param cellKey 
	*/ 
    @SuppressWarnings("rawtypes")
	public Object getCellValue(Object object, String cellKey) {
		if(object == null){
			return null;
		}
		//如果cellKey不为空，并且对象是个Map对象，那么返回对应的value值
		if(StringUtils.isNotBlank(cellKey) && Map.class.isInstance(object)){
			Map mapObject = (Map)object;
			return mapObject.get(cellKey);
		}
		if(StringUtils.isEmpty(mappedBy)){
			throw new BanaUtilException("没有指明匹配的对象的列属性值");
		}
		try {
			PropertyDescriptor pd = new PropertyDescriptor(this.mappedBy, object.getClass());
			Method readMethod = pd.getReadMethod();
			Object columnValue = readMethod.invoke(object);
			if(isMuti()&&StringUtils.isNotBlank(cellKey) && Map.class.isInstance(columnValue)){
				Map mapObject = (Map)columnValue;
				return mapObject.get(cellKey);
			}else{
				return columnValue;
			}
		} catch (Exception e) {
			LOG.error("按照指定的属性名" + this.mappedBy + " 从对象 " + object.getClass().getName() + " 中获取值时失败",e);
			throw new BanaUtilException("按照指定的属性名" + this.mappedBy + " 从对象 " + object.getClass().getName() + " 中获取值时失败",e);
		} 
		
	}
	
    public void setColumnValue(Object value, Object obj) {
    	setColumnValue(value,obj,null);
	}


	/** 
	* @Description: 设置当前列对应的属性值
	* @author Liu Wenjie   
	* @date 2015-7-13 下午2:09:05 
	* @param value
	* @param object  
	*/ 
	public void setColumnValue(Object value, Object object, String colName) {
		if(object == null){
			return;
		}
		if(StringUtils.isEmpty(mappedBy)){
			throw new BanaUtilException("没有指明匹配的对象的列属性值");
		}
		if("@index".equalsIgnoreCase(mappedBy)){
			LOG.debug("当前值为 " + value + ",因为使用的是特殊公式 @index, 所以实际对象中不包含此数据");
			return;
		}
		if(object instanceof JSONObject){//如果是jsonObject进行设置
			JSONObject jsonObject = (JSONObject)object;
			if(isMuti()){
				Map<String,Object> map = jsonObject.getObject(this.mappedBy, Map.class);
				if(map == null){
					map = new HashMap<String,Object>();
					jsonObject.put(this.mappedBy, map);
				}
				map.put(colName, parseTypeValue(value));
			}else{
				jsonObject.put(this.mappedBy, parseTypeValue(value));
			}
		}else{//通过反射设置对象的属性
			try {
				PropertyDescriptor pd = new PropertyDescriptor(this.mappedBy, object.getClass());
				if(isMuti()){
					Method readMethod = pd.getReadMethod();
					Map<String,Object> map = (Map<String,Object>)readMethod.invoke(object);
					if(map == null){
						map = new HashMap<String,Object>();
						Method writeMethod = pd.getWriteMethod();
						writeMethod.invoke(object,map);
					}
					map.put(colName, parseTypeValue(value));
				}else{
					Method writeMethod = pd.getWriteMethod();
					writeMethod.invoke(object,parseTypeValue(value));
				}
			} catch (Exception e) {
				LOG.error("按照指定的属性名" + this.mappedBy + "使用" + value + " 设置对象 " + object.getClass().getName() + " 的属性值时失败",e);
				throw new BanaUtilException("按照指定的属性名" + this.mappedBy + "使用" + value + "设置对象 " + object.getClass().getName() + " 的属性值时失败",e);
			}
		}
	}
	
	
	/** 
	* @Description: 解析返回的数据为指定的数据类型
	* @author Liu Wenjie   
	* @date 2015-11-20 上午10:52:51 
	* @param value
	* @return  
	*/ 
	public Object parseTypeValue(Object value) {
		if(StringUtils.isBlank(type)){
			return value;
		}
		if(value instanceof String){
			String str = (String)value;
			if(StringUtils.isBlank(str)){
				return null;
			}
			if("int".equalsIgnoreCase(type)||"Integer".equalsIgnoreCase(type)){
				return Integer.parseInt(str);
			}else if("long".equalsIgnoreCase(type)){
				return Long.parseLong(str);
			}else if("float".equalsIgnoreCase(type)){
				return Float.parseFloat(str);
			}else if("double".equalsIgnoreCase(type)){
				return Double.parseDouble(str);
			}else if("boolean".equalsIgnoreCase(type)){
				return Boolean.parseBoolean(str);
			}else if("short".equalsIgnoreCase(type)){
				return Short.parseShort(str);
			}else if("byte".equalsIgnoreCase(type)){
				return Byte.parseByte(str);
			}else if("date".equalsIgnoreCase(type)){
				return DateUtil.formateToDate(str);
			}
		}
		return value;
	}


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
	 * @Description: 属性 colspan 的get方法 
	 * @return colspan
	 */
	public Integer getColspan() {
		return colspan;
	}
	/**
	 * @Description: 属性 colspan 的set方法 
	 * @param colspan 
	 */
	public void setColspan(Integer colspan) {
		this.colspan = colspan;
	}
	/**
	 * @Description: 属性 mappedBy 的get方法 
	 * @return mappedBy
	 */
	public String getMappedBy() {
		return mappedBy;
	}
	/**
	 * @Description: 属性 mappedBy 的set方法 
	 * @param mappedBy 
	 */
	public void setMappedBy(String mappedBy) {
		this.mappedBy = mappedBy;
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
	 * @Description: 属性 mutiMap 的get方法 
	 * @return mutiMap
	 */
	public String getMutiMap() {
		return mutiMap;
	}

	public boolean isMuti() {
		return isMuti;
	}


	public void setMuti(boolean isMuti) {
		this.isMuti = isMuti;
	}


	/**
	 * @Description: 属性 mutiMap 的set方法 
	 * @param mutiMap 
	 */
	public void setMutiMap(String mutiMap) {
		this.mutiMap = mutiMap;
	}
	

	/**
	 * @Description: 属性 useDic 的get方法 
	 * @return useDic
	 */
	public boolean isUseDic() {
		return useDic;
	}


	/**
	 * @Description: 属性 useDic 的set方法 
	 * @param useDic 
	 */
	public void setUseDic(boolean useDic) {
		this.useDic = useDic;
	}


	/**
	 * @Description: 属性 dicType 的get方法 
	 * @return dicType
	 */
	public String getDicType() {
		return dicType;
	}


	/**
	 * @Description: 属性 dicType 的set方法 
	 * @param dicType 
	 */
	public void setDicType(String dicType) {
		this.dicType = dicType;
	}
	
	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
	
	public static ColumnConfig parseString(String configString){
		ColumnConfig columnConfig = new ColumnConfig();
		if(StringUtils.isBlank(configString)){
			throw new BanaUtilException("初始化ColumnCOnfig的字符串不能为空");
		}
		if(!configString.contains("(")){
			columnConfig.setName(configString);
		}else{
			if(!configString.contains(")")){
				throw new BanaUtilException("ColumnConfig的配置字符串不正确,没有右括号"+configString);
			}else{
				String configArr = configString.substring(configString.indexOf("(")+1,configString.lastIndexOf(")"));
				String name = configString.substring(0,configString.indexOf("("));
				columnConfig.setName(name);
				for (String config : configArr.split(";")) {
					int index = config.indexOf(":");
					if(config.indexOf(":") != -1){
						String configName = config.substring(0,index);
						String configValue = config.substring(index+1);
						if("dicType".equals(configName)){
							columnConfig.setUseDic(true);
						}
						try {
							Field declaredField = FieldUtils.getDeclaredField(columnConfig.getClass(), configName,true);
							if(declaredField.getType().equals(boolean.class) || declaredField.getType().equals(Boolean.class)){
								FieldUtils.writeField(columnConfig, configName, Boolean.valueOf(configValue),true);
							}else{
								FieldUtils.writeField(columnConfig, configName, configValue,true);
							}
						} catch (IllegalAccessException e) {
							throw new BanaUtilException("写入配置属性出错"+configName+"="+configValue,e);
						}
					}else{
						throw new BanaUtilException("配置错误，配置中需要用':' 分开");
					}
				}
			}
		}
		return columnConfig;
	}
	
	@Override
	public String toString() {
		return "ColumnConfig [sort=" + sort + ", name=" + name + ", colspan=" + colspan + ", mappedBy=" + mappedBy
				+ ", type=" + type + ", style=" + style + ", mutiMap=" + mutiMap + ", useDic=" + useDic
				+ ", showSelectList=" + showSelectList + ", isMuti=" + isMuti + ", dicType=" + dicType + "]";
	}

	public boolean isShowSelectList() {
		return showSelectList;
	}

	public void setShowSelectList(boolean showSelectList) {
		this.showSelectList = showSelectList;
	}
	
}
