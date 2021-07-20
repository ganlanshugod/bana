/**
* @Company weipu   
* @Title: WSGeneratorConfig4Axis1.java 
* @Package org.bana.common.util.code.webservice.axis1 
* @author Liu Wenjie   
* @date 2015-1-16 下午1:32:45 
* @version V1.0   
*/ 
package org.bana.common.util.code.webservice.axis1;

import org.apache.axis.wsdl.WSDL2Java;
import org.bana.common.util.basic.StringUtils;
import org.bana.common.util.basic.URLUtil;
import org.bana.common.util.code.impl.CodeTemplateConfig;
import org.bana.common.util.code.impl.CodeTemplateConfig.GeneratorFileType;
import org.bana.common.util.code.webservice.WsGeneratorConfigWithSpring;
import org.bana.common.util.exception.BanaUtilException;

/** 
 * @ClassName: WSGeneratorConfig4Axis1 
 * @Description: 使用Axis 生成客户端访问webservice接口的方法
 *  
 */
public class WSGeneratorConfig4Axis1 extends WsGeneratorConfigWithSpring{
	/** 
	* @Fields default_WS_Factory_Xml : 默认的spring ws factory 工厂配置文件 
	*/ 
	private static CodeTemplateConfig default_WS_Factory_Xml = new CodeTemplateConfig(GeneratorFileType.WS_Spring_Factory_xml, "", "code/ws/axis/defaultWsFactory.vm");
	
	/**
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2015-1-16 下午1:33:29  
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
				     "-o", getBaseWsCodeFilePath(), 
				     "-u", this.wsdlWebUrl};
			WSDL2Java.main(lsag1);
			return;
		}
		throw new BanaUtilException("wsdl is blank,can not generate ws client!!!!!");
	}

	/**
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2015-1-16 下午5:20:41 
	* @return 
	* @see org.bana.common.util.code.webservice.WsGeneratorConfigWithSpring#getDefaultWsFactoryConfig() 
	*/ 
	@Override
	protected CodeTemplateConfig getDefaultWsFactoryConfig() {
		return default_WS_Factory_Xml;
	}
	
	
	
	
}
