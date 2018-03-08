package org.bana.common.util.office.config;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 下载文档时使用的配置对象
 * @author Liu Wenjie
 *
 */
public interface ExcelDownloadConfig extends ExcelConfig{

	/**
	 * 基于某一个文件进行生成导出的excel配置
	 * @return
	 */
	String getBaseFile();

	/**
	 * Excel的配置类
	 * @return
	 */
	Object getType();

	/**
	 * 清理生成文件过程中可能产生的配置信息
	 */
	void clear();

	/**
	 * 根据配置获取单元格样式，样式保存在配置内部，防止重复生成同样的样式对象
	 * @param workbook
	 * @param sheetConfig
	 * @param rowConfig
	 * @param columnConfig
	 * @return
	 */
	CellStyle getCellStyle(Workbook workbook, SheetConfig sheetConfig, RowConfig rowConfig, ColumnConfig columnConfig);

	/**
	 * @param value
	 * @param columnConfig
	 * @return
	 */
	Object getDicValue(Object value, ColumnConfig columnConfig);

}
