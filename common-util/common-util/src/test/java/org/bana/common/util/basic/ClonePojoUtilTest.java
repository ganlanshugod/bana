/**
* @Company 青鸟软通   
* @Title: ClonePojoUtilTest.java 
* @Package org.bana.common.util.basic 
* @author Liu Wenjie   
* @date 2015-4-29 下午6:35:51 
* @version V1.0   
*/ 
package org.bana.common.util.basic;

import java.util.Date;

import org.bana.common.util.basic.ClonePojoUtil.Formatter;
import org.junit.Test;

/** 
 * @ClassName: ClonePojoUtilTest 
 * @Description: 怎么回事 
 *  
 */
public class ClonePojoUtilTest {
	/**
	 * Test method for {@link org.bana.common.util.basic.ClonePojoUtil#copyClassFromTo(java.lang.Object, java.lang.Class)}.
	 */
	@Test
	public void testCopyClassFromTo() {
		TestA testa = new TestA();
		testa.setToday(new Date());
		TestB testb = ClonePojoUtil.copyClassFromTo(testa, TestB.class);
		System.out.println(testb.getToday()+"");
	}
	
	public static class TestA {
		private Date today;

		/**
		 * @Description: 属性 today 的get方法 
		 * @return today
		 */
		public Date getToday() {
			return today;
		}

		/**
		 * @Description: 属性 today 的set方法 
		 * @param today 
		 */
		public void setToday(Date today) {
			this.today = today;
		}
	}
	
	public static class TestB {
		@Formatter("yyyy-MM-dd HH:mm:ss")
		private String today;

		/**
		 * @Description: 属性 today 的get方法 
		 * @return today
		 */
		public String getToday() {
			return today;
		}

		/**
		 * @Description: 属性 today 的set方法 
		 * @param today 
		 */
		public void setToday(String today) {
			this.today = today;
		}
	}


}
