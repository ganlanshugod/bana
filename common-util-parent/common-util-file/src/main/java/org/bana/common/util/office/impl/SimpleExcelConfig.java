package org.bana.common.util.office.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.bana.common.util.office.config.ColumnConfig;
import org.bana.common.util.office.config.ExcelConfig;
import org.bana.common.util.office.config.SheetConfig;

/**
 * @author Liu Wenjie
 * 最基本的一个Excel配置实现类
 */
public class SimpleExcelConfig implements ExcelConfig,Serializable {

	private static final long serialVersionUID = -7583535745650181893L;

	private List<SheetConfig> sheetConfigList;
	
	/** 
	* @Fields dicMap : 导出时使用的一个map值，根据如果使用了useMap参数，那么根据dicKey去查找对应的价值对去设置此值
	*/ 
	protected Map<String,Map<String,Object>> dicMap;
	
	/** 
	* @Fields mutiTitleMap : 当一列支持多种列时，支持
	*/ 
	protected Map<String,List<ColumnConfig>> mutiTitleMap;

	@Override
	public List<SheetConfig> getSheetConfigList() {
		return sheetConfigList;
	}
	
	@Override
	public Map<String, List<ColumnConfig>> getMutiTitleMap() {
		return this.mutiTitleMap;
	}

	public void setMutiTitleMap(Map<String, List<ColumnConfig>> mutiTitleMap) {
		this.mutiTitleMap = mutiTitleMap;
	}

	public void setSheetConfigList(List<SheetConfig> sheetConfigList) {
		this.sheetConfigList = sheetConfigList;
	}
	
	public void setDicMap(Map<String, Map<String, Object>> dicMap) {
		this.dicMap = dicMap;
	}
}
