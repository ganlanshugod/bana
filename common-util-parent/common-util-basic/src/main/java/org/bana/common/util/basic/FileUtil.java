/**
* @Company 青鸟软通   
* @Title: FileUtil.java 
* @Package org.bana.common.util.basic 
* @author Liu Wenjie   
* @date 2014-10-24 下午5:48:55 
* @version V1.0   
*/ 
package org.bana.common.util.basic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.bana.common.util.exception.BanaUtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @ClassName: FileUtil 
 * @Description: 文件相关的工具类
 *  
 */
public class FileUtil {
	private static  Logger LOG = LoggerFactory.getLogger(FileUtil.class); 
	/** 
	* @Description: 创建文件时出错
	* @author Liu Wenjie   
	* @date 2014-10-24 下午5:56:18 
	* @param file
	* @param fileContent
	* @param cover  
	*/ 
	public static void generateFile(File file, String fileContent) {
		if(file == null){
			throw new BanaUtilException("can not generate file,params file is null");
		}
		if(StringUtils.isBlank(fileContent)){
			throw new BanaUtilException("can not generate file,params fileContent is blank");
		}
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		FileOutputStream out = null;
		try {
			if(file.exists()){
				file.delete();
			}
			out = new FileOutputStream(file);
			out.write(fileContent.getBytes());
		} catch (Exception e) {
			throw new BanaUtilException("create file error.......",e);
		} finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					LOG.error("close outputStream error.............",e);
				}
			}
		}
	}  
	
	

	/** 
	* @Description: 以追加内容的方式增加文件内容
	* @author Liu Wenjie   
	* @date 2014-10-24 下午6:50:00 
	* @param file
	* @param fileContent  
	*/ 
	public static void appendFile(File file ,String fileContent){
		if(file == null){
			throw new BanaUtilException("to append file is null");
		}
		//文件不存在就已新建的方式创建
		if(!file.exists()){
			generateFile(file, fileContent);
			return;
		}
		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件     
			FileWriter writer = new FileWriter(file, true);     
			writer.write(fileContent);
			writer.close();
		} catch (Exception e) {
			throw new BanaUtilException("to append file " + file.getAbsolutePath() + " error",e);
		}       
	}
}
