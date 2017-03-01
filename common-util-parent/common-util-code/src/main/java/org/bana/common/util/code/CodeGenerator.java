/**
* @Company 青鸟软通   
* @Title: CodeGenerator.java 
* @Package org.bana.common.util.code 
* @author Liu Wenjie   
* @date 2014-10-24 下午3:51:34 
* @version V1.0   
*/ 
package org.bana.common.util.code;

/** 
 * @ClassName: CodeGenerator 
 * @Description: 根据指定的配置,生成对应的代码
 *  
 */
public interface CodeGenerator {

	/** 
	* @Description: 设置生成代码的配置
	* @author Liu Wenjie   
	* @date 2014-10-24 下午3:53:24   
	*/ 
	void setGeneratorConfig(GeneratorConfig generatorConfig);
	/** 
	* @Description: 生成代码的方法
	* @author Liu Wenjie   
	* @date 2014-10-24 下午3:52:37   
	*/ 
	void generate();
	
	
}
