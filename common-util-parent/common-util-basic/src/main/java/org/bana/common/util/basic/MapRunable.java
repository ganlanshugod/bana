/**
* @Company weipu   
* @Title: MapRunable.java 
* @Package org.bana.common.util.basic
* @author Liu Wenjie   
* @date 2014-1-6 下午7:34:03 
* @version V1.0   
*/ 
package org.bana.common.util.basic;

import java.util.Map;

/** 
 * @ClassName: MapRunable 
 * @Description: 可以传递map参数的执行方法
 *  
 */
public abstract class MapRunable implements Runnable {

	protected Map<String,Object> map;
	
	public MapRunable(Map<String,Object> map){
		this.map = map;
	}
	
}
