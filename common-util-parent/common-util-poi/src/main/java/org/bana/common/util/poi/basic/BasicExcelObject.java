package org.bana.common.util.poi.basic;

import org.bana.common.util.poi.param.BaseExcelObject;

/** 
* @ClassName: BasicExcelObject 
* @Description: 获取基本的Excel生成对象
* @author liuwenjie   
*/ 
public class BasicExcelObject extends BaseExcelObject{

	/** 
	* @Fields type : Excel对象类型
	*/ 
	protected ExcelType excelType;

	public ExcelType getExcelType() {
		return excelType;
	}

	public void setExcelType(ExcelType excelType) {
		this.excelType = excelType;
	}
}
