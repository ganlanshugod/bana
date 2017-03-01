/**
* @Company 青鸟软通   
* @Title: TestExecute.java 
* @Package org.bana.common.util.proxy.cglib 
* @author Liu Wenjie   
* @date 2015-1-23 下午3:07:35 
* @version V1.0   
*/ 
package org.bana.common.util.proxy.cglib;

import org.junit.Test;

/** 
 * @ClassName: TestExecute 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 *  
 */
public class TestExecute {

	@Test
	public void test1(){
		System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
		TestServiceInterface test = new TestServiceImpl();
		TestFacadeProxy proxy = new TestFacadeProxy();
		TestServiceInterface instance = proxy.getInstance(test,null,null);
		String testSay = instance.testSay("liuwenjie");
		System.out.println(testSay);
	}
	
	@Test
	public void test2(){
		TestAbstractService test = new TestServiceImpl2("22",12222);
		
		TestFacadeProxy proxy = new TestFacadeProxy();
		 Class[] classes = new Class[2];
	        classes[1] = Integer.class;
	        classes[0] = String.class;
	        
	        Object[] obj = new Object[2];
	        obj[1] = 23;
	        obj[0] = "3432";
		TestAbstractService instance = proxy.getInstance(test,classes,obj);
		String testSay = instance.testSay2("liuwenjie");
		System.out.println(testSay);
	}
	
	@Test
	public void test3(){
		System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
		TestServiceInterface test = new TestServiceImpl();
		TestFacadeProxy2 proxy = new TestFacadeProxy2();
		TestServiceInterface instance = (TestServiceInterface)proxy.bind(test);
		String testSay = instance.testSay("liuwenjie");
		System.out.println(testSay);
	}
}
