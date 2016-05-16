/**
* @Company 青鸟软通   
* @Title: MailVelocityEngineFactory.java 
* @Package org.bana.common.util.email 
* @author Liu Wenjie   
* @date 2014-10-21 下午3:44:00 
* @version V1.0   
*/ 
package org.bana.common.util.velocity;

import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.bana.common.util.exception.BanaUtilException;

/** 
 * @ClassName: MailVelocityEngineFactory 
 * @Description: 产生邮件内容的模板工厂类
 *  
 */
public class SimpleVelocityEngineFactory {
	/*================常量属性 ==============*/
	private static final String RESOURCE_LOADER = "class";
	private static final String RESOURCE_LOADER_CLASS = "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader";
	
	/*============= 可定义的几个属性参数 ===============*/
	private static String inputEncoding = "utf-8";
	private static String outputEncoding = "utf-8";
	private static int maxSize = 10;
	
	private static int currentValue = 0; 
	
	private static VelocityEngine[] velocityEngineArray = new VelocityEngine[maxSize];
	
	
	/**
	* @Description: 获取模板引擎的通用方法
	* @author Liu Wenjie   
	* @date 2014-10-21 下午5:28:10 
	* @return
	 */
	public static VelocityEngine getVelocityEngine(){
		VelocityEngine ve = velocityEngineArray[currentValue];
		if(ve == null){
			ve = createVelocityEngine();
			velocityEngineArray[currentValue] = ve;
		}
		return ve;
	}
	
	/**
	* @Description: 创建一个可用的模板引擎来使用
	* @author Liu Wenjie   
	* @date 2014-10-21 下午5:23:42 
	* @return
	 */
	private static VelocityEngine createVelocityEngine(){
		VelocityEngine ve = new VelocityEngine();  
		ve.setProperty(Velocity.RESOURCE_LOADER, RESOURCE_LOADER);  
		ve.setProperty("class.resource.loader.class", RESOURCE_LOADER_CLASS); // 设置类路径加载模板  
		ve.setProperty(Velocity.INPUT_ENCODING, inputEncoding);// 设置输入字符集  
		ve.setProperty(Velocity.OUTPUT_ENCODING, outputEncoding);// 设置输出字符集  
		try {
			ve.init();// 初始化模板引擎  
			return ve;
		} catch (Exception e) {
			throw new BanaUtilException("初始化模板引擎失败",e);
		}
	}
	
	
	/** 
	* @Description: 根据指定文件加载模板文件
	* @author Liu Wenjie   
	* @date 2014-10-21 下午5:33:07 
	* @param templatePath
	* @return  
	*/ 
	public static Template getTemplate(String templatePath){
		try {
			return getVelocityEngine().getTemplate(templatePath);
		} catch (Exception e) {
			throw new BanaUtilException("根据地址   ==" + templatePath + "==获取模板失败",e);
		}
	}

	
	/*==================getter and setter =================*/
	/**
	 * @Description: 属性 maxSize 的set方法 
	 * @param maxSize 
	 */
	public static void setMaxSize(int maxSize) {
		SimpleVelocityEngineFactory.maxSize = maxSize;
	}

	/**
	 * @Description: 属性 currentValue 的set方法 
	 * @param currentValue 
	 */
	public static void setCurrentValue(int currentValue) {
		SimpleVelocityEngineFactory.currentValue = currentValue;
	} 
	
}
