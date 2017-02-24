/**
* @Company 青鸟软通   
* @Title: SystemConfigurationTest.java 
* @Package org.apache.commons.configuration 
* @author liuwenjie   
* @date 2016-2-25 下午12:51:40 
* @version V1.0   
*/ 
package org.apache.commons.configuration;

import org.apache.commons.lang.SystemUtils;
import org.junit.Test;

/** 
 * @ClassName: SystemConfigurationTest 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 *  
 */
public class SystemConfigurationTest {

	@Test
	public void testGetProerty(){
		System.out.println(SystemUtils.JAVA_HOME);
		System.out.println(SystemUtils.FILE_ENCODING);
		System.out.println(SystemUtils.IS_OS_LINUX);
		System.out.println(SystemUtils.IS_OS_WINDOWS);
		System.out.println(SystemUtils.IS_OS_WINDOWS_VISTA);
	}
}
