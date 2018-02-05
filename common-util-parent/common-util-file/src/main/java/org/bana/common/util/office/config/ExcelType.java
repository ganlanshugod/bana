package org.bana.common.util.office.config;

/*==============其他本类中实用的配置信息 =============*/

public  enum ExcelType{
	XLS("xls"),XLSX("xlsx");
	private String extName ; //后缀名称
	private ExcelType(String extName){
		this.extName = extName;
	}
	public String getExtName(){
		return this.extName;
	}
	public static ExcelType getInstance(String extName){
		for (ExcelType excelType : values()) {
			if(excelType.getExtName().equals(extName)){
				return excelType;
			}
		}
		return XLS;
	}
}