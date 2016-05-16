/**
* @Company 青鸟软通   
* @Title: TestServiceImpl.java 
* @Package org.bana.common.util.proxy.cglib 
* @author Liu Wenjie   
* @date 2015-1-23 下午3:03:00 
* @version V1.0   
*/ 
package org.bana.common.util.proxy.cglib;

/** 
 * @ClassName: TestServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 *  
 */
public class TestServiceImpl implements TestServiceInterface {

	/**
	 * <p>Description: </p> 
	 * @author Liu Wenjie   
	 * @date 2015-1-23 下午3:03:00 
	 * @param name
	 * @return 
	 * @see org.bana.common.util.proxy.cglib.TestServiceInterface#testSay(java.lang.String) 
	 */
	@Override
	public String testSay(String name) {
		System.out.println("TestServiceImpl test say ........");
		return "say " + name;
	}

}
