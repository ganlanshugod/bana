/**
* @Company 青鸟软通   
* @Title: MongoGroup.java 
* @Package org.bana.common.util.etl.config 
* @author Liu Wenjie   
* @date 2015-8-7 下午4:20:52 
* @version V1.0   
*/ 
package org.bana.common.util.etl.config;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.bana.common.util.basic.DateUtil;

/** 
 * @ClassName: MongoGroup 
 * @Description: mongoGroup的配置内容
 *  
 */
public class MongoGroup implements Serializable{

	/** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = -8431561600807788841L;

	private String keyf;
	private List<Condition> conditions;
	private String reduce;
	private Map<String,Object> initial;
	
	
	public static class Condition implements Serializable{
		/** 
		* @Fields serialVersionUID :  
		*/ 
		private static final long serialVersionUID = -2931476513743880822L;
		private String name;
		private String value;
		private String type;
		private String min;
		private String max;
		private String dataType;
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
		 * @Description: 属性 value 的get方法 
		 * @return value
		 */
		public Object getValue() {
			if(StringUtils.isBlank(getDataType())){
				return value;
			}else if("boolean".equalsIgnoreCase(getDataType())){
				return Boolean.valueOf(value);
			}else if("Date".equalsIgnoreCase(getDataType())){
				return DateUtil.formateToDate(value);
			}else if("int".equalsIgnoreCase(getDataType())){
				return Integer.parseInt(value);
			}else{
				return value;
			}
		}
		/**
		 * @Description: 属性 value 的set方法 
		 * @param value 
		 */
		public void setValue(String value) {
			this.value = value;
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
		 * @Description: 属性 min 的get方法 
		 * @return min
		 */
		public String getMin() {
			return min;
		}
		/**
		 * @Description: 属性 min 的set方法 
		 * @param min 
		 */
		public void setMin(String min) {
			this.min = min;
		}
		/**
		 * @Description: 属性 max 的get方法 
		 * @return max
		 */
		public String getMax() {
			return max;
		}
		/**
		 * @Description: 属性 max 的set方法 
		 * @param max 
		 */
		public void setMax(String max) {
			this.max = max;
		}
		/**
		 * @Description: 属性 dataType 的get方法 
		 * @return dataType
		 */
		public String getDataType() {
			return dataType;
		}
		/**
		 * @Description: 属性 dataType 的set方法 
		 * @param dataType 
		 */
		public void setDataType(String dataType) {
			this.dataType = dataType;
		}
		
	}
	/**
	 * @Description: 属性 keyf 的get方法 
	 * @return keyf
	 */
	public String getKeyf() {
		return keyf;
	}
	/**
	 * @Description: 属性 keyf 的set方法 
	 * @param keyf 
	 */
	public void setKeyf(String keyf) {
		this.keyf = keyf;
	}
	/**
	 * @Description: 属性 conditions 的get方法 
	 * @return conditions
	 */
	public List<Condition> getConditions() {
		return conditions;
	}
	/**
	 * @Description: 属性 conditions 的set方法 
	 * @param conditions 
	 */
	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}
	/**
	 * @Description: 属性 reduce 的get方法 
	 * @return reduce
	 */
	public String getReduce() {
		return reduce;
	}
	/**
	 * @Description: 属性 reduce 的set方法 
	 * @param reduce 
	 */
	public void setReduce(String reduce) {
		this.reduce = reduce;
	}
	/**
	 * @Description: 属性 initial 的get方法 
	 * @return initial
	 */
	public Map<String, Object> getInitial() {
		return initial;
	}
	/**
	 * @Description: 属性 initial 的set方法 
	 * @param initial 
	 */
	public void setInitial(Map<String, Object> initial) {
		this.initial = initial;
	}
	
}
