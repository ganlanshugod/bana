/**
* @Company 青鸟软通   
* @Title: DateUtilTest.java 
* @Package org.bana.common.util.date 
* @author liuwenjie   
* @date 2016-4-6 上午9:53:37 
* @version V1.0   
*/ 
package org.bana.common.util.date;

import java.util.Date;

import org.bana.common.util.basic.DateUtil;
import org.junit.Test;

/** 
 * @ClassName: DateUtilTest 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 *  
 */
public class DateUtilTest {

	@Test
	public void testString(){
		System.out.println(DateUtil.formateToDate("2016-04-06 20:00:00").getTime());
	}
	
	@Test
	public void testString3(){
		System.out.println(DateUtil.formateToDate("2016-04-27 05:25 ").getTime());
	}
	
	@Test
	public void testString2(){
		System.out.println(DateUtil.toString(new Date(1450281600000L),"yyyy-MM-dd HH:mm:ss"));
	}
	
	@Test
	public void testString4(){
		System.out.println(new Date(1463573660687L));
	}
}
