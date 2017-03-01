/**
* @Company 青鸟软通   
* @Title: GeneratorConfig.java 
* @Package org.bana.common.util.code 
* @author Liu Wenjie   
* @date 2014-10-24 下午3:54:25 
* @version V1.0   
*/ 
package org.bana.common.util.code;

import java.util.List;

import org.bana.common.util.code.impl.FileConfig;
import org.bana.common.util.code.impl.GeneratorOptions;

/** 
 * @ClassName: GeneratorConfig 
 * @Description: 生成器的配置功能
 *  
 */
public interface GeneratorConfig {

	
	/** 
	* @Description: 获取代码生成需要的代码文件
	* @author Liu Wenjie   
	* @date 2014-10-24 下午4:37:05 
	* @return  
	*/ 
	List<FileConfig> getCodeFiles();
	
	/** 
	* @Description: 获取代码生成需要的资源文件
	* @author Liu Wenjie   
	* @date 2014-10-24 下午4:38:15 
	* @return  
	*/ 
	List<FileConfig> getResourcesFiles();
	
	
	/** 
	* @Description: 获取代码生成的选项配置
	* @author Liu Wenjie   
	* @date 2014-10-24 下午5:20:05 
	* @return  
	*/ 
	GeneratorOptions getGeneratorOptions();
}
