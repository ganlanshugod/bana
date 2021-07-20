/**
* @Company weipu   
* @Title: ETLConfigTest.java 
* @Package org.bana.common.util.etl.config 
* @author Liu Wenjie   
* @date 2014-11-10 下午8:21:11 
* @version V1.0   
*/ 
package org.bana.common.util.etl.config;

import org.junit.Test;

/** 
 * @ClassName: ETLConfigTest 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 *  
 */
public class ETLConfigTest {

	/**
	 * Test method for {@link org.bana.common.util.etl.config.ETLConfig#initConfig(java.lang.String)}.
	 */
	@Test
	public void testInitConfig() {
		ETLConfig config = new ETLConfig();
		config.initConfig("/etl/testConfig.xml");
		System.out.println(config);
	}
	
}
