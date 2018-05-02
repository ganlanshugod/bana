package org.bana.common.util.office.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.bana.common.util.basic.StringUtils;
import org.bana.common.util.office.config.ColumnConfig;
import org.bana.common.util.office.config.ExcelDownloadConfig;
import org.bana.common.util.office.config.ExcelType;
import org.bana.common.util.office.config.RowConfig;
import org.bana.common.util.office.config.SheetConfig;

import com.alibaba.fastjson.JSON;

/**
 * 一个基本的配置对象
 * @author Liu Wenjie
 */
public class SimpleExcelDownloadConfig extends SimpleExcelConfig implements ExcelDownloadConfig {
	private static final long serialVersionUID = 6498692834320772847L;
	
	/** 
    * @Fields style : 设置的默认样式
    */ 
    private Map<String,String> style;
	
	/**
	 * 导出的依赖文件
	 */
	private String baseFile;
	
	/** 
	* @Fields type : excel的类型，.xls 或者 是.xlsx
	*/ 
	private ExcelType type;
	
	/** 
	* @Fields styleMap : 配置中所有用到的style集合
	*/ 
	private ThreadLocal<Map<String,CellStyle>> styleMapCollection = new ThreadLocal<Map<String,CellStyle>>();
	
	
	@Override
	public String getBaseFile() {
		return baseFile;
	}
	
	public void setBaseFile(String baseFile) {
		this.baseFile = baseFile;
	}

	@Override
	public Object getType() {
		return type;
	}
	
	public void setType(ExcelType type) {
		this.type = type;
	}

	@Override
	public void clear() {
		styleMapCollection.set(null);
	}

	/** 
	* @Description: 获取单元格的默认样式
	* @author Liu Wenjie   
	* @date 2015-7-8 上午10:48:07 
	* @param workbook
	* @param sheetConfig
	* @param rowConfig
	* @param columnConfig  
	*/ 
	public CellStyle getCellStyle(Workbook workbook, SheetConfig sheetConfig, RowConfig rowConfig, ColumnConfig columnConfig) {
		//构建当前map的样式类型
		Map<String,String> cellMap = new HashMap<String, String>();
		if(this.style != null){
			cellMap.putAll(this.style);
		}
		if(sheetConfig.getStyle() != null){
			cellMap.putAll(sheetConfig.getStyle());
		}
		if(rowConfig.getStyle() != null){
			cellMap.putAll(rowConfig.getStyle());
		}
		if(columnConfig.getStyle() != null){
			cellMap.putAll(columnConfig.getStyle());
		}
		if(!cellMap.isEmpty()){
			String styleKey = JSON.toJSONString(cellMap);// JSONObject.fromObject(cellMap).toString();
			Map<String, CellStyle> styleMap = styleMapCollection.get();
			if(styleMap == null){
				styleMap = new HashMap<String, CellStyle>();
				styleMapCollection.set(styleMap);
			}
			CellStyle cellStyle = styleMap.get(styleKey);
			if(cellStyle == null){
				cellStyle = StyleSerializer.generatorCellStyle(workbook,cellMap);
				styleMap.put(styleKey, cellStyle);
				return cellStyle;
			}else{
				return cellStyle;
			}
		}
		return null;
	}

	/** 
	* @Description: 根据字典Map获取对应的字典对应的值
	* @author liuwenjie   
	* @date 2016-2-20 上午11:38:41 
	* @param value
	* @param columnConfig
	* @return  
	*/ 
	@Override
	public Object getDicValue(Object key, ColumnConfig columnConfig) {
		if(this.dicMap == null || this.dicMap.isEmpty()){
			return key;
		}
		String dicType = columnConfig.getDicType();
		Map<String, Object> dic = dicMap.get(dicType);
		if(dic == null || dic.isEmpty()){
			return key;
		}
		if(key instanceof String[]){//数组数据
			String[] keyArr = (String[])key;
			List<Object> valueList = new ArrayList<Object>();
			for (String object : keyArr) {
				Object value = dic.get(object);
				if(value == null){
					valueList.add(object);
				}else{
					valueList.add(value);
				}
			}
			return valueList;
		}else if(key instanceof Collection<?>){//集合数据
			Collection<?> collection = (Collection<?>)key;
			List<Object> valueList = new ArrayList<Object>();
			for (Object object : collection) {
				Object value = dic.get(object);
				if(value == null){
					valueList.add(object);
				}else{
					valueList.add(value);
				}
			}
			return valueList;
		}else{ //正常的字段情况下
			Object value = dic.get(key);
			if(value == null){
				return key;
			}
			return value;
		}
	}
	
	
	/** 
	* @Description: 获取执行过程中总共使用了几个样式
	* @author Liu Wenjie   
	* @date 2015-7-9 下午1:25:29 
	* @return  
	*/ 
	public int getCellStyleSize(){
		Map<String, CellStyle> styleMap = styleMapCollection.get();
		return styleMap == null ? 0 :styleMap.size();
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

	@Override
	public List<String> getDicValueList(String dicType) {
		if(this.dicMap == null || this.dicMap.isEmpty()){
			return null;
		}
		Map<String, Object> dic = dicMap.get(dicType);
		if(dic == null || dic.isEmpty()){
			return null;
		}
		List<String> arrayList = new ArrayList<String>();
		for (Object obj : dic.values()) {
			arrayList.add(obj.toString());
		}
		return arrayList;
	}

}
