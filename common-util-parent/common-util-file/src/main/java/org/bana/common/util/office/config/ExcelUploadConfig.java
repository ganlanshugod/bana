package org.bana.common.util.office.config;

/**
 * @author 导入一个流程时的excel配置
 *
 */
public interface ExcelUploadConfig extends ExcelConfig{

	/**
	 * @param colName
	 * @return
	 */
	String getMutiConfigNameUseColName(String colName);
	
	/**
	 * @param colName
	 * @return
	 */
	ColumnConfig getMutiConfigUseColName(String colName);

	String getDicCodeUseValue(Object value, ColumnConfig columnConfig);

}
