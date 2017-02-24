/**
* @Company 青鸟软通   
* @Title: ColumnConfig.java 
* @Package org.bana.common.util.office.impl 
* @author Liu Wenjie   
* @date 2015-7-7 下午3:49:32 
* @version V1.0   
*/ 
package org.bana.common.util.office.impl.config;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;

import org.bana.common.util.basic.DateUtil;
import org.bana.common.util.basic.StringUtils;
import org.bana.common.util.exception.BanaUtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
			return readMethod.invoke(object);
		} catch (Exception e) {
			LOG.error("按照指定的属性名" + this.mappedBy + " 从对象 " + object.getClass().getName() + " 中获取值时失败",e);
			throw new BanaUtilException("按照指定的属性名" + this.mappedBy + " 从对象 " + object.getClass().getName() + " 中获取值时失败",e);
		} 
	}
	

	/** 
	* @Description: 设置当前列对应的属性值
	* @author Liu Wenjie   
	* @date 2015-7-13 下午2:09:05 
	* @param value
	* @param object  
	*/ 
	public void setColumnValue(Object value, Object object) {
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
		try {
			PropertyDescriptor pd = new PropertyDescriptor(this.mappedBy, object.getClass());
			Method writeMethod = pd.getWriteMethod();
			writeMethod.invoke(object,parseTypeValue(value));
		} catch (Exception e) {
			LOG.error("按照指定的属性名" + this.mappedBy + "使用" + value + " 设置对象 " + object.getClass().getName() + " 的属性值时失败",e);
			throw new BanaUtilException("按照指定的属性名" + this.mappedBy + "使用" + value + " 设置对象 " + object.getClass().getName() + " 的属性值时失败",e);
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
			if("int".equalsIgnoreCase(type)){
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


	/**
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2015-7-8 上午11:26:30 
	* @return 
	* @see java.lang.Object#toString() 
	*/ 
	@Override
	public String toString() {
		return "ColumnConfig [name=" + name + ", colspan=" + colspan + ", mappedBy=" + mappedBy + ", style=" + style + "]";
	}

}
