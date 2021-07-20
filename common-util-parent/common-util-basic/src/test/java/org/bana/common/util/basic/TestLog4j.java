/**
* @Company weipu
* @Title: TestLog4j.java 
* @Package org.bana.common.util.basic 
* @author liuwenjie   
* @date Sep 25, 2020 3:23:17 PM 
* @version V1.0   
*/ 
package org.bana.common.util.basic;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
* @ClassName: TestLog4j 
* @Description: 
* @author liuwenjie   
*/
public class TestLog4j {

	private static final Logger LOG = LoggerFactory.getLogger(TestLog4j.class);
	
	@Test
	public void testPrint() {
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			System.out.println(String.valueOf(i));
		}
		long end1 = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			LOG.info(String.valueOf(i));
		}
		long end2 = System.currentTimeMillis();
		
		System.out.println("system.out 执行了" + (end1-begin));
		System.out.println("LOG 执行了" + (end2-end1));
	}
}
