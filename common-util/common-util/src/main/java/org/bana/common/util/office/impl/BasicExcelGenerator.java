/**
* @Company 青鸟软通   
* @Title: BasicExcelGenerator.java 
* @Package org.bana.common.util.poi.impl 
* @author Liu Wenjie   
* @date 2015-7-2 上午11:09:34 
* @version V1.0   
*/ 
package org.bana.common.util.office.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bana.common.util.basic.CollectionUtils;
import org.bana.common.util.basic.StringUtils;
import org.bana.common.util.exception.BanaUtilException;
import org.bana.common.util.office.ExcelGenerator;
import org.bana.common.util.office.ExcelObject;
import org.bana.common.util.office.impl.config.ColumnConfig;
import org.bana.common.util.office.impl.config.ExcelConfig;
import org.bana.common.util.office.impl.config.ExcelConfig.ExcelType;
import org.bana.common.util.office.impl.config.RowConfig;
import org.bana.common.util.office.impl.config.RowConfig.RowType;
import org.bana.common.util.office.impl.config.SheetConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @ClassName: BasicExcelGenerator 
 * @Description: Excel生成方法的基本实现类
 *  
 */
public class BasicExcelGenerator implements ExcelGenerator {
	
	private static final Logger LOG = LoggerFactory.getLogger(BasicExcelGenerator.class);
	
	/** 
	* @Fields excelConfig : excel的配置
	*/ 
	protected ExcelConfig excelConfig;

	/**
	 * <p>Description: </p> 
	 * @author Liu Wenjie   
	 * @date 2015-7-2 上午11:09:34 
	 * @param outputStream
	 * @param excelObject 
	 * @see org.bana.common.util.poi.POIHXSSFGenerator#generatorExcel(java.io.OutputStream, org.bana.common.util.office.ExcelObject) 
	 */
	@Override
	public void generatorExcel(OutputStream outputStream, ExcelObject excelObject) {
		if(excelConfig == null){
			throw new BanaUtilException("没有指定excelConfig");
		}
		//创建一个文件对象(根据type类型获取)
		Workbook workbook = getWorkBook();
		//根据excelConfig进行创建excel文件
		List<SheetConfig> sheetConfigList = excelConfig.getSheetConfigList();
		if(sheetConfigList != null){
			for (SheetConfig sheetConfig : sheetConfigList) {
				createSheet(workbook, sheetConfig,excelObject);
			}
		}else{
			LOG.warn("excelConfig中没有或地区到sheetConfigList " + excelConfig);
		}
		try {
			workbook.write(outputStream);
		} catch (IOException e) {
			throw new BanaUtilException("将生成的Excel写入到指定的流时出现读写错误" + this.excelConfig);
		}finally{
			excelConfig.clear();
		}
		
	}

	/** 
	* @Description: 创建一个sheet页的记录
	* @author Liu Wenjie   
	* @date 2015-7-7 下午6:23:45 
	* @param workbook
	* @param sheetConfig  
	 * @param excelObject 
	*/ 
	private void createSheet(Workbook workbook, SheetConfig sheetConfig, ExcelObject excelObject) {
		Sheet sheet = workbook.createSheet(sheetConfig.getName());
		//根据sheet的行配置文件，创建对应的行信息
		List<RowConfig> rowConfigList = sheetConfig.getRowConfigList();
		int currentRowNum = 0;
		int columnSize = 0;
		if(rowConfigList != null){
			for (RowConfig rowConfig : rowConfigList) {
				//获取当前的起始行数
				Integer rowIndex = rowConfig.getRowIndex();
				if(rowIndex != null && !rowIndex.equals(new Integer(-1))){
					//如果指定了特定的生成行数，则跳到指定的行进行生成
					currentRowNum = rowIndex - 1;
				}
				//创建一行记录
				if(RowType.标题.equals(rowConfig.getType())){
					createTitleRow(workbook,sheet,sheetConfig,rowConfig,currentRowNum++);
				}else{
					//根据数据集合，循环创建数据行
					List<? extends Object> data = excelObject.getData(sheetConfig.getName());
					if(data != null){
						for (int i = 0; i < data.size(); i++) {
							Object object = data.get(i);
							if(Map.class.isInstance(object) || object.getClass().getName().equals(rowConfig.getClassName())){
								int currentColumnSize = createDataRow(workbook,sheet,sheetConfig,rowConfig,object,currentRowNum++,i);
								//获取总列数
								if(currentColumnSize > columnSize){
									columnSize = currentColumnSize;
								}
							}else{
								throw new BanaUtilException("指定的数据类型 " + rowConfig.getClassName() + " 与传入的数据类型 " + object.getClass() + " 不匹配");
							}
						}
					}
					
				}
				
			}
		}
		//设置列宽自适应
		for (int i = 0; i < columnSize; i++) {
			sheet.autoSizeColumn((short)i); //调整第一列宽度
		}
		
	}

