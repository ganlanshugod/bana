/**
* @Company 青鸟软通   
* @Title: VelocityTest.java 
* @Package org.bana.common.util.velocity 
* @author Liu Wenjie   
* @date 2014-8-26 上午10:27:00 
* @version V1.0   
*/ 
package org.bana.common.util.velocity;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.bana.common.util.code.impl.FileConfig;
import org.junit.Test;

/** 
 * @ClassName: VelocityTest 
 * @Description: 测试模板技术Velocity的使用
 *  
 */
public class VelocityTest {
	
	@Test
	public void testCommon() throws Exception{
		VelocityEngine ve = new VelocityEngine();  
		ve.setProperty(Velocity.RESOURCE_LOADER, "class");  
		ve.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader"); // 设置类路径加载模板  
		ve.setProperty(Velocity.INPUT_ENCODING, "utf-8");// 设置输入字符集  
		ve.setProperty(Velocity.OUTPUT_ENCODING, "utf-8");// 设置输出字符集  
		ve.init();// 初始化模板引擎  
		Template t = ve.getTemplate("/email/templateDemo.vm");// 加载模板，相对于classpath路径  
		VelocityContext context = new VelocityContext();  
		  
		HashMap<String, Object> result = new HashMap<String, Object>();  
		result.put("Name", "模板");  
		result.put("key", "语言");  
		result.put("applyFormId", "applyFormId");  
		
		List<String> list = new ArrayList<String>();
		list.add("test1");
		list.add("test2");
		list.add("test3");
		result.put("list", list);
		context.put("map", result);  
		
		FileConfig fileConfig = new FileConfig();
		fileConfig.setFileContent("helloe2");
		context.put("fileConfig", fileConfig);
		
		StringWriter writer = new StringWriter();  
		t.merge(context, writer); // 转换  
		System.out.println(writer.toString());  //形成最终结果  
	}
}
