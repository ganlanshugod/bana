package org.bana.common.util.poi.template;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.bana.common.util.poi.BanaPoiException;
import org.bana.common.util.poi.POIExcelGenerator;
import org.bana.common.util.poi.POIUtil;
import org.bana.common.util.poi.param.ExcelObject;
import org.bana.common.util.poi.template.param.TemplateExcelConfig;
import org.bana.common.util.poi.template.parser.TemplateParser;
import org.bana.common.util.poi.template.parser.VmTemplateParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
* @ClassName: TemplateExcelGenerator 
* @Description: 模板Excel生成类
* @author liuwenjie   
*/ 
public class TemplatePOIExcelGenerator implements POIExcelGenerator{
	
	private static final Logger LOG = LoggerFactory.getLogger(TemplatePOIExcelGenerator.class);
	
	private TemplateParser templateParser;

	@Override
	public void generatorExcel(OutputStream outputStream, ExcelObject excelObj) {
		if(excelObj == null) {
			throw new BanaPoiException("必须指定Excel对象");
		}
		if(outputStream == null) {
			throw new BanaPoiException("必须指定生成文件的输出流");
		}
		// 必须是模板类型的Excel对象
		if(!(excelObj instanceof TemplateExcelObject)) {
			throw new BanaPoiException("必须是TemplateExcelObject的对象");
		}
		
		TemplateExcelObject templateObj = (TemplateExcelObject)excelObj;
		Workbook workbook = POIUtil.getWorkbook(templateObj.getTemplateInputStream());
		TemplateExcelConfig excelConfig = templateObj.getExcelConfig();
		Object excelData = templateObj.getExcelData();
		int index = excelConfig.getSheetFrom();
		int sheetNum = excelConfig.getSheetNum();
		if(sheetNum == -1) {
			int numberOfSheets = workbook.getNumberOfSheets();
			sheetNum = numberOfSheets - index;
		}
		if(sheetNum >= 1) {
			while(sheetNum - index > 0) {
				createSheet(workbook,excelConfig,excelData,index);
				index ++;
			}
		}else{
			LOG.warn("excelConfig中没有要出里的sheet页 " + excelConfig);
		}
		try {
			workbook.write(outputStream);
		} catch (IOException e) {
			throw new BanaPoiException("将生成的Excel写入到指定的流时出现读写错误" + excelConfig);
		}
		
	}

	protected void doCreateSheet(Workbook workbook, Sheet sheet, TemplateExcelConfig excelConfig, Object excelData, int index) {
		
	}

	/** 
	* @Description: 处理指定的sheet页
	* @author liuwenjie   
	* @date 2021年8月30日 下午8:55:25 
	* @param workbook
	* @param excelConfig
	* @param excelData
	* @param index  
	*/ 
	private void createSheet(Workbook workbook, TemplateExcelConfig excelConfig, Object excelData, int index) {
		Sheet sheet = workbook.getSheetAt(index);
		if(sheet == null) {
			throw new BanaPoiException(String.format("模板文件中不存在指定的第 %s sheet页", index));
		}
		// 循环每一行进行数据替换
		this.getTemplateParser().parseTemplate(sheet, excelData);
		
		this.doCreateSheet(workbook,sheet,excelConfig,excelData,index);
	}


	public TemplateParser getTemplateParser() {
		if(templateParser == null) {
			templateParser = new VmTemplateParser();
		}
		return templateParser;
	}

	public void setTemplateParser(TemplateParser templateParser) {
		this.templateParser = templateParser;
	}

}
