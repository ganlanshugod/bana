/**
* @Company 青鸟软通   
* @Title: BasicExcelGeneratorTest.java 
* @Package org.bana.common.util.office.impl 
* @author Liu Wenjie   
* @date 2015-7-9 下午12:15:00 
* @version V1.0   
*/ 
package org.bana.common.util.office.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bana.common.util.office.ExcelObject;
import org.bana.common.util.office.impl.config.ExcelConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** 
 * @ClassName: BasicExcelGeneratorTest 
 * @Description: 测试生成Excel的方法
 *  
 */
public class BasicExcelGeneratorTest {
	private BasicExcelGenerator excelGenerator;
	@Before
	public void init(){
		excelGenerator = new BasicExcelGenerator();
		//构造excelConfig
		ExcelConfig excelConfig = new ExcelConfig();
		excelConfig.setConfigFile("/office/excelConfig.xml");
		excelConfig.init();
		excelGenerator.setExcelConfig(excelConfig);
	}
	
	@Test
	public void testInit(){
		
	}
	
	@Test
	public void testReadExcel2() throws IOException{
		ExcelConfig excelConfig = new ExcelConfig();
		excelConfig.setConfigFile("/office/excelConfigStudentScore.xml");
		excelConfig.init();
		excelGenerator.setExcelConfig(excelConfig);
		FileInputStream inputStream = new FileInputStream(new File("C:/Users/liuwenjie/Desktop/成绩上传的测试excel.xls"));
		
		ExcelObject generatorObject = excelGenerator.generatorObject(inputStream);
		Assert.assertNotNull(generatorObject);
		List<? extends Object> data = generatorObject.getData("学生成绩单");
//		System.out.println();
//		Assert.assertEquals(1000, data.size());
//		System.out.println(data.get(500));
	}
	
	@Test
	public void testReadExcel() throws IOException {
		FileInputStream inputStream = new FileInputStream(new File("D:/test.xls"));
		ExcelObject generatorObject = excelGenerator.generatorObject(inputStream);
		Assert.assertNotNull(generatorObject);
		List<? extends Object> data = generatorObject.getData("sheet1-test");
		Assert.assertEquals(1000, data.size());
		System.out.println(data.get(500));
	}
	
	@Test
	public void testReadMutiExcel() throws IOException {
		ExcelConfig excelConfig = new ExcelConfig();
		excelConfig.setConfigFile("/office/excelMutiConfig.xml");
		excelConfig.init();
		Map<String,List<String>> mutiMap = new HashMap<String, List<String>>();
		mutiMap.put("性别", Arrays.asList("男","女"));
		excelConfig.setMutiTitleMap(mutiMap);
		excelGenerator.setExcelConfig(excelConfig);
		FileInputStream inputStream = new FileInputStream(new File("D:/test.xls"));
		ExcelObject generatorObject = excelGenerator.generatorObject(inputStream);
		Assert.assertNotNull(generatorObject);
		List<? extends Object> data = generatorObject.getData("sheet1-test");
		Assert.assertEquals(1000, data.size());
		System.out.println(data.get(500));
	}
	
	@Test
	public void testReadMutiExcel2() throws IOException {
		ExcelConfig excelConfig = new ExcelConfig();
		excelConfig.setConfigFile("/office/excelConfigStudentScore.xml");
		excelConfig.init();
		Map<String,List<String>> mutiMap = new HashMap<String, List<String>>();
		mutiMap.put("科目", Arrays.asList("语文","数学","英语","总分","排名"));
		excelConfig.setMutiTitleMap(mutiMap);
		excelGenerator.setExcelConfig(excelConfig);
		FileInputStream inputStream = new FileInputStream(new File("D:/成绩模版－横版.xls"));
		ExcelObject generatorObject = excelGenerator.generatorObject(inputStream);
		Assert.assertNotNull(generatorObject);
		List<? extends Object> data = generatorObject.getData("学生成绩单");
		Assert.assertEquals(2007, data.size());
		System.out.println(data.get(500));
	}

	/**
	 * Test method for {@link org.bana.common.util.office.impl.BasicExcelGenerator#generatorExcel(java.io.OutputStream, org.bana.common.util.office.ExcelObject)}.
	 * @throws IOException 
	 */
	@Test
	public void testGeneratorExcel() throws IOException {
		FileOutputStream outputStream = new FileOutputStream(new File("D:/test.xls"));
		ExcelObject excelObject = new ExcelObject();
		List<TestData> dataList = new ArrayList<TestData>();
		addDate(dataList);
		excelObject.putData("sheet1-test", dataList);
		excelGenerator.generatorExcel(outputStream, excelObject);
		outputStream.close();
		System.out.println("样式数量为" + excelGenerator.excelConfig.getCellStyleSize());
	}
	
	@Test
	public void testGeneratorMutiExcel() throws IOException {
		ExcelConfig excelConfig = new ExcelConfig();
		excelConfig.setConfigFile("/office/excelMutiConfig.xml");
		excelConfig.init();
		Map<String,List<String>> mutiMap = new HashMap<String, List<String>>();
		mutiMap.put("性别", Arrays.asList("男","女"));
//		excelConfig.setMutiTitleMap(mutiMap);
		excelGenerator.setExcelConfig(excelConfig);
		FileOutputStream outputStream = new FileOutputStream(new File("D:/test3.xls"));
		ExcelObject excelObject = new ExcelObject();
		List<TestData> dataList = new ArrayList<TestData>();
		addDate(dataList);
//		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
//		addMapData(dataList);
		excelObject.putData("sheet1-test", dataList);
		excelGenerator.generatorExcel(outputStream, excelObject);
		outputStream.close();
		System.out.println("样式数量为" + excelGenerator.excelConfig.getCellStyleSize());
	}
	
	/** 
	* @Description: TODO (这里用一句话描述这个类的作用)
	* @author Liu Wenjie   
	* @date 2015-11-29 下午4:50:49 
	* @param dataList  
	*/ 
	private void addMapData(List<Map<String, Object>> dataList) {
		Random random = new Random();
		for (int i = 0; i < 1000; i++) {
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("姓名", "name" + i + ":" + random.nextInt(100));
			map.put("男", random.nextInt(100));
			map.put("女",random.nextInt(100));
			map.put("住址", "address"+i+":" + random.nextInt(100));
			map.put("日期", new Date());
			dataList.add(map);
		}
	}

	private void addDate(List<TestData> dataList){
		Random random = new Random();
		for (int i = 0; i < 1000; i++) {
			TestData testData = new TestData();
			testData.setAddress("地址内容，看看"+i+":" + random.nextInt(100));
			testData.setAge(random.nextInt(100));
			testData.setName("name"+i+":" + random.nextInt(100));
			testData.setSex("性别，弄个长一点的"+i+":" + random.nextInt(100));
			testData.setDate(new Date());
			dataList.add(testData);
		}
	}

}
