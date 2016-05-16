/**
* @Company 青鸟软通   
* @Title: BeanXmlUtil.java 
* @Package org.bana.common.util.basic 
* @author Liu Wenjie   
* @date 2015-9-17 下午6:48:57 
* @version V1.0   
*/ 
package org.bana.common.util.basic;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.bana.common.util.exception.BanaUtilException;

/** 
 * @ClassName: BeanXmlUtil 
 * @Description: 對象與xml互相轉化的方法
 *  
 */
public class BeanXmlUtil {

	/** 
	* @Description: 包含@XmlRootElment注解的对象转化为xml字符串
	* @author Liu Wenjie   
	* @date 2015-9-17 下午6:51:12 
	* @param object
	* @return  
	*/ 
	public static String beanToXml(Object object){
		try {
			JAXBContext context = JAXBContext.newInstance(object.getClass());
			Marshaller marshaller = context.createMarshaller();  
	        StringWriter writer = new StringWriter();
	        marshaller.marshal(object, writer);  
	        return writer.toString();
		} catch (JAXBException e) {
			throw new BanaUtilException("對象" + object + "转化为xml字符串是出现问题",e);
		}  
	}
	
	/** 
	* @Description: xml 转化为 指定类型的对象
	* @author Liu Wenjie   
	* @date 2015-9-17 下午7:14:48 
	* @param xmlStr
	* @param cls
	* @return  
	*/ 
	@SuppressWarnings("unchecked")
	public static <T>T xmlToBean(String xmlStr,Class<T> cls){
		try {
			JAXBContext context = JAXBContext.newInstance(cls);
			Unmarshaller unmarshaller = context.createUnmarshaller();  
	        return (T)unmarshaller.unmarshal(new StringReader(xmlStr));  
		} catch (JAXBException e) {
			throw new BanaUtilException("xmlStr " + xmlStr + "转化为对象时出现问题",e);
		}  
	}
}
