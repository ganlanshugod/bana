package org.bana.common.util.poi.template.parser;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.bana.common.util.basic.BeanToMapUtil;
import org.bana.common.util.poi.BanaPoiException;
import org.bana.common.util.velocity.SimpleVelocityEngineFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: TextTemplateParser
 * @Description: 解析器
 * @author liuwenjie
 */
public class VmTemplateParser implements TemplateParser {

	private static final Logger LOG = LoggerFactory.getLogger(VmTemplateParser.class);
	
	@Override
	public void parseTemplate(Sheet sheet,Object excelData) {
		// 循环每一行进行数据替换
		int firstRowNum = sheet.getFirstRowNum();
		int lastRowNum = sheet.getLastRowNum();
		System.out.println(firstRowNum +" ~ " + lastRowNum);
		for (int rowIndex = firstRowNum; rowIndex < lastRowNum; rowIndex++) {
			LOG.info("解析 第{}行",rowIndex);
			Row row = sheet.getRow(rowIndex);
			short lastCellNum = row.getLastCellNum();
			short firstCellNum = row.getFirstCellNum();
			for(short colIx=firstCellNum; colIx<lastCellNum; colIx++) {
				Cell cell = row.getCell(colIx);
			   if(cell != null) {
				   //print(cell);
				   boolean hasForEach = hasForEach(cell);
				   if(hasForEach) {
					   ForEachResult result = parseForEachRow(excelData,sheet, row, cell,colIx);
					   colIx = result.colIndex;
					   rowIndex += (result.rowNum-1);
					   lastRowNum += (result.rowNum-1);
				   }else {
					   this.parseTemplateCell(cell, row, excelData);
				   }
			   }
			 }
			System.out.println();
		}
	}
	
	private String numSplit = ":_:_:";
	private String colSplit = ",_,  ";
	
	static class ForEachResult{
		short colIndex;
		int rowNum;
	}
	
	private ForEachResult parseForEachRow(Object excelData,Sheet sheet,Row sourceRow,Cell startCell,short startForEachIndex){
		int startRow = sourceRow.getRowNum();
		short lastCellNum = sourceRow.getLastCellNum();
		short endForEachIndex = lastCellNum;
		String startCellValue = startCell.getStringCellValue();
		StringBuffer vm = new StringBuffer(startCellValue).append(numSplit+startForEachIndex+colSplit);
		int fnNum = getNum(startCellValue,keyFn);
		for(short i = (short)(startForEachIndex+1); i< lastCellNum; i++) {
			Cell cell2 = sourceRow.getCell(i);
			CellType cellType = cell2.getCellType();
			LOG.info("循环列 {} ,第{}位 ",cellType,i);
			if(CellType.STRING.equals(cell2.getCellType())) {
				String cellValue = cell2.getStringCellValue();
				// 验证有几个end符号
				int endNum = getNum(cellValue,end);
				int newFun = getNum(cellValue,keyFn);
				System.out.println(cellValue + " ," + endNum + "," + newFun); 
				if(endNum - newFun > 0) {
					vm.append(cellValue.replace("#end", numSplit+i+ colSplit +"\n#end"));
				}else if(endNum - newFun < 0) {
					vm.append("\t").append(cellValue).append(numSplit+i+colSplit);
				}else {
					vm.append(cellValue).append(numSplit+i+colSplit);
				}
				
				// 验证有几个end符号
				fnNum -= endNum;
				fnNum += newFun;
				if(fnNum <= 0) {
					endForEachIndex = i;
					break;
				}
			}
		}
		LOG.info("endForEachIndex: {}" , endForEachIndex);
		LOG.info("循环模板数据为:{}",vm.toString());
		
		if(fnNum != 0) {
			throw new BanaPoiException("模板不正确，foreach行必须有对等的#end");
		}
		String parserString = parserString(vm.toString(),excelData);
		LOG.info("解析结果为；\n{}" , parserString);
		parserString = parserString.replaceAll("\n", "").replaceAll("\t", "");
		String[] split = parserString.split(colSplit);
		List<RowData> rowList = new ArrayList<>();
		
		// 解析表格数据
		parseRow(startForEachIndex,split,rowList,0);
		LOG.info("解析数据一共{}行, 数据为{}",rowList.size(),rowList);
		// 输出表格数据
//		writeForEachTableData(rowList);
		if(!rowList.isEmpty()) {
			int rows = rowList.size();
			int addRows = 0;
			// 添加指定的行数
			sheet.shiftRows(startRow+1, sheet.getLastRowNum(), rows-1, true, false);
			for (int i=0; i < rowList.size() ; i++) {
				int currentRow = startRow + addRows;
				Row targetRow;
				if(addRows > 0) {
					targetRow = sheet.createRow(currentRow);
					copyRowsStyle(sourceRow, targetRow);
				}else {
					targetRow = sheet.getRow(currentRow);
				}
				// 当前行数据
				RowData rowItem = rowList.get(i);
				for (ColData cellItem : rowItem.columnList) {
					// 每列的数据
					Cell cell1 = targetRow.getCell(cellItem.colIndex);
					cell1.setCellValue(cellItem.value);
				}
				addRows++;
			}
		}
		ForEachResult result = new ForEachResult();
		result.colIndex = endForEachIndex;
		result.rowNum = rowList.size();
		return result;
		
	}
	
