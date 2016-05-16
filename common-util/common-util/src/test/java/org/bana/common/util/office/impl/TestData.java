/**
* @Company 青鸟软通   
* @Title: TestData.java 
* @Package org.bana.common.util.office.impl 
* @author Liu Wenjie   
* @date 2015-7-9 下午1:01:48 
* @version V1.0   
*/ 
package org.bana.common.util.office.impl;

import java.util.Date;

/** 
 * @ClassName: TestData 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 *  
 */
public class TestData {

	private String name;
	private String sex ;
	private Integer age;
	private String address;
	private Date date;
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
	 * @Description: 属性 sex 的get方法 
	 * @return sex
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * @Description: 属性 sex 的set方法 
	 * @param sex 
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * @Description: 属性 age 的get方法 
	 * @return age
	 */
	public Integer getAge() {
		return age;
	}
	/**
	 * @Description: 属性 age 的set方法 
	 * @param age 
	 */
	public void setAge(Integer age) {
		this.age = age;
	}
	/**
	 * @Description: 属性 address 的get方法 
	 * @return address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @Description: 属性 address 的set方法 
	 * @param address 
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @Description: 属性 date 的get方法 
	 * @return date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @Description: 属性 date 的set方法 
	 * @param date 
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2015-7-13 下午2:40:28 
	* @return 
	* @see java.lang.Object#toString() 
	*/ 
	@Override
	public String toString() {
		return "TestData [name=" + name + ", sex=" + sex + ", age=" + age + ", address=" + address + "]";
	}
	
}
