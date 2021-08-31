package org.bana.common.util.poi.param;

/** 
* @ClassName: BaseExcelObject 
* @Description: 基础的ExcelObject实现类
* @author liuwenjie   
*/ 
public abstract class BaseExcelObject implements ExcelObject{

	protected ExcelConfig excelConfig;
	
	protected Object excelData;
	
	@Override
	public ExcelConfig getExcelConfig() {
		return excelConfig;
	}

	@Override
	public Object getExcelData() {
		return excelData;
	}

	public void setExcelData(Object excelData) {
		this.excelData = excelData;
	}
	
}
