/**
* @Company ������ͨ   
* @Title: PropertiesConfigTest.java 
* @Package org.bana.common.util.email 
* @author Liu Wenjie   
* @date 2014-10-23 ����3:22:32 
* @version V1.0   
*/ 
package org.bana.common.util.email;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.reloading.PeriodicReloadingTrigger;
import org.junit.Test;

/** 
 * @ClassName: PropertiesConfigTest 
 * @Description: �����ļ��Ļ�ȡ��ʽ
 *  
 */
public class PropertiesConfigTest {

	@Test
	public void testInitProperties() throws Exception{
		Configurations configs = new Configurations();
        // setDefaultEncoding是个静态方法,用于设置指定类型(class)所有对象的编码方式。
        // 本例中是PropertiesConfiguration,要在PropertiesConfiguration实例创建之前调用。
        FileBasedConfigurationBuilder.setDefaultEncoding(PropertiesConfiguration.class, "UTF-8");
        PropertiesConfiguration config = configs.properties(this.getClass().getClassLoader().getResource("email/emailCOnfig.properties"));
		
		String properties = config.getString("from.user_sign_name");
		System.out.println(properties);
	}
	
	
	@Test
	public void testInitProperties2() throws IOException, ConfigurationException{
		 String filePath = "email/emailCOnfig.properties";
		 Parameters params = new Parameters();
         File propertiesFile = new File(URLDecoder.decode(filePath, "UTF_8"));

         ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration> reloadbuilder = new ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration>(
                 PropertiesConfiguration.class)
                         .configure(params.fileBased().setEncoding("UTF_8").setFile(propertiesFile));
         reloadbuilder.setAutoSave(true);
         PeriodicReloadingTrigger trigger = new PeriodicReloadingTrigger(reloadbuilder.getReloadingController(),
                 null, 1, TimeUnit.MINUTES);
         trigger.start();

         PropertiesConfiguration config = (PropertiesConfiguration) reloadbuilder.getConfiguration();
         String properties = config.getString("from.user_sign_name");
 		 System.out.println(properties);
	}
}
