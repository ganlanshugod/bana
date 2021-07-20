/**
* @Company weipu   
* @Title: SpringGeneratorConfig.java 
* @Package org.bana.common.util.code.service.spring 
* @author Liu Wenjie   
* @date 2014-11-3 下午6:41:36 
* @version V1.0   
*/ 
package org.bana.common.util.code.service.spring;

import org.apache.velocity.VelocityContext;
import org.bana.common.util.code.impl.BaseGeneratorConfig;

/** 
 * @ClassName: SpringGeneratorConfig 
 * @Description: Spring的基本代码生成器配置
 *  
 */
public class SpringGeneratorConfig extends BaseGeneratorConfig {

	/** 
	* @Fields configUseAnnotation : spring的bean声明是否使用注解，true使用，false不使用
	*/ 
	protected boolean configUseAnnotation = false;
	
	
	
	/**
	 * <p>Description: </p> 
	 * @author Liu Wenjie   
	 * @date 2014-11-3 下午6:41:36 
	 * @param context 
	 * @see org.bana.common.util.code.impl.BaseGeneratorConfig#doInitTemplateContext(org.apache.velocity.VelocityContext) 
	 */
	@Override
	protected void doInitTemplateContext(VelocityContext context) {
		
	}

}
