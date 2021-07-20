/**
 * @Company weipu   
 * @Title: ValidataXMLTest.java 
 * @Package org.bana.common.util.xml 
 * @author Liu Wenjie   
 * @date 2015-7-2 下午3:48:24 
 * @version V1.0   
 */
package org.bana.common.util.xml;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.SAXValidator;
import org.dom4j.io.XMLWriter;
import org.dom4j.util.XMLErrorHandler;
import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * @ClassName: ValidataXMLTest
 * @Description: 测试使用schama校验xml的方法
 * 
 */
public class ValidataXMLTest {
//	private String xsdFileName = "/xml/test/noteschema2.xsd";
//	private String xmlFileName = "/xml/test/note2.xml";
	private String xsdFileName = "/office/excelConfig.xsd";
	private String xmlFileName = "/office/excelConfig.xml";
	
	private SAXValidator createValidator() throws ParserConfigurationException, SAXException{
		// 创建默认的XML错误处理器
		XMLErrorHandler errorHandler = new XMLErrorHandler();
		// 获取基于 SAX 的解析器的实例
		SAXParserFactory factory = SAXParserFactory.newInstance();
		// 解析器在解析时验证 XML 内容。
		factory.setValidating(true);
		// 指定由此代码生成的解析器将提供对 XML 名称空间的支持。
		factory.setNamespaceAware(true);
		// 使用当前配置的工厂参数创建 SAXParser 的一个新实例。
		SAXParser parser = factory.newSAXParser();
		parser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
//				ValidataXMLTest.class.getResource(xsdFileName).getPath();
		parser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaSource", ValidataXMLTest.class.getResource(xsdFileName).getPath());
		// 创建一个SAXValidator校验工具，并设置校验工具的属性
		SAXValidator validator = new SAXValidator(parser.getXMLReader());
		// 设置校验工具的错误处理器，当发生错误时，可以从处理器对象中得到错误信息。
		validator.setErrorHandler(errorHandler);
		return validator;
	}
	
	public Document getDocument() throws DocumentException, SAXException{
		SAXReader xmlReader = new SAXReader();
//		Map<String,String> schamaMap = new HashMap<String, String>();
//		schamaMap.put("default", "http://www.w3.org/2001/XMLSchema");
//		xmlReader.getDocumentFactory().setXPathNamespaceURIs(schamaMap);
//		xmlReader.setFeature("http://java.sun.com/xml/jaxp/properties/schemaSource", true);
		xmlReader.setFeature("http://apache.org/xml/features/validation/schema", true);
		  
		xmlReader.setProperty("http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation",
				ValidataXMLTest.class.getResource(xsdFileName).getPath());
		xmlReader.setFeature("http://xml.org/sax/features/validation", true);
//		xmlReader.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
//		xmlReader.setProperty("http://java.sun.com/xml/jaxp/properties/schemaSource",
//				ValidataXMLTest.class.getResource(xsdFileName).getPath());
		xmlReader.setFeature("http://apache.org/xml/features/validation/schema-full-checking",true);
		// 获取要校验xml文档实例
		Document xmlDocument = xmlReader.read(ValidataXMLTest.class.getResourceAsStream(xmlFileName));
		// 设置 XMLReader 的基础实现中的特定属性。核心功能和属性列表可以在
		// [url]http://sax.sourceforge.net/?selected=get-set[/url] 中找到。
		return xmlDocument;
	}

	@Test
	@Ignore
	public void validateXMLByXSD() throws Exception{
		SAXValidator validator = createValidator();
		// 创建一个读取工具
		Document xmlDocument = getDocument();
		// 校验
		validator.validate(xmlDocument);

		XMLWriter writer = new XMLWriter(OutputFormat.createPrettyPrint());
		
		XMLErrorHandler errorHandler = (XMLErrorHandler)validator.getErrorHandler();
		// 如果错误信息不为空，说明校验失败，打印错误信息
		if (errorHandler.getErrors().hasContent()) {
			System.out.println("XML文件通过XSD文件校验失败！");
			writer.write(errorHandler.getErrors());
		} else {
			System.out.println("Good! XML文件通过XSD文件校验成功！");
		}
//		Element root = (Element)xmlDocument.getRootElement();
//		System.out.println(xmlDocument.selectSingleNode("/default:note/default:to").getStringValue());
//		System.out.println(xmlDocument.selectSingleNode("/default:note/default:from").getStringValue());
//		System.out.println(xmlDocument.selectSingleNode("/note/to").getStringValue());
//		System.out.println(xmlDocument.selectSingleNode("/note/from").getStringValue());
		System.out.println(xmlDocument.selectSingleNode("/excel[@name]").getStringValue());
		System.out.println(xmlDocument.selectSingleNode("/excel/sheet").getStringValue());
		
	}
}
