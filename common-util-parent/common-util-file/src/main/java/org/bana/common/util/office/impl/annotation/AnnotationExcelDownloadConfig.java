package org.bana.common.util.office.impl.annotation;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bana.common.util.basic.StringUtils;
import org.bana.common.util.office.config.ColumnConfig;
import org.bana.common.util.office.config.ExcelDownloadConfig;
import org.bana.common.util.office.config.RowConfig;
import org.bana.common.util.office.config.SheetConfig;
import org.bana.common.util.office.impl.SimpleExcelDownloadConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationExcelDownloadConfig extends SimpleExcelDownloadConfig implements ExcelDownloadConfig {

	private static final long serialVersionUID = 4590841512187036389L;
	
	private static final Logger LOG = LoggerFactory.getLogger(AnnotationExcelDownloadConfig.class);
	
	private List<SheetConfig> sheetConfigList;
	
	private Class<?>[] clsArr;
	
	public AnnotationExcelDownloadConfig(Class<?>... cls) {
		super();
		this.clsArr = cls;
	}

	@Override
	public List<SheetConfig> getSheetConfigList() {
		if(sheetConfigList == null){
			sheetConfigList = AnnotationExcelConfigSerializer.initSheetConfigList(this.clsArr);
		}
		return sheetConfigList;
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

}
