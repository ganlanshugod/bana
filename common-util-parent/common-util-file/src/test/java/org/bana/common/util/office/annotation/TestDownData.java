/**
* @Company 青鸟软通   
* @Title: TestData.java 
* @Package org.bana.common.util.office.impl 
* @author Liu Wenjie   
* @date 2015-7-9 下午1:01:48 
* @version V1.0   
*/ 
package org.bana.common.util.office.annotation;

import java.util.Date;
import java.util.Map;

import org.bana.common.util.office.impl.annotation.ExcelColumn;
import org.bana.common.util.office.impl.annotation.Sheet;
import org.bana.common.util.office.impl.annotation.TitleRow;

/** 
 * @ClassName: TestData 
 * @Description:
 *  
 */
@Sheet(index=2)
@TitleRow(titleIndex=6,indexName="序号")
public class TestDownData {

	@ExcelColumn(name="年龄",sort=5)
	private Integer age;
	@ExcelColumn(name="住址",sort=3)
	private String address;
	@ExcelColumn(name="日期",sort=4,style="dataFormat:yyyy-MM-dd HH:mm:ss;")
	private Date date;
	@ExcelColumn(name="姓名",sort=1)
	private String name;
	@ExcelColumn(name="性别",sort=2,colspan=1)
	private String sex ;
	
	@ExcelColumn(name="额外配置",mutiMap="额外配置",sort=6)
	private Map<String,Object> mutiMap;
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
	public Map<String, Object> getMutiMap() {
		return mutiMap;
	}
	public void setMutiMap(Map<String, Object> mutiMap) {
		this.mutiMap = mutiMap;
	}
	@Override
	public String toString() {
		return "TestData [age=" + age + ", address=" + address + ", date=" + date + ", name=" + name + ", sex=" + sex
				+ ", mutiMap=" + mutiMap + "]";
	}
	
	
}