	private void copyRowsStyle(Row sourceRow,Row targetRow) {
		targetRow.setHeight(sourceRow.getHeight());
		// 复制每行的数据
		for (short m = sourceRow.getFirstCellNum(); m < sourceRow.getLastCellNum(); m++) {
			Cell sourceCell = sourceRow.getCell(m);
			Cell targetCell = targetRow.createCell(m);
			targetCell.setCellStyle(sourceCell.getCellStyle());
		}
	}
	
	private int parseRow(short startColIndex,String[] split,List<RowData> rowList,int cusor) {
		RowData currentRow = null;
		int lastIndex = 0;
		short maxEndIndex = 0;
		if(!rowList.isEmpty()) {
			List<ColData> lastRowColList = rowList.get(rowList.size()-1).columnList;
			ColData lastRowLastCol = lastRowColList.get(lastRowColList.size()-1);
			short firstRowfirstColIndex = rowList.get(0).columnList.get(0).colIndex;
			// 如果是从第一个开始的，则不通过第一个开始
			if(startColIndex != firstRowfirstColIndex) {
				maxEndIndex = lastRowLastCol.colIndex;
			}
		}
		for (int i = cusor; i < split.length; i++) {
			String string = split[i];
			String[] split3 = string.split(numSplit);
			ColData colData = new ColData();
			colData.colIndex = Short.parseShort(split3[1]);
			colData.value = split3[0];
			short currentIndex = colData.colIndex;
			// 当前处理的比当前循环的的小，则不能继续处理，说明开启了一个新的循环，需要启用新的一行进行处理和保存
			if(currentIndex < startColIndex) {
				return --i;
			}
			
			if(maxEndIndex != 0 && currentIndex > maxEndIndex) {
				return --i;
			}
			if(lastIndex < currentIndex) {
				if(currentRow == null || currentIndex == startColIndex) {
					currentRow = new RowData();
					rowList.add(currentRow);
					System.out.println("");
				}
				System.out.print(","+startColIndex + "-" + lastIndex + "-" + currentIndex + "-" + maxEndIndex);
				currentRow.addCol(colData);
				lastIndex = currentIndex;
			}else {
				// 如果递归去查询，则当前的并没有写入到当前行中，所以不能变更lastIndex
				i = parseRow(currentIndex, split, rowList, i);
			}
		}
		return split.length;
	}
	
	public static class RowData{
		List<ColData> columnList = new ArrayList<>();
		
		public void addCol(ColData colData){
			this.columnList.add(colData);
		}

		@Override
		public String toString() {
			return "\nRowData [columnList=" + columnList + "]";
		}
	}
	
	public static class ColData{
		short colIndex;
		String value;
		@Override
		public String toString() {
			return "ColData [colIndex=" + colIndex + ", value=" + value + "]";
		}
	}
	
	private static Pattern forEach = Pattern.compile("#foreach");
	private static Pattern end = Pattern.compile("#end");
	private static Pattern keyFn = Pattern.compile("#(foreach)|(if)");
	