	/** 
	* @Description: 根据指定的数据和匹配关系，构造一行记录
	* @author Liu Wenjie   
	* @date 2015-7-8 上午9:36:59 
	* @param workbook
	* @param sheet
	* @param sheetConfig
	* @param rowConfig
	* @param object
	* @param i  
	*/ 
	private int createDataRow(Workbook workbook, Sheet sheet, SheetConfig sheetConfig, RowConfig rowConfig, Object object, int currentRowNum,int index) {
		return createRow(workbook, sheet, sheetConfig, rowConfig, object, currentRowNum,index);
	}

	/** 
	* @Description: 创建一行记录
	* @author Liu Wenjie   
	* @date 2015-7-7 下午6:19:07 
	* @param workbook
	* @param sheet
	* @param sheetConfig
	 * @param rowConfig 
	* @param currentRowNum  
	*/ 
	private int createTitleRow(Workbook workbook, Sheet sheet, SheetConfig sheetConfig, RowConfig rowConfig, int currentRowNum) {
		return createRow(workbook, sheet, sheetConfig, rowConfig, null, currentRowNum,1);
	}
	
	/** 
	* @Description: 创建一行记录
	* @author Liu Wenjie   
	* @date 2015-7-8 上午10:41:05 
	* @param workbook
	* @param sheet
	* @param sheetConfig
	* @param rowConfig
	* @param currentRowNum  
	 * @param index 
	* @return 创建的总列数，单元格个数
	*/ 
	private int createRow(Workbook workbook, Sheet sheet, SheetConfig sheetConfig, RowConfig rowConfig,Object object, int currentRowNum, int index){
		Row row = sheet.createRow(currentRowNum);
		int currentColNum = 0;
	    List<ColumnConfig> columnConfigList = rowConfig.getColumnConfigList();
	    if(columnConfigList != null){
	    	List<String> mutiMapColumnNameList = rowConfig.getMutiMapColumnName();//所有mutiMap指向的列名
	    	for (ColumnConfig columnConfig : columnConfigList) {
	    		//如果标题列的对应关系不为空，则执行按照列配置设置名字的类型
	    		Map<String, List<String>> mutiTitleMap = this.excelConfig.getMutiTitleMap();
    			String configName = columnConfig.getName();
    			ColumnConfig targetColumnConfig = columnConfig;
    			if(this.excelConfig.getMutiTitleMap() != null){
    				List<String> list = mutiTitleMap.get(configName);
    				if(mutiMapColumnNameList.contains(configName)){
    					String sourceConfigName = rowConfig.getColumnNameUseMutiMapName(configName);
    					if(mutiTitleMap.get(sourceConfigName) != null && !mutiTitleMap.get(sourceConfigName).isEmpty()){
    						continue;
    					}
					}
    				if(list != null && !list.isEmpty()){
    					//如果指定动态列中对应的mutiMap包含当前列，则此列不进行列值创建
    					for (String cellKey : list) {//动态循环对应的内容来设置cell
    						//标题使用自己的column配置，如果是数据，则使用mutiMap的配置
    						if(!RowType.标题.equals(rowConfig.getType())){
    							String mutiMapColumnName = columnConfig.getMutiMap();
    							targetColumnConfig = rowConfig.getColumnConfigMap().get(mutiMapColumnName);
    							if(targetColumnConfig == null){
    								throw new BanaUtilException("没有找到 “" + configName + "” 对应的mutiMap " + mutiMapColumnName +"　的配置");
    							}
    						}
    						//按照当前的列进行创建单元格
    						currentColNum = createCurrentCell(workbook,sheet,row,sheetConfig,rowConfig,targetColumnConfig,cellKey,object,currentRowNum,currentColNum,index);
    					}
    				}else{//使用固定列值来设置内容
    					currentColNum = createCurrentCell(workbook,sheet,row,sheetConfig,rowConfig,targetColumnConfig,configName,object,currentRowNum,currentColNum,index);
    				}
    			}else{//使用固定内容来设置对应的内容
    				currentColNum = createCurrentCell(workbook,sheet,row,sheetConfig,rowConfig,targetColumnConfig,configName,object,currentRowNum,currentColNum,index);
    			}
			}
	    }
	    return currentColNum;
	}

