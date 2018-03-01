package org.bana.common.util.office.impl;

import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.bana.common.util.office.config.ColumnConfig;
import org.bana.common.util.office.config.ExcelDownloadConfig;
import org.bana.common.util.office.config.RowConfig;
import org.bana.common.util.office.config.SheetConfig;

/**
 * 一个基本的配置对象
 * @author Liu Wenjie
 */
public class SimpleExcelDownloadConfig extends SimpleExcelConfig implements ExcelDownloadConfig {
	private static final long serialVersionUID = 6498692834320772847L;
	
	@Override
	public String getBaseFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public CellStyle getCellStyle(Workbook workbook, SheetConfig sheetConfig, RowConfig rowConfig,
			ColumnConfig columnConfig) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getDicValue(String value, ColumnConfig columnConfig) {
		// TODO Auto-generated method stub
		return null;
	}

}
