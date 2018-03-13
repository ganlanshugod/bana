/**
 * @Company 青鸟软通   
 * @Title: XmlLoader.java 
 * @Package com.jbinfo.common.util 
 * @author Liu Wenjie   
 * @date 2013-11-25 下午4:21:00 
 * @version V1.0   
 */
package org.bana.common.util.basic;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.bana.common.util.exception.BanaUtilException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.SAXValidator;
import org.dom4j.io.XMLWriter;
import org.dom4j.util.XMLErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * @ClassName: XmlLoader
 * @Description: 读取Xml的工具类
 * 
 */
public class XmlLoader {
	private static final Logger LOG = LoggerFactory.getLogger(XmlLoader.class);

	/**
	 * @Description: 根据指定的文件路径获取Xml对象
	 * @author Liu Wenjie
	 * @date 2013-11-25 下午4:24:33
	 * @return
	 */
	public static Document getDocument(String filePath) {
		if(StringUtils.isBlank(filePath)){
			throw new BanaUtilException("param filePath" + filePath + " is blank!!!!!!");
		}
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(XmlLoader.class.getResourceAsStream(filePath));
			return doc;
		} catch (DocumentException e) {
			LOG.error("读取指定的xml错误" + filePath,e);
			throw new BanaUtilException("读取指定的xml错误 " + filePath,e);
		}
	}
	
	/** 
	* @Description: 根据指定的xsd文件去验证并加载指定的xml文件
	* @author Liu Wenjie   
	* @date 2015-7-7 下午3:31:47 
	* @param filePath
	* @param xsdFile
	* @return  
	 * @throws Throwable 
	*/ 
	public static Document getDocument(String filePath,String xsdFile){
		SAXReader xmlReader = new SAXReader();
		try {
			// 获取要校验xml文档实例
			xmlReader.setFeature("http://apache.org/xml/features/validation/schema", true);
			xmlReader.setProperty("http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation",xsdFile);
			xmlReader.setFeature("http://xml.org/sax/features/validation", true);
			xmlReader.setFeature("http://apache.org/xml/features/validation/schema-full-checking",true);
			Document xmlDocument = xmlReader.read(XmlLoader.class.getResourceAsStream(filePath));
			//创建一个校验器
			SAXValidator validator = createValidator(xsdFile);
			// 校验
			validator.validate(xmlDocument);

			XMLWriter writer = new XMLWriter(OutputFormat.createPrettyPrint());
			XMLErrorHandler errorHandler = (XMLErrorHandler)validator.getErrorHandler();
			// 如果错误信息不为空，说明校验失败，打印错误信息
			if (errorHandler.getErrors().hasContent()) {
				LOG.error(("XML文件通过XSD文件校验失败！"));
				writer.write(errorHandler.getErrors());
				throw new BanaUtilException("XML文件通过XSD文件校验失败！" + errorHandler.getErrors().content());
			} else {
				LOG.info("Good! XML文件通过XSD文件校验成功！");
				return xmlDocument;
			}
		} catch (Exception e) {
			LOG.error("加载xml配置文件时出现异常，xmlFile = " +filePath + ",xsdFile + " + xsdFile,e);
			throw new BanaUtilException("加载xml配置文件时出现异常，xmlFile = " +filePath + ",xsdFile + " + xsdFile,e);
		}
		
	}

	
	/** 
	* @Description: 创建一个xml文档的校验器
	* @author Liu Wenjie   
	* @date 2015-7-7 下午3:34:13 
	* @return
	* @throws ParserConfigurationException
	* @throws SAXException  
	*/ 
	private static SAXValidator createValidator(String xsdFile){
		// 创建默认的XML错误处理器
		XMLErrorHandler errorHandler = new XMLErrorHandler();
		// 获取基于 SAX 的解析器的实例
		SAXParserFactory factory = SAXParserFactory.newInstance();
		// 解析器在解析时验证 XML 内容。
		factory.setValidating(true);
		// 指定由此代码生成的解析器将提供对 XML 名称空间的支持。
		factory.setNamespaceAware(true);
		try {
			// 使用当前配置的工厂参数创建 SAXParser 的一个新实例。
			SAXParser parser = factory.newSAXParser();
			parser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
			parser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaSource", XmlLoader.class.getResource(xsdFile).getPath());
			// 创建一个SAXValidator校验工具，并设置校验工具的属性
			SAXValidator validator = new SAXValidator(parser.getXMLReader());
			// 设置校验工具的错误处理器，当发生错误时，可以从处理器对象中得到错误信息。
			validator.setErrorHandler(errorHandler);
			return validator;
		} catch (Exception e) {
			LOG.error("创建xml解析器时出现错误,xsdFile =" + xsdFile,e);
			throw new BanaUtilException("创建xml解析器时出现错误,xsdFile =" + xsdFile,e);
		}
	}
}
