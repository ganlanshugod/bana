package org.bana.common.util.office.impl.annotation;

import java.util.List;

import org.bana.common.util.office.config.SheetConfig;
import org.bana.common.util.office.impl.SimpleExcelUploadConfig;

/**
 * 
 * 使用注解模式配置Excel的上传配置类
 * @author Liu Wenjie
 */
public class AnnotationExcelUploadConfig extends SimpleExcelUploadConfig{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6490574846980009474L;

	/** 
	* @Fields sheetConfigList : 
	*/ 
	private List<SheetConfig> sheetConfigList;
	
	
	private Class<?>[] clsArr;
	
	public AnnotationExcelUploadConfig(Class<?>... cls) {
		super();
		this.clsArr = cls;
	}

	@Override
	public List<SheetConfig> getSheetConfigList() {
		if(sheetConfigList == null){
			sheetConfigList = AnnotationExcelConfigSerializer.initSheetConfigList(this.clsArr);
		}
		return sheetConfigList;
	}

	/**
	 * 获取一个解析后的ExcelUploadConfig
	 * @return
	 */
	public SimpleExcelUploadConfig toSimpleExcelUploadConfig(){
		SimpleExcelUploadConfig simpleExcelUploadConfig = new SimpleExcelUploadConfig();
		simpleExcelUploadConfig.setSheetConfigList(this.getSheetConfigList());
		simpleExcelUploadConfig.setMutiTitleMap(this.getMutiTitleMap());
		return simpleExcelUploadConfig;
	}

}
