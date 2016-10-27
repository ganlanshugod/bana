/**
* @Company 艾美伴行   
* @Title: ReflectTest.java 
* @Package org.bana.common.util.basic 
* @author liuwenjie   
* @date 2016-9-5 上午11:52:58 
* @version V1.0   
*/ 
package org.bana.common.util.basic;

import java.lang.reflect.Field;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameter;

/** 
 * @ClassName: ReflectTest 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 *  
 */
public class ReflectTest {

	@Test
	public void testFiled(){
//		Field[] declaredFields = SubClassTest.class.getDeclaredFields();
		Field[] declaredFields = SubClassTest.class.getSuperclass().getDeclaredFields();
		System.out.println(declaredFields.length);
		for (Field field : declaredFields) {
			System.out.print(field.getName()+"-");
			System.out.print(field.getDeclaredAnnotations().length+"-");
			System.out.println(field.getAnnotation(Parameter.class));
		}
	}
	
	@Test
	public void testSubClass(){
		System.out.println(TestBaseClass.class.isAssignableFrom(SubClassTest.class));
		
	}
}

class TestBaseClass{
	@Parameter
	private String name1;
	private String name2;
	/**
	 * @Description: 属性 name1 的get方法 
	 * @return name1
	 */
	public String getName1() {
		return name1;
	}
	/**
	 * @Description: 属性 name1 的set方法 
	 * @param name1 
	 */
	public void setName1(String name1) {
		this.name1 = name1;
	}
	/**
	 * @Description: 属性 name2 的get方法 
	 * @return name2
	 */
	public String getName2() {
		return name2;
	}
	/**
	 * @Description: 属性 name2 的set方法 
	 * @param name2 
	 */
	public void setName2(String name2) {
		this.name2 = name2;
	}
	
}
class SubClassTest extends TestBaseClass{
	private String name2;
	
	private String value1;
	private String value2;
	/**
	 * @Description: 属性 name2 的get方法 
	 * @return name2
	 */
	public String getName2() {
		return name2;
	}
	/**
	 * @Description: 属性 name2 的set方法 
	 * @param name2 
	 */
	public void setName2(String name2) {
		this.name2 = name2;
	}
	/**
	 * @Description: 属性 value1 的get方法 
	 * @return value1
	 */
	public String getValue1() {
		return value1;
	}
	/**
	 * @Description: 属性 value1 的set方法 
	 * @param value1 
	 */
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	/**
	 * @Description: 属性 value2 的get方法 
	 * @return value2
	 */
	public String getValue2() {
		return value2;
	}
	/**
	 * @Description: 属性 value2 的set方法 
	 * @param value2 
	 */
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	
}
