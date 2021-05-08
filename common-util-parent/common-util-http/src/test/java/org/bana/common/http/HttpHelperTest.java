/**
* @Company 全域旅游
* @Title: HttpHelperTest.java 
* @Package org.bana.common.http 
* @author liuwenjie   
* @date 2021年4月12日 下午6:38:12 
* @version V1.0   
*/ 
package org.bana.common.http;

import static org.junit.Assert.*;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

/** 
* @ClassName: HttpHelperTest 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author liuwenjie   
*/
public class HttpHelperTest {
	private HttpHelper httpHelper = new HttpHelper();

	@Test
	public void testHttp() {
		JSONObject httpGet = httpHelper.httpPost("http://keyance-api.sh.ceboshi.cn/",null,true);
		System.out.println(httpGet);
	}
	
	@Test
	public void testDelete() {
		JSONObject httpGet = httpHelper.httpDelete("http://keyance-api.sh.ceboshi.cn/",true);
		System.out.println(httpGet);
	}

}