	public static void main(String[] args) {
		String str = "#if(${velocityCount}>0) 1 #else 0 #end";
		String str2 = "$ele.code";
		System.out.println(getNum(str,keyFn));
		System.out.println(getNum(str2,keyFn));
		print(1);
	}
	
	private static void print(int size){
		if(size == 10) {
			return;
		}
		if(size < 5) {
			System.out.print(size);
			print(++size);
		}
		System.out.println(size);
	}
	
	private int getEndNum(Cell cell){
		CellType cellType = cell.getCellType();
		if(CellType.STRING.equals(cellType)) {
			String cellValue = cell.getStringCellValue();
			return getNum(cellValue,end);
		}
		return 0;
	}
	
	private int getForEachNum(Cell cell) {
		CellType cellType = cell.getCellType();
		if(CellType.STRING.equals(cellType)) {
			String cellValue = cell.getStringCellValue();
			return getNum(cellValue,forEach);
		}
		return 0;
	}
	
	public static int getNum(String cellValue,Pattern p) {
		int i = 0;
		Matcher matcher = p.matcher(cellValue);
		while(matcher.find()) {
			i ++;
		}
		return i;
	}
	
	public static boolean hasForEach(Cell cell) {
		CellType cellType = cell.getCellType();
		if(CellType.STRING.equals(cellType)) {
			String cellValue = cell.getStringCellValue();
			return has(cellValue,forEach);
		}
		return false;
	}
	
	public static boolean has(String cellValue,Pattern p) {
		Matcher matcher = p.matcher(cellValue);
		return matcher.find();
	}
	
	
	public void parseTemplateCell(Cell cell, Row row, Object data) {
		CellType cellType = cell.getCellType();
		switch (cellType) {
		case _NONE:
		case BLANK:
			break;
		case BOOLEAN:
			LOG.warn("BOOLEAN类型的单元格，不支持解析 {} ,{},{}",cell.getBooleanCellValue() , row.getRowNum(),cell.getColumnIndex());
			break;
		case STRING:
			String stringCellValue = cell.getStringCellValue();
			String parserString = parserString(stringCellValue, data);
			if(!parserString.equals(stringCellValue)) {
				cell.setCellValue(parserString);
			}
			break;
		case NUMERIC:
			LOG.warn("数字类型的单元格，不支持解析 {} ,{},{}",cell.getNumericCellValue() , row.getRowNum(),cell.getColumnIndex());
			break;
		case FORMULA:
			LOG.warn("公式类型的单元格，不支持解析 {} ,{},{}",cell.getCellFormula() , row.getRowNum(),cell.getColumnIndex());
			break;
		case ERROR:
			String info = String.format("这是个奇怪的解析错误单元格，第 %s 行，第 %s 列", row.getRowNum(), cell.getColumnIndex());
			LOG.warn(info);
			break;
		default:
			throw new BanaPoiException("奇怪的事情，这个单元格不在计划解析的类型中");
		}

	}
	
	private void print(Cell cell) {
		CellType cellType = cell.getCellType();
		switch (cellType) {
		case _NONE:
		case BLANK:
			break;
		case BOOLEAN:
			System.out.print(cell.getBooleanCellValue());
			break;
		case FORMULA:
			System.out.print(cell.getCellFormula());
			break;
		case NUMERIC:
			System.out.print(cell.getNumericCellValue());
			break;
		case STRING:
			System.out.print(cell.getStringCellValue());
			break;
		case ERROR:
			System.out.print(cell.getErrorCellValue());
			break;
		default:
			break;
		}
	}
	
	
	private String parserString(String template,Object data) {
		VelocityEngine velocityEngine = SimpleVelocityEngineFactory.getVelocityEngine();
		Map<String, Object> contextMap;
		if(data instanceof Map) {
			contextMap = (Map<String, Object>)data;
		}else {
			contextMap = BeanToMapUtil.convertBean(data);
		}
		VelocityContext context = new VelocityContext(contextMap);
		StringWriter writer = new StringWriter();
		velocityEngine.evaluate(context, writer, "", template); 
		return writer.toString();
	}

}
