package org.bana.common.util.poi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.bana.common.util.basic.CollectionUtils;
import org.bana.common.util.poi.POIExcelGeneratorTest.Data;
import org.bana.common.util.poi.POIExcelGeneratorTest.Item;
import org.bana.common.util.poi.POIExcelGeneratorTest.Item2;
import org.bana.common.util.poi.template.TemplatePOIExcelGenerator;
import org.bana.common.util.poi.template.param.TemplateExcelConfig;

public class CustomTemplatePOIExcelGenerator extends TemplatePOIExcelGenerator {

	@Override
	protected void doCreateSheet(Workbook workbook, Sheet sheet, TemplateExcelConfig excelConfig, Object excelData,
			int index) {
		System.out.println("customer do create sheet");
		
		Data data = (Data)excelData;
		List<Item> itemList = data.getItemList();
		if(CollectionUtils.isEmpty(itemList)) {
			return;
		}
		int rows = 0;
		for (Item item : itemList) {
			List<Item2> itemList2 = item.getItemList2();
			if(CollectionUtils.isNotEmpty(itemList2)) {
				rows += itemList2.size();
			}else {
				rows +=1;
			}
		}
		
		int startRow = 11;
		// 获取原合并单元格的配置
		List<CellRangeAddress> newReginList = new ArrayList<>();
		int sheetMergeCount = sheet.getNumMergedRegions(); 
		Set<Integer> removeMerge = new HashSet<>();
		for (int mi = 0; mi < sheetMergeCount; mi++) {
			CellRangeAddress cellRangeAddress = sheet.getMergedRegion(mi);
			if(cellRangeAddress.getFirstRow() == startRow && cellRangeAddress.getLastRow() == startRow) {
				// 需要复制合并单元格，横向的单元格合并
				for (int i = 0; i < rows; i++) {
					CellRangeAddress region1 = new CellRangeAddress(startRow+i, startRow+i, cellRangeAddress.getFirstColumn(), cellRangeAddress.getLastColumn());
					addMergeRegionSafe(newReginList, region1);
				}
				removeMerge.add(mi);
			}
		}
		sheet.removeMergedRegions(removeMerge);
		// 添加指定的行数
		sheet.shiftRows(startRow+1, sheet.getLastRowNum(), rows-1, true, false);

		// 复制样式和填写数据
		int addRows = 0;
		Row sourceRow = sheet.getRow(startRow);
		for (int i=0; i < itemList.size() ; i++) {
//			startRow = startRow + 1;
			int currentRow = startRow + addRows;
			Row targetRow;
			if(addRows > 0) {
				targetRow = sheet.createRow(currentRow);
				copyRowsStyle(sourceRow, targetRow);
			}else {
				targetRow = sheet.getRow(currentRow);
			}
			// 当前行数据
			Item item = itemList.get(i);
			List<Item2> itemList2 = item.getItemList2();
			// 样品类型
			Cell cell1 = targetRow.getCell(1);
			cell1.setCellValue(item.getValue());
			// 样品名称 
			Cell cell2 = targetRow.getCell(2);
			cell2.setCellValue(item.getValue());
			if(CollectionUtils.isNotEmpty(itemList2)) {
				for (Item2 item2 : itemList2) {
					Row targetRow2 = sheet.getRow(startRow + addRows);
					if(targetRow2 == null) {
						targetRow2 = sheet.createRow(startRow + addRows);
						copyRowsStyle(sourceRow, targetRow2);
					}
					// 样品编号
					Cell cell4 = targetRow2.getCell(4);
					cell4.setCellValue(item2.getValue());
					addRows ++;
				}
				if(itemList2.size() > 1) {
					// 增加每列的合并单元格,
					// 样品类型
					addMergeRegionSafe(newReginList,new CellRangeAddress(currentRow, currentRow+itemList2.size()-1, 1,1));
					// 样品名称
					addMergeRegionSafe(newReginList,new CellRangeAddress(currentRow, currentRow+itemList2.size()-1, 2,3));
				}
			}else {
				addRows ++;
			}
			
		}
		
		// 复制合并单元格
		if(CollectionUtils.isNotEmpty(newReginList)) {
			newReginList.forEach(item -> {
				sheet.addMergedRegion(item);
			});
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
}
