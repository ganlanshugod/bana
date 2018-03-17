/**
* @Company ������ͨ   
* @Title: VelocityTest.java 
* @Package org.bana.common.util.velocity 
* @author Liu Wenjie   
* @date 2014-8-26 ����10:27:00 
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
 * @Description: ����ģ�弼��Velocity��ʹ��
 *  
 */
public class VelocityTest {
	
	@Test
	public void testCommon() throws Exception{
		String templatePath = "/email/templateDemo.vm";
		templatePath = "code/jpa/mysql/defaultEntity.vm";
		VelocityEngine ve = new VelocityEngine();  
		ve.setProperty(Velocity.RESOURCE_LOADER, "class");  
		ve.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader"); // ������·������ģ��  
		ve.setProperty(Velocity.INPUT_ENCODING, "utf-8");// ���������ַ�  
//		ve.setProperty(Velocity.OUTPUT_ENCODING, "utf-8");// ��������ַ�  
		ve.init();// ��ʼ��ģ������  
		Template t = ve.getTemplate(templatePath);// ����ģ�壬�����classpath·��  
		VelocityContext context = new VelocityContext();  
		  
		HashMap<String, Object> result = new HashMap<String, Object>();  
		result.put("Name", "ģ��");  
		result.put("key", "����");  
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
		t.merge(context, writer); // ת��  
		System.out.println(writer.toString());  //�γ����ս��  
	}
}
