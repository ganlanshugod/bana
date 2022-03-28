package org.bana.common.util.office.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bana.common.util.basic.StringUtils;
import org.bana.common.util.office.config.ColumnConfig;
import org.bana.common.util.office.config.ExcelConfig;
import org.bana.common.util.office.config.RowConfig;
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

	public Map<String, Map<String, Object>> getDicMap() {
		return dicMap;
	}

	/**
	 * 获取所有配置中指定的字典对象
	 * @return
	 */
	public Set<String> getDicKey(){
		Set<String> dicKey = new HashSet<String>();
		List<SheetConfig> sheetConfigList = getSheetConfigList();
		for (SheetConfig sheetConfig : sheetConfigList) {
			List<RowConfig> rowConfigList = sheetConfig.getRowConfigList();
			for (RowConfig rowConfig : rowConfigList) {
				List<ColumnConfig> columnConfigList = rowConfig.getColumnConfigList();
				for (ColumnConfig columnConfig : columnConfigList) {
					if(columnConfig.isUseDic() && StringUtils.isNotBlank(columnConfig.getDicType())){
						dicKey.add(columnConfig.getDicType());
					}
				}
			}
		}
		Map<String, List<ColumnConfig>> mutiTitleMap2 = this.getMutiTitleMap();
		if(mutiTitleMap2 != null && !mutiTitleMap2.isEmpty()){
			Collection<List<ColumnConfig>> values = mutiTitleMap2.values();
			for (List<ColumnConfig> list : values) {
				for (ColumnConfig columnConfig : list) {
					if(columnConfig.isUseDic() && StringUtils.isNotBlank(columnConfig.getDicType())){
						dicKey.add(columnConfig.getDicType());
					}
				}
			}
		}
		return dicKey;
	}
	
	
	/**
	 * 获取所有配置中指定的字典对象
	 * @return
	 */
	public List<ColumnConfig> getDicColumnConfig(){
		List<ColumnConfig> dicKey = new ArrayList<ColumnConfig>();
		List<SheetConfig> sheetConfigList = getSheetConfigList();
		for (SheetConfig sheetConfig : sheetConfigList) {
			List<RowConfig> rowConfigList = sheetConfig.getRowConfigList();
			for (RowConfig rowConfig : rowConfigList) {
				List<ColumnConfig> columnConfigList = rowConfig.getColumnConfigList();
				for (ColumnConfig columnConfig : columnConfigList) {
					if(columnConfig.isUseDic() && StringUtils.isNotBlank(columnConfig.getDicType())){
						dicKey.add(columnConfig);
					}
				}
			}
		}
		Map<String, List<ColumnConfig>> mutiTitleMap2 = this.getMutiTitleMap();
		if(mutiTitleMap2 != null && !mutiTitleMap2.isEmpty()){
			Collection<List<ColumnConfig>> values = mutiTitleMap2.values();
			for (List<ColumnConfig> list : values) {
				for (ColumnConfig columnConfig : list) {
					if(columnConfig.isUseDic() && StringUtils.isNotBlank(columnConfig.getDicType())){
						dicKey.add(columnConfig);
					}
				}
			}
		}
		return dicKey;
	}
}
