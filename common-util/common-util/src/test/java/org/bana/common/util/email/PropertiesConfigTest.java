/**
* @Company 青鸟软通   
* @Title: PropertiesConfigTest.java 
* @Package org.bana.common.util.email 
* @author Liu Wenjie   
* @date 2014-10-23 下午3:22:32 
* @version V1.0   
*/ 
package org.bana.common.util.email;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Test;

/** 
 * @ClassName: PropertiesConfigTest 
 * @Description: 属性文件的获取方式
 *  
 */
public class PropertiesConfigTest {

	@Test
	public void testInitProperties() throws Exception{
		PropertiesConfiguration config = new PropertiesConfiguration();
		config.setEncoding("utf8");
		config.load("email/emailCOnfig.properties");
		
		String properties = config.getString("from.user_sign_name");
		System.out.println(properties);
	}
}
