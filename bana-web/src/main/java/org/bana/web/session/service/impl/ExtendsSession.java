/**
* @Company weipu   
* @Title: ExtendsSession.java 
* @Package org.bana.web.session.service.impl 
* @author liuwenjie   
* @date 2016-5-11 下午3:56:10 
* @version V1.0   
*/ 
package org.bana.web.session.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/** 
 * @ClassName: ExtendsSession 
 * @Description: 扩展字段的session
 *  
 */
public class ExtendsSession implements Serializable {

	/** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = -2053676881584997428L;

	Map<String,Object> extendsMap = new HashMap<String,Object>();
	
	public void put(String key,Object value){
		extendsMap.put(key, value);
	}
	public Object get(String key){
		return extendsMap.get(key);
	}
	
	public int size(){
		return extendsMap.size();
	}
	
	public boolean isEmpty(){
		return extendsMap.isEmpty();
	}
	
	public Map<String,Object> toMap(){
		return extendsMap;
	}
}