	/** 
	* @Description: 创建当前单元格内容
	* @author Liu Wenjie   
	* @date 2015-11-29 下午4:37:12 
	* @param workbook
	 * @param sheet 
	 * @param row 
	* @param sheetConfig
	* @param rowConfig
	* @param targetColumnConfig
	* @param cellKey
	* @param object
	* @param i
	* @param index  
	 * @param index2 
	*/ 
	private int createCurrentCell(Workbook workbook, Sheet sheet, Row row, SheetConfig sheetConfig, RowConfig rowConfig, ColumnConfig columnConfig, String cellKey, Object object, int currentRowNum, int currentColNum, int index) {
		//设置单元格样式
		CellStyle cellStyle = this.excelConfig.getCellStyle(workbook,sheetConfig,rowConfig,columnConfig);
		//创建单元格，并付上样式
		Integer colspan = columnConfig.getColspan();
		Cell cell = row.createCell(currentColNum++);
		if(cellStyle != null){
			cell.setCellStyle(cellStyle);
		}
		if(colspan != null && colspan > 1){
			for (int i = 0; i < colspan - 1 ; i++) {
				Cell emptyCell = row.createCell(currentColNum++);
	    		if(cellStyle != null){
	    			emptyCell.setCellStyle(cellStyle);
	    		}
			}
			sheet.addMergedRegion(new CellRangeAddress(currentRowNum, currentRowNum, currentColNum-colspan, currentColNum-1));
		}
		//根据配置设置当前对象的内容
		
		//设置单元格类型和值
		if(RowType.标题.equals(rowConfig.getType())){
			cell.setCellValue(cellKey);
		}else{
			Object value = null;
			if("@index".equalsIgnoreCase(columnConfig.getMappedBy())){
				value = new Integer(index+1);
			}else{
				value = columnConfig.getCellValue(object,cellKey);
				//如果使用字典，那么将字典值进行转化
				if(value instanceof String && columnConfig.isUseDic()){
					value = this.excelConfig.getDicValue((String)value,columnConfig);
				}
			}
			if(value != null){//控制时，不进行任何设置
				if (value instanceof Double) {
					cell.setCellValue((Double) value);
				}else if(value instanceof Integer){
					cell.setCellValue(1D*(Integer)value);
				}else if(value instanceof Date){
					cell.setCellValue((Date) value);
				}else if(value instanceof Boolean){
					cell.setCellValue((Boolean) value);
				}else if(value instanceof Calendar){
					cell.setCellValue((Calendar) value);
				}else if(value instanceof RichTextString){
					cell.setCellValue((RichTextString) value);
				}else {
					cell.setCellValue(String.valueOf(value));
				}
			}
		}
		return currentColNum;
	}

	/** 
	* @Description: 根据配置类型获取workBook类型
	* @author Liu Wenjie   
	* @date 2015-7-7 下午5:17:16 
	* @return  
	*/ 
	private Workbook getWorkBook() {
		if(StringUtils.isNotBlank(excelConfig.getBaseFile())){
			try {
				return WorkbookFactory.create(BasicExcelGenerator.class.getResourceAsStream(excelConfig.getBaseFile()));
			} catch (IOException e) {
				throw new BanaUtilException("初始化workBook出错",e);
			} catch (InvalidFormatException e) {
				throw new BanaUtilException("初始化workBook出错",e);
			}
		}
		if(ExcelType.XLS.equals(excelConfig.getType())){
			//03文档
			return new HSSFWorkbook();
		}
		//03以后文档
		return new XSSFWorkbook();
	}
	
