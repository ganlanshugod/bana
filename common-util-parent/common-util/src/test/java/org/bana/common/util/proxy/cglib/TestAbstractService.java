/**
* @Company 青鸟软通   
* @Title: TestAbstractService.java 
* @Package org.bana.common.util.proxy.cglib 
* @author Liu Wenjie   
* @date 2015-1-23 下午3:10:56 
* @version V1.0   
*/ 
package org.bana.common.util.proxy.cglib;

/** 
 * @ClassName: TestAbstractService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 *  
 */
public abstract class TestAbstractService implements TestServiceInterface {

	/**
	 * <p>Description: </p> 
	 * @author Liu Wenjie   
	 * @date 2015-1-23 下午3:10:56 
	 * @param name
	 * @return 
	 * @see org.bana.common.util.proxy.cglib.TestServiceInterface#testSay(java.lang.String) 
	 */
	@Override
	public String testSay(String name) {
		System.out.println("TestAbstractService .. test say ...");
		return "hello " + name;
	}
	
	public abstract String testSay2(String name);

}
