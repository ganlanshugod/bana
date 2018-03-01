package org.bana.common.util.office.annotation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bana.common.util.office.ExcelGenerator;
import org.bana.common.util.office.ExcelObject;
import org.bana.common.util.office.impl.BasicExcelGenerator;
import org.bana.common.util.office.impl.annotation.AnnotationExcelDownloadConfig;
import org.bana.common.util.office.impl.annotation.AnnotationExcelUploadConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AnnoGeneratorTest {

	private ExcelGenerator excelGenerator;
	
	private String basePath = "testexcel";
	
	@Before
	public void init(){
		excelGenerator = new BasicExcelGenerator();
	}
	
	@Test
	public void testSimpleUpload() throws FileNotFoundException{
		FileInputStream inputStream = new FileInputStream(new File("D:/test.xls"));
		Map<String,List<String>> mutiMap = new HashMap<String,List<String>>();
		mutiMap.put("额外配置", Arrays.asList("身份证","联系电话"));
		AnnotationExcelUploadConfig excelConfig = new AnnotationExcelUploadConfig(TestData.class);
		excelConfig.setMutiTitleMap(mutiMap);
		ExcelObject generatorObject = excelGenerator.generatorObject(inputStream,excelConfig.toSimpleExcelUploadConfig());
		
		Assert.assertNotNull(generatorObject);
//		List<? extends Object> data = generatorObject.getData("sheet1-test");
		List<? extends Object> data = generatorObject.getData(0);
		Assert.assertEquals(1000, data.size());
		System.out.println(data.get(500));
//		excelGenerator.generatorExcel(outputStream, excelObject);
	}
	
	@Test
	public void testSimpleDownload() throws IOException {
		// 第四种： D:\git\daotie\daotie
		AnnotationExcelDownloadConfig excelConfig = new AnnotationExcelDownloadConfig(TestDownData.class);
//        System.out.println(System.getProperty("user.dir"));
		excelConfig.setBaseFile("/office/学生成绩单模版-科目横版-v1.1.xls");
		Map<String,List<String>> mutiMap = new HashMap<String,List<String>>();
		mutiMap.put("额外配置", Arrays.asList("身份证","联系电话"));
		excelConfig.setMutiTitleMap(mutiMap);
        File file = new File(basePath + "/test2.xls");
        if(!file.getParentFile().exists()){
        	file.getParentFile().mkdirs();
        }
		FileOutputStream outputStream = new FileOutputStream(file);
		ExcelObject excelObject = new ExcelObject();
		List<TestDownData> dataList = new ArrayList<TestDownData>();
		addDate(dataList);
		excelObject.putData(dataList);
		excelObject.setExcelConfig(excelConfig);
		excelGenerator.generatorExcel(outputStream, excelObject);
		outputStream.close();
		System.out.println("样式数量为" + excelConfig.getCellStyleSize());
	}
	
	
	private void addDate(List<TestDownData> dataList){
		Random random = new Random();
		for (int i = 0; i < 1000; i++) {
			TestDownData testData = new TestDownData();
			testData.setAddress("地址内容，看看"+i+":" + random.nextInt(100));
			testData.setAge(random.nextInt(100));
			testData.setName("name"+i+":" + random.nextInt(100));
			testData.setSex("性别，弄个长一点的"+i+":" + random.nextInt(100));
			testData.setDate(new Date());
			Map<String,Object> mutiMap = new HashMap<String,Object>();
			mutiMap.put("身份证", "18263906395:"+i);
			mutiMap.put("联系电话","372928198708098136:"+i);
			testData.setMutiMap(mutiMap);
			dataList.add(testData);
		}
	}
}
