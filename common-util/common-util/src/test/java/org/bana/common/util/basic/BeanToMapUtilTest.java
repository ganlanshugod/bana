/**
* @Company 青鸟软通   
* @Title: BeanToMapUtilTest.java 
* @Package com.haier.common.util 
* @author Liu Wenjie   
* @date 2014-3-25 下午6:57:54 
* @version V1.0   
*/ 
package org.bana.common.util.basic;


import java.beans.IntrospectionException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/** 
 * @ClassName: BeanToMapUtilTest 
 * @Description:  
 *  
 */
public class BeanToMapUtilTest {

	/**
	 * Test method for {@link com.haier.common.util.BeanToMapUtil#convertMap(java.lang.Class, java.util.Map)}.
	 * @throws Exception 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 */
	@Test
	public void testConvertMap() throws IntrospectionException, IllegalAccessException, Exception {
		Map<String, Object> map = BeanToMapUtil.convertBean(new TestBean("liuwenjie",25,"19870809"));
		System.out.println(map.entrySet());
	}

	/**
	 * Test method for {@link com.haier.common.util.BeanToMapUtil#convertBean(java.lang.Object)}.
	 * @throws Exception
	 */
	@Test
	public void testConvertBean() throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("name", "liuwenjie");
		map.put("age", 28);
//		map.put("birthday", "19870809");
//		map.put("mark", "额外");
		Object bean = BeanToMapUtil.convertMap(TestBean.class, map);
		System.out.println(bean);
	}

	public static class TestBean{
    	private String name;
    	private int age;
    	private String birthday;
    	private String url;
    	/** 
		 * <p>Description: </p> 
		 * @author Liu Wenjie   
		 * @date 2014-3-25 下午6:53:46  
		 */
		public TestBean() {
		}
		public TestBean( String name,int age,String birthday) {
			this.name = name;
			this.age = age;
			this.birthday = birthday;
		}
		/**
		 * @Description: 属性 name 的get方法 
		 * @return name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @Description: 属性 name 的set方法 
		 * @param name 
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * @Description: 属性 age 的get方法 
		 * @return age
		 */
		public int getAge() {
			return age;
		}
		/**
		 * @Description: 属性 age 的set方法 
		 * @param age 
		 */
		public void setAge(int age) {
			this.age = age;
		}
		/**
		 * @Description: 属性 birthday 的get方法 
		 * @return birthday
		 */
		public String getBirthday() {
			return birthday;
		}
		/**
		 * @Description: 属性 birthday 的set方法 
		 * @param birthday 
		 */
		public void setBirthday(String birthday) {
			this.birthday = birthday;
		}
		/**
		* <p>Description: </p> 
		* @author Liu Wenjie   
		* @date 2014-3-25 下午6:55:49 
		* @return 
		* @see java.lang.Object#toString() 
		*/ 
		@Override
		public String toString() {
			return "TestBean [name=" + name + ", age=" + age + ", birthday="
					+ birthday + ", uRL=" + url  + "]";
		}
		/**
		 * @Description: 属性 url 的get方法 
		 * @return url
		 */
		public String getUrl() {
			return url;
		}
		/**
		 * @Description: 属性 url 的set方法 
		 * @param url 
		 */
		public void setUrl(String url) {
			this.url = url;
		}
		
    }
}
