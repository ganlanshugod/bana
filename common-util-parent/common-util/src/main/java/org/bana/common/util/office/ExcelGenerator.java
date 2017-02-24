/**
* @Company 青鸟软通   
* @Title: POIGenerator.java 
* @Package org.bana.common.util.poi 
* @author Liu Wenjie   
* @date 2015-7-2 上午10:24:11 
* @version V1.0   
*/ 
package org.bana.common.util.office;

import java.io.InputStream;
import java.io.OutputStream;

/** 
 * @ClassName: POIGenerator 
 * @Description: 利用POI参数,访问和操作Excel文档的接口方法
 *  
 */
public interface ExcelGenerator {
	
	/** 
	* @Description: 生成Excel文件，导入到输出流中
	* @author Liu Wenjie   
	* @date 2015-7-2 上午10:31:03   
	*/ 
	public void generatorExcel(OutputStream outputStream,ExcelObject excelObject) ;
	
	/** 
	* @Description: 将一个Excel的输入流转化为一个Excel类型的对象
	* @author Liu Wenjie   
	* @date 2015-7-2 上午10:36:47 
	* @return  
	*/ 
	public ExcelObject generatorObject(InputStream inputStream);
	
}
