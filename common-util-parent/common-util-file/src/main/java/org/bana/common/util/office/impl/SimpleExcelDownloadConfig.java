package org.bana.common.util.office.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
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
	
	
	/** 
	* @Fields dicMap : 导出时使用的一个map值，根据如果使用了useMap参数，那么根据dicKey去查找对应的价值对去设置此值
	*/ 
	private Map<String,Map<String,Object>> dicMap;
	
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
	public Object getDicValue(String key, ColumnConfig columnConfig) {
		if(this.dicMap == null || this.dicMap.isEmpty()){
			return key;
		}
		String dicType = columnConfig.getDicType();
		Map<String, Object> dic = dicMap.get(dicType);
		if(dic == null || dic.isEmpty()){
			return key;
		}
		Object value = dic.get(key);
		if(value == null){
			return key;
		}
		return value;
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

	public void setDicMap(Map<String, Map<String, Object>> dicMap) {
		this.dicMap = dicMap;
	}
	
}
