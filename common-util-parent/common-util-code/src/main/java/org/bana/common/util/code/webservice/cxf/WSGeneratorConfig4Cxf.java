/**
* @Company weipu   
* @Title: CxfWSGeneratorConfig.java 
* @Package org.bana.common.util.code.webservice.cxf 
* @author Liu Wenjie   
* @date 2014-11-10 上午9:33:08 
* @version V1.0   
*/ 
package org.bana.common.util.code.webservice.cxf;

import org.apache.cxf.tools.wsdlto.WSDLToJava;
import org.bana.common.util.basic.StringUtils;
import org.bana.common.util.basic.URLUtil;
import org.bana.common.util.code.impl.CodeTemplateConfig;
import org.bana.common.util.code.impl.CodeTemplateConfig.GeneratorFileType;
import org.bana.common.util.code.webservice.WsGeneratorConfigWithSpring;
import org.bana.common.util.exception.BanaUtilException;

/** 
 * @ClassName: CxfWSGeneratorConfig 
 * @Description: Cxf代码生成器的客户端生成方法 
 *  
 */
public class WSGeneratorConfig4Cxf extends WsGeneratorConfigWithSpring{
	
	/** 
	* @Fields default_WS_Factory_Xml : 默认的spring ws factory 工厂配置文件 
	*/ 
	private static CodeTemplateConfig default_WS_Factory_Xml = new CodeTemplateConfig(GeneratorFileType.WS_Spring_Factory_xml, "", "code/ws/cxf/defaultWsFactory.vm");
	
	/**
	* <p>Description: 生成WS客户端</p> 
	* @author Liu Wenjie   
	* @date 2014-11-10 上午9:53:50  
	* @see org.bana.common.util.code.webservice.WSGeneratorCofing#generatorWSClient() 
	*/ 
	@Override
	protected void generatorWSClient() {
		//根据wsdlWeb生成url地址
		if(!StringUtils.isBlank(this.wsdlWebUrl)){
			int responseCode = URLUtil.isConnect(this.wsdlWebUrl);
			if(responseCode != 200){
				throw new BanaUtilException("the wsdlWebUrl " + this.wsdlWebUrl + " can not connect! the response code is " + responseCode + " . canot generate ws client!!");
			}
			String[] lsag1 = { 
					 "-p", getBaseWsPackage(),
				     "-d", getBaseWsCodeFilePath(), 
				     "-client", this.wsdlWebUrl};
			WSDLToJava.main(lsag1);
			return;
		}
		throw new BanaUtilException("wsdl is blank,can not generate ws client!!!!!");
	}
	
	
	@Override
	protected CodeTemplateConfig getDefaultWsFactoryConfig() {
		return default_WS_Factory_Xml;
	}

}
