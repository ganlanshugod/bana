/**
 * @Company 青鸟软通   
 * @Title: XmlConfigurationTest.java 
 * @Package org.apache.commons.configuration 
 * @author liuwenjie   
 * @date 2016-2-25 上午10:38:06 
 * @version V1.0   
 */
package org.apache.commons.configuration;

import java.net.URL;
import java.util.List;

import org.apache.commons.configuration.resolver.CatalogResolver;
import org.junit.Test;

/**
 * @ClassName: XmlConfigurationTest
 * @Description: 读取xml配置的方法接口
 * 
 */
public class XmlConfigurationTest {

	@Test
	public void testGetConfig() throws ConfigurationException {
		String file = "apache/configuration/AppConfig.xml";
		XMLConfiguration config = new XMLConfiguration(file);
		System.out.println("成功加载:" + file);
		String btime = config.getString("应用程序配置参数.考勤时间.上班时间");
		double basicm = config.getDouble("应用程序配置参数.个人所得税起征额");
		//中括号来判断属性和子元素
//		String key1 =  config.getString("应用程序配置参数.properties.property(0)[@value]");
		//选择节点中的属性使用｛｝
		String key1 =  config.getString("应用程序配置参数.properties.property{@name='name1'}");
//		String key1 =  config.getString("应用程序配置参数.properties.property{0}@name");
		SubnodeConfiguration configurationAt = config.configurationAt("应用程序配置参数.properties.property(0)");
		
		System.out.println(configurationAt.getString("[@value]"));
		//循环某个节点的方法
		List<HierarchicalConfiguration> list = config.configurationsAt("应用程序配置参数.properties.property");
		
		for (HierarchicalConfiguration subConfig : list) {
			System.out.print(subConfig.getString("[@name]"));
			System.out.println("=" + subConfig.getString("[@value]"));
		}
		System.out.println("上班时间：" + btime);
		System.out.println("个人所得税起征额:" + basicm + "元");
	}
	
	/** 
	* @Description: 测试需要进行校验的xml文档
	* @author liuwenjie   
	 * @throws ConfigurationException 
	* @date 2016-2-25 上午11:48:05   
	*/ 
	@Test
	public void  testValidateXml() throws ConfigurationException{
		String validateXml = "/office/excelConfig.xsd";
		CatalogResolver resolver = new CatalogResolver();
//		resolver.setCatalogFiles(validateXml + " http://www.bana.com/common-util/excelConfig.xsd");
		String targetXml = "office/excelConfig.xml";
		XMLConfiguration config = new XMLConfiguration();
		config.setEntityResolver(resolver);
//		config.registerEntityId("http://www.bana.com/excelConfig", getClass().getResource(validateXml));
		config.setFileName(targetXml);
		config.setSchemaValidation(true);
		// This will throw a ConfigurationException if the XML document does not
		// conform to its DTD.
		config.load();
		System.out.println(config.getString("sheet.row(0)[@type]"));
	}
}
