/**
* @Company weipu   
* @Title: WSGeneratorCofing.java 
* @Package org.bana.common.util.code.webservice 
* @author Liu Wenjie   
* @date 2014-11-10 上午9:44:18 
* @version V1.0   
*/ 
package org.bana.common.util.code.webservice;

import org.bana.common.util.basic.StringUtils;
import org.bana.common.util.code.impl.BaseGeneratorConfig;

/** 
 * @ClassName: WSGeneratorCofing 
 * @Description: ws客户端的一个生成文件配置 基类
 *  
 */
public abstract class WSGeneratorCofing extends BaseGeneratorConfig {
	protected static final String DEFAULT_WS_MODULE = "ws";
	protected static final String DEFAULT_WS_CLIENT_PACKAGE = "client";
	
	/** 
	* @Fields generatorClient : 是否生成客户端的配置项
	*/ 
	private boolean generatorClient = true;
	
	/** 
	* @Fields wsdlWebUrl : wsdl 的网络地址 
	*/ 
	protected String wsdlWebUrl;
	

	/** 
	* @Description: 生成webservice客户端的方法
	* @author Liu Wenjie   
	* @date 2014-11-10 上午9:45:03   
	*/ 
	protected abstract void generatorWSClient();
	
	
	/** 
	* @Description: 获取生成ws客户端对应的包名
	* @author Liu Wenjie   
	* @date 2014-11-10 上午10:25:34 
	* @return  
	*/ 
	protected String getBaseWsCodeFilePath(){
		return StringUtils.unionAsFilePath(this.projectBasePath,this.codePath);
	}
	
	
	/*================= getter and setter =============*/

	/**
	 * @Description: 属性 generatorClient 的get方法 
	 * @return generatorClient
	 */
	public boolean isGeneratorClient() {
		return generatorClient;
	}

	/**
	 * @Description: 属性 generatorClient 的set方法 
	 * @param generatorClient 
	 */
	public void setGeneratorClient(boolean generatorClient) {
		this.generatorClient = generatorClient;
	}
	
	/**
	 * @Description: 属性 wsdlWebUrl 的get方法 
	 * @return wsdlWebUrl
	 */
	public String getWsdlWebUrl() {
		return wsdlWebUrl;
	}

	/**
	 * @Description: 属性 wsdlWebUrl 的set方法 
	 * @param wsdlWebUrl 
	 */
	public void setWsdlWebUrl(String wsdlWebUrl) {
		this.wsdlWebUrl = wsdlWebUrl;
	}
}