	/** 
	* @Description: 根据输入流创建对应的excel对象
	* @author Liu Wenjie   
	* @date 2015-7-9 下午4:45:02 
	* @param is
	* @return  
	*/ 
	private Workbook getWorkBook(InputStream is) {
		try {
//			if(ExcelType.XLS.equals(excelConfig.getType())){
//				//03文档
//				return new HSSFWorkbook(is);
//			}
//			//03以后文档
//			return new XSSFWorkbook(is);
			return WorkbookFactory.create(is);
		} catch (IOException e) {
			throw new BanaUtilException("初始化workBook出错",e);
		} catch (InvalidFormatException e) {
			throw new BanaUtilException("初始化workBook出错",e);
		}
	}

	/**
	 * <p>Description: </p> 
	 * @author Liu Wenjie   
	 * @date 2015-7-2 上午11:09:34 
	 * @param inputStream
	 * @return 
	 * @see org.bana.common.util.poi.POIHXSSFGenerator#generatorObject(java.io.InputStream) 
	 */
	@Override
	public ExcelObject generatorObject(InputStream inputStream) {
		if(inputStream == null){
			return null;
		}
		if(excelConfig == null){
			throw new BanaUtilException("没有指定excelConfig");
		}
		//创建workBook，根据配置，读取内容
		Workbook workBook = getWorkBook(inputStream);
		List<SheetConfig> sheetConfigList = this.excelConfig.getSheetConfigList();
		if(sheetConfigList == null){
			return null;
		}
		ExcelObject excelObject = new ExcelObject();
		for (SheetConfig sheetConfig : sheetConfigList) {
			String name = sheetConfig.getName();
			Sheet sheet = workBook.getSheet(name);
			if(sheet == null){
				throw new BanaUtilException("没有找到指定的excel的sheet页 " + name);
			}
			List<? extends Object> readSheet = readSheet(sheet,sheetConfig);
			excelObject.putData(name, readSheet);
		}
		return excelObject;
		
	}

	/** 
	* @Description: 读取一个sheet页中的数据
	* @author Liu Wenjie   
	* @date 2015-7-9 下午5:22:38 
	* @param sheet
	* @param sheetConfig
	* @return  
	*/ 
	private List<? extends Object> readSheet(Sheet sheet,SheetConfig sheetConfig){
		//根据sheet的行配置文件，创建对应的行信息
		List<RowConfig> rowConfigList = sheetConfig.getRowConfigList();
		int currentRowNum = 0;
		if(rowConfigList == null){
			 return null;
		}
		List<Object> dataList = new ArrayList<Object>();
		//读取数据的列名list
		List<String> colNameList = null;
		for (RowConfig rowConfig : rowConfigList) {
			//获取当前的起始行数
			Integer rowIndex = rowConfig.getRowIndex();
			if(rowIndex != null && !rowIndex.equals(new Integer(-1))){
				//如果指定了特定的生成行数，则跳到指定的行进行生成
				currentRowNum = rowIndex - 1;
			}
			if(RowType.标题.equals(rowConfig.getType())){
				//获取一行记录或验证该行记录
				Row row = sheet.getRow(currentRowNum++);
				LOG.info("标题行，当前行数: " + currentRowNum);
				colNameList = readTitleRow(row,sheetConfig,rowConfig);
			}else{
				//根据excel内容，循环读取数据，如果遇到一行空数据，则停止此次读取
				boolean hasRecordValue = true;
				while(hasRecordValue){
					Row row = sheet.getRow(currentRowNum++);
					LOG.info("获取数据行。。。，当前行数: " + currentRowNum);
					if(row == null){
						hasRecordValue = false;
						break;
					}
					Object value = readDataRow(row,sheetConfig,rowConfig,colNameList);
					if(value == null){
						hasRecordValue = false;
					}else{
						if(Map.class.isInstance(value)){//返回值是否为一个map对象
							//如果是map对象
							LOG.info("返回值是一个Map对象");
						}else if(!value.getClass().getName().equals(rowConfig.getClassName())){
							throw new BanaUtilException("指定的数据类型 " + rowConfig.getClassName() + " 与传入的数据类型 " + value.getClass().getName() + " 不匹配");
						}
						dataList.add(value);
					}
				}
			}
			
		}
		return dataList;
	}

