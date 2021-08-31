package org.bana.common.util.poi.basic;


/** 
* @ClassName: ExcelType 
* @Description: Excel的类型文件
* @author liuwenjie   
*/ 
public enum ExcelType {

	XLS("xls"), XLSX("xlsx");

	private String extName; // 后缀名称

	private ExcelType(String extName) {
		this.extName = extName;
	}

	public String getExtName() {
		return this.extName;
	}

	public static ExcelType getInstance(String extName) {
		for (ExcelType excelType : values()) {
			if (excelType.getExtName().equals(extName)) {
				return excelType;
			}
		}
		return XLS;
	}
}