/**
* @Company weipu   
* @Title: POIGenerator.java 
* @Package org.bana.common.util.poi 
* @author Liu Wenjie   
* @date 2015-7-2 上午10:24:11 
* @version V1.0   
*/ 
package org.bana.common.util.office;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.bana.common.util.office.config.ExcelUploadConfig;

/** 
 * @ClassName: POIGenerator 
 * @Description: 利用POI参数,访问和操作Excel文档的接口方法
 *  
 */
/**
 * @author Liu Wenjie
 *
 */
public interface ExcelGenerator {
	
	/** 
	* @Description: 生成Excel文件，导入到输出流中
	* @author Liu Wenjie   
	* @date 2015-7-2 上午10:31:03   
	*/ 
	void generatorExcel(OutputStream outputStream,ExcelObject excelObject) ;
	
	/** 
	* @Description: 将一个Excel的输入流转化为一个Excel类型的对象
	* @author Liu Wenjie   
	* @date 2015-7-2 上午10:36:47 
	* @return  
	*/ 
	ExcelObject generatorObject(InputStream inputStream,ExcelUploadConfig excelConfig);
	
	/**
	 * 根据上传配置添加错误信息
	 * @param inputStream 原始的excel的输入楼
	 * @param outputStream 生成后的excel的输出流
	 * @param excelUploadConfig 上传的配置信息
	 * @param errorRecords 错误记录，多个sheet页中的错误信息是按照顺序排列的
	 */
	void addErrorResult(InputStream inputStream,OutputStream outputStream,ExcelUploadConfig excelUploadConfig,List<Map<Integer, String>> errorRecords);
	
	
	/**
	 * 根据上传配置添加错误信息
	 * @param inputStream 原始的excel的输入楼
	 * @param outputStream 生成后的excel的输出流
	 * @param excelUploadConfig 上传的配置信息
	 * @param errorRecords 错误记录，多个sheet页中的错误信息是按照顺序排列的
	 * @param includeCorrect 导出的文件中是否包含正确的记录
	 */
	void addErrorResult(InputStream inputStream,OutputStream outputStream,ExcelUploadConfig excelUploadConfig,List<Map<Integer, String>> errorRecords,boolean includeCorrect);
}
