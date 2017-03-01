/**
* @Company 艾美伴行   
* @Title: URLUtilTest.java 
* @Package org.bana.common.util.basic 
* @author liuwenjie   
* @date 2016-8-26 上午11:33:20 
* @version V1.0   
*/ 
package org.bana.common.util.basic;

import static org.junit.Assert.*;

import org.junit.Test;

/** 
 * @ClassName: URLUtilTest 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 *  
 */
public class URLUtilTest {

	/**
	 * Test method for {@link org.bana.common.util.basic.URLUtil#removeUrlParam(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testRemoveUrlParam() {
		String url = "http://www.baidu.com/icon/todo/name?name=name1&value=value1&age=123";
		String url1 = URLUtil.removeUrlParam(url, "name");
		String url2 = URLUtil.removeUrlParam(url, "value");
		String url3 = URLUtil.removeUrlParam(url, "age");
		System.out.println(url1);
		System.out.println(url2);
		System.out.println(url3);
		System.out.println(URLUtil.removeUrlParam(url,"name","age"));
	}

	/**
	 * Test method for {@link org.bana.common.util.basic.URLUtil#replaceUrlParam(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testReplaceUrlParam() {
		String url = "http://www.baidu.com/icon/todo/name?name=name1&value=value1&age=123";
		System.out.println(URLUtil.replaceUrlParam(url, "value", "value2"));
	}

	/**
	 * Test method for {@link org.bana.common.util.basic.URLUtil#isConnect(java.lang.String)}.
	 */
//	@Test
//	public void testIsConnect() {
//		fail("Not yet implemented");
//	}

	/**
	 * Test method for {@link org.bana.common.util.basic.URLUtil#authenticatorUrl(java.lang.String, java.lang.String)}.
	 */
//	@Test
//	public void testAuthenticatorUrl() {
//		fail("Not yet implemented");
//	}

	/**
	 * Test method for {@link org.bana.common.util.basic.URLUtil#getRegStr(java.lang.String)}.
	 */
//	@Test
//	public void testGetRegStr() {
//		fail("Not yet implemented");
//	}

	/**
	 * Test method for {@link org.bana.common.util.basic.URLUtil#setREQUEST_COUNT(int)}.
	 */
//	@Test
//	public void testSetREQUEST_COUNT() {
//		fail("Not yet implemented");
//	}

}
