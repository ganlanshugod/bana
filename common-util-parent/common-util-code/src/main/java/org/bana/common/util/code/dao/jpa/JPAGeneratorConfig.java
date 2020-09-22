package org.bana.common.util.code.dao.jpa;

import org.apache.velocity.VelocityContext;
import org.bana.common.util.code.impl.BaseGeneratorConfig;

/** 
 * @ClassName: MybatisGeneratorConfig 
 * @Description: mybaties 的生成配置类
 *  
 */
public class JPAGeneratorConfig extends BaseGeneratorConfig{

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
