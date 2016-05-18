/**
* @Company 青鸟软通   
* @Title: ClonePojoUtilTest.java 
* @Package org.bana.common.util.basic 
* @author Liu Wenjie   
* @date 2015-4-29 下午6:35:51 
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
	public void test(){
		//更新点代码
		//ceshi1234576789
	}
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
//		map.put("mark", "����");
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
		 * @date 2014-3-25 ����6:53:46  
		 */
		public TestBean() {
		}
		public TestBean( String name,int age,String birthday) {
			this.name = name;
			this.age = age;
			this.birthday = birthday;
		}
		/**
		 * @Description: ���� name ��get���� 
		 * @return name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @Description: ���� name ��set���� 
		 * @param name 
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * @Description: ���� age ��get���� 
		 * @return age
		 */
		public int getAge() {
			return age;
		}
		/**
		 * @Description: ���� age ��set���� 
		 * @param age 
		 */
		public void setAge(int age) {
			this.age = age;
		}
		/**
		 * @Description: ���� birthday ��get���� 
		 * @return birthday
		 */
		public String getBirthday() {
			return birthday;
		}
		/**
		 * @Description: ���� birthday ��set���� 
		 * @param birthday 
		 */
		public void setBirthday(String birthday) {
			this.birthday = birthday;
		}
		/**
		* <p>Description: </p> 
		* @author Liu Wenjie   
		* @date 2014-3-25 ����6:55:49 
		* @return 
		* @see java.lang.Object#toString() 
		*/ 
		@Override
		public String toString() {
			return "TestBean [name=" + name + ", age=" + age + ", birthday="
					+ birthday + ", uRL=" + url  + "]";
		}
		/**
		 * @Description: ���� url ��get���� 
		 * @return url
		 */
		public String getUrl() {
			return url;
		}
		/**
		 * @Description: ���� url ��set���� 
		 * @param url 
		 */
		public void setUrl(String url) {
			this.url = url;
		}
		
    }
}