	/** 
	* @Description: 读取一行数据记录为一个指定的数据类型
	* @author Liu Wenjie   
	* @date 2015-7-9 下午5:25:32 
	* @param row
	* @param sheetConfig
	* @param rowConfig
	 * @param colNameList 
	* @return  
	*/ 
	private Object readDataRow(Row row, SheetConfig sheetConfig, RowConfig rowConfig, List<String> colNameList) {
		if(colNameList != null && !colNameList.isEmpty()){
			//如果返回对象是按照map列值获取的
			return readDataRowAsMap(row,sheetConfig,rowConfig,colNameList);
		}
		List<ColumnConfig> columnConfigList = rowConfig.getColumnConfigList();
		if(columnConfigList == null){
			return null;
		}
		int currentColumnNum = 0;
		Object object;
		try {
			object = Class.forName(rowConfig.getClassName()).newInstance();
		} catch (Exception e) {
			throw new BanaUtilException("使用" + rowConfig.getClassName() + " 名称创建类时失败 ",e);
		}
		boolean isEmpty = true;
		for (ColumnConfig columnConfig : columnConfigList) {
			Cell cell = row.getCell(currentColumnNum++);
			Object value = readCellValue(cell,columnConfig);
			if(value == null){
				continue;
			}
			isEmpty = false;
			columnConfig.setColumnValue(value,object);
			Integer colspan = columnConfig.getColspan();
			if(colspan != null && colspan > 1){
				for (int i = 0; i < colspan - 1; i++) {
					Cell emptyCell = row.getCell(currentColumnNum++);
					String stringCellValue = emptyCell.getStringCellValue();
					if(!StringUtils.isEmpty(stringCellValue)){
						throw new BanaUtilException("Excel中列值为 " + value +"的模板中要求有合并单元格，而实际的内容没有包含合并单元格，或内容不为空 ");
					}
				}
			}
		}
		if(isEmpty){
			return null;
		}
		return object;
	}

	/** 
	* @Description: 读取内容为一个Map对象
	* @author Liu Wenjie   
	* @date 2015-11-29 下午2:20:04 
	* @param row
	* @param sheetConfig
	* @param rowConfig
	* @param colNameList
	* @return  
	*/ 
	private Object readDataRowAsMap(Row row, SheetConfig sheetConfig, RowConfig rowConfig, List<String> colNameList) {
		Map<String,Object> oneData = new HashMap<String, Object>();
		boolean isEmpty = true;
		for (int i = 0; i < colNameList.size(); i++) {
			String colName = colNameList.get(i);
			Cell cell = row.getCell(i);
			ColumnConfig columnConfig = rowConfig.getColumnConfigMap().get(colName);
			//没有找到指定名字的columnConfig的时候，根据map值去获取对应的map对象的值
			if(columnConfig == null){
				String configName = this.excelConfig.getMutiConfigNameUseColName(colName);
				if(StringUtils.isBlank(configName)){
					throw new BanaUtilException("根据动态列名 “" + colName + "” 获取配置对象列的配置内容时没有获取到");
				}
				ColumnConfig columnConfigCol = rowConfig.getColumnConfigMap().get(configName);
				if(columnConfigCol == null){
					throw new BanaUtilException("没有找到动态列 “" + configName + "”  的列配置内容");
				}
				String mutiMapName = columnConfigCol.getMutiMap();
				if(StringUtils.isBlank(mutiMapName)){
					throw new BanaUtilException("动态列 “ " + configName + "”  的配置没有mutiMap属性，或属性为空");
				}
				columnConfig = rowConfig.getColumnConfigMap().get(mutiMapName);
				if(columnConfig == null){
					throw new BanaUtilException("动态列 “" + configName + "”  指定的mutiMap 值" + mutiMapName + " 没有配置信息");
				}
			}
			//动态列excel中不允许读取合并单元格的内容
			if(columnConfig.getColspan() != null && columnConfig.getColspan() > 1){
				throw new BanaUtilException("动态列excel中不允许读取合并单元格的内容，" + columnConfig.getName() + "Colspan 的值是" + columnConfig.getColspan());
			}
			Object value = readCellValue(cell,columnConfig);
			if(value != null){
				isEmpty = false;
			}
			oneData.put(colName, columnConfig.parseTypeValue(value));
		}
		//验证改行是否至少有一个数据，如果是空行，则返回空值
		if(isEmpty){
			return null;
		}else{
			return oneData;
		}
	}

