/**
* @Company 青鸟软通   
* @Title: Procedures.java 
* @Package org.bana.common.util.etl.config 
* @author Liu Wenjie   
* @date 2014-11-28 下午8:13:59 
* @version V1.0   
*/ 
package org.bana.common.util.etl.config;

import java.util.List;

/** 
 * @ClassName: Procedures 
 * @Description: 存储过程的配置
 *  
 */
public class Procedures {

	private String name;
	
	private List<String> params;

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
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2015-1-8 下午10:07:22 
	* @return 
	* @see java.lang.Object#toString() 
	*/ 
	@Override
	public String toString() {
		return "Procedures [name=" + name + ", params=" + params + "]";
	}

	/**
	 * @Description: 属性 params 的get方法 
	 * @return params
	 */
	public List<String> getParams() {
		return params;
	}

	/**
	 * @Description: 属性 params 的set方法 
	 * @param params 
	 */
	public void setParams(List<String> params) {
		this.params = params;
	}
	
	
	
	
}
