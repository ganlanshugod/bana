/**
* @Company 青鸟软通   
* @Title: MybatisGeneratorConfig.java 
* @Package org.bana.common.util.code.dao.mybatis 
* @author Liu Wenjie   
* @date 2014-10-29 上午10:25:50 
* @version V1.0   
*/ 
package org.bana.common.util.code.dao.mybatis;

import org.apache.velocity.VelocityContext;
import org.bana.common.util.code.impl.BaseGeneratorConfig;

/** 
 * @ClassName: MybatisGeneratorConfig 
 * @Description: mybaties 的生成配置类
 *  
 */
public class MybatisGeneratorConfig extends BaseGeneratorConfig{

	/**
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2014-10-29 上午11:02:49 
	* @param context 
	* @see org.bana.common.util.code.impl.BaseGeneratorConfig#doInitTemplateContext(org.apache.velocity.VelocityContext) 
	*/ 
	@Override
	protected void doInitTemplateContext(VelocityContext context) {
		//加载code对象的基本信息
		
	}

}
