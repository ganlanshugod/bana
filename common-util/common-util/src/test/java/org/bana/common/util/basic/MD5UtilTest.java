/**
* @Company 青鸟软通   
* @Title: MD5UtilTest.java 
* @Package org.bana.common.util.basic 
* @author Liu Wenjie   
* @date 2015-9-17 下午8:07:58 
* @version V1.0   
*/ 
package org.bana.common.util.basic;

import static org.junit.Assert.*;

import org.junit.Test;

/** 
 * @ClassName: MD5UtilTest 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 *  
 */
public class MD5UtilTest {

	/**
	 * Test method for {@link org.bana.common.util.basic.MD5Util#getMD5UseKey(java.lang.String)}.
	 */
	@Test
	public void testGetMD5UseKey() {
		System.out.println(MD5Util.getMD5UseKey("111111"));
	}
	
	@Test
	public void testGetMD5(){
		System.out.println(MD5Util.getMD5(MD5Util.getMD5("admin888")));
	}

}
