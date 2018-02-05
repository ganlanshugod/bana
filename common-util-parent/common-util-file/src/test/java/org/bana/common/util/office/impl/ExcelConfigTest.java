/**
* @Company 青鸟软通   
* @Title: ExcelConfigTest.java 
* @Package org.bana.common.util.office.impl 
* @author Liu Wenjie   
* @date 2015-7-7 下午4:57:15 
* @version V1.0   
*/ 
package org.bana.common.util.office.impl;

import java.util.List;

import org.bana.common.util.office.config.SheetConfig;
import org.bana.common.util.office.impl.config.XmlExcelConfig;
import org.junit.Assert;
import org.junit.Test;

/** 
 * @ClassName: ExcelConfigTest 
 * @Description: 
 *  
 */
public class ExcelConfigTest {

	/**
	 * Test method for {@link org.bana.common.util.office.config.ExcelConfig#init()}.
	 */
	@Test
	public void testInit() {
		XmlExcelConfig excelConfig = new XmlExcelConfig();
		excelConfig.setConfigFile("/office/excelConfig.xml");
		excelConfig.init();
		Assert.assertEquals("测试名称", excelConfig.getName());
		Assert.assertEquals("xls", excelConfig.getType().getExtName());
		List<SheetConfig> sheetConfigList = excelConfig.getSheetConfigList();
		Assert.assertNotNull(sheetConfigList);
		SheetConfig sheetConfig = sheetConfigList.get(0);
		Assert.assertEquals("sheet1-test",sheetConfig.getName());
		System.out.println(excelConfig);
	}

}
