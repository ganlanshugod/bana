package org.bana.common.util.office.impl.annotation;

import java.util.List;

import org.bana.common.util.office.config.ExcelDownloadConfig;
import org.bana.common.util.office.config.SheetConfig;
import org.bana.common.util.office.impl.SimpleExcelDownloadConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationExcelDownloadConfig extends SimpleExcelDownloadConfig implements ExcelDownloadConfig {

	private static final long serialVersionUID = 4590841512187036389L;
	
	private static final Logger LOG = LoggerFactory.getLogger(AnnotationExcelDownloadConfig.class);
	
	private List<SheetConfig> sheetConfigList;
	
	private Class<?>[] clsArr;
	
	public AnnotationExcelDownloadConfig(Class<?>... cls) {
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
	
}
