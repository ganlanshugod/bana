package org.bana.common.util.office.impl.annotation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bana.common.util.basic.StringUtils;
import org.bana.common.util.exception.BanaUtilException;
import org.bana.common.util.office.config.ColumnConfig;
import org.bana.common.util.office.config.ExcelUploadConfig;
import org.bana.common.util.office.config.RowConfig;
import org.bana.common.util.office.config.RowConfig.RowType;
import org.bana.common.util.office.config.SheetConfig;

/**
 * 
 * 使用注解模式配置Excel的上传配置类
 * @author Liu Wenjie
 */
public class AnnotationExcelUploadConfig implements ExcelUploadConfig{
	
	private Class<?>[] clsArr;
	
	/** 
	* @Fields mutiTitleMap : 当一列支持多种列时，支持
	*/ 
	private Map<String,List<String>> mutiTitleMap;
	
	/** 
	* @Fields sheetConfigList : 
	*/ 
	private List<SheetConfig> sheetConfigList;
	
	public AnnotationExcelUploadConfig(Class<?>... cls) {
		super();
		this.clsArr = cls;
	}

	@Override
	public List<SheetConfig> getSheetConfigList() {
		if(sheetConfigList == null){
			initSheetConfigList();
		}
		return sheetConfigList;
	}

	/**
	 * 初始化sheetConfig配置
	 */
	private void initSheetConfigList() {
		if(sheetConfigList == null){
			sheetConfigList = new ArrayList<SheetConfig>();
		}
		if(clsArr == null || clsArr.length == 0){
			throw new BanaUtilException("AnnotationExcelUploadConfig 中指定的配置对象类为空");
		}
		for (Class<?> cls : clsArr) {
			Sheet sheet = cls.getAnnotation(Sheet.class);
			if(sheet == null){
				throw new BanaUtilException("配置类" + cls.getName() + "没有声明Sheet配置对象");
			}
			SheetConfig sheetConfig = new SheetConfig();
			sheetConfig.setIndex(sheet.index());
			sheetConfig.setName(sheet.name());
			sheetConfig.setStyle(StringUtils.parseStyleStr(sheet.style()));
			sheetConfig.setCheckTitle(sheet.checkTitle());
			sheetConfig.setRowConfigList(initRowConfigList(cls));
			sheetConfigList.add(sheetConfig);
		}
	}

	/**
	 * 配置行配置对象
	 * @param cls
	 * @return
	 */
	private List<RowConfig> initRowConfigList(Class<?> cls) {
		List<RowConfig> rowConfigList = new ArrayList<RowConfig>();
		RowConfig titleConfig = new RowConfig();
		TitleRow titleRow = cls.getAnnotation(TitleRow.class);
		titleConfig.setType(RowType.标题);
		if(titleRow == null){
			titleConfig.setRowIndex(-1);
			titleConfig.setMutiTitle(true);
		}else{
			titleConfig.setRowIndex(titleRow.titleIndex());
			titleConfig.setStyle(StringUtils.parseStyleStr(titleRow.style()));
			titleConfig.setMutiTitle(titleRow.mutiTitle());
		}
		
		RowConfig dataConfig = new RowConfig();
		dataConfig.setType(RowType.数据);
		dataConfig.setClassName(cls.getName());
		dataConfig.setRowIndex(-1);
		
		
		List<ColumnConfig> columnConfigList = initColumnConfigList(cls);
		String indexName = titleRow.indexName();
		if(StringUtils.isNotBlank(indexName)){
			ColumnConfig indexColumn = new ColumnConfig();
			indexColumn.setMappedBy("@index");
			indexColumn.setName(indexName);
			columnConfigList.add(indexColumn);
		}
		titleConfig.setColumnConfigList(columnConfigList);
		dataConfig.setColumnConfigList(columnConfigList);
		rowConfigList.add(titleConfig);
		rowConfigList.add(dataConfig);
		return rowConfigList;
	}

	private List<ColumnConfig> initColumnConfigList(Class<?> cls) {
		List<ColumnConfig> columnConfigList = new ArrayList<ColumnConfig>(); 
		Field[] declaredFields = cls.getDeclaredFields();
		for (Field field : declaredFields) {
			ColumnConfig columnConfig = new ColumnConfig();
			ExcelColumn column = field.getAnnotation(ExcelColumn.class);
			if(column == null){
				continue;
			}
			columnConfig.setColspan(column.colspan());
			columnConfig.setName(column.name());
			columnConfig.setMappedBy(field.getName());
			String style = column.style();
			if(field.getType().equals(int.class) || field.getType().equals(Integer.class)){
				style = "dataFormat:#;"+style;
			}
			columnConfig.setStyle(StringUtils.parseStyleStr(style));
			columnConfig.setType(field.getType().getSimpleName());
			columnConfig.setMutiMap(column.mutiMap());
			columnConfig.setUseDic(column.useDic());
			columnConfig.setDicType(column.dicType());
			columnConfigList.add(columnConfig);
		}
		return columnConfigList;
	}

	@Override
	public String getMutiConfigNameUseColName(String colName) {
		if(this.mutiTitleMap != null){
			Set<Entry<String, List<String>>> entrySet = mutiTitleMap.entrySet();
			for (Entry<String, List<String>> entry : entrySet) {
				List<String> mutiTilte = entry.getValue();
				if(mutiTilte.contains(colName)){
					return entry.getKey();
				}
			}
		}
		return null;
	}

	@Override
	public Map<String, List<String>> getMutiTitleMap() {
		return this.mutiTitleMap;
	}

	public void setMutiTitleMap(Map<String, List<String>> mutiTitleMap) {
		this.mutiTitleMap = mutiTitleMap;
	}
}
