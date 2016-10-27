/**
* @Company 艾美伴行   
* @Title: JsonTest.java 
* @Package org.bana.common.util.basic 
* @author liuwenjie   
* @date 2016-9-29 下午1:02:36 
* @version V1.0   
*/ 
package org.bana.common.util.basic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.processors.PropertyNameProcessor;
import net.sf.json.processors.PropertyNameProcessorMatcher;

import org.junit.Test;



/** 
 * @ClassName: JsonTest 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 *  
 */
public class JsonTest {

	@Test
	public void testJson(){
		String json = "[{\"roleCode\":\"delete_reply\",\"orgs\":[],\"users\":[33]},{\"roleCode\":\"school_admin\",\"orgs\":[],\"users\":[45]}]";
		JSONArray jsonArray = JSONArray.fromObject(json);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setRootClass(RoleRefSave.class);
		Map<String,Object> configMap = new HashMap<String,Object>();
		configMap.put("users", Long.class);
		jsonConfig.setClassMap(configMap);
		RoleRefSave[] rolrRefSaveArr = (RoleRefSave[])JSONArray.toArray(jsonArray,jsonConfig);
		System.out.println(Arrays.toString(rolrRefSaveArr));
		for (RoleRefSave roleRefSave : rolrRefSaveArr) {
			List<Long> users = roleRefSave.getUsers();
			for (Long long1 : users) {
				System.out.println(long1);
			}
		}
//		Map<String,Object>[] rolrRefSaveArr = (Map<String,Object>[])JSONArray.toArray(jsonArray,Map.class);
//		for (Map<String, Object> map : rolrRefSaveArr) {
//			Set<Entry<String, Object>> entrySet = map.entrySet();
//			for (Entry<String, Object> entry : entrySet) {
//				System.out.println(entry.getKey().getClass() + "=" +  entry.getValue().getClass());
//			}
//		}
	}
}
