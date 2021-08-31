package org.bana.common.util.poi.template;

import java.io.InputStream;

import org.bana.common.util.poi.param.ExcelObject;
import org.bana.common.util.poi.template.param.TemplateExcelConfig;

/** 
* @ClassName: TemplateExcelObject 
* @Description: 模板Excel的生成对象
* @author liuwenjie   
*/ 
public interface TemplateExcelObject extends ExcelObject{

	/** 
	* @Description: 获取模板文件的输入流
	* @author liuwenjie   
	* @date 2021年8月30日 下午7:19:51 
	* @return  
	*/ 
	public InputStream getTemplateInputStream();
	
	
	/**
	* <p>Description: 获取模板类型的Excel配置类</p> 
	* @author liuwenjie   
	* @date 2021年8月30日 下午8:43:36 
	* @return 
	* @see org.bana.common.util.poi.param.ExcelObject#getExcelConfig() 
	*/ 
	public TemplateExcelConfig getExcelConfig();
}
