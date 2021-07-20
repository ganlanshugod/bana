/**
* @Company weipu   
* @Title: SimpleCodeGenerator.java 
* @Package org.bana.common.util.code.impl 
* @author Liu Wenjie   
* @date 2014-10-24 下午4:14:20 
* @version V1.0   
*/ 
package org.bana.common.util.code.impl;

import java.io.File;
import java.util.List;

import org.bana.common.util.basic.FileUtil;
import org.bana.common.util.code.CodeGenerator;
import org.bana.common.util.code.GeneratorConfig;
import org.bana.common.util.code.impl.GeneratorOptions.CoverResourceFile;
import org.bana.common.util.exception.BanaUtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @ClassName: SimpleCodeGenerator 
 * @Description: 基本的代码生成器
 *  
 */
public class SimpleCodeGenerator implements CodeGenerator {
	/** 
	* @Fields generatorConfig : 代码生成的基本工具类
	*/ 
	protected GeneratorConfig generatorConfig;
	
	private static final Logger LOG = LoggerFactory.getLogger(SimpleCodeGenerator.class);

	/**
	 * <p>Description: 加载项目配置文件</p> 
	 * @author Liu Wenjie   
	 * @date 2014-10-24 下午4:14:20 
	 * @param generatorConfig 
	 * @see org.bana.common.util.code.CodeGenerator#setGeneratorConfig(org.bana.common.util.code.GeneratorConfig) 
	 */
	public void setGeneratorConfig(GeneratorConfig generatorConfig) {
		this.generatorConfig = generatorConfig;
	}

	/**
	 * <p>Description: 生成代码</p> 
	 * @author Liu Wenjie   
	 * @date 2014-10-24 下午4:14:20  
	 * @see org.bana.common.util.code.CodeGenerator#generate() 
	 */
	public void generate() {
		if(this.generatorConfig == null){
			throw new BanaUtilException("**********generateConfig is null,generate error!!!!!!**********");
		}
		GeneratorOptions generatorOptions = this.generatorConfig.getGeneratorOptions();
		if(generatorOptions == null){//如果配置没有指定生成参数，那么可以使用默认选项
			generatorOptions = new GeneratorOptions();
		}
		// 生成代码文件
		List<FileConfig> codeFiles = this.generatorConfig.getCodeFiles();
		if(codeFiles == null || codeFiles.isEmpty()){
			LOG.warn("*********generate codeFiles is null or empty ,no code file will be generated*********");
		}else{
			for (FileConfig fileConfig : codeFiles) {
				String fileAbsolutePath = fileConfig.getFileAbsolutePath();
				File file = new File(fileAbsolutePath);
				//如果文件存在，则判断是增加还是不增加
				if(file.exists()&&!generatorOptions.isCoverCodeFile()){
					LOG.warn("******* can not generatoe codeFiles " + fileAbsolutePath + " ,beacause it has existed!!!!!");
					continue;
				}
				
				FileUtil.generateFile(file, fileConfig.getFileContent());
				LOG.info("generate codeFiles " + fileConfig.getFileAbsolutePath() + " success...");
			}
		}
		
		//生成配置或资源文件 
		List<FileConfig> resourcesFiles = this.generatorConfig.getResourcesFiles();
		if(resourcesFiles == null || resourcesFiles.isEmpty()){
			LOG.warn("*********generate resourceFiles is null or empty ,no resource file will be generated*********");
		}else{
			for (FileConfig fileConfig : resourcesFiles) {
				String fileAbsolutePath = fileConfig.getFileAbsolutePath();
				File file = new File(fileAbsolutePath);
				//文件已存在，并且配置要求文件不覆盖
				if(file.exists()&&CoverResourceFile.不覆盖.equals(generatorOptions.getCoverResourceFile())){
					LOG.warn("******* can not generatoe resourceFiles " + fileAbsolutePath + " ,beacause it has existed!!!!!");
					continue;
				}
				if(CoverResourceFile.覆盖.equals(generatorOptions.getCoverResourceFile())){
					//生成文件
					FileUtil.generateFile(file, fileConfig.getFileContent());
					LOG.info("generate resourceFiles " + fileConfig.getFileAbsolutePath() + " success...");
					continue;
				}
				
				//生成文件
				FileUtil.appendFile(file, fileConfig.getFileContent());
				LOG.info("appendFile resourceFiles " + fileConfig.getFileAbsolutePath() + " success...");
			}
		}
	}

}
