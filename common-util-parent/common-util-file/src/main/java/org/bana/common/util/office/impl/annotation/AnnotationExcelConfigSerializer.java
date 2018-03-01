package org.bana.common.util.office.impl.annotation;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bana.common.util.basic.StringUtils;
import org.bana.common.util.exception.BanaUtilException;
import org.bana.common.util.office.config.ColumnConfig;
import org.bana.common.util.office.config.RowConfig;
import org.bana.common.util.office.config.RowConfig.RowType;
import org.bana.common.util.office.config.SheetConfig;
import org.bana.common.util.office.impl.StyleSerializer;

public class AnnotationExcelConfigSerializer implements Serializable {

	private static final long serialVersionUID = -6424809711299838663L;
	
	/**
	 * 初始化sheetConfig配置
	 */
	public static List<SheetConfig> initSheetConfigList(Class<?>... clsArr) {
		List<SheetConfig> sheetConfigList = new ArrayList<SheetConfig>();
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
			sheetConfig.setStyle(StyleSerializer.parseStyleStr(sheet.style()));
			sheetConfig.setCheckTitle(sheet.checkTitle());
			sheetConfig.setRowConfigList(initRowConfigList(cls));
			sheetConfigList.add(sheetConfig);
		}
		return sheetConfigList;
	}

	/**
	 * 配置行配置对象
	 * @param cls
	 * @return
	 */
	private static List<RowConfig> initRowConfigList(Class<?> cls) {
		List<RowConfig> rowConfigList = new ArrayList<RowConfig>();
		RowConfig titleConfig = new RowConfig();
		TitleRow titleRow = cls.getAnnotation(TitleRow.class);
		titleConfig.setType(RowType.标题);
		if(titleRow == null){
			titleConfig.setRowIndex(-1);
			titleConfig.setMutiTitle(true);
		}else{
			titleConfig.setRowIndex(titleRow.titleIndex());
			titleConfig.setStyle(StyleSerializer.parseStyleStr(titleRow.style()));
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
			indexColumn.setSort(titleRow.indexSort());
			columnConfigList.add(indexColumn);
		}
		
		Collections.sort(columnConfigList, new Comparator<ColumnConfig>() {
			@Override
			public int compare(ColumnConfig o1, ColumnConfig o2) {
				return o1.getSort() - o2.getSort();
			}
		});
		
		titleConfig.setColumnConfigList(columnConfigList);
		dataConfig.setColumnConfigList(columnConfigList);
		rowConfigList.add(titleConfig);
		rowConfigList.add(dataConfig);
		return rowConfigList;
	}

	private static List<ColumnConfig> initColumnConfigList(Class<?> cls) {
		List<ColumnConfig> columnConfigList = new ArrayList<ColumnConfig>(); 
		Field[] declaredFields = cls.getDeclaredFields();
		for (Field field : declaredFields) {
			ColumnConfig columnConfig = new ColumnConfig();
			ExcelColumn column = field.getAnnotation(ExcelColumn.class);
			if(column == null){
				continue;
			}
			columnConfig.setColspan(column.colspan());
			columnConfig.setSort(column.sort());
			columnConfig.setName(column.name());
			columnConfig.setMappedBy(field.getName());
			String style = column.style();
			if(field.getType().equals(int.class) || field.getType().equals(Integer.class)
					||field.getType().equals(long.class)|| field.getType().equals(Long.class)){
				style = "dataFormat:#;"+style;
			}
			columnConfig.setStyle(StyleSerializer.parseStyleStr(style));
			columnConfig.setType(field.getType().getSimpleName());
			columnConfig.setMutiMap(column.mutiMap());
			columnConfig.setMuti(StringUtils.isNotBlank(column.mutiMap()));
			columnConfig.setUseDic(column.useDic());
			columnConfig.setDicType(column.dicType());
			columnConfigList.add(columnConfig);
		}
		return columnConfigList;
	}
	
}
