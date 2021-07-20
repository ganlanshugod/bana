/**
* @Company weipu   
* @Title: TestServiceImpl2.java 
* @Package org.bana.common.util.proxy.cglib 
* @author Liu Wenjie   
* @date 2015-1-23 下午3:12:55 
* @version V1.0   
*/ 
package org.bana.common.util.proxy.cglib;

/** 
 * @ClassName: TestServiceImpl2 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 *  
 */
public class TestServiceImpl2 extends TestAbstractService{
	
	private String value ;
	private Integer num;
	
	public TestServiceImpl2(String value , Integer num){
		this.value = value;
		this.num = num;
	}

	/**
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2015-1-23 下午3:13:12 
	* @param name
	* @return 
	* @see org.bana.common.util.proxy.cglib.TestAbstractService#testSay2(java.lang.String) 
	*/ 
	@Override
	public String testSay2(String name) {
		System.out.println("TestServiceImpl2 test say2 ");
		return "hello2" + name + value + String.valueOf(num);
	}

}
