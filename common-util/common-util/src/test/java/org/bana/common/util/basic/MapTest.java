/**
* @Company 青鸟软通   
* @Title: MapTest.java 
* @Package org.bana.common.util.basic 
* @author Liu Wenjie   
* @date 2015-7-8 上午11:05:33 
* @version V1.0   
*/ 
package org.bana.common.util.basic;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.processors.JsonBeanProcessor;

import org.junit.Assert;
import org.junit.Test;

import com.mongodb.util.JSONSerializers;

/** 
 * @ClassName: MapTest 
 * @Description: 测试map问题
 *  
 */
public class MapTest {
	@Test
	public void testMapJson(){
		Map<String,String> cellMap = new HashMap<String, String>();
		cellMap.put("test1", "value1");
		cellMap.put("gogo", "testsdf");
		Map<String,String> cellMap2 = new HashMap<String, String>();
		cellMap2.put("gogo", "testsdf");
		cellMap2.put("test1", "value1");
		JSONObject fromObject = JSONObject.fromObject(cellMap);
		JSONObject fromObject2 = JSONObject.fromObject(cellMap2);
		Assert.assertEquals(fromObject.toString(), fromObject2.toString());
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("test1", "value1");
		jsonObject.put("gogo", "testsdf");
		System.out.println(jsonObject.toString());
		cellMap2.put("test2", "value3");
		cellMap2.putAll(cellMap);
		fromObject2 = JSONObject.fromObject(cellMap2);
		System.out.println(fromObject2.toString());
		System.out.println(cellMap2);
	}
	
	@Test
	public void testInstance(){
		Map<String,Object> map = new HashMap<String,Object>();
		System.out.println(Map.class.isInstance(map));
		
	}
	
	@Test
	public void testNullMap(){
		Map<String,String> map = new HashMap<String, String>();
		map.put(null, "123");
		System.out.println(map.get(null));
	}

}
