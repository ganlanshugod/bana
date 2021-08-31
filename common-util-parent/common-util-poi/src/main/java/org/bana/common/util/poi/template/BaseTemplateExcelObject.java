package org.bana.common.util.poi.template;

import java.io.InputStream;

import org.bana.common.util.poi.param.BaseExcelObject;
import org.bana.common.util.poi.template.param.TemplateExcelConfig;

/** 
* @ClassName: BaseTemplateExcelObject 
* @Description: 基础的模板ExcelObject
* @author liuwenjie   
*/ 
public abstract class BaseTemplateExcelObject extends BaseExcelObject implements TemplateExcelObject{

	/** 
	* @Fields inputStream : 模板文件对应的输入流
	*/ 
	protected InputStream templateInputStream;
	
	private TemplateExcelConfig excelConfig;

	@Override
	public InputStream getTemplateInputStream() {
		return this.templateInputStream;
	}

	public void setTemplateInputStream(InputStream templateInputStream) {
		this.templateInputStream = templateInputStream;
	}
	
	@Override
	public TemplateExcelConfig getExcelConfig() {
		if(this.excelConfig == null) {
			this.excelConfig = new TemplateExcelConfig();
		}
		return this.excelConfig;
	}
	
	public void setExcelConfig(TemplateExcelConfig excelConfig) {
		this.excelConfig = excelConfig;
	}
	
}
