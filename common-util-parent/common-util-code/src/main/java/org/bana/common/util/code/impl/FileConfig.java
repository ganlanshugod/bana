/**
* @Company weipu   
* @Title: FileConfig.java 
* @Package org.bana.common.util.code.impl 
* @author Liu Wenjie   
* @date 2014-10-24 下午5:11:23 
* @version V1.0   
*/ 
package org.bana.common.util.code.impl;

/** 
 * @ClassName: FileConfig 
 * @Description: 代码生成器生成文件时的配置
 *  
 */
public class FileConfig {
	
	/** 
	* @Fields fileAbsolutePath : 文件的绝对路径
	*/ 
	private String fileAbsolutePath;
	
	/** 
	* @Fields fileContent : 生成文件的内容
	*/ 
	private String fileContent;
	
	
	
	/** 
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2014-10-27 下午4:41:48  
	*/ 
	public FileConfig() {
		super();
	}

	/** 
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2014-10-27 下午4:41:43 
	* @param fileAbsolutePath
	* @param fileContent 
	*/ 
	public FileConfig(String fileAbsolutePath, String fileContent) {
		super();
		this.fileAbsolutePath = fileAbsolutePath;
		this.fileContent = fileContent;
	}

	/*===========getter and setter ===============*/

	/**
	 * @Description: 属性 fileAbsolutePath 的get方法 
	 * @return fileAbsolutePath
	 */
	public String getFileAbsolutePath() {
		return fileAbsolutePath;
	}

	/**
	 * @Description: 属性 fileAbsolutePath 的set方法 
	 * @param fileAbsolutePath 
	 */
	public void setFileAbsolutePath(String fileAbsolutePath) {
		this.fileAbsolutePath = fileAbsolutePath;
	}

	/**
	 * @Description: 属性 fileContent 的get方法 
	 * @return fileContent
	 */
	public String getFileContent() {
		return fileContent;
	}

	/**
	 * @Description: 属性 fileContent 的set方法 
	 * @param fileContent 
	 */
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
	
}
