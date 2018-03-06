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
import org.bana.common.util.office.annotation.TestData;
import org.bana.common.util.office.impl.config.XmlExcelConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/** 
 * @ClassName: BasicExcelGeneratorTest 
 * @Description: 测试生成Excel的方法
 *  
 */
public class BasicExcelGeneratorTest {
	private BasicExcelGenerator excelGenerator;
	private XmlExcelConfig excelConfig;
	@Before
	public void init(){
		excelGenerator = new BasicExcelGenerator();
		//构造excelConfig
		excelConfig = new XmlExcelConfig();
		excelConfig.setConfigFile("/office/excelConfig.xml");
		excelConfig.init();
	}
	
	@Test
	public void testInit(){
		
	}
	
	@Test
	@Ignore
	public void testReadExcel2() throws IOException{
		XmlExcelConfig excelConfig = new XmlExcelConfig();
		excelConfig.setConfigFile("/office/excelConfigStudentScore.xml");
		excelConfig.init();
		FileInputStream inputStream = new FileInputStream(new File("C:/Users/liuwenjie/Desktop/成绩上传的测试excel.xls"));
		
		ExcelObject generatorObject = excelGenerator.generatorObject(inputStream,excelConfig);
		Assert.assertNotNull(generatorObject);
		List<? extends Object> data = generatorObject.getData("学生成绩单");
//		System.out.println();
//		Assert.assertEquals(1000, data.size());
//		System.out.println(data.get(500));
	}
	
	@Test
//	@Ignore
	public void testReadExcel() throws IOException {
		FileInputStream inputStream = new FileInputStream(new File("D:/test.xls"));
		ExcelObject generatorObject = excelGenerator.generatorObject(inputStream,excelConfig);
		Assert.assertNotNull(generatorObject);
		List<? extends Object> data = generatorObject.getData("sheet1-test");
		Assert.assertEquals(1000, data.size());
		System.out.println(data.get(500));
	}
	
	@Test
	@Ignore
	public void testReadMutiExcel() throws IOException {
		XmlExcelConfig excelConfig = new XmlExcelConfig();
		excelConfig.setConfigFile("/office/excelMutiConfig.xml");
		excelConfig.init();
		Map<String,List<String>> mutiMap = new HashMap<String, List<String>>();
//		mutiMap.put("性别", Arrays.asList("男","女"));
//		excelConfig.setMutiTitleMap(mutiMap);
		FileInputStream inputStream = new FileInputStream(new File("D:/test.xls"));
		ExcelObject generatorObject = excelGenerator.generatorObject(inputStream,excelConfig);
		Assert.assertNotNull(generatorObject);
		List<? extends Object> data = generatorObject.getData("sheet1-test");
		Assert.assertEquals(1000, data.size());
		System.out.println(data.get(500));
	}
	
	@Test
	@Ignore
	public void testReadMutiExcel2() throws IOException {
		XmlExcelConfig excelConfig = new XmlExcelConfig();
		excelConfig.setConfigFile("/office/excelConfigStudentScore.xml");
		excelConfig.init();
		Map<String,List<String>> mutiMap = new HashMap<String, List<String>>();
		mutiMap.put("科目", Arrays.asList("语文","数学","英语","总分","排名"));
//		excelConfig.setMutiTitleMap(mutiMap);
		FileInputStream inputStream = new FileInputStream(new File("D:/成绩模版－横版.xls"));
		ExcelObject generatorObject = excelGenerator.generatorObject(inputStream,excelConfig);
		Assert.assertNotNull(generatorObject);
		List<? extends Object> data = generatorObject.getData("学生成绩单");
		Assert.assertEquals(2007, data.size());
		System.out.println(data.get(500));
	}
	
	/** 
	* @Description: 测试导出学生成绩的列宽问题
	* @author liuwenjie   
	 * @throws IOException 
	 * @date 2016-10-11 下午3:34:06   
	*/ 
	@Test
	public void testGeneratorScore() throws IOException{
		XmlExcelConfig excelConfig = new XmlExcelConfig();
		excelConfig.setConfigFile("/office/excelStudentAchievement.xml");
		excelConfig.init();
		FileOutputStream outputStream = new FileOutputStream(new File("D:/成绩页面.xls"));
		ExcelObject excelObject = new ExcelObject();
		List<StudentScoreDto4Query> dataList = new ArrayList<StudentScoreDto4Query>();
		addScoreDate(dataList);
		excelObject.putData("学生成绩单", dataList);
		excelGenerator.generatorExcel(outputStream, excelObject);
		outputStream.close();
		System.out.println("样式数量为" + excelConfig.getCellStyleSize());
	}

	/** 
	* @Description: 增加学生的测试数据
	* @author liuwenjie   
	* @date 2016-10-11 下午3:38:44 
	* @param dataList  
	*/ 
	private void addScoreDate(List<StudentScoreDto4Query> dataList) {
		Random random = new Random();
		for (int i = 0; i < 1000; i++) {
			StudentScoreDto4Query scoreData = new StudentScoreDto4Query();
			scoreData.setTitle("标题"+i+random.nextInt(100));
			scoreData.setStudentId(new Long(i)+random.nextInt(100));
			scoreData.setStudentName("学生名字"+i+random.nextInt(100));
			scoreData.setClassOrgName("班级名字"+i+random.nextInt(100));
			scoreData.setSubject(random.nextInt()/2 == 0?"语文":"数学");
			scoreData.setScore(""+i+random.nextInt(100));
			scoreData.setExaminationTime("2016-10-11");
			if(!(i>30 && i<50)){
				scoreData.setComment("评论"+i+random.nextInt(100));
			}else if(i==40 || i==45){
				scoreData.setComment("超长评论的问题评论"+i+random.nextInt(100));
			}
			scoreData.setCreateName("任命"+i+random.nextInt(100));
			dataList.add(scoreData);
		}
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
		System.out.println("样式数量为" + excelConfig.getCellStyleSize());
	}
	
	@Test
	public void testGeneratorMutiExcel() throws IOException {
		XmlExcelConfig excelConfig = new XmlExcelConfig();
		excelConfig.setConfigFile("/office/excelMutiConfig.xml");
		excelConfig.init();
		Map<String,List<String>> mutiMap = new HashMap<String, List<String>>();
		mutiMap.put("性别", Arrays.asList("性别：（男）","性别：（女）"));
//		excelConfig.setMutiTitleMap(mutiMap);
		FileOutputStream outputStream = new FileOutputStream(new File("D:/test3.xls"));
		ExcelObject excelObject = new ExcelObject();
//		List<TestData> dataList = new ArrayList<TestData>();
//		addDate(dataList);
		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		addMapData(dataList);
		excelObject.putData("sheet1-test", dataList);
		excelGenerator.generatorExcel(outputStream, excelObject);
		outputStream.close();
		System.out.println("样式数量为" + excelConfig.getCellStyleSize());
	}
	
	/** 
	* @Description: 
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
//			testData.setName("name"+i+":" + random.nextInt(100));
//			testData.setSex("性别，弄个长一点的"+i+":" + random.nextInt(100));
			testData.setDate(new Date());
			dataList.add(testData);
		}
	}

}
