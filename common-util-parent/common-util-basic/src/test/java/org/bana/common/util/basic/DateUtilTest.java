/**
* @Company 青鸟软通   
* @Title: DateUtilTest.java 
* @Package org.bana.common.util.date 
* @author liuwenjie   
* @date 2016-4-6 上午9:53:37 
* @version V1.0   
*/ 
package org.bana.common.util.basic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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
		System.out.println(DateUtil.formateToDate("2017-03-19").getTime());
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
	
	@Test
	public void testString5(){
		System.out.println(DateUtil.formateToDate("2016年08月05日 11时35分", "yyyy年MM月dd日 HH时mm分"));
	}
	
	@Test
	public void testmain() throws ParseException {
		Date date = new Date();
		System.out.println(date);
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-mm-dd", Locale.CHINA);
		Date parse2 = simpleDateFormat2.parse("2021-03-31");
		System.out.println(parse2);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
		Date parse = simpleDateFormat.parse("2021-03-31");
		System.out.println(parse);
		Calendar instance = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		instance.setTime(new Date());
		System.out.println(instance.getTime());
		System.out.println(Locale.getDefault());
		System.out.println(TimeZone.getDefault());
	}
}
