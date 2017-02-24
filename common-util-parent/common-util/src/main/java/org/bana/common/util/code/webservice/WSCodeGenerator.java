/**
* @Company 青鸟软通   
* @Title: WSCodeGenerator.java 
* @Package org.bana.common.util.code.webservice.cxf 
* @author Liu Wenjie   
* @date 2014-11-10 上午9:26:08 
* @version V1.0   
*/ 
package org.bana.common.util.code.webservice;

import org.bana.common.util.code.impl.SimpleCodeGenerator;
import org.bana.common.util.exception.BanaUtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @ClassName: WSCodeGenerator 
 * @Description: webservice的代码生成器
 *  
 */
public class WSCodeGenerator extends SimpleCodeGenerator {
	
	private static final Logger LOG = LoggerFactory.getLogger(WSCodeGenerator.class);

	/**
	* <p>Description: 在simpleCodeGenerator的基础上，增加生成ws客户端的方法</p> 
	* @author Liu Wenjie   
	* @date 2014-11-10 上午9:26:59  
	* @see org.bana.common.util.code.impl.SimpleCodeGenerator#generate() 
	*/ 
	public void generate(){
		//生成别的代码和配置文件
		super.generate();
		//生成客户端的代码
		if (this.generatorConfig instanceof WSGeneratorCofing) {
			WSGeneratorCofing wsGenratorConfig = (WSGeneratorCofing) this.generatorConfig;
			if(wsGenratorConfig.isGeneratorClient()){
				wsGenratorConfig.generatorWSClient();
			}else{
				LOG.warn("option isGeneratorClient is false, so will not generate wsclient!!!!");
			}
		}else{
			throw new BanaUtilException("generatorConfig is not WSGenratorConfig,can not generate wsclient!!!!!");
		}
		
	}
}
