/**
* @Company 青鸟软通   
* @Title: GeneratorOptions.java 
* @Package org.bana.common.util.code.impl 
* @author Liu Wenjie   
* @date 2014-10-24 下午4:56:43 
* @version V1.0   
*/ 
package org.bana.common.util.code.impl;

/** 
 * @ClassName: GeneratorOptions 
 * @Description: 保存代码生成的基本选项类配置
 *  
 */
public class GeneratorOptions {
	
	/** 
	* @Fields coverCodeFile : 生成代码的时候是否覆盖已经生成的代码文件
	*/ 
	private boolean coverCodeFile = false;
	/** 
	* @Fields coverResourceFile : 生成资源文件时，针对已有的资源文件如何来生成，不覆盖、覆盖还是追加
	*/ 
	public CoverResourceFile coverResourceFile = CoverResourceFile.不覆盖;
	
	
	/** 
	* @ClassName: CoverResourceFile 
	* @Description: 资源文件的处理方式
	*  
	*/ 
	public enum CoverResourceFile{
		覆盖,
		不覆盖,
		追加
	}
	
	
	/*================getter and setter ===================*/


	/**
	 * @Description: 属性 coverCodeFile 的get方法 
	 * @return coverCodeFile
	 */
	public boolean isCoverCodeFile() {
		return coverCodeFile;
	}


	/**
	 * @Description: 属性 coverCodeFile 的set方法 
	 * @param coverCodeFile 
	 */
	public void setCoverCodeFile(boolean coverCodeFile) {
		this.coverCodeFile = coverCodeFile;
	}


	/**
	 * @Description: 属性 coverResourceFile 的get方法 
	 * @return coverResourceFile
	 */
	public CoverResourceFile getCoverResourceFile() {
		return coverResourceFile;
	}


	/**
	 * @Description: 属性 coverResourceFile 的set方法 
	 * @param coverResourceFile 
	 */
	public void setCoverResourceFile(CoverResourceFile coverResourceFile) {
		this.coverResourceFile = coverResourceFile;
	}
	
	
	
}