	/** 
	* @Description: 读取一个单元格的值
	* @author Liu Wenjie   
	* @date 2015-7-13 下午2:06:05 
	* @param cell
	 * @param columnConfig 
	* @return  
	*/ 
	private Object readCellValue(Cell cell, ColumnConfig columnConfig) {
		if(cell == null){
			return null;
		}
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_FORMULA: ////公式型(公式类型和数值类型进行相同的处理)
			// cell.getCellFormula();
			try {
				return String.valueOf(cell.getNumericCellValue());
			} catch (IllegalStateException e) {
				return String.valueOf(cell.getRichStringCellValue());
			}
//			throw new BanaUtilException("当前上传文件不支持包含公式类型的单元格内容");
		case Cell.CELL_TYPE_NUMERIC://数值型(包括时间)
			if(DateUtil.isCellDateFormatted(cell)){//返回指定格式的时间字符串
				return cell.getDateCellValue();
			}
			if("date".equalsIgnoreCase(columnConfig.getType())){
				return cell.getDateCellValue();
			}
			Map<String, String> columnStyle = columnConfig.getStyle();
			if(columnStyle != null){
				String format = columnStyle.get("dataFormat");
				if(!StringUtils.isBlank(format)){
					DecimalFormat df = new DecimalFormat(format);
					return df.format(cell.getNumericCellValue());
				}
			}
			return String.valueOf(cell.getNumericCellValue());
		case Cell.CELL_TYPE_STRING://字符串类型
			return cell.getStringCellValue();
		case Cell.CELL_TYPE_BOOLEAN:////布尔值
			return cell.getBooleanCellValue();
		case Cell.CELL_TYPE_ERROR://错误
			throw new BanaUtilException("存在错误的表格格式");
		case Cell.CELL_TYPE_BLANK://空白
			return null;
		default:
			throw new BanaUtilException("存在无法解析的表单类型");
		}
	}

	/** 
	* @Description:  读取一行标题记录，并根据配置决定是否进行验证,
	* 				如果进行验证，将按照execl的数据列顺序返回标题行的表头集合，如果不验证，将返回空
	* @author Liu Wenjie   
	* @date 2015-7-9 下午5:25:23 
	* @param row
	* @param sheetConfig
	* @param rowConfig  
	*/ 
	private List<String> readTitleRow(Row row, SheetConfig sheetConfig, RowConfig rowConfig) {
		if(sheetConfig.isCheckTitle()){
			if(rowConfig.isMutiTitle()){
				//如果excel的列是可能多列动态设置，那么根据数据的配置进行解析
				List<String> colNameList = new ArrayList<String>();
				int currentColumnNum = 0;
				while(true){
					Cell cell = row.getCell(currentColumnNum++);
					if(cell == null){
						break;
					}
					String colName = cell.getStringCellValue();
					if(cell == null || StringUtils.isBlank(colName)){
						continue;
					}
					//添加每列的名称到一个集合
					colNameList.add(StringUtils.trimAll(colName));
				}
				//验证获取到的列名称中，是否包含所有指定的列名，如果都包含则是正确，如果不包含，必须都不包含，而且需要验证是否是指定范围内容的列，如果不是，则抛出错误
				if(checkMutiTitle(sheetConfig, rowConfig,colNameList)){
					return colNameList;
				}
			}else{
				List<ColumnConfig> columnConfigList = rowConfig.getColumnConfigList();
				if(columnConfigList != null){
					int currentColumnNum = 0;
					for (ColumnConfig columnConfig : columnConfigList) {
						String titleName = columnConfig.getName();
						Cell cell = row.getCell(currentColumnNum++);
						String cellValue = cell.getStringCellValue();
						if(StringUtils.isEmpty(cellValue,titleName) || !cellValue.equalsIgnoreCase(titleName)){
							throw new BanaUtilException("Excel中需要要列名 " + titleName +", 但得到的是" + cellValue);
						}
						Integer colspan = columnConfig.getColspan();
						if(colspan != null && colspan > 1){
							for (int i = 0; i < colspan - 1; i++) {
								Cell emptyCell = row.getCell(currentColumnNum++);
								if(emptyCell == null){
									throw new BanaUtilException("Excel中列名为“ " + titleName +" ”的模板中要求有 " + colspan + " 列合并单元格，而实际的内容没有包含合并单元格，或内容不符 ");
								}
								String stringCellValue = emptyCell.getStringCellValue();
								if(!StringUtils.isEmpty(stringCellValue) && !stringCellValue.equalsIgnoreCase(cellValue)){
									throw new BanaUtilException("Excel中列名为“ " + titleName +" ”的模板中要求有合并单元格，而实际的内容没有包含合并单元格，或内容不符 ");
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	/** 
	* @Description: 验证获取到的列名称中，是否包含所有指定的列名，如果都包含则是正确，如果不包含，必须都不包含，而且需要验证是否是指定范围内容的列，如果不是，则抛出错误
	* @author Liu Wenjie   
	* @date 2015-11-29 下午12:28:38 
	* @param sheetConfig
	* @param rowConfig
	* @param colNameList  
	*/ 
	private boolean checkMutiTitle(SheetConfig sheetConfig, RowConfig rowConfig, List<String> colNameList) {
		//验证是否包含重复的列
		List<String> findDuplicateList = CollectionUtils.findDuplicateList(colNameList);
		if(findDuplicateList != null && !findDuplicateList.isEmpty()){
			throw new BanaUtilException("发现了重复的列名" + findDuplicateList);
		}
		//验证获取到的列名称中，是否只包含所有指定的列名
		List<String> allColumnName = rowConfig.getAllColumnName();
		if(colNameList.containsAll(allColumnName)){
			//包含所有指定的列名，那么其他的列名也不能存在
			if(allColumnName.containsAll(colNameList)){
				//验证正确，使用的是全部对应的列名，没有动态列名
				return false;
			}else{
				throw new BanaUtilException("固定模板只允许的列名为" + allColumnName + ", 而在上传的文件中发现了不合法的列名 " + colNameList);
			}
		}
		//如果列名不包含所有列名，说明使用的是动态模板，动态模板不能包含动态指定的任何一列
		List<String> mutiColumnNameList = rowConfig.getMutiIncludeMapColumnName();
			//获取所有指定的动态列名
		Map<String, List<String>> mutiTitleMap = this.excelConfig.getMutiTitleMap();
		List<String> allowColumnList = new ArrayList<String>();
		if(mutiTitleMap != null){
			Set<Entry<String, List<String>>> entrySet = mutiTitleMap.entrySet();
			for (Entry<String, List<String>> entry : entrySet) {
				allowColumnList.addAll(entry.getValue());
			}
		}
		for (String colName : colNameList) {
			//不能包含mutiColumnName
			if(mutiColumnNameList.contains(colName)){
				throw new BanaUtilException("动态列名模板中，不能包含这些列名" + mutiColumnNameList + " 而解析的列名中为" + colNameList);
			}
			//如果所有列中也不包含此列的话，那么需要验证他必须在指定的map中
			if(!allColumnName.contains(colName) && !allowColumnList.contains(colName)){
				throw new BanaUtilException("不允许的动态列名 " + colName + " 所有允许的动态列名为" + allowColumnList);
			}
		}
		//通过所有的验证后，设置当前的数据读取为动态读取
		return true;
	}

	/** 
	* @Description:  设置配置项，生成Excel的配置方法
	* @author Liu Wenjie   
	* @date 2015-7-2 上午11:19:18 
	* @param excelConfig  
	*/ 
	public void setExcelConfig(ExcelConfig excelConfig) {
		this.excelConfig = excelConfig;
	}

	/**
	 * @Description: 属性 excelConfig 的get方法 
	 * @return excelConfig
	 */
	public ExcelConfig getExcelConfig() {
		return excelConfig;
	}
	
}
