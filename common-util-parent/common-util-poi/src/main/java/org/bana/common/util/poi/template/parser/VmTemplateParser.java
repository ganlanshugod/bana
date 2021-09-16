package org.bana.common.util.poi.template.parser;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
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
			LOG.debug("解析 第{}行",rowIndex);
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
	private String colSplit = ", , ,";
	
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
			LOG.debug("循环列 {} ,第{}位 ",cellType,i);
			if(CellType.STRING.equals(cell2.getCellType())) {
				String cellValue = cell2.getStringCellValue();
				// 验证有几个end符号
				int endNum = getNum(cellValue,end);
				int newFun = getNum(cellValue,keyFn);
				System.out.println(cellValue + " ," + endNum + "," + newFun); 
				if(endNum - newFun > 0) {
					// 匹配最后一个end，把最后一个end进行更新? 匹配最后一个的 regx /#end(?!.*#end)/  实际是匹配第一个
					vm.append(cellValue.replaceFirst("#end", numSplit+i+ colSplit +"\n#end"));
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
		LOG.debug("endForEachIndex: {}" , endForEachIndex);
		LOG.debug("循环模板数据为:{}",vm.toString());
		
		if(fnNum != 0) {
			throw new BanaPoiException("模板不正确，foreach行必须有对等的#end");
		}
		String parserString = parserString(vm.toString(),excelData);
		LOG.debug("解析结果为；\n{}" , parserString);
		parserString = parserString.replaceAll("\n", "").replaceAll("\t", "").trim();
		// 数据不为空的情况下继续解析
		if(StringUtils.isNotBlank(parserString)) {
			String[] split = parserString.split(colSplit);
			List<RowData> rowList = new ArrayList<>();
			
			// 解析表格数据
			System.out.println(Arrays.toString(split));
			parseRow(startForEachIndex,split,rowList,0);
			LOG.debug("解析数据一共{}行, 数据为{}",rowList.size(),rowList);
			// 输出表格数据
			writeForEachRows(sheet, sourceRow, startRow, rowList);
			ForEachResult result = new ForEachResult();
			result.colIndex = endForEachIndex;
			result.rowNum = rowList.size();
			return result;
		}else {
			// 如果数据为空的情况下，删除当前行数据
			deleteRow(sheet, startRow);
			ForEachResult result = new ForEachResult();
			result.colIndex = 0;
			result.rowNum = 0;
			return result;
		}
	}
	
	private void deleteRow(Sheet sheet,int rowNum){
		// 删除一行中的合并单元格
		int sheetMergeCount = sheet.getNumMergedRegions(); 
		Set<Integer> removeMerge = new HashSet<>();
		for (int mi = 0; mi < sheetMergeCount; mi++) {
			CellRangeAddress cellRangeAddress = sheet.getMergedRegion(mi);
			if(cellRangeAddress.getFirstRow() == rowNum && cellRangeAddress.getLastRow() == rowNum) {
				removeMerge.add(mi);
			}
		}
		sheet.removeMergedRegions(removeMerge);
		//此方法只能删除行内容
		sheet.removeRow(sheet.getRow(rowNum));
		int lastRowNum = sheet.getLastRowNum();
		//此方法是将余下的行向上移
		sheet.shiftRows(rowNum+1, lastRowNum, -1);
	}

	private void writeForEachRows(Sheet sheet, Row sourceRow, int startRow, List<RowData> rowList) {
		if(!rowList.isEmpty()) {
			int rows = rowList.size();
			// 可能需要合并单元格的列
			Set<Short> lastSet = new HashSet<>();
			// 确定要合并的单元格列
			List<CellRangeAddress> rangeList = new ArrayList<>();
			// 准备要合并的单元格列
			List<CellRangeAddress> readyList = new ArrayList<>();
			// 横向要合并的单元格列
			List<CellRangeAddress> exsitList = new ArrayList<>();
			
			// 1.0 获取已经存在的横向合并单元格需求
			int sheetMergeCount = sheet.getNumMergedRegions(); 
			Set<Integer> removeMerge = new HashSet<>();
			for (int mi = 0; mi < sheetMergeCount; mi++) {
				CellRangeAddress cellRangeAddress = sheet.getMergedRegion(mi);
				if(cellRangeAddress.getFirstRow() == startRow && cellRangeAddress.getLastRow() == startRow) {
					// 需要复制合并单元格，横向的单元格合并
					for (int i = 0; i < rows; i++) {
						CellRangeAddress region1 = new CellRangeAddress(startRow+i, startRow+i, cellRangeAddress.getFirstColumn(), cellRangeAddress.getLastColumn());
						exsitList.add(region1);
						addMergeRegionSafe(rangeList, region1);
					}
					removeMerge.add(mi);
				}
			}
			sheet.removeMergedRegions(removeMerge);
			
			int addRows = 0;
			// 添加指定的行数
			if(rows > 1) {
				sheet.shiftRows(startRow+1, sheet.getLastRowNum(), rows-1, true, false);
			}
			
			List<CellRangeAddress> newRangeList = new ArrayList<>();
			for (int i=0; i < rowList.size() ; i++) {
				int currentRow = startRow + addRows;
				Row targetRow;
				if(addRows > 0) {
					targetRow = sheet.createRow(currentRow);
					copyRowsStyle(sourceRow, targetRow);
				}else {
					targetRow = sheet.getRow(currentRow);
				}
				
				Set<Short> curretIndxSet = new HashSet<>();
				// 当前行数据
				RowData rowItem = rowList.get(i);
				for (ColData cellItem : rowItem.columnList) {
					// 每列的数据
					Cell cell1 = targetRow.getCell(cellItem.colIndex);
					cell1.setCellValue(cellItem.value);
					curretIndxSet.add(cellItem.colIndex);
				}
				// 上一行存在， 当前行不存在 && 可能的合并区域不存在， 这类添加到可能区域中，添加时，参考是否已经存在横向合并，如果存在，则增加跨行区间
				// 上一行存在， 当前行不存在 但是可能的合并区域存在， 这类改变合并区域到当前行
				List<CellRangeAddress> newReadyList = new ArrayList<>();
				lastSet.stream().filter(col1 -> !curretIndxSet.contains(col1)).forEach(colIndx ->{
					// 存在
					boolean hasReady = false;
					for (CellRangeAddress ready : readyList) {
						if(ready.containsColumn(colIndx)){
							hasReady = true;
							ready.setLastRow(currentRow);
						}
					}
					// 没有ready的情况下，需要添加到ready中
					if(!hasReady) {
						Optional<CellRangeAddress> findFirst = exsitList.stream().filter(item->item.containsColumn(colIndx)).findFirst();
						CellRangeAddress ready = new CellRangeAddress(currentRow-1,currentRow,colIndx,colIndx);
						if(findFirst.isPresent()) {
							CellRangeAddress exist = findFirst.get();
							ready.setFirstColumn(exist.getFirstColumn());
							ready.setLastColumn(exist.getLastColumn());
						}
						newReadyList.add(ready);
					}
				});
				// 上一行不存在，但是当前行存在， 则查看可能合并区是否存在（应该存在),把可能的合并行添加的确定合并区域中
				for (Short col : curretIndxSet) {
					if(!lastSet.contains(col)) {
						Optional<CellRangeAddress> findFirst = readyList.stream().filter(ready->ready.containsColumn(col)).findFirst();
						if(findFirst.isPresent()) {
							CellRangeAddress cellRangeAddress = findFirst.get();
							// 说明上一行是他的终结行
							cellRangeAddress.setLastRow(currentRow-1);
							newRangeList.add(cellRangeAddress);
						}
					}
				}
				readyList.removeAll(newRangeList);
				readyList.addAll(newReadyList);

				lastSet = curretIndxSet;
				addRows++;
			}
			// 最后把所有ready的填写到range里面
			for (CellRangeAddress item : readyList) {
				item.setLastRow(startRow + addRows -1);
			}
			newRangeList.addAll(readyList);
			// 执行合并单元格操作
			addMergeRegionSafe(rangeList, newRangeList);
			// 复制合并单元格
			if(CollectionUtils.isNotEmpty(rangeList)) {
				rangeList.forEach(item -> {
					sheet.addMergedRegion(item);
				});
			}
		}
	}
	
	private void addMergeRegionSafe(List<CellRangeAddress> rangeList,List<CellRangeAddress> newRangeList) {
		for (CellRangeAddress cellRangeAddress : newRangeList) {
			addMergeRegionSafe(rangeList, cellRangeAddress);
		}
	}
	private void addMergeRegionSafe(List<CellRangeAddress> rangeList,CellRangeAddress range) {
		List<CellRangeAddress> collect = rangeList.stream().filter(item -> 
			item.intersects(range)
		).collect(Collectors.toList());
		if(!collect.isEmpty()) {
			rangeList.removeAll(collect);
		}
		rangeList.add(range);
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
	
//	public static void main(String[] args) {
//		String str = "#if(${velocityCount}>0) 1 #else 0 #end";
//		String str2 = "$ele.code";
//		System.out.println(getNum(str,keyFn));
//		System.out.println(getNum(str2,keyFn));
//		print(1);
//		
//		CellRangeAddress address = new CellRangeAddress(1,2,3,4);
//		CellRangeAddress address2 = new CellRangeAddress(3,4,5,6);
//		CellRangeAddress address3 = new CellRangeAddress(3,4,5,6);
//		List<CellRangeAddress> list = new ArrayList<>();
//		list.add(address);
//		list.add(address2);
//		list.add(address3);
//		List<CellRangeAddress> list2 = new ArrayList<>();
//		list2.add(address);
//		list2.add(address2);
//		System.out.println(list.size());
//		System.out.println(list2.size());
//		list.removeAll(list2);
//		System.out.println(list.size());
//		System.out.println(list2.size());
//		
//	}
	
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
			RichTextString richStringCellValue = cell.getRichStringCellValue();
			String stringCellValue = richStringCellValue.toString();
			String parserString = parserString(stringCellValue, data);
			if(!parserString.equals(stringCellValue)) {
				if(richStringCellValue instanceof XSSFRichTextString) {
//					XSSFRichTextString xxsString = (XSSFRichTextString)richStringCellValue;
					XSSFRichTextString newString = new XSSFRichTextString(parserString);
					cell.setCellValue(newString);
				}else if(richStringCellValue instanceof HSSFRichTextString) {
//					HSSFRichTextString hssfString = (HSSFRichTextString)richStringCellValue;
					HSSFRichTextString newValue = new HSSFRichTextString(parserString);
					cell.setCellValue(newValue);
				}
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
