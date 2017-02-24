/**
* @Company 艾美伴行   
* @Title: TestGeneratorExcelUseFile.java 
* @Package org.bana.common.util.office.impl 
* @author liuwenjie   
* @date 2016-6-8 上午11:42:58 
* @version V1.0   
*/ 
package org.bana.common.util.office.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;

/** 
 * @ClassName: TestGeneratorExcelUseFile 
 * @Description: 测试使用一个文件模板来创建excel数据并导出
 *  
 */
public class TestGeneratorExcelUseFile {
	String filePath = "D:/学生成绩单模版-科目横版-v1.1.xls";
	String outPutFile = "D:/test.xls";

	@Test
	public void TestExcel() throws InvalidFormatException, IOException{
		FileInputStream is = new FileInputStream(filePath);
		Workbook workbook  = WorkbookFactory.create(is);
		Sheet sheet = workbook.createSheet("内容测试");
		Row row = sheet.createRow(0);
		Cell createCell = row.createCell(0);
		createCell.setCellValue("1234");
		workbook.write(new FileOutputStream(outPutFile));
	}
}
