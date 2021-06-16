package org.bana.common.util.office.doc;

import java.io.OutputStream;

import org.bana.common.util.office.doc.config.DocObject;

/** 
* @ClassName: DocGenerator 
* @Description: word文件的生成和解析方法
* @author liuwenjie   
*/ 
public interface DocGenerator {

	/** 
	* @Description: 根据
	* @author liuwenjie   
	* @date 2021年6月15日 下午3:08:05 
	* @param outputStream
	* @param excelObject  
	*/ 
	void generatorDoc(OutputStream outputStream,DocObject docObject) ;
}
